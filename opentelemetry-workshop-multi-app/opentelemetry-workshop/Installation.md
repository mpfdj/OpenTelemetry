# Installation instructions

## Prerequisites

To participate in this workshop you will need

- Java 17 or higher
- a git client
- Docker or Podman or similar
- Your favourite IDE

It is recommended that you perform the following installation instructions at home.
## SigNoz

[SigNoz](https://signoz.io/) only works for Linux and MacOS, for Windows you need to [install Windows subsystem for Linux 2 (WSL2)](https://learn.microsoft.com/en-us/windows/wsl/install) and install SigNoz in the WSL shell. Choose Ubuntu 22.04 as the Linux distribution to use!

Don't forget to switch the WSL2 integration in Docker Desktop on!

### Linux/Mac/WSL2 shell

```
git clone -b main https://gitlab.com/Craftsmen/signoz.git
cd signoz/deploy
./install.sh
```
This will install and start a Docker instance of SigNoz

Now you can open the UI on

```
http://localhost:3301
```
> You will be asked to create an account. You can enter fake details, just remember them!
>
To stop this instance run in the `signoz/deploy` directory

```
docker-compose -f docker/clickhouse-setup/docker-compose.yaml down -v
```
To start it again, you can run in the `signoz/deploy` directory
```
docker-compose -f docker/clickhouse-setup/docker-compose.yaml up -d
```
If it is not possible to install SigNoz on your machine, we will have a running version available, which you can connect to. You will need to change the IP of the SigNoz instance from localhost to the provided IP-address.

## The IT Brewery application

The first assignments make use of a single Spring Boot 3 application

To clone and download all dependencies before the start of the workshop, run
```
git clone https://gitlab.com/craftsmen/opentelemetry-workshop.git
cd opentelemetry-workshop
./mvnw clean package
```
