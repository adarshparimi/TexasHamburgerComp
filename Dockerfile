##FROM openjdk:8-jdk-alpine
##VOLUME /tmp
##EXPOSE 8080
##ADD build/libs/TexasHamburgComp-0.0.1-SNAPSHOT.jar springbootmysqldocker.jar
##ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/springbootmysqldocker.jar"]
#
#FROM openjdk:8-jdk-alpine
#EXPOSE 8080
#ARG JAR_FILE=build/libs/TexasHamburgComp-0.0.1-SNAPSHOT.jar
#ADD ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]