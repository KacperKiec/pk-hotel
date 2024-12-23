import { HotelDTO } from '../Hotel/Hotel'
import { RoomDTO } from '../Rooms/Room'
import { User, UserDTO, transformUser} from '../Users/User'
const baseUrl = 'http://localhost:8080'

export interface LoginData {
   email: string,
   password: string
}

// Register API
export const registerAPI = async (user: User, password: string): Promise<{ status: number; message?: string; data?: any }> => {
   try{
      const userToRegister: any = transformUser(user);
      userToRegister.password = password;

      const response = await fetch(`${baseUrl}/register`, {
         method: "POST",
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(userToRegister),
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to register.",
         };
      }

      const data = await response.json();
      return {
         status: response.status,
         data,
      };
   } catch(error: any){
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while register.",
      };
   }
}


export const loginApi = async (data: LoginData): Promise<{ status: number; message?: string; data?: UserDTO }> => {
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
      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to login.",
         };
      }

      const data = await response.json();
      return {
         status: response.status,
         data,
      };
   } catch(error: any){
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while login in.",
      };
   }
}

export const updateUserApi = async (updatedUser: User): Promise<{ status: number; message?: string; data?: any }> => {
   try{
      console.log(transformUser(updatedUser));
      const response = await fetch(`${baseUrl}/user`, {
         method: 'PATCH',
         headers: {
           'Content-Type': 'application/json',
         },
         body: JSON.stringify(transformUser(updatedUser)),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: -1,
            message: errorData.message || "Failed to update room.",
         };
      }

      const data = await response.json();
      return {
         status: response.status,
         data,
      };
   } catch(error: any){
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while updating the room.",
      };
   }
}

export const logoutAPI = async () => {
   try {
     const response = await fetch(`${baseUrl}/logout`, {
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


export const addHotelApi = async (hotel: HotelDTO): Promise<{ status: number; message?: string; data?: any }> => {

   try{
      const response = await fetch(`${baseUrl}/admin/hotel`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(hotel),
         credentials: 'include',
      });
      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to add hotel.",
         };
      }

      const data = await response.json();
      return {
         status: response.status,
         data,
      };
   } catch(error: any){
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while adding the hotel.",
      };
   }
}

export const removeHotelApi = async (id: number): Promise<{ status: number; message?: string; data?: any }> => {
   try{
      const response = await fetch(`${baseUrl}/admin/hotel`, {
         method: 'DELETE',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({id}),
         credentials: 'include',
      });
      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to remove hotel.",
         };
      }

      return {
         status: response.status,
      };
   } catch(error: any){
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while removing the hotel.",
      };
   }
}

export const updateHotelApi = async (hotel: HotelDTO, id: number): Promise<{ status: number; message?: string; data?: any }> => {
   try{
      const hotelToUpdate: any = hotel;
      hotelToUpdate.id = id;

      const response = await fetch(`${baseUrl}/admin/hotel`, {
         method: 'PATCH',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(hotelToUpdate),
         credentials: 'include',
      });
      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to add hotel.",
         };
      }

      const data = await response.json();
      return {
         status: response.status,
         data,
      };
   } catch(error: any){
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while adding the hotel.",
      };
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

export const findRoomApi = async (hotelID: number, roomNr: number): Promise<{ status: number; message?: string; data?: any }> => {
   try {
      const response = await fetch(`${baseUrl}/rooms/${hotelID}-${roomNr}`, {
         method: 'GET',
         headers: {
            'Content-Type': 'application/json',
         },
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: -1,
            message: errorData.message || "Failed to update room.",
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
         message: error.message || "An unexpected error occurred while updating the room.",
      };
   }
};


export const updateRoomApi = async (room: RoomDTO): Promise<{ status: number; message?: string; data?: any }> => {
   try {
      const response = await fetch(`${baseUrl}/admin/room`, {
         method: 'PATCH',
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
            message: errorData.message || "Failed to update room.",
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
         message: error.message || "An unexpected error occurred while updating the room.",
      };
   }
};
