package com.haxor.techpedia.favorite


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class DBManager(private val context: Context) {

    private var dbHelper: DatabaseHelper? = null

    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): DBManager {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    fun insert(id: String,
               judul: String,
               foto_modul: String,
               tanggal: String,
               kategori: String,
               bahasa: String,
               judul_uri: String) {
        val contentValue = ContentValues()
        contentValue.put(DatabaseHelper.ID, id)
        contentValue.put(DatabaseHelper.JUDUL, judul)
        contentValue.put(DatabaseHelper.FOTO_MODUL, foto_modul)
        contentValue.put(DatabaseHelper.TANGGAL, tanggal)
        contentValue.put(DatabaseHelper.KATEGORI,kategori)
        contentValue.put(DatabaseHelper.BAHASA,bahasa)
        contentValue.put(DatabaseHelper.JUDUL_URI,judul_uri)

        database!!.insert(DatabaseHelper.TABLE_NAME, null, contentValue)
    }

    fun fetch(): Cursor? {
        val columns = arrayOf(DatabaseHelper._ID, DatabaseHelper.ID, DatabaseHelper.JUDUL, DatabaseHelper.FOTO_MODUL, DatabaseHelper.TANGGAL,DatabaseHelper.KATEGORI,DatabaseHelper.BAHASA,DatabaseHelper.JUDUL_URI)
        val cursor = database!!.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null)
        cursor?.moveToFirst()
        return cursor
    }

    fun fetchbyid(id: String): Cursor? {
        val columns = arrayOf(DatabaseHelper._ID, DatabaseHelper.ID, DatabaseHelper.JUDUL, DatabaseHelper.FOTO_MODUL, DatabaseHelper.TANGGAL,DatabaseHelper.KATEGORI,DatabaseHelper.BAHASA,DatabaseHelper.JUDUL_URI)
        val cursor = database!!.query(DatabaseHelper.TABLE_NAME, columns, DatabaseHelper.ID + "=" + id, null, null, null, null)
        cursor?.moveToFirst()
        return cursor
    }

    fun update(id: String, judul: String, foto_modul: String, tanggal: String, kategori: String, bahasa: String , judul_uri: String): Int {
        val contentValues = ContentValues()
        contentValues.put(DatabaseHelper.ID, id)
        contentValues.put(DatabaseHelper.JUDUL, judul)
        contentValues.put(DatabaseHelper.FOTO_MODUL, foto_modul)
        contentValues.put(DatabaseHelper.TANGGAL, tanggal)
        contentValues.put(DatabaseHelper.KATEGORI,kategori)
        contentValues.put(DatabaseHelper.BAHASA,bahasa)
        contentValues.put(DatabaseHelper.JUDUL_URI,judul_uri)
        return database!!.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + id, null)
    }


    fun deletebyid(id: String) {
        database!!.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "=" + id, null)

    }



}