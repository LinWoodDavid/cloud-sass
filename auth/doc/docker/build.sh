#构建镜像
docker build -f DockerFile -t auth:1.0 .;
#上传标记的镜像
docker push 192.168.15.1:5000/auth:1.0;
#标记此镜像为私有仓库的镜像
docker tag auth:1.0 192.168.15.1:5000/auth:1.0;
#上传标记的镜像到私有仓库
docker push 192.168.15.1:5000/auth:1.0;