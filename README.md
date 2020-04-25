# CovSense
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![Version](https://badge.fury.io/gh/saivittalb%2Fcovsense.svg)](https://badge.fury.io/gh/saivittalb%2FCovsense) [![PR's Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat)](http://makeapullrequest.com) [![Dependencies](https://david-dm.org/saivittalb/covsense.svg)](https://david-dm.org/saivittalb/covsense) [![devDependencies](https://david-dm.org/saivittalb/covsense/dev-status.svg)](https://david-dm.org/saivittalb/covsense?type=dev)
[![GitHub followers](https://img.shields.io/github/followers/saivittalb.svg?style=social&label=Follow)](https://github.com/saivittalb?tab=followers) 
[![Twitter Follow](https://img.shields.io/twitter/follow/saivittalb.svg?style=social)](https://twitter.com/saivittalb) 

<p align="center"><img src="https://user-images.githubusercontent.com/36305142/80282766-eb0e8a00-8730-11ea-9fa8-b0861d046ad1.png" height="300" width="300"></p> 

An Android based contact tracing app which enables people to self-isolate if they have been in close proximity to someone tested positive for COVID-19. The app uses a combination of Wi-Fi, Bluetooth, BluetoothLE and ultrasonic modem to communicate a unique-in-time pairing code between devices (using the Nearby Messages Google API). This technology can be scaled beyond the demo to work on iOS as well. Furthermore the solution can be embedded in other existing applications as a library to have larger adoption of the contact tracer.

Available in Dark and Light themes. Themes are pre-chosen depending on the user system settings. Preview of the app is available below in this document. 

## License
This project is licensed under the Apache License 2.0, a permissive license whose main conditions require preservation of copyright and license notices. Contributors provide an express grant of patent rights. Licensed works, modifications, and larger works may be distributed under different terms and without source code. Trademark use is also strictly prohibited. Any material found which vandalises or threatens any sort of plagiarism will be strictly given a legal action.

<p align="center"> Copyright (c) 2020 Sai Vittal B. All rights reserved.</p>

## Preview

### Dark theme 
<p align="center"><img src="https://firebasestorage.googleapis.com/v0/b/poised-elf-275018.appspot.com/o/DarkThemeScreens.png?alt=media&token=c257b467-c5b9-42a4-89d3-ea5a27f72784"></p> 

### Light theme
<p align="center"><img src="https://firebasestorage.googleapis.com/v0/b/poised-elf-275018.appspot.com/o/LightThemeScreens.png?alt=media&token=ebcd46f2-7a91-4231-a070-0fa7cfd2379e"></p> 

## Demo
A short video demonstration of the app when the health status of one user has been modified is available here.

## Android APK build 0.1.0 (Debug)
The .apk build of this app that you can install on your Android device is available here.

## Corner cases (known as of now)
- Tracking does not happen when the user is on a phone call.

If you discover any failing test cases, you are encouraged to open an issue or a PR regarding it. 

## Working
After you sign in, you get an OTP generated using Firebase Phone Authentication. After you login, the application starts a background service that constantly publishes and receives the Firestore Database UIDs, by using the Nearby Messages API from Google. When two devices are in close proximity (approximately 4 metres to 5 metres for Bluetooth + Sonar) their meetup is registered in Firestore.

In the logged in screen, you can choose your current health status and press the button. This updates your health status in the database. Using Firestore Cloud Messages, there is a JavaScript function that triggers when this update happens and sends a push notification to the users that you have interacted with. 

## Components
- Android codebase in Java 
- Firebase Authentication   (authenticate requests)
- Firestore                 (database)
- Nearby Messages API       (contact tracing)
- Firebase Functions        (serverless code)

###### Note 
Developed with Android Studio version 3.6.3.

## Installation
### Prerequisites
- Before you begin, make sure your development environment includes [Node.js®](https://nodejs.org/), [yarn](https://classic.yarnpkg.com/) and [npm](https://www.npmjs.com) package managers.
- Download [Android Studio](https://developer.android.com/studio).
- Also make sure to create [Google Developers Console](https://console.developers.google.com), [Google Cloud Platform](https://console.cloud.google.com), and [Firebase](https://firebase.google.com) accounts for generating necessary API keys and setting up the project. 

### Android app configuration
- Follow the instructions for setting up your [Google Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk/start).
- Follow the instructions for interacting with the [Google Nearby Messages API](https://developers.google.com/nearby/messages/android/get-started).
- Update your app module <b>build.gradle</b> file with
    - The <b>API_KEY</b> for the Nearby Messages API, ```API_KEY = "\"YOUR_API_KEY_HERE\""```.
    - The <b>MAPS_API_KEY</b> for the Google Maps SDK for Android, ```MAPS_API_KEY = "\"YOUR_MAPS_API_KEY_HERE\""```.  
- Update your <b>AndroidManifest.xml</b> file in maps meta data with your <b>API_KEY</b>, ```value = "YOUR_MAPS_API_KEY_HERE"```.
- Go to your Firebase console, setup this project, select Android app, add the package name of this app and download <b>google-services.json</b>.
- Move the <b>google-services.json</b> file you just downloaded into your Android app module root directory.

### Database configuration
- In the Firebase console, open the Authentication section.
- On the Sign-in Method page, enable the Phone Number sign-in method.
- Follow the installation guide for [Firestore](https://firebase.google.com/docs/firestore/quickstart).
- Set up [Firebase Functions](https://firebase.google.com/docs/functions/get-started).
- Once you complete the Firebase Functions setup, open terminal/console window over the project directory and run following commands.
```bash
$ cd ./firestore/functions

$ npm install

$ yarn

$ cd ..
 
$ firebase deploy --only functions
```

### JSON schema for Firebase
Two tables named <b>users.json</b> and <b>users-meetings.json</b> are pre-saved in the `./CovSense/firestore` directory.
    
## Contributing
- Fork this project by clicking the ```Fork``` button on top right corner of this page.
- Open terminal/console window. 
- Clone the repository by running following command in git:
 ```bash
$ git clone https://github.com/[YOUR-USERNAME]/BasicCRUDWebApp.git
```
- Add all changes by running this command.
```bash
$ git add .
```
- Or to add specific files only, run this command.
```bash
$ git add path/to/your/file
```
- Commit changes by running these commands.
```bash
$ git commit -m "DESCRIBE YOUR CHANGES HERE"

$ git push origin
```
- Create a Pull Request by clicking the ```New pull request``` button on your repository page.

[![ForTheBadge built-with-love](http://ForTheBadge.com/images/badges/built-with-love.svg)](https://GitHub.com/saivittalb/) 
[![ForTheBadge powered-by-electricity](http://ForTheBadge.com/images/badges/powered-by-electricity.svg)](http://ForTheBadge.com)

<p align="center"> Copyright (c) 2020 Sai Vittal B. All rights reserved.</p>
<p align="center"> Made with ❤ by <a href="https://github.com/saivittalb">Sai Vittal B</a></p>
