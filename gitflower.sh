#!/usr/bin/env bash

# Facilita el hacer mergear cosas en la feature y volver inmediatamente a la personal

git checkout "$1";git merge "$2";git checkout "$2"