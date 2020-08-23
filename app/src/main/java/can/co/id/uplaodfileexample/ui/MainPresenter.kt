package can.co.id.uplaodfileexample.ui

import android.content.Context
import can.co.id.uplaodfileexample.network.ClientService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MainPresenter(val context: Context, var views:MainViews) {
    var disposable: Disposable? = null
    val ApiServiceServerFile by lazy { ClientService().create() }
    
    fun upload(file: File){
        views.onLoading()
        
        var image: MultipartBody.Part? = null
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        
        /*@Param image di isi sesuai paramter ada di API*/
        image = MultipartBody.Part.createFormData("image", file.getName(), requestFile)
        
        views.onLoading()
        disposable = ApiServiceServerFile.upload(
            image
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    if (result.error){
                        views.onFailed(result.msg)
                    }else{
                        views.onSuccess()
                    }
                },
                { error ->
                    try {
                        if (error.message!!.contains("Failed to connect", true)) {
                            views.onFailed("Terjadi kesalahan,periksa internet anda")
                        } else {
                            views.onFailed("Terjadi Kesalahan, ulangi beberapa saat lagi")
                        }
                    } catch (e: KotlinNullPointerException) {
                        views.onFailed("Terjadi Kesalahan, ulangi beberapa saat lagi")

                    }
                }

            )

    }
}