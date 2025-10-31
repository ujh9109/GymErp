package com.example.gymerp.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.example.gymerp.dto.PtRegistrationDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PtRegistrationServiceImpl implements PtRegistrationService {

    private final SqlSession session; 

    @Override
    public List<PtRegistrationDto> getAllPtRegistration(Integer empNum, String date) {
        return session.selectList("PtRegistrationMapper.getAllPtRegistration");
    }

    @Override
    public PtRegistrationDto getPtRegistrationById(int regNum) {
        return session.selectOne("PtRegistrationMapper.getPtRegistrationById", regNum);
    }

    @Override
    public int insertPtRegistration(PtRegistrationDto dto) {
        return session.insert("PtRegistrationMapper.insertPtRegistration", dto);
    }

    @Override
    public int updatePtRegistration(PtRegistrationDto dto) {
        return session.update("PtRegistrationMapper.updatePtRegistration", dto);
    }

    @Override
    public int deletePtRegistration(int regNum) {
        return session.delete("PtRegistrationMapper.deletePtRegistration", regNum);
    }
}
