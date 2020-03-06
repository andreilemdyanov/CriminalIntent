package com.work.criminalintent.kt.database

import android.database.Cursor
import android.database.CursorWrapper
import com.work.criminalintent.kt.Crime
import com.work.criminalintent.kt.database.CrimeDbSchema.CrimeTable
import java.util.*

class CrimeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {

    fun getCrime(): Crime {
        val uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID))
        val title = getString(getColumnIndex(CrimeTable.Cols.TITLE))
        val date = getLong(getColumnIndex(CrimeTable.Cols.DATE))
        val isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED))
        val suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT))

        return Crime(UUID.fromString(uuidString)).apply {
            this.title = title
            this.date = Date(date)
            this.isSolved = (isSolved != 0)
            this.suspect = suspect
        }
    }
}