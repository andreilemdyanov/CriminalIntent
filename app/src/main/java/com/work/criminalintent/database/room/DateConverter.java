package com.work.criminalintent.database.room;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public Date TimestampToDate(Long date) {
        if (date == null) {
            return null;
        } else {
            return new Date(date);
        }
    }

}