// Rooms standards used for filtering results
export type Standard = 'Apartament' | 'Hotel' | 'Hostel' | 'Homestays' | undefined;

// Main class that contains all the important information for searching
export class Room {
  // Private fields
  private _hotelId: number | undefined;
  private _roomNr: number | undefined;
  private _name: string = '';
  private _standard: Standard;
  private _capacity: number | undefined;
  private _description: string = '';
  private _price: number | undefined;
  private _imageUrl: string = '';

  // Getter for checking if the room is new
  get isNew(): boolean {
    return this._roomNr === undefined;
  }

  // Constructor for initializing values
  constructor(initializer: Partial<Room>) {
    if (!initializer) return;
    if (initializer.hotelId) this._hotelId = initializer.hotelId;
    if (initializer.roomNr) this._roomNr = initializer.roomNr;
    if (initializer.name) this._name = initializer.name;
    if (initializer.standard) this._standard = initializer.standard;
    if (initializer.capacity) this._capacity = initializer.capacity;
    if (initializer.description) this._description = initializer.description;
    if (initializer.price) this._price = initializer.price;
    if (initializer.imageUrl) this._imageUrl = initializer.imageUrl;
  }

  // Getters and setters for encapsulated fields
  get hotelId(): number | undefined {
    return this._hotelId;
  }
  set hotelId(value: number | undefined) {
    this._hotelId = value;
  }

  get roomNr(): number | undefined {
    return this._roomNr;
  }
  set roomNr(value: number | undefined) {
    this._roomNr = value;
  }

  get name(): string {
    return this._name;
  }
  set name(value: string) {
    this._name = value;
  }

  get standard(): Standard {
    return this._standard;
  }
  set standard(value: Standard) {
    this._standard = value;
  }

  get capacity(): number | undefined {
    return this._capacity;
  }
  set capacity(value: number | undefined) {
    this._capacity = value;
  }

  get description(): string {
    return this._description;
  }
  set description(value: string) {
    this._description = value;
  }

  get price(): number | undefined {
    return this._price;
  }
  set price(value: number | undefined) {
    this._price = value;
  }

  get imageUrl(): string {
    return this._imageUrl;
  }
  set imageUrl(value: string) {
    this._imageUrl = value;
  }
}

