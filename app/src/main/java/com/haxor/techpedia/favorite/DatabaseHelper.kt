package com.haxor.techpedia.favorite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {

        // Table Name
        val TABLE_NAME = "modules"

        // Table columns
        val _ID = "_id"

        val ID = "id"
        val JUDUL = "judul"
        val FOTO_MODUL = "foto_modul"
        val TANGGAL = "tanggal"
        val KATEGORI = "kategori"
        val BAHASA = "bahasa"
        val JUDUL_URI = "judul_uri"


        // Database Information
        internal val DB_NAME = "db_elearning.DB"

        // database version
        internal val DB_VERSION = 1

        // Creating table query
        private val CREATE_TABLE = "create table " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " INTEGER , " +
                JUDUL + " TEXT , " +
                FOTO_MODUL + " TEXT ," +
                TANGGAL + " TEXT , " +
                KATEGORI + " TEXT , " +
                BAHASA + " TEXT , " +
                JUDUL_URI + " TEXT);"
    }
}