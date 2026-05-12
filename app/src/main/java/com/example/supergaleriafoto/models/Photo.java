package com.example.supergaleriafoto.models;

import android.net.Uri;

public class Photo {
    private final long id;
    private final Uri uri;
    private final String displayName;
    private final long dateTaken;
    private final long sizeBytes;

    public Photo(long id, Uri uri, String displayName, long dateTaken, long sizeBytes) {
        this.id = id;
        this.uri = uri;
        this.displayName = displayName;
        this.dateTaken = dateTaken;
        this.sizeBytes = sizeBytes;
    }

    public long getId() {
        return id;
    }

    public Uri getUri() {
        return uri;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }
}
