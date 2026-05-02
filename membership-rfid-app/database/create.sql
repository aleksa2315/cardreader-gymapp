-- MySQL schema for Membership + RFID access application
-- NOTE: On alwaysdata, create the database from the panel first if CREATE DATABASE is not allowed.
-- Suggested database name: cardreader_membership

CREATE DATABASE IF NOT EXISTS cardreader_membership
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE cardreader_membership;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS access_logs;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS subscriptions;
DROP TABLE IF EXISTS rfid_cards;
DROP TABLE IF EXISTS membership_plans;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS app_users;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE app_users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'ADMIN',
    active TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_app_users_username (username),
    KEY ix_app_users_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE members (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NULL,
    email VARCHAR(150) NULL,
    note TEXT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY ix_members_name (last_name, first_name),
    KEY ix_members_status (status),
    KEY ix_members_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE membership_plans (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    code VARCHAR(50) NOT NULL,
    duration_days INT NOT NULL,
    price DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    active TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_membership_plans_code (code),
    KEY ix_membership_plans_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE rfid_cards (
    id BIGINT NOT NULL AUTO_INCREMENT,
    card_id VARCHAR(100) NOT NULL,
    member_id BIGINT NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    issued_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deactivated_at DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_rfid_cards_card_id (card_id),
    KEY ix_rfid_cards_member_id (member_id),
    KEY ix_rfid_cards_active (active),
    CONSTRAINT fk_rfid_cards_member
        FOREIGN KEY (member_id) REFERENCES members(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE subscriptions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    price DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY ix_subscriptions_member_id (member_id),
    KEY ix_subscriptions_plan_id (plan_id),
    KEY ix_subscriptions_period (start_date, end_date),
    KEY ix_subscriptions_status (status),
    CONSTRAINT fk_subscriptions_member
        FOREIGN KEY (member_id) REFERENCES members(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_subscriptions_plan
        FOREIGN KEY (plan_id) REFERENCES membership_plans(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE payments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    subscription_id BIGINT NULL,
    amount DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    payment_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    payment_method VARCHAR(50) NULL,
    note TEXT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY ix_payments_member_id (member_id),
    KEY ix_payments_subscription_id (subscription_id),
    KEY ix_payments_payment_date (payment_date),
    CONSTRAINT fk_payments_member
        FOREIGN KEY (member_id) REFERENCES members(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_payments_subscription
        FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE access_logs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    card_id VARCHAR(100) NOT NULL,
    member_id BIGINT NULL,
    allowed TINYINT(1) NOT NULL,
    reason VARCHAR(100) NOT NULL,
    request_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY ix_access_logs_card_id (card_id),
    KEY ix_access_logs_member_id (member_id),
    KEY ix_access_logs_allowed (allowed),
    KEY ix_access_logs_request_time (request_time),
    CONSTRAINT fk_access_logs_member
        FOREIGN KEY (member_id) REFERENCES members(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO membership_plans (name, code, duration_days, price, active)
VALUES
    ('Dnevna članarina', 'DAILY', 1, 0.00, 1),
    ('Nedeljna članarina', 'WEEKLY', 7, 0.00, 1),
    ('Polumesečna članarina', 'HALF_MONTH', 15, 0.00, 1),
    ('Mesečna članarina', 'MONTHLY', 30, 0.00, 1);

-- Admin user is created by the backend on first startup.
-- Default app login from env if users table is empty:
-- ADMIN_USERNAME=admin
-- ADMIN_PASSWORD=admin123
