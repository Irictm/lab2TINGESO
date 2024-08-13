Para deplegar microservicios localmente:

cambiar en application properties de cada microservicio:

spring.config.import=configserver:http://localhost:8888

El resto de archivos van en cofig-data