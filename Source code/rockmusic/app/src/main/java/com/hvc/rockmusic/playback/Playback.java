package com.hvc.rockmusic.playback;

import static android.support.v4.media.session.MediaSessionCompat.QueueItem;

public interface Playback {
    void start();

    void play(QueueItem item);

    void pause();

    void stop(boolean notifyListeners);

    int getState();

    void setState(int state);

    boolean isConnected();

    boolean isPlaying();

    void seekTo(long position);

    long getCurrentStreamPosition();

    void updateLastKnownStreamPosition();

    String getCurrentMediaId();

    void setCurrentMediaId(String mediaId);

    void setCallback(Callback callback);

    interface Callback {
        void onCompletion();

        void onPlaybackStatusChanged(int state);

        void onError(String error);

        void setCurrentMediaId(String mediaId);
    }
}
