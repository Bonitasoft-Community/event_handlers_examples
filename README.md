# README #

This README would normally document whatever steps are necessary to get event handlers up and running.

### What is this repository for? ###

This repository is to show how to use event handlers. 
The following use cases are represented:
* A new human task is activated
* A user is assigned to the task
* A human task is executed and the handler will get the value of a transient data variable

### How do I get set up? ###

In order to install the event handlers:
* build the jar
* copy the jar to your tomcat bonita webapps lib
* execute setup pull
* update the file setup/platform-conf/current/tenants/1/tenant-engine/bonita-tenant-sp-custom.xml with the one under src/main/resources
* execute setup push
* restart the server

