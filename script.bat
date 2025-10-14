@echo off
echo Stopping wigelltravel
docker stop wigelltravel
echo Deleting container wigelltravel
docker rm wigelltravel
echo Deleting image wigelltravelimage
docker rmi wigelltravelimage
echo Running mvn package
call mvn clean package -DskipTests
echo Creating image wigelltravelimage
docker build -t wigelltravelimage .
echo Creating and running container wigelltravel
docker run -d -p 8585:8585 --name wigelltravel --network services-network wigelltravelimage
echo Done!