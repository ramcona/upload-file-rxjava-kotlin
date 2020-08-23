package can.co.id.uplaodfileexample.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ClientService {

    fun create(): ApiServiceServer {
        var cTO:Long = 60
        var wTO:Long = 60
        var rTO:Long = 60

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient: OkHttpClient
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(cTO, TimeUnit.SECONDS)
            .writeTimeout(wTO, TimeUnit.SECONDS)
            .readTimeout(rTO, TimeUnit.SECONDS)
            .build()

        var url = "url anda"


        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create())
            .baseUrl(url)
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiServiceServer::class.java)
    }

}