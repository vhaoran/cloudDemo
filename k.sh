
APP_FULL='spring-boot'


for ((i = 0; i < 10; i++)); do
    echo $i
tpid=`ps -ef|grep $APP_FULL|grep -v grep|grep -v kill|awk '{print $2}'`
    if [[ $tpid ]]; then
        echo 'Kill Process!'$tpid
        kill -9 $tpid
    fi
done

echo ""
echo "----------------"
jps -m

