import React from 'react'
import { NavLink } from 'react-router-dom';
import './Header.css'

const Header = () => {
  return (
    <header className="sticky">
      <div className="container">
        <div className="row">
          <NavLink to="/" className="logo col-sm-12">
          Hote<mark>love</mark>
          </NavLink>
          <NavLink to="/log-in" className="button rounded">
            <span className="icon-user inverse"></span>
          </NavLink>
        </div>
      </div>
  </header>
  )
}

export default Header;