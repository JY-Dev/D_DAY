package com.jaeyoungkim.app.d_day.Activty

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*
import com.gun0912.tedpermission.TedPermission
import com.gun0912.tedpermission.PermissionListener
import android.provider.MediaStore
import android.content.Intent
import java.io.File
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.IOException
import java.text.SimpleDateFormat
import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.jaeyoungkim.app.d_day.DataProcess
import com.jaeyoungkim.app.d_day.Dialog.ImgSelDialog
import com.jaeyoungkim.app.d_day.Format
import com.jaeyoungkim.app.d_day.R




class SettingActivity : AppCompatActivity() {
    private var toggleData = false
    private var selRepeat = ""
    private var title = ""
    private var calMil = 0L
    var data = mutableListOf<DataPage>()
    private lateinit var format: Format
    private val REQUEST_TAKE_PHOTO = 2222
    private val REQUEST_TAKE_ALBUM = 3333
    //private val REQUEST_IMAGE_CROP = 4444
    private var mCurrentPhotoPath: String? = null
    private var imageUri: Uri? = null
    private var dataProcess = DataProcess()
    private var dday = 0L
    private var modify = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER

        val mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = resources.getString(R.string.forward_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())




        title = intent.getStringExtra("title")
        selRepeat = intent.getStringExtra("selRepeat")
        toggleData = intent.getBooleanExtra("toggleData",true)
        calMil = intent.getLongExtra("cal",0)
        modify = intent.getIntExtra("modify",-1)

        format = Format()

        init()
        dday = format.dday(calMil,selRepeat)
        d_day_tv.text = if (dday != 0L)format.ddayCheck(dday,d_day_sign).toString() else "DAY"

        img_sel_btn.setOnClickListener {
            tedPermission(this)
        }
        sel_complete_btn.setOnClickListener {

            val dataMutable = dataProcess.dataLoad(this)
            if (dataMutable!=null){
                // 수정할경우
                if (modify !=-1){
                    dataMutable[modify] = DataPage(
                        title,
                        selRepeat,
                        toggleData,
                        calMil,
                        imageUri.toString()
                    )
                } else {
                    dataMutable.add(
                        DataPage(
                            title,
                            selRepeat,
                            toggleData,
                            calMil,
                            imageUri.toString()
                        )
                    )
                }
                dataProcess.dataSave(this,dataMutable)
            } else {
                data.add(
                    DataPage(
                        title,
                        selRepeat,
                        toggleData,
                        calMil,
                        imageUri.toString()
                    )
                )
                dataProcess.dataSave(this,data)
            }
            val intent = Intent(this, ShowActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            if (mInterstitialAd.isLoaded){
                mInterstitialAd.show()
            }

        }
    }

    private fun init(){
        title_tv.text = title
        seldate_tv.text = format.dateFormat1(calMil)
        if (modify != -1){
            val dataMutable = dataProcess.dataLoad(this)
            if (dataMutable!=null) {
                img_layout.setImageURI(Uri.parse(dataMutable[modify].imgUrl))
                imageUri = Uri.parse(dataMutable[modify].imgUrl)
            }
        }
    }

    private fun tedPermission(context: Context) {

        val permissionListener = object : PermissionListener {
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                //권한 실패시
            }

            override fun onPermissionGranted() {
                ImgSelDialog(context, { captureCamera() }, { getAlbum() })
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(resources.getString(R.string.permission_2))
            .setDeniedMessage(resources.getString(R.string.permission_1))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode != RESULT_OK) return
        when(requestCode){
            REQUEST_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        galleryAddPic()
                        img_layout.setImageURI(imageUri)
                        System.out.println("imgUri=$imageUri")
                    } catch (e: Exception) {
                    }

                } else {
                    Toast.makeText(this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_TAKE_ALBUM ->{
                if (resultCode == Activity.RESULT_OK) {
                    if (data?.data != null) {
                        try {
                            imageUri = data.data
                            img_layout.setImageURI(imageUri)
                            //cropImage()
                        } catch (e: Exception) {
                        }
                    }
                }
            }

        }
    }

    fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_$timeStamp.jpg"
        val storageDir = File(Environment.getExternalStorageDirectory().toString() + "/Pictures", "d_day")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val imageFile = File(storageDir, imageFileName)
        mCurrentPhotoPath = imageFile.absolutePath

        return imageFile
    }


    private fun captureCamera() {
        val state = Environment.getExternalStorageState()
        // 외장 메모리 검사
        if (Environment.MEDIA_MOUNTED == state) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(packageManager) != null) {
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                    System.out.println("error="+ex.stackTrace)
                }

                if (photoFile != null) {
                    // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함
                    val providerURI = FileProvider.getUriForFile(this, packageName, photoFile)
                    imageUri = providerURI
                    // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        } else {
            Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun galleryAddPic() {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        val f = File(mCurrentPhotoPath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        sendBroadcast(mediaScanIntent)
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.setDataAndType(imageUri, "image/*")
        startActivityForResult(intent, REQUEST_TAKE_ALBUM)
    }

//     카메라 전용 크랍
//   private fun cropImage() {
//
//        val cropIntent = Intent("com.android.camera.action.CROP")
//
//        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
//        cropIntent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//        cropIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//        cropIntent.setDataAndType(photoURI, "image/*")
//        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
//        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
//        cropIntent.putExtra("aspectX", 1) // crop 박스의 x축 비율, 1&1이면 정사각형
//        cropIntent.putExtra("aspectY", 1) // crop 박스의 y축 비율
//        cropIntent.putExtra("scale", true)
//        cropIntent.putExtra("output", albumURI) // 크랍된 이미지를 해당 경로에 저장
//        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP)
//    }
  class DataPage(var title : String, var selRepeat : String, var selToogle : Boolean, var calMil : Long, var imgUrl : String)
}
