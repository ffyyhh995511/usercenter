#!/bin/sh
#gateway启动脚本

NAME=cloud-service-gateway-zuul
JAR=/u01/usercenter/srv/cloud-service-gateway-zuul/cloud-service-gateway-zuul-0.0.1-SNAPSHOT.jar
ENV=--spring.profiles.active=qa
JAVA_OPTS="-Xms256M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/u01/usercenter/srv/cloud-service-gateway-zuul/heapdumpfile.hprof -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:/u01/usercenter/srv/cloud-service-gateway-zuul/gc.log"

case "$1" in
    start)
        echo "Starting $NAME... "

		if netstat -tnl | grep -q '8262';then
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

		if ! netstat -tnl | grep -q '8262';then
            echo "$NAME is not running."
            exit 1
		else
	    	ps aux | grep ${NAME} |grep -v grep |awk '{print $2}' | xargs kill
	    	echo "$NAME stop done."
	    	exit 0
        fi	
        ;;
	
    restart)
	echo "restart $NAME... "
        if netstat -tnl | grep -q '8262';then
	    ps aux | grep ${NAME} |grep -v grep |awk '{print $2}' | xargs kill
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
