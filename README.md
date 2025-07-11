# AskAtEase 🚀

**AskAtEase** is an intelligent Q&A platform inspired by Quora that leverages advanced Natural Language Processing to create a seamless knowledge-sharing experience. Users can post questions, receive community-driven answers, and discover semantically similar content through cutting-edge AI-powered search capabilities.

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![Python](https://img.shields.io/badge/Python-3.10+-blue.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue.svg)

---

## ✨ Key Features

### 🔐 **Secure Authentication**
- JWT-based authentication with Spring Security
- Role-based access control
- Secure password hashing and validation

### 🤖 **AI-Powered Search**
- **Semantic Search**: Find relevant content beyond keyword matching
- **Question Similarity**: Discover related questions using vector embeddings

### 🧠 **Advanced NLP Capabilities**
- **Query Summarization**: Summarization of search results
- **Embedding Generation**: Vector representations for semantic understanding

### 💬 **Community Features**
- **Question & Answer System**: Full-featured Q&A with rich text support
- **Topic Spaces**: Organized content by subject areas

### ⚡ **Performance Optimizations**
- **Asynchronous Processing**: Non-blocking API operations for Summarization
- **Vector Search**: Optimized similarity search with pgvector

---

## 🛠️ Tech Stack

### Backend Infrastructure
- **Framework**: Java Spring Boot 3.x
- **Database**: PostgreSQL 14+ with `pgvector` extension
- **Security**: Spring Security with JWT tokens

### AI/ML Services
- **NLP Engine**: FastAPI (Python) microservice
- **Embeddings**: Sentence Transformers(all-MiniLM-L6-v2)for semantic representations
- **Summarization**: Hugging Face Transformer models (sshleifer/distilbart-cnn-12-6)
- **Vector Operations**: pgvector for embedding storing and similarity computations.

### Additional Tools
- **Build Tool**: Maven
- **API Documentation**: OpenAPI/Swagger

---

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Postman       │    │   Spring Boot   │    │   FastAPI       │
│                │───▶│   Backend       │───▶│   NLP Service   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │                        │
                              ▼                        ▼
                       ┌─────────────┐         ┌─────────────┐
                       │ PostgreSQL  │         │ ML Models   │
                       │ + pgvector  │         │ (HuggingFace)│
                       └─────────────┘         └─────────────┘
                              
```

---
## Database Design
<img width="1325" height="1267" alt="AskatEase DB" src="https://github.com/user-attachments/assets/71010650-5694-46fa-b8e0-30c2d7508fc0" />


## 📋 Prerequisites

Before setting up AskAtEase, ensure you have the following installed:

- **Java Development Kit**: 17 or higher
- **Python**: 3.10 or higher with pip
- **PostgreSQL**: 14+ with `pgvector` extension
- **Maven**: 3.8+ for dependency management
- **Git**: For version control
- **Docker** : For pgvector in windows
---

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/jayesh1802/AskAtEase-Backend.git
cd AskAtEase-Backend
```

### 2. Database Setup

#### Install pgvector extension
```sql
-- Connect to PostgreSQL as superuser
CREATE EXTENSION IF NOT EXISTS vector;
```

#### Create database and user
```sql
CREATE DATABASE askatease_db;
CREATE USER askatease_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE askatease_db TO askatease_user;
```


### 3. Backend Configuration

#### Update `application.yml`
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/askatease_db
    username: askatease_user
    password: your_password
  
  redis:
    host: localhost
    port: 6379
    
  security:
    jwt:
      secret: your-jwt-secret-key
      expiration: 86400000 # 24 hours

nlp:
  service:
    url: http://localhost:8001
```

#### Build and run the Spring Boot application
```bash
# Install dependencies
mvn clean install

# Run the application
mvn spring-boot:run

### 5. NLP Service Setup

#### Navigate to NLP service directory
```bash
cd nlp-service
```

#### Create virtual environment
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

#### Install Python dependencies
```bash
pip install -r requirements.txt
```

#### Start the FastAPI service
```bash
1. For Generating Emebdding
uvicorn main:app --host 0.0.0.0 --port 8001 --reload
2. For Text Summarization
uvicorn main:app --host 0.0.0.0 --port 8002 --reload

```


## 🔧 Configuration

### Environment Variables
Create a `.env` file in the root directory:
```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=askatease_db
DB_USER=askatease_user
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your-super-secret-jwt-key
JWT_EXPIRATION=86400000

# NLP Service
EMBEDDING_URL=(http://localhost:8001/embed)

```

### Application Profiles
- **Development**: `application-dev.yml`
- **Production**: `application-prod.yml`
- **Testing**: `application-test.yml`

---

## 📖 API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

### Key Endpoints

#### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh JWT token

#### Questions & Answers
- `GET /api/questions` - List all questions
- `POST /api/questions` - Create a new question
- `GET /api/questions/{id}` - Get question details
- `POST /api/questions/{id}/answers` - Add an answer

#### Search & Discovery
- `GET /api/search` - Search questions and answers
- `GET /api/questions/{id}/similar` - Find similar questions

---


## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Development Guidelines
- Follow Java coding standards
- Write comprehensive tests
- Update documentation
- Ensure all tests pass

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🆘 Support & Contact

- **Issues**: [GitHub Issues](https://github.com/jayesh1802/AskAtEase-Backend/issues)
- **Discussions**: [GitHub Discussions](https://github.com/jayesh1802/AskAtEase-Backend/discussions)
- **Email**: jayeshdpadiya1802@gmail.com

---

## 🙏 Acknowledgments

- **Hugging Face** for providing excellent NLP models
- **pgvector** team for PostgreSQL vector extension
- **Spring Boot** community for the robust framework
- **FastAPI** for the high-performance Python web framework

---

## 📊 Project Status

![GitHub stars](https://img.shields.io/github/stars/jayesh1802/AskAtEase-Backend)
![GitHub forks](https://img.shields.io/github/forks/jayesh1802/AskAtEase-Backend)
![GitHub issues](https://img.shields.io/github/issues/jayesh1802/AskAtEase-Backend)
![GitHub pull requests](https://img.shields.io/github/issues-pr/jayesh1802/AskAtEase-Backend)

**Current Version**: 1.0.0  
**Development Status**: Active  
**Last Updated**: June 2025
