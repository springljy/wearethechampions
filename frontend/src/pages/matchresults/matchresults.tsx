import React, { useState, useEffect } from 'react';
import './matchresults.css';

interface MatchDto {
    id?: number;
    teamAName: string;
    teamBName: string;
    teamAGoals: number;
    teamBGoals: number;
}

interface MatchResult {
    id?: number;
    teamA: Team;
    teamB: Team;
    teamAGoals: number;
    teamBGoals: number;
}

interface Team {
    id?: number;
    name: string;
    registrationDate: string;
    groupNumber: number;
    totalMatchPoints: number;
    totalGoalsScored: number;
    alternateMatchPoints: number;
    matchesPlayed: number;
}

const MatchResults: React.FC = () => {
    const [matchResults, setMatchResults] = useState<MatchResult[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [showModal, setShowModal] = useState<boolean>(false);
    const [newMatchDetails, setNewMatchDetails] = useState<string>('');

    // To retrieve all match results
    useEffect(() => {
        fetch('http://localhost:8080/api/matches')
            .then(response => response.json())
            .then(data => setMatchResults(data))
            .catch(error => console.error('Error fetching match results:', error));
    }, []);

    // To handle adding match results
    const handleAddMatchResults = () => {
        const tokens = newMatchDetails.trim().split(/\s+/);

        if (tokens.length % 4 !== 0) {
            alert(`Invalid format in line`);
            return;
        }

        const matchesToAdd: MatchDto[] = [];

        for (let i = 0; i < tokens.length; i += 4) {
            const teamAName = tokens[i];
            const teamBName = tokens[i + 1];
            const teamAGoals = parseInt(tokens[i + 2], 10);
            const teamBGoals = parseInt(tokens[i + 3], 10);

            matchesToAdd.push({ teamAName, teamBName, teamAGoals, teamBGoals });
        }

        const requestBody = JSON.stringify(matchesToAdd);
        console.log('Request Body:', requestBody);

        fetch('http://localhost:8080/api/matches', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(matchesToAdd),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then((savedMatches) => {
                setMatchResults([...matchResults, ...savedMatches]);
                setNewMatchDetails('');
                setShowModal(false);
            })
            .catch((error) => console.error('Error adding match results:', error));
    };

    // Delete match result
    const handleDelete = (id: number) => {
        if (window.confirm("Are you sure you want to delete this match result?")) {
            fetch(`http://localhost:8080/api/matches/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    const updatedResults = matchResults.filter(match => match.id !== id);
                    setMatchResults(updatedResults);
                })
                .catch(error => console.error('Error deleting match result:', error));
        }
    };

    // Delete All Matches
    const handleDeleteAll = () => {
        if (window.confirm("Are you sure you want to delete all matches? This action cannot be undone.")) {
            fetch('http://localhost:8080/api/matches', {
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
                    setMatchResults([]);
                })
                .catch(error => console.error('Error deleting all matches:', error));
        }
    };

    return (
        <div className="match-results wrapper">
            <h1>Match Results</h1>

            <div className="button-group">
                {/* Add Match Button */}
                <button onClick={() => setShowModal(true)} className="add-button">
                    Add Match
                </button>

                {/* Delete All Button */}
                <button onClick={handleDeleteAll} className="delete-all-button">
                    Delete All
                </button>
            </div>

            <table className="match-table">
                <thead>
                    <tr>
                        <th>Team A</th>
                        <th>Team B</th>
                        <th>Score</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {matchResults.length > 0 ? (
                        matchResults.map((match, index) => (
                            <tr key={index}>
                                <td>{match.teamA.name}</td>
                                <td>{match.teamB.name}</td>
                                <td>{`${match.teamAGoals} - ${match.teamBGoals}`}</td>
                                <td>
                                    <button onClick={() => handleDelete(match.id!)}>Delete</button>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={4}>No match results found</td>
                        </tr>
                    )}
                </tbody>
            </table>

            {showModal && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Add New Match Results</h2>
                        <textarea
                            placeholder="Enter match results in format: <Team A> <Team B> <Team A Score> <Team B Score>"
                            value={newMatchDetails}
                            onChange={(e) => setNewMatchDetails(e.target.value)}
                            className="match-details-input"
                        />
                        <button onClick={handleAddMatchResults} className="submit-button">
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

export default MatchResults;
