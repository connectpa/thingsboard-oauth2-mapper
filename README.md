# ThingsBoard OAuth 2.0 mapper

This project (based on the code provided by [Thingsboard](https://github.com/thingsboard/custom-oauth2-mapper)) serves as a custom mapper in order to map OAuth 2.0 user to Thingsboard user.

This project is not meant to be run as it is, instead the docker image built from this source code is used in the project [IOT](https://github.com/connectpa/IOT).

To create the docker image, from the root folder launch the command:

`mvn clean install -Ddockerfile.skip=false`

The resulting docker image should be named like `psacr.azure.io/oauth2-mapper:latest`