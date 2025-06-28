import React, { useState } from "react";
import { DateRange } from "react-date-range";
import { format } from "date-fns";
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import "bootstrap/dist/css/bootstrap.min.css";

const DateRangeSelector = ({ doctorId, onCreate, onClose }) => {
    const durations = [15, 30, 60];
    const [state, setState] = useState([
        {
            startDate: new Date(),
            endDate: new Date(),
            key: 'selection',
        },
    ]);

    const [startTime, setStartTime] = useState("09:00");
    const [endTime, setEndTime] = useState("13:00");
    const [slotDuration, setSlotDuration] = useState(15);
    const [showDropdown, setShowDropdown] = useState(false);

    const handleCreateClick = () => {
        const { startDate, endDate } = state[0];

        if (!startTime || !endTime || !slotDuration) {
            alert("Please fill in all fields.");
            return;
        }

        onCreate({
            doctorId,
            startDate,
            endDate,
            startTime,
            endTime,
            slotDuration,
        });
    };

    return (
        <div
            className="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center"
            style={{ backgroundColor: "rgba(0, 0, 0, 0.5)", zIndex: 1050 }}
        >
            <div
                className="bg-white rounded shadow-lg p-4"
                style={{
                    width: "850px",
                    maxWidth: "95vw",
                    maxHeight: "90vh",
                    overflow: "hidden"
                }}
            >
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h5 className="text-primary mb-0">Generate Doctor Slots</h5>
                    <button className="btn btn-sm btn-outline-danger" onClick={onClose}>
                        ×
                    </button>
                </div>

                <div className="row">
                    {/* Calendar on the left */}
                    <div className="col-md-6">
                        <div className="border rounded p-2">
                            <DateRange
                                editableDateInputs={true}
                                onChange={(item) => setState([item.selection])}
                                moveRangeOnFirstSelection={false}
                                ranges={state}
                                months={1}
                                direction="vertical"
                            />
                        </div>
                    </div>

                    {/* Time & duration inputs on the right */}
                    <div className="col-md-6">
                        <div className="mb-3">
                            <label className="form-label fw-semibold">Start Time</label>
                            <input
                                type="time"
                                className="form-control"
                                value={startTime}
                                onChange={(e) => setStartTime(e.target.value)}
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label fw-semibold">End Time</label>
                            <input
                                type="time"
                                className="form-control"
                                value={endTime}
                                onChange={(e) => setEndTime(e.target.value)}
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label fw-semibold">Slot Duration (minutes)</label>
                            <div className="position-relative">
                                <button
                                    className="btn btn-outline-primary w-100"
                                    onClick={() => setShowDropdown(prev => !prev)}
                                >
                                    {slotDuration || "Select Duration"} ▾
                                </button>
                                {showDropdown && (
                                    <ul className="list-group position-absolute w-100 mt-1 z-3">
                                        {durations.map((duration) => (
                                            <li
                                                key={duration}
                                                className="list-group-item list-group-item-action"
                                                style={{ cursor: "pointer" }}
                                                onClick={() => {
                                                    setSlotDuration(duration);
                                                    setShowDropdown(false);
                                                }}
                                            >
                                                {duration} Minutes
                                            </li>
                                        ))}
                                    </ul>
                                )}
                            </div>
                        </div>

                    </div>
                </div>

                {/* Fixed bottom buttons */}
                <div className="d-flex justify-content-end gap-2 mt-4">
                    <button className="btn btn-secondary" onClick={onClose}>Cancel</button>
                    <button className="btn btn-success" onClick={handleCreateClick}>Create Slots</button>
                </div>
            </div>
        </div>
    );
};

export default DateRangeSelector;
