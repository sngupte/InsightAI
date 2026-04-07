# GitHub Insight AI

A full-stack application that analyzes GitHub repositories and issues using AI (LLM).

---

## 📁 Project Structure

```
/backend   → Spring Boot application (build + packaging)
/frontend  → React.js application (auto-built via Maven)
README.md  → Project documentation
```

---

## 🚀 Overview

GitHub Insight AI allows users to:

* Scan a GitHub repository
* Fetch issues and metadata
* Analyze issues using an LLM (Claude by Anthropic)
* Generate insights and summaries

---

## ⚙️ Prerequisites

Ensure the following are installed:

* Java 17+
* Maven
* MySQL
* Git

❗ Node.js is **NOT required manually** (handled by Maven build)

---

## 🗄️ Database Setup (MySQL)

### 1. Create Database

```sql
create database InsightAIDb;

use InsightAIDb;

CREATE TABLE IF NOT EXISTS github_issues (
    id  BIGINT NOT NULL AUTO_INCREMENT,
    github_id BIGINT NOT NULL,
    title TEXT,
    body TEXT,
    html_url VARCHAR(500),
    created_at TIMESTAMP,
    repo VARCHAR(255) NOT NULL,
    state varchar(20) NOT NULL,
    
    PRIMARY KEY (id, repo)
);

CREATE INDEX idx_repo ON github_issues(repo);
```

---

## ⚙️ Full Application Build (Frontend + Backend)

🚀 **Single Command Build**

```bash
mvn clean package
```

### ✅ What happens internally:

* Node.js installed automatically
* React dependencies installed
* React app built (`npm run build`)
* React build copied into Spring Boot (`/static`)
* Spring Boot JAR created
* Deployment package generated

---

## 📦 Output Package

```
target/
 └── build-package/
      ├── app.jar
      └── *.bat
```

---

## ▶️ Run Application

```bash
cd target/build-package
start.bat
```

## ⚙️ Backend Setup (Spring Boot)

### 1. Navigate to Backend

```bash
cd InsightAIService
```

### 2. Build the Project

```bash
mvn clean install
```

---

### 3. External Configuration (MANDATORY)

All sensitive configurations are stored in an external file:

```
D:\config\insightAI.properties
```

✔ This file is **committed in the repository with blank values**

---

### 4. Sample `insightAI.properties`

```properties
# Database Configuration
db.url=jdbc:mysql://localhost:3306/InsightAIDb
db.username=
db.password=


# LLM API Key
anthropic.api-key=
```

👉 Fill these values before running the application.

---

⚠️ Backend must be started before frontend.

---

## 🖥️ Frontend Setup (React.js)

### 1. Navigate to Frontend

```bash
cd frontend
```

### 2. Install Dependencies

```bash
npm install
```

### 3. Start Application

```bash
npm start
```

Application runs at:

```
http://localhost:3000
```

---


## 🔐 Security & Configuration

* `insightAI.properties` is included with **blank values only**
* Do NOT commit actual credentials
* Fill values locally before running

### Best Practices Followed

* Externalized configuration
* No secrets in source code
* Config file separation

---

## 🔗 API Endpoints

### 1. Scan GitHub Repository

**Endpoint:**

```
POST /githubinsightai/scan
```

**Description:**
Fetches repository issues and metadata from GitHub.

**Sample Request:**

```bash
curl -X POST http://localhost:8080/githubinsightai/scan \
-H "Content-Type: application/json" \
-d '{
  "repo": "facebook/react"
}'
```

```json
{
    "repo": "navinreddy20/Spring_Course",
    "issuesFetched": 19,
    "storedStatus": "Issues scanned and stored successfully."
}
```

---

### 2. Analyze Issues (LLM)

**Endpoint:**

```
POST /githubinsightai/analyze
```

**Description:**
Generates AI-based insights from issue data.

**Sample Request:**

```bash
curl -X POST http://localhost:8080/githubinsightai/analyze \
-H "Content-Type: application/json" \
-d '{
  "repo": "facebook/react",
  "prompt": "Analyze open issues and provide summary"
}'
```

**Sample Response:**

```json
{
  "analysis": "AI-generated insights..."
}
```

### 3. API Documentation (Swagger UI)

Swagger UI is integrated for easy API testing and documentation.

**Access Swagger UI**

Once the backend is running, open:

http://localhost:8080/swagger-ui/index.html
---

## ⚠️ Common Issues & Fixes

### 1. CORS Error

If frontend cannot call backend:

Add CORS configuration in Spring Boot:

```java
@CrossOrigin(origins = "http://localhost:3000")
```

---

### 2. MySQL Connection Error

* Ensure MySQL is running
* Verify credentials in `insightAI.properties`

---

### 3. API Key Issues

* Ensure LLM API key is configured

---

### 4. Backend Not Starting

* Check Java version (17+)
* Ensure config file exists at:

  ```
  D:\config\insightAI.properties
  ```

---

## 📌 Features

* GitHub repository scanning
* Issue extraction
* AI-powered analysis
* Clean React UI
* Externalized configuration

---

## 👨‍💻 Author

Saurabh Gupte

---
