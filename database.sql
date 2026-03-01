CREATE DATABASE smart_transport;
USE smart_transport;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE tickets (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    transport_type VARCHAR(20),
    source VARCHAR(100),
    destination VARCHAR(100),
    fare DOUBLE,
    issue_time DATETIME,
    qr_path VARCHAR(200)
);

CREATE TABLE journey_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ticket_id INT,
    planned_time DATETIME,
    actual_time DATETIME,
    delay_minutes INT
);