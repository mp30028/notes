FROM ubuntu:focal

LABEL maintainer="zonesoft" \
      email="mp30028@gmail.com" \
      description="Dockerfile used for learning purposes"
RUN apt-get update; apt-get install -y nginx
RUN echo 'If you are seeing this message then Ubuntu (v.20.04) and nginx were successfully installed in this image.' > /var/www/html/index.html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
