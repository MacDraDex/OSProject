Operating System Project
======================================
There are 2 folders called client and server and each folder contains 2 files (client -> Requester,Person),(server->EchoServer,Person).
To run the code files from the server folder must be uploaded to cloud server and they must be in the same directory.
To run the program you must start EchoServer.java first and then run Requester.java which contains main method to connect to a server using port ("2004") and then follow instructions given in program. When you compile the program you will be asked to manually enter the IP address of the server that you will be using.Then you will be asked to input username and password from a client side that will send a message that stores username and password in object which will be sent to server in order to verify if the credentials given by user matches a record in ArrayList.User will be informed if details are correct or not.

For some reason after I enter correct username and password I get exception that I don't know how to fix it. After you enter correct details and you get message that "Login Unsucessful" that will only happen when Person.java isn't compiled.


Resources used to do this project :
https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
http://www.journaldev.com/2452/java-serialization-example-tutorial-serializable-serialversionuid
http://stackoverflow.com/questions/26255223/sending-different-objects-to-server-in-java
http://codereview.stackexchange.com/questions/20961/java-multi-thread-file-server-and-client

Book:
Java - The Complete Reference Eight Edition by Herbert Schildt (polish language edition)
