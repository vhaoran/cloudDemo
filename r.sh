cd ~/work_cloud/cloudDemo/eureka-server
mvn spring-boot:run >a.txt &
cd ~/work_cloud/cloudDemo/temp-service
mvn spring-boot:run >a.txt &
cd ~/work_cloud/cloudDemo/zuul-server
mvn spring-boot:run >a.txt &


echo ""
echo "-----------"
echo "zuul  :8799"
echo "eureka:8761"
echo "temp  :8762"

echo "--------------"
jps -m |sort 

