import React from "react";
import './Dashboard.css'
export default function Card({title, donation,icon}) {
    return (
        <div className="col-md-3 mb-3">
            <div className="summary-card">
              <div className="d-flex justify-content-between align-items-center">
                <div>
                  <h4>{title}</h4>
                  <div className="number">{donation}</div> 
                </div>
                <div className="card-icon">
                  <i className={icon} />
                </div>
              </div>
            </div>
        </div>
    )
}