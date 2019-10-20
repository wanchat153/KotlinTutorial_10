package com.example.kotlintutorial_10

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

private const val  TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val database = baseContext.openOrCreateDatabase("sqlite-test-1.db", Context.MODE_PRIVATE, null)
        database.execSQL("DROP TABLE IF EXISTS contacts")
        var sql = "CREATE TABLE IF NOT EXISTS contacts(_id INTEGER PRIMARY KEY NOT NULL, name TEXT, phone INTEGER, email TEXT)"
        Log.d(TAG, "onCreate: sql is $sql")
        database.execSQL(sql)

        sql = "INSERT INTO contacts(name, phone, email) VALUES('tim', 6456789, 'tim@email.com')"
        Log.d(TAG, "onCreate: sql is $sql")
        database.execSQL(sql)

        val value = ContentValues().apply {
            put("name", "Fred")
            put("phone", 12345)
            put("email", "fred@nurk.com")
        }

//        val value = ContentValues().apply {
//            put("name", "Fred")
//            put("phone", 12345)
//            put("email", "fred@nurk.com")
//        }

        val generatedId = database.insert("contacts", null, value)

        val query = database.rawQuery("SELECT * FROM contacts", null)
        query.use {
            while (it.moveToNext()){
                with(query){
                    val id = getLong(0)
                    val name = getString(1)
                    val phone = getInt(2)
                    val email = getString(3)
                    val result = "ID: $id. Name = $name phone = $phone email = $email"
                    Log.d(TAG, "onCreate: reading data $result")
                }
            }
        }
        database.close()

        Log.d(TAG, "onCreate: record added with id $generatedId")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
