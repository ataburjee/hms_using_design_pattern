// DoctorAvailabilityPage.jsx
import React, { useEffect, useState } from "react";
import DoctorSlotGrid from "../components/DoctorSlotGrid";
import axios from "axios";
import NavbarReplica from "../components/NavbarReplica";

const DoctorAvailabilityPage = () => {
    const [doctors, setDoctors] = useState([]);
    const [selectedDate, setSelectedDate] = useState(() => {
        const today = new Date();
        return today.toISOString().split("T")[0];
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await axios.get(
                    `http://localhost:8080/api/slots?date=${selectedDate}`
                );

                const transformed = res.data.map((doc) => {
                    const slots = {};

                    doc.timeSlots.forEach((slot, index) => {
                        const hourLabel = formatHourLabel(slot.startTime);
                        const status = slot.status?.toLowerCase() || "unavailable";
                        if (!slots[hourLabel]) slots[hourLabel] = [];
                        slots[hourLabel].push({
                            ...slot,
                            displayNumber: slots[hourLabel].length + 1,
                            status,
                        });
                    });

                    return {
                        ...doc,
                        status: "AVAILABLE",
                        slots,
                        availability: [
                            {
                                time: `${formatHour(doc.timeSlots[0]?.startTime)} - ${formatHour(
                                    doc.timeSlots[doc.timeSlots.length - 1]?.endTime
                                )}`,
                                type: doc.doctorType || "Consultation",
                            },
                        ],
                    };
                });

                setDoctors(transformed);
            } catch (err) {
                console.error("Error fetching slots:", err);
            }
        };

        fetchData();
    }, [selectedDate]);

    const formatHour = (timeStr) => {
        if (!timeStr) return "";
        const [h, m] = timeStr.split(":");
        const hour = parseInt(h);
        const suffix = hour >= 12 ? "PM" : "AM";
        const formattedHour = ((hour + 11) % 12) + 1;
        return `${formattedHour}:${m} ${suffix}`;
    };

    const formatHourLabel = (timeStr) => {
        const [h] = timeStr.split(":");
        const hour = parseInt(h);
        const suffix = hour >= 12 ? "PM" : "AM";
        const formattedHour = ((hour + 11) % 12) + 1;
        return `${formattedHour} ${suffix}`;
    };

    return (
        <>
            <NavbarReplica />
            <div className="container-fluid mt-4 d-flex justify-content-center">
                <div style={{ width: "95%", maxWidth: "1600px" }}>
                    {/* <h5 className="text-center mb-2 fw-bold">Select Date:</h5> */}
                    <div className="mb-3 text-center d-flex justify-content-center align-items-center gap-2 flex-wrap">
                        <button
                            className="btn btn-outline-primary btn-sm"
                            onClick={() =>
                                setSelectedDate(prev =>
                                    new Date(new Date(prev).setDate(new Date(prev).getDate() - 1))
                                        .toISOString()
                                        .split("T")[0]
                                )
                            }
                        >
                            ⬅️ Previous Day
                        </button>

                        <input
                            type="date"
                            value={selectedDate}
                            onChange={(e) => setSelectedDate(e.target.value)}
                            className="form-control w-auto d-inline-block"
                        />

                        <button
                            className="btn btn-outline-primary btn-sm"
                            onClick={() =>
                                setSelectedDate(prev =>
                                    new Date(new Date(prev).setDate(new Date(prev).getDate() + 1))
                                        .toISOString()
                                        .split("T")[0]
                                )
                            }
                        >
                            Next Day ➡️
                        </button>
                    </div>
                    <DoctorSlotGrid doctors={doctors} />
                </div>
            </div>
        </>
    );

};

export default DoctorAvailabilityPage;
