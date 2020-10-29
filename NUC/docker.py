FROM node:10.15.1
MAINTAINER Eunseok Shim <ses8323@naver.com>
ENV Topic initial

COPY ./docker-entrypoint.sh/
RUN mkdir Subscriber
COPY ./ /Subscriber
WORKDIR /Subscriber

RUN npm install mqtt --save
RUN npm install --save influx

EXPOSE 1883
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]




#!/bin/bash
sed -i s/'topic_sub'/$Topic/g sub.js
node sub.js


