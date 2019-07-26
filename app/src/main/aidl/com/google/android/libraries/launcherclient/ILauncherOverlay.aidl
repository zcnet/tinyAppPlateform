// ILauncherOverlay.aidl
package com.google.android.libraries.launcherclient;

import android.view.WindowManager.LayoutParams;
import android.os.Bundle;
import com.google.android.libraries.launcherclient.ILauncherOverlayCallback;

interface ILauncherOverlay {
    void startMove();

    void updateMove(float progress);

    void endMove();

    void onAttachedToWindow(in LayoutParams windowAttrs, ILauncherOverlayCallback callbacks, int flags);

    void onDetachedFromWindow(boolean isChangingConfigurations);

    void hideOverlay(int animate);

    void onPause();

    void onResume();

    void showOverlay(int animate);

    void requestHotwordDetection(boolean requestHotword);

    String getVoiceSearchLanguage();

    boolean checkHotwordService();

    boolean checkOverlayContent();

    void onAttachedToWindow2(in Bundle clientOptions, ILauncherOverlayCallback callbacks);

    void noop();

    void updateActivityLifecycleState(int state);

}
