#!/bin/bash
count=1;

for filename in plc-test/*;
do
	solution=`./plc $filename | ./ctd`;
	exercise=`java PLC $filename | ./ctd`;
	
	if [[ $solution == $exercise ]]
	then
		echo "${count}. Ok!";
	else 
		echo "${count}. ERROR! Expected: ${solution} Given: ${exercise}";
	fi

	count=$((count+1));
done
