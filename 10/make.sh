#!/bin/sh
for f in $(find . -name '*.jack'); do java -jar token.jar $f; done
sleep(100)