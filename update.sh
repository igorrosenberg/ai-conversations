#!/bin/bash

for f in */index.md
do 
	# echo $f
	< $f sed "s/\(^[A-Za-z].*$\)/[\\1](<\\1.md>)/g" > /tmp/delme
	mv /tmp/delme $f
done