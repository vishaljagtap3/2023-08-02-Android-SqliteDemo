package com.bitcodetech.sqlitedemo

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbUtil(
    context: Context
) {
    private val db : SQLiteDatabase

    init {
        db = DBBitCodeHelper(
            context,
            "db_bitcode",
            null,
            1
        ).writableDatabase

    }


    /*init {
        db = context.openOrCreateDatabase("db_bitcode", Activity.MODE_PRIVATE, null)
        try {
            db.execSQL(
                "create table students(_id integer primary key, name text not null, marks integer)"
            )
        } catch (e: java.lang.Exception) {
        }
    }*/

    fun insertSubject(
        id : Int,
        title : String,
        rating : Int
    ) : Boolean {

        val values = ContentValues()
        values.put("_id", id)
        values.put("title", title)
        values.put("rating", rating)

        //val rowNum = db.insert("students", "department, rating", values)
        val rowNum = db.insert("subjects", null, values)


        return rowNum >= 0;
    }

    fun insertStudent(
        id : Int,
        name : String,
        marks : Int
    ) : Boolean {

        val values = ContentValues()
        values.put("_id", id)
        values.put("name", name)
        values.put("marks", marks)

        //val rowNum = db.insert("students", "department, rating", values)
        val rowNum = db.insert("students", null, values)


        return rowNum >= 0;
    }

    fun getStudents() : ArrayList<Student> {
        val c = db.query(
            "students",
            null,
            null,
            null,
            null,
            null,
            "marks desc"
        )

        val students = ArrayList<Student>()
        while(c.moveToNext()) {
            students.add(
                Student(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2)
                )
            )
        }

        return students
    }

    fun getStudentsWithRawQuery() : ArrayList<Student> {
        val c = db.rawQuery("select _id, name, marks from students", null)

        val students = ArrayList<Student>()
        while(c.moveToNext()) {
            students.add(
                Student(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2)
                )
            )
        }

        return students
    }

    fun updateStudentMarks(id : Int, marks : Int) : Boolean {

        val values = ContentValues()
        values.put("marks", marks)

        val count = db.update("students", values, "_id = ?", arrayOf("$id"))

        return count > 0
    }

    fun deleteStudent(id : Int) : Boolean{

        val count = db.delete(
            "students",
            "_id = ?",
            arrayOf("$id")
        )

        return count > 0
    }

    fun close() {
        db.close()
    }


}