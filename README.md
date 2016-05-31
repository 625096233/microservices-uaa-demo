microservices-uaa-demo
======================

This is a reference application that implements a microservices-based architecture using [Spring Cloud], [Spring Cloud Config] and [Spring Cloud Netflix].  This application used JHipster to scaffold out the various components and you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

The following projects are included in this repo:

* [gateway] - This server supports serving an AngularJS/Bootstrap web client in addition to being an [Embedded Zuul Reverse Proxy] that forwards requests to the backend services.

* [uaa] - This is the user account and authentication (uaa) that is used by the [gateway] to authenticate calls to the backend services.

* [docker directory] - This contains a [docker-compose.yml] to spin up the demo using docker containers.  For local development, you can use the [jhipster-registry.yml] and [elk.yml] for the config, registry, and centralized logging. Then run the uaa and gateway projects via your ide.

* [device-service] and [event-service] - These are simple microservices that register themselves with the [jhipster-registry] for service discovery that implements [Spring Cloud Netflix Eureka].

TODOs
-----

* Update gateway with bootstrap template
* swap out logo to generic
* add service to service call between device and event service
* add redis, rabbitmq, kafka messaging

Target Architecture
-------------------

![Target Architecture][architecture-diagram]


Development
------------

Before you can build this project, you must install and configure the following dependencies on your machine:

* Install Java 8 from the Oracle website.
* Install [docker] and [docker-compose].
* Install Node.js from the Node.js website (prefer an LTS version). This will also install npm, which is the node package manager we are using in the next commands.
* Install Yeoman: npm install -g yo
* Install Bower: npm install -g bower
* Install Gulp: npm install -g gulp
* Install JHipster: npm install -g generator-jhipster

More details here: [http://jhipster.github.io/installation/](http://jhipster.github.io/installation/)


Running locally
----------------------

These steps require that you have [docker] and [docker-compose] installed.

1. Start the [jhipster-registry] via docker: `docker-compose -f docker/jhipster-registry.yml up -d`
1. Start the [jhipster-console] via docker: `docker-compose -f docker/elk.yml up -d`
1. Run the [uaa] service with a dev profile: `mvn -Pdev`
1. Run the [gateway] service with a dev profile: `mvn -Pdev`
1. Run the [device-service] with a dev profile: `mvn -Pdev`
1. Run the [event-service] with a dev profile: `mvn -Pdev`


Building for production
-----------------------

To optimize the projects for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war --spring.profiles.active=prod

Continuous Integration
----------------------

To setup this project in Jenkins, see each module's readme.

* [uaa/README.md](uaa/README.md)
* [gateway/README.md](gateway/README.md)
* [device-service/README.md](device-service/README.md)
* [event-service/README.md](event-service/README.md)


[Spring Cloud]: http://projects.spring.io/spring-cloud/
[Spring Cloud Config]: http://cloud.spring.io/spring-cloud-config/
[Spring Cloud Netflix]: http://cloud.spring.io/spring-cloud-netflix/
[Spring Cloud Netflix Eureka]: http://cloud.spring.io/spring-cloud-static/spring-cloud.html#_service_discovery_eureka_clients
[Embedded Zuul Reverse Proxy]: http://cloud.spring.io/spring-cloud-static/spring-cloud.html#netflix-zuul-reverse-proxy
[gateway]: gateway/README.md
[uaa]: uaa/README.md
[docker directory]: docker/docker-compose.yml
[device-service]: device-service/README.md
[event-service]: event-service/README.md
[JHipster]: https://jhipster.github.io/
[jhipster-registry]: docker/jhipster-registry.yml
[jhipster-console]: docker/jhipster-console.yml
[Docker Toolbox]: https://www.docker.com/products/docker-toolbox
[docker]: http://www.docker.com/
[docker-compose]: https://docs.docker.com/compose/install/
[architecture-diagram]: gateway-uaa-architecture.png