#!/bin/sh
for f in $(find . -type d); do find $f -name '*.vm' | xargs java -jar trans.jar; done