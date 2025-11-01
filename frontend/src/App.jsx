// src/App.jsx
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import "./App.css";

<<<<<<< HEAD
import './App.css'

import {  useOutlet } from 'react-router';
import BsNavBar from './component/BsNavBar';
import BsSideBar from './component/BsSideBar';
=======
import { useOutlet } from "react-router-dom";
import BsNavBar from "./component/BsNavBar";
import BsSideBar from "./component/BsSideBar";
>>>>>>> af92dadf950abff5dee5f39779bd9ea0a4eb5c36

function App() {
  const currentOutlet = useOutlet();

  return (
    <>
      <BsNavBar />
      <BsSideBar />
      <div className="container" style={{ marginTop: "60px" }}>
        {currentOutlet}
      </div>
    </>
  );
}

export default App;
