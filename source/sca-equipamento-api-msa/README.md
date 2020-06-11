# sca-equipamento-api-msa
    
    mvn quarkus:dev -Ddebug=5006 \
    -Djvm.args="-DJAEGER_SERVICE_NAME=sca-equipamento-api-msa -DJAEGER_SAMPLER_TYPE=const -DJAEGER_SAMPLER_PARAM=1"