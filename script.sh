#!/bin/bash

mvn package
cd target
mv CodeQuest2017-1.0-SNAPSHOT-jar-with-dependencies.jar "bot.jar"
zip build.zip "bot.jar"
