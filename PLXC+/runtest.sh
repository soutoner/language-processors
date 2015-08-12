#!/bin/bash

# $1 path to the exercise
# $2 counter if any
correct_exercise () {
	solution=`./plxc $1 | ./ctd`;
	exercise=`java PLXC $1 | ./ctd`;
	name=$(basename "$1")

	if [[ $solution == $exercise ]]
	then
		echo "${2-1}. ${name} Ok!";
	else 
		echo "${2-1}. ${name} ERROR! Expected: ${solution} Given: ${exercise}";
	fi
}

if [ $1 ]
then
	correct_exercise "plxc-test/${1}"
else
	count=1;

	for filename in plxc-test/*;
	do
		correct_exercise $filename $count

		count=$((count+1));
	done
fi


