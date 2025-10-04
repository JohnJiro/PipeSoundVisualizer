#!/bin/bash

arecord -t raw -f dat | \
java -jar PipeSoundVisualizer.jar -fw 600 -fh 400 --single 1> /dev/null
