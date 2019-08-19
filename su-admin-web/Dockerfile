FROM node:11
VOLUME /app
MAINTAINER "su"<978230136@qq.com>
COPY . /app
EXPOSE 8080
ENTRYPOINT ["cd","/app"]
ENTRYPOINT ["npm","install"]
ENTRYPOINT ["npm","run","stage"]