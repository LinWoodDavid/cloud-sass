#拉取镜像
docker pull docker.elastic.co/kibana/kibana:$1;
#标记此镜像为私有仓库的镜像
docker tag docker.elastic.co/kibana/kibana:$1 192.168.15.1:5000/elk-kibana:$1;
#上传标记的镜像到私有仓库
docker push 192.168.15.1:5000/elk-kibana:$1;