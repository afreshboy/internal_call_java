# 根据版本
FROM public-cn-beijing.cr.volces.com/public/maven:3-jdk-8-slim as builder

# 指定构建过程中的工作目录
WORKDIR /opt/application
# 将当前目录（dockerfile所在目录）下所有文件都拷贝到工作目录下（.dockerignore中文件除外）
COPY . .

# 在settings.xml中更换依赖源, 用户指定的情况
RUN if [ $(ls /opt/application | grep -w settings.xml | wc -l ) = 1 ]; then mvn -s /opt/application/settings.xml -f /opt/application/pom.xml clean package; fi

# 在settings.xml中更换依赖源, 用户未指定的情况
RUN if [ $(ls /opt/application | grep -w settings.xml | wc -l ) = 0 ]; then mvn -f /opt/application/pom.xml clean package; fi
ARG WAR_NAME=$(cd /target | ls | grep .war -m 1)

# 采用抖音云基础镜像, 包含https证书, bash, tzdata等常用命令
FROM public-cn-beijing.cr.volces.com/public/dycloud-openjdk:tomcat-jre8-alpine
WORKDIR /opt/application

# 分析打包文件, maven: pom.xml 可得
COPY --from=builder  /opt/application/target/$WAR_NAME /usr/local/tomcat/webapps/

# 替换端口号
RUN sed -i s/8080/8000/g /usr/local/tomcat/conf/server.xml

# 写入run.sh
RUN echo -e '#!/usr/bin/env bash\nsh /usr/local/tomcat/bin/catalina.sh run' > /opt/application/run.sh

# 指定run.sh权限
Run chmod a+x run.sh

EXPOSE 8000