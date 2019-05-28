package com.baddelni.baddelni.account

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
import com.baddelni.baddelni.Response.Countries.Countries
import com.baddelni.baddelni.Response.Countries.CountriesItem
import com.baddelni.baddelni.account.adapters.AdapterEditIntrests
import com.baddelni.baddelni.account.pojos.pojoCats
import com.baddelni.baddelni.loginRegister.interests.InterestsActivity
import com.baddelni.baddelni.packageSection.CreatePostActivity
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.YesNoInterface
import kotlinx.android.synthetic.main.edit_profile.*
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


class EditProfileActivity : AppCompatActivity() {
    private var countryId: Int = 0
    private val co: CommonObjects by lazy { CommonObjects(this) }
    private lateinit var countryList: List<CountriesItem>
    private val IMAGE_DIRECTORY = "/Baddelni"
    private val GALLERY = 213
    private val CATEGORY = 152
    private val CAMERA = 142
    private var imageFile: File? = null
    var MEDIA_TYPE_FORM = MediaType.parse("image/png")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)


        intent.extras?.apply {


            text.setText(getString("name"))
            mail.setText(getString("email"))
            countryId = getInt("countryId", 0)
            country.text = getString("countryName")
            phone.setText(getString("phone"))
            descroiptionText.text = getString("description")
            profileImage.setGlideImageNetworkPath(getString("imageUrl") ?: "")

            val serializable = getSerializable("cats") as Array<pojoCats>

            intrestRecycler.adapter = AdapterEditIntrests(this@EditProfileActivity, serializable)
        }

        getCountyData()


        fabCreatePost.setOnClickListener {
            if (co.getStringPrams() == AppConstants.GuestUserId) {
                co.showLoginDialog(getString(R.string.dont_have_permission))
            } else
                startActivity(Intent(this, CreatePostActivity::class.java))
        }

        profileImage.setOnClickListener {
            showPictureDialog()
        }

        changeBt.setOnClickListener {
            val intent = Intent(this, InterestsActivity::class.java)
            intent.putExtra("toScreen", "finish")
            startActivityForResult(intent, CATEGORY)
        }

        doneBt.setOnClickListener { saveDataToServer() }
        backBt.setOnClickListener { finish() }

    }

    private fun getCountyData() {
        co.showLoading()

        Api.getApi().getCountries(co.getAppLanguage().langCode()).enqueue(object : Callback<Countries> {

            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {
                        this@EditProfileActivity.countryList = countryList!!
                        country.setOnClickListener {
                            MaterialDialog
                                    .Builder(this@EditProfileActivity)
                                    .itemsCallbackSingleChoice(0, object : MaterialDialog.ListCallbackSingleChoice {
                                        override fun onSelection(dialog: MaterialDialog?, itemView: View?, postion: Int, text: CharSequence?): Boolean {
                                            country.text = countryList[postion].country
                                            countryId = countryList[postion].id!!
                                            return true
                                        }
                                    })
                                    .items(countryList.map { it.country })
                                    .build()
                                    .show()
                        }
                    }
                } ?: co.myToast(response.errorBody().toString())



                co.hideLoading()
            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })


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
                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    profileImage.setGlideImage(bitmap)


                } catch (e: IOException) {
                    e.printStackTrace()
                    co.showToastDialog(detail = getString(R.string.error), yesNo = null)
                }

            }

        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            profileImage!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
        } else if (requestCode == CATEGORY) {
            val arrayOfPojoCatss = data?.extras?.getSerializable("data") as Array<pojoCats>
            intrestRecycler.adapter = AdapterEditIntrests(this@EditProfileActivity, arrayOfPojoCatss)

        }
    }

    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
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

            imageFile = f
            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    fun saveDataToServer() {

        co.showLoading()

        Api.getApi().updateAccount(createProfileBody()).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                co.hideLoading()
                val body = response.body()
                if (body != null) {

                    val jString = body.string()

                    val jsonObject = JSONObject(jString)
                    if (jsonObject.getInt("code") == 0) {
                        co.showToastDialog(detail = getString(R.string.updateSuccessful), yesNo = object : YesNoInterface {
                            override fun onClickYes() {
                                finish()
                            }
                        })

                    } else {
                        co.showToastDialog(detail = getString(R.string.updateUnSuccessful), yesNo = null)
                    }

                } else {
                    co.showToastDialog(detail = getString(R.string.ErrorPleaseTryAgainLater), yesNo = null)
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
                .addFormDataPart("name", text.text.toString().trim())
                .addFormDataPart("email", mail.text.toString().trim())
                .addFormDataPart("phone", phone.text.toString().trim())
                .addFormDataPart("country_id", countryId.toString())
                .addFormDataPart("password", password.text.toString().trim())
                .addFormDataPart("user_id", co.getStringPrams())
                .addFormDataPart("trans", co.getAppLanguage().langCode())

        if (imageFile != null) {


            //      imageFile = compressImage(imageFile)

            builder
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", "images.png",
                            RequestBody.create(MEDIA_TYPE_FORM, imageFile!!)).build()
        }

        return builder.build()


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }
}