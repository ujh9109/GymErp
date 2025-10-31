// src/pages/Home.jsx

import React from 'react';
import { NavLink } from 'react-router-dom';

function Home() {
    return <>
        
            <h1>박종복 테스트 페이지 입니다</h1>
             <ul>
                <li>
                    <NavLink to="/EmpVacationList" >휴가 리스트 테스트</NavLink>
                </li>
                <li>
                    <NavLink to="/EmpAttendanceList">출퇴근 리스트 테스트</NavLink>
                </li>
                <li>
                    <NavLink to="/EmpAttendanceMy">내 출퇴근 테스트</NavLink>
                </li>
                <li>
                    <NavLink to="/EmpAttendanceView">출퇴근 상세 테스트</NavLink>
                </li>
            </ul>
        </>
    
}

export default Home;