import React, { useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import { CheckCircle, X, PlusCircle, Pencil, Trash2, Search, UserPlus } from "lucide-react";
import "react-toastify/dist/ReactToastify.css";
import "../styles/SlotPopup.css";
import axios from "axios";

const SlotPopup = ({ slot, doctor, onClose, onDelete }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({
        patientId: "",
        patientNumber: "",
        visitType: "Consultation",
        slotCount: 1,
        date: slot?.date || "",
        time: slot?.startTime || "",
        details: ""
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const validate = () => {
        if (!formData.patientId.trim()) {
            toast.error("Patient id is required");
            return false;
        }
        if (!/^[0-9]{10}$/.test(formData.patientNumber)) {
            toast.error("Valid 10-digit mobile number required");
            return false;
        }
        if (!formData.date) {
            toast.error("Please select a valid date");
            return false;
        }
        return true;
    };

    const handleSubmit = async () => {
        if (!validate()) return;

        const appointmentPayload = {
            patientId: formData.patientId,
            doctorId: doctor?.doctorId,
            timeSlotId: slot?.id,
            isOnline: false
        };

        try {
            const res = await axios.post("http://localhost:8080/api/appointments/book", appointmentPayload);
            console.log("Appointment booked:", res.data);

            toast.success("Appointment booked successfully");
            onClose();
        } catch (error) {
            console.error("Booking error:", error);
            toast.error("Failed to book appointment");
        }
    };


    return (
        <div className="modal d-block" tabIndex="-1" role="dialog" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
            <div className="modal-dialog modal-xl" role="document">
                <div className="modal-content shadow-lg rounded-3">
                    <div className="modal-header bg-primary text-white">
                        <h5 className="modal-title fw-bold">🩺 Appointment Booking</h5>
                        <button type="button" className="btn-close btn-close-white" onClick={onClose}></button>
                    </div>

                    <div className="modal-body px-4 py-3">
                        <div className="bg-light p-3 rounded mb-3 border">
                            <div className="row g-2 align-items-center">
                                <label className="col-sm-2 col-form-label fw-semibold">Search By</label>
                                <div className="col-sm-3">
                                    <select className="form-select">
                                        <option>Name or Number</option>
                                    </select>
                                </div>
                                <div className="col-sm-4">
                                    <input type="text" className="form-control" />
                                </div>
                                <div className="col-sm-3 d-flex gap-2">
                                    <button className="btn btn-outline-primary"><Search size={16} /></button>
                                    <button className="btn btn-outline-danger"><X size={16} /></button>
                                    <button className="btn btn-info text-white"><UserPlus size={16} className="me-1" />Register</button>
                                </div>
                            </div>

                            <div className="row mt-3 g-2">
                                <label className="col-sm-2 col-form-label fw-semibold">New Patient</label>
                                <div className="col-sm-5">
                                    <input type="text" className="form-control" name="patientId" placeholder="patientId" value={formData.patientId} onChange={handleChange} />
                                </div>
                                <div className="col-sm-5">
                                    <input type="text" className="form-control" name="patientNumber" placeholder="Mobile Number" value={formData.patientNumber} onChange={handleChange} />
                                </div>
                            </div>
                        </div>

                        <div className="row g-2 align-items-center mb-3">
                            <label className="col-sm-2 col-form-label fw-semibold">Visit</label>
                            <div className="col-sm-3 d-flex gap-3">
                                <div className="form-check">
                                    <input className="form-check-input" type="radio" name="visitRadio" defaultChecked />
                                    <label className="form-check-label">First-Time</label>
                                </div>
                                <div className="form-check">
                                    <input className="form-check-input" type="radio" name="visitRadio" />
                                    <label className="form-check-label">Re-Visit</label>
                                </div>
                            </div>
                            <label className="col-sm-1 col-form-label">Source</label>
                            <div className="col-sm-3">
                                <select className="form-select">
                                    <option>--Select--</option>
                                </select>
                            </div>
                            <div className="col-sm-3">
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" id="international" />
                                    <label className="form-check-label" htmlFor="international">International Patient</label>
                                </div>
                            </div>
                        </div>

                        <div className="row g-2 mb-3">
                            <label className="col-sm-2 col-form-label fw-semibold">Doctor</label>
                            <div className="col-sm-3">
                                <input type="text" className="form-control" value={doctor?.doctorName} disabled />
                            </div>
                            <label className="col-sm-2 col-form-label">Visit Type</label>
                            <div className="col-sm-2">
                                <select className="form-select" name="visitType" value={formData.visitType} onChange={handleChange}>
                                    <option value="Consultation">Consultation</option>
                                    <option value="Follow-up">Follow-up</option>
                                </select>
                            </div>
                            <label className="col-sm-1 col-form-label">Slots</label>
                            <div className="col-sm-2">
                                <input type="number" name="slotCount" className="form-control" value={formData.slotCount} onChange={handleChange} min={1} />
                            </div>
                        </div>

                        <div className="row mb-3">
                            <label className="col-sm-2 col-form-label fw-semibold">Treatment</label>
                            <div className="col-sm-10">
                                <button className="btn btn-outline-primary">Get Treatments</button>
                            </div>
                        </div>

                        <div className="row g-2 mb-3 align-items-center">
                            <label className="col-sm-2 col-form-label fw-semibold">Time</label>
                            <div className="col-sm-3">
                                <input type="date" name="date" className="form-control" value={formData.date} onChange={handleChange} />
                            </div>
                            <div className="col-sm-2">
                                <input type="text" name="time" className="form-control" value={formData.time} disabled />
                            </div>
                            <div className="col-sm-5 d-flex flex-wrap gap-3">
                                <a href="#" className="text-decoration-none">Change Time</a>
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" id="markArrived" />
                                    <label className="form-check-label" htmlFor="markArrived">Mark Arrived</label>
                                </div>
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" id="walkin" />
                                    <label className="form-check-label" htmlFor="walkin">Is Walkin</label>
                                </div>
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" id="attention" />
                                    <label className="form-check-label" htmlFor="attention">Needs Attention</label>
                                </div>
                            </div>
                        </div>

                        <div className="row g-2 mb-3">
                            <label className="col-sm-2 col-form-label fw-semibold">Details</label>
                            <div className="col-sm-5">
                                <textarea name="details" className="form-control" rows="2" value={formData.details} onChange={handleChange}></textarea>
                            </div>
                            <div className="col-sm-5">
                                <input type="text" className="form-control" placeholder="Auto-filled summary" />
                            </div>
                        </div>

                        <div className="form-check mb-2 ms-2">
                            <input className="form-check-input" type="checkbox" id="repeat" />
                            <label className="form-check-label fw-semibold" htmlFor="repeat">Enable Repeats</label>
                        </div>
                    </div>

                    <div className="modal-footer bg-light border-top">
                        {slot?.status === "booked" && !isEditing ? (
                            <>
                                <button className="btn btn-warning" onClick={() => setIsEditing(true)}><Pencil size={16} className="me-1" />Edit</button>
                                <button className="btn btn-danger" onClick={() => onDelete(slot)}><Trash2 size={16} className="me-1" />Delete</button>
                            </>
                        ) : (
                            <button className="btn btn-success" onClick={handleSubmit}><PlusCircle size={16} className="me-1" />{isEditing ? "Update" : "Add Appointment"}</button>
                        )}
                        <button className="btn btn-secondary" onClick={onClose}><X size={16} className="me-1" />Close</button>
                    </div>
                </div>
            </div>
            <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} newestOnTop closeOnClick pauseOnHover theme="colored" />
        </div>
    );
};

export default SlotPopup;
