#FROM openjdk:11 AS BUILD
#ADD . /app
#WORKDIR app
#RUN ./gradlew -Pvaadin.productionMode

# Run stage
FROM tomcat:9.0.35-jdk11-openjdk
COPY ./build/libs/borrow.war /usr/local/tomcat/webapps/ROOT.war
COPY jdbc.sh .
COPY start.sh .
RUN chmod +x start.sh && chmod +x jdbc.sh

ENV PORT=8080
ENV DATABASE_URL=postgres://ukmlnmjnjmkgyx:0d65b3b5ad57c81a02e759272ba51a2d048d8947e085f469b2d3c48e1b5a4ca2@ec2-34-193-235-32.compute-1.amazonaws.com:5432/db0rvl0ae3kekr
RUN adduser borrow
USER borrow

ENTRYPOINT "./start.sh"