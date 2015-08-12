#!/bin/bash

cup PLC.cup 
jflex PLC.flex
javac -cp ../lib/java-cup-11b-runtime.jar *.java
