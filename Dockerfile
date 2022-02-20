FROM openjdk:17-slim
COPY social-media-parser-impl/build/libs/boot_*.jar app.jar
CMD ["java", "-jar", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/var/log/oom_dumps/", "app.jar"]
EXPOSE 8080