import React from 'react'
import { NavLink } from 'react-router-dom';
import './Header.css'
import { Role } from '../Users/User';

interface HeaderProps{
  isUserLogged: boolean,
  role?: Role,
}

const Header = ({isUserLogged, role}: HeaderProps) => {
  const getURL = (): string => {
    if(!isUserLogged) return "/login";
    return role === "CLIENT" ? "/user-panel" : "/admin-panel"; 
  };
  return (
    <header className="sticky">
      <div className="container">
        <div className="row">
          <NavLink to="/" className="logo col-sm">
          Hote<mark>love</mark>
          </NavLink>
          <NavLink to={getURL()} className="button rounded">
            <span className="icon-user inverse"></span>
          </NavLink>
        </div>
      </div>
  </header>
  )
}

export default Header;