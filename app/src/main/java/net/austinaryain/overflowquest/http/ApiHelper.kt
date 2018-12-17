package net.austinaryain.overflowquest.http

import com.google.gson.GsonBuilder
import net.austinaryain.overflowquest.data.Question
import retrofit2.converter.gson.GsonConverterFactory
import net.austinaryain.overflowquest.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.reflect.Modifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHelper {

    private var retrofit: Retrofit

    init {
        val builder = OkHttpClient.Builder()
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_BASE)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getQuestions(onDataCallback: OnDataCallback<QuestionsResponse>) {

        var params: MutableMap<String, String> = mutableMapOf()
        params["access_token"] = Constants.ACCESS_TOKEN
        params["key"] = Constants.CLIENT_KEY
        params["page"] = "1"
        params["pagesize"] = "100"
        params["order"] = "desc"
        params["sort"] = "activity"
        params["site"] = "stackoverflow"
        params["filter"] = Constants.QUESTION_FILTER

        val questionService = retrofit.create(QuestionService::class.java)
        val call = questionService.getQuestions(params)

        call.enqueue(object : Callback<QuestionsResponse> {
            override fun onResponse(call: Call<QuestionsResponse>, response: Response<QuestionsResponse>) {
                if (response.isSuccessful) {
                    onDataCallback.onSuccess(response.body()!!)
                } else {
                    onDataCallback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<QuestionsResponse>, t: Throwable) {
                onDataCallback.onFailure(t.message!!)
            }
        })
    }
}