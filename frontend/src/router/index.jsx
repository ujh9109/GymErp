
/*
    src/router/index.jsx 파일

    -파일명을 index.js or index.jsx 로 작성하면 외부에서 router 폴더 까지만
    import 했을 때 자동으로 index.js or index.jsx 파일에서 export 된
    자원을 사용할 수 있다
    -index 는 약속된 파일명이다.
*/

// src/router/index.jsx
import { createBrowserRouter, Navigate } from "react-router-dom";
import App from "../App.jsx";
import EmpList from "../pages/EmpList.jsx";
import EmpDetail from "../pages/EmpDetail.jsx";
import EmpEdit from "../pages/EmpEdit.jsx";

import Home from "../pages/JONGBOKHome.jsx";
import EmpVacationList from "../pages/EmpVacation/list";
import EmpAttendanceList from "../pages/EmpAttendance/list";
import EmpAttendanceMy from "../pages/EmpAttendance/myAttendance";
import EmpAttendanceView from "../pages/EmpAttendance/viewAttendance";
import JONGBOKHome from "../pages/JONGBOKHome.jsx";

import SchedulePage from "../pages/SchedulePage";


const router = createBrowserRouter([
  {
    path: "/",
    element: <App />, // 공통 레이아웃
    children: [
      { index: true, element: //<Navigate to="emp" replace /> 
      <JONGBOKHome/>},
      { path: "emp", element: <EmpList /> },
      { path: "emp/:empNum", element: <EmpDetail /> },
      { path: "emp/edit/:empNum", element: <EmpEdit /> },
      { path: "/EmpvacationList", element: <EmpVacationList /> },
      { path: "/EmpattendanceList", element: <EmpAttendanceList /> },
      { path: "/EmpAttendanceMy", element: <EmpAttendanceMy /> },
      { path: "/EmpAttendanceView", element: <EmpAttendanceView /> },
<<<<<<< HEAD
      {path:"/JONGBOKHome", element:<JONGBOKHome />},
=======
      { path: "schedule", element: <SchedulePage /> },
      
>>>>>>> af92dadf950abff5dee5f39779bd9ea0a4eb5c36
    ],
  },
]);

export default router;

