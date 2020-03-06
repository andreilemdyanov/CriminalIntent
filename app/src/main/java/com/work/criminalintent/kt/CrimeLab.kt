package com.work.criminalintent.kt

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.work.criminalintent.kt.database.CrimeBaseHelper
import com.work.criminalintent.kt.database.CrimeCursorWrapper
import com.work.criminalintent.kt.database.CrimeTable
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab private constructor(context: Context) {

    private val mDatabase: SQLiteDatabase = CrimeBaseHelper(context).writableDatabase

    companion object {
        private var sCrimelab: CrimeLab? = null
        fun get(context: Context): CrimeLab = sCrimelab ?: CrimeLab(context)
    }

    fun getCrimes(): List<Crime> {
        val crimes = ArrayList<Crime>()
        queryCrimes(null, null).use { cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                crimes.add(cursor.getCrime())
                cursor.moveToNext()
            }
        }
        return crimes
    }


    fun getCrime(uuid: UUID): Crime? {
        queryCrimes(CrimeTable.Cols.UUID + " = ?",
                arrayOf(uuid.toString())).use { cursor ->
            if (cursor.count == 0) return null
            cursor.moveToFirst()
            return cursor.getCrime()
        }
    }

    fun updateCrime(c: Crime) {
        val values = getContentValues(c)
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?",
                arrayOf(c.id.toString()))
    }

    fun addCrime(c: Crime) {
        val values = getContentValues(c)
        mDatabase.insert(CrimeTable.NAME, null, values)
    }

    fun deleteCrime(c: Crime) =
            mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?",
                    arrayOf(c.id.toString()))


    private fun getContentValues(crime: Crime): ContentValues {
        return ContentValues().apply {
            put(CrimeTable.Cols.UUID, crime.id.toString())
            put(CrimeTable.Cols.TITLE, crime.title)
            put(CrimeTable.Cols.DATE, crime.date.time)
            put(CrimeTable.Cols.SOLVED, if (crime.isSolved) 1 else 0)
            put(CrimeTable.Cols.SUSPECT, crime.suspect)
        }
    }

    private fun queryCrimes(whereClause: String?, whereArgs: Array<String>?): CrimeCursorWrapper {
        val cursor: Cursor = mDatabase.query(CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null)
        return CrimeCursorWrapper(cursor)
    }
}