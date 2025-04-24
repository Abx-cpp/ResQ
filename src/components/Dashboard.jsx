import React, { useEffect } from "react";
import "mdb-ui-kit/css/mdb.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import FloodImage from "../assets/Floods.png";
import QuakeImage from "../assets/Earthquake.png";
import DroughtImage from "../assets/Droughts.png";
import SearchBar from "./SearchBar";
import Card from "./Card";

// Register chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const ResQDashboard = () => {
  useEffect(() => {
    const sidebar = document.querySelector(".sidebar");
    const toggleBtn = document.querySelector(".toggle-sidebar");

    const checkWindowSize = () => {
      if (window.innerWidth <= 991.98) {
        toggleBtn.style.display = "flex";
        sidebar.classList.remove("active");
      } else {
        toggleBtn.style.display = "none";
        sidebar.classList.add("active");
      }
    };

    const toggleSidebar = () => {
      sidebar.classList.toggle("active");
    };

    toggleBtn.addEventListener("click", toggleSidebar);
    window.addEventListener("load", checkWindowSize);
    window.addEventListener("resize", checkWindowSize);

    return () => {
      toggleBtn.removeEventListener("click", toggleSidebar);
      window.removeEventListener("load", checkWindowSize);
      window.removeEventListener("resize", checkWindowSize);
    };
  }, []);

  const chartData1 = {
    labels: ["Nov", "Dec", "Jan", "Feb", "Mar", "Apr"],
    datasets: [
      {
        label: "Donations Over Last 6 Months",
        data: [5000, 10000, 15000, 12000, 18000, 22000],
        backgroundColor: "rgba(75,192,192,0.2)",
        borderColor: "black",
        borderWidth: 1,
      },
    ],
  };

  return (
    <>
      <header>
        <nav className="navbar navbar-expand-lg navbar-dark">
          <div className="container-fluid">
            <a className="navbar-brand nav-link" href="#resQ">
              <strong>ResQ</strong>
            </a>
            <button className="btn btn-signin">Sign In</button>
          </div>
        </nav>

        <SearchBar />
      </header>

      <div className="sidebar">
        <p>
          Welcome to the ResQ portal. Manage your donations and contribute to
          disaster relief efforts. Explore active relief operations via the
          dashboard.
        </p>
      </div>

      <div className="main-content">
        <h2 className="dashboard-title" style={{ fontWeight: "1000" }}>
          Dashboard
        </h2>

        <div className="row mb-4">
          <Card title = "Total Donations" donation="24,500" icon="fas fa-rupee-sign" />
          <Card title = "Active Disasters" donation="7" icon="fas fa-exclamation-triangle" />
          <Card title = "Volunteers" donation="358" icon="fas fa-users" />
          <Card title = "People Helped" donation="12,763" icon="fas fa-hands-helping" />
        </div>

        <h4 style={{ fontWeight: "1000", marginBottom: "30px" }}>
          Active Disasters
        </h4>
        <div className="row">
          {/* Disaster 1 */}
          <div className="col-lg-4 col-md-6 mb-4">
            <div className="disaster-card">
              <div className="position-relative">
                <img
                  src={FloodImage}
                  className="card-img-top"
                  alt="Flood in Pakistan"
                />
              </div>
              <div className="card-body">
                <h5 className="card-title">Flood in Pakistan</h5>
                <p className="card-text">
                  Severe flooding has affected over 3 million people. Food,
                  clean water, and medicine are urgently needed.
                </p>
              </div>
            </div>
          </div>

          {/* Disaster 2 */}
          <div className="col-lg-4 col-md-6 mb-4">
            <div className="disaster-card">
              <div className="position-relative">
                <img
                  src={QuakeImage}
                  className="card-img-top"
                  alt="Earthquake in Turkey"
                />
              </div>
              <div className="card-body">
                <h5 className="card-title">Earthquake in Turkey</h5>
                <p className="card-text">
                  A 7.2 magnitude earthquake devastated southeastern Turkey.
                  Thousands need shelter and emergency supplies.
                </p>
              </div>
            </div>
          </div>

          {/* Disaster 3 */}
          <div className="col-lg-4 col-md-6 mb-4">
            <div className="disaster-card">
              <div className="position-relative">
                <img
                  src={DroughtImage}
                  className="card-img-top"
                  alt="Drought in East Africa"
                />
              </div>
              <div className="card-body">
                <h5 className="card-title">Drought in East Africa</h5>
                <p className="card-text">
                  Severe drought has led to food insecurity affecting over 5
                  million people. Urgent aid is needed. Every moment counts in
                  providing relief to those affected.
                </p>
              </div>
            </div>
          </div>
        </div>

        <div className="card1">
          <h4 style={{ fontWeight: "1000", marginBottom: "10px" }}>
            Top Donors This Month
          </h4>
          <div className="row">
            <h6>
              A big thank you to our top donors for their incredible generosity
              and support! Your donations are making a huge difference in the
              lives of those in need. 👏
            </h6>
            <div
              className="col-lg-6"
              style={{
                width: "61%",
                marginBottom: "70px",
                marginLeft: "210px",
              }}
            >
              <div className="disaster-card">
                <div className="card-body">
                  <Bar
                    data={{
                      labels: [
                        "Ahmed Khan",
                        "Sarah Johnson",
                        "Muhammad Ali",
                        "Fatima Zahra",
                      ],
                      datasets: [
                        {
                          label: "Donation Amount (Rs)",
                          data: [5000, 3500, 2800, 2250],
                          backgroundColor: "rgba(75,192,192,0.5)",
                          borderColor: "black",
                          borderWidth: 1,
                        },
                      ],
                    }}
                    options={{
                      indexAxis: "y",
                      scales: {
                        x: {
                          beginAtZero: true,
                        },
                      },
                    }}
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="card2" style={{ marginBottom: "200px" }}>
            <h5 style={{ marginBottom: "10px", fontWeight: "1000" }}>
              Donation Comparison (Last 6 Months)
            </h5>
            <h6>
              Great progress over the last few months! We’re seeing a steady
              increase in donations, which means more help for those who need it
              most. 📊
            </h6>
            <div
              className="col-lg-6"
              style={{ width: "60%", marginTop: "0", marginLeft: "210px" }}
            >
              <div className="disaster-card">
                <div className="row">
                  <div className="col-lg-12">
                    <div className="card-body">
                      <Bar data={chartData1} />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="toggle-sidebar">
        <i className="fas fa-bars" />
      </div>
    </>
  );
};

export default ResQDashboard;
