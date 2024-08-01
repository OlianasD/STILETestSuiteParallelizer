#!/bin/bash

num_execution=$1

for (( i = 0; i < $num_execution; i++ ))
do
	./run-docker.sh
	sleep 10
	./run-testsuite.sh
	./teardown-docker.sh joomla
	sleep 1
	cp test-output/emailable-report.html test-output/emailable-report-$i.html
done