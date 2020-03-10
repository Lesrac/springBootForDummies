pipeline {
  agent any
  environment {
    MAIN_BRANCH = 'master'
    BUILD_NAME = 'springForDummies'
  }
  tools {
    maven 'Maven 3.6.3'
    jdk 'jdk8'
  }
  stages {
    stage ('Initialize') {
        steps {
            sh '''
                echo "PATH = ${PATH}"
                echo "M2_HOME = ${M2_HOME}"
            '''
            sh 'mvn clean compile'
        }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
      post {
          success {
              junit 'target/surefire-reports/**/*.xml'
          }
      }
    }
    stage('Build') {
      steps {
          sh 'mvn install'
      }
    }
  }
}