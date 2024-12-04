import { User } from "./User";

interface ValidationResult {
  isValid: boolean;
  errors: string[];
}

export const validateUserDetails = (user: User): ValidationResult => {
  const errors: string[] = [];

  // Validate name
  if (!user.name || user.name.trim().length === 0) {
    errors.push("Name cannot be empty.");
  } else if (user.name.length < 2) {
    errors.push("Name must be at least 2 characters long.");
  }

  // Validate surname
  if (!user.surname || user.surname.trim().length === 0) {
    errors.push("Surname cannot be empty.");
  } else if (user.surname.length < 2) {
    errors.push("Surname must be at least 2 characters long.");
  }

  // Validate email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!user.email || !emailRegex.test(user.email)) {
    errors.push("Invalid email format.");
  }

  // Validate password
  const passwordCriteria = [
    { regex: /.{8,}/, message: "Password must be at least 8 characters long." },
    { regex: /[A-Z]/, message: "Password must contain at least one uppercase letter." },
    { regex: /[a-z]/, message: "Password must contain at least one lowercase letter." },
    { regex: /\d/, message: "Password must contain at least one number." },
    { regex: /[!@#$%^&*(),.?":{}|<>]/, message: "Password must contain at least one special character." },
  ];

  if (!user.password || user.password.trim().length === 0) {
    errors.push("Password cannot be empty.");
  } else {
    passwordCriteria.forEach((criteria) => {
      if (!criteria.regex.test(user.password)) {
        errors.push(criteria.message);
      }
    });
  }

  // Validate birthDate (simple validation for format)
  const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
  if (!user.birthDate || !dateRegex.test(user.birthDate)) {
    errors.push("BirthDate must be in the format YYYY-MM-DD.");
  }

  return {
    isValid: errors.length === 0,
    errors,
  };
};
