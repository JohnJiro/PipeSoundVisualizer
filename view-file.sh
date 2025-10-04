#!/bin/bash

ffmpeg -loglevel error -i sample.mp3 \
    -af asetpts=PTS,arealtime,asetpts=PTS \
    -ar 48000 -ac 2 -f wav - | \
java -jar PipeSoundVisualizer.jar -fw 600 -fh 400 --single | \
aplay -t raw -f dat
