// Rooms standards used for filtering results
export type Standard = 'Apartament' | 'Hotel' | 'Hostel' | 'Homestays' | undefined;

// Main class have all the important informations for searching  
export class Room {
  hotelId: number | undefined;
  roomNr: number | undefined;
  name: string = '';
  standard: Standard;
  capacity: number | undefined;
  description: string = '';
  price: number | undefined;
  imageUrl: string = '';

  // Method for checking if the room was previously registered in database
  get isNew(): boolean {
    return this.roomNr === undefined;
  }

  // Constructor, value assigment take place here
  constructor(initializer: Partial<Room>){
    if(!initializer) return;
    if(initializer.hotelId) this.hotelId = initializer.hotelId;
    if(initializer.roomNr) this.roomNr = initializer.roomNr;
    if(initializer.name) this.name = initializer.name;
    if(initializer.standard) this.standard = initializer.standard;
    if(initializer.capacity) this.capacity = initializer.capacity;
    if(initializer.description) this.description = initializer.description;
    if(initializer.price) this.price = initializer.price;
    if(initializer.imageUrl) this.imageUrl = initializer.imageUrl;
  }
}

