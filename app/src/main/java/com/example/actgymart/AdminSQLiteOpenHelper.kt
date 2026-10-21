package com.example.actgymart

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(
    context: Context,
    name: String = "administracion",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table usuarios(usuario text primary key, nombre text, pass text, email text unique)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists usuarios")
        onCreate(db)
    }

    fun existeUsuario(usuario: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("select usuario from usuarios where usuario = ?", arrayOf(usuario))
        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }

    fun existeEmail(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("select email from usuarios where email = ?", arrayOf(email))
        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }

    fun registrarUsuario(usuario: String, nombre: String, pass: String, email: String): Boolean {
        if (existeUsuario(usuario) || existeEmail(email)) {
            return false
        }
        val db = writableDatabase
        val registro = ContentValues().apply {
            put("usuario", usuario)
            put("nombre", nombre)
            put("pass", pass)
            put("email", email)
        }
        val resultado = db.insert("usuarios", null, registro)
        db.close()
        return resultado != -1L
    }

    fun validarUsuario(usuario: String, pass: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "select usuario from usuarios where usuario = ? and pass = ?",
            arrayOf(usuario, pass)
        )
        val esValido = cursor.count > 0
        cursor.close()
        db.close()
        return esValido
    }
}