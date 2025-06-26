import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import Navbar from "./components/Navbar";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Dashboard from "./pages/Dashboard";
import DoctorAvailabilityPage from "./pages/DoctorAvailabilityPage";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import NavbarReplica from "./components/NavbarReplica";

function App() {
  return (
    <>
      <AuthProvider>
        <Router>
          <Navbar />
          <NavbarReplica />
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/doctor-availability" element={<DoctorAvailabilityPage />}></Route>
          </Routes>
        </Router>
      </AuthProvider>
      <ToastContainer position="top-right" autoClose={2000} />
    </>
  );
}

export default App;
