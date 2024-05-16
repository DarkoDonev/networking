FROM eclipse-temurin
COPY . /usr/server
WORKDIR /usr/server
RUN javac Worker.java Server.java
EXPOSE 8522
CMD ["java","Server"]