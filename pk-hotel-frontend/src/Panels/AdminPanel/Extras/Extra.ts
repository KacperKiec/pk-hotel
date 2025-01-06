export class Extra{
    private _id: number | undefined;
    private _pricePerDay: number = 0.0;
    private _name: string = '';

    constructor(intializer: Extra){
        if(intializer.id) this.id = intializer.id;
        this.name = intializer.name;
        this.pricePerDay = intializer.pricePerDay;
    }

    get id(): number | undefined{
        return this._id;
    }

    set id(id: number | undefined){
        this._id = id;
    }

    get pricePerDay(): number {
        return this._pricePerDay;
    }

    set pricePerDay(price: number){
        this._pricePerDay = price;
    }

    get name(): string {
        return this._name;
    }

    set name(name: string){
        this._name = name;
    }
}