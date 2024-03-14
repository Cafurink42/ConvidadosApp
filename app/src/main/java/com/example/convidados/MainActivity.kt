package com.example.convidados

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var databaseApp:SQLiteDatabase
    private var guestList: ArrayList<GuestModel> = ArrayList()
    private lateinit var listViewGuests: ListView
    private lateinit var btnAdd: FloatingActionButton

    override fun onResume() { //deixa a MainActivity em resume atualiza a lista
        super.onResume()
        getGuestList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener{
            val intent = Intent(this, GuestForm::class.java)
            startActivity(intent)

        }
         listViewGuests = findViewById<ListView>(R.id.listViewGuests)
        createDatabase()
        getGuestList()

    }
    private fun getGuestList(){
        try{
            guestList = ArrayList()
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)

            //retorna o resultado da consulta
            val  cursor: Cursor = databaseApp.rawQuery("SELECT id, name FROM guestTable", null)
            //alimentando o array list de convidados
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                guestList.add(GuestModel(cursor.getInt(0), cursor.getString(1)))
                cursor.moveToNext()

            }
            cursor.close()

            val adapterGuest: ArrayAdapter<GuestModel> = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                guestList
            )
            listViewGuests.adapter = adapterGuest;


        }
        catch (e:Exception){
            e.printStackTrace()

        }
    }
    private fun createDatabase(){
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            databaseApp.execSQL("CREATE TABLE IF NOT EXISTS guestTable" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)")
            databaseApp.close()
        }catch (e:Exception){
            e.printStackTrace() //vai imprimir o erro caso tenha
        }
    }
}