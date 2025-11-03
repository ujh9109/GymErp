// src/main/java/com/example/gymerp/service/impl/ScheduleServiceImpl.java
package com.example.gymerp.service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.repository.ScheduleDao;   // DAO는 repository 패키지에 있는 걸로 보임
import com.example.gymerp.service.ScheduleService; // ← 인터페이스 임포트

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleDao dao;

    @Override
    public Integer create(ScheduleDto dto) {
        dao.insert(dto);                 // selectKey로 dto.shNum 채워짐
        return dto.getShNum();
    }

    @Override
    public void update(ScheduleDto dto) {
        dao.update(dto);
    }

    @Override
    public void remove(Integer shNum) {
        dao.delete(shNum);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDto get(Integer shNum) {
        return dao.selectOne(shNum);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDto> getRange(LocalDateTime from, LocalDateTime to, Integer empNum, String refType) {
        return dao.selectRange(from, to, empNum, refType); // ✔ 인터페이스와 동일 시그니처
    }

    @Override
    public void updateTime(Integer shNum, LocalDateTime startTime, LocalDateTime endTime) {
        dao.updateTime(shNum, startTime, endTime);         // ✔ 인터페이스와 동일 시그니처
    }
}
