# Assignments

Here you find the exercises for the OpenTelemetry workshop.
At the end of each exercise some hints are provided,
mostly in the form of a link to the location where more information can be found.

## Exercise 1 - Auto instrumentation

Start the application using the OpenTelemetry java agent provided in the lib folder.

Don't forget to set the environment variables needed for the OpenTelemetry Collector.

```shell
export OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317
export OTEL_LOGS_EXPORTER=otlp
export OTEL_RESOURCE_ATTRIBUTES=service.name=Brewery

java -javaagent:lib/opentelemetry-javaagent.jar -jar target/it-brewery-1.0.0-SNAPSHOT.jar
```

Run a few queries with the REST Http client (or run them in Postman or a similar tool), for example
- [get jobs](rest/job/get-jobs.http)
- [get companies](rest/company/get-companies.http)
- [post-new-job](rest/job/post-new-job.http)

Take a look in de [SigNoz UI](http://localhost:3301)

- Click on the Services tab. Can you see your application?
- Click on the application name. Can you see the metrics?
- Click on Traces tab.
- Find the operations you just called (this may take some time)
- Click on the operation and open the spans

### Hints

See https://opentelemetry.io/docs/instrumentation/java/automatic/ or the presentation for more information

## Exercise 2 - Manual instrumentation

Stop the application and add the Micrometer dependencies for Open Telemetry to the pom file.

Also set the sampling probability to 1.0 as Spring Boot uses a sampling probability of 0.1 (1 sample in every 10 calls) by default.

Also add the application name, spanid and traceid to the log pattern

Start the application, either in the IDE or command line using

```
./mvnw spring-boot:run
```

Run a few queries and take a look in the SigNoz UI

Can you spot the difference with the previous traces?

Can you also see the application name, spanid and traceid in the application log?

### Hints

See https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#actuator.metrics.micrometer-observation,  
https://micrometer.io/docs/tracing or the presentation for more information for adding the MicroMeter dependencies

See https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#actuator.metrics.micrometer-observation on how to add the traceId and spanId to the log pattern
and how to set the sampling probability to 1.0.

## Exercise 3 - Adding database instrumentation

OpenTelemetry also supports some out-of-the-box instrumentation for standard libraries like JDBC,
so now we're going to add the Micrometer JDBC dependency to our pom file.

Stop the application and add the JDBC Micrometer dependencies for Open Telemetry to the pom file.

Start the application, either in the IDE or command line and run a few queries.

Can you see the JDBC calls in the traces?

To make it easier to get the right trace, you can copy the trace ID from the application log and use it to filter the trace.

### Hints

See https://jdbc-observations.github.io/datasource-micrometer/docs/current/docs/html/ on how to add the MicroMeter JDBC for Spring Boot library to the dependencies


_**For the next exercises we have to switch to another branch**_

Stop the application and checkout the multi-app branch

```
git checkout multi-app
```

## Exercise 4 - Multi app dependencies

We have just added the Micrometer dependencies to our Spring Boot 3 app, but now we have multiple apps
- job-app (Spring Boot 3)
- company-app (Quarkus 3)
- project-app (Spring Boot 3)

The Spring Boot (MicroMeter) dependencies are already in place. Add the Quarkus OpenTelementry dependencies to the _company-app_ pom file.

Run all three applications either in the IDE or command line

### Job app

```
cd job-app
../mvnw clean package
../mvnw spring-boot:run
```

### Project app

```
cd project-app
../mvnw clean package
../mvnw spring-boot:run
```

### Company app

```
cd company-app
../mvnw clean package
../mvnw quarkus:dev
```

After all apps are started, run a few queries, like

- [get jobs](rest/job/get-jobs.http)
- [get job by id](rest/job/get-job-by-id.http)
- [post new job](rest/job/post-new-job.http)

Find the traces in the Signoz UI

Can you spot the difference between the Spring Boot and Quarkus application?

### Hints

See https://quarkus.io/guides/opentelemetry on how to add the quarkus OpenTelemetry and JDBC dependencies to the Company application

## Exercise 5 - Create your own span

Go to the [JobController](job-app/src/main/java/nl/craftsmen/brewery/job/controller/JobController.java) and create your own span in the `listJobs`-method
with, for example, `list jobs` as the span name.

Restart the JobApplication and fire a couple of [get jobs](rest/job/get-jobs.http) requests

Check in the SigNoz UI if you can see the newly created Span.

### Hints

See https://opentelemetry.io/docs/instrumentation/java/manual/#acquiring-a-tracer to get a Tracer and create a Span

## Exercise 6 - Add a child span

Go the [JobService](job-app/src/main/java/nl/craftsmen/brewery/job/controller/JobService.java) and create a child span, for example with the name `find all jobs`.

Restart the JobApplication and fire a couple of [get jobs](rest/job/get-jobs.http) requests

Check in the SigNoz UI if you can see the newly created Span.

### Hints

See https://opentelemetry.io/docs/instrumentation/java/manual/#create-nested-spans on how to create a child span

## Exercise 7 - Create a remote span

Go to the [ProjectController](project-app/src/main/java/nl/craftsmen/brewery/project/controller/ProjectController.java) (`getProject` method)
or [CompanyController](company-app/src/main/java/nl/craftsmen/brewery/company/controller/CompanyController.java) (`getDeveloperByName` method)
and create a remote span so that the span is part of the previous created child span.

For the Quarkus MicroProfile implementation of OpenTelemetry, you can use annotations.

### Hints

See https://opentelemetry.io/docs/instrumentation/java/manual/#create-nested-spans

You don't need to set the remoteContext of the spans, as this is down automatically by the Spring Boot RestTemplate. Retrieving of the context is also
automatically done in the OpenTelemetry implementations.

## Exercise 8 - Add attributes to the spans

The ProjectController and CompanyController spans miss some essential information at the moment, we don't know which names are retrieved.

Add the names as attributes to the current span of the ProjectController or CompanyController.

### Hints

See https://opentelemetry.io/docs/instrumentation/java/manual/#span-attributes on how to add attributes to a span

## Exercise 9 - Add events

You can also log important information to the OpenTelemetry dashboard. One means of doing so is with events.

Add an event and check the SigNoz UI if you can see the event after you have fired the `get-jobs` request.

You can, for example in the JobController, log when nothing is found, or when you got a result back.
Or in the ProjectController the name of the project or in the Company app the name of the developer you are retrieving.

For the Quarkus Company app you need to inject a CDI bean to add an event, you cannot use the annotations. So, for example,
add an event to the span in de CompanyService `findDeveloperByName` method.

### Hints

See https://opentelemetry.io/docs/instrumentation/java/manual/#create-spans-with-events on how to add events to a span.

## Exercise 10 - Adding baggage

You can also give some contextual information to another application, which isn't available there. That is called baggage.

Add some baggage (for example a userid) in the JobController and retrieve it in the ProjectController
and add it to the span attributes and see if it shows up in de SigNoz UI.

Can you also retrieve the baggage in the CompanyService?

### Hints

See https://github.com/open-telemetry/opentelemetry-java/discussions/4801 for an implementation how to add baggage and
https://stackoverflow.com/questions/76767269/quarkus-opentelemetry-baggage to retrieve the baggage.