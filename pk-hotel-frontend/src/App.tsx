import React from 'react';
import './App.css';
import Header from './common/Header';
import { SearchBar } from './common/SearchBar';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import RoomList from './Rooms/RoomList';
import { RoomsPage } from './Rooms/RoomsPage';
import LogInPage from './LogIn/LogInPage';
import RegisterPage from './Register/RegisterPage';

function App() {
  return (
  <Router>
    <Header/>
    <div className="container">
      <Routes>
        <Route path="/" element={<RoomsPage/>}></Route>
        <Route path='/log-in' element={<LogInPage/>}></Route>
        <Route path="/forgot-password" ></Route>
        <Route path='/register' element={<RegisterPage/>}></Route>
      </Routes>
    </div>
  </Router>
  );
}

export default App;
