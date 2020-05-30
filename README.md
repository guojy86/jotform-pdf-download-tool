## Getting Started

This application automate the download of [JotForm](https://www.jotform.com/) submissions in PDF format. There are 2 supported
configurations in the `application.properties`

* `app.jotform.apikey`: You need to create an API Key for your account. Please see [here](https://www.jotform.com/help/253-How-to-Create-a-JotForm-API-key)
* `app.jotform.pdf.download.location`: The download location of the form submissions in PDF format.

* Please note that you need to disable the following option in your JotForm account settings

  * Require log-in to see submissions
  * Require log-in to view uploaded files
  * Required log-in to view submission RSS

* Instructions for Windows

  * To build and package the app, please run `gradlew.bat build` 
  * To run the app, please run `gradlew.bat bootRun` or 

* Instructions for Linux/Unix

  * To build and package the app, please run `./gradlew build`  
  * To run the app, please run `./gradlew bootRun`

* To run the application without gradle 

  * Please run `java -jar jotform-pdf-download-tool-0.0.1-SNAPSHOT.jar`. The jar can be found in `build/libs` folder after build.