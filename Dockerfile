# ====== STAGE 1: build da aplicação ======
FROM maven:3.9-eclipse-temurin-21 AS builder

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar cache de dependências
COPY pom.xml .

# Baixa as dependências (sem rodar testes)
RUN mvn -q -DskipTests dependency:go-offline

# Copia o código fonte
COPY src ./src

# Build do jar (gera em target/)
RUN mvn -q -DskipTests clean package


# ====== STAGE 2: imagem final só com o JRE ======
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o jar gerado no stage anterior
# Se seu projeto gera apenas um jar, o *.jar resolve bem
COPY --from=builder /app/target/*.jar app.jar

# Porta que sua API expõe (Spring Boot padrão)
EXPOSE 8080

# Profile opcional (se você usar)
ENV SPRING_PROFILES_ACTIVE=docker

# Sobe a aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]
