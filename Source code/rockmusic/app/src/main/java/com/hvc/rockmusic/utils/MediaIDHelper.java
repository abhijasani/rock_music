package com.hvc.rockmusic.utils;

import android.support.annotation.NonNull;

public class MediaIDHelper {

    // Media IDs used on browse items of MediaBrowser
    public static final String MEDIA_ID_EMPTY_ROOT = "_EMPTY_ROOT_";
    public static final String MEDIA_ID_ROOT = "_ROOT_";
    public static final String MEDIA_ID_PLAYLIST = "_BY_PLAYLIST_";
    public static final String MEDIA_ID_TRACKS = "_BY_TRACK_"; // track is special category where no further subcategory
    public static final String MEDIA_ID_TRACKS_ALL = "_ALL_TRACK_"; // use this for subcategory for track
    public static final String MEDIA_ID_ALBUM = "_BY_ALBUM_";
    public static final String MEDIA_ID_ARTIST = "_BY_ARTIST_";
    public static final String MEDIA_ID_GENRE = "_BY_GENRE_";
    public static final String MEDIA_ID_FOLDER = "_BY_FOLDER_";

    public static final String MEDIA_ID_SEARCH = "_SEARCH_";

    private static final char CATEGORY_SEPARATOR = ',';
    private static final char LEAF_SEPARATOR = '|';

    public static String createMediaID(String musicID, String... categories) {
        StringBuilder sb = new StringBuilder();
        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                if (!isValidCategory(categories[i])) {
                    throw new IllegalArgumentException("Invalid category: " + categories[i]);
                }
                sb.append(categories[i]);
                if (i < categories.length - 1) {
                    sb.append(CATEGORY_SEPARATOR);
                }
            }
        }
        if (musicID != null) {
            sb.append(LEAF_SEPARATOR).append(musicID);
        }
        return sb.toString();
    }

     private static boolean isValidCategory(String category) {
        return category == null || (category.indexOf(CATEGORY_SEPARATOR) < 0 && category.indexOf(LEAF_SEPARATOR) < 0);
    }

    public static String extractMusicIDFromMediaID(@NonNull String mediaID) {
        int pos = mediaID.indexOf(LEAF_SEPARATOR);
        if (pos >= 0) {
            return mediaID.substring(pos + 1);
        }
        return null;
    }

    public static
    @NonNull
    String[] getHierarchy(@NonNull String mediaID) {
        int pos = mediaID.indexOf(LEAF_SEPARATOR);
        if (pos >= 0) {
            mediaID = mediaID.substring(0, pos);
        }
        return mediaID.split(String.valueOf(CATEGORY_SEPARATOR));
    }

}
