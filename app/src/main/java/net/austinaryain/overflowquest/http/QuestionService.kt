package net.austinaryain.overflowquest.http

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface QuestionService {

    @GET("questions")
    fun getQuestions(@QueryMap params: Map<String, String>):
            Call<QuestionsResponse>

}