import React, { useState } from "react";
import "./App.css";
import Header from "./Header/Header";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { MainPage } from "./MainPage/MainPage";
import LogInPage from "./LogIn/LogInPage";
import RegisterPage from "./Register/RegisterPage";
import { User } from "./Users/User";
import { UserPanel } from "./Panels/UserPanel/UserPanel";
import { AdminPanel } from "./Panels/AdminPanel/AdminPanel";
import RoomPage from "./Rooms/RoomPage";
import { PaymentsPage } from "./Payments/PaymentsPage";

function App() {
  const [loggedUser, setLoggedUser] = useState<User | undefined>(undefined);

  return (
    <Router>
      <Header
        isUserLogged={loggedUser ? true : false}
        role={loggedUser?.role}
      />
      <div className="container">
        <Routes>
          <Route
            path="/"
            element={<MainPage loggedUser={loggedUser} />}
          ></Route>
          <Route
            path="/login"
            element={
              <LogInPage
                loggedUser={loggedUser}
                setLoggedUser={setLoggedUser}
              />
            }
          ></Route>
          {loggedUser && (
            <Route
              path="/user-panel"
              element={
                <UserPanel
                  loggedUser={loggedUser}
                  setLoggedUser={setLoggedUser}
                />
              }
            ></Route>
          )}
          <Route
            path="/admin-panel"
            element={
              <AdminPanel
                loggedUser={loggedUser}
                setLoggedUser={setLoggedUser}
              />
            }
          ></Route>
          <Route path="/forgot-password"></Route>
          <Route path="/register" element={<RegisterPage />}></Route>
          <Route path="/room-page" element={<RoomPage />}></Route>
          <Route path="/payments" element={<PaymentsPage />}></Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
