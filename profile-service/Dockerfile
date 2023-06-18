FROM openjdk:17-alpine 
WORKDIR /app
COPY target/leaderboard-0.0.1-SNAPSHOT.jar build/ 
WORKDIR /app/build 
EXPOSE 8081 
ENTRYPOINT java -jar leaderboard-0.0.1-SNAPSHOT.jar