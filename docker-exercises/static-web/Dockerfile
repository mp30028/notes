FROM ubuntu
MAINTAINER Map Ahmed "mp30028@gmail.com"
RUN apt-get update; apt-get install -y nginx
RUN echo 'Hi, This message is from the container' > /var/www/html/index.html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
