import React, { useState, useEffect } from 'react';
import './teammanagement.css';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

interface Team {
  id?: number;
  name: string;
  registrationDate: string;
  groupNumber: number;
  totalMatchPoints: number;
  totalGoalsScored: number;
  alternateMatchPoints: number;
}

const TeamManagement: React.FC = () => {
  const [teams, setTeams] = useState<Team[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [showModal, setShowModal] = useState<boolean>(false);
  const [newTeamDetails, setNewTeamDetails] = useState<string>('');
  const [filteredTeams, setFilteredTeams] = useState<Team[]>(teams);

  // To retrieve all teams
  useEffect(() => {
    fetch('http://localhost:8080/api/teams')
      .then(response => response.json())
      .then(data => setTeams(data))
      .catch(error => console.error('Error fetching teams:', error));
  }, []);

  // To search for teams
  useEffect(() => {
    const filtered = teams.filter((team) =>
      team.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredTeams(filtered);
  }, [searchTerm, teams]);

  // To handle adding teams
  const handleAddTeams = () => {
    const tokens = newTeamDetails.trim().split(/\s+/);

    if (tokens.length % 3 !== 0) {
      alert('Please enter team details in the correct format.');
      return;
    }

    const teamsToAdd: Team[] = [];

    for (let i = 0; i < tokens.length; i += 3) {
      const name = tokens[i];
      const registrationDate = tokens[i + 1];
      const groupNumber = parseInt(tokens[i + 2], 10);

      if (isNaN(groupNumber)) {
        alert(`Invalid group number for team ${name}`);
        return;
      }

      teamsToAdd.push({
        name,
        registrationDate,
        groupNumber,
        totalMatchPoints: 0,
        totalGoalsScored: 0,
        alternateMatchPoints: 0,
      });

    }

    const requestBody = JSON.stringify(teamsToAdd);
    console.log('Request Body:', requestBody);

    fetch('http://localhost:8080/api/teams', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify(teamsToAdd),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then((savedTeams) => {
        if (Array.isArray(savedTeams)) {
          setTeams([...teams, ...savedTeams]);
        } else {
          console.error('Unexpected response format:', savedTeams);
        }
        setNewTeamDetails('');
        setShowModal(false);
      })
      .catch((error) => console.error('Error adding teams:', error));

  };

  // To handle editing the teams
  const [editingTeam, setEditingTeam] = useState<number | null>(null);
  const [editedTeam, setEditedTeam] = useState<Team | null>(null);
  const [validationErrors, setValidationErrors] = useState<{ [key: string]: string }>({});

  const handleEdit = (index: number) => {
    setEditingTeam(index);
    setEditedTeam({ ...filteredTeams[index] });
    setValidationErrors({});
  };

  // Validation during edits
  const validateTeam = (field: string, value: string | number) => {
    let error = '';

    switch (field) {
      case 'name':
        if (typeof value === 'string' && value.includes(' ')) {
          error = 'Team name should be only one word';
        }
        break;
      case 'registrationDate':
        const dateRegex = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])$/;
        if (typeof value === 'string' && !dateRegex.test(value)) {
          error = 'Date should be in DD/MM format';
        }
        break;
      case 'groupNumber':
        if (typeof value === 'number' && (isNaN(value) || value <= 0)) {
          error = 'Group number should be a positive number';
        }
        break;
    }

    setValidationErrors(prev => ({ ...prev, [field]: error }));
    return error === '';
  };

  const handleInputChange = (field: keyof Team, value: string | number) => {
    if (editedTeam) {
      const newTeam = { ...editedTeam, [field]: value };
      setEditedTeam(newTeam);
      validateTeam(field, value);
    }
  };

  // Edit
  const handleSave = (index: number) => {
    if (editedTeam && Object.values(validationErrors).every(error => error === '')) {
      fetch(`http://localhost:8080/api/teams/update/${editedTeam.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(editedTeam),
      })
        .then(response => {
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          return response.json();
        })
        .then((updatedTeam) => {
          // Update the teams state
          const updatedTeams = [...teams];
          const teamIndex = updatedTeams.findIndex(team => team.id === updatedTeam.id);
          if (teamIndex !== -1) {
            updatedTeams[teamIndex] = updatedTeam;
            setTeams(updatedTeams);
          }
          setEditingTeam(null);
          setEditedTeam(null);
        })
        .catch(error => console.error('Error updating team:', error));
    }
  };

  const handleCancel = () => {
    setEditingTeam(null);
    setEditedTeam(null);
    setValidationErrors({});
  };


  // Delete
  const handleDelete = (id: number) => {
    if (window.confirm("Are you sure you want to delete this team?")) {
      fetch(`http://localhost:8080/api/teams/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then(response => {
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          // Remove the deleted team from the state
          const updatedTeams = teams.filter(team => team.id !== id);
          setTeams(updatedTeams);
        })
        .catch(error => console.error('Error deleting team:', error));
    }
  };

  // Delete All Teams
  const handleDeleteAll = () => {
    if (window.confirm("Are you sure you want to delete all teams? This action cannot be undone.")) {
      fetch('http://localhost:8080/api/teams', {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then(response => {
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          // Clear all teams from the state
          setTeams([]);
          setFilteredTeams([]);
        })
        .catch(error => console.error('Error deleting all teams:', error));
    }
  };

  return (
    <div className="team-management wrapper">
      <h1>Team Management</h1>

      <div className="table-header">
        {/* Search Bar */}
        <div className="search-container">
          <input
            type="text"
            placeholder="Search team by name"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-bar"
          />
        </div>
        <div className="button-group">

        {/* Add Team Button */}
        <button onClick={() => setShowModal(true)} className="add-button">
          Add Team
        </button>

        {/* Delete All Teams Button */}
        <button onClick={handleDeleteAll} className="delete-all-button">
          Delete All
        </button>
        </div>

      </div>

      {/* Team Table */}
      <table className="team-table">
        <thead>
          <tr>
            <th>Team Name</th>
            <th>Registration Date</th>
            <th>Group Number</th>
            <th>Matches Played</th>
          </tr>
        </thead>
        <tbody>
          {filteredTeams.length > 0 ? (
            filteredTeams.map((team, index) => (
              <tr key={index}>
                <td>
                  {editingTeam === index ? (
                    <div>
                      <input
                        value={editedTeam?.name || ''}
                        onChange={(e) => handleInputChange('name', e.target.value)}
                      />
                      {validationErrors.name && <div className="error">{validationErrors.name}</div>}
                    </div>
                  ) : (
                    team.name
                  )}
                </td>
                <td>
                  {editingTeam === index ? (
                    <div>
                      <input
                        value={editedTeam?.registrationDate || ''}
                        onChange={(e) => handleInputChange('registrationDate', e.target.value)}
                      />
                      {validationErrors.registrationDate && <div className="error">{validationErrors.registrationDate}</div>}
                    </div>
                  ) : (
                    team.registrationDate
                  )}
                </td>
                <td>
                  {editingTeam === index ? (
                    <div>
                      <input
                        type="number"
                        value={editedTeam?.groupNumber || ''}
                        onChange={(e) => handleInputChange('groupNumber', parseInt(e.target.value, 10))}
                      />
                      {validationErrors.groupNumber && <div className="error">{validationErrors.groupNumber}</div>}
                    </div>
                  ) : (
                    team.groupNumber
                  )}
                </td>
                <td>
                  {editingTeam === index ? (
                    <>
                      <button onClick={() => handleSave(index)}>Save</button>
                      <button onClick={handleCancel}>Cancel</button>
                    </>
                  ) : (
                    <>
                      <button onClick={() => handleEdit(index)}>
                        <EditIcon />
                      </button>
                      <button onClick={() => handleDelete(team.id!)}>
                        <DeleteIcon />
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={6}>No teams found</td>
            </tr>
          )}
        </tbody>
      </table>

      {/* Modal for Adding Teams */}
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h2>Add New Teams</h2>
            <textarea
              placeholder="Enter team details in format: <Team Name> <Registration Date DD/MM> <Group Number> ..."
              value={newTeamDetails}
              onChange={(e) => setNewTeamDetails(e.target.value)}
              className="team-details-input"
            />
            <button onClick={handleAddTeams} className="submit-button">
              Submit
            </button>
            <button onClick={() => setShowModal(false)} className="close-button">
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default TeamManagement;
