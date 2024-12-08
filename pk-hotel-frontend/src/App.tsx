import React, { useState } from 'react';
import './App.css';
import Header from './common/Header';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import { RoomsPage } from './Rooms/RoomsPage';
import LogInPage from './LogIn/LogInPage';
import RegisterPage from './Register/RegisterPage';
import { User } from './Users/User';
import { UserPanel } from './Panels/UserPanel/UserPanel';
import { AdminPanel } from './Panels/AdminPanel/AdminPanel';

function App() {
  const [loggedUser, setLoggedUser] = useState<User | undefined>(undefined);

  return (
  <Router>
    <Header isUserLogged={loggedUser ? true : false} role={loggedUser?.role}/>
    <div className="container">
      <Routes>
        <Route path="/" element={<RoomsPage/>}></Route>
        <Route 
          path='/login' 
          element={
            <LogInPage 
            loggedUser={loggedUser}
            setLoggedUser={setLoggedUser}
          />}>
        </Route>
        {loggedUser && 
          <Route
          path='/user-panel'
          element={
            <UserPanel 
              loggedUser={loggedUser}
              setLoggedUser={setLoggedUser}
            />}>
          </Route>
        }
        <Route path='/admin-panel' element={<AdminPanel/>}></Route>
        <Route path="/forgot-password" ></Route>
        <Route path='/register' element={<RegisterPage/>}></Route>
      </Routes>
    </div>
  </Router>
  );
}

export default App;
