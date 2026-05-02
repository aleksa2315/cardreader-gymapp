# Membership RFID App

Full-stack application for membership management and RFID card access.

## Modules

- `backend` - Java 21 + Spring Boot + JWT + MySQL
- `frontend` - Angular responsive UI
- `database/create.sql` - complete MySQL schema and default membership plans

## Important security note

Do not commit production database passwords or JWT secrets. Use environment variables.
If you shared a database password in a chat or ticket, rotate/change it before production use.

## Database setup

On alwaysdata, create the MySQL database from the panel if `CREATE DATABASE` is not allowed.
Suggested database name: `cardreader_membership`.

Then import:

```bash
mysql -h mysql-cardreader.alwaysdata.net -u cardreader -p cardreader_membership < database/create.sql
```

If your database name is different, change it in the command and in backend env variables.

## Backend local run

```bash
cd backend
mvn clean package
DB_HOST=localhost DB_PORT=3306 DB_NAME=cardreader_membership DB_USERNAME=root DB_PASSWORD=root JWT_SECRET=CHANGE_ME_CHANGE_ME_CHANGE_ME_CHANGE_ME_1234567890 mvn spring-boot:run
```

Or with Docker Compose:

```bash
docker compose up --build
```

Backend runs on:

```text
http://localhost:8080
```

Default login is created on first startup if `app_users` is empty:

```text
username: admin
password: admin123
```

Change this immediately after first login.

## Frontend local run

```bash
cd frontend
npm install
npm start
```

Frontend runs on:

```text
http://localhost:4200
```

## RFID access endpoint

This endpoint is public and intended for WECON Lua / PLC integration:

```http
POST /api/access/check
Content-Type: application/json
```

Request:

```json
{
  "cardId": "123456"
}
```

Response:

```json
{
  "cardId": "123456",
  "allowed": 1
}
```

`allowed = 1` means access granted, `allowed = 0` means access denied.

Access is granted only if:

1. RFID card exists.
2. Card is active.
3. Member is ACTIVE.
4. Member has active subscription for today.

Every check is logged in `access_logs`.
