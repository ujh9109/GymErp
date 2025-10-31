package com.example.gymerp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.SalesService;
import com.example.gymerp.dto.VoucherLogDto;
import com.example.gymerp.repository.EmpDao;
import com.example.gymerp.repository.MemberDao;
import com.example.gymerp.repository.SalesServiceDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesServiceServiceImpl implements SalesServiceService {

    private final SalesServiceDao salesServiceDao;
    private final LogService logService;
    private final MemberDao memberDao;
    private final EmpDao empDao;

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

    @Override
    @Transactional
    public int createSalesService(SalesService salesService) {

        String memberName = memberDao.selectMemberNameById(salesService.getMemNum().intValue());
        String trainerName = empDao.selectEmployeeNameById(salesService.getEmpNum().intValue());

        // ------------------------------
        // [회원권(VOUCHER) 상품]
        // ------------------------------
        if ("VOUCHER".equalsIgnoreCase(salesService.getServiceType())) {

            // 현재 회원권 유효 여부 확인
            boolean hasValidVoucher = logService.isVoucherValid(salesService.getMemNum());
            LocalDateTime now = LocalDateTime.now();

            LocalDateTime startDate;
            LocalDateTime endDate;

            // 회원권이 없거나 만료된 경우 → 새로 시작
            if (!hasValidVoucher) {
                startDate = now;
                endDate = now.plusDays(salesService.getBaseCount()); // baseCount = 이용일수
            }
            // 회원권이 유효한 경우 → 기간 연장
            else {
                VoucherLogDto existing = logService.getVoucherByMember(salesService.getMemNum());
                startDate = existing.getStartDate() != null 
                            ? LocalDateTime.parse(existing.getStartDate() + "T00:00:00") : now;
                endDate = LocalDateTime.parse(existing.getEndDate() + "T00:00:00")
                            .plusDays(salesService.getBaseCount());
            }

            VoucherLogDto voucher = VoucherLogDto.builder()
                    .memNum(salesService.getMemNum())
                    .memberName(memberName)
                    .startDate(startDate.toLocalDate().toString())
                    .endDate(endDate.toLocalDate().toString())
                    .build();

            logService.saveOrUpdateVoucher(voucher);
        } 

        // ------------------------------
        // [PT 상품]
        // ------------------------------
        else if ("PT".equalsIgnoreCase(salesService.getServiceType())) {

            // 회원권이 유효하지 않으면 예외 발생
            if (!logService.isVoucherValid(salesService.getMemNum()))
                throw new IllegalStateException("회원권이 만료되었거나 존재하지 않습니다.");

            PtLogDto ptLog = PtLogDto.builder()
                    .memNum(salesService.getMemNum())
                    .empNum(salesService.getEmpNum())
                    .trainerName(trainerName)
                    .memberName(memberName)
                    .status("충전")
                    .countChange(salesService.getActualCount())
                    .totalAmount(salesService.getActualAmount().intValue())
                    .consumeAmount(0)
                    .createdAt(LocalDateTime.now())
                    .build();

            logService.addPtChargeLog(ptLog);
        }

        // ------------------------------
        // [판매 테이블 insert]
        // ------------------------------
        return salesServiceDao.insertSalesService(salesService);
    }

    // 판매 수정
    @Override
    public int updateSalesService(SalesService salesService) {
        SalesService existing = salesServiceDao.selectSalesServiceById(salesService.getServiceSalesId());
        if (existing == null) throw new IllegalArgumentException("해당 판매 내역이 존재하지 않습니다.");

        int updated = salesServiceDao.updateSalesService(salesService);

        if ("PT".equalsIgnoreCase(existing.getServiceType())) {
            String memberName = memberDao.selectMemberNameById(existing.getMemNum().intValue());
            String trainerName = empDao.selectEmployeeNameById(existing.getEmpNum().intValue());

            PtLogDto ptLog = PtLogDto.builder()
                    .memNum(existing.getMemNum())
                    .empNum(existing.getEmpNum())
                    .trainerName(trainerName)
                    .memberName(memberName)
                    .totalAmount(existing.getActualAmount().intValue())
                    .createdAt(existing.getCreatedAt())
                    .countChange(salesService.getActualCount())
                    .totalAmount(salesService.getActualAmount().intValue())
                    .consumeAmount(salesService.getAvgPrice().intValue())
                    .build();
            logService.updatePtLogManual(ptLog);
        }

        return updated;
    }

    // 판매 삭제
    @Override
    public int deleteSalesService(long serviceSalesId) {
        SalesService sale = salesServiceDao.selectSalesServiceById(serviceSalesId);
        if (sale == null) throw new IllegalArgumentException("해당 판매 내역이 존재하지 않습니다.");

        if ("PT".equalsIgnoreCase(sale.getServiceType())) {
            int remaining = logService.getRemainingPtCount(sale.getMemNum());
            if (remaining < sale.getActualCount())
                throw new IllegalStateException("이미 일부 이용 이력이 있어 삭제할 수 없습니다.");

            ((LogServiceImpl) logService).deletePtLogBySaleInfo(
                    sale.getMemNum(),
                    sale.getActualAmount().intValue(),
                    sale.getCreatedAt()
            );
        } 
        else if ("VOUCHER".equalsIgnoreCase(sale.getServiceType())) {
            logService.rollbackOrDeleteVoucher(sale.getMemNum());
        }

        return salesServiceDao.deleteSalesService(serviceSalesId);
    }

    // 판매 내역 조회 (검색 + 페이지네이션)
    @Override
    public Map<String, Object> getPagedServiceSales(String keyword, int page, int scrollStep,
                                                    Long empNum, Long memNum, List<Long> serviceIds,
                                                    String startDate, String endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("page", page);
        param.put("scrollStep", scrollStep);
        param.put("empNum", empNum);
        param.put("memNum", memNum);
        param.put("serviceIds", serviceIds);
        param.put("startDate", startDate);
        param.put("endDate", endDate);

        List<SalesService> list = salesServiceDao.selectPagedServiceSales(param);
        int total = salesServiceDao.countPagedServiceSales(param);

        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        result.put("total", total);
        return result;
    }

    // 서비스 매출 그래프 조회
    @Override
    public List<Map<String, Object>> getServiceSalesGraph(String startDate, String endDate,
                                                          List<Long> serviceIds, Long memNum, Long empNum) {
        Map<String, Object> param = new HashMap<>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("serviceIds", serviceIds);
        param.put("memNum", memNum);
        param.put("empNum", empNum);
        return salesServiceDao.selectServiceSalesGraph(param);
    }
}
