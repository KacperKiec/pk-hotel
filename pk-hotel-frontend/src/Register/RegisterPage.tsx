import React, { useState } from 'react';
import './style.css';
import 'boxicons/css/boxicons.min.css';
import InputField from '../common/InputField';
import { Link, useNavigate } from 'react-router-dom';
import { registerAPI, Response } from '../Api/Api';
import { User, Role } from '../Users/User';
import { validateUserDetails } from '../Users/ValidateUserDetails';


const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const role: Role = 'CLIENT';
  const [formData, setFormData] = useState({
    email: '',
    name: '',
    surname: '',
    password: '',
    confirmPassword: '',
    birthDate: '',
    role
  });
  const [errors, setErrors] = useState<string[]>([]);
  const [passwordsNotMatch, setPasswordsNotMatch] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if(formData.password !== formData.confirmPassword){
      setFormData(prev => ({
        email: '',
        name: '',
        surname: '',
        password: '',
        confirmPassword: '',
        birthDate: '',
        role
      }));
      setPasswordsNotMatch(true);
      return;
    }
    const user: User = new User({name: formData.name, surname: formData.surname, 
      email: formData.email, password: formData.password, birthDate: formData.birthDate,
      role: formData.role});

    const validate = validateUserDetails(user);
    if(!validate.isValid){
      setErrors(validate.errors);
      return;
    }
    
    const response: Response = await registerAPI(user);
    if(response.status === 201){
      navigate('/login');
    }
    else if(response.status === 409){
      setErrors((prev) => (['User with this email already exist, please login.']));
    }
    else{
      setErrors((prev) => (['Unable to connect to the server.']));
    }
  };

  return (
    <div className="container-register">
      <div className="wrapper">
        <form className="register-form" onSubmit={handleSubmit}>
          <h1>Register</h1>

          {/* Reusing InputField Component for Username */}
          <InputField
            type="text"
            name="name"
            id="name"
            placeholder="Name"
            iconClass="bx bxs-user"
            value={formData.name}
            onChange={handleInputChange}
          />

          <InputField
            type="text"
            name="surname"
            id="surname"
            placeholder="Surname"
            iconClass="bx bxs-user"
            value={formData.surname}
            onChange={handleInputChange}
          />

          {/* Reusing InputField Component for Email */}
          <InputField
            type="email"
            name="email"
            id="email"
            placeholder="Email"
            iconClass="bx bxs-envelope"
            onChange={handleInputChange}
            value={formData.email}
          />

          <InputField
            type="text"
            name="birthDate"
            id="birthDate"
            placeholder="Birth date"
            iconClass="bx bxs-user"
            value={formData.birthDate}
            onChange={handleInputChange}
          />

          {/* Reusing InputField Component for Password */}
          <InputField
            type="password"
            name="password"
            id="password"
            placeholder="Password"
            iconClass="bx bxs-lock-alt"
            onChange={handleInputChange}
            value={formData.password}
          />

          {/* Reusing InputField Component for Confirm Password */}
          <InputField
            type="password"
            name="confirmPassword"
            id="confirmPassword"
            placeholder="Confirm Password"
            iconClass="bx bxs-lock-alt"
            onChange={handleInputChange}
            value={formData.confirmPassword}
          />

          <button type="submit" className="register-button">
            Register
          </button>

          <div className="login">
            <label>
              Already have an account?{' '}
              <Link className="loginLink register-link" to="/login">
                Login
              </Link>
            </label>
          </div>
          {passwordsNotMatch && (
            <div className='passwords-not-match'>
              Passwords does not match.
            </div>
          )}
          { errors.map((error) => {
            if(error.length === 0) return -1;
            return(
              <div className='error'>{error}</div>
            )
          })}
          
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;
