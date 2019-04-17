FROM gradle:jdk8
ARG GIT_USER
ARG GIT_PASSWD
ARG BRANCH
ARG MODULE_PATH='czb-bn/bn-api'
USER root

RUN git clone -b $BRANCH https://$GIT_USER:$GIT_PASSWD@git.singularitychina.com/bazhong-project/government-server.git /opt/app
WORKDIR /opt/app
VOLUME /home/gradle/.gradle
EXPOSE 8090

CMD /bin/sh -c "git reset --hard && git pull && gradle --project-cache-dir /var/app_dependencies -p $MODULE_PATH bootRun --parallel"