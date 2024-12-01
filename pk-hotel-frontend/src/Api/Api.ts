import { User, transformUser } from '../Users/User'
const baseUrl = 'http://localhost:8080'

export interface RegisterResponse {
   succes: boolean,
   user?: User
}

// Register API
export const registerAPI = async (user: User): Promise<RegisterResponse> => {
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
      
      const registerResponse: RegisterResponse = await response.json();
      registerResponse.succes = true;
      console.log(registerResponse);
      return registerResponse;
   } catch(error){
      return {
         succes: false
      };
   }
}