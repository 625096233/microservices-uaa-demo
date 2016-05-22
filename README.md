# microservices-demo

This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

## Getting Started
    [ ] Identify dependencies for local development
    [ ] Generate uaa    
    [ ] Generate gateway
    [ ] Generate service(s)
    [ ] Generate docker-compose
    [ ] Bring up Registry and ELK via docker
    [ ] Update application.yml in config repo to turn on logging / update to docker.local ip
    [ ] Validate log forwarding is working as expected http://docker.local:5601
    [ ] Generate Entities in services
    [ ] Generate Entities in gateway
    [ ] Validate Entities can be created via gateway UI
    [ ] Validate API calls via Swagger UI

## Development (TODO)

Before you can build this project, you must install and configure the following dependencies on your machine:


## Building for production (TODO)

To optimize the app1 client for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war --spring.profiles.active=prod

## Continuous Integration (TODO)

To setup this project in Jenkins, use the following configuration:

* Project name: `app1`
* Source Code Management
    * Git Repository: `git@github.com:xxxx/app1.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Maven / Tasks: `-Pprod clean package`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

[JHipster]: https://jhipster.github.io/
