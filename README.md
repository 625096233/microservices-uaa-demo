# microservices-demo

This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

## Gateway

This service is responsible for TODO

## UAA

This service is responsible for TODO

## Device Service

This service is responsible for the DeviceGroup and Device Entities.

### Device Service Entities
* DeviceGroup - groupId, groupName, groupDescription
* Device - deviceId, deviceName, deviceDescription
* Relationship: OneToMany - DeviceGroup has Many Devices
* Relationship: ManyToOne - A Device belongs to one group
* [Link to JDL Studio](http://jhipster.github.io/jdl-studio/#view/entity%20Device%20%7B%0A%09deviceId%20Long%2C%0A%20%20%20%20deviceName%20String%2C%0A%20%20%20%20deviceDescription%20String%0A%7D%0A%0Aentity%20DeviceGroup%20%7B%0A%09groupId%20Long%2C%0A%20%20%20%20groupName%20String%2C%0A%20%20%20%20groupDescription%20String%0A%7D%0A%0Arelationship%20OneToMany%20%7B%0A%09%2F**%0A%09*%20A%20relationship%0A%09*%2F%0A%09DeviceGroup%7Bdevice%7D%20to%0A%09%2F**%0A%09*%20Another%20side%20of%20the%20same%20relationship%0A%09*%2F%0A%09Device%0A%7D%0A%0A)

## Event Service

This service is responsible for accepting events from devices.

### Event Service Entities
* EventHistory - eventId, eventType, deviceId, eventData 


## Getting Started
    [ ] Identify dependencies for local development
    [ ] Generate uaa    
    [ ] Generate gateway
    [ ] Generate service(s) and entities
    [ ] Generate UI in gateway project
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

    Install Java 8 from the Oracle website.
    (Optional) Install a Java build tool.
    Whether you choose to use Maven or Gradle, you normally don’t have to install anything, as JHipster will automatically install the Maven Wrapper or the Gradle Wrapper for you.
    If you don’t want to use those wrappers, go to the official Maven webiste or Gradle website to do your own installation.
    Install Git from git-scm.com. We recommend you also use a tool like SourceTree if you are starting with Git.
    Install Node.js from the Node.js website (prefer an LTS version). This will also install npm, which is the node package manager we are using in the next commands.
    Install Yeoman: npm install -g yo
    Install Bower: npm install -g bower
    Install Gulp: npm install -g gulp
    Install JHipster: npm install -g generator-jhipster
    To find more information, tips and help, please have a look at the Yeoman “getting starting” guide and at the NPM documentation before submitting a bug.

More deatails here: [http://jhipster.github.io/installation/](http://jhipster.github.io/installation/)


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


## Ideas for demo app
- Photo Sharing for Slack?
- Online Shopping - Account, Catalog, Cart, Order 
    - https://github.com/paulc4/microservices-demo
    - https://www.infoq.com/articles/boot-microservices
- Users, Tasks, and Comments - https://github.com/rohitghatol/spring-boot-microservices
- Task/reward system -https://www.3pillarglobal.com/insights/building-a-microservice-architecture-with-spring-boot-and-docker-part-i
- Product, Order, Customer -  https://dzone.com/articles/spring-boot-creating
- Product Composite > Product > Recommendation > Review
- Moves and Actors
- Employee / Managers
- iot example - Thing > Event -http://java.sys-con.com/node/3729779
- iot example - Device > Event - https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&ved=0ahUKEwi13anZr_DMAhVGph4KHa-dD5oQFgg-MAE&url=http%3A%2F%2Fwww.iot-a.eu%2Fpublic%2Fpublic-documents%2Fdocuments-1%2F1%2F2%2Fservice-modelling-for-the-internet-of-things%2Fat_download%2Ffile&usg=AFQjCNGjurngaHouh14MoaBaUPMOBQC7uA&sig2=P3-hL0FqXxooxp2R_xpFEA&bvm=bv.122676328,d.dmo
    - http://www.slideshare.net/andrewhenson/data-modelling-and-knowledge-engineering-for-the-internet-of-things
- AWS API Gateway and ECS - https://aws.amazon.com/blogs/compute/using-amazon-api-gateway-with-microservices-deployed-on-amazon-ecs/
