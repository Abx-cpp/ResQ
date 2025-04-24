import React, { useState } from 'react';
import './SearchBar.css';

const SearchBar = ({ onSearch, onFilter }) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [activeFilter, setActiveFilter] = useState('all');

  const handleSearch = (e) => {
    const query = e.target.value.toLowerCase();
    setSearchQuery(query);
    onSearch(query);
  };

  const handleFilter = (filter) => {
    setActiveFilter(filter);
    onFilter(filter);
  };

  return (
    <div className="search-container">
      <div className="container">
        <div style={{width:"100%"}}>
          <div style={{width:"100%"}}>
            <div className="search-box">
              <div className="input-group">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Search disasters or city codes..."
                  value={searchQuery}
                  onChange={handleSearch}
                />
                <button className="btn btn-search" type="button">
                  <i className="fas fa-search"></i>
                </button>
              </div>
              <div className="filter-options mt-2">
                {['all', 'flood', 'earthquake', 'drought', 'fire'].map((filter) => (
                  <button
                    key={filter}
                    className={`btn btn-filter ${activeFilter === filter ? 'active' : ''}`}
                    onClick={() => handleFilter(filter)}
                  >
                    {filter.charAt(0).toUpperCase() + filter.slice(1) + (filter !== 'all' ? 's' : '')}
                  </button>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchBar;
