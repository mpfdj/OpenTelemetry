git clone https://gitlab.com/craftsmen/opentelemetry-workshop.git
git branch --all
see solution branches


# Install SigNoz Using Docker Compose
https://signoz.io/docs/install/docker/
git clone -b main https://github.com/SigNoz/signoz.git
open a wsl prompt as Administrator !! IMPORTANT !!
cd "/mnt/c/Users/TO11RC/OneDrive - ING/miel/workspace/OpenTelemetry/signoz.io/signoz/deploy"
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
