#!/bin/bash
wtds=$(ls wtds/)
for wtd in $wtds
do
	echo "Running $wtd"
	./run-docker.sh
	sleep 10
	./run-warranted.sh "wtds/$wtd"
	./teardown-docker.sh joomla
	sleep 5
	cp test-output/emailable-report.html "test-output/emailable-report-$wtd.html"
done