# 一、ELK集群搭建
#### 官网地址
    https://www.elastic.co/guide/en/elastic-stack/current/installing-elastic-stack.html
## 1.1环境准备
#### 修改配置
    sysctl -w vm.max_map_count=262144
#### 查看结果：
    sysctl -a|grep vm.max_map_count
#### vim /etc/security/limits.conf 在文件末尾加
    elasticsearch soft nofile 65536
    elasticsearch hard nofile 65536
    elasticsearch soft nproc 4096
    elasticsearch hard nproc 4096
#### windows换行符转换
    dos2unix kibana/build.sh logstash/build.sh elasticsearch-ik/build.sh
## 1.2构建镜像
#### 执行脚本
    sh build.sh 7.9.2
    sh build.sh 7.9.2
    sh build.sh 7.9.2
## 1.3启动elk服务
#### 更改目录权限
    chmod -R 777 es01
#### 授权文件夹
    chmod 777 data/ conf/ logs/ plugins/
#### 启动
    docker-compose up -d
#### 查看集群状态
    http://47.114.137.37:9200/_cluster/health?pretty
# 二、nginx+Elk加密配置
##2.1安装Apache Httpd 密码生成工具
    yum install httpd-tools -y
    mkdir /usr/local/src/nginx/conf.d/passwd/
# 生成密码，用户名 kibana
    htpasswd -c /usr/local/src/nginx/conf.d/passwd/kibana.passwd kibana
# 提示输入2遍密码
    htpasswd -c -b /usr/local/src/nginx/conf.d/passwd/kibana.passwd user bm521n1314
## 2.2、nginx 配置

    server {
        listen 8001;
        server_name localhost;
            # 设置 auth
            auth_basic "kibana login auth";
            auth_basic_user_file /etc/nginx/conf.d/passwd/kibana.passwd;

            location / {
                # 设置 auth
                auth_basic "kibana login auth";
                auth_basic_user_file /etc/nginx/conf.d/passwd/kibana.passwd;

                # 转发到 kibana
                proxy_pass http://127.0.0.1:5601;
                proxy_set_header Host $host;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            }

        }

## 2.3配置Kibana
    vim /usr/local/kibana/config/kibana.yml

