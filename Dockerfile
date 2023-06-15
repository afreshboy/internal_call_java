
# 根据版本
FROM maven:3-jdk-8-alpine as builder

# 指定构建过程中的工作目录
WORKDIR /opt/application
# 将当前目录(dockerfile所在目录)下所有文件都拷贝到工作目录下（.dockerignore中文件除外)
COPY . .

# 在settings.xml中更换依赖源, 用户指定的情况
RUN if [ $(ls /opt/application | grep -w settings.xml | wc -l ) = 1 ]; then mvn -s /opt/application/settings.xml -f /opt/application/pom.xml clean package; fi

# 在settings.xml中更换依赖源, 用户未指定的情况
RUN if [ $(ls /opt/application | grep -w settings.xml | wc -l ) = 0 ]; then mvn -f /opt/application/pom.xml clean package; fi


# 采用抖音云基础镜像, 包含https证书, bash, tzdata等常用命令
FROM openjdk:8-jdk-alpine
WORKDIR /opt/application

# 将产物copy到运行镜像中
COPY --from=builder /opt/application/target /opt/application

RUN apk update && \
    apk upgrade && \
    apk add bash && \
    apk add curl

USER root

# 写入run.sh
RUN echo -e '#!/usr/bin/env bash\ncd /opt/application/ && java -jar $(ls | grep .jar -m 1) ' > /opt/application/run.sh

# 指定run.sh权限
Run chmod a+x run.sh

EXPOSE 8000
