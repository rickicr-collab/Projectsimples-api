FROM maven:3.9.8-eclipse-temurin-21
ENV PROJECT_HOME /usr/src/projectsimplesapp
ENV JAR_NAME ProjectSimplesapp.jar
RUN mkdir -p $PROJECT_HOME
WORKDIR $PROJECT_HOME
COPY . . 
RUN mvn clean package -DskipTests
RUN mv $PROJECT_HOME/target/$JAR_NAME $PROJECT_HOME/
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "ProjectSimplesapp.jar"]