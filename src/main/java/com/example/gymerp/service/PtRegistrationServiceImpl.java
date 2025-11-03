package com.example.gymerp.service;

import java.util.List;
import java.time.LocalDateTime;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.PtRegistrationDto;
import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.repository.LogDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PtRegistrationServiceImpl implements PtRegistrationService {

    private final SqlSession session;
    private final LogDao logDao;

    // =========================================
    // 전체 예약 목록 조회
    // =========================================
    @Override
    public List<PtRegistrationDto> getAllPtRegistration(Integer empNum, String date) {
        return session.selectList("com.example.gymerp.repository.PtRegistrationMapper.getAllPtRegistration",
                                  java.util.Map.of("empNum", empNum, "date", date));
    }

    // =========================================
    // 단일 예약 조회
    // =========================================
    @Override
    public PtRegistrationDto getPtRegistrationById(int regNum) {
        return session.selectOne("com.example.gymerp.repository.PtRegistrationMapper.getPtRegistrationById", regNum);
    }

    // =========================================
    // 예약 등록 (+ PT_LOG 소비 -1)
    // =========================================
    @Override
    @Transactional
    public int insertPtRegistration(PtRegistrationDto dto) {
        // 1️⃣ 스케줄 먼저 등록
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setEmpNum(dto.getEmpNum());
        scheduleDto.setStartTime(dto.getRegTime());
        scheduleDto.setEndTime(dto.getLastTime());
        scheduleDto.setMemo(dto.getRegNote());
        scheduleDto.setCodeBid("SCHEDULE-PT"); // 스케줄에서만 사용

        session.insert("ScheduleMapper.insert", scheduleDto);
        dto.setShNum(scheduleDto.getShNum());

        // 2️⃣ PT 등록
        int result = session.insert("com.example.gymerp.repository.PtRegistrationMapper.insertPtRegistration", dto);

        // 3️⃣ 로그 기록 (소비)
        if (result > 0) {
            PtLogDto logDto = PtLogDto.builder()
                .memNum((long) dto.getMemNum())
                .empNum((long) dto.getEmpNum())
                .regId((long) dto.getRegNum())
                .status("소비")
                .countChange(-1L)
                .createdAt(LocalDateTime.now())
                .salesId(null)
                .build();

            logDao.insertPtConsumeLog(logDto);
        }

        return result;
    }


    // =========================================
    // 예약 수정
    // =========================================
    @Override
    public int updatePtRegistration(PtRegistrationDto dto) {
        return session.update("com.example.gymerp.repository.PtRegistrationMapper.updatePtRegistration", dto);
    }

    // =========================================
    // 예약 삭제 (+ PT_LOG 복구 +1)
    // =========================================
    @Override
    @Transactional
    public int deletePtRegistration(int regNum) {
        // 1️⃣ 기존 소비 로그 찾기
        var existingLog = logDao.selectPtLogByRegId(regNum);
        if (existingLog != null) {
            // 2️⃣ 복구 로그 추가
            PtLogDto cancelLog = PtLogDto.builder()
                .memNum(existingLog.getMemNum())
                .empNum(existingLog.getEmpNum())
                .status("예약취소")
                .countChange(1L)
                .createdAt(LocalDateTime.now())
                .salesId(null)
                .regId(null)
                .build();
            logDao.insertPtCancelLog(cancelLog);
        }

        // 3️⃣ 예약 삭제
        return session.delete("com.example.gymerp.repository.PtRegistrationMapper.deletePtRegistration", regNum);
    }

	
}
