package pl.bemideas.covid_19info

import android.util.Log
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection


data class JSONDataClass(
    val `data`: Data,
    val provider: String
)

data class Data(
    val cases: Cases,
    val name: String
)

data class Cases(
    val activeCases: Int,
    val deaths: Int,
    val infections: Int,
    val mortalityRate: Double,
    val recovered: Int,
    val revoveryRate: Double
)

class ApiFetcher {

    fun getUrlBytes(urlSpec: String) : ByteArray {

        val url = URL(urlSpec)
        val connection = url.openConnection() as HttpsURLConnection

        try{
            val out = ByteArrayOutputStream()
            val input = connection.inputStream

            if(connection.responseCode != HttpsURLConnection.HTTP_OK){
                throw IOException(connection.responseMessage)
            }
            var bytesRead: Int
            val buffer = ByteArray(1024)

            do {
                bytesRead = input.read(buffer)
                out.write(buffer, 0, bytesRead)

            }while (input.read(buffer) > 0)
            out.close()

            return out.toByteArray()

        } catch (e: IOException){
            Log.e("ERROR_HTTP_CONNECTION", e.message)
            return ByteArray(0)
        }

        finally {
            connection.disconnect()
        }
    }

    fun getUrlString(urlSpec: String): String{
        return String(getUrlBytes(urlSpec))
    }

    fun getJSONString(): String{
        var jsonString = "Something went wrong..."

        try {
            var url: String = "https://covid-19-data.herokuapp.com/api/cases/total" //instead of 'total': ISO 3166-1 alpha-3 country code
//                              Uri.parse("https://covid-19-data.herokuapp.com/api/cases/total")
//                                  .buildUpon()
//                                  .build().toString()
            jsonString = getUrlString(url)

        } catch (je: JSONException){
            Log.e("JSON_ERROR", je.message)
        }
        return jsonString
    }
}