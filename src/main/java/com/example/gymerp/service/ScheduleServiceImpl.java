package com.example.gymerp.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.PtRegistrationDto;
import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.repository.LogDao;
import com.example.gymerp.repository.PtRegistrationMapper;
import com.example.gymerp.repository.ScheduleDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {


    private final ScheduleDao scheduleDao;
    private final PtRegistrationMapper ptRegistrationMapper;
	private final LogDao logDao;

	
	//어드민용 추가 작성
	  @Override
	  public Map<String, Object> searchForAdmin(Map<String, Object> p) {
		    String keyword = String.valueOf(p.getOrDefault("keyword","")).trim();
		    String codeBid = String.valueOf(p.getOrDefault("codeBid","")).trim();
		    int offset = (int) p.getOrDefault("offset", 0);
		    int limit  = (int) p.getOrDefault("limit", 20);

		    Map<String,Object> param = new HashMap<>();
		    param.put("keyword", keyword);
		    param.put("codeBid", codeBid);
		    param.put("offset", offset);
		    param.put("limit", limit);

		    List<ScheduleDto> list = scheduleDao.selectByFiltersForAdmin(param);
		    int total = scheduleDao.countByFiltersForAdmin(param);

		    Map<String,Object> res = new HashMap<>();
		    res.put("list", list);
		    res.put("total", total);
		    res.put("page", (offset/Math.max(limit,1))+1);
		    res.put("size", limit);
		    return res;
		}

	    private static Integer optInt(String s){ try{ return s==null?null:Integer.valueOf(s);}catch(Exception e){return null;}}
	    private static int optInt(String s,int d){ try{ return Integer.parseInt(s);}catch(Exception e){return d;}}
	    private static String str(String s){ return (s==null||s.isBlank())?null:s; }
	    private static Timestamp ts(String s){
	        if (s==null || s.isBlank()) return null;
	        return Timestamp.valueOf(s.replace("T"," ") + (s.length()==16?":00":"")); // 'YYYY-MM-DDTHH:mm'도 대응
	    }
	
	
	
    // 전체 일정 조회 
    @Override
    public List<ScheduleDto> getAllSchedules() {
        return scheduleDao.selectAll();
    }


	// 단건 조회
	@Override
	public ScheduleDto getScheduleById(int shNum) {
		return scheduleDao.selectByShNum(shNum);
	}

	// 직원별 일정 조회
	@Override
	public List<ScheduleDto> getSchedulesByEmpNum(int empNum) {
		return scheduleDao.selectByEmpNum(empNum);
	}

	// 날짜 범위 조회
	@Override
	public List<ScheduleDto> getSchedulesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
		return scheduleDao.selectByDateRange(startDate, endDate);
	}


    //일정 등록
    @Override
    @Transactional
    public int createSchedule(ScheduleDto schedule) {
        if (schedule.getEmpNum() == null || schedule.getEmpNum() <= 0) {
            throw new IllegalArgumentException("직원번호가 유효하지 않음");
        }

        // SCHEDULE 테이블에 일정 등록
        int result = scheduleDao.insert(schedule); // shNum이 selectKey로 채워짐

        // PT 일정이면서 회원 정보도 있을 때만 REGISTRATION 생성
        if (("PT".equalsIgnoreCase(schedule.getRefType()) ||
             "SCHEDULE-PT".equalsIgnoreCase(schedule.getCodeBid()))
            && schedule.getMemNum() != null) {

            PtRegistrationDto regDto = new PtRegistrationDto();
            regDto.setEmpNum(schedule.getEmpNum());
            regDto.setShNum(schedule.getShNum());
            regDto.setRegNote(schedule.getMemo());
            regDto.setMemNum(schedule.getMemNum());

            ptRegistrationMapper.insertPtRegistration(regDto);
        }

        return result; // insert 한 번만 호출
    }


    //일정 수정 
    @Override
    public int updateSchedule(ScheduleDto schedule) {
        if (schedule.getShNum() <= 0) {
            throw new IllegalArgumentException("수정할 일정 번호가 유효하지 않음");
        }
        return scheduleDao.update(schedule);
    }

	
}
