package net.austinaryain.overflowquest.data

import androidx.room.TypeConverter
import com.google.gson.Gson


object AnswersConverter {

    @TypeConverter
    fun toString(answerList: MutableList<Answer>): String {
        var gson = Gson()
        return gson.toJson(answerList)
    }

//    @TypeConverter
//    fun fromString(json: String): MutableList<Answer> {
//        var gson = Gson()
//        return gson.fromJson(json, MutableList<Answer>::class.java)
//    }

}