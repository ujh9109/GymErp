package com.example.gymerp.repository;

import java.time.LocalDateTime; // ✅
// import java.sql.Timestamp;  // ❌ 사용 안 함, 제거
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.gymerp.dto.ScheduleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScheduleDaoImpl implements ScheduleDao {

    private static final String NS = "ScheduleMapper.";
    private final SqlSessionTemplate sst;

    @Override
    public int insert(ScheduleDto dto) { return sst.insert(NS + "insert", dto); }

    @Override
    public int update(ScheduleDto dto) { return sst.update(NS + "update", dto); }

    @Override
    public int delete(Integer shNum) { return sst.delete(NS + "delete", shNum); }

    @Override
    public ScheduleDto selectOne(Integer shNum) { return sst.selectOne(NS + "selectOne", shNum); }

    @Override // ✅ 인터페이스에 맞춰 오버라이드
    public List<ScheduleDto> selectRange(LocalDateTime from, LocalDateTime to, Integer empNum, String refType) {
        Map<String,Object> p = new HashMap<>();
        p.put("from", from); p.put("to", to);
        p.put("empNum", empNum); p.put("refType", refType);
        return sst.selectList(NS + "selectRange", p);
    }

    @Override // ✅ 인터페이스에 맞춰 오버라이드
    public int updateTime(Integer shNum, LocalDateTime startTime, LocalDateTime endTime) {
        Map<String,Object> p = new HashMap<>();
        p.put("shNum", shNum); p.put("startTime", startTime); p.put("endTime", endTime);
        return sst.update(NS + "updateTime", p);
    }
}
