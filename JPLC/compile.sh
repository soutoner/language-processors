#!/bin/bash

cup JPLC.cup 
jflex JPLC.flex
javac -cp ../lib/java-cup-11b-runtime.jar *.java
