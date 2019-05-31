package com.example.alphacaller

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class Database: SQLiteOpenHelper {
    object Statics {
        val DB_NAME = "MobileDatabase"
        val TABLE_NAME = "NumberTable"
        val COLUMN_NUMBER = "MobileNumber"
        var NumberList = ArrayList<String>()
        val DB_VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + Statics.TABLE_NAME + "( " + Statics.COLUMN_NUMBER + " STRING);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(
        context,
        name,
        factory,
        version
    )

    constructor(context: Context?) : super(
        context,
        Statics.DB_NAME,
        null,
        Statics.DB_VERSION
    )

    fun storeNumber(number: String?) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Statics.COLUMN_NUMBER, number)
        db.insert(Statics.TABLE_NAME, null, contentValues)
        db.close()
    }

    fun queryDB(): ArrayList<String>? {
        try {
            val db = this.readableDatabase
            val queryParams = "SELECT * FROM " + Statics.TABLE_NAME
            val cSor = db.rawQuery(queryParams, null, null)
            if(cSor.moveToFirst()) {
                do {
                    val number = cSor.getString(cSor.getColumnIndexOrThrow(Statics.COLUMN_NUMBER))
                    Statics.NumberList.add(number)
                }while(cSor.moveToNext())
            }
            else
            {
                return null
            }
            cSor.close()
            db.close()
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
        return Statics.NumberList
    }

    fun deleteData() {
        val db = this.writableDatabase
        db.delete(Statics.TABLE_NAME, null, null)
        db.close()
    }
}