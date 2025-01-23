FROM mysql:8.2.0

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=pk_hotel_db
ENV MYSQL_USER=developer
ENV MYSQL_PASSWORD=developer

COPY ./pk-hotel-backend/src/main/resources/db/migration /docker-entrypoint-initdb.d/

EXPOSE 3307
