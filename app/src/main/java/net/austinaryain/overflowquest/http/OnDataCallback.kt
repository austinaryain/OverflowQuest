package net.austinaryain.overflowquest.http

interface OnDataCallback<T> {
    fun onSuccess(data: T)
    fun onFailure(message: String)
}