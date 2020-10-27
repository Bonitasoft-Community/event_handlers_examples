# README #



### What is this repository for? ###

This project shows some examples on how to use event handlers.

An event handler is a java code that is executed when a certain event is listened.

We can consider to use it when sometimes we need to move from process to platform perspective. 

We need to be careful in setting up these events because a wrong implementation can have a strong impact on the platform performance.

The following use cases are covered in the example (at the moment). Handlers that trigger when:

* Any new human task is activated
* Any task is assigned to a new user
* A human task is executed and the handler will get the value of a transient data variable

For sake of simplicity the handler will just log into the filesystem some information.

### How to install the handler? ###

The event handler jar needs to be included in the bonita webapps lib, and registered through some spring beans, provided 

* Build the jar with maven. you can change in the pom.xm the location to your tomcat webapps bonita lib
* From your setup directory execute the command setup pull
* update the file setup/platform-conf/current/tenants/1/tenant-engine/bonita-tenant-sp-custom.xml with the one provided under src/main/resources (spring beans)
* execute setup push
* restart the server

### How to test ? ###

* Create a process with a human task, or just use a process you have already with some human tasks
* Optionally add a variable called "status" to the human task, and update it through operations
* Execute the process
* Open your Bonita engine log, you should see the traces of your handlers 