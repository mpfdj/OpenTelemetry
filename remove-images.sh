#!/bin/bash
docker images | grep -e bitnami/zookeeper \
-e clickhouse/clickhouse-server \
-e gliderlabs/logspout \
-e signoz/alertmanager \
-e signoz/frontend \
-e signoz/query-service \
-e signoz/signoz-otel-collector \
-e signoz/signoz-schema-migrator \
| awk '{print $3}' | xargs docker rmi

docker images
