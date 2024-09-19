import React from 'react';
import './navbar.css';
import logo from '../../assets/logo.png'
import { AdminPanelSettings } from '@mui/icons-material'


const Navbar: React.FC = () => {
  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <img src={logo} alt="Logo" />
      </div>
      <div className="navbar-user">
        <span>Admin User</span>
        <AdminPanelSettings className="admin-icon" />
      </div>
    </nav>
  );
};

export default Navbar;