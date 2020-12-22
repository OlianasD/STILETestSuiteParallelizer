#!/bin/bash
for i in {0..10}
do
	./run-docker.sh -p yes -n mrbs0
	sleep 5
	./run-testsuite.sh
	sleep 2
	./teardown-docker.sh mrbs0
	cp "test-output/Suite/Test.xml" "/home/anonymous/testNGtimes/mrbs$i.xml"
done