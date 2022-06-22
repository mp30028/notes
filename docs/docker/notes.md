#### Basic docker commands

##### Start up an ubuntu container and provide an interactive shell

`docker run -i -t --name my_ubuntu ubuntu /bin/bash`

`-i` flag instructs docker to keep the STDIN from the container open

`-t` flag instructs docker to attach a tty to the container

`exit` to shutdown the running container

`docker start my_ubuntu` to restart a stopped container 

##### See a list of containers 
`docker ps -a` to see a list of current containers `-a` to show all including stopped containers. `-l` to show the last container

##### Remove a container
`docker rm my_ubuntu`


---
#### Basic unix commands

`hostname`

`cat /etc/hosts`

`ps -aux`

`apt-get update; apt-get install vim`
