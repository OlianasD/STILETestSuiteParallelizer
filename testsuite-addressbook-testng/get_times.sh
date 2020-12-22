#!/bin/bash
for i in {0..10}
do
	./run-docker.sh -p yes -n addressbook0
	sleep 5
	./run-testsuite.sh
	sleep 2
	./teardown-docker.sh addressbook0
	cp "test-output/Suite/Test.xml" "/home/anonymous/testNGtimes/addressbook$i.xml"
done