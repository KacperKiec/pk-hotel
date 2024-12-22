import { Hotel, HotelDTO } from '../Hotel/Hotel'
import { RoomDTO } from '../Rooms/Room'
import { User, transformUser, transformUserDTOToUser } from '../Users/User'
const baseUrl = 'http://localhost:8080'

export interface LoginData {
   email: string,
   password: string
}

export interface Response {
   status: number,
   user?: User,
   message?: string
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

export const addRoomApi = async (room: RoomDTO): Promise<{ status: number; message?: string; data?: any }> => {
   try {
      const response = await fetch(`${baseUrl}/admin/room`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(room),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to add room.",
         };
      }

      const data = await response.json();
      return {
         status: response.status,
         data,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while adding the room.",
      };
   }
};


export interface addImageProps {
   room: RoomDTO,
   image: string[]
}

export const addImageApi = async (roomImage: addImageProps): Promise<{ status: number; message?: string }> => {
   try {
      const imagesWithPaths = roomImage.image.map((img) => ({ path: img }));

      // Prepare the updated request payload
      const payload = {
         room: roomImage.room,
         images: imagesWithPaths, // Use the transformed array here
      };

      const response = await fetch(`${baseUrl}/admin/room-image`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(payload),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to add images.",
         };
      }

      return {
         status: response.status,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while adding images.",
      };
   }
};


export interface addConveniencesToRoomProps {
   room: RoomDTO,
   conveniencesIds: number[]
}


export const addConvenienceAndAssignToRoom = async (
   name: string,
   room: RoomDTO
): Promise<{ status: number; message?: string }> => {
   try {
      // Step 1: Add convenience
      const convenienceResponse = await fetch(`${baseUrl}/admin/convenience`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({ name }),
         credentials: 'include',
      });

      if (!convenienceResponse.ok) {
         const errorData = await convenienceResponse.json();
         return {
            status: convenienceResponse.status,
            message: errorData.message || "Failed to add convenience.",
         };
      }

      const addedConvenience = await convenienceResponse.json(); // { id: number, name: string }

      // Step 2: Assign the convenience to the room
      const roomConveniences: addConveniencesToRoomProps = {
         room: room,
         conveniencesIds: [addedConvenience.id],
      };

      const assignResponse = await fetch(`${baseUrl}/admin/room-conveniences`, {
         method: 'PATCH',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(roomConveniences),
         credentials: 'include',
      });

      if (!assignResponse.ok) {
         const errorData = await assignResponse.json();
         return {
            status: assignResponse.status,
            message: errorData.message || "Failed to assign convenience to room.",
         };
      }

      return {
         status: assignResponse.status,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while adding convenience and assigning to room.",
      };
   }
};
