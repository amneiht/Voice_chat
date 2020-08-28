#!/bin/bash
sudo docker run --name mysql -p 3306:3306 -v mysql:/var/lib/mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql:5.7 --max-allowed-packet=16777216
sudo docker run --name myadmin -d --link mysql:db -p 8080:80 phpmyadmin
