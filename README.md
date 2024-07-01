The food delivery microservices application is designed to facilitate online food ordering and delivery, providing a seamless user experience while supporting multiple restaurants. The application is built using a microservices architecture, ensuring scalability, maintainability, and resilience. The project consists of several microservices: user service, order service, restaurant listing service, and food catalogue service.

**Microservices:**

1. **Eureka Service:**
    - **Purpose:** Service discovery and registration.
    - **Technology:** Spring Boot, Eureka.
    - **Deployment:** Deployed as a StatefulSet in Kubernetes, ensuring high availability and resilience.
2. **User Service:**
    - **Purpose:** Manage user information, including registration and authentication.
    - **Technology:** Spring Boot, MySQL, MapStruct.
    - **Features:**
        - User registration and login.
        - JWT-based authentication for secure access.
        - Role-based access control using Spring Security.
    - **Testing:** Unit tests using JUnit and Mockito.
    - **Deployment:** Dockerized and deployed in Kubernetes.
3. **Restaurant Listing Service:**
    - **Purpose:** Manage restaurant information.
    - **Technology:** Spring Boot, MySQL, MapStruct.
    - **Features:**
        - Retrieve and add restaurant details.
        - Efficient database access and data handling.
    - **Testing:** Unit tests using JUnit and Mockito.
    - **Deployment:** Dockerized and deployed in Kubernetes.
4. **Food Catalogue Service:**
    - **Purpose:** Manage food items available at restaurants.
    - **Technology:** Spring Boot, MySQL, RestTemplate, MapStruct.
    - **Features:**
        - Add and retrieve food items.
        - Inter-service communication using RestTemplate.
    - **Testing:** Unit tests using JUnit and Mockito.
    - **Deployment:** Dockerized and deployed in Kubernetes.
5. **Order Service:**
    - **Purpose:** Handle order creation and processing.
    - **Technology:** Spring Boot, MongoDB, RestTemplate, MapStruct.
    - **Features:**
        - Create and process orders.
        - Generate unique order IDs using a sequence generator.
        - Communicate with user service to fetch user details.
    - **Testing:** Unit tests using JUnit and Mockito.
    - **Deployment:** Dockerized and deployed in Kubernetes.

**Deployment and Infrastructure:**

- **Containerization:** All microservices are containerized using Docker.
- **Orchestration:** Deployed using Kubernetes, ensuring high availability and scalability.
- **Service Discovery:** Eureka is used for service discovery and registration.
- **Load Balancing:** AWS ALB (Application Load Balancer) is used for traffic management.
- **CI/CD:** Jenkins is used for continuous integration and deployment, automating the build, test, and deployment processes.
- **Code Quality:** SonarQube is integrated for code quality and coverage analysis.

**Frontend:**

- **Technology:** Angular, Angular CLI, CSS, SCSS.
- **Features:** Responsive and dynamic user interface for a seamless user experience.
- **Deployment:** Integrated with backend microservices and deployed in Kubernetes.
