package com.example.gymerp.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpVacationDto;
import com.example.gymerp.repository.EmpVacationDao;
import com.example.gymerp.repository.EmpVacationDaysDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpVacationServiceImpl implements EmpVacationService {

    private final EmpVacationDao empVacationDao;
    private final EmpVacationDaysDao vacationDaysDao; // ✅ 누락된 주입 추가

    // ✅ 상태 변경 + 잔고 반영 (인터페이스 시그니처와 동일)
    @Override
    public int updateEmpVacationState(int vacNum, String vacState) {
        // 0) 현재 상태 조회
        EmpVacationDto cur = empVacationDao.selectEmpVacationById(vacNum);
        String prev = cur.getVacState();

        // 1) 상태값 검증(선택)
        if (!"APPROVED".equals(vacState) && !"REJECTED".equals(vacState)) {
            throw new IllegalArgumentException("state는 APPROVED|REJECTED만 허용");
        }

        // 2) 상태 업데이트
        int rows = empVacationDao.updateEmpVacationState(vacNum, vacState);

        // 3) 잔고 반영 (정일 단위: 시작~종료 포함)
        int useDays = daysInclusive(cur.getVacStartedAt(), cur.getVacEndedAt());

        if (!"APPROVED".equals(prev) && "APPROVED".equals(vacState)) {
            // 미승인 → 승인 : 차감
            vacationDaysDao.consumeDays(Map.of("empNum", cur.getEmpNum(), "useDays", useDays));
        } else if ("APPROVED".equals(prev) && !"APPROVED".equals(vacState)) {
            // 승인 → 비승인 : 복원
            vacationDaysDao.restoreDays(Map.of("empNum", cur.getEmpNum(), "useDays", useDays));
        }

        return rows;
    }

    private int daysInclusive(java.sql.Date start, java.sql.Date end) { // ✅ 중복 제거 (하나만)
        long s = start.toLocalDate().toEpochDay();
        long e = end.toLocalDate().toEpochDay();
        return (int) (e - s) + 1;
    }

    @Override
    public List<EmpVacationDto> getAllEmpVacations() {
        return empVacationDao.selectAllEmpVacations();
    }

    @Override
    public List<EmpVacationDto> getEmpVacationsByEmpNum(int empNum) {
        return empVacationDao.selectEmpVacationsByEmpNum(empNum);
    }

    @Override
    public EmpVacationDto getEmpVacationById(int vacNum) {
        return empVacationDao.selectEmpVacationById(vacNum);
    }

    @Override
    public int addEmpVacation(EmpVacationDto dto) {
        return empVacationDao.insertEmpVacation(dto);
    }

    @Override
    public int updateEmpVacation(EmpVacationDto dto) {
        return empVacationDao.updateEmpVacation(dto);
    }

    @Override
    public int deleteEmpVacation(int vacNum) {
        return empVacationDao.deleteEmpVacation(vacNum);
    }
}
