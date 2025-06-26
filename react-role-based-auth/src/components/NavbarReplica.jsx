import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { FaCalendarAlt, FaUsers, FaMoneyBill, FaFileMedical, FaFlask, FaXRay, FaStore, FaPills, FaBed, FaSms, FaChartBar, FaCog, FaPlus, FaBell, FaCheckSquare, FaList, FaClock } from "react-icons/fa";
import { MdSchedule } from "react-icons/md";

const NavbarReplica = () => {
    return (
        <div className="bg-white border-bottom shadow-sm">
            <div className="d-flex px-3 py-2 align-items-center gap-3 text-primary" style={{ fontSize: "0.95rem", fontWeight: "500" }}>
                <FaCalendarAlt /> <span>Appointments</span>
                <FaUsers /> <span>Patients</span>
                <FaMoneyBill /> <span>Billing</span>
                <FaFileMedical /> <span>EMR</span>
                <FaFileMedical /> <span>E-Consult</span>
                <FaFlask /> <span>Lab</span>
                <FaXRay /> <span>Radiology</span>
                <FaStore /> <span>Store</span>
                <FaPills /> <span>Pharmacy</span>
                <FaBed /> <span>IP</span>
                <FaSms /> <span>SMS Center</span>
                <FaChartBar /> <span>Analysis</span>
                <FaCog /> <span>Settings</span>
            </div>

            <div className="d-flex px-3 py-2 align-items-center gap-4 text-secondary border-top" style={{ fontSize: "0.9rem", fontWeight: "500" }}>
                <FaCalendarAlt /> <span>Calendar</span>
                <FaList /> <span>List</span>
                <FaClock /> <span>Waiting Room</span>
                <MdSchedule /> <span>Weekly Schedule</span>
                <FaCheckSquare /> <span>Checkin</span>
                <FaBell /> <span>Notification</span>
                <FaPlus /> <span>Book</span>
            </div>
        </div>
    );
};

export default NavbarReplica;
