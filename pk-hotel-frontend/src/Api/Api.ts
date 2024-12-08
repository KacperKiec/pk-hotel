import { Hotel, HotelDTO } from '../Hotel/Hotel'
import { User, transformUser, transformUserDTOToUser } from '../Users/User'
const baseUrl = 'http://localhost:8080'

export interface LoginData {
   email: string,
   password: string
}

export interface Response {
   status: number,
   user?: User
}

export interface UpdateResponse {
   message: string
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
      console.log(response.statusText);
      if(!response.ok){
         throw new Error(`${response.status}`);
      }
      return {
         status: response.status,
      }
   } catch(error: any){
      const status: number = Number(error.message)
      return {
         status
      }
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
         throw new Error(`${response.status}`);
      }
      const loggedUser: User = transformUserDTOToUser(await response.json());
      let loginResponse: Response = {
         status: response.status,
         user: loggedUser
      };

      return loginResponse;
   }
   catch(error: any){
      const status: number = Number(error.message)
      return{
         status: status,
      }
   }
}

export const updateUserApi = async (updatedUser: User): Promise<UpdateResponse> => {
   try{
      const response = await fetch(`${baseUrl}/user`, {
         method: 'PATCH',
         headers: {
           'Content-Type': 'application/json',
         },
         body: JSON.stringify(transformUser(updatedUser)),
         credentials: 'include',
      });

      if(!response.ok){
         throw new Error(`Unable to connect to the server. ${response.status}`);
      }
      const updateResponse: UpdateResponse = await response.json();
      updateResponse.message = "Saved"
      return updateResponse;
   } catch(error: any){
      throw new Error(`Connection refused`);
   }
}

export const logoutAPI = async () => {
   try {
     const response = await fetch('http://localhost:8080/logout', {
       method: 'POST',
       credentials: 'include', // Ensures cookies (JSESSIONID) are sent with the request
     });
 
     if (!response.ok) {
      throw new Error("Error during logout");
     }
   } catch (error) {
     console.error('Error during logout:', error);
   }
 };


export const addHotelApi = async (hotel: HotelDTO): Promise<Response> => {

   try{
      const response = await fetch(`${baseUrl}/admin/hotel`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(hotel),
         credentials: 'include',
      });
      if(!response.ok){
         throw new Error(`${response.status}`);
      }

      return {
         status: response.status,
      }
   }
   catch(error: any){
      const status: number = Number(error.message)
      return{
         status: status,
      }
   }
}

export const removeHotelApi = async (id: number): Promise<Response> => {

   try{
      const response = await fetch(`${baseUrl}/admin/hotel`, {
         method: 'DELETE',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({id}),
         credentials: 'include',
      });
      if(!response.ok){
         throw new Error(`${response.status}`);
      }

      return {
         status: response.status,
      }
   }
   catch(error: any){
      const status: number = Number(error.message)
      return{
         status: status,
      }
   }
}