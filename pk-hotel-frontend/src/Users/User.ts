export class User {
    // Private properties
    private _id: number | undefined;
    private _username: string;
    private _password: string;
    private _email: string;
  
    // Constructor to initialize the User
    constructor(initializer: Omit<User, 'id'> & Partial<Pick<User, 'id'>>) {
      this._username = initializer.username;
      this._password = initializer.password;
      this._email = initializer.email;
    }
  
    // Getter and setter for id
    public get id(): number | undefined {
      return this._id;
    }
  
    public set id(value: number | undefined) {
      this._id = value;
    }
  
    // Getter and setter for username
    public get username(): string {
      return this._username;
    }
  
    public set username(value: string) {
      if (value.trim().length === 0) {
        throw new Error("Username cannot be empty");
      }
      this._username = value;
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
  }
  