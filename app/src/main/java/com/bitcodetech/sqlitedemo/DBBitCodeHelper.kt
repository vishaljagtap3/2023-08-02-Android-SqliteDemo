package com.bitcodetech.sqlitedemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBBitCodeHelper(
    context : Context,
    dbName : String,
    factory : CursorFactory?,
    version : Int
) : SQLiteOpenHelper(context, dbName, factory, version){

    override fun onCreate(db: SQLiteDatabase?) {
        Log.e("tag", "oncreate of sqliteopenhelper called...")
        db?.execSQL(
            "create table students(_id integer primary key, name text not null, marks integer)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.e("tag", "onUpgrade called...")
        db?.execSQL("alter table students add column department text")
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

}