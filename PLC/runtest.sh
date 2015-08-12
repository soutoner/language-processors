#!/bin/bash
count=1;

for filename in ./plc-test/*;
do
	solution=`./plc $filename | ./ctd`;
	exercise=`java -cp .:../lib/java-cup-11b-runtime.jar PLC $filename | ./ctd`;
	
	if [[ $solution == $exercise ]]
	then
		echo "${count}. `basename $filename` - Ok!";
	else 
		echo "${count}. `basename $filename` - ERROR! Expected: ${solution} Given: ${exercise}";
	fi

	count=$((count+1));
done
