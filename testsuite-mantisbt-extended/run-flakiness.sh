#!/bin/bash

num_execution=$1

for (( i = 0; i < $num_execution; i++ ))
do
	./run-docker.sh -p yes -n mantisbt-0
	sleep 1
	./run-testsuite.sh
	./teardown-docker.sh mantisbt-0
	sleep 1
	cp test-output/emailable-report.html test-output/emailable-report-$i.html
done