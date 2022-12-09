#!/usr/bin/env groovy
def gv

pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        
        stage('run unit tests') {
            steps {
                script {
                    gv.runUnitTests()
                }
            }
        }
        
        stage('test with sonar') {
            steps {
                script {
                    gv.sonarTest()
                }
            }
        }
        
        stage('build app') {
            steps {
                script {
                    echo "building jar"
                    gv.buildJar()
                }
            }
        }

        stage('push to nexus') {
            steps {
                script {
                    echo "pushing jar to nexus"
                    gv.pushToNexus()
                }
            }
        }

        stage('build image') {
            steps {
                script {
                     echo "building image"
                    gv.buildImage()
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    echo 'deploying the application...'
                }
            }
        }

    }
}
