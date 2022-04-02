FROM nginx:latest
MAINTAINER "Jerry"
LABEL description="spvue-frotend"
COPY target/dist/ /usr/share/nginx/html/dist
COPY default.conf /etc/nginx/conf.d/default.conf
