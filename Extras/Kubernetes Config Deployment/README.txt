Para deplegar microservicios en Kubernetes:

cambiar en application.properties de cada microservicio:

spring.config.import=configserver:http://config-server-service:8888

El resto de archivos van en config-data