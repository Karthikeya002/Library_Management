📚 Library Management System
A modern, full-stack Library Management System built with Spring Boot and a responsive web interface. This application provides complete library management capabilities including book inventory, member management, and borrowing transactions.
Java Spring Boot MySQL Bootstrap

🚀 Features
📚 Core Functionality
- 📘 Book Management: Add, view, update, and delete books from inventory
- 🧑‍💼 Member Management: Register and manage library members
- 🔄 Borrowing System: Complete book issue and return workflow
- 📊 Real-time Dashboard: Live statistics and analytics
- 🔍 Search & Filter: Find books and members quickly
🎨 User Interface
- 🪟 Modern Glass Design: Beautiful glass morphism UI
- 📱 Responsive Layout: Works perfectly on all devices
- 🌀 Smooth Animations: Enhanced user experience
- ⚡ Real-time Updates: Instant data refresh
📊 Dashboard & Analytics
- 📚 Live statistics (total books, available copies, members, active borrowings)
- 🕒 Recent activity tracking
- 📈 Visual charts and data representation
- ⏰ Overdue book tracking

🛠 Tech Stack
🔧 Backend
- ☕ Java 17
- 🌱 Spring Boot 3.2.0
- 🗃️ Spring Data JPA
- 🐬 MySQL 8.0
- 📦 Maven
🎨 Frontend
- 🧱 HTML5, CSS3, JavaScript
- 🎀 Bootstrap 5.3
- ⭐ Font Awesome Icons
- 📊 Chart.js for analytics

📦 Installation & Setup
✅ Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+
🧭 Steps
- Clone the Repository
git clone https://github.com/Karthikeya002/Library_Management.git
cd Library_Management
- Database Setup
CREATE DATABASE library_db;
- Configuration Update src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=your_username
spring.datasource.password=your_password
- Run the Application
mvn spring-boot:run


- Access at: http://localhost:8080
🎯 API Endpoints📘 Books- GET /api/books – Get all books
- GET /api/books/{id} – Get book by ID
- POST /api/books – Create new book
- PUT /api/books/{id} – Update book
- DELETE /api/books/{id} – Delete book
🧑 Members- GET /api/members – Get all members
- GET /api/members/{id} – Get member by ID
- POST /api/members – Create new member
- PUT /api/members/{id} – Update member
- DELETE /api/members/{id} – Delete member
🔄 Borrowing- POST /api/borrowing/borrow?bookId={}&memberId={} – Borrow book
- POST /api/borrowing/return/{recordId} – Return book
- GET /api/borrowing/current – Get current borrowings
- GET /api/borrowing/history – Get borrowing history
🗂 Project StructureLibrary_Management/
├── src/main/java/com/example/demo/
│   ├── controller/          # REST Controllers
│   ├── model/              # Entity Classes
│   ├── repository/         # Data Access Layer
│   ├── service/            # Business Logic
│   └── config/             # Configuration
├── src/main/resources/
│   ├── static/             # Frontend Assets
│   └── application.properties
└── pom.xml
📋 Entity Relationship- Book: id, title, author, isbn, genre, publicationYear, isAvailable
- Member: id, firstName, lastName, email, phone, membershipDate
- BorrowRecord: id, book, member, borrowDate, dueDate, returnDate, status
🎨 UI Sections- 📊 Dashboard: Overview with statistics and recent activity
- 📘 Books Management: Complete book inventory management
- 🧑 Members Management: Member registration and management
- 🔄 Borrow & Return: Transaction processing
- 🔍 Search & Filter: Advanced search capabilities
🔧 Development🏗️ Building the Projectmvn clean compile
🧪 Running Testsmvn test
📦 Packagingmvn clean package
🌟 Key Features in Detail📘 Book Management- Add books with complete metadata
- Track availability status
- Search by title, author, or genre
- ISBN validation and uniqueness
🧑 Member Management- Member registration with email validation
- Contact information management
- Membership date tracking
🔄 Borrowing System- Automatic due date calculation (14 days)
- Availability checks
- Overdue book tracking
- Return processing
