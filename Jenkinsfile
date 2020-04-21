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
          sh 'export MDP_SMTP=exemple'
          sh 'cd kaude/dfts && mvn clean package'
          stash name: "app", includes: "**"
        }
      }
      stage('UnitTest') {
        agent {
          docker {
            image 'maven:3-alpine'
          }
        }
        steps {
          unstash "app"
          sh 'export MDP_SMTP=exemple'
          sh 'cd kaude/dfts && mvn clean test'
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
          sh 'cd kaude/dfts && mvn sonar:sonar -Dsonar.projectKey=julien331_dont-forget-to-sleep-jee -Dsonar.organization=spring-qdl-ekipe -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=51c596b64d3f51e4a40063d0e90369992df408fb'
        }
      }
      stage('IntegrationTest'){
        agent{
          docker{
            image 'katalonstudio/katalon'
            args '-u root'
          }
        }
        steps {
          /*sh 'katalon-execute.sh -browserType="Firefox" -retry=0 -statusDelay=15 -testSuitePath="kaude/dfts-katalon-tests/TestCases/Connection"'
          sh 'katalon-execute.sh -browserType="Firefox" -retry=0 -statusDelay=15 -testSuitePath="kaude/dfts-katalon-tests/TestCases/crud"'*/
          sh 'echo "license problem"'
        }

      }
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
            /*sh "docker rmi $registry:$BUILD_NUMBER"*/
            sh "docker rmi $registry:latest"
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
