/* pipeline {
    agent any
    environment {
        SPRING_DATASOURCE_URL='jdbc:mysql://157.26.83.80:3306/spring_db_2020?useSSL=false'
        SPRING_DATASOURCE_USERNAME  = credentials('SPRING_DATASOURCE_USERNAME')
        SPRING_DATASOURCE_PASSWORD = credentials('SPRING_DATASOURCE_PASSWORD')
        JDC_ENV_TEST = credentials('JDC_ENV_TEST')
    }
    stages {
        stage('Echo Sample') {
            steps{
                echo "ECHO SAMPLE"
                sh '(printenv)'
            }
        }
        stage('Build') {
            agent {
              docker {
               image 'maven:3.6.3-jdk-11-slim'
              }
            }
            steps {
			sh '(cd ./SpringTestDemo/; mvn clean package)'
		stash name: "app", includes: "**"
            }
        }
	stage('QualityTest') {
            agent {
              docker {
               image 'maven:3.6.3-jdk-11-slim'
              }
            }
            steps {
		    unstash "app"
			sh '(cd ./SpringTestDemo/; mvn clean test)'
		    sh '(cd ./SpringTestDemo/; mvn sonar:sonar)'
	    }
        }
        stage('IntegrationTest'){
		agent{
			docker{
				image 'lucienmoor/katalon-for-jenkins:latest'
				args '-p 8888:8080'
			}
		}
		   steps {
			unstash "app"
			sh 'java -jar ./SpringTestDemo/target/SpringTestDemo-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &'
			sh 'sleep 30'
			sh 'chmod +x ./runTest.sh'
			sh './runTest.sh'
			cleanWs()
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
 */

/* DEMO sample */
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
