package net.adiwilaga.githubuserfinder.repo

import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import net.adiwilaga.githubuserfinder.api.APIBuilder
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

object GHUserRepository  {

    var job:CompletableJob? =null

    fun getUsers(q: String,p:Int, listener:GHUserListListener) {
        cancelJob()
        job = Job()
        job?.let { myjob->
            CoroutineScope(IO +myjob).launch {


                try {
                    var users=APIBuilder.apiServices.getUsers(q,p,10)

                    withContext(Main){

                        if(users.totalCount>0)
                        listener.onSuccess(users)
                        else
                            listener.onError("No Match Found")
                    }
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> {
                            withContext(Main){
                            listener.onError("Connection Problem")
                        }}
                        is HttpException -> {

                            withContext(Main){
                                listener.onError(convertErrorBody(throwable))
                            }
                        }
                        else -> {
                            withContext(Main){
                                listener.onError(throwable.message.toString())
                            }
                        }
                    }
                }

                myjob.complete()

            }
        }


    }

    fun cancelJob(){
        job?.cancel()
    }


    private fun convertErrorBody(throwable: HttpException):String {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val inputAsString = it.readByteString().string(Charset.defaultCharset())
                var obj= Gson().fromJson<JsonObject>(inputAsString,JsonObject::class.java)
                obj.get("message").asString
            }.toString()
        } catch (exception: Exception) {
            exception.message.toString()
        }
    }
}