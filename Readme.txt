Steps to integrate jenkins with the test suite:
1) Install jenkins as local administrator
2) open localhost:8080
3) Provide username and password, username is admin, password is present in program data->jenkins->.jenkins->secret->adminpassowrd
4) Click on setting and add plugins maven, email extension, git plugin
5)click on new item and select freestyle project
6)source code management select git add username and password in credentials
7)in build step add invoke top level maven projects, clean test
8) post build action add archive artifact **/.*/html **/*.pdf
9)  Add editable email notificaion
10) update email notificaiton in setitngs add smtp.gmail.com , tsl checked credentials and port 587
11) click on build