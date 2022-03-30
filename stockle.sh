#!/bin/bash

COMPILE=$(javac -d bin src/company/*.java src/stockle/*.java src/support/cse131/*.java -Xlint:deprecation)

java -cp bin stockle.Stockle

