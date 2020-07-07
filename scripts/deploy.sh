#!/usr/bin/env bash
# we say that it is bash scrypt

mvn clean package
#clean – the project is clean of all artifacts that came from previous compilations
#compile – the project is compiled into /target directory of project root
# install – packaged archive is copied into local maven repository (could in your user's home directory under /.m2)
# test – unit tests are run package – compiled sources are packaged into archive (JAR by default)

echo 'Copy files ...'

scp -i ~/.ssh/MySSHKeyPair.pem target/sweater-1.0-SNAPSHOT.jar anton@3.120.33.162:/home/anton/
# secure copying with help of ssh

echo 'Restart server ...'
#source ~/.bashrc
#This method is reload system environment. Then shell script can use java command.

ssh -i ~/.ssh/MySSHKeyPair.pem anton@3.120.33.162 << EOF
pgrep java | xargs kill -9
nohup java -jar sweater-1.0-SNAPSHOT.jar > log.txt &
EOF
#pgrep: get id of process *****; xargs: transmit process to ..
#nohup + &: start the process in the background and doesn't allow it to break after session end

echo 'Bye'