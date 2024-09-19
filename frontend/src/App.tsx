import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/navbar/navbar';
import Sidebar from './components/sidebar/sidebar';
import TeamManagement from './pages/teammanagement/teammanagement';
import MatchResults from './pages/matchresults/matchresults';
import Rankings from './pages/rankings/rankings';
import AuditLogs from './pages/auditlogs/auditlogs';

const App: React.FC = () => {
  return (
    <div>

      <Router>
        <div>
        <Navbar />
        <Sidebar />
          <div style={{ marginLeft: '240px', paddingTop: '60px', padding: '20px' }}>
            <Routes>
              <Route path="/teams" element={<TeamManagement />} />
              <Route path="/matches" element={<MatchResults />} />
              <Route path="/rankings" element={<Rankings />} />
              <Route path="/auditlogs" element={<AuditLogs />} />
            </Routes>
          </div>
        </div>
      </Router>
    </div>
  );
};

export default App;
