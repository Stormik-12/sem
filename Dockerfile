FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем JAR (имя должно совпадать с finalName в pom.xml)
COPY server/target/chat-server-jar-with-dependencies.jar app.jar

# Копируем конфиги
COPY server/src/main/resources/*.yaml .
COPY server/src/main/resources/*.xml .

CMD ["java", "-jar", "app.jar"]
