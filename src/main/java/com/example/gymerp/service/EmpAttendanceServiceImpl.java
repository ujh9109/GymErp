package com.example.gymerp.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpAttendanceDto;
import com.example.gymerp.repository.EmpAttendanceDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpAttendanceServiceImpl implements EmpAttendanceService {

    private final EmpAttendanceDao empAttendanceDao;

    @Override
    public List<EmpAttendanceDto> getAllEmpAttendances() {
        return empAttendanceDao.selectAllEmpAttendances();
    }

    @Override
    public List<EmpAttendanceDto> getEmpAttendancesByEmpNum(int empNum) {
        return empAttendanceDao.selectEmpAttendancesByEmpNum(empNum);
    }

    @Override
    public EmpAttendanceDto getEmpAttendanceById(int attNum) {
        return empAttendanceDao.selectEmpAttendanceById(attNum);
    }

    @Override
    public int addEmpAttendance(EmpAttendanceDto dto) {
        return empAttendanceDao.insertEmpAttendance(dto);
    }

    @Override
    public int updateEmpAttendanceCheckOut(int attNum, Timestamp checkOut) {
        return empAttendanceDao.updateEmpAttendanceCheckOut(attNum, checkOut);
    }

    @Override
    public int updateEmpAttendance(EmpAttendanceDto dto) {
        return empAttendanceDao.updateEmpAttendance(dto);
    }

    @Override
    public int deleteEmpAttendance(int attNum) {
        return empAttendanceDao.deleteEmpAttendance(attNum);
    }
}
