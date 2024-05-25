create database Vivianne;

use Vivianne;


CREATE TABLE `users` (
    `user_id` CHAR(36) NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `username` VARCHAR(255) UNIQUE NOT NULL,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `salt` BINARY(16) NOT NULL, -- 128-bit salt;
    `hashed_password` BINARY(64) NOT NULL, -- SHA-256 hash;
    `role` ENUM('SUPER_ADMIN', 'ADMIN', 'USER') NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `companies` (
    `company_id` CHAR(36) NOT NULL,
    `company_name` VARCHAR(255) NOT NULL,
    `area_code` ENUM('PRISTINA', 'MITROVICA', 'PEJA', 'PRIZREN', 'FERIZAJ', 'GJILAN', 'GJAKOVA'),
    `company_status` ENUM('ACTIVE', 'SUSPENDED') NOT NULL,
    PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `buses` (
    `bus_id` CHAR(36) NOT NULL,
    `bus_model` VARCHAR(255) NOT NULL,
    `vin` VARCHAR(17) NOT NULL UNIQUE,  -- standard length of vin
    `passenger_capacity` INT UNSIGNED NOT NULL,
    `bus_type` ENUM('DOUBLE_DECKER', 'SINGLE_DECKER', 'MINIBUS', 'COACH'),
    `activity_status` ENUM('ACTIVE', 'SUSPENDED'),
    `comfort_rating` ENUM('ONE_STAR', 'TWO_STARS','THREE_STARS', 'FOUR_STARS', 'FIVE_STARS'),
    PRIMARY KEY (`bus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `company_lines` (
    `line_id` CHAR(36) NOT NULL,
    `status` ENUM('ACTIVE', 'COMPLETED', 'FAILED') NOT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `creator_user_id` CHAR(36),
    `creation_time` DATETIME NOT NULL,
    `start_location` VARCHAR(255) NOT NULL,
    `end_location` VARCHAR(255) NOT NULL,
    `company_assigned_id` char(36),
    `bus_model_id` char(36),
    PRIMARY KEY (`line_id`),
    FOREIGN KEY (`creator_user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL,
    FOREIGN KEY (`company_assigned_id`) REFERENCES `companies` (`company_id`) ON DELETE SET NULL,
    FOREIGN KEY (`bus_model_id`) REFERENCES `buses` (`bus_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `company_buses` (
    `company_id` CHAR(36) NOT NULL,
    `bus_id` CHAR(36) NOT NULL,
    PRIMARY KEY (`company_id`, `bus_id`),
    FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
    FOREIGN KEY (`bus_id`) REFERENCES `buses` (`bus_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `companies`
ADD COLUMN `description` VARCHAR(500);

ALTER TABLE users
    MODIFY COLUMN salt VARCHAR(128);


ALTER TABLE users
    MODIFY COLUMN hashed_password VARCHAR(256);

alter table company_lines
    modify start_location enum ('PRISTINA', 'MITROVICA', 'PEJA', 'PRIZREN', 'FERIZAJ', 'GJILAN', 'GJAKOVA') not null;


alter table company_lines
    modify end_location enum ('PRISTINA', 'MITROVICA', 'PEJA', 'PRIZREN', 'FERIZAJ', 'GJILAN', 'GJAKOVA') not null;


DELIMITER //

CREATE PROCEDURE GetStatisticsByLocationAndTime(
    IN p_start_location VARCHAR(255),
    IN p_weeks INT
)
BEGIN
    DECLARE v_start_time DATETIME;

    SET v_start_time = NOW() - INTERVAL p_weeks WEEK;

    SELECT
        COUNT(*) AS totalLines,
        CONCAT(ROUND(SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) / COUNT(*) * 100, 2), '%') AS successRate,
        CONCAT(ROUND(SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)), 2), ' hours') AS hoursTraveled
    FROM
        vivianne.company_lines
    WHERE
            start_location = p_start_location
      AND start_time >= v_start_time;
END //

DELIMITER ;


create table bus_line_stops(
    line_id char(36),
    stop_name varchar(256),
    PRIMARY KEY (line_id, stop_name)
);
