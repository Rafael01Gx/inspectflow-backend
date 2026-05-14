################################################################################
# INSPECTFLOW — Estrutura de Diretórios e Instruções de Setup
# Observabilidade Completa
################################################################################

# ==============================================================================
# ESTRUTURA DE ARQUIVOS ESPERADA
# ==============================================================================
#
# inspectflow/                                  ← raiz do projeto
# │
# ├── docker-compose.yml                        ← Fase 6
# ├── Dockerfile                                ← (você já deve ter)
# │
# ├── logs/                                     ← gerado em runtime pela aplicação
# │   └── inspectflow.log                       ← JSON estruturado (logback)
# │
# ├── observability/
# │   ├── prometheus/
# │   │   └── prometheus.yml                    ← Fase 6
# │   │
# │   ├── loki/
# │   │   └── loki.yml                          ← Fase 6
# │   │
# │   ├── promtail/
# │   │   └── promtail-config.yml               ← Fase 5
# │   │
# │   └── grafana/
# │       ├── provisioning/
# │       │   ├── datasources/
# │       │   │   └── datasources.yml           ← Fase 6
# │       │   └── dashboards/
# │       │       └── dashboards.yml            ← Fase 6
# │       └── dashboards/
# │           └── inspectflow-dashboard.json    ← Fase 6
# │
# └── src/main/
#     ├── resources/
#     │   ├── application.yml                   ← Fase 2
#     │   └── logback-spring.xml                ← Fase 5
#     └── java/br/com/inspectflow/
#         └── infrastructure/
#             └── config/
#                 └── observability/
#                     ├── ObservabilityConfig.java          ← Fase 3
#                     ├── AsyncConfig.java                  ← Fase 3
#                     ├── SchedulingObservabilityConfig.java ← Fase 3
#                     ├── MinioObservabilityAdapter.java    ← Fase 3
#                     ├── MongoObservabilityConfig.java     ← Fase 3
#                     ├── ObservabilityHttpFilter.java      ← Fase 3
#                     ├── SecurityMdcFilter.java            ← Fase 5
#                     └── InspectionObservation.java        ← Fase 3

# ==============================================================================
# COMANDOS DE INICIALIZAÇÃO
# ==============================================================================

# 1. Criar diretório de logs (deve existir antes de subir os containers)
mkdir -p logs
mkdir -p observability/prometheus
mkdir -p observability/loki
mkdir -p observability/promtail
mkdir -p observability/grafana/provisioning/datasources
mkdir -p observability/grafana/provisioning/dashboards
mkdir -p observability/grafana/dashboards

# 2. Copiar arquivos de configuração para os diretórios corretos
cp prometheus.yml          observability/prometheus/
cp loki.yml                observability/loki/
cp promtail-config.yml     observability/promtail/
cp datasources.yml         observability/grafana/provisioning/datasources/
cp dashboards.yml          observability/grafana/provisioning/dashboards/
cp inspectflow-dashboard.json observability/grafana/dashboards/

# 3. Subir apenas a infraestrutura de observabilidade primeiro
docker compose up -d prometheus jaeger loki promtail grafana

# 4. Verificar se todos subiram saudáveis
docker compose ps

# 5. Subir a infraestrutura da aplicação
docker compose up -d postgres mongodb redis minio

# 6. Subir a aplicação
docker compose up -d inspectflow-app

# 7. Verificar logs da aplicação
docker compose logs -f inspectflow-app

# ==============================================================================
# VERIFICAÇÃO DE SAÚDE
# ==============================================================================

# Actuator — health check
curl http://localhost:8090/actuator/health

# Actuator — métricas Prometheus
curl http://localhost:8090/actuator/prometheus | grep inspection

# Prometheus — verificar targets
# http://localhost:9090/targets  → inspectflow deve estar UP

# Jaeger UI
# http://localhost:16686

# Grafana
# http://localhost:3000  (admin/admin)
# Dashboard: InspectFlow — Observabilidade

# ==============================================================================
# QUERIES ÚTEIS — LogQL (Loki/Grafana)
# ==============================================================================

# Todos os erros da aplicação
# {application="inspectflow"} | json | level="ERROR"

# Logs de uma requisição específica pelo traceId
# {application="inspectflow"} | json | traceId="SEU_TRACE_ID"

# Logs de um usuário específico
# {application="inspectflow"} | json | userId="user-123"

# Slow queries MongoDB
# {application="inspectflow"} | json | message=~".*SLOW QUERY.*"

# Logs de jobs agendados
# {application="inspectflow"} | json | logger=~".*Scheduler.*"

# ==============================================================================
# QUERIES ÚTEIS — PromQL (Prometheus/Grafana)
# ==============================================================================

# Taxa de requisições por endpoint (últimos 5min)
# sum(rate(http_server_requests_seconds_count{application="inspectflow"}[5m])) by (uri, method)

# Latência p99 geral
# histogram_quantile(0.99, sum(rate(http_server_requests_seconds_bucket{application="inspectflow"}[5m])) by (le))

# Conexões ativas no pool PostgreSQL
# hikaricp_connections_active{application="inspectflow"}

# Uso de heap JVM
# jvm_memory_used_bytes{application="inspectflow", area="heap"}

# Inspeções criadas na última hora
# sum(increase(inspection_created_total{application="inspectflow"}[1h]))

# Taxa de erros HTTP
# sum(rate(http_server_requests_seconds_count{application="inspectflow", status=~"5.."}[5m]))