pipeline {
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
          //TODO julien
          //sh '(mvn sonar:sonar -Dsonar.projectKey=bull0n_springwater -Dsonar.organization=bull0n-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=0916c4e7bc3cf93e96f97c679a7cde097309b43d)'
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
      environment {
        registry = "jojoc4/dont-forget-to-sleep-jee"
        registryCredential = 'dockerhubjojoc4'
        dockerImage = ''
      }
      stage('DockerImageCreation') {
          steps{
            unstash "app"
            script {
              dockerImage = docker.build registry + ":$BUILD_NUMBER"
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

/* DEMO sample *//*
 pipeline {
    agent any
    stages {
        stage('Build') {
            steps{
                echo "Build"
            }
        }
        stage('Print all environnement'){
            steps{
                sh '(printenv)'
            }
        }
        stage('Test') {
            steps{
                echo "Build"
            }
        }
        stage('Deploy') {
            steps{
                echo "Build"
            }
        }
    }
}
*/