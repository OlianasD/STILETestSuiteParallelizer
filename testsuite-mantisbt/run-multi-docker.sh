#!/bin/bash
app=$1
srv_no=$2
base_port=3000
for i in {0..10}
do
	let app_port=$base_port+$i
	let db_port=$base_port+$i+100
	./run-docker.sh -p yes -n $app-$i -a $app_port -d $db_port
	sleep 15
done