import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useAuth();

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">
      <Link className="navbar-brand" to="/">HMS-UI</Link>

      <div className="collapse navbar-collapse">
        {user ? (
          <>
            <ul className="navbar-nav me-auto">
              <li className="nav-item">
                <Link className="nav-link" to="/dashboard">Dashboard</Link>
              </li>

              {user.roles.includes("ROLE_ADMIN") ? (
                <>
                  <li className="nav-item">
                    <Link className="nav-link" to="/admin/users">Manage Users</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/admin/reports">Reports</Link>
                  </li>
                </>
              ) : (
                <>
                  <li className="nav-item">
                    <Link className="nav-link" to="/profile">Profile</Link>
                  </li>
                </>
              )}
            </ul>

            <span className="navbar-text text-white me-3">
              Hello, {user.name}
            </span>
            <button className="btn btn-outline-light" onClick={logout}>
              Logout
            </button>
          </>
        ) : (
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">Login</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/signup">Signup</Link>
            </li>
          </ul>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
