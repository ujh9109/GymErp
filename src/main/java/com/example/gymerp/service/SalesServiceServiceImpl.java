package com.example.gymerp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.SalesService;
import com.example.gymerp.dto.VoucherLogDto;
import com.example.gymerp.repository.EmpDao;
import com.example.gymerp.repository.LogDao;
import com.example.gymerp.repository.MemberDao;
import com.example.gymerp.repository.SalesServiceDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesServiceServiceImpl implements SalesServiceService {

    private final SalesServiceDao salesServiceDao;
    private final LogService logService;
    private final MemberDao memberDao;
    private final EmpDao empDao;
    private final LogDao logDao;

    /* ===============================
       [1. 조회]
    =============================== */

    // 전체 서비스 판매 내역 조회
    @Override
    public List<SalesService> getAllSalesServices() {
        return salesServiceDao.selectAllSalesServices();
    }

    // 단일 서비스 판매 내역 조회
    @Override
    public SalesService getSalesServiceById(long serviceSalesId) {
        return salesServiceDao.selectSalesServiceById(serviceSalesId);
    }


    /* ===============================
       [2. 등록]
    =============================== */

    // 서비스 판매 등록 (회원권/ PT 등록 및 로그 생성)
    @Override
    @Transactional
    public int createSalesService(SalesService salesService) {

        // 회원명, 트레이너명 조회
        String memberName = memberDao.selectMemberNameById(salesService.getMemNum().intValue());
        String trainerName = empDao.selectEmployeeNameById(salesService.getEmpNum().intValue());

        // ✅ 회원권 등록 또는 연장 처리
        if ("VOUCHER".equalsIgnoreCase(salesService.getServiceType())) {
            boolean hasValidVoucher = logService.isVoucherValid(salesService.getMemNum());
            LocalDate start = LocalDate.now();
            LocalDate end = start.plusDays(salesService.getActualCount());

            VoucherLogDto voucher = VoucherLogDto.builder()
                    .memNum(salesService.getMemNum())
                    .memberName(memberName)
                    .startDate(start.toString())
                    .endDate(end.toString())
                    .build();

            // 신규 or 연장 분기
            if (!hasValidVoucher) logService.insertVoucherLog(voucher);
            else logService.extendVoucherLog(voucher);
        }

        // ✅ PT 등록 처리
        else if ("PT".equalsIgnoreCase(salesService.getServiceType())) {

            // 회원권 유효성 검증
            if (!logService.isVoucherValid(salesService.getMemNum()))
                throw new IllegalStateException("회원권이 유효하지 않아 PT 등록이 불가합니다.");

            // PT 충전 로그 생성
            PtLogDto ptLog = PtLogDto.builder()
                    .memNum(salesService.getMemNum())
                    .empNum(salesService.getEmpNum())
                    .status("충전")
                    .countChange(Long.valueOf(salesService.getActualCount()))
                    .salesId(salesService.getServiceSalesId())
                    .createdAt(LocalDateTime.now())
                    .build();

            logService.addPtChargeLog(ptLog);
        }

        // DB 등록 (판매내역)
        return salesServiceDao.insertSalesService(salesService);
    }


    /* ===============================
       [3. 수정]
    =============================== */

    // 서비스 판매 수정 (부분환불/기간 단축 등 포함)
    @Override
    @Transactional
    public int updateSalesService(SalesService salesService) {

        // 기존 판매내역 조회
        SalesService existing = salesServiceDao.selectSalesServiceById(salesService.getServiceSalesId());
        if (existing == null)
            throw new IllegalArgumentException("해당 판매 내역이 존재하지 않습니다.");
        
        // ✅ 상품 타입 변경 방지
        if (!existing.getServiceType().equalsIgnoreCase(salesService.getServiceType())) {
            throw new IllegalStateException("상품 타입(PT/회원권)은 수정할 수 없습니다.");
        }

        // ✅ PT 수정 시 (부분환불 처리)
        if ("PT".equalsIgnoreCase(existing.getServiceType())) {
            int oldCount = existing.getActualCount();
            int newCount = salesService.getActualCount();

            // 부분환불 분기
            if (newCount < oldCount) {
                int refundCount = oldCount - newCount;

                // 부분환불 로그 생성
                PtLogDto refundLog = PtLogDto.builder()
                        .memNum(existing.getMemNum())
                        .empNum(existing.getEmpNum())
                        .status("부분환불")
                        .countChange(Long.valueOf(-refundCount))
                        .salesId(existing.getServiceSalesId())
                        .createdAt(LocalDateTime.now())
                        .build();

                logService.addPtPartialRefundLog(refundLog);
            }
        }

        // ✅ 회원권 수정 시 (기간 단축 → 부분환불)
        else if ("VOUCHER".equalsIgnoreCase(existing.getServiceType())) {
            VoucherLogDto voucher = logService.getVoucherByMember(existing.getMemNum());
            if (voucher != null) {
                LocalDate endDate = LocalDate.parse(voucher.getEndDate());
                LocalDate newEndDate = endDate.minusDays(existing.getBaseCount() - salesService.getBaseCount());
                voucher.setEndDate(newEndDate.toString());

                // 기간 단축 = 부분환불 처리
                logService.partialRefundVoucherLog(voucher);
            }
        }

        // DB 업데이트
        return salesServiceDao.updateSalesService(salesService);
    }


    /* ===============================
       [4. 삭제]
    =============================== */

    // 서비스 판매 삭제 (환불/회귀 처리 포함)
    @Override
    @Transactional
    public int deleteSalesService(long serviceSalesId) {

        // 기존 판매내역 조회
        SalesService sale = salesServiceDao.selectSalesServiceById(serviceSalesId);
        if (sale == null)
            throw new IllegalArgumentException("해당 판매 내역이 존재하지 않습니다.");

        // ✅ PT 전체 환불 처리
        if ("PT".equalsIgnoreCase(sale.getServiceType())) {
            int remaining = logService.getRemainingPtCount(sale.getMemNum());

            // 일부 사용된 PT는 삭제 불가
            if (remaining < sale.getActualCount())
                throw new IllegalStateException("이미 일부 이용된 PT는 삭제할 수 없습니다.");

            // 전체 환불 로그 생성
            PtLogDto refundLog = PtLogDto.builder()
                    .memNum(sale.getMemNum())
                    .empNum(sale.getEmpNum())
                    .status("전체환불")
                    .countChange(Long.valueOf(-sale.getActualCount()))
                    .salesId(sale.getServiceSalesId())
                    .createdAt(LocalDateTime.now())
                    .build();

            logService.addPtFullRefundLog(refundLog);
        } 

        // ✅ 회원권 전체환불 (회귀 처리)
        else if ("VOUCHER".equalsIgnoreCase(sale.getServiceType())) {
            VoucherLogDto voucher = logService.getVoucherByMember(sale.getMemNum());
            if (voucher != null) logService.rollbackVoucherLog(voucher);
        }

        // DB 논리삭제
        return salesServiceDao.deleteSalesService(serviceSalesId);
    }
}
