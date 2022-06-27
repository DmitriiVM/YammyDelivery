package com.dvm.database

import com.squareup.sqldelight.ColumnAdapter
import java.util.*

object DateAdapter : ColumnAdapter<Date, Long> {

    override fun decode(databaseValue: Long): Date =
        Date(databaseValue)

    override fun encode(value: Date): Long =
        value.time
}