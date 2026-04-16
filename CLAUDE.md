# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Tennis Club Attendance & Transfer Management System (テニスクラブ 出欠・振替管理システム) — a Spring Boot MVC web app for managing student attendance, absences, and lesson transfers.

## Commands

### Build & Run

```bash
cd tennis

# Build WAR for deployment
./gradlew build

# Build skipping tests
./gradlew build -x test

# Run standalone (port 2289)
./gradlew bootRun

# Run tests
./gradlew test
```

### Docker Deployment

```bash
cd tennis-docker
docker-compose up
```

Copy the built WAR from `tennis/build/libs/` to `tennis-docker/webapps/` before starting Docker. The app is served at `http://localhost:8080/app/`.

Services: MySQL (3306), Tomcat (8080), phpMyAdmin (3001).

## Architecture

### Stack

- **Backend**: Spring Boot 3.5.4, Spring Data JPA / Hibernate, Java 21
- **Frontend**: Thymeleaf templates (HTML/CSS, mobile-responsive, Japanese text)
- **Database**: MySQL 8.0 (`tennis_user` DB, auto-DDL update mode)
- **Build**: Gradle 8.14.3, packaged as WAR for Tomcat 10.1.41

### Source Layout

All Java code lives under `tennis/src/main/java/jp/kobe_u/cs/tennis/tennis/`:

| Package | Purpose |
|---|---|
| `controller/` | MVC controllers: `HomeController`, `StudentController`, `CoachController` |
| `entity/` | JPA entities: `User`, `Schedule`, `Attendance`; enums `Role`, `AttendanceStatus` |
| `service/` | `AttendanceService` — core business logic |
| `repository/` | Spring Data JPA repositories for each entity |

Thymeleaf templates are in `tennis/src/main/resources/templates/`.

### Domain Model

- **User**: students and coaches, identified by `Role` enum (STUDENT/COACH). Students have a `remainingTransfers` counter.
- **Schedule**: weekly lesson slots (dayOfWeek, startTime, endTime, coach). Many-to-many relationship with students via `schedule_student` join table.
- **Attendance**: records per-student per-session status (`ATTENDED`, `ABSENT`, `TRANSFERRED`).

### Key Behaviors

- **DataInitializer**: On startup, seeds the DB with 2 test students, 1 coach, and 2 schedules if empty.
- **Scheduled cleanup**: `AttendanceService` deletes attendance records older than 1 month, daily at 4 AM JST (`@Scheduled` cron).
- **Mock auth**: Login is not implemented — `MOCK_STUDENT_ID=1` and `MOCK_COACH_ID=3` are hardcoded constants used by controllers.

### Route Structure

**Student routes** (`StudentController`):
- `GET /` — home
- `/student/qr` — QR scan attendance
- `/student/attendance` — history
- `/student/schedule` — view schedule
- `/student/absence` — report absence
- `/student/transfer` — request transfer

**Coach routes** (`CoachController`):
- `GET /coach` — home
- `/coach/attendance` — attendance list (date filter)
- `/coach/schedule` — schedule management
- `/coach/absence` — absence management
- `/coach/transfer` — transfer management

### Database Config

Defined in `tennis/src/main/resources/application.properties`:
- JDBC URL: `jdbc:mysql://localhost:3306/tennis_user`
- Credentials: `tennis_user` / `tennis_user`
- Timezone: `Asia/Tokyo`, charset: `utf8mb4`
- `spring.jpa.hibernate.ddl-auto=update`
