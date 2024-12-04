import { User, UserDTO, transformUser, transformUserDTOToUser } from '../Users/User'
const baseUrl = 'http://localhost:8080'

export interface Response {
   succes: boolean,
   user?: User 
}

export interface LoginData {
   email: string,
   password: string
}

// Register API
export const registerAPI = async (user: User): Promise<Response> => {
   try{
      const response = await fetch(`${baseUrl}/register`, {
         method: "POST",
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(transformUser(user)),
      });
      //console.log(JSON.stringify(transformUser(user)));
      
      if(!response.ok){
         throw new Error(`Registration failed ${response.statusText}`);
      }
      
      const registerResponse: Response = await response.json();
      registerResponse.succes = true;
      console.log(registerResponse);
      return registerResponse;
   } catch(error){
      return {
         succes: false
      };
   }
}


export const loginApi = async (data: LoginData): Promise<Response> => {
   const params = new URLSearchParams();
   params.append('email', data.email);
   params.append('password', data.password);

   try{
      const response = await fetch(`${baseUrl}/login`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
         },
         body: params.toString(),
         credentials: 'include',
      });

      if(!response.ok){
         throw new Error(`HTTP error! status: ${response.status}`);
      }
      const loggedUser: User = transformUserDTOToUser(await response.json());
      let loginResponse: Response = {
         succes: true,
         user: loggedUser
      };
      return loginResponse;
   }
   catch(error){
      return{
         succes: false
      }
   }
}