package com.work.criminalintent.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.work.criminalintent.database.room.DateConverter;
import java.util.Date;

@Entity
public class Crime {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long mId;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private Date mDate;
    @ColumnInfo(name = "solved")
    private boolean mSolved;
    @ColumnInfo(name = "suspect")
    private String mSuspect;


    public Crime() {
        mDate = new Date();
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getPhotoFileName() {
        return "IMG_" + getId() + ".jpg";
    }

    @Override
    public String toString() {
        return "Crime{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mDate=" + mDate +
                ", mSolved=" + mSolved +
                ", mSuspect='" + mSuspect + '\'' +
                '}';
    }
}
