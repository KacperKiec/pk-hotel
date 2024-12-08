import React, { Dispatch, SetStateAction, useState } from 'react';
import './style.css';
import 'boxicons/css/boxicons.min.css';
import InputField from '../common/InputField'
import { Link, useNavigate } from 'react-router-dom';
import { Response, loginApi } from '../Api/Api';
import { User } from '../Users/User';

interface LoginPageProps {
  loggedUser: User | undefined,
  setLoggedUser: Dispatch<SetStateAction<User | undefined>>
}

const LogInPage = ({loggedUser, setLoggedUser}: LoginPageProps) => {
  // State to manage input values
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  const [error, setErrors] = useState('');

  const navigate = useNavigate();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setFormData((prev) => ({
      ...prev, 
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); 
    const response: Response = await loginApi({
      email: formData.email, 
      password: formData.password
    });   
    if(response.status === 202){
      setLoggedUser(response.user);
      navigate("/");
    }
    else if(response.status === 404){
      setErrors('Invalid login credentials.');
      setFormData({
        email: '',
        password: '',
      });
    }
    else{
      setErrors('Unable to connect to the server.\nPlease try later.');
    }
    console.log(response.status);
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
          {error !== '' && 
          <div className="login-error-message">
            {error}
          </div>
          }
        </form>
      </div>
    </div>
  );
};

export default LogInPage;
