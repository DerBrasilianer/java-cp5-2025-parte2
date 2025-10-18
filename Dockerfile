FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copia o pom.xml primeiro (para cache de dependências)
COPY pom.xml .

# Baixa as dependências
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src/ src/

# Compila o projeto
RUN mvn clean package -DskipTests

# Runtime image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=build /app/target/brinquedos-revisao-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8081
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]