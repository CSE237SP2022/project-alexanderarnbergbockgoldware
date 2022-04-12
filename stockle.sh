#!/bin/bash

COMPILE=$(javac -d bin src/company/*.java src/stockle/*.java -Xlint:deprecation)

java -cp bin stockle.Stockle
