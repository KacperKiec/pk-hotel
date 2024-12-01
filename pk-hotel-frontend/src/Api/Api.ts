import { User, transformUser } from '../Users/User'
const baseUrl = 'http://localhost:8080'

interface RegisterResponse {
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
      
      const regiserResponse: RegisterResponse = await response.json();
      console.log(regiserResponse);
      return regiserResponse;
   } catch(error){
      return {
         succes: false
      };
   }
}