package pl.bemideas.covid_19info

import android.app.Activity
import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_world_popup.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WorldPopup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_popup)

        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val code = "total" //countryCode: "total" or ISO 3166-1 alpha-3 country code
            Log.d("HTTP_JSON", ApiFetcher().getJSONString(code))

            val gson = Gson()
            val obiekt = gson.fromJson(ApiFetcher().getJSONString(code), JSONDataClass::class.java)

            runOnUiThread{
                zakazeniaWrldPop.text = obiekt.data.cases.infections.toString()
                zgonyWrldPop.text = obiekt.data.cases.deaths.toString()
                aktywneWrld.text = obiekt.data.cases.activeCases.toString()
                uratowaniWrld.text = obiekt.data.cases.recovered.toString()
                smiertelnoscWrld.text = ("%.2f".format(obiekt.data.cases.mortalityRate * 100)).toString()+"%"
                wsp_wyzdrowienWrld.text = ("%.2f".format(obiekt.data.cases.revoveryRate * 100)).toString()+"%"
            }

        }
    }
}
