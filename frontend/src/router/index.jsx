/*
    src/router/index.jsx 파일

    -파일명을 index.js or index.jsx 로 작성하면 외부에서 router 폴더 까지만
    import 했을 때 자동으로 index.js or index.jsx 파일에서 export 된
    자원을 사용할 수 있다
    -index 는 약속된 파일명이다.
*/

import { createHashRouter } from "react-router-dom";
import App from "../App";
import Home from "../pages/Home";
import EmpVacationList from "../pages/EmpVacation/list";
import EmpAttendanceList from "../pages/EmpAttendance/list";
import EmpAttendanceMy from "../pages/EmpAttendance/myAttendance";
import EmpAttendanceView from "../pages/EmpAttendance/viewAttendance";


const routes = [
    { path: "/index.html", element: <Home /> },
    { path: "/", element: <Home /> },
    { path: "/EmpvacationList", element: <EmpVacationList/> },
    {path:"/EmpattendanceList",element:<EmpAttendanceList/>},
    {path:"/EmpAttendanceMy",element:<EmpAttendanceMy/>},
    {path:"/EmpAttendanceView",element:<EmpAttendanceView/>}
];

const router = createHashRouter([{
    path: "/",
    element: <App />,
    children: routes.map((route) => {
        return {
            index: route.path === "/", //자식의 path 가 "/" 면 index 페이지 역할을 하게 하기 
            path: route.path === "/" ? undefined : route.path, // path 에 "/" 두개가 표시되지 않게  
            element: route.element //어떤 컴포넌트를 활성화 할것인지 
        }
    })
}]);


export default router;