#!/bin/bash
app=$1
for i in {0..10}
do
	docker stop $app-$i
	docker rm $app-$i
	sleep 2
done