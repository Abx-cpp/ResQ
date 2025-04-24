import React, { useState } from "react";
import './VolunteerDashB.css'; // Make sure to import the CSS file

const VolunteerDashboard = () => {
  const [sidebarActive, setSidebarActive] = useState(false);

  const handleSidebarToggle = () => {
    setSidebarActive(!sidebarActive);
  };

  const joinDisaster = (disasterId) => {
    const disasterName = document.querySelector(`#disaster-${disasterId}`).innerText;
    alert(`Joining disaster: ${disasterName}`);
  };

  return (
    <div>
      <header>
        <nav className="navbar navbar-expand-lg navbar-dark" style={{ zIndex: "2000" }}>
          <div className="container-fluid">
            <a className="navbar-brand nav-link" href="volunteer-dashboard.html">
              <strong>ResQ Volunteer</strong>
            </a>
            <button
              className="navbar-toggler"
              type="button"
              onClick={handleSidebarToggle}
              aria-controls="navbarExample01"
              aria-expanded={sidebarActive ? "true" : "false"}
              aria-label="Toggle navigation"
            >
              <i className="fas fa-bars"></i>
            </button>
            <div className={`collapse navbar-collapse ${sidebarActive ? 'show' : ''}`} id="navbarExample01">
              <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item active">
                  <a className="nav-link" href="volunteer-dashboard.html">
                    <i className="fas fa-chart-line me-2"></i>Dashboard
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="tasks.html">
                    <i className="fas fa-tasks me-2"></i>My Tasks
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="role-selection.html">
                    <i className="fas fa-user-tag me-2"></i>Role Selection
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="nearby-victims.html">
                    <i className="fas fa-map-marked-alt me-2"></i>Nearby Victims
                  </a>
                </li>
              </ul>
              <div className="admin-info">
                <span>Welcome, Volunteer</span>
                <button onClick={() => window.close()} className="btn btn-logout ms-2">
                  <i className="fas fa-sign-out-alt me-1"></i>Logout
                </button>
              </div>
            </div>
          </div>
        </nav>
        <div className="search-container">
          <div className="container">
            <div className="row justify-content-center">
              <div className="col-md-8">
                <div className="search-box">
                  <div className="input-group">
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Search disasters or city codes..."
                      id="searchInput"
                    />
                    <button className="btn btn-search" type="button" id="searchButton">
                      <i className="fas fa-search"></i>
                    </button>
                  </div>
                  <div className="filter-options mt-2">
                    <button className="btn btn-filter active" data-filter="all">All</button>
                    <button className="btn btn-filter" data-filter="flood">Floods</button>
                    <button className="btn btn-filter" data-filter="earthquake">Earthquakes</button>
                    <button className="btn btn-filter" data-filter="drought">Droughts</button>
                    <button className="btn btn-filter" data-filter="fire">Fires</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </header>
      
      <div className={`sidebar ${sidebarActive ? 'active' : ''}`}>
        <div className="profile-info">
          <img className="profile-pic" src="/api/placeholder/150/150" alt="Profile" />
          <div className="user-name">Sarah Ahmed</div>
          <div className="user-role">Volunteer</div>
          <div className="user-phone">0312 5557890</div>
          <div className="user-role-type mt-2">
            <span className="badge bg-success">On-site Volunteer</span>
          </div>
        </div>
        <div className="mt-4">
          <h6 className="text-light mb-3">Quick Actions</h6>
          <ul className="list-unstyled">
            <li className="mb-2"><a href="volunteer-dashboard.html" className="text-decoration-none text-light"><i className="fas fa-home me-2"></i> Dashboard</a></li>
            <li className="mb-2"><a href="tasks.html" className="text-decoration-none text-light"><i className="fas fa-tasks me-2"></i> My Tasks</a></li>
            <li className="mb-2"><a href="role-selection.html" className="text-decoration-none text-light"><i className="fas fa-user-tag me-2"></i> Change Role</a></li>
            <li className="mb-2"><a href="nearby-victims.html" className="text-decoration-none text-light"><i className="fas fa-map-marked-alt me-2"></i> Nearby Victims</a></li>
            <li className="mb-2"><a href="settings.html" className="text-decoration-none text-light"><i className="fas fa-cog me-2"></i> Settings</a></li>
            <li className="mb-2"><a href="#" className="text-decoration-none text-light"><i className="fas fa-sign-out-alt me-2"></i> Logout</a></li>
          </ul>
        </div>
      </div>

      <div className="main-content">
        <h2 className="dashboard-title">Volunteer Dashboard</h2>
        <div className="volunteer-banner">
          <div className="volunteer-info">
            <h3>Active Participation</h3>
            <div className="amount">3 Active Disasters</div>
          </div>
          <div className="volunteer-status">
            <div className="status-icon">
              <i className="fas fa-check-circle"></i>
            </div>
            <div className="status-text">Your Status: Available</div>
          </div>
        </div>
        
        <div className="row mb-4">
          <div className="col-md-4 mb-3">
            <div className="summary-card">
              <div className="d-flex justify-content-between align-items-center">
                <div>
                  <h4>Your Tasks</h4>
                  <div className="number">4</div>
                </div>
                <div className="card-icon">
                  <i className="fas fa-tasks"></i>
                </div>
              </div>
            </div>
          </div>
        </div>

        <section id="disaster-management" className="mb-5">
          <div className="section-header">
            <h3 className="section-title">Available Disasters</h3>
            <button className="btn btn-create" id="refreshDisasters">
              <i className="fas fa-sync-alt me-2"></i>Refresh List
            </button>
          </div>
          <div className="disaster-list">
            <div className="table-responsive">
              <table className="table table-admin">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Disaster Title</th>
                    <th>Location</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Volunteers Needed</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>001</td>
                    <td id="disaster-001">Flood in Pakistan</td>
                    <td>Southern Pakistan</td>
                    <td>05/03/2025</td>
                    <td><span className="badge bg-danger">Urgent</span></td>
                    <td>45 / 100</td>
                    <td>
                      <button className="btn btn-sm btn-primary" onClick={() => joinDisaster('001')}>
                        <i className="fas fa-info-circle me-1"></i> Details
                      </button>
                      <button className="btn btn-sm btn-success" onClick={() => joinDisaster('001')}>
                        <i className="fas fa-plus me-1"></i> Join
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </section>
      </div>

      <div className="toggle-sidebar">
        <i className="fas fa-bars"></i>
      </div>
    </div>
  );
};

export default VolunteerDashboard;
