git clone https://gitlab.com/craftsmen/opentelemetry-workshop.git
git branch --all
see solution branches


# Install SigNoz Using Docker Compose
https://signoz.io/docs/install/docker/
git clone -b main https://github.com/SigNoz/signoz.git
run wsl as Administrator !! IMPORTANT !!
cd "/mnt/c/Users/TO11RC/OneDrive - ING/miel/workspace/OpenTelemetry/signoz.io/signoz/deploy"
stop all running containers first
docker compose -f docker/docker-compose.yaml up -d --remove-orphans
docker ps


# Repair IDE
> File > Repair IDE > Rescan Project Indexes
https://stackoverflow.com/questions/5816419/intellij-does-not-show-project-folders


# Disable Docker Scout
> Docker Desktop, Settings > General settings > deselect SBOM indexing
https://github.com/docker/roadmap/issues/606


# Signup
http://localhost:3301
u: admin
p: Welkom01


# Spring-Boot auto-configuration for OpenTelemetry
OpenTelemetry Spring Boot Starter: opentelemetry-spring-boot-starter provides an out-of-box integration that requires no extra coding to enable OpenTelemetry observability capacity.
By default, the exporter sends data to http://localhost:4317.
To override the defaults you can use application.properties.

https://opentelemetry.io/docs/zero-code/java/spring-boot-starter/out-of-the-box-instrumentation/
https://signoz.io/blog/opentelemetry-spring-boot/
https://medium.com/@yangli136/how-opentelemetry-is-integrated-with-spring-boot-application-7e309efc0011