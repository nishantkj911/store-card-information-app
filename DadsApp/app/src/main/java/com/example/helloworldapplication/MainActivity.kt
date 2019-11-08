package com.example.helloworldapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val SHARED_PREF_STRING: String = "sharedPrefString"
    val SHARED_PREF_ARRAYLIST_STRING: String = "sharedPrefArrayListString"

    companion object {
        var cards: ArrayList<Card>? = ArrayList<Card>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        initCards()
        loadData()
        initRecyclerView()

        // opening the card page to show card information
        fab.setOnClickListener {
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AddNewCardActivity::class.java))
        }

//        TODO("Adjust size of text in name text view and card number text view based on the length of string")
    }

    private fun initCards() {
        cards!!.add(
            Card(
                "SBI Card",
                "NISHANT KARTIKEYA J",
                "4202541120209999",
                GregorianCalendar(2013, 11, 1),
                GregorianCalendar(2023, 11, 1),
                366
            )
        )
        cards!!.add(
            Card(
                "Axis Card",
                "NISHANT KARTIKEYA J",
                "4202541120209999",
                GregorianCalendar(2013, 11, 1),
                GregorianCalendar(2023, 11, 1),
                366
            )
        )
    }

    private fun initRecyclerView() {
        var adapter: MyRecyclerViewAdapter = MyRecyclerViewAdapter(this, cards!!)
        var rv: RecyclerView = findViewById(R.id.recyclerView)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }

    private fun loadData() {
        var sp: SharedPreferences? = getSharedPreferences(SHARED_PREF_STRING, MODE_PRIVATE)
        var jsonString: String? = sp!!.getString(SHARED_PREF_ARRAYLIST_STRING, "")

        cards = Gson().fromJson(jsonString, object : TypeToken<ArrayList<Card>>() {}.type)
        if (cards == null)
            cards = ArrayList<Card>()
        Log.d(packageName + "LogTag", "Loaded data")
    }

    fun saveData() {
        var sp: SharedPreferences = getSharedPreferences(SHARED_PREF_STRING, MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sp.edit()
        var gson: Gson = Gson()
        var jsonString: String = gson.toJson(MainActivity.cards)

        editor.putString(SHARED_PREF_ARRAYLIST_STRING, jsonString)
        editor.apply()
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
    }
}
