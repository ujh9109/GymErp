package com.example.gymerp.service;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.PtRegistrationDto;
import com.example.gymerp.dto.ScheduleDto;
import com.example.gymerp.repository.LogDao;
import com.example.gymerp.repository.ScheduleDao;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {


    private final ScheduleDao scheduleDao; // 일정테이블 접근
    private final PtRegistrationService ptRegistrationService; // PT 등록 테이블 접근
	private final LogDao logDao; // PT 로그 테이블 접근

	
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

	/** 기간별 일정 조회 */
    @Override
    public List<ScheduleDto> getSchedulesByDateRange(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate) {
        return scheduleDao.selectByDateRange(startDate, endDate);
    }


    //일정 등록
    @Override
    @Transactional
    public int createSchedule(ScheduleDto schedule) {
    	// 1️⃣ 일정 기본 등록
        int result = scheduleDao.insert(schedule);
        System.out.println("[일정 등록 완료] shNum=" + schedule.getShNum() + ", codeBid=" + schedule.getCodeBid());

        // 2️⃣ PT 일정인 경우만 추가 로직 수행
        if ("SCHEDULE-PT".equalsIgnoreCase(schedule.getCodeBid())) {
            
            // 회원이 선택되지 않은 경우 → PT 등록 생략
            if (schedule.getMemNum() == null) {
                System.out.println("[PT 등록 생략] memNum=null");
                return result;
            }

            // 3️⃣ REGISTRATION 테이블에 PT 예약 등록
            PtRegistrationDto reg = PtRegistrationDto.builder()
                    .empNum((long) schedule.getEmpNum())                                 // 직원 번호
                    .memNum(schedule.getMemNum() == null ? null : schedule.getMemNum().longValue()) // 회원 번호
                    .shNum((long) schedule.getShNum())                                   // 일정 번호
                    .regNote(schedule.getMemo())                                         // 메모
                    .build();

            ptRegistrationService.insertPtRegistration(reg);
            System.out.println("[PT 예약 등록 완료] regNum=" + reg.getRegNum());

            // 4️ PT_LOG 테이블에 "소비" 로그 등록 (-1)
            PtLogDto consumeLog = PtLogDto.builder()
                    .memNum(schedule.getMemNum() == null ? null : schedule.getMemNum().longValue())
                    .empNum((long) schedule.getEmpNum())
                    .regId(reg.getRegNum() == null ? null : reg.getRegNum().longValue())
                    .status("소비")   // 소비 로그
                    .countChange(-1L) // 회차 차감
                    .build();

            logDao.insertPtConsumeLog(consumeLog);
            System.out.println("[PT 소비 로그 등록 완료]");
        }

        return result;
    }


    //일정 수정 
    @Transactional
    @Override
    public int updateSchedule(ScheduleDto schedule) {
        int updated = scheduleDao.update(schedule);
        System.out.println("[일정 수정 완료] shNum=" + schedule.getShNum());
        return updated;
    }
    
    
    // 일정 삭제
    @Override
    @Transactional
    public int deleteSchedule(int shNum) {
        // 1️⃣ 삭제 대상 조회
        ScheduleDto target = scheduleDao.selectByShNum(shNum);
        if (target == null) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }

        System.out.println("[일정 삭제 요청] shNum=" + shNum + ", type=" + target.getCodeBid());

        // 2️ PT 일정인 경우만 추가 로직 수행
        if ("SCHEDULE-PT".equalsIgnoreCase(target.getCodeBid())) {
            Long regNum = ptRegistrationService.findRegNumByShNum(shNum);

            if (regNum != null) {
                // ✅ 회원이 존재할 때만 PT_LOG 기록
                if (target.getMemNum() != null) {
                    PtLogDto cancelLog = PtLogDto.builder()
                            .memNum(target.getMemNum().longValue())
                            .empNum((long) target.getEmpNum())
                            .regId(regNum.longValue())
                            .status("예약취소")
                            .countChange(1L)
                            .build();

                    logDao.insertPtCancelLog(cancelLog);
                    System.out.println("[PT 예약취소 로그 등록 완료] regNum=" + regNum);
                } else {
                    System.out.println("[회원 없는 일정 → PT 로그 생략]");
                }

                // REGISTRATION 테이블에서 PT 등록 삭제
                ptRegistrationService.deletePtRegistration(regNum);
                System.out.println("[PT 등록 데이터 삭제 완료]");
            } else {
                System.out.println("[PT 등록번호 없음 → 로그 등록 생략]");
            }
        }

        // 3️ SCHEDULE 테이블에서 일정 삭제
        int deleted = scheduleDao.delete(shNum);
        System.out.println("[일정 삭제 완료] deleted=" + deleted);

        return deleted;
    }
}