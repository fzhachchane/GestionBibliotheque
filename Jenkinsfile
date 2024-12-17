pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/fzhachchane/GestionBibliotheque.git'
            }
        }
        stage('Build') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn test'
            }
        }
        stage('Quality Analysis') {
            steps {
                try {
                    withSonarQubeEnv('SonarQube') {
                        sh '${MAVEN_HOME}/bin/mvn sonar:sonar'
                    }
                } catch (Exception e) {
                    echo "SonarQube analysis failed: ${e.getMessage()}"
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Déploiement simulé réussi'
            }
        }
    }
    post {
        success {
            emailext to: 'f.z.hachchane@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.'
        }
        failure {
            emailext to: 'f.z.hachchane@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.'
        }
    }
}
