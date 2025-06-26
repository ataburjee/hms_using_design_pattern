import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

// General Auth Route (just check login)
export const ProtectedRoute = () => {
    const { user } = useAuth();
    return user ? <Outlet /> : <Navigate to="/" />;
};

// Role-Based Route (check if user has one of required roles)
export const RoleRoute = ({ allowedRoles }) => {
    const { user } = useAuth();

    if (!user) return <Navigate to="/" />;
    const hasRole = user.roles?.some((role) => allowedRoles.includes(role));

    return hasRole ? <Outlet /> : <Navigate to="/unauthorized" />;
};
