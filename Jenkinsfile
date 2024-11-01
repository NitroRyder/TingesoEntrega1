pipeline {
    agent any
    tools{
        maven 'maven_3_9_1'
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/NitroRyder/TingesoEntrega1']])
                bat 'mvn clean package'
            }
        }

        stage('Tests') {
            steps {
                // Run Maven 'test' phase. It compiles the test sources and runs the unit tests
                bat 'mvn test' // Use 'bat' for Windows agents or 'sh' for Unix/Linux agents
            }
        }

        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t nitroryder/entrega1:latest .'
                }
            }
        }
        stage('Push image to Docker Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dhpswid', variable: 'dhpsw')]) {
                        bat 'docker login -u nitroryder -p %dhpsw%'
                   }
                   bat 'docker push nitroryder/entrega1:latest'
                }
            }
        }
    }
}