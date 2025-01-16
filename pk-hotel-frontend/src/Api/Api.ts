import { HotelDTO } from '../Hotel/Hotel'
import { Extra } from '../Panels/AdminPanel/Extras/Extra'
import { Convenience, Images } from '../Panels/AdminPanel/Room/AddRoom'
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
}

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
         status: error.status,
         message: error.message || "An unexpected error occurred while adding the room.",
      };
   }
}

export interface addImageProps {
   room: RoomDTO,
   images: Images[]
}

export const addImageApi = async (roomImages: addImageProps): Promise<{ status: number; message?: string; images?: Images[] }> => {
   try {
      // Prepare the updated request payload
      const payload = {
         room: roomImages.room,
         images: roomImages.images, // Use the transformed array here
      };

      console.log(payload);

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

      const data = await response.json();
      console.log(data);

      return {
         status: response.status,
         images: data.images,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while adding images.",
      };
   }
}

export const removeImagesApi = async (room: RoomDTO, images: Images[]): Promise<{ status: number; message?: string;}> => {
   try {
      const response = await fetch(`${baseUrl}/admin/room-image`, {
         method: 'DELETE',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({
            room: {
               hotel: {
                  id: room.hotel.id
               },
               roomNr: room.roomNr
            },
            images
         }),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: -1,
            message: errorData.message || "Failed to delete images.",
         };
      }

      return {
         status: response.status,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while deleting the images.",
      };
   }
}

interface addConveniencesToRoomProps {
   room: RoomDTO;
   conveniencesIds: number[]
}

export const addConvenienceAndAssignToRoom = async (
   name: string,
   room: RoomDTO
): Promise<{ status: number; message?: string; convenience?: Convenience }> => {
   try {
      // Step 1: Get all conveniences and check if the desired one exists
      const allConveniencesResponse = await fetch(`${baseUrl}/admin/convenience?page=0`, {
         method: 'GET',
         headers: {
            'Content-Type': 'application/json',
         },
         credentials: 'include',
      });

      if (!allConveniencesResponse.ok) {
         const errorData = await allConveniencesResponse.json();
         return {
            status: allConveniencesResponse.status,
            message: errorData.message || "Failed to fetch conveniences.",
         };
      }

      const allConveniences = await allConveniencesResponse.json(); // { content: Convenience[] }
      const existingConvenience = allConveniences.content.find((c: Convenience) => c.name.toLowerCase() === name.toLowerCase());
      let convenience;

      if (existingConvenience) {
         // If convenience exists, use it
         convenience = existingConvenience;
      } else {
         // Step 2: Add convenience
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

         convenience = await convenienceResponse.json(); // { id: number, name: string }
      }

      // Step 3: Assign the convenience to the room
      const roomConveniences: addConveniencesToRoomProps = {
         room: room,
         conveniencesIds: [convenience.id],
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
         convenience,
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
}

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
}

export const removeRoomConveniencesApi = async (roomNr: number, hotelID: number, conveniencesIDs: number[]): Promise<{ status: number; message?: string;}> => {
   try {
      const response = await fetch(`${baseUrl}/admin/room-conveniences`, {
         method: 'DELETE',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({
            room: {
               roomNr,
               hotel: {
                  id: hotelID
               }
            },
            conveniencesIds: conveniencesIDs
         }),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: -1,
            message: errorData.message || "Failed to delete convenience.",
         };
      }

      return {
         status: response.status,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while deleting the conveniece.",
      };
   }
}

export const removeConvenienceApi = async (convenienveID: number): Promise<{ status: number; message?: string;}> => {
   try {
      const response = await fetch(`${baseUrl}/admin/room-conveniences`, {
         method: 'DELETE',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({
            convenienveId: convenienveID
         }),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: -1,
            message: errorData.message || "Failed to delete convenience.",
         };
      }

      return {
         status: response.status,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while deleting the convenience.",
      };
   }
}

export const findHotelByName = async (name: string): Promise<{ status: number; message?: string; data?: any }> => {
   try {
      const response = await fetch(`${baseUrl}/hotels/search?name=${encodeURIComponent(name)}`, {
         method: 'GET',
         headers: {
            'Content-Type': 'application/json',
         },
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to find this hotel.",
         };
      }

      const data = await response.json();
      return {
         status: response.status,
         data: data.content, // Assuming the response has a `content` property
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while searching for the hotel.",
      };
   }
};

export const findHotelById = async (id: number): Promise<{ status: number; message?: string; data?: any }> => {
   try {
      const response = await fetch(`${baseUrl}/hotels/${id}`, {
         method: 'GET',
         headers: {
            'Content-Type': 'application/json',
         },
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to find this hotel.",
         };
      }

      const data = await response.json();
      return {
         status: response.status,
         data: data.content, // Assuming the response has a `content` property
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while searching for the hotel.",
      };
   }
};

export const removeRoomApi = async (hotelId: number, roomNr:number): Promise<{ status: number; message?: string; data?: any }> => {
   try{
      const response = await fetch(`${baseUrl}/admin/room`, {
         method: 'DELETE',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({
            hotel:{
               id: hotelId
            },
            roomNr
         }),
         credentials: 'include',
      });
      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to remove a room.",
         };
      }

      return {
         status: response.status,
      };
   } catch(error: any){
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while removing the room.",
      };
   }
}

interface AddExtraProps {
   name: string,
   pricePerDay: number
}

export const addExtraApi = async ({name, pricePerDay}: AddExtraProps): Promise<{ status: number; message?: string;}> => {
   try {
      const response = await fetch(`${baseUrl}/admin/extra`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({
            name,
            pricePerDay
         }),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to add extra.",
         };
      }

      return {
         status: response.status,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while adding extra.",
      };
   }
}

export const getAllExtrasApi = async (): Promise<{ status: number; message?: string; data?: any[] }> => {
   let allExtras: any[] = [];
   let currentPage = 0;
   try {
      while (true) {
         const response = await fetch(`${baseUrl}/admin/extra?page=${currentPage}`, {
            method: 'GET',
            headers: {
               'Content-Type': 'application/json',
            },
            credentials: 'include',
         });

         if (!response.ok) {
            const errorData = await response.json();
            return {
               status: response.status,
               message: errorData.message || "Failed to fetch extras.",
            };
         }

         const responseData = await response.json();

         const items = responseData.content || [];
         allExtras = [...allExtras, ...items];

         if (currentPage >= responseData.totalPages) {
            break;
         }
         currentPage++;
      }

      return {
         status: 200,
         data: allExtras,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while fetching extras.",
      };
   }
};


export const deleteExtraApi = async (id:number): Promise<{ status: number; message?: string; data?: any }> => {
   try{
      const response = await fetch(`${baseUrl}/admin/extra`, {
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
            message: errorData.message || "Failed to remove an extra.",
         };
      }

      return {
         status: response.status,
      };
   } catch(error: any){
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while removing the extra.",
      };
   }
}

export const getHotelsFromCity = async (city: string): Promise<{ status: number; message?: string; data?: any }> => {
   let allHotels: HotelDTO[] = [];
   let currentPage = 1;

   try {
      while (true) {
         const response = await fetch(`${baseUrl}/hotels/search?page=${currentPage}&cities=${city}`, {
            method: 'GET',
            headers: {
               'Content-Type': 'application/json',
            },
            credentials: 'include',
         });

         if (!response.ok) {
            const errorData = await response.json();
            return {
               status: response.status,
               message: errorData.message || "Failed to fetch hotels.",
            };
         }

         const responseData = await response.json();

         const items = responseData.content || [];
         allHotels = [...allHotels, ...items];

         if (responseData.totalPages && currentPage >= responseData.totalPages) {
            break;
         }

         currentPage++;
      }

      return {
         status: 200,
         data: allHotels,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while fetching hotels.",
      };
   }
}

interface getRoomsWithFiltersProps{
   standard?: string;
   startDate?: string;
   endDate?: string;
   places?: number;
   page: number;
   city?: string;
}

export const getRoomsWithFilters = async ({ standard, startDate, endDate, places, page, city}: getRoomsWithFiltersProps): Promise<{status: number, message?: string, data?: any[], totalPages?: number}> =>{
   const params = new URLSearchParams(
      Object.entries({
        cities: city,
        standard,
        startDate,
        endDate,
        places: places ? places.toString() : undefined, // Convert number to string
        page: page.toString(), // Ensure page is always included
      })
        .filter(([_, value]) => value !== undefined) // Filter out undefined
        .map(([key, value]) => [key, value as string]) // Cast to string to satisfy types
    );
  
    try {
      const response = await fetch(`${baseUrl}/rooms/search?${params.toString()}`,{
         method: 'GET',
         headers: {
            'Content-Type': 'application/json',
         },
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to fetch rooms.",
         };
      }

      const responseData = await response.json();

      return {
         status: 200,
         data: responseData.content,
         totalPages: responseData.totalPages,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while fetching rooms.",
      };
   }
} 


interface makeReservationProps {
   user: UserDTO,
   room: RoomDTO,
   startDate: string,
   endDate: string,
   extras: Extra[]
}

export const makeReservationApi = async ({user, room, startDate, endDate, extras}: makeReservationProps): Promise<{status: number, message?: string, data?: any[]}> =>{
   const content = {
      reservation: {
         user: {
            email: user.email
         },
         room: {
            roomNr: room.roomNr,
            hotel: {
               id: room.hotel.id
            }
         },
         checkInDate: startDate,
         checkOutDate: endDate
      },
      extraIds: extras.map((element) => element.id)
   }

   console.log(content);

   try {
      const response = await fetch(`${baseUrl}/reservation`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(content),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to make reservation.",
         };
      }

      return {
         status: response.status,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while making reservation.",
      };
   }
}

export const getAllUserReservationsApi = async (): Promise<{ status: number; message?: string; data?: any[] }> => {
   let allReservations: any[] = [];
   let currentPage = 0;
   try {
      while (true) {
         const response = await fetch(`${baseUrl}/user/reservations?page=${currentPage}`, {
            method: 'GET',
            headers: {
               'Content-Type': 'application/json',
            },
            credentials: 'include',
         });

         if (!response.ok) {
            const errorData = await response.json();
            return {
               status: response.status,
               message: errorData.message || "Failed to fetch reservations.",
            };
         }

         const responseData = await response.json();

         const items = responseData.content || [];
         allReservations = [...allReservations, ...items];

         if (currentPage >= responseData.totalPages) {
            break;
         }
         currentPage++;
      }

      return {
         status: 200,
         data: allReservations,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while fetching reservations.",
      };
   }
};

export interface AddReviewProps {
   hotel: {
      id: number
    };
    user: {
      email: string
    };
    rating: number;
    content: string;
}

export const addReviewApi= async ({hotel, user, rating, content}: AddReviewProps): Promise<{ status: number; message?: string;}> => {
   try {
      const response = await fetch(`${baseUrl}/hotels/review`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
         },
         body: JSON.stringify({hotel, user, rating, content}),
         credentials: 'include',
      });

      if (!response.ok) {
         const errorData = await response.json();
         return {
            status: response.status,
            message: errorData.message || "Failed to add Review.",
         };
      }

      return {
         status: response.status,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while adding review.",
      };
   }
}


export const getAllReviewsApi = async (hotelId: number): Promise<{ status: number; message?: string; data?: any[] }> => {
   let allReviews: any[] = [];
   let currentPage = 0;
   try {
      while (true) {
         const response = await fetch(`${baseUrl}/hotels/review/${hotelId}?page=${currentPage}`, {
            method: 'GET',
            headers: {
               'Content-Type': 'application/json',
            },
            credentials: 'include',
         });

         if (!response.ok) {
            const errorData = await response.json();
            return {
               status: response.status,
               message: errorData.message || "Failed to fetch reviews.",
            };
         }

         const responseData = await response.json();

         const items = responseData.content || [];
         allReviews = [...allReviews, ...items];

         if (currentPage >= responseData.totalPages) {
            break;
         }
         currentPage++;
      }

      return {
         status: 200,
         data: allReviews,
      };
   } catch (error: any) {
      return {
         status: -1,
         message: error.message || "An unexpected error occurred while fetching reviews.",
      };
   }
};