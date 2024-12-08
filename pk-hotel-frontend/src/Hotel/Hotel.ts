import dayjs from 'dayjs';

export type HotelDTO = {
  name: string;
  owner: string;
  registerDate: string;
  country: string;
  city: string;
  address: string;
}

export class Hotel {
  //private properties
  private _name: string;
  private _owner: string;
  private _registerDate: string;
  private _country: string;
  private _city: string;
  private _address: string;

  constructor(initializer: Omit<Hotel, 'registerDate'> & Partial<Pick<Hotel, 'registerDate'>>){
    this._name = initializer.name;
    this._owner = initializer.owner;
    this._registerDate = dayjs().format('YYYY-MM-DD');
    this._country = initializer.country;
    this._city = initializer.city;
    this._address = initializer.address;
  }

  public get name(): string {
    return this._name;
  }

  public set name(name: string){
    this._name = name;
  }

  public get owner(): string {
    return this._owner;
  }

  public set owner(owner: string){
    this._owner = owner;
  }

  public get registerDate(): string {
    return this._registerDate;
  }

  public set registerDate(registerDate: string){
    this._registerDate = registerDate;
  }

  public get country(): string {
    return this._country;
  }

  public set country(country: string){
    this._country = country;
  }

  public get city(): string {
    return this._city;
  }

  public set city(city: string){
    this._city = city;
  }

  public get address(): string {
    return this._address;
  }

  public set address(address: string){
    this._address = address;
  }
}

export const transfromHotel = (hotel: Hotel): HotelDTO => {
  return {
    name: hotel.name,
    owner: hotel.owner,
    registerDate: hotel.registerDate,
    country: hotel.country,
    city: hotel.city,
    address: hotel.address
  };
}