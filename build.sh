#!/bin/bash

rm -rf bin/*

javac -sourcepath src -d bin src/Main.java && \
jar -cfm PipeSoundVisualizer.jar manifest.mf help.txt -C bin .
