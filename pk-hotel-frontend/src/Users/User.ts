export type Role = "ADMIN" | "CLIENT";

export class User {
    // Private properties
    private _id: number | undefined;
    private _name: string;
    private _surname: string;
    private _password: string;
    private _email: string;
    private _role: Role;
    private _birthDate: string;
  
    // Constructor to initialize the User
    constructor(initializer: Omit<User, 'id'> & Partial<Pick<User, 'id'>>) {
      this._name = initializer.name;
      this._surname = initializer.surname;
      this._password = initializer.password;
      this._email = initializer.email;
      this._role = initializer.role;
      this._birthDate = initializer.birthDate;
    }
  
    public get surname(): string {
      return this._surname;
    }
  
    public set surname(value: string) {
      if (value.trim().length === 0) {
        throw new Error("Username cannot be empty");
      }
      this._surname = value;
    }
  

    // Getter and setter for id
    public get id(): number | undefined {
      return this._id;
    }
  
    public set id(value: number | undefined) {
      this._id = value;
    }
  
    // Getter and setter for username
    public get name(): string {
      return this._name;
    }
  
    public set name(value: string) {
      if (value.trim().length === 0) {
        throw new Error("Username cannot be empty");
      }
      this._name = value;
    }
  
    // Getter and setter for password (typically, you'd avoid exposing password directly)
    public get password(): string {
      return this._password;
    }
  
    public set password(value: string) {
      if (value.trim().length < 6) {
        throw new Error("Password must be at least 6 characters long");
      }
      this._password = value;
    }
  
    // Getter and setter for email
    public get email(): string {
      return this._email;
    }
  
    public set email(value: string) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(value)) {
        throw new Error("Invalid email format");
      }
      this._email = value;
    }

    public get role():  Role{
      return this._role;
    }

    public set role(role: Role){
      this._role = role;
    }

    public get birthDate(): string {
      return this._birthDate;
    }

    public set birthDate(birthDate: string){
      this._birthDate = birthDate;
    }
  }
  
type UserDTO = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: string;
  birthDate: string;
};

export const transformUser = (user: any): UserDTO => {
  return {
    firstName: user._name,
    lastName: user._surname,
    email: user._email,
    password: user._password,
    role: user._role,
    birthDate: user._birthDate,
  };
};
