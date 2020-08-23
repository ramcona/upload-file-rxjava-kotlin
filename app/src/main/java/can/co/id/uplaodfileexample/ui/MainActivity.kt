package can.co.id.uplaodfileexample.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import can.co.id.uplaodfileexample.R
import kotlinx.android.synthetic.main.activity_main.*
import net.alhazmy13.mediapicker.Image.ImagePicker
import java.io.File
import java.util.ArrayList

class MainActivity : AppCompatActivity(), MainViews {

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionRequest(this)

        // memeperkenalkan presenter
        presenter = MainPresenter(this, this)

        main_btn_ambil.setOnClickListener {

            /*Mengecek ijin*/
            if (permissionRequest(this)){

                /*Mengambil gambar dengan bantuan Library ImagePicker*/
                ImagePicker.Builder(this)
                    .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                    .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                    .directory(ImagePicker.Directory.DEFAULT)
                    .extension(ImagePicker.Extension.PNG)
                    .scale(600, 600)
                    .allowMultipleImages(false)
                    .enableDebuggingMode(true)
                    .build()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //MENERIMA DATA
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val mpath: ArrayList<String> =
                data!!.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH)!!

            val filess = File(mpath[0])
            val myBitmap = BitmapFactory.decodeFile(filess.absolutePath)

            /*SET KE IMAGE*/
            main_img_data.setImageBitmap(myBitmap)

            /*UPLAOD FILE*/
            presenter.upload(filess)
        }
    }


    /*Meminta ijin*/
    fun permissionRequest(context: Context): Boolean {
        val camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val r_penyimpanan =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        val w_penyimpanan =
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val listPermissionsNeed = ArrayList<String>()
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeed.add(Manifest.permission.CAMERA)
        }
        if (r_penyimpanan != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeed.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (w_penyimpanan != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeed.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (!listPermissionsNeed.isEmpty()) {
            ActivityCompat.requestPermissions(
                context as Activity,
                listPermissionsNeed.toTypedArray(),
                443
            )
            return false
        }

        return true
    }

    override fun onLoading() {
        /*Sedang proses upload*/
    }

    override fun onSuccess() {
        /*uplaod berhasil*/
        
    }

    override fun onFailed(msg: String) {
        /*Upload gagal*/
    }
}