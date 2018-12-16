package net.austinaryain.overflowquest.http

import com.google.gson.Gson
import net.austinaryain.overflowquest.data.Answer
import net.austinaryain.overflowquest.data.Question
import okhttp3.*
import java.io.InputStream
import java.io.IOException
import org.json.JSONArray
import org.json.JSONObject


open class SOService {
    private val QUESTION_FILTER: String = "!)5IW-5QvYx.h(*2yhEafL-9rg0bY"
    private val ACCESS_TOKEN: String = "YOUR_ACCESS_TOKEN"
    private val CLIENT_KEY: String = "YOUR_CLIENT_KEY"

    private var page = 1
    private val API_BASE: String = "https://api.stackexchange.com/2.2/"

    private var questions: MutableList<Question> = mutableListOf()
    private val gson: Gson = Gson()

    fun getQuestions(): MutableList<Question> {
        var url =
            "${API_BASE}questions?access_token=$ACCESS_TOKEN&key=$CLIENT_KEY&page=$page&pagesize=100&order=desc&sort=activity&site=stackoverflow&filter=$QUESTION_FILTER"

        run(url)

        return questions
    }

    @Throws(Exception::class)
    fun run(url: String): Boolean {
        var has_more = false
        val request = Request.Builder()
            .url(url)
            .build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                response.body().use { responseBody ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val jsonData = response.body()?.string()
                    val Jobject = JSONObject(jsonData)
                    has_more = Jobject.optBoolean("has_more", false)
                    val Jarray = Jobject.getJSONArray("items")
                    for (i in 0 until Jarray.length()) {
                        var obj = Jarray.getJSONObject(i)
                        if (obj.optBoolean("is_answered", false) && obj.optInt(
                                "answer_count",
                                0
                            ) > 1 && obj.optInt("accepted_answer_id", 0) > 0
                        ) {

                            var answerArray = obj.getJSONArray("answers")
                            var answerList = mutableListOf<Answer>()
                            for (y in 0 until answerArray.length()) {
                                var ansObj = answerArray.getJSONObject(y)
                                answerList.add(gson.fromJson(ansObj.toString(), Answer::class.java))
                            }

                            var questionObj = gson.fromJson<Question>(obj.toString(), Question::class.java)
                            questionObj.answers = answerList
                            questions.add(questionObj)
                        }
                    }
                }
            }
        })
        return has_more
    }

}

