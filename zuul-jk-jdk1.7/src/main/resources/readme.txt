启动命令start.sh：
java -Dfile.encoding=utf-8 -jar 
-Dspring.profiles.active=dev 
-Dserver.port=8080 
zuul-1.0.jar




-Dspring.profiles.active=test

后台启动：
nohup ./start.sh  > eureka.log 2>&1 &