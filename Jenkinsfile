pipeline {
    agent any

    environment {
        imagename = "jongdo737/carraway"
        registryCredential = 'docker-hub'
        dockerImage = ''
    }

    stages {
        stage('Prepare') {
          steps {
            echo 'Clonning Repository'
            git url: 'git@github.com:Carry-A-Way/Career-Server.git',
              branch: 'main',
              credentialsId: 'github'
            }
            post {
             success {
               echo 'Successfully Cloned Repository'
             }
           	 failure {
               error 'This pipeline stops here...'
             }
          }
        }

        stage('Bulid Gradle') {
          steps {
            echo 'Bulid Gradle'
            dir('.'){
                sh './gradlew build -x test'
                sh './gradlew clean build'
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }
        stage('Bulid Docker') {
          steps {
            echo 'Bulid Docker'

            script {
                dockerImage = docker.build imagename
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        stage('Push Docker') {
          steps {
            echo 'Push Docker'
            script {
                docker.withRegistry( '', registryCredential) {
                    dockerImage.push()
                }
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        stage('Docker Run') {
            steps {
                echo 'Pull Docker Image & Docker Image Run'
                sshagent (credentials: ['ssh']) {
                    sh 'hostname'
                    sh 'chmod 400  ~/.ssh/id_rsa'
                    sh "ssh -o StrictHostKeyChecking=no root@27.96.135.215 'docker pull jongdo737/carraway'"
                    sh "ssh -o StrictHostKeyChecking=no root@27.96.135.215 'docker ps -q --filter name=carry | grep -q . && docker rm -f \$(docker ps -aq --filter name=carry); docker run -d --name carry -p 8080:8080 jongdo737/carraway'"

                }
            }
        }
    }
	post {
        success {
            discordSend description: "알림테스트",
            footer: "테스트 빌드가 성공했습니다.",
            link: env.BUILD_URL, result: currentBuild.currentResult,
            title: "테스트 젠킨스 job",
            webhookURL: "https://discord.com/api/webhooks/1176830413927366696/SWHvUY1sEE4EFKQPf33gJiLClpZz-diK9mOn6OUF0_EFjLTYq2D2Mv8U7HpBVHToQdCT"
        }
        failure {
            discordSend description: "알림테스트",
            footer: "테스트 빌드가 실패했습니다.",
            link: env.BUILD_URL, result: currentBuild.currentResult,
            title: "테스트 젠킨스 job",
            webhookURL: "https://discord.com/api/webhooks/1176830413927366696/SWHvUY1sEE4EFKQPf33gJiLClpZz-diK9mOn6OUF0_EFjLTYq2D2Mv8U7HpBVHToQdCT"
        }
    }
}