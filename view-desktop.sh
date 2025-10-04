#!/bin/bash

ffmpeg -loglevel error \
    -f pulse -i default -acodec copy \
    -ar 48000 -ac 2 -f wav - | \
java -jar PipeSoundVisualizer.jar -fw 600 -fh 400 --single 1> /dev/null
