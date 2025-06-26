import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Signup = () => {
  const [form, setForm] = useState({ name: "", username: "", password: "", roles: [] });
  const { signup } = useAuth();
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  const handleRoleChange = (role) => {
    setForm((prev) => ({
      ...prev,
      roles: prev.roles.includes(role)
        ? prev.roles.filter((r) => r !== role)
        : [...prev.roles, role],
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMsg("");
    setLoading(true);

    try {
      const res = await signup(form);
      if (res.success) navigate("/");
      else setErrorMsg(res.message || "Signup failed");
    } catch (err) {
      setErrorMsg("Server error. Try again.");
    }

    setLoading(false);
  };

  return (
    <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card p-4 shadow w-100" style={{ maxWidth: "450px" }}>
        <h3 className="text-center mb-3">Create Account</h3>

        {errorMsg && <div className="alert alert-danger py-1">{errorMsg}</div>}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label fw-bold">Full Name</label>
            <input
              type="text"
              className="form-control"
              placeholder="John Doe"
              required
              onChange={(e) => setForm({ ...form, name: e.target.value })}
            />
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold">Email</label>
            <input
              type="email"
              className="form-control"
              placeholder="you@example.com"
              required
              onChange={(e) => setForm({ ...form, username: e.target.value })}
            />
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold">Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Strong password"
              required
              onChange={(e) => setForm({ ...form, password: e.target.value })}
            />
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold">Select Roles</label>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                id="roleAdmin"
                checked={form.roles.includes("ROLE_ADMIN")}
                onChange={() => handleRoleChange("ROLE_ADMIN")}
              />
              <label className="form-check-label" htmlFor="roleAdmin">Admin</label>
            </div>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                id="roleDoctor"
                checked={form.roles.includes("ROLE_DOCTOR")}
                onChange={() => handleRoleChange("ROLE_DOCTOR")}
              />
              <label className="form-check-label" htmlFor="roleDoctor">Doctor</label>
            </div>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                id="rolePatient"
                checked={form.roles.includes("ROLE_PATIENT")}
                onChange={() => handleRoleChange("ROLE_PATIENT")}
              />
              <label className="form-check-label" htmlFor="rolePatient">Patient</label>
            </div>
          </div>

          <button type="submit" className="btn btn-primary w-100" disabled={loading}>
            {loading ? "Signing up..." : "Sign Up"}
          </button>
        </form>

        <div className="text-center mt-3">
          <small>
            Already have an account? <a href="/" className="text-decoration-none">Login</a>
          </small>
        </div>
      </div>
    </div>
  );
};

export default Signup;
