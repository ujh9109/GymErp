// src/App.jsx

import 'bootstrap/dist/css/bootstrap.css'
import "bootstrap-icons/font/bootstrap-icons.css";

import './App.css'

import { NavLink, useOutlet } from 'react-router';
import BsNavBar from './component/BsNavBar';
import BsSideBar from './component/BsSideBar';
import Home from './pages/Home';

function App() {

  const currentOutlet = useOutlet();

  return <>
    <BsNavBar />
    <BsSideBar />
    <div className="container" style={{ marginTop: "60px" }}>
      {currentOutlet}
    </div>
    <hr>  </hr>
    <NavLink to="/Home">박종복 테스트용</NavLink>
  </>
}

export default App;
