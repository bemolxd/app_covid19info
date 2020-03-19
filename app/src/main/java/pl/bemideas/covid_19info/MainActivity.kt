package pl.bemideas.covid_19info

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.math.round
import kotlin.math.roundToInt

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
                zgonyWrld.text = obiekt.data.cases.deaths.toString()

//                zakazeniaWrldPop.text = obiekt.data.cases.infections.toString()
//                zgonyWrldPop.text = obiekt.data.cases.deaths.toString()
//                aktywneWrld.text = obiekt.data.cases.activeCases.toString()
//                uratowaniWrld.text = obiekt.data.cases.recovered.toString()
//                smiertelnoscWrld.text = (obiekt.data.cases.mortalityRate * 100).toString()+"%"
//                wsp_wyzdrowienWrld.text = (obiekt.data.cases.revoveryRate * 100).toString()+"%"
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
                smiertelnosc2.text = ("%.2f".format(obiekt.data.cases.mortalityRate * 100)).toString() + "%"
                wsp_wyzdrowien2.text = ("%.2f".format(obiekt.data.cases.revoveryRate * 100)).toString() + "%"

//                debugTV.text = obiekt.data.name.toString() + ", by " + obiekt.provider.toString()
            }

        }
        job.start()
        jobPL.start()
        if (job.isCompleted && jobPL.isCompleted) {
            job.cancel()
            jobPL.cancel()
        }

        refreshBtn.setOnClickListener{

            val animation = AnimationUtils.loadAnimation(this,R.anim.rotate)
            refreshBtn.startAnimation(animation)

            //TODO: proper refresh code
        }

        worldCard.setOnClickListener {
            val popIntent = Intent(this, WorldPopup::class.java)
            startActivity(popIntent)
        }

        whoBtn.setOnClickListener {

            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.who.int/news-room/q-a-detail/q-a-coronaviruses")
            startActivity(openURL)
        }

        mzBtn.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.gov.pl/web/koronawirus")
            startActivity(openURL)
        }


    }
}