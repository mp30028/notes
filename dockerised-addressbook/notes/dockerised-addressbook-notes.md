
## Login to Docker-Hub

`docker login -u mebsp`

![screenshot](./images/docker-login.png)

---

## Building a test Dockerfile with nginx

***Create the Dockerfile***
```dockerfile
########## Dockerfile contents ##########
FROM ubuntu:focal
LABEL maintainer="zonesoft" \
      email="mp30028@gmail.com" \
      description="Dockerfile used for learning purposes"
RUN apt-get update; apt-get install -y nginx
RUN echo 'If you are seeing this message then Ubuntu (v.20.04) and nginx were successfully installed in this image.' > /var/www/html/index.html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

***Run the build command***
`docker build -t="mebsp/addrbook" .`
![screenshot](./images/docker-build-addrbook.png)

***Run the container***
`docker run -d --name addrbook -p 8080:80 mebsp/addrbook`
![screenshot](./images/docker-run.png)

***Check it's running ok***
`invoke-webrequest -Uri 'http://localhost:8080'`
![screenshot](./images/invoke-webrequest.png)

---

## Building a Dockerfile starting with a MySql official image

 ***1. Clean up previous addrbook containers and images***
`docker stop addrbook`
`docker rm addrbook`
`docker images`
`docker rmi mebsp/addrbook`


***2. Create the Dockerfile***
```dockerfile
########## Dockerfile contents ##########
FROM mysql:8.0.29
LABEL maintainer="zonesoft" \
      email="mp30028@gmail.com" \
      description="Dockerfile used for learning purposes"
ENV MYSQL_ROOT_PASSWORD root*P^55word
EXPOSE 3306
```

***3. Login to Docker-Hub***
`docker login -u mebsp`

***4. Run the build command***
`docker build --no-cache -t="mebsp/addrbook" .`

***5. Run the container***
`docker run -d --name addrbook -p 3303:3306 mebsp/addrbook`
![screenshot](./images/docker-run-2.png)

***6. Connect to MySql with MySql-shell***
`mysqlsh -u root -proot*P^55word --port=3303`

At the mysqlsh prompt ![screenshot](./images/mysqlsh-prompt.png ) switch to sql mode with `\sql` command

![screenshot](./images/connect-mysqlsh.png)

## Building an image with a default database created and seeded with some data

***1. Update the Dockerfile created in the previous exercise***

```dockerfile
FROM mysql:8.0.29
LABEL maintainer="zonesoft" \
      email="mp30028@gmail.com" \
      description="Dockerfile with MySql-Server, used for learning purposes"
ENV MYSQL_ROOT_PASSWORD root*P^55word

## Create a database by setting the MYSQL_DATABASE environment variable
ENV MYSQL_DATABASE addressbook

## Copy the create_adressbook_db.sql (should be in the same folder as the Dockerfile) file to  /docker-entrypoint-initdb.d (in the image)
ADD create_adressbook_db.sql /docker-entrypoint-initdb.d

EXPOSE 3306
```
 ***2. Run steps 4 to 6 in the previous exercise***

 Should now be able to connect to the database in the container and see the newly created addressbook db.

 ![screenshot](./images/creating-db-in-image.png)
