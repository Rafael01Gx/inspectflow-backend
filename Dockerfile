# Estágio 1: Build
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Instala o bash para execução do mvnw
RUN apk add --no-cache bash

# Copia os arquivos de configuração do Maven Wrapper para aproveitar o cache
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Baixa as dependências (camada de cache)
RUN ./mvnw dependency:go-offline

# Copia o código fonte e compila
COPY src/ src/
RUN ./mvnw clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:25-jre-noble
WORKDIR /app

# Dependências úteis e fuso horário
RUN apt-get update && \
    apt-get install -y curl tzdata && \
    rm -rf /var/lib/apt/lists/*
ENV TZ=America/Sao_Paulo

# Copia o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Configurações de performance otimizadas para Java 25 em containers
# -XX:+UseContainerSupport: Garante que a JVM entenda os limites de CPU/RAM do Docker
# -XX:MaxRAMPercentage: Alocação dinâmica de memória (75% da RAM do container)
# -XX:+UseZGC: O Z Garbage Collector é excelente para baixa latência em versões modernas do Java
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseZGC -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

# Execução direta para melhor gestão de processos pelo Docker
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
