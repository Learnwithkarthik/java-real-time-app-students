Java issue troubleshooting:
 
 export JAVA_HOME="$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")"



export PATH="$JAVA_HOME/bin:$PATH"


 mvn clean package
    
   ls -lh target/*.jar
   
    java -jar target/campus-enrollment-api-1.0.0-SNAPSHOT.jar
   
     
     docker --version
     
     docker compose version
     
     free -h
     
     sudo apt update
     
     sudo apt install -y docker.io docker-compose-v2
     
     sudo systemctl enable --now docker
     
     sudo docker --version
     
     sudo docker compose version
     
     sudo docker ps
     
     sudo docker run --rm hello-world
     
     ls
     
     cd infra/
     
     ls
     
     cp .env.example .env
     
     pwd
     
     ls -la
     
     printf 'SONAR_DB_PASSWORD=%s\n' "$(openssl rand -hex 24)" > .env
     
     ls -la .env
     
     sudo docker compose up -d nexus
     
     sudo docker ps
     
     sudo docker exec campus-nexus cat /nexus-data/admin.password
     
     printf 'SONAR_DB_PASSWORD=%s\n' "$(openssl rand -hex 24)" > .env
     
     sudo docker compose up -d nexus
     
     cd /home/laborant/java-real-time-app-students/campus-enrollment-maven-nexus-sonarqube/campus-enrollment-maven-nexus-sonarqube
     
     ls -l pom.xml docs/settings-nexus.xml
     
     mvn -version


     printf 'NEXUS_USER configured: %s\n' "${NEXUS_USER:+yes}"
    
     printf 'NEXUS_PASSWORD configured: %s\n' "${NEXUS_PASSWORD:+yes}"
     
     export NEXUS_USER=admin
     
     read -rsp "Enter your current Nexus UI password: " NEXUS_PASSWORD
     
     echo
    
    export NEXUS_PASSWORD
    
     curl -u admin -sS -o /dev/null   -w "Nexus HTTP Status: %{http_code}\n"   http://localhost:8081/service/rest/v1/repositories
     
     mvn -s docs/settings-nexus.xml clean deploy

   
