#!/bin/bash

# $1 path to the exercise
# $2 counter if any
correct_exercise () {
	# Concat with jplcore.j both files
	cat jplcore.j | tee tmp/Solution.j > tmp/Exercise.j;
	# Compile solution 
	./jplc $1 >> tmp/Solution.j;
	# Compile exercise
	java JPLC $1 >> tmp/Exercise.j;
	# Run jasmin on solution and execute 
	java -jar jasmin.jar -d tmp/ tmp/Solution.j &> /dev/null;
	solution=`java -cp tmp JPL 1`;
	rm tmp/JPL.class;
	# Run jasmin on exercise and execute 
	java -jar jasmin.jar -d tmp/ tmp/Exercise.j &> /dev/null;
	exercise=`java -cp tmp JPL 1`;
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
	correct_exercise "jplc-test/${1}";	
	rm -rf tmp/*;
else
	count=1;

	for filename in jplc-test/*;
	do
		correct_exercise $filename $count;

		count=$((count+1));
	done

	rm -rf tmp/*;
fi


