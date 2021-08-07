#拉取镜像
#docker pull elasticsearch:$1;
docker pull docker.elastic.co/elasticsearch/elasticsearch:$1;
#下载ik分词器插件
wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v$1/elasticsearch-analysis-ik-$1.zip;
# 解压(如果提示没有unzip命令则先执行：`yum install -y unzip`，再执行下载命令)
unzip elasticsearch-analysis-ik-$1.zip -d elasticsearch-analysis-ik;
#构建镜像
docker build -f DockerFile -t elk-elasticsearch-ik:$1 --build-arg ELK_VERSION=$1  .;
#上传标记的镜像
docker push 192.168.15.1:5000/elk-elasticsearch-ik:$1;
#标记此镜像为私有仓库的镜像
docker tag elk-elasticsearch-ik:$1 192.168.15.1:5000/elk-elasticsearch-ik:$1;
#上传标记的镜像到私有仓库
docker push 192.168.15.1:5000/elk-elasticsearch-ik:$1;