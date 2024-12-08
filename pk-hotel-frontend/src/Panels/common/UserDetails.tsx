import React, { Dispatch, SetStateAction, useState } from "react";
import "../UserPanel/UserPanel.css";
import { User } from "../../Users/User";
import { useNavigate } from "react-router-dom";
import { updateUserApi } from "../../Api/Api";

interface UserDetailsProps {
  loggedUser: User;
  setLoggedUser: Dispatch<SetStateAction<User | undefined>>;
}

const UserDetails: React.FC<UserDetailsProps> = ({ loggedUser, setLoggedUser }: UserDetailsProps) => {
  const navigator = useNavigate();
  const [isEditing, setIsEditing] = useState({
    name: false,
    surname: false,
    email: false,
    birthDate: false,
  });

  const [userData, setUserData] = useState({
    name: loggedUser.name,
    surname: loggedUser.surname,
    //email: loggedUser.email,
    birthDate: loggedUser.birthDate,
  });

  const [updateError, setUpdateError] = useState(false);

  const handleEdit = (field: keyof typeof isEditing) => {
    setIsEditing({ ...isEditing, [field]: true });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>, field: keyof typeof userData) => {
    setUserData({ ...userData, [field]: e.target.value });
  };

  const handleSave = async (field: keyof typeof isEditing) => {
    setIsEditing({ ...isEditing, [field]: false });
    //console.log(`SAVE ${field}`);
    // Tylko te które da sie zmienić reszta idzie z zalogowanego usera
    const updatedUser = new User({
      name: userData.name,
      surname: userData.surname,
     // email: userData.email,
      email: loggedUser.email,
      birthDate: userData.birthDate,
      password: loggedUser.password,
      id: loggedUser.id,
      role: loggedUser.role
    });
    
    try{
      await updateUserApi(updatedUser);
      //zmien aktualnie zalogowanego uzytkownika
      setLoggedUser(updatedUser);
    }catch(error: any){
      setUpdateError(true);
      //zmien wartosc inputow spowrotem na zalogowanego uzytkownika
      setUserData({
        name: loggedUser.name,
        surname: loggedUser.surname,
        birthDate: loggedUser.birthDate
      });
    }
  };

  const handleLogout = (e: any) => {
    setLoggedUser(undefined);
    navigator('/');
  }

  return (
    <div className="user-details">
      <div className="h1-logout">
        <h1 className="user-details-h1">User details</h1>
        <button 
          className="logout"
          onClick={handleLogout}  
        > 
        Logout
        </button>
      </div>
      {Object.keys(userData).map((key) => {
        const field = key as keyof typeof userData;
        const label = field.charAt(0).toUpperCase() + field.slice(1); // Capitalize field name for the label
        return (
          <div key={field} className="user-details__field">
            <label className="user-details__label">{label}</label>
            <div>
              <input
                name={field}
                type="text"
                value={userData[field]}
                disabled={!isEditing[field]}
                onChange={(e) => handleChange(e, field)}
                className="user-details__input"
              />
              {isEditing[field] ? (
                <button className="user-details__button" onClick={() => handleSave(field)}>
                  Save
                </button>
              ) : (
                <button className="user-details__button" onClick={() => handleEdit(field)}>
                  Edit
                </button>
              )}
            </div>
           
          </div>
        );
      })}
      { updateError && 
        <div className="update-user-error">
          There was a problem with updating your profile. Please try again later.
        </div>
      }
    </div>
  );
};

export default UserDetails;
