# 🧾 Trackly Backend

Trackly is the backend service for the **Trackly** expense tracking application.
It provides APIs for user authentication, expense and category management, and integrates with **Google OAuth2** for secure login.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Security (OAuth2 with Google)
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

---

## ⚙️ Project Setup

### 1️⃣ Clone the repository
```bash
git clone https://github.com/<your-username>/trackly-backend.git
cd trackly-backend
```

### 2️⃣ Create a .env file in the project root
### .env file (do NOT commit this file)
```bash
DB_HOST=localhost
DB_PORT=5432
DB_NAME=trackly
DB_USER=your_db_user
DB_PASSWORD=your_db_password

GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

### 3️⃣ Export environment variables and run the application
```bash
export $(grep -v '^#' .env | xargs)
./mvnw spring-boot:run
```

## 🐘 Database Setup
Option 1 — Local PostgreSQL
Create a database and user matching the .env variables.

### How to run db
Launch this command on your terminal
```bash
psql -U your_db_user -d trackly
```

Option 2 — Using Docker
docker run --name trackly-db -e POSTGRES_DB=trackly \
  -e POSTGRES_USER=your_db_user \
  -e POSTGRES_PASSWORD=your_db_password \
  -p 5432:5432 -d postgres:15


## 🔐 Google OAuth2 Configuration
### Steps for Google OAuth2 setup:

### 1. Go to Google Cloud Console -> APIs & Services -> Credentials
### 2. Create an OAuth 2.0 Client ID
### 3. Add an authorized redirect URI:
###    http://localhost:8080/login/oauth2/code/google
### 4. Copy your Client ID and Client Secret into the .env file:
GOOGLE_CLIENT_ID=your_client_id
GOOGLE_CLIENT_SECRET=your_client_secret


## 🧩 Configuration in application.properties
```bash
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:trackly}
spring.datasource.username=${DB_USER:trackly}
spring.datasource.password=${DB_PASSWORD:}

spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID:}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET:}
spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}
spring.security.oauth2.client.registration.google.scope=email,profile
```


## ⚠️ Security Notes
### Never commit files containing real passwords, client secrets, or tokens.
### Always store credentials in environment variables or secret managers.
### Rotate your keys if they have ever been committed.