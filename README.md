# 👶 Allo Nounou

> **A smart and secure babysitting platform connecting parents with trusted and available nannies.**

**Allo Nounou** is a full-stack web application designed to simplify the search and booking of babysitting services.

The platform connects **parents** looking for reliable childcare with **nannies** who want to offer their services. It provides a secure environment with identity verification, availability management, reservations, messaging, notifications and live video monitoring.

---

## 📌 Table of Contents

- [About the Project](#-about-the-project)
- [Problematic](#-problematic)
- [Solution](#-solution)
- [Main Features](#-main-features)
- [User Roles](#-user-roles)
- [Application Entities](#-application-entities)
- [Architecture](#-architecture)
- [Technologies](#-technologies)
- [Backend](#-backend)
- [Frontend](#-frontend)
- [Security](#-security)
- [Main Functionalities](#-main-functionalities)
- [API Testing](#-api-testing)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Project Structure](#-project-structure)
- [Screenshots](#-screenshots)
- [UI/UX Design](#-uiux-design)
- [Project Impact](#-project-impact)
- [Sustainable Development Goals](#-sustainable-development-goals)
- [Future Improvements](#-future-improvements)
- [Academic Context](#-academic-context)
- [Acknowledgements](#-acknowledgements)
- [Author](#-author)
- [License](#-license)

---

# 🌟 About the Project

**Allo Nounou** is a digital platform developed to make babysitting services easier, faster and safer.

The application allows parents to:

- 🔎 Search for available nannies
- 📍 Find nannies according to their location
- 📅 Check nanny availability
- 📝 Consult nanny announcements
- 📌 Make babysitting reservations
- 💬 Communicate with nannies
- 🔔 Receive notifications
- 🎥 Follow their children through a live video during the babysitting session
- 🔐 Benefit from secure authentication and identity verification

On the other side, nannies can:

- 👤 Create and manage their profile
- 📢 Publish babysitting announcements
- 📅 Manage their availability
- 📩 Receive reservation requests
- 💬 Communicate with parents
- 🔔 Receive notifications
- 📈 Find new babysitting opportunities

The main objective of Allo Nounou is to transform the traditionally informal babysitting search process into a **structured, digital and secure experience**.

---

# ❓ Problematic

Finding a trustworthy and available nanny at the right moment can be difficult.

In Tunisia, parents often rely on:

- Word of mouth
- Friends and family recommendations
- Social media groups
- Informal contacts

These methods can make it difficult to quickly find someone who is:

- ✅ Available
- 📍 Close to the parent's location
- 👩‍🍼 Experienced
- 🔐 Trustworthy
- 👶 Suitable for the child's needs

Parents also need reassurance about the safety of their children during the babysitting session.

This creates a real need for a platform that can facilitate the search, verification, communication and reservation process while creating a trusted environment for both parents and nannies.

---

# 💡 Solution

**Allo Nounou** provides a centralized digital solution that simplifies the entire babysitting process.

Instead of spending hours searching for a nanny, parents can use the platform to:

1. Create an account
2. Authenticate securely
3. Browse nanny profiles and announcements
4. Check availability
5. Communicate with nannies
6. Make a reservation
7. Receive notifications
8. Monitor their children through a live video during the babysitting session

The platform also introduces an additional level of trust through **identity verification using an identity card**.

For nannies, the platform provides a way to promote their services, manage their availability, receive reservations and communicate directly with parents.

---

# 🚀 Main Features

## 👤 User Management

The application supports different types of users.

Users can:

- Register
- Log in
- Manage their profile
- Update their personal information
- Log out
- Manage their role according to their account type

The two main user roles are:

- 👨‍👩‍👧 Parent
- 👩 Nanny

---

## 🔐 Secure Authentication

Authentication is implemented using **JWT (JSON Web Token)**.

The authentication system provides:

- 🔑 Secure login
- 🎟️ JWT token generation
- 🔒 Protected resources
- 👥 Role-based authorization
- 🛡️ Controlled access to application functionalities

This ensures that users can only access functionalities authorized for their role.

---

## 🪪 Identity Verification

One of the important security features of Allo Nounou is **identity verification using an identity card**.

This mechanism aims to:

- Increase user trust
- Reduce fake profiles
- Improve platform security
- Strengthen confidence between parents and nannies
- Provide a safer environment for families

Identity verification is particularly important because the platform deals with childcare and personal information.

---

# 📢 Announcements

Nannies can publish announcements describing their babysitting services.

An announcement can contain information such as:

- 📝 Description
- 📍 Location
- 📅 Availability
- 👩‍🍼 Experience
- 🧸 Services offered
- ℹ️ Other relevant information

Parents can browse available announcements and choose the nanny who best matches their needs.

---

# 📅 Availability Management

Nannies can manage their availability through the platform.

They can specify the periods during which they are available for babysitting.

Parents can then consult these availabilities before making a reservation.

This functionality helps to:

- Avoid unnecessary communication
- Reduce scheduling conflicts
- Simplify the booking process
- Save time for both parents and nannies

---

# 📝 Reservation System

Parents can make a reservation with a nanny based on her availability.

The reservation workflow is structured as follows:

```text
Parent
   │
   ▼
Select a nanny
   │
   ▼
Check availability
   │
   ▼
Send reservation request
   │
   ▼
Nanny receives notification
   │
   ▼
Nanny responds
   │
   ▼
Reservation status is updated
```

The reservation system provides a structured and transparent booking process.

---

# 💬 Messaging

Allo Nounou provides a messaging system allowing parents and nannies to communicate directly.

The messaging system can be used to:

- Ask questions
- Discuss babysitting requirements
- Confirm details
- Exchange information before the reservation
- Communicate during the preparation of a babysitting session

The application also supports **responses**, allowing users to continue structured conversations.

---

# 🔔 Notifications

The platform includes a notification system that keeps users informed about important events.

Notifications can be generated for:

- 📩 New reservation requests
- ✅ Reservation responses
- 💬 New messages
- 🔔 Important updates
- 📢 Other platform activities

This allows users to react quickly without constantly checking every section of the application.

---

# 🎥 Live Video Monitoring

One of the innovative features of Allo Nounou is the **live video functionality**.

During a babysitting session, the parent can access a live video session and monitor their children remotely.

The objective is to provide:

- 👶 Better visibility during babysitting
- ❤️ More reassurance for parents
- 🔒 Additional transparency
- 📱 Remote monitoring

The live video feature is designed to strengthen the relationship of trust between parents and nannies.

---

# 👥 User Roles

## 👨‍👩‍👧 Parent

Parents can:

- Create an account
- Authenticate securely
- Manage their profile
- Search for nannies
- View announcements
- Check availability
- Make reservations
- Send messages
- Receive notifications
- Access live monitoring during babysitting

---

## 👩 Nanny

Nannies can:

- Create an account
- Verify their identity
- Manage their profile
- Publish announcements
- Manage availability
- Receive reservation requests
- Respond to reservations
- Communicate with parents
- Receive notifications
- Participate in babysitting sessions

---

# 🗃️ Application Entities

The main entities of the application are:

### 👤 User

Represents the users of the platform.

A user can be a:

- Parent
- Nanny

---

### 📅 Disponibilité

Represents the availability periods of a nanny.

It allows parents to know when a nanny is available for babysitting.

---

### 📢 Annonce

Represents a babysitting service published by a nanny.

An announcement allows the nanny to present her services to potential clients.

---

### 📝 Réservation

Represents a booking request made by a parent for a nanny.

The reservation manages the relationship between the parent, the nanny and the selected availability.

---

### 💬 Message

Represents a message exchanged between users.

It allows parents and nannies to communicate through the platform.

---

### ↩️ Réponse

Represents a response associated with a message or communication.

It allows users to continue a structured conversation.

---

### 🔔 Notification

Represents a notification generated by an action performed on the platform.

Examples include:

- New reservation
- Reservation response
- New message
- Platform updates

---

# 🏗️ Architecture

Allo Nounou follows a **client-server architecture** based on a modern full-stack approach.

```text
                    ┌──────────────────────┐
                    │       Angular        │
                    │      Frontend        │
                    └──────────┬───────────┘
                               │
                         REST API / HTTP
                               │
                               ▼
                    ┌──────────────────────┐
                    │     Spring Boot      │
                    │       Backend        │
                    └──────────┬───────────┘
                               │
                ┌──────────────┼──────────────┐
                │              │              │
                ▼              ▼              ▼
           Controllers      Services       Security
                │              │              │
                └──────────────┼──────────────┘
                               ▼
                         Repositories
                               │
                               ▼
                    ┌──────────────────────┐
                    │       Database       │
                    └──────────────────────┘
```

The architecture separates the frontend, backend, business logic, data access and security layers.

This organization improves:

- Maintainability
- Scalability
- Code organization
- Security
- Reusability

---

# 🛠️ Technologies

## 🔙 Backend

- ☕ Java
- 🌱 Spring Boot
- 🔐 Spring Security
- 🔑 JWT
- 🗄️ Spring Data JPA
- 📡 REST API
- 💬 Real-time communication

---

## 🎨 Frontend

- 🅰️ Angular
- TypeScript
- HTML5
- CSS3
- Bootstrap / UI components
- Angular Services
- Angular Routing
- Angular HTTP Client
- Reactive Forms

---

## 🛠️ Development Tools

- 💻 IntelliJ IDEA
- 🧪 Postman
- 🌳 Git
- 🐙 GitHub
- 📦 Maven
- 🗄️ MySQL Workbench

---

# 🔙 Backend

The backend was developed using **Spring Boot**.

It follows a layered architecture:

```text
Controller
     ↓
  Service
     ↓
 Repository
     ↓
  Database
```

## Controllers

Controllers expose REST endpoints used by the Angular frontend.

They receive HTTP requests and return the appropriate responses.

## Services

Services contain the business logic of the application.

They process requests and coordinate interactions between controllers and repositories.

## Repositories

Repositories provide access to the application's database using Spring Data JPA.

## Entities

Entities represent the application's main data structures, such as:

- User
- Disponibilité
- Annonce
- Réservation
- Message
- Réponse
- Notification

---

# 🎨 Frontend

The frontend was developed using **Angular**.

Angular provides:

- Component-based architecture
- Routing
- Reactive forms
- HTTP communication with the backend
- Authentication management
- Dynamic interfaces
- Role-based navigation
- Reusable services

The interface was designed to be simple and intuitive so that both parents and nannies can easily use the platform.

---

# 🔒 Security

Security is an important part of Allo Nounou because the platform handles personal information related to users and children.

The application implements:

- 🔐 JWT authentication
- 🔑 Secure authentication
- 👥 Role-based access control
- 🛡️ Protected API endpoints
- 🪪 Identity verification
- 🔒 Controlled access to reservations
- 👤 Controlled access to user information

The objective is to provide a trustworthy environment for all platform users.

---

# 🧪 API Testing

The backend REST APIs were tested using **Postman**.

Postman was used to verify different functionalities, including:

- Authentication
- User management
- Announcements
- Availability
- Reservations
- Messages
- Responses
- Notifications

### Example Authentication Workflow

```text
POST /api/auth/register
        │
        ▼
POST /api/auth/login
        │
        ▼
Receive JWT Token
        │
        ▼
Add JWT Token to Request
        │
        ▼
Access Protected Endpoints
```

Postman makes it possible to test backend functionality independently from the Angular frontend.

---

# 📁 Project Structure

## Backend

```text
backend/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com.allonounou/
│       │       ├── controller/
│       │       ├── service/
│       │       ├── repository/
│       │       ├── entity/
│       │       ├── dto/
│       │       ├── security/
│       │       └── configuration/
│       │
│       └── resources/
│           └── application.properties
│
└── pom.xml
```

## Frontend

```text
frontend/
│
├── src/
│   ├── app/
│   │   ├── components/
│   │   ├── services/
│   │   ├── models/
│   │   ├── guards/
│   │   └── pages/
│   │
│   ├── assets/
│   └── environments/
│
├── angular.json
├── package.json
└── tsconfig.json
```

---

# ⚙️ Installation

## Prerequisites

Make sure you have installed:

- Java JDK
- Maven
- Node.js
- npm
- Angular CLI
- MySQL
- Git

---

## 📥 Clone the Repository

```bash
git clone https://github.com/Nour-Bouslimi/babySittingPlatform.git
cd allo-nounou
```



---

## ▶️ Run the Backend

Navigate to the backend directory:

```bash
cd backend
```

Install the dependencies:

```bash
mvn clean install
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend will normally be available at:

```text
http://localhost:8080
```

---

## ▶️ Run the Frontend

Navigate to the frontend directory:

```bash
cd frontend
```

Install the dependencies:

```bash
npm install
```

Run Angular:

```bash
ng serve
```

The frontend will normally be available at:

```text
http://localhost:4200
```

---

# 🗄️ Database Configuration

If the project uses MySQL, create the database:

```sql
CREATE DATABASE allo_nounou;
```

Then configure the database connection in:

```text
application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/allo_nounou
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Replace:

```text
YOUR_PASSWORD
```

with your local MySQL password.

> Adapt the database configuration to your actual project configuration if the database name, username or port is different.

---

# 🖼️ Screenshots

The following screenshots can be added to the `screenshots/` directory.

## 🏠 Home Page

The home page provides the main entry point to the Allo Nounou platform.

It allows users to discover the platform and access its main functionalities through a clear and intuitive interface.

Example:

```markdown
![Home Page](screenshots/home.png)
```

---

## 🔐 Authentication

The authentication interface allows users to securely access the platform.

It provides the login and registration functionalities required to create and access an account.

```markdown
![Authentication](screenshots/authentication.png)
```

---

## 👤 User Profile

The profile interface allows users to manage their personal information.

```markdown
![User Profile](screenshots/profile.png)
```

---

## 📢 Announcements

The announcements interface allows parents to browse babysitting services published by nannies.

```markdown
![Announcements](screenshots/announcements.png)
```

---

## 📅 Availability

The availability interface allows nannies to manage their available periods.

Parents can consult these periods before making a reservation.

```markdown
![Availability](screenshots/availability.png)
```

---

## 📝 Reservation

The reservation interface allows parents to select an available nanny and submit a booking request.

```markdown
![Reservation](screenshots/reservation.png)
```

---

## 💬 Messaging

The messaging interface allows parents and nannies to communicate directly.

```markdown
![Messaging](screenshots/messaging.png)
```

---

## 🔔 Notifications

The notification interface allows users to view important updates related to their activities.

```markdown
![Notifications](screenshots/notifications.png)
```

---

## 🎥 Live Video

The live video functionality allows parents to monitor their children remotely during the babysitting session.

```markdown
![Live Video](screenshots/live-video.png)
```

---

# 🎨 UI/UX Design

The user interface of Allo Nounou was designed around three main principles.

## 💙 Trust

The visual identity uses soft and reassuring colors to create a feeling of safety and confidence.

This is particularly important for a platform dedicated to childcare.

## 👶 Comfort

The design is friendly and adapted to a family-oriented platform.

The objective is to create a pleasant experience for both parents and nannies.

## ✨ Simplicity

The main actions are easy to identify and access:

- 🔎 Search
- 📅 Booking
- 💬 Messaging
- 📆 Availability
- 🔔 Notifications
- 👤 Profile management

The goal is to provide an intuitive experience without unnecessary complexity.

---

# 📊 Project Impact

Allo Nounou aims to transform a traditionally informal process into a more structured, secure and accessible digital experience.

## 👨‍👩‍👧 For Parents

Parents can:

- ✅ Save time
- ✅ Find available nannies
- ✅ Consult announcements
- ✅ Check availability
- ✅ Make reservations
- ✅ Communicate easily
- ✅ Receive notifications
- ✅ Benefit from identity verification
- ✅ Monitor their children remotely

---

## 👩 For Nannies

Nannies can:

- ✅ Find new babysitting opportunities
- ✅ Promote their services
- ✅ Manage their profile
- ✅ Manage their availability
- ✅ Receive reservation requests
- ✅ Communicate with parents
- ✅ Receive notifications
- ✅ Build trust through verified profiles

---

# 🌍 Sustainable Development Goals

Allo Nounou contributes to several **United Nations Sustainable Development Goals (SDGs)**.

---

## 🎯 SDG 5 – Gender Equality

Allo Nounou can contribute to gender equality by facilitating access to childcare services.

Reliable childcare can help parents, particularly mothers, better reconcile professional and family responsibilities.

The platform can also create new professional opportunities for women working as nannies.

---

## 🎯 SDG 8 – Decent Work and Economic Growth

Allo Nounou facilitates access to babysitting opportunities by connecting nannies with families looking for childcare services.

The platform can help nannies:

- Find new clients
- Promote their services
- Manage their availability
- Organize reservations

---

## 🎯 SDG 9 – Industry, Innovation and Infrastructure

Allo Nounou uses digital technologies to modernize the way parents search for and book babysitting services.

The project combines technologies such as:

- Spring Boot
- Angular
- REST APIs
- JWT authentication
- Database management
- Real-time communication
- Live video

These technologies allow the development of a modern digital solution responding to a real-life need.

---

## 🎯 SDG 11 – Sustainable Cities and Communities

By facilitating connections between families and nearby nannies, Allo Nounou encourages local services and strengthens relationships within communities.

The platform contributes to a more connected and accessible local childcare ecosystem.

---

# 🔮 Future Improvements

Several improvements could be added in future versions of Allo Nounou:

- ⭐ Rating and review system
- 💳 Online payment
- 📱 Mobile application
- 🤖 AI-based nanny recommendation
- 🗺️ Advanced map and geolocation
- 📊 Administration dashboard
- 📈 Statistics and analytics
- 🪪 Advanced identity verification
- 🔔 Push notifications
- 🎥 Improved real-time video infrastructure
- 🌐 Multi-language support
- 📄 Digital contracts between parents and nannies

These improvements could further increase the platform's usability, security and scalability.

---

# 🎓 Academic Context

This project was developed as part of a **Full Stack Spring Boot & Angular training at 9antra.tn – The Bridge**.

The project provided an opportunity to apply several software engineering concepts in a practical context.

The main concepts applied include:

- Object-Oriented Programming
- Java
- Spring Boot
- REST API development
- Spring Security
- JWT authentication
- JPA / Hibernate
- Angular
- TypeScript
- Database management
- Agile / Scrum methodology
- API testing
- Full-stack application architecture
- User authentication and authorization

The project also provided practical experience in designing and developing a complete web application from the backend to the frontend.

---

# 🙏 Acknowledgements

Special thanks to **9antra.tn – The Bridge** for the training, guidance and learning opportunities provided throughout the development of this project.

I would also like to thank everyone who contributed directly or indirectly to the realization of **Allo Nounou**.

---

# 👩‍💻 Author

## Nour Elhouda Bouslimi

🎓 Engineering Student – ESPRIT  
💻 Full Stack Developer  
🌱 Interested in Web Development, Software Engineering and innovative digital solutions.

---

# 📜 License

This project was developed for **educational and academic purposes**.

© 2025 Nour Elhouda Bouslimi – All rights reserved.

---

# ⭐ Allo Nounou

> **Find. Book. Connect. Babysit with confidence. 👶💙**
