import React, { useState } from 'react';
import './style.css';
import 'boxicons/css/boxicons.min.css';
import InputField from '../common/InputField'
import { Link } from 'react-router-dom';

const LogInPage: React.FC = () => {
  // State to manage input values
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setFormData((prev) => ({
      ...prev, 
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();    
  };

  return (
    <div className="container-login">
      <div className="login-wrapper">
        <form className="login-form" onSubmit={handleSubmit}>
          <h1>Login</h1>

          {/* Reusing InputField Component */}
          <InputField
            type="text"
            name="email"
            id="email"
            placeholder="Email"
            iconClass="bx bxs-user"
            onChange={handleInputChange}
            value={formData.email}
          />
          <InputField
            type="password"
            name="password"
            id="password"
            placeholder="Password"
            iconClass="bx bxs-lock-alt"
            onChange={handleInputChange}
            value={formData.password}
          />

          <div className="remember-forgot">
            <label>
              <input type="checkbox" name="rememberMe" /> Remember me
            </label>
            <label>
              <Link className="forgotLink login-link" to="/forgot-password">
                Forgot Password?
              </Link>
            </label>
          </div>

          <button type="submit" className="login-button">
            Login
          </button>

          <div className="register">
            <label>
              Don't have an account?{' '}
              <Link className="registerLink login-link" to="/register">
                Register
              </Link>
            </label>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LogInPage;
