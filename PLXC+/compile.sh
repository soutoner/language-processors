#!/bin/bash

cup PLXC.cup 
jflex PLXC.flex
javac -cp ../lib/java-cup-11b-runtime.jar *.java
