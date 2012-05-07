Decision Maker
---------------------------------------------------------
University of Southern California
Viterbi School of Engineering
EE 579 Final Project
Group 8: Wanlong Cui, Juanyi Feng, Wei Sun
---------------------------------------------------------

This is a client-server based application which can help to divide a large group of users into sub-groups based on their common preferences, and also make the decision for each sub-group.

This application can be quite useful when a large group people are gathering together and need to decide some certain things to form smaller groups.

In our app, we¡¯ve built three scenarios ¨C Restaurant, Movie and Customize.

In the restaurant scenario, the application will automatically get the restaurants¡¯ name near user¡¯s current location and allows user to enter his/her on preference (rate each restaurant on the list).

In the movie scenario, the application will automatically get the movies¡¯ title which are in theater near user¡¯s current location and allows user to enter his/her on preference (rate each movie on the list).

In the customize scenario, the application will allow the user to enter the option list.

Once each user has entered their preference list, the server will run K-Means clustering algorithm to generate the grouping result.

After each user gets the result, users can enter a chat room to communicate with other users within the same sub-group.



The code is divided into two parts -- server side and client side.
The server side code contains the code for socket server which is the project ¡°DecisionMakerServer¡±.
It also contains two Perl Code running on Apache HTTP Server and a java code running on Tomcat Web Server.
The client side code is an android project named ¡°DecisionMaker¡±.
