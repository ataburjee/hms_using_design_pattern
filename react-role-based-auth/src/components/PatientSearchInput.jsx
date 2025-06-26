// components/PatientSearchInput.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";

const PatientSearchInput = ({ onSelectPatient }) => {
    const [query, setQuery] = useState("");
    const [suggestions, setSuggestions] = useState([]);
    const [showSuggestions, setShowSuggestions] = useState(false);

    useEffect(() => {
        if (query.length < 2) {
            setSuggestions([]);
            return;
        }

        const timeoutId = setTimeout(async () => {
            try {
                const res = await axios.get(`http://localhost:8080/api/patients/search?q=${query}`);
                setSuggestions(res.data || []);
                console.log("query = ", query);
                console.log("suggestions = ", suggestions);
                console.log("showSuggestions = ", showSuggestions);
            } catch (err) {
                console.error("Patient search failed:", err);
            }
        }, 300);

        return () => clearTimeout(timeoutId);
    }, [query]);

    const handleSelect = (patient) => {
        setQuery(`${patient.fullName} (${patient.contactNumber})`);
        setSuggestions([]);
        setShowSuggestions(false);
        onSelectPatient(patient);
    };

    return (
        <div className="position-relative">
            <input
                type="text"
                className="form-control"
                placeholder="Search patient"
                value={query}
                onChange={(e) => {
                    setQuery(e.target.value);
                    setShowSuggestions(true);
                }}
                onBlur={() => setTimeout(() => setShowSuggestions(false), 200)}
                onFocus={() => query.length >= 2 && setShowSuggestions(true)}
            />

            {showSuggestions && suggestions.length > 0 && (
                <ul
                    className="list-group position-absolute w-100 bg-white shadow"
                    style={{
                        top: "100%",
                        left: 0,
                        maxHeight: "250px",
                        overflowY: "auto",
                        zIndex: 1056,
                        border: "1px solid #ddd",
                        borderTop: "none"
                    }}
                >
                    {suggestions.map((p) => (
                        <li
                            key={p.id}
                            className="list-group-item list-group-item-action"
                            style={{ cursor: "pointer" }}
                            onClick={() => handleSelect(p)}
                        >
                            {p.fullName} - {p.contactNumber}
                        </li>
                    ))}
                </ul>
            )}

        </div>
    );
};

export default PatientSearchInput;
