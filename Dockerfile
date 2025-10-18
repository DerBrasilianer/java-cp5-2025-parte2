FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia o Maven wrapper e o pom.xml
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Copia o código fonte
COPY src/ src/

# Dá permissão de execução para o Maven Wrapper
RUN chmod +x mvnw

# Compila o projeto
RUN ./mvnw clean package -DskipTests

# Expõe a porta 8081 que a aplicação vai rodar
EXPOSE 8081

# Comando para rodar a aplicação
CMD ["java", "-jar", "target/brinquedos-revisao-0.0.1-SNAPSHOT.jar"]