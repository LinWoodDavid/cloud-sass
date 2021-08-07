# 一、搭建2m2s集群
#创建2个master
#nameserver1 docker create -p 9876:9876 --name rmqserver01 \ -e "JAVA_OPT_EXT=-server -Xms128m -Xmx128m -Xmn128m" \ -e "JAVA_OPTS=-Duser.home=/opt" \ -v /haoke/rmq/rmqserver01/logs:/opt/logs \ -v /haoke/rmq/rmqserver01/store:/opt/store \ foxiswho/rocketmq:server-4.3.2