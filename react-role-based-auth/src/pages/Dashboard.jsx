import React from "react";
import { useAuth } from "../context/AuthContext";

const Dashboard = () => {
  const { user } = useAuth();
  return (
    <div className="p-5">
      <h1>Welcome {user?.name}</h1>
      <p>Your role: <strong>{user?.roles}</strong></p>
    </div>
  );
};

export default Dashboard;