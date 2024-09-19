import React, { useState, useEffect } from 'react';
import './rankings.css';

interface Team {
  id?: number;
  name: string;
  registrationDate: string;
  groupNumber: number;
  totalMatchPoints: number;
  totalGoalsScored: number;
  alternateMatchPoints: number;
}

const Rankings: React.FC = () => {
  const [group1, setGroup1] = useState<Team[]>([]);
  const [group2, setGroup2] = useState<Team[]>([]);

  // Fetch rankings from backend for both groups
  useEffect(() => {
    const fetchGroupRankings = async (groupNumber: number) => {
      try {
        const response = await fetch(`http://localhost:8080/api/teams/rankings?groupNumber=${groupNumber}`);
        const data = await response.json();
        if (Array.isArray(data)) {
          return data;
        } else if (typeof data === 'object' && data !== null) {
          return Object.values(data);
        } else {
          console.error(`Unexpected data format for group ${groupNumber}:`, data);
          return [];
        }
      } catch (error) {
        console.error(`Error fetching rankings for group ${groupNumber}:`, error);
        return [];
      }
    };

    Promise.all([
      fetchGroupRankings(1),
      fetchGroupRankings(2)
    ]).then(([group1Teams, group2Teams]) => {
      setGroup1(group1Teams);
      setGroup2(group2Teams);
    });
  }, []);

  // Render the table for a specific group
  const renderTable = (groupTeams: Team[], groupNumber: number) => {
    return (
      <table className="rankings-table">
        <thead>
          <tr>
            <th>Rank</th>
            <th>Team Name</th>
            <th>Match Points</th>
            <th>Goals Scored</th>
            <th>Alternate Points</th>
          </tr>
        </thead>
        <tbody>
          {groupTeams.map((team, index) => (
            <tr key={team.id} className={index < 4 ? 'highlighted' : ''}>
              <td>{index + 1}</td>
              <td>{team.name}</td>
              <td>{team.totalMatchPoints}</td>
              <td>{team.totalGoalsScored}</td>
              <td>{team.alternateMatchPoints}</td>
            </tr>
          ))}
        </tbody>
      </table>
    );
  };

  return (
    <div className="rankings wrapper">
      <h1>Rankings</h1>
      <div className="group-rankings">
        {/* Group 1 */}
        <div className="group">
          <h2>Group 1</h2>
          {renderTable(group1, 1)}
        </div>
        {/* Group 2 */}
        <div className="group">
          <h2>Group 2</h2>
          {renderTable(group2, 2)}
        </div>
      </div>
    </div>
  );
};

export default Rankings;
