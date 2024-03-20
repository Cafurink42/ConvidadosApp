package com.example.convidados

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class GuestForm : AppCompatActivity() {
    private lateinit var databaseApp: SQLiteDatabase
    private lateinit var edtGuestName:EditText
    private lateinit var btnSave:Button
    private var idReceivedParam: Int? = null

 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        val intent: Intent = intent
        idReceivedParam = intent.getIntExtra("ID_SELECIONADO", 0)
        edtGuestName = findViewById(R.id.edtGuestName)
        load()

        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener{
            if(idReceivedParam!!> 0){ //zero é o padrão default dos nomes da lista
                update() //se for maior que zero será um update no nome da lista
            }else{
                save()
            }

        }
    }

    private fun update() {
        val valueName = edtGuestName.text.toString()
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            var sql = "UPDATE guestTable SET name = ? WHERE id= ? "
            val stmt:SQLiteStatement = databaseApp.compileStatement(sql)
            stmt.bindString(1, valueName)
            stmt.bindLong(2, idReceivedParam!!.toLong())
            stmt.executeUpdateDelete()
            databaseApp.close()
            Toast.makeText(this, "Dados Atualizados! ", Toast.LENGTH_SHORT).show() //menasagem de atualização

        }catch (e:Exception){
            e.printStackTrace()
        }
        finish()

    }

    private fun load() {
            try{
                databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
                //retorna o resultado da consulta
                val  cursor: Cursor = databaseApp.rawQuery("SELECT id, name FROM guestTable WHERE id="+idReceivedParam, null)
                //alimentando o array list de convidados
                cursor.moveToFirst()
                edtGuestName.setText(cursor.getString(1))


        }catch (e:Exception){
            e.printStackTrace()
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
            Toast.makeText(this, "Os dados foram salvos! ", Toast.LENGTH_SHORT).show() //menasagem de atualização


        }catch (e:Exception){
            e.printStackTrace()
        }
        finish() //finaliza a tela
    }
}