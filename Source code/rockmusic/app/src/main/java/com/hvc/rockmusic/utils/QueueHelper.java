package com.hvc.rockmusic.utils;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.hvc.rockmusic.source.MusicProvider;

import java.util.ArrayList;
import java.util.List;


public class QueueHelper {

    private static final String TAG = FireLog.makeLogTag(QueueHelper.class);

    public static List<MediaSessionCompat.QueueItem> getPlayingQueue(String mediaId,
                                                                     MusicProvider musicProvider) {
        FireLog.d(TAG, "(++) getPlayingQueue: mediaId=" + mediaId);

        String[] hierarchy = MediaIDHelper.getHierarchy(mediaId);

        if (hierarchy.length != 2) {
            FireLog.e(TAG, "Could not build a playing queue for this mediaId: " + mediaId);
            return null;
        }

        String categoryType = hierarchy[0];
        String categoryValue = hierarchy[1];
        FireLog.d(TAG, "Creating playing queue for " + categoryType + ",  " + categoryValue);

        List<MediaMetadataCompat> tracks = musicProvider.getAllRetrievedMetadata();

        if (tracks == null) {
            FireLog.e(TAG, "Unrecognized category type: " + categoryType + " for media " + mediaId);
            return null;
        }

        return convertToQueue(tracks, hierarchy[0], hierarchy[1]);
    }

    private static List<MediaSessionCompat.QueueItem> convertToQueue(
            Iterable<MediaMetadataCompat> tracks, String... categories) {
        FireLog.d(TAG, "(++) convertToQueue: tracks=" + tracks + ", categories=" + categories);
        List<MediaSessionCompat.QueueItem> queue = new ArrayList<>();
        long id = 0;
        for (MediaMetadataCompat track : tracks) {
               String hierarchyAwareMediaID = MediaIDHelper.createMediaID(
                    track.getDescription().getMediaId(), categories);

            MediaMetadataCompat trackCopy = new MediaMetadataCompat.Builder(track)
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, hierarchyAwareMediaID)
                    .build();

            MediaSessionCompat.QueueItem item = new MediaSessionCompat.QueueItem(
                    trackCopy.getDescription(), id++);
            queue.add(item);
        }
        return queue;

    }

    public static int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue,
                                           String mediaId) {
        FireLog.d(TAG, "(++) getMusicIndexOnQueue: mediaId=" + mediaId);
        int index = 0;
        for (MediaSessionCompat.QueueItem item : queue) {
            if (mediaId.equals(item.getDescription().getMediaId())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue,
                                           long queueId) {
        FireLog.d(TAG, "(++) getMusicIndexOnQueue: queueId=" + queueId);
        int index = 0;
        for (MediaSessionCompat.QueueItem item : queue) {
            if (queueId == item.getQueueId()) {
                return index;
            }
            index++;
        }
        return -1;
    }


    public static boolean isIndexPlayable(int index, List<MediaSessionCompat.QueueItem> queue) {
        return (queue != null && index >= 0 && index < queue.size());
    }
}
