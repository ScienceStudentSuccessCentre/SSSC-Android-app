# <!-- omit in toc --> Carleton Science Student Success Centre - Android App

This is the Android app for the Carleton University Science Student Success Centre. You can find the SSSC Server code [here](https://github.com/ScienceStudentSuccessCentre/SSSC-Server).

- [Project summary](#project-summary)
- [Updates](#updates)
- [Getting started](#getting-started)
  - [Downloading the project](#downloading-the-project)
  - [Setting up an emulator](#setting-up-an-emulator)
- [Uploading to the Play Store](#uploading-to-the-play-store)
  - [Part 1: Generating a Signed APK](#generating-a-signed-apk)
  - [Part 2: Creating a Release in the Play Console](#part-2-creating-a-release-in-the-play-console)
  - [Part 3: Filling in app details on App Store Connect](#part-3-filling-in-app-details-on-app-store-connect)

## Project summary

This project is the mirror version of the iOS Science Student Success Centre app, for Android! It allows students to view all of the events that are shown on the SSSC's website and quickly access the SSSC's resources page. 
Event data for both the Android and iOS apps are retrieved using a GET request to the SSSC Server (linked above) using the `/events` endpoint, to ensure their data is sychronized. 

## Updates

(Refer to the ios app for the following features)
1. Add ability to back up grades
2. Add colors to a course
3. Add search bars to the top app bar(Terms screen, Calculator screen)
4. Find a more user friendly/appealing way to delete list items. Current implemention is long press on the item.

## Getting started

There are a few different steps you'll need to go through to work on the app. The first step should be to have Android Studio installed on your computer if you dont have it already. You can follow the download instrauction [here](https://developer.android.com/studio/install).

### Downloading the project

The first thing you'll need to do in order to work on the app is to download it! Here are the steps to get it onto your machine properly.

1. Clone the repository: `git clone https://github.com/ScienceStudentSuccessCentre/SSSC-Android-app.git`
2. Open the project in Android Studio.

### Setting up an emulator
If you plan to use an emulator follow this [tutorial](https://developer.android.com/studio/run/emulator) that walk you through setting up an emulator and actually running the app!

### Uploading to the Play Store

When you are satisfied with the new features you have added it's time to release your update to the Play Store. Here is a quick sumnary of the steps you'll need to take in order to upload a new build to the Play Store and release it. The official guide can be found [here](https://developer.android.com/studio/publish)

## Part 1: Generating a Signed APK

Google requires that you app is signed with a certain key. In this section we'll go through getting a signed APK from Android Studio.

* Note make sure you have the keystore file(.jks) before you start this process *

1. First step is to increment the versionCode and versionName attributes in the app level gradle file.
2. Next select Build->Generate Signed Bundle/APK from the top menu.
3. Select Android App Bundle in the dialog and click next.
4. In key store path browse to find the keystore file.
5. Enter the keystore password and the keypassword.
6. Set the key alias as "upload".
7. Click next...
8. Select Release and click finish.
9. In the Event Log window along the bottom of Android Studio, it will show that your app is building and an Android App Bundle is being generated.
10. Once it completes a notification will appear prompting you to locate the newly generated bundle. This is the file you will ned to upload to the Play Console

## Part 2: Creating a Release in the Play Console

Now that you have a signed app bundle you can go over to the Play Console site and setup the release.

1. Once you login you will see a list of published apps. Select the SSSC app(it should be the only one) ;)
2. Navigate to Store Prescense -> Store Listing in the menu on the left. In the Store Listing you can modify the screenshots if needed and also edit the app description to included new features. (ONLY if neeeded)
3. Navigate to Release Management -> App Releases on the menu.
4. In the Production section sleect "Manage"
5. Click "Create Release"
6. In the "Android App Bundles and APKs to add" section add you signed app bundle that was made in Part 1.
7. Scroll down fill in the release name.
8. Add a description of what's new in the release. 
9. Once you are satisfied with the information click "Review" at the bottom of the screen.
10. On the next screen hit "Rollout"!

Awesome! You just published your update to the Play Store! It might take a few hours for the update to be seen on the Play store.
