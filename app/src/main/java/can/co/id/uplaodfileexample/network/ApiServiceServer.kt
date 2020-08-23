package can.co.id.uplaodfileexample.network

import can.co.id.uplaodfileexample.network.response.DefaultResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServiceServer {


    @Multipart
    @POST("api-upload")
    fun upload(
        @Part file: MultipartBody.Part
    ): Observable<DefaultResponse>


}