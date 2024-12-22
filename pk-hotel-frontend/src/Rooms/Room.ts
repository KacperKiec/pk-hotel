// Rooms standards used for filtering results
export type Standard = 'LOW' | 'AVERAGE' | 'HIGH';

// Main class that contains all the important information for searching
export class Room {
  // Private fields
  private _hotelId: number = -1;
  private _roomNr: number = -1;
  private _standard: Standard = 'LOW';
  private _places: number = -1;
  private _description: string = '';
  private _price: number = -1;
  private _imagesUrl: string[] = [];
  private _reviews: number = 0.0;
  private _conveniences: string[] = [];
  private _name: string = "";

  // Getter for checking if the room is new
  get isNew(): boolean {
    return this._roomNr === undefined;
  }

  // Constructor for initializing values
  constructor(initializer: Partial<Room>) {
    if (!initializer) return;
    if (initializer.hotelId) this._hotelId = initializer.hotelId;
    if (initializer.roomNr) this._roomNr = initializer.roomNr;
    if (initializer.standard) this._standard = initializer.standard;
    if (initializer.places) this._places = initializer.places;
    if (initializer.description) this._description = initializer.description;
    if (initializer.price) this._price = initializer.price;
    if (initializer.imagesUrl) this._imagesUrl = initializer.imagesUrl;
    if (initializer.conveniences) this._conveniences = initializer.conveniences;
  }

  // Getters and setters for encapsulated fields
  get hotelId(): number {
    return this._hotelId;
  }
  set hotelId(value: number) {
    this._hotelId = value;
  }

  get roomNr(): number{
    return this._roomNr;
  }
  set roomNr(value: number) {
    this._roomNr = value;
  }

  get standard(): Standard {
    return this._standard;
  }
  set standard(value: Standard) {
    this._standard = value;
  }

  get places(): number {
    return this._places;
  }

  set places(value: number) {
    this._places = value;
  }

  get description(): string {
    return this._description;
  }
  set description(value: string) {
    this._description = value;
  }

  get price(): number {
    return this._price;
  }
  set price(value: number) {
    this._price = value;
  }

  get imagesUrl(): string[] {
    return this._imagesUrl;
  }
  set imagesUrl(value: string[]) {
    this._imagesUrl = value;
  }

  get conveniences(): string[]{
    return this._conveniences
  }

  get reviews(): number{
    return this._reviews;
  }

  set reviews(value: number){
    this._reviews = value;
  }

  set name(value: string){
    this._name = value;
  }

  get name(): string {
    return this._name;
  }
}

type hotel = {
  "id": number
}

export type RoomDTO = {
  hotel: hotel;
  roomNr: number,
  places: number,
  price: number,
  standard: Standard;
  description: string;
}

export const transformRoom = (room: Room): RoomDTO =>{
  return {
    hotel: {"id": room.hotelId},
    roomNr: room.roomNr,
    places: room.places,
    price: room.price,
    standard: room.standard,
    description: room.description,
  }
}
