# simple2
Simple app to test Charles Library and proxy

# Synopsis
The use of groovy plugins to configure Charles proxy settings is possible, but very awkward.

The better approach when using Android Studio is to create a library, put it in a maven repository, and add it as a dependency to your project.  Then, Android Studio takes care of merging the android manifest file from the library with the app android manifest file, and merging the network_security_config.xml file into the APK build archive.

# Details
The important parts are the following.

The android/defaultConfig/minSdkVersion should be equal to or below the charles library minSdkVersion.

The one dependency to add the dependencies closure block in the app build.gradle is:
compile 'com.robotsandpencils:charles-library:1.0'
