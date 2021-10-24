package com.dvm.database.impl

import androidx.room.TypeConverter
import java.util.Date

internal class DateConverter {
    @TypeConverter
    fun from(date: Date?): Long? = date?.time

    @TypeConverter
    fun to(time: Long?): Date? =
        if (time != null) Date(time) else null
}