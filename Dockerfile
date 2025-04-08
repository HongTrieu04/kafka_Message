# Sử dụng image base là OpenJDK 17
FROM openjdk:17-jdk-slim

# Thêm JAR file vào container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]