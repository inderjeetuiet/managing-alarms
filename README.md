# managing-alarms
This project demonstrates the concept of using alarm to keep the services running in background all the time. In addition, the same services can be used in foreground with minor tweaking. 
In this demo project, I have defined two modes which I called them Device and Server. 
In both the modes, I have created 3 timertask classes that get triggered repeatedly.

Device Mode:
These 3 timertask classes get triggered with interval of 5 seconds.
Possible usage: developer can use these timertask classes to get the location of user and updating UI, get bluetooth devices etc.

First TimerTask: Handles the scaning all the wifi signals that can be captured by device, and updates in listview in every 5 seconds. (Still not complete yet)

Server Mode:
I use the same 3 timertask classes, but with the interval of 10 mins
Possible usage: For example, developer can use them to send data to server, performaing sync with server etc.

In addition, the timertask collects data periodically as per the mode selected, currently only data collection for wifi has been implemented. The collected data passed to activity with the use of ResultReciever. 

Comming soon:
Periodic data collection of sensor data such GPS coordinates, speed etc 
