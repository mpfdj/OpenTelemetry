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


# Units of work
- trace 
  - span
    - attribute
    - event
    - baggage


# TODO: Purge SigNoz database (clickhouse container)
https://signoz.io/docs/operate/clickhouse/connect-to-clickhouse/#for-docker-users
https://community-chat.signoz.io/t/9691240/support-how-to-delete-all-logs-and-traces-for-the-docker

$ Install xargs on Alpine
$ apk update && apk add findutils

$ Open an interactive Docker session with the ClickHouse container and start the clickhouse client.
$ clickhouse-client

$ Or run a sql script file from the command line
$ clickhouse-client --multiquery < purge.sql




// Run below clickhouse queries to truncate (got this from deepseek.com)
Identify the Tables:
- Signoz stores data in specific tables in ClickHouse. The main tables include:
  - signoz_traces.signoz_index_v2 (for trace data)
  - signoz_traces.signoz_spans (for span data)
  - signoz_metrics.* (for metrics data)
  - signoz_logs.* (for logs data)
  
- You can list the tables using:
  sql> SHOW TABLES FROM signoz_traces;
  sql> SHOW TABLES FROM signoz_metrics;
  sql> SHOW TABLES FROM signoz_logs;

- Run the below queries from the command line; All these tables can be truncated to clean-up Signoz.
clickhouse-client -q "SHOW TABLES FROM signoz_traces;"
clickhouse-client -q "SHOW TABLES FROM signoz_metrics;"
clickhouse-client -q "SHOW TABLES FROM signoz_logs;"


# Generate sql purge scripts
clickhouse-client -q "SHOW TABLES FROM signoz_traces;" | xargs --max-lines=1 -I {} echo "TRUNCATE TABLE signoz_traces.{};" | tee /tmp/purge_signoz_traces.sql
clickhouse-client -q "SHOW TABLES FROM signoz_metrics;" | xargs --max-lines=1 -I {} echo "TRUNCATE TABLE signoz_metrics.{};" | tee /tmp/purge_signoz_metrics.sql
clickhouse-client -q "SHOW TABLES FROM signoz_logs;" | xargs --max-lines=1 -I {} echo "TRUNCATE TABLE signoz_logs.{};" | tee /tmp/purge_signoz_logs.sql


# Run sql purge scripts
clickhouse-client --multiquery < /tmp/purge_signoz_traces.sql
clickhouse-client --multiquery < /tmp/purge_signoz_metrics.sql
clickhouse-client --multiquery < /tmp/purge_signoz_logs.sql


# Another example, where all purge queries are put into 1 file (purge.sql)
clickhouse-client --multiquery < purge.sql

-- signoz_traces
TRUNCATE TABLE signoz_traces.dependency_graph_minutes_db_calls_mv_v2;
TRUNCATE TABLE signoz_traces.dependency_graph_minutes_messaging_calls_mv_v2;
TRUNCATE TABLE signoz_traces.dependency_graph_minutes_service_calls_mv_v2;
TRUNCATE TABLE signoz_traces.dependency_graph_minutes_v2;
TRUNCATE TABLE signoz_traces.distributed_dependency_graph_minutes_v2;
TRUNCATE TABLE signoz_traces.distributed_schema_migrations_v2;
TRUNCATE TABLE signoz_traces.distributed_signoz_error_index_v2;
TRUNCATE TABLE signoz_traces.distributed_signoz_index_v2;
TRUNCATE TABLE signoz_traces.distributed_signoz_index_v3;
TRUNCATE TABLE signoz_traces.distributed_signoz_spans;
TRUNCATE TABLE signoz_traces.distributed_span_attributes;
TRUNCATE TABLE signoz_traces.distributed_span_attributes_keys;
TRUNCATE TABLE signoz_traces.distributed_tag_attributes_v2;
TRUNCATE TABLE signoz_traces.distributed_top_level_operations;
TRUNCATE TABLE signoz_traces.distributed_trace_summary;
TRUNCATE TABLE signoz_traces.distributed_traces_v3_resource;
TRUNCATE TABLE signoz_traces.distributed_usage;
TRUNCATE TABLE signoz_traces.distributed_usage_explorer;
TRUNCATE TABLE signoz_traces.durationSort;
TRUNCATE TABLE signoz_traces.root_operations;
TRUNCATE TABLE signoz_traces.schema_migrations_v2;
TRUNCATE TABLE signoz_traces.signoz_error_index_v2;
TRUNCATE TABLE signoz_traces.signoz_index_v2;
TRUNCATE TABLE signoz_traces.signoz_index_v3;
TRUNCATE TABLE signoz_traces.signoz_spans;
TRUNCATE TABLE signoz_traces.span_attributes;
TRUNCATE TABLE signoz_traces.span_attributes_keys;
TRUNCATE TABLE signoz_traces.sub_root_operations;
TRUNCATE TABLE signoz_traces.tag_attributes_v2;
TRUNCATE TABLE signoz_traces.top_level_operations;
TRUNCATE TABLE signoz_traces.trace_summary;
TRUNCATE TABLE signoz_traces.trace_summary_mv;
TRUNCATE TABLE signoz_traces.traces_v3_resource;
TRUNCATE TABLE signoz_traces.usage;
TRUNCATE TABLE signoz_traces.usage_explorer;
TRUNCATE TABLE signoz_traces.usage_explorer_mv;

-- signoz_metrics
TRUNCATE TABLE signoz_metrics.distributed_exp_hist;
TRUNCATE TABLE signoz_metrics.distributed_metadata;
TRUNCATE TABLE signoz_metrics.distributed_samples_v2;
TRUNCATE TABLE signoz_metrics.distributed_samples_v4;
TRUNCATE TABLE signoz_metrics.distributed_samples_v4_agg_30m;
TRUNCATE TABLE signoz_metrics.distributed_samples_v4_agg_5m;
TRUNCATE TABLE signoz_metrics.distributed_schema_migrations_v2;
TRUNCATE TABLE signoz_metrics.distributed_time_series_v2;
TRUNCATE TABLE signoz_metrics.distributed_time_series_v4;
TRUNCATE TABLE signoz_metrics.distributed_time_series_v4_1day;
TRUNCATE TABLE signoz_metrics.distributed_time_series_v4_1week;
TRUNCATE TABLE signoz_metrics.distributed_time_series_v4_6hrs;
TRUNCATE TABLE signoz_metrics.distributed_usage;
TRUNCATE TABLE signoz_metrics.exp_hist;
TRUNCATE TABLE signoz_metrics.metadata;
TRUNCATE TABLE signoz_metrics.samples_v2;
TRUNCATE TABLE signoz_metrics.samples_v4;
TRUNCATE TABLE signoz_metrics.samples_v4_agg_30m;
TRUNCATE TABLE signoz_metrics.samples_v4_agg_30m_mv;
TRUNCATE TABLE signoz_metrics.samples_v4_agg_5m;
TRUNCATE TABLE signoz_metrics.samples_v4_agg_5m_mv;
TRUNCATE TABLE signoz_metrics.schema_migrations_v2;
TRUNCATE TABLE signoz_metrics.time_series_v2;
TRUNCATE TABLE signoz_metrics.time_series_v4;
TRUNCATE TABLE signoz_metrics.time_series_v4_1day;
TRUNCATE TABLE signoz_metrics.time_series_v4_1day_mv;
TRUNCATE TABLE signoz_metrics.time_series_v4_1day_mv_separate_attrs;
TRUNCATE TABLE signoz_metrics.time_series_v4_1week;
TRUNCATE TABLE signoz_metrics.time_series_v4_1week_mv;
TRUNCATE TABLE signoz_metrics.time_series_v4_1week_mv_separate_attrs;
TRUNCATE TABLE signoz_metrics.time_series_v4_6hrs;
TRUNCATE TABLE signoz_metrics.time_series_v4_6hrs_mv;
TRUNCATE TABLE signoz_metrics.time_series_v4_6hrs_mv_separate_attrs;
TRUNCATE TABLE signoz_metrics.usage;

-- signoz_logs
TRUNCATE TABLE signoz_logs.distributed_logs;
TRUNCATE TABLE signoz_logs.distributed_logs_attribute_keys;
TRUNCATE TABLE signoz_logs.distributed_logs_resource_keys;
TRUNCATE TABLE signoz_logs.distributed_logs_v2;
TRUNCATE TABLE signoz_logs.distributed_logs_v2_resource;
TRUNCATE TABLE signoz_logs.distributed_schema_migrations_v2;
TRUNCATE TABLE signoz_logs.distributed_tag_attributes;
TRUNCATE TABLE signoz_logs.distributed_tag_attributes_v2;
TRUNCATE TABLE signoz_logs.distributed_usage;
TRUNCATE TABLE signoz_logs.logs;
TRUNCATE TABLE signoz_logs.logs_attribute_keys;
TRUNCATE TABLE signoz_logs.logs_resource_keys;
TRUNCATE TABLE signoz_logs.logs_v2;
TRUNCATE TABLE signoz_logs.logs_v2_resource;
TRUNCATE TABLE signoz_logs.schema_migrations_v2;
TRUNCATE TABLE signoz_logs.tag_attributes;
TRUNCATE TABLE signoz_logs.tag_attributes_v2;
TRUNCATE TABLE signoz_logs.usage;