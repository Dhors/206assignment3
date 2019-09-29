# SOFTENG 206 Assignment 3 
This is program is a prototype for VARpedia (Visual Audio Reading wikipedia search)
This application should be run on the SOFTENG 206 updated VirtualBox image.

This application was compiled with Java13.

The API keys for flickr are stored in flickr-api-keys.txt.

# Libraries
The following libraries must be imported for the source code to work.

JavaFX13
flickr4java-3.0.1
scribejava-apis-6.6.3
scribejava-core-6.2.0
slf4j-api-1.7.25
slf4j-nop-1.7.26

Excluding JavaFX13, these libraries are included in the libs folder from the 206_FlickrExample ACP project.


# Runnable jar file
This jar file should be run on the SOFTENG 206 updated VirtualBox image.

The following files must be in the same folder:
206assignment3.jar
script.sh
run.sh
flickr-api-keys.txt

Steps to run:
1 Open up terminal and navigate to the folder that contains these files.

2a The jar file can be run the included run.sh script by typing the the command "./run.sh" into terminal. the run.sh script file must be in the same folder as the jar file.

2b alternativly, The jar file can be run by typing the following two commands into terminal:
PATH=/home/student/Downloads/openjdk-13_linux-x64_bin/jdk-13/bin:$PATH
java --module-path /home/student/Downloads/openjfx-13-rc+2_linux-x64_bin-sdk/javafx-sdk-13/lib --add-modules javafx.base,javafx.controls,javafx.media,javafx.graphics,javafx.fxml -jar 206Assignment3.jar
