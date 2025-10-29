package com.example.gymerp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.EmpVacationDto;
import com.example.gymerp.repository.EmpVacationDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpVacationServiceImpl implements EmpVacationService {

    private final EmpVacationDao empVacationDao;

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
    public int updateEmpVacationState(int vacNum, String vacState) {
        return empVacationDao.updateEmpVacationState(vacNum, vacState);
    }

    @Override
    public int deleteEmpVacation(int vacNum) {
        return empVacationDao.deleteEmpVacation(vacNum);
    }
}
