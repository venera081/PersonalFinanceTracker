# PersonalFinanceTracker

A RESTful API for personal finance management, developed with Java and Spring Boot.

PersonalFinanceTracker is a backend application designed to help users manage their personal finances by tracking income and expenses, organizing transactions into categories, and viewing financial summaries.

The project focuses on backend development, REST API design, database integration, and clean application architecture.

## Features

- **Transaction Management** — manage financial transactions through a REST API.
- **Income and Expenses** — distinguish between income and expense transactions.
- **Transaction Categories** — organize transactions into predefined categories.
- **Data Validation** — validate incoming requests to maintain data consistency.
- **Exception Handling** — handle application errors and return appropriate HTTP responses.
- **Transaction Filtering** — filter transactions based on available criteria.
- **Financial Summary** — retrieve summarized financial information.
- **API Documentation** — explore and test API endpoints using Swagger UI.

## Technology Stack

- Java 21
- Spring Boot 4.1.2
- Spring WebMVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Git

## Database

The application uses PostgreSQL as its relational database.

Spring Data JPA and Hibernate are used for object-relational mapping and database interaction.

Database credentials and connection settings are managed through environment variables.

## Project Structure

The application follows a layered backend architecture:

- **Controller** — handles HTTP requests and responses.
- **Service** — contains business logic.
- **Repository** — provides database access.
- **Model** — represents application entities.
- **DTO** — defines data transfer objects.
- **Exception** — handles application-specific exceptions.

## Getting Started

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/venera081/PersonalFinanceTracker.git
   ```

2. Navigate to the project directory:

   ```bash
   cd PersonalFinanceTracker
   ```

3. Create a `.env` file based on `.env.example` and configure your database credentials.

4. Make sure PostgreSQL is running and the database is created.

5. Build the application:

   ```bash
   mvn clean install
   ```

6. Run the application:

   ```bash
   mvn spring-boot:run
   ```

### API Documentation

After starting the application, Swagger UI is available at:

`http://localhost:8080/swagger-ui.html`

## Development Status

The project is under active development.

### Completed

- REST API implementation.
- Transaction validation.
- Exception handling.
- Transaction filtering.
- Financial summary endpoint.
- PostgreSQL database configuration.
- Spring Data JPA integration.
- Initial database entity and table generation.

### Planned

- Complete transaction persistence and CRUD operations with PostgreSQL.
- Unit and integration testing.
- Database migrations with Flyway.
- Docker and Docker Compose.
- Spring Security and JWT authentication.
- Deployment to a Linux server.

---

# PersonalFinanceTracker — Русская версия

REST API для управления личными финансами, разработанный с использованием Java и Spring Boot.

PersonalFinanceTracker — это backend-приложение, предназначенное для учёта личных финансов. Оно позволяет работать с доходами и расходами, распределять транзакции по категориям и получать финансовую сводку.

Проект направлен на практическое изучение backend-разработки, проектирования REST API, интеграции баз данных и построения архитектуры приложения.

## Возможности

- **Управление транзакциями** — работа с финансовыми операциями через REST API.
- **Доходы и расходы** — разделение финансовых операций на доходы и расходы.
- **Категории транзакций** — распределение операций по заранее определённым категориям.
- **Валидация данных** — проверка входящих запросов.
- **Обработка исключений** — обработка ошибок приложения и возврат соответствующих HTTP-ответов.
- **Фильтрация транзакций** — поиск операций по доступным критериям.
- **Финансовая сводка** — получение обобщённой финансовой информации.
- **Документация API** — просмотр и тестирование API через Swagger UI.

## Технологический стек

- Java 21
- Spring Boot 4.1.2
- Spring WebMVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Git

## База данных

В качестве реляционной базы данных используется PostgreSQL.

Для объектно-реляционного отображения и взаимодействия с базой данных используются Spring Data JPA и Hibernate.

Настройки подключения и учётные данные базы данных хранятся в переменных окружения.

## Архитектура проекта

Приложение использует многоуровневую backend-архитектуру:

- **Controller** — обрабатывает HTTP-запросы и формирует ответы.
- **Service** — содержит бизнес-логику приложения.
- **Repository** — отвечает за взаимодействие с базой данных.
- **Model** — представляет сущности приложения.
- **DTO** — определяет объекты передачи данных.
- **Exception** — отвечает за обработку исключений.

## Запуск проекта

### Требования

- Java 21
- Maven
- PostgreSQL

### Установка

1. Клонировать репозиторий:

   ```bash
   git clone https://github.com/venera081/PersonalFinanceTracker.git
   ```

2. Перейти в директорию проекта:

   ```bash
   cd PersonalFinanceTracker
   ```

3. Создать файл `.env` на основе `.env.example` и указать параметры подключения к базе данных.

4. Убедиться, что PostgreSQL запущен и база данных создана.

5. Собрать приложение:

   ```bash
   mvn clean install
   ```

6. Запустить приложение:

   ```bash
   mvn spring-boot:run
   ```

### Документация API

После запуска приложения Swagger UI доступен по адресу:

`http://localhost:8080/swagger-ui.html`

## Статус разработки

Проект находится в активной разработке.

### Реализовано

- REST API.
- Валидация транзакций.
- Обработка исключений.
- Фильтрация транзакций.
- Endpoint финансовой сводки.
- Настройка подключения к PostgreSQL.
- Интеграция Spring Data JPA.
- Первоначальное создание сущности и таблицы базы данных.

### В планах

- Полноценное сохранение транзакций и реализация CRUD через PostgreSQL.
- Unit- и интеграционные тесты.
- Миграции базы данных с помощью Flyway.
- Docker и Docker Compose.
- Spring Security и JWT-аутентификация.
- Развёртывание приложения на Linux-сервере.