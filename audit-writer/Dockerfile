# ========== STAGE BUILD ==========
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /auditwriter

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# ========== STAGE RUN ==========

FROM eclipse-temurin:21-jre-alpine AS jre

WORKDIR /auditwriter

COPY --from=build /auditwriter/target/*.jar app.jar

EXPOSE 9010
ENTRYPOINT ["java","-jar","app.jar"]