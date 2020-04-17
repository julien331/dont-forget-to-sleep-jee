pipeline {
  environment {
    registry = "jojoc4/dont-forget-to-sleep-jee"
    registryCredential = 'dockerhubjojoc4'
    dockerImage = ''
  }
  agent any
  stages {
      stage('Build') {
        agent {
          docker {
            image 'maven:3-alpine'
          }
        }
        steps {
          sh 'cd kaude/dfts && mvn clean package'
          stash name: "app", includes: "**"
        }
      }
      stage('QualityTest') {
        agent {
          docker {
            image 'maven:3-alpine'
          }
        }
        steps {
          unstash "app"
          sh 'cd kaude/dfts && mvn clean test'
          sh 'cd kaude/dfts && mvn sonar:sonar -Dsonar.projectKey=julien331_dont-forget-to-sleep-jee -Dsonar.organization=spring-qdl-ekipe -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=51c596b64d3f51e4a40063d0e90369992df408fb'
        }
      }
      /*stage('IntegrationTest'){
        agent{
          docker{
            image 'lucienmoor/katalon-for-jenkins:latest'
            args '-p 8888:8080'
          }
        }
        steps {
          unstash "app"
          sh 'java -jar ./target/dfts-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &'
          sh 'sleep 30'
          sh 'chmod +x ./runTest.sh'
          sh './runTest.sh'

          cleanWs()
        }

      }*/
      stage('DockerImageCreation') {
          steps{
            unstash "app"
            script {
              /*dockerImage = docker.build registry + ":$BUILD_NUMBER"*/
              dockerImage = docker.build registry + ":latest"
              docker.withRegistry( '', registryCredential ) {
                dockerImage.push()
              }
            }
            sh "docker rmi $registry:$BUILD_NUMBER"
          }
      }
    }
    post {
      always {
        echo 'always clean up'
        deleteDir()
      }
    }
  }
