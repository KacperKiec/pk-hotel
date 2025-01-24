**pk-hotel**
pk-hotel is an application developed as part of a university project, designed for managing hotel reservations. The project consists of three main components:
- Backend: Server logic and API.
- Frontend: User interface.
- Database: Data storage structure.

**Table of Contents:**
- Technologies
- Installation
- Running the Application
- Project Structure
- Thoughts
- Authors

**Technologies**
The project uses the following technologies:
- Backend:
  - Java
  - Spring Boot

- Frontend:
  - TypeScript
  - React

- Database:
  - MySQL

- Others:
  - Docker

**Installation**
To run the project locally, follow these steps:

1. Clone the repository:

git clone https://github.com/KacperKiec/pk-hotel.git
cd pk-hotel

Prerequisites:
Ensure you have installed:
- Docker

Environment Configuration:
Configure the environment files for the backend and frontend if required.

Running the Application
Start with Docker Compose:

In the root directory of the project, there is a docker-compose.yml file. To start all services, run:
docker-compose up

This will start the backend, frontend, and database in Docker containers.

Access the application:

Frontend: The application will be available at http://localhost:3000.
Backend: The API will be available at http://localhost:8080.

**Project Structure**
pk-hotel-backend/ – Backend source code.
pk-hotel-frontend/ – Frontend source code.
pk-hotel-database/ – Database dockerfile (deprecated).
docker-compose.yml – Docker Compose configuration file.

**Thoughts**
Database is filled with sample data of hotels and rooms. There are no users and reservations.

**Authors**
This project was developed by:
- Kacper Kiec
- Kamil Bołoz
