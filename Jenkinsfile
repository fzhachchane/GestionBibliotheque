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
        stage('Code Coverage') {
            steps {
                // Generate JaCoCo coverage report
                sh "${MAVEN_HOME}/bin/mvn verify"
            }
        }
        stage('Quality Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '${MAVEN_HOME}/bin/mvn sonar:sonar \
                                            -Dsonar.projectKey=GestionBibliotheque \
                                            -Dsonar.host.url=http://localhost:9000 \
                                            -Dsonar.login=squ_574135a1b9f1038377ca557d2740feda70228daa'
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
