package com.hvc.rockmusic;

import android.support.v4.media.MediaBrowserCompat;



public interface FirePopupMenuSelectedListener {
    void onPlaySelected(MediaBrowserCompat.MediaItem item);

    void onShareSelected(MediaBrowserCompat.MediaItem item);
}
