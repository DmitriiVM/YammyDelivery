package com.dvm.database.impl

import com.squareup.sqldelight.ColumnAdapter
import java.util.*

internal object DateAdapter: ColumnAdapter<Date, Long> {

    override fun decode(databaseValue: Long): Date =
        Date(databaseValue)

    override fun encode(value: Date): Long =
        value.time
}