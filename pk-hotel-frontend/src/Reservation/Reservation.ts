import { Extra } from "../Panels/AdminPanel/Extras/Extra";
import { Room } from "../Rooms/Room";
import dayjs from 'dayjs'

interface ReservationInitializer {
  room: Room;
  extras?: Extra[];
  startDate: string;
  endDate: string;
  id?: number;
}

export class Reservation {
  private _room: Room = new Room({});
  private _extras: Extra[] = [];
  private _startDate: string = '';
  private _endDate: string = '';
  private _id: number = -1;

  constructor(initializer: ReservationInitializer){
    this.room = initializer.room;
    if(initializer.extras) this.extras = initializer.extras;
    this.startDate = initializer.startDate;
    this.endDate = initializer.endDate;
    if(initializer.id) this.id = initializer.id;
  }

  set room(room: Room){
    this._room = room;
  }

  get room(): Room{
    return this._room;
  }

  set id(id: number){
    this._id = id;
  }

  get id(): number{
    return this._id;
  }


  set extras(extras: Extra[]){
    this._extras = extras;
  }

  get extras(): Extra[]{
    return this._extras;
  }

  set startDate(startDate: string){
    this._startDate = startDate;
  }

  get startDate(): string{
    return this._startDate;
  }

  set endDate(endDate: string){
    this._endDate = endDate;
  }

  get endDate(): string{
    return this._endDate;
  }

  calculatePrice(){
    const start = dayjs(this.startDate);
    const end = dayjs(this.endDate);

    let days = end.diff(start, 'days');
    let price = this.room.price * days;

    this.extras.forEach(element => {
      price += element.pricePerDay * days;
    });

    return price;
  }
}