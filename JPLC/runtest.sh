#!/bin/bash

# $1 path to the exercise
# $2 counter if any
correct_exercise () {
	# Compile solution
	./jplc $1 solution.j;
	# Compile exercise
	java -cp .:../lib/java-cup-11b-runtime.jar JPLC $1 exercise.j;
	# Run solution and execute
	solution=`./jpl solution 1`;
	# Run jasmin on exercise and execute
	exercise=`./jpl exercise 1`;
	name=$(basename "$1")
	# Remove files
	rm -f solution.j exercise.j;

	if [[ $solution == $exercise ]]
	then
		echo "${2-1}. ${name} Ok!";
	else 
		echo "${2-1}. ${name} ERROR! Expected: ${solution} Given: ${exercise}";
	fi
}

if [ $1 ]
then
	correct_exercise "jplc-test/${1}";	
else
	count=1;

	for filename in jplc-test/*;
	do
		correct_exercise $filename $count;

		count=$((count+1));
	done
fi


