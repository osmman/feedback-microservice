[![Build Status](https://travis-ci.org/osmman/feedback-microservice.svg?branch=master)](https://travis-ci.org/osmman/feedback-microservice)
# Feedback Microservice
This project is show case how to implement a very simple microservice which is created by using
Spring Boot. An application is storing feedback messages and make them accessible through REST API.

API operations:

* `POST /feedback` - store feedback
* `GET /feedback` - list of all feedback in system.
  * `GET /feedback?name={name}` - filter all feedback by author's name
* `GET /feedback/{id}` - find feedback by Id

More information on how to communicate with the REST interface is contained in Swagger Documentation on URL <http://localhost:8080/v2/api-docs>.

> You can use the Swagger UI to visualize and interact with the API. To activate this feature,
> you need to use maven profile `swagger-ui` during [building](#building) of project. This profile integrates
> Swagger UI into the application and exposes it on URL <http://localhost:8080/swagger-ui.html>.

All data are stored only in-memory storage [Spring Data Key Value](https://github.com/spring-projects/spring-data-keyvalue).  

Because this is a microservice I added [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
to be production ready. This starter adds endpoints to monitor and interact with the application.
For example, you can use <http://localhost:8081/health> endpoint to check if microservice is ready.

## Requirements

Before building and running this project you need:

* JDK 1.8
* Maven 3.3.3 or newer

## Building

The project can be built with

```bash
mvn clean install
```

if you want to integrate Swagger UI then build with following goals:

```bash
mvn clean install -Pswagger-ui
```

### Building Docker image

In order to build docker image, you must have access to a Docker daemon.
The easiest way is to setup the environment variable `DOCKER_HOST`.
Alternatively, you can use the Maven property `docker.host` to point to the Docker daemon.

The Docker image can be created by using following goals:

```bash
mvn clean install -Pdocker
```

## Running the tests
JUnit tests can be executed by a following goal:

```
mvn test
```

## Running project
Simplest way how to run application locally is to use Maven goal
```bash
mvn spring-boot:run
```

or you can execute built *jar*
```bash
java -jar feedback-0.0.1-SNAPSHOT.jar
```

or start [Docker image](#building-docker-image)
```bash
docker run -p 8080:8080 -p 8081:8081 osmman/feedback:latest
```

### Play with app

Get all feedback stored in system
```
curl -X GET http://localhost:8080/feedback
```

Send new feedback
```
curl -X POST --header 'Content-Type: application/json;charset=UTF-8' -d '{"name":"Bob","message":"Your feedback"}' http://localhost:8080/feedback
```

Filter all feedback written by Bob
```
curl -X GET http://localhost:8080/feedback?name=Bob
```

Get feedback by *ID*. Replace `{id}` placeholder with some stored in the system.
```
curl -X GET http://localhost:8080/feedback/{id}
```

## Deployment
The application can be easily deployed on OpenShift or Kubernetes cluster.
You can test it by running a single-node cluster inside your VM by one of these tools:
* [minishift](https://docs.openshift.org/latest/minishift/getting-started/index.html):
    ```bash
    minishift start && eval $(minishift docker-env)
    ```
* [minikube](https://github.com/kubernetes/minikube):
    ```bash
    minikube start && eval $(minikube docker-env)
    ```

Then the following command will package your app and run it on cluster:
```bash
mvn fabric8:build fabric8:deploy
```

to test that service works on OpenShift run
```
curl http://feedback-myproject.$(minishift ip).nip.io/feedback
```

for Kuberneties use
```bash
curl $(minikube service feedback --url)/feedback
```

Undeploy microservice from cluster by following command

```bash
mvn fabric8:undeploy
```

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

