// DoctorSlotGrid.jsx
import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../styles/DoctorSlotGrid.css";
import SlotPopup from "./SlotPopup";
import axios from "axios";


const hours = [
    "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM",
    "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM",
    "7 PM", "8 PM", "9 PM", "10 PM", "11 PM", "12 AM"
];

const DoctorSlotGrid = ({ doctors }) => {
    const [search, setSearch] = useState("");
    const [selectedSlot, setSelectedSlot] = useState(null);
    const [showSlotPopup, setShowSlotPopup] = useState(false);
    const [selectedDoctor, setSelectedDoctor] = useState(null);
    const [loadingDoctor, setLoadingDoctor] = useState(false);

    const getSlotStyle = (type) => {
        switch (type) {
            case "available": return { backgroundColor: "lightgreen" };
            case "booked": return { backgroundColor: "dodgerblue", color: "white" };
            case "walkin": return { backgroundColor: "orange", color: "white" };
            case "blocked": return { backgroundColor: "black" };
            case "no-show": return { backgroundColor: "red", color: "white" };
            case "reserved": return { backgroundColor: "skyblue" };
            case "additional": return { backgroundColor: "violet" };
            default: return { backgroundColor: "#ccc" }; // gray for unfilled
        }
    };

    const filteredDoctors = doctors.filter((doc) =>
        doc.doctorName.toLowerCase().includes(search.toLowerCase()) ||
        doc.department?.toLowerCase().includes(search.toLowerCase())
    );

    const setDoctorForSlot = async (slot) => {
        try {
            setLoadingDoctor(true);

            const doctorId = slot?.doctorId;
            if (!doctorId) return;

            const res = await axios.get(`http://localhost:8080/api/doctors/${doctorId}`);
            const data = res.data;
            setSelectedDoctor(data);
            setLoadingDoctor(false);

        } catch (err) {
            console.error("Error fetching doctor:", err);
            setLoadingDoctor(false);
        }
    };


    const handleSlotDelete = () => {

    }

    return (
        <div className="table-responsive px-2">
            <div className="mb-3 d-flex justify-content-end">
                <input
                    type="text"
                    className="form-control w-25"
                    placeholder="Search by Doctor or Dept..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                />
            </div>
            <table className="table table-bordered table-sm text-center w-100">
                <thead className="table-light">
                    <tr>
                        <th style={{ width: "12%" }}>Doctor</th>
                        <th style={{ width: "18%" }}>Availability</th>
                        {hours.map((h) => (
                            <th key={h} style={{ width: `${70 / hours.length}%`, fontSize: "0.75rem" }}>{h}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {filteredDoctors.map((doc, i) => (
                        <tr key={i}>
                            <td className="text-start">
                                <strong>{doc.doctorName}</strong><br />
                                <small>{doc.doctorType}</small><br />
                                <span className={`badge bg-${doc.status === 'OUT' ? 'danger' : 'success'}`}>{doc.status}</span>
                            </td>
                            <td className="text-start">
                                {(doc.availability || []).map((a, idx) => (
                                    <div key={idx}>
                                        <strong>{a.time}</strong><br />
                                        <small>{a.type}</small>
                                    </div>
                                ))}
                                <div className="mt-2 d-flex flex-wrap gap-1">
                                    <button className="btn btn-success btn-sm">New Availability</button>
                                    <button className="btn btn-danger btn-sm">New Busy Time</button>
                                    <button className="btn btn-primary btn-sm">Notify</button>
                                </div>
                            </td>
                            {hours.map((hour) => (
                                <td key={hour} style={{ minWidth: 40, verticalAlign: "top" }}>
                                    <div className="d-flex flex-column align-items-center gap-1">
                                        {(doc.slots?.[hour] || [null, null, null, null]).map((slot, j) => (
                                            <div
                                                key={j}
                                                className="slot-box"
                                                title={slot?.status || "Unavailable"}
                                                style={{ ...getSlotStyle(slot?.status), width: 25, height: 25, borderRadius: 4, cursor: slot ? 'pointer' : 'default', fontSize: "0.70rem", display: "flex", justifyContent: "center", alignItems: "center" }}
                                                onClick={() => {
                                                    if (slot) {
                                                        console.log("Clicked Slot = ", slot);
                                                        setSelectedSlot(slot);
                                                        setDoctorForSlot(slot);
                                                        setShowSlotPopup(true);
                                                    }
                                                }}
                                            >
                                                {j + 1}
                                            </div>
                                        ))}
                                    </div>
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>

            <div className="legend-bar bg-light border-top p-2 d-flex flex-wrap gap-3 justify-content-center fixed-bottom">
                <Legend color="#ccc" label="Unavailable" />
                <Legend color="lightgreen" label="Available" />
                <Legend color="dodgerblue" label="Booked" />
                <Legend color="violet" label="Additional" />
                <Legend color="orange" label="Walkin (W)" />
                <Legend color="black" label="Blocked" />
                <Legend color="green" label="Completed" />
                <Legend color="red" label="No Show (X)" />
                <Legend color="skyblue" label="Reserved" />
            </div>

            {loadingDoctor && (
                <div className="d-flex justify-content-center align-items-center" style={{ position: "fixed", top: 0, left: 0, width: "100%", height: "100%", backgroundColor: "rgba(0,0,0,0.5)", zIndex: 1050 }}>
                    <div className="spinner-border text-primary" role="status" style={{ width: "4rem", height: "4rem" }}>
                        <span className="visually-hidden">Loading...</span>
                    </div>
                </div>
            )}

            {showSlotPopup && selectedDoctor && (
                <SlotPopup
                    slot={selectedSlot}
                    doctor={selectedDoctor}
                    onClose={() => setShowSlotPopup(false)}
                    onDelete={handleSlotDelete}
                />
            )}

        </div>
    );
};

const Legend = ({ color, label }) => (
    <div className="d-flex align-items-center">
        <div style={{ width: 15, height: 15, backgroundColor: color, marginRight: 5, border: "1px solid #ccc" }}></div>
        <small>{label}</small>
    </div>
);

export default DoctorSlotGrid;





// // DoctorSlotGrid.jsx
// import React, { useState } from "react";
// import "bootstrap/dist/css/bootstrap.min.css";
// import "../styles/DoctorSlotGrid.css";

// const hours = [
//     "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM",
//     "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM",
//     "7 PM", "8 PM", "9 PM", "10 PM", "11 PM", "12 AM"
// ];

// const DoctorSlotGrid = ({ doctors }) => {
//     const [search, setSearch] = useState("");

//     const getSlotStyle = (type) => {
//         switch (type) {
//             case "available": return { backgroundColor: "lightgreen" };
//             case "booked": return { backgroundColor: "dodgerblue", color: "white" };
//             case "walkin": return { backgroundColor: "orange", color: "white" };
//             case "blocked": return { backgroundColor: "black" };
//             case "no-show": return { backgroundColor: "red", color: "white" };
//             case "reserved": return { backgroundColor: "skyblue" };
//             case "additional": return { backgroundColor: "violet" };
//             default: return { backgroundColor: "#ccc" }; // gray for unfilled
//         }
//     };

//     const handleSlotClick = (slot) => {
//         alert(`Slot ID: ${slot.id}\nTime: ${slot.startTime} - ${slot.endTime}\nStatus: ${slot.status}`);
//     };

//     const filteredDoctors = doctors.filter((doc) =>
//         doc.doctorName.toLowerCase().includes(search.toLowerCase()) ||
//         doc.department?.toLowerCase().includes(search.toLowerCase())
//     );

//     return (
//         <div className="table-responsive px-2">
//             <div className="mb-3 d-flex justify-content-end">
//                 <input
//                     type="text"
//                     className="form-control w-25"
//                     placeholder="Search by Doctor or Dept..."
//                     value={search}
//                     onChange={(e) => setSearch(e.target.value)}
//                 />
//             </div>
//             <table className="table table-bordered table-sm text-center w-100">
//                 <thead className="table-light">
//                     <tr>
//                         <th style={{ width: "12%" }}>Doctor</th>
//                         <th style={{ width: "18%" }}>Availability</th>
//                         {hours.map((h) => (
//                             <th key={h} style={{ width: `${70 / hours.length}%`, fontSize: "0.75rem" }}>{h}</th>
//                         ))}
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {filteredDoctors.map((doc, i) => (
//                         <tr key={i}>
//                             <td className="text-start">
//                                 <strong>{doc.doctorName}</strong><br />
//                                 <small>{doc.doctorType}</small><br />
//                                 <span className={`badge bg-${doc.status === 'OUT' ? 'danger' : 'success'}`}>{doc.status}</span>
//                             </td>
//                             <td className="text-start">
//                                 {(doc.availability || []).map((a, idx) => (
//                                     <div key={idx}>
//                                         <strong>{a.time}</strong><br />
//                                         <small>{a.type}</small>
//                                     </div>
//                                 ))}
//                                 <div className="mt-2 d-flex flex-wrap gap-1">
//                                     <button className="btn btn-success btn-sm">New Availability</button>
//                                     <button className="btn btn-danger btn-sm">New Busy Time</button>
//                                     <button className="btn btn-primary btn-sm">Notify</button>
//                                 </div>
//                             </td>
//                             {hours.map((hour) => (
//                                 <td key={hour} style={{ minWidth: 40, verticalAlign: "top" }}>
//                                     <div className="d-flex flex-column align-items-center gap-1">
//                                         {(doc.slots?.[hour] || [null, null, null, null]).map((slot, j) => (
//                                             <div
//                                                 key={j}
//                                                 className="slot-box"
//                                                 style={{ ...getSlotStyle(slot?.status), width: 25, height: 25, borderRadius: 4, cursor: 'pointer', fontSize: "0.70rem", display: "flex", justifyContent: "center", alignItems: "center" }}
//                                                 onClick={() => slot && handleSlotClick(slot)}
//                                             >
//                                                 {j + 1}
//                                             </div>
//                                         ))}
//                                     </div>
//                                 </td>
//                             ))}
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>

//             <div className="legend-bar bg-light border-top p-2 d-flex flex-wrap gap-3 justify-content-center fixed-bottom">
//                 <Legend color="#ccc" label="Unavailable" />
//                 <Legend color="lightgreen" label="Available" />
//                 <Legend color="dodgerblue" label="Booked" />
//                 <Legend color="violet" label="Additional" />
//                 <Legend color="orange" label="Walkin (W)" />
//                 <Legend color="black" label="Blocked" />
//                 <Legend color="green" label="Completed" />
//                 <Legend color="red" label="No Show (X)" />
//                 <Legend color="skyblue" label="Reserved" />
//             </div>
//         </div>
//     );
// };

// const Legend = ({ color, label }) => (
//     <div className="d-flex align-items-center">
//         <div style={{ width: 15, height: 15, backgroundColor: color, marginRight: 5, border: "1px solid #ccc" }}></div>
//         <small>{label}</small>
//     </div>
// );

// export default DoctorSlotGrid;
