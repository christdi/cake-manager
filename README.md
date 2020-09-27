
# Cake Manager Micro Service (fictitious)  

## Introduction
This service allows you to view and add new cakes via a web and REST interface.

A small amount of cakes are prepopulated.

## Usage
### Web Interface
The web interface can be accessed via http://localhost:8080/ with your browser. It makes use of OAuth2 Github authentication.  It provides a user interface which allows one to view and add cakes.  In instances where the cake image cannot be retrieved, it will be substituted for a placeholder image.  Basic form validation is also implemented when adding a cake.

### REST Interface
The REST interface is unauthenticated.  The following endpoints are available: 

#### GET http://localhost:8080/cakes
Retrieve a list of all cakes as JSON.

#### POST http://localhost:8080/cakes
Allows the creation of a new cake.  The body of the request should be JSON in the following format
```
{
    "title": "title of the cake",
    "desc": "description of the cake",
    "image": "URL for an image of the cake"
}
```
All fields are mandatory.

## Development
This project uses maven as the build tool.  The application is implemented primarily with [Spring Boot](https://spring.io/projects/spring-boot) and members of the Spring family, as follows:
  * Spring Web
  * Spring Data/JPA
  * Spring Thymeleaf
  * Spring Validation
  * Spring Security
  * Spring 0Auth2 Client
  * Spring Test

It also uses [hsqldb](http://hsqldb.org/) for persistence.  Note this is only an in-memory database and new data is only retained while the application is running.

### Running Cake Manager
This project can be run via maven.  Github OAuth2 client ID and client secret must be provided for successful initialisation.  These are not stored in the repository for security purposes. To start the application while providing these properties, use the following:

`mvn -Dspring-boot.run.arguments="--github.client.id=<client-id> --github.client.secret=<client-secret>" spring-boot:run 
`

**IntelliJ Warning** If you're running mvn via IntelliJ maven runners, it escapes the quotes and the fields are not set properly.  The work around is to set these environment fields in IntelliJ settings->Build, Execution, Deployment->Build Trools->Maven->Runner->Environment Variables and then execute:

`mvn spring-boot:run`

### Docker Container
Spring Boot 2.3 supports building a docker image out of the box.  The following maven command can be used to build the docker container.  A local Docker daemon must be running or the command will fail.

`mvn spring-boot:build-image -Dspring-boot.build-image.imageName=christdi/cake-manager-docker`

When starting the container, the OAuth2 github client id and secret must be provided as environment variables.

`docker run -e github.client.id=<id> -e github.client.secret=<secret> -p 8080:8080 christdi/cake-manager-docker`

**Windows 7 Warning** The Docker daemon runs as a virtual machine on Windows 7 and the port forwarding specified in the command line only happens on the docker virtual machine.  You will have to enable port forwarding via VirtualBox settings from the docker virtual machine to the Windows 7 host.  OAuth2 will not properly work if you access the UI via the docker virtual machine IP as OAuth2 expects the authentication call from localhost:8080.

### Continuous Integration
The repository is configured to run a build including tests via Github Actions when a push occurs.

# Possible Improvements
This project was time boxed.  A few potential improvements I identified.

  * A standalone client.  Thymeleaf was great for getting something a web client up and running relatively quickly but it is bundled and deployed with the server itself.  A standalone client using the REST API would offer a greater deal of flexibility and separation of concerns.
 
  * Improved API error handling.  Spring API errors out of the box tend to be a bit on the verbose and technical side.  Something friendlier could be put together, especially if it was used by an external client.

  * REST API authentication.  This was omitted to allow easy usage of the API.  In a real project, there's pretty much no way you'd only lock down the UI and not the API.

# Log

## Commit [26a56d](https://github.com/christdi/cake-manager/commit/26a56d57273b7afd89e1e3e1e6864cd3093dc834)
Made the decision to just strip out all existing code and dependencies as the technology was dated and would just muddy the water if I attempted to build on top of it.
## Commit [15658b](https://github.com/christdi/cake-manager/commit/15658bd484ad3777609e12ca34caba93183aee64)
Introducing the very basics of Spring Boot.  Using a hard coded latest version because my IDE complains at me if I don't.
## Commit [0dacab](https://github.com/christdi/cake-manager/commit/0dacabf2523d72b676284961d924121b0b2bfce6)
Just stubbing out my basic endpoints here, creating the initial model and writing some early tests.  Spent a bit of time reading up on what Spring Boot offers for testing.  WebMvcTest seems like the right thing to use here as there's no real need to spin up the full context.

## Commit [6a7cf4](https://github.com/christdi/cake-manager/commit/6a7cf4a94b3ed33365f23527a14cb04df9784d75)
Primarily focused on getting persistence set up at this point.  Wiring in HSQLDB and annotating the model.  Spring Boot uses Hibernate to implement JPA out of the box, so all good there.  Decide to add an UUID as the key to the cake model.  The changes require me to introduce mocking to the existing integration tests. Some tweaks to the pom to correct some issues I'm seeing with packaged builds and to make it more accurate.

## Commit [d147ef](d147ef906faa7a5741d5023d9d33aca2e4ba4e67)
I was working on retrieving the pre-existing cake data with this commit.  I take a look at the endpoint [provided](https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json) and note that it has duplicates so we should probably handle that scenario.  A set seems like the simplest way to do so, introduce equals and hashCode to the model so they can be deduplicated when added to the set.

Spent a bit of time trying to decide which mechanism to use to load the data at startup.  Opted to use a @PostConstruct as we can ensure it happens while the context is being spun up and the dependencies on the repository and object mapper should ensure everything we need has already been instanced by Spring.  Also decide to swallow and log the exception if we can't grab the data, it's not the end of the world.

## Commit [ceba5f](https://github.com/christdi/cake-manager/commit/ceba5f3bca6b1de14632cfbab9411c690413b061)
Have to make a decision with a client, due to time constraints, I decide to go with Thymeleaf as it's paired with Spring Boot.  Little bit of a learning curve as I'm not familiar with the library itself, but I understand the concept.  Also add some validation to the model and extend the cake repository so we can check for duplicate cakes without relying on an exception being thrown when trying to save it.

Spent way too long messing around with CSS and Bootstrap but get something I'm vaguely happy with.

## Commit [e78597](https://github.com/christdi/cake-manager/commit/e78597aa6db09b2ea6012052717f61775506bd19)
Add GitHub continous integration support.  I haven't used GitHub's offering before, but it seems straight forward enough.  Pretty much just using their template as is.

## Commit [c16d94](https://github.com/christdi/cake-manager/commit/c16d94db5b0f423882d41cb0ba9c125c7d0764ba)
Introduce Spring Security into the mix, start off with locking everything down but realise it'll be a pain to use the API without a standalone client doing the OAuth2 stuff for you, so loosen it off for ease of use sake for this task.  Not storing the client ID and the secret in the repository is a massive pain due to various hiccups I hit along the way with a [known issue](https://github.com/spring-projects/spring-boot/issues/19998) with multiple command line arguments and my IDE escaping quotes when trying to set environment properties.

Looked into Google as an OAuth2 authority, saw some posts saying they can take 2 to 3 months to approve.  Decided to just use Github.

Run into some Docker problems at this point.  Docker is running in a VM on Windows 7, so OAuth2 doesn't work as I have to access the application via the IP of the virtual machine.  I mess around with some port forwarding just to make sure it works and make a note of it for anyone daft else daft enough to run Docker on Windows 7.