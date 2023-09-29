package com.bitcodetech.sqlitedemo

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*db = openOrCreateDatabase(
            "db_bitcode",
            Activity.MODE_PRIVATE,
            null
        )

        try {
            db.execSQL(
                "create table students(_id integer primary key, name text not null, marks integer)"
            )
        } catch (e: java.lang.Exception) {
        }

        //val query = "insert into students values( $id, '$name', $marks)";
        val values = ContentValues()
        values.put("_id", 109)
        values.put("name", "AA")
        values.put("marks", 78)
        val rowNum = db.insert("students", null, values)

        if (rowNum >= 0) {
            mt("Insertion Successful!")
        } else {
            mt("Insertion failed!")
        }*/

        /*val columns = arrayOf("_id", "name", "marks")
        val whereClause = "marks >= ? and marks <= ?"
        val whereArgs = arrayOf("40", "60")
        val groupByClause = "departments"
        val havingClause = "count(*) > 10"
        val orderByClause = "marks desc"
        db.query(
                "students",
                columns,
                whereClause,
                whereArgs,
                groupByClause,
                havingClause,
                orderByClause
        )*/

        /*val c: Cursor = db.query(
            "students",
            null,
            null,
            null,
            null,
            null,
            "marks desc"
        );

        while(c.moveToNext()) {
            mt("id : ${c.getInt(0)} name : ${c.getString(1)} marks : ${c.getInt(2)}")
        }*/

        //db.close()

        val dbUtil = DbUtil(this)

        /*var isInserted = dbUtil.insertStudent(109, "DD", 68)
        mt("$isInserted")
        isInserted = dbUtil.insertStudent(101, "AA", 65)
        mt("$isInserted")
        isInserted = dbUtil.insertStudent(208, "RR", 89)
        mt("$isInserted")*/

        //var students = dbUtil.getStudents()
        var students = dbUtil.getStudentsWithRawQuery()
        for(student in students) {
            mt(student.toString())
        }

        mt("--------------------------------------")

        val res = dbUtil.updateStudentMarks(208, 60)
        mt("update res: $res")
        students = dbUtil.getStudents()
        for(student in students) {
            mt(student.toString())
        }

        val delRes = dbUtil.deleteStudent(208)
        mt("delete res: $delRes")
        students = dbUtil.getStudents()
        for(student in students) {
            mt(student.toString())
        }

        dbUtil.close()
    }

    private fun mt(text: String) {
        Log.e("tag", text)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
