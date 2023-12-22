#!/bin/bash
test $(curl localhost:8766/sum?a=80\&b=20) -eq 100
