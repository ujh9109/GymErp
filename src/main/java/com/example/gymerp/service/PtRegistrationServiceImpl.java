package com.example.gymerp.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gymerp.dto.PtRegistrationDto;

import lombok.RequiredArgsConstructor;

/**
 * 📦 PtRegistrationServiceImpl
 * ------------------------------------------------------------ PT(개인 트레이닝) 예약
 * 관련 로직을 담당하는 서비스 클래스. REGISTRATION 테이블의 CRUD를 수행하며, 로그(PT_LOG)는
 * ScheduleServiceImpl에서 처리한다.
 *
 * 주요 기능: - PT 예약 등록/수정/삭제 - 일정번호(shNum)로 등록번호(regNum) 조회
 */
@Service
@RequiredArgsConstructor
public class PtRegistrationServiceImpl implements PtRegistrationService {

	/* ============================= 💾 의존성 주입 ============================= */
	private final SqlSession session; // MyBatis 세션 (단건 / 간단 조회/수정용)
	private final SqlSessionTemplate sessionT; // Spring 관리형 SqlSessionTemplate (Thread-safe)

	/* ============================= 📖 조회 관련 ============================= */


	/**
	 * 전체 PT 예약 조회 ------------------------------------------------------------ 현재는
	 * empNum, date 파라미터를 사용하지 않지만, 향후 트레이너별 / 날짜별 필터 기능 추가를 고려한 구조이다.
	 */
	@Override
	public List<PtRegistrationDto> getAllPtRegistration(Integer empNum, String date) {
		// Mapper에서 모든 REGISTRATION 데이터 조회
		return session.selectList("PtRegistrationMapper.getAllPtRegistration");
	}

	/**
	 * PT 예약 단건 조회 ------------------------------------------------------------
	 * regNum(등록번호)을 기준으로 단일 예약 정보를 반환한다.
	 */

	// 단일 예약 조회

	@Override
	public PtRegistrationDto getPtRegistrationById(long regNum) {
		return session.selectOne("PtRegistrationMapper.getPtRegistrationById", regNum);
	}

	/* ============================= 🟢 등록 ============================= */

	/**
	 * PT 예약 등록 ------------------------------------------------------------ 1️⃣
	 * Mapper를 통해 REGISTRATION 테이블에 데이터 삽입 2️⃣ regNum은 SEQUENCE(NEXTVAL)로 자동 생성됨 3️⃣
	 * 로그는 ScheduleServiceImpl에서 별도로 처리
	 */

	// 예약 등록 (+ PT_LOG 소비 -1)
	@Transactional
	@Override
	public int insertPtRegistration(PtRegistrationDto dto) {

		if (dto.getShNum() == null) {
			throw new IllegalArgumentException("shNum이 없습니다. 먼저 일정(SCHEDULE)을 생성하세요.");
		}

		// shNum을 UNIQUE로 운영한다면 사전 중복 체크
		Integer dup = session.selectOne("PtRegistrationMapper.existsRegistrationByShNum", dto.getShNum());
		if (dup != null && dup > 0) {
			throw new IllegalStateException("이미 해당 일정(shNum=" + dto.getShNum() + ")으로 등록이 존재합니다.");
		}

		try {
			int rows = session.insert("PtRegistrationMapper.insertPtRegistration", dto);
			System.out.println("[PT 예약 등록 완료] regNum=" + dto.getRegNum());
			// 필요 시: session.insert("LogMapper.insertPtConsumeLog", ...);
			return rows;
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalStateException("중복 등록입니다. (유니크 제약 충돌) shNum=" + dto.getShNum(), e);
		}
	}

	/* ============================= 🔵 수정 ============================= */

	/**
	 * PT 예약 수정 ------------------------------------------------------------ 예약된 PT
	 * 정보를 수정한다. (예: 트레이너 변경, 메모 변경 등)
	 */
	@Transactional
	@Override
	public int updatePtRegistration(PtRegistrationDto dto) {
		return session.update("PtRegistrationMapper.updatePtRegistration", dto);
	}

	/* ============================= 🔴 삭제 ============================= */

	/**
	 * PT 예약 삭제 ------------------------------------------------------------ 1️⃣ 삭제
	 * 전 regNum으로 해당 예약 존재 여부 확인 2️⃣ 존재하지 않으면 예외 발생 3️⃣ Mapper를 통해 REGISTRATION에서
	 * 데이터 삭제 4️⃣ 로그 등록은 ScheduleServiceImpl에서 이미 수행하므로 생략
	 */

	// 예약 삭제 (+ PT_LOG 복구 +1)

	@Transactional
	@Override
	public int deletePtRegistration(int regNum) {
		// 1️⃣ 삭제 대상 조회
		PtRegistrationDto target = session.selectOne("PtRegistrationMapper.getPtRegistrationById", regNum);
		if (target == null) {
			throw new IllegalArgumentException("존재하지 않는 PT 등록입니다.");
		}

		System.out.println("[PT 예약 삭제 시작] regNum=" + regNum);

		// 2️⃣ PT 등록 데이터 삭제
		int deleted = session.delete("PtRegistrationMapper.deletePtRegistration", regNum);
		System.out.println("[PT 예약 삭제 완료] deleted=" + deleted);

		return deleted;
	}

	/*
	 * ============================= 🔍 조회 (by 일정번호) =============================
	 */

	/**
	 * 일정번호(shNum)로 PT 등록번호(regNum) 조회
	 * ------------------------------------------------------------ SCHEDULE 삭제 시
	 * REGISTRATION의 regNum을 찾기 위해 사용. ex) 일정 삭제 시 → 해당 일정(shNum)에 연결된 REGISTRATION
	 * 찾기
	 */

    //일정번호(shNum)로 PT 등록번호(regNum) 조회 
    @Override
    public Integer findRegNumByShNum(int shNum) {
        System.out.println("[findRegNumByShNum 호출] shNum=" + shNum);
    return sessionT.selectOne("PtRegistrationMapper.findRegNumByShNum", shNum);
    }

	

}
