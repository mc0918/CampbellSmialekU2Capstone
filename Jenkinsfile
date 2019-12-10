pipeline {
    agent any

    stages {

        stage('build') {
            steps {
              bat '''
                 cd config-server
                 ./mvnw -DskipTests clean compile
                 cd eureka-server
                 ./mvnw -DskipTests clean compile
                 cd level-up-queue-consumer
                 ./mvnw -DskipTests clean compile
                 cd customer-service
                 ./mvnw -DskipTests clean compile
                 cd invoice-service
                 ./mvnw -DskipTests clean compile
                 cd level-up-service
                 ./mvnw -DskipTests clean compile
                 cd product-service
                 ./mvnw -DskipTests clean compile
                 cd admin-api
                 ./mvnw -DskipTests clean compile
                 cd retail-api
                 ./mvnw -DskipTests clean compile
              '''
            }
        }

        stage('test') {
            steps {
              bat '''
                 cd config-server
                 ./mvnw test
                 cd eureka-server
                 ./mvnw test
                 cd level-up-queue-consumer
                 ./mvnw test
                 cd customer-service
                 ./mvnw test
                 cd invoice-service
                 ./mvnw test
                 cd level-up-service
                 ./mvnw test
                 cd product-service
                 ./mvnw test
                 cd admin-api
                 ./mvnw test
                 cd retail-api
                 ./mvnw test
              '''
            }
        }

        stage('deliver') {
            steps {
              bat '''
                 cd config-server
                 ./mvnw -DskipTests install
                 cd eureka-server
                 ./mvnw -DskipTests install
                 cd level-up-queue-consumer
                 ./mvnw -DskipTests install
                 cd customer-service
                 ./mvnw -DskipTests install
                 cd invoice-service
                 ./mvnw -DskipTests install
                 cd level-up-service
                 ./mvnw -DskipTests install
                 cd product-service
                 ./mvnw -DskipTests install
                 cd admin-api
                 ./mvnw -DskipTests install
                 cd retail-api                     
                 ./mvnw -DskipTests install
              '''
            }
        }

    }
}
