package com.blogspot.ryo813.iot

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_main.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        MyAsyncTask().execute()

        FuelManager.instance.basePath = "http://192.168.11.10"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "text/plain")

        val CeilingLightOn = findViewById(R.id.CeilingLightOn) as Button
        val CeilingLightOff = findViewById(R.id.CeilingLightOff) as Button
        val HumidifierPower = findViewById(R.id.HumidifierPower) as Switch
        val Humidity = findViewById(R.id.Humidity) as Button
        val Momentum = findViewById(R.id.Momentum) as Button



        CeilingLightOn.setOnClickListener {
            val body: String = "t:CeilingLight, m:o,"
            val (request, response, result) = Fuel.post("").body(body).response()
            Fuel.post("").body(body).response { request, response, result -> }
        }


        CeilingLightOff.setOnClickListener {
            val body: String = "t:CeilingLight, m:f,"
            val (request, response, result) = Fuel.post("").body(body).response()
            Fuel.post("").body(body).response { request, response, result -> }
        }


        Humidity.setOnClickListener {
            val body: String = "t:Humidifier, m:h,"
            val (request, response, result) = Fuel.post("").body(body).response()
            Fuel.post("").body(body).response { request, response, result -> }
        }


        Momentum.setOnClickListener {
            val body: String = "t:Humidifier, m:m,"
            val (request, response, result) = Fuel.post("").body(body).response()
            Fuel.post("").body(body).response { request, response, result -> }
        }


        HumidifierPower.setOnCheckedChangeListener { comButton, isChecked ->
            if (isChecked) {
                val body: String = "t:Humidifier, m:o,"
                val (request, response, result) = Fuel.post("").body(body).response()
                Fuel.post("").body(body).response { request, response, result -> }
            } else {
                val body: String = "t:Humidifier, m:f,"
                val (request, response, result) = Fuel.post("").body(body).response()
                Fuel.post("").body(body).response { request, response, result -> }
            }
        }
    }



    inner class MyAsyncTask: AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg p0: Void?): String? {
            return getHtml()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val tview = findViewById<TextView>(R.id.textView4)
            tview.setText(result)
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


fun getHtml(): String {
    val client = OkHttpClient()
    val req = Request.Builder().url("http://192.168.11.10").get().build()
    val resp = client.newCall(req).execute()
    return resp.body()!!.string()
}
