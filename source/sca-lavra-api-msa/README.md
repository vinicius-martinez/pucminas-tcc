# sca-lavra-api-msa project
    
    mvn quarkus:dev -Ddebug=5007 \
    -Djvm.args="-DJAEGER_SERVICE_NAME=sca-lavra-api-msa -DJAEGER_SAMPLER_TYPE=const -DJAEGER_SAMPLER_PARAM=1"