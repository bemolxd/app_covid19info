package pl.bemideas.covid_19info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val code = "total" //countryCode: "total" or ISO 3166-1 alpha-3 country code
            Log.d("HTTP_JSON", ApiFetcher().getJSONString(code))

            val gson = Gson()
            val obiekt = gson.fromJson(ApiFetcher().getJSONString(code), JSONDataClass::class.java)

//            Log.d("HTTP_JSON", "Infections: "+obiekt.data.cases.infections.toString())
//            Log.d("HTTP_JSON", "Active Cases: "+obiekt.data.cases.activeCases.toString())
//            Log.d("HTTP_JSON", "Deaths: "+obiekt.data.cases.deaths.toString())
//            Log.d("HTTP_JSON", "Recovered: "+obiekt.data.cases.recovered.toString())
//            Log.d("HTTP_JSON", "Mortality Rate: "+(obiekt.data.cases.mortalityRate * 100).toString()+"%")
//            Log.d("HTTP_JSON", "Recovery Rate: "+(obiekt.data.cases.revoveryRate * 100).toString()+"%")

            runOnUiThread {
                zakazeniaWrld.text = obiekt.data.cases.infections.toString()
//                aktywne2.text = obiekt.data.cases.activeCases.toString()
                zgonyWrld.text = obiekt.data.cases.deaths.toString()
//                uratowani2.text = obiekt.data.cases.recovered.toString()
//                smiertelnosc2.text = (obiekt.data.cases.mortalityRate * 100).toString()+"%"
//                wsp_wyzdrowien2.text = (obiekt.data.cases.revoveryRate * 100).toString()+"%"
//
//                debugTV.text = obiekt.data.name.toString() + ", by " + obiekt.provider.toString()
            }

        }
        val jobPL: Job = CoroutineScope(Dispatchers.IO).launch {
            val code = "POL" //countryCode: "total" or ISO 3166-1 alpha-3 country code; TODO: let end user choose country code
            Log.d("HTTP_JSON", ApiFetcher().getJSONString(code))

            val gson = Gson()
            val obiekt = gson.fromJson(ApiFetcher().getJSONString(code), JSONDataClass::class.java)

//            Log.d("HTTP_JSON", "Infections: "+obiekt.data.cases.infections.toString())
//            Log.d("HTTP_JSON", "Active Cases: "+obiekt.data.cases.activeCases.toString())
//            Log.d("HTTP_JSON", "Deaths: "+obiekt.data.cases.deaths.toString())
//            Log.d("HTTP_JSON", "Recovered: "+obiekt.data.cases.recovered.toString())
//            Log.d("HTTP_JSON", "Mortality Rate: "+(obiekt.data.cases.mortalityRate * 100).toString()+"%")
//            Log.d("HTTP_JSON", "Recovery Rate: "+(obiekt.data.cases.revoveryRate * 100).toString()+"%")

            runOnUiThread {
                zakazenia2.text = obiekt.data.cases.infections.toString()
                aktywne2.text = obiekt.data.cases.activeCases.toString()
                zgony2.text = obiekt.data.cases.deaths.toString()
                uratowani2.text = obiekt.data.cases.recovered.toString()
                smiertelnosc2.text = (obiekt.data.cases.mortalityRate * 100).toString() + "%"
                wsp_wyzdrowien2.text = (obiekt.data.cases.revoveryRate * 100).toString() + "%"

//                debugTV.text = obiekt.data.name.toString() + ", by " + obiekt.provider.toString()
            }

        }
        job.start()
        jobPL.start()
        if (job.isCompleted && jobPL.isCompleted) {
            job.cancel()
            jobPL.cancel()
        }
    }
}
