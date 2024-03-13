package com.example.convidados

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class GuestForm : AppCompatActivity() {
    private lateinit var databaseApp: SQLiteDatabase
    private lateinit var edtGuestName:EditText
    private lateinit var btnSave:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)
        edtGuestName = findViewById(R.id.edtGuestName)
        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener{
            save()
        }
    }
    private fun save(){
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            val sql = "INSERT INTO guestTable (name) VALUES (?)" //parametro que será adicioando
            val stmt = databaseApp.compileStatement(sql) //statement verifica se é um sql válido
            stmt.bindString(1, edtGuestName.text.toString())
            stmt.executeInsert() //executa o insert no banco
            databaseApp.close()

        }catch (e:Exception){
            e.printStackTrace()
        }
        finish() //finaliza a tela
    }
}