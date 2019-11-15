#!/usr/bin/env bash

# 接收Jenkins动态传过来的参数
appName=$1
env=$2

# 输出信息
echo 'the Environmental is:'$env

# 拼接目录
appPath='/data/bin/'$appName

# 过滤应用实例的进程Id
mpid=`ps -eaf|grep java|grep $appName|awk '{print $2}'`

# 找到进程Id就强制杀掉
if [ ! $mpid = '' ];then
   echo 'kill the '$appName
   kill -9 $mpid
fi

# ==================打印信息====================
echo 'the app run path is : ' $appPath


# 拷贝公共的jar到指定的目录，然后指定入口Springboot的Java类与时区与启动环境进行启动服务， >/dev/null  & 把输出信息放入到Linux的垃圾桶(黑洞) & 代表永远指定后台运行
exec java -cp  $appPath/$appName-1.0.0-SNAPSHOT.jar  com.flong.kotlin.Application -Duser.timezone=GMT+8 --spring.profiles.active=$env >/dev/null  &