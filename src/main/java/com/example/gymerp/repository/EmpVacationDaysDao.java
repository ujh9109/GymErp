package com.example.gymerp.repository;

import java.util.Map;

public interface EmpVacationDaysDao {
	 Map<String, Object> selectByEmpNum(int empNum);
	    int insertDays(Map<String, Object> p);        // vacDaysNum, empNum, earnedDays, remainingDays, usedDays
	    int consumeDays(Map<String, Object> p);       // empNum, useDays
	    int restoreDays(Map<String, Object> p);       // empNum, useDays
}
