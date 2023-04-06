@echo off
cd estore-api
start /b cmd /c "mvn compile exec:java"
cd ../estore-ui
start /b cmd /c "ng serve --open"