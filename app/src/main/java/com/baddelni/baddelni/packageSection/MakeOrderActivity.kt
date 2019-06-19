package com.baddelni.baddelni.packageSection

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.categories.categoriesNew.CategoriesItem
import com.baddelni.baddelni.Response.categories.Category
import com.baddelni.baddelni.Response.categories.categoriesNew.CategoriesResponse
import com.baddelni.baddelni.account.setGlideImage
import com.baddelni.baddelni.account.setGlideUserImage
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.GlobalSharing
import com.baddelni.baddelni.util.YesNoInterface
import kotlinx.android.synthetic.main.activity_create_post_new.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class MakeOrderActivity : AppCompatActivity() {

    private val co: CommonObjects by lazy { CommonObjects(this) }

    private val IMAGE_DIRECTORY = "/Baddelni"
    private val GALLERY = 213
    private val CAMERA = 142
    private var badliniImage: File? = null
    var MEDIA_TYPE_FORM = MediaType.parse("image/png")
    var categoryList: List<CategoriesItem>? = null
    var selectedBandliniCategory: Int? = null
    var pId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_new)

        postCount.text = "${GlobalSharing.postCount} ${getString(R.string.posts)}"

        adsScreen.visibility = View.GONE
        badScreen.visibility = View.VISIBLE
        addOther.visibility = View.GONE

        hideOut.visibility = View.GONE


        //  createBt.text = getString(R.string.create)

        profileImg.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username.text = co.getStringPrams(AppConstants.PERSON_NAME)


        pId = intent.extras?.getInt("pId", 0) ?: 0
        backBt.setOnClickListener { finish() }
        getCategoriesData()

        /*
        include.newPostText.text = getString(R.string.buyNewPosts)
        include.text.text = getString(R.string.avaliablePosts)
        include.newPostText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shop, 0, 0, 0);
        include.newPostBt.setOnClickListener { startActivity(Intent(this, BuyPackageActivity::class.java)) }
        include.count.text = co.getStringPrams(AppConstants.AVALIABLE_POSTS)
        */

        badCategory.setOnClickListener {
            MaterialDialog
                    .Builder(this)
                    .itemsCallbackSingleChoice(0) { dialog, itemView, postion, text ->
                        selectedBandliniCategory = categoryList?.get(postion)?.id!!
                        badCategory.text = categoryList?.get(postion)?.category
                        true
                    }
                    .items(categoryList?.map { it.category }!!)
                    .build()
                    .show()
        }


        createBt.setOnClickListener { saveDataToServer() }
        badImage.setOnClickListener { showPictureDialog() }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle(getString(R.string.selectAction))
        val pictureDialogItems = arrayOf(getString(R.string.selectPhotoFromGallery), getString(R.string.capturePhotoFromCamera))
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            return
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                    co.showToastDialog(detail = getString(R.string.failed), yesNo = null)
                }

            }

        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            //        profileImage!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
        }
    }

    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdirs()
        }

        try {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                    .timeInMillis).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                    arrayOf(f.path),
                    arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            setImageIn(f, myBitmap)
            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    private fun setImageIn(file: File, myBitmap: Bitmap) {
        badImage.setGlideImage(myBitmap)
        badliniImage = file
    }

    fun convertImageToStringForServer(imageBitmap: Bitmap?): String? {
        val stream = ByteArrayOutputStream()
        if (imageBitmap != null) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val byteArray = stream.toByteArray()
            return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
        } else {
            return null
        }
    }

    fun saveDataToServer() {


        co.showLoading()
        Api.getApi().makeOrder(createProfileBody()).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                co.hideLoading()
                val body = response.body()
                if (body != null) {

                    val jString = body.string()

                    val jsonObject = JSONObject(jString)
                    if (jsonObject.getString("code") == "0") {
                        co.showToastDialog(detail = jsonObject.getString("msg"), yesNo = object : YesNoInterface {
                            override fun onClickYes() {
                                finish()
                            }

                        })

                    } else {
                        co.showToastDialog(detail = jsonObject.getString("msg")
                                ?: "Unable to Make order", yesNo = null)
                    }

                } else {
                    co.showToastDialog(detail = getString(R.string.ErrorPleaseTryAgainLater)
                            ?: "Unable to Make order", yesNo = null)
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })
    }

    private fun createProfileBody(): MultipartBody {

        val builder = MultipartBody.Builder()

        builder.setType(MultipartBody.FORM)
                .addFormDataPart("description", "Field Removed From Design")
                .addFormDataPart("user_id", co.getStringPrams())
                .addFormDataPart("category_id", selectedBandliniCategory.toString())
                .addFormDataPart("name", badName.text.toString())
                .addFormDataPart("trans", co.getAppLanguage().langCode())
                .addFormDataPart("product_id", pId.toString())
         //       .addFormDataPart("phone", phoneNoBd.toString())


        if (badliniImage != null) {
            builder
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", "image.jpg",
                            RequestBody.create(MEDIA_TYPE_FORM, badliniImage!!)).build()

        }
        return builder.build()


    }

    fun getCategoriesData() {
        co.showLoading()
        Api.getApi().getCategoriesAndSubCats(co.getAppLanguage().langCode()).enqueue(object : Callback<CategoriesResponse> {

            override fun onResponse(call: Call<CategoriesResponse>, response: Response<CategoriesResponse>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {

                        this@MakeOrderActivity.categoryList = categories


                    }

                }
                co.hideLoading()
            }

            override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })

    }

}