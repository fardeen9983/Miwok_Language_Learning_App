package com.example.android.miwok;

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageId = NO_IMAGE_PROVIDED;
    private static int NO_IMAGE_PROVIDED = -1;
    private int mMediaId;

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mMediaId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mMediaId = mMediaId;
    }

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageId, int mMediaId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageId = mImageId;
        this.mMediaId = mMediaId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageId=" + mImageId +
                ", mMediaId=" + mMediaId +
                '}';
    }

    public String getDeaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageId() {
        return mImageId;
    }

    public boolean hasImage() {
        if (mImageId != NO_IMAGE_PROVIDED) return true;
        else return false;
    }

    public int getmMediaId() {
        return mMediaId;
    }
}
