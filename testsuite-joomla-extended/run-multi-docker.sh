#!/bin/bash
num_execution=$1

for (( i = 0; i <= $num_execution; i++ ))
do
	./run-docker.sh -p yes -n "mantisbt$i" -a $(expr 3000 + $i) -d $(expr 3306 + $i)
	sleep 5
done
