#!/bin/sh
#用户demo消费者脚本
#/u01/usercenter/srv/cloud-service-user-consumer/cloud-service-user-consumer-0.0.1-SNAPSHOT.jar

NAME=cloud-service-user-consumer
JAR=/u01/usercenter/srv/cloud-service-user-consumer/cloud-service-user-consumer-0.0.1-SNAPSHOT.jar
PORT=8263
ENV=--spring.profiles.active=qa
JAVA_OPTS="-Xms256M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/u01/usercenter/srv/cloud-service-user-consumer/heapdumpfile.hprof -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:/u01/usercenter/srv/cloud-service-user-consumer/gc.log"

case "$1" in
    start)
        echo "Starting $NAME... "

	if netstat -tnl | grep -q ${PORT};then
            echo "$NAME (pid `pidof $NAME`) already running."
            exit 1
        fi

        java -jar ${JAVA_OPTS} ${JAR} ${ENV} & 1>/dev/null 2>&1

        if [ "$?" != 0 ] ; then
            echo " Starting failed"
            exit 1
        else
            echo " Starting done"
	    exit 0
        fi
        ;;

    stop)
        echo "Stoping $NAME... "

	if ! netstat -tnl | grep -q ${PORT};then
            echo "$NAME is not running."
            exit 1
	else
	    ps aux | grep cloud-service-user |grep -v grep |awk '{print $2}' | xargs kill
	    echo "$NAME stop done."
	    exit 0
        fi	
        ;;
	
    restart)
	echo "restart $NAME... "
        if netstat -tnl | grep -q ${PORT};then
	    ps aux | grep cloud-service-user |grep -v grep |awk '{print $2}' | xargs kill
            echo "$NAME stop done."	
        fi
	java -jar ${JAVA_OPTS} ${JAR} ${ENV} &  1>/dev/null 2>&1
	if [ "$?" != 0 ] ; then
            echo " restart failed"
            exit 1
        else
            echo " restart done"
	    exit 0
       	fi
	;;
		
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
        ;;
	
esac
