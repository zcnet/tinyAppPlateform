// ILauncherOverlayCallback.aidl
package com.google.android.libraries.launcherclient;

// Declare any non-default types here with import statements

interface ILauncherOverlayCallback {
    void onOverlayScrollChanged(float progress);

    void onServiceStateChanged(int status);

}
