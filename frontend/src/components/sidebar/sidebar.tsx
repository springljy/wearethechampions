import React from 'react';
import { Link } from 'react-router-dom';
import './sidebar.css';

import GroupsIcon from '@mui/icons-material/Groups';
import SportsSoccerIcon from '@mui/icons-material/SportsSoccer';
import LeaderboardIcon from '@mui/icons-material/Leaderboard';

const Sidebar: React.FC = () => {
  const navItems = [
    { text: 'Teams Management', path: '/teams', icon: <GroupsIcon /> },
    { text: 'Match Results', path: '/matches', icon: <SportsSoccerIcon /> },
    { text: 'Rankings', path: '/rankings', icon: <LeaderboardIcon /> },
  ];

  return (
    <nav className="sidebar">
      <ul className="sidebar-menu">
        {navItems.map((item) => (
          <li key={item.text} className="sidebar-item">
            <Link to={item.path} className="sidebar-link">
              <span className="sidebar-icon">{item.icon}</span>
              <span className="sidebar-text">{item.text}</span>
            </Link>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default Sidebar;
