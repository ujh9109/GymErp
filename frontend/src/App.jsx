// src/App.jsx

import 'bootstrap/dist/css/bootstrap.css'
import "bootstrap-icons/font/bootstrap-icons.css";

import './App.css'
import { Outlet } from "react-router-dom";
import { useOutlet } from 'react-router';
import BsNavBar from './component/BsNavBar';
import BsSideBar from './component/BsSideBar';

function App() {

  const currentOutlet = useOutlet();

  return <>
    <BsNavBar/>
    <BsSideBar/>
    <div className="container" style={{marginTop:"60px"}}>
      {currentOutlet}
    </div>
    <div>
      {/* 공통 Header */}
      <header className="p-3 bg-dark text-white">
        <div className="container d-flex justify-content-between">
          <h2>🏢 직원관리</h2>
        </div>
      </header>

      {/* 페이지별 내용 */}
      <main className="p-4">
        <Outlet />
      </main>
    </div>
  </>
}

export default App;
