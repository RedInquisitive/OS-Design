#!/bin/sh
for f in $(find . -name '*.asm'); do cat $f | java -jar hack.jar > $f.hack; done