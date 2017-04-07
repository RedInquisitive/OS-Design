#!/bin/sh
for f in $(find . -name '*.vm'); do java -jar trans.jar $f; done