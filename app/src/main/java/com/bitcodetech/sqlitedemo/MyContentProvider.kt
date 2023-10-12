package com.bitcodetech.sqlitedemo

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MyContentProvider : ContentProvider() {

    private lateinit var db: SQLiteDatabase

    val ALL_STUDENTS = 1
    val STUDENTS_FILTER = 2
    val ALL_SUBJECTS = 3
    val SUBJECTS_FILTER = 4

    val uriMatcher = UriMatcher(-1)

    init {
        uriMatcher.addURI("in.bitcode.EDUCATION", "students", ALL_STUDENTS)
        uriMatcher.addURI("in.bitcode.EDUCATION", "students/#", STUDENTS_FILTER)
        uriMatcher.addURI("in.bitcode.EDUCATION", "subjects", ALL_SUBJECTS)
        uriMatcher.addURI("in.bitcode.EDUCATION", "subjects/#", SUBJECTS_FILTER)
    }

    override fun onCreate(): Boolean {
        db = DBBitCodeHelper(
            context!!,
            "db_bitcode",
            null,
            1
        ).writableDatabase

        return true
    }

    override fun query(
        contentUri: Uri,
        columns: Array<out String>?,
        where: String?,
        whereArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        val pathSegments = contentUri.pathSegments

        when (uriMatcher.match(contentUri)) {
            ALL_STUDENTS -> {
                return db.query(
                    "students",
                    columns,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            }

            STUDENTS_FILTER -> {
                return db.query(
                    "students",
                    columns,
                    "_id = ?",
                    arrayOf("${pathSegments[1]}"),
                    null,
                    null,
                    sortOrder
                )
            }

            ALL_SUBJECTS -> {
                return db.query(
                    "subjects",
                    columns,
                    where,
                    whereArgs,
                    null,
                    null,
                    sortOrder
                )
            }

            SUBJECTS_FILTER -> {
                return db.query(
                    "subjects",
                    null,
                    "_id = ?",
                    arrayOf("${pathSegments[1]}"),
                    null,
                    null,
                    null
                )
            }
        }

        return null
    }

    /* override fun query(
         contentUri: Uri,
         columns: Array<out String>?,
         where: String?,
         whereArgs: Array<out String>?,
         sortOrder: String?
     ): Cursor? {

         val pathSegments = contentUri.pathSegments

         when {
             (pathSegments.size == 1 && pathSegments[0].equals("students")) -> {
                 return db.query(
                     "students",
                     columns,
                     null,
                     null,
                     null,
                     null,
                     null
                 )
             }

             (pathSegments.size == 2 && pathSegments[0].equals("students")) -> {
                 return db.query(
                     "students",
                     columns,
                     "_id = ?",
                     arrayOf("${pathSegments[1]}"),
                     null,
                     null,
                     sortOrder
                 )
             }

             (pathSegments.size == 1 && pathSegments[0].equals("subjects")) ->
                 return db.query(
                     "subjects",
                     columns,
                     where,
                     whereArgs,
                     null,
                     null,
                     sortOrder
                 )

             (pathSegments.size == 2 && pathSegments[0].equals("subjects")) ->
                 return db.query(
                     "subjects",
                     null,
                     "_id = ?",
                     arrayOf("${pathSegments[1]}"),
                     null,
                     null,
                     null
                 )
         }

         return null
     }*/

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            ALL_STUDENTS -> {
                val rowNum = db.insert(
                    "students",
                    null,
                    values
                )

                if(rowNum >= 0) {
                    //ins success
                    return Uri.withAppendedPath(uri, "${values!!.getAsInteger("_id")}")
                }

                //ins failed
                return null
            }
        }

        return null
    }

    override fun delete(uri: Uri, where: String?, whereArgs: Array<out String>?): Int {

        //way 2
        when( uriMatcher.match(uri)) {
            ALL_STUDENTS -> {
                return db.delete("students", null, null)
            }

            STUDENTS_FILTER -> {
                val where = "_id = ?"
                val whereArgs = arrayOf( uri.pathSegments[1])
                return db.delete("students", where, whereArgs)
            }
        }


        //way 1
        /*when( uriMatcher.match(uri)) {
            ALL_STUDENTS -> {
                if(where == null) {
                    return 0
                }

                return db.delete("students", where, whereArgs)
            }
        }*/

        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, where: String?, whereArgs: Array<out String>?): Int {

        when(uriMatcher.match(uri)) {

            ALL_STUDENTS -> {
                return db.update(
                    "students",
                    values,
                    null,
                    null
                )
            }

            STUDENTS_FILTER -> {
                return db.update(
                    "students",
                    values,
                    "_id = ?",
                    arrayOf("${uri.pathSegments[1]}")
                )
            }


        }

        return 0;

    }


}