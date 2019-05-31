package com.baddelni.baddelni.packageSection

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.afollestad.materialdialogs.MaterialDialog
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Countries.Countries
import com.baddelni.baddelni.Response.Countries.CountriesItem
import com.baddelni.baddelni.Response.categories.CategoriesItem
import com.baddelni.baddelni.Response.categories.Category
import com.baddelni.baddelni.Response.categories.SingleProductResponse.SingleProductResponse
import com.baddelni.baddelni.Response.categories.SubCategoryItem
import com.baddelni.baddelni.account.setGlideImage
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.account.setGlideUserImage
import com.baddelni.baddelni.categories.pojoProductDetail
import com.baddelni.baddelni.databinding.ActivityEditPostNewBinding
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.GlobalSharing
import com.baddelni.baddelni.util.YesNoInterface
import kotlinx.android.synthetic.main.activity_edit_post_new.*
import kotlinx.android.synthetic.main.activity_edit_post_new.detail
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

class EditPostActivity : AppCompatActivity() {

    private val co: CommonObjects by lazy { CommonObjects(this) }

    private val IMAGE_DIRECTORY = "/Baddelni"
    private val GALLERY = 213
    private val CAMERA = 142
    private var adsImage: File? = null
    private var badliniImage: File? = null
    private var subImageList = arrayOfNulls<File>(4)
    var MEDIA_TYPE_FORM = MediaType.parse("image/png")
    var categoryList: List<CategoriesItem>? = null
    var subCategoryList: ArrayList<SubCategoryItem>? = null
    var selectedCategory: Int? = null
    var selectedSubCategory: Int? = null
    var selectedBandliniCategory: Int? = null
    var selectedImage = ImageSet.PRODUCT_IMAGE
    var screenMode = ScreenMode.AdsScreen
    var binding: ActivityEditPostNewBinding? = null
    var countryList: List<CountriesItem> = emptyList()

    val pId by lazy { intent.extras.getInt("pid") }

    enum class ImageSet { PRODUCT_IMAGE, IMG1, IMG2, IMG3, IMG4, BADLINI_IMAGE }
    enum class ScreenMode { AdsScreen, BadliniScreen }

    var badal_namesList = emptyList<String>().toMutableList()
    var badal_ImageList = emptyList<File>().toMutableList()
    var badal_CategoryList = emptyList<String>().toMutableList()
    var badal_DescriptionList = emptyList<String>().toMutableList()
    var badal_PhoneList = emptyList<String>().toMutableList()
    var badal_PriceList = emptyList<String>().toMutableList()
    var badal_ItemCheckList = emptyList<Boolean>().toMutableList()
    var badal_SellingPriceCheckList = emptyList<Boolean>().toMutableList()
    var badal_MakeAdSpecialCheckList = emptyList<Boolean>().toMutableList()
    var countryId: Int? = null

    var sectionSize = 0
    var oldImageIds: MutableList<String> = listOf<String>().toMutableList()
    var oldProductsIds: MutableList<String> = listOf<String>().toMutableList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_create_post)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_post_new)
        getCategoriesData()
        postCount.text = "${GlobalSharing.postCount} ${getString(R.string.posts)}"

        addOther.visibility = GONE
        //      cardView301.visibility = GONE
        badImage.visibility = GONE
        textView241.visibility = GONE
        backBt.setOnClickListener { finish() }

        profileImg.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username.text = co.getStringPrams(AppConstants.PERSON_NAME)

        /*
        include.newPostText.text = getString(R.string.buyNewPosts)
        include.text.text = getString(R.string.avaliablePosts)

        include.newPostText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shop, 0, 0, 0)
        include.newPostBt.setOnClickListener { startActivity(Intent(this, BuyPackageActivity::class.java)) }
        include.count.text = co.getStringPrams(AppConstants.AVALIABLE_POSTS)
*/

        category.setOnClickListener {
            MaterialDialog
                    .Builder(this)
                    .itemsCallbackSingleChoice(0) { dialog, itemView, postion, text ->
                        subCategoryList = categoryList?.get(postion)?.subCategory
                        selectedCategory = categoryList?.get(postion)?.id!!
                        category.text = categoryList?.get(postion)?.category
                        subCategory.text = ""
                        selectedSubCategory = null
                        true
                    }
                    .items(categoryList?.map { it.category }!!)
                    .build()
                    .show()
        }
        subCategory.setOnClickListener {

            if (selectedCategory == null) {
                co.showToastDialog(detail = getString(R.string.selectCategoryFirst), yesNo = null)
                return@setOnClickListener
            }
            MaterialDialog
                    .Builder(this)
                    .itemsCallbackSingleChoice(0) { dialog, itemView, postion, text ->
                        selectedSubCategory = subCategoryList?.get(postion)?.id
                        subCategory.text = subCategoryList?.get(postion)?.subCategory

                        true
                    }
                    .items(subCategoryList?.map { it.subCategory }!!)
                    .build()
                    .show()
        }
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

        country.setOnClickListener {
            MaterialDialog
                    .Builder(this)
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

        binding!!.baddItemCB.isChecked = true

        binding!!.baddItemCB.setOnClickListener {
            binding!!.sellingItemCB.isChecked = !binding!!.baddItemCB.isChecked
        }
        binding!!.sellingItemCB.setOnClickListener {
            binding!!.baddItemCB.isChecked = !binding!!.baddItemCB.isChecked
        }

        addOther.setOnClickListener {

            badal_namesList.add(binding!!.badName.text.toString().trim())
            badal_CategoryList.add(selectedBandliniCategory.toString())
            badal_DescriptionList.add(binding!!.detailBad.text.toString())
            //        badal_PhoneList.add(binding!!.phoneNoBd.text.toString())
            badal_PriceList.add(binding!!.baddPrice.text.toString())


            badal_ItemCheckList.add(binding!!.baddItemCB.isChecked)
            badal_SellingPriceCheckList.add(binding!!.sellingItemCB.isChecked)
            badal_MakeAdSpecialCheckList.add(binding!!.makeSpecialCB.isChecked)


            if (badliniImage != null) {
                badal_ImageList.add(badliniImage!!)
            }

            badName.setText("")
            badCategory.text = ""
            badImage.setImageDrawable(resources.getDrawable(R.drawable.camera_icon))
            sectionSize++
        }


        createBt.text = getString(R.string.next)
        createBt.setOnClickListener { saveDataToServer() }
        /*    productImage.setOnClickListener { showPictureDialog(ImageSet.PRODUCT_IMAGE) }
            image1.setOnClickListener { showPictureDialog(ImageSet.IMG1) }
            image2.setOnClickListener { showPictureDialog(ImageSet.IMG2) }
            image3.setOnClickListener { showPictureDialog(ImageSet.IMG3) }
            image4.setOnClickListener { showPictureDialog(ImageSet.IMG4) }
            badImage.setOnClickListener { showPictureDialog(ImageSet.BADLINI_IMAGE) }
    */
    }


    private fun showPictureDialog(setImageIn: ImageSet) {
        selectedImage = setImageIn
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

    private fun getCountyData() {


        Api.getApi().getCountries(co.getAppLanguage().langCode()).enqueue(object : Callback<Countries> {

            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {
                        this@EditPostActivity.countryList = countryList!!
                        getData()
                    }
                }
            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
            }

        })

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
            saveImage(thumbnail, true)
        }
    }

    fun saveImage(myBitmap: Bitmap, isThumb: Boolean = false): String {
        val bytes = ByteArrayOutputStream()
        if (!isThumb) {
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
        } else {
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        }
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

        when (selectedImage) {

            ImageSet.PRODUCT_IMAGE -> {
                productImage.setGlideImage(myBitmap)
                adsImage = file
            }
            ImageSet.IMG1 -> {
                image1.setGlideImage(myBitmap, false)
                subImageList[0] = file
            }
            ImageSet.IMG2 -> {
                image2.setGlideImage(myBitmap, false)
                subImageList[1] = file
            }
            ImageSet.IMG3 -> {
                image3.setGlideImage(myBitmap, false)
                subImageList[2] = file
            }
            ImageSet.IMG4 -> {
                image4.setGlideImage(myBitmap, false)
                subImageList[3] = file
            }
            ImageSet.BADLINI_IMAGE -> {
                badImage.setGlideImage(myBitmap)
                badliniImage = file
            }
        }

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

        if (screenMode == ScreenMode.AdsScreen) {


            if (binding?.name?.text.toString().isEmpty() || detail.text.toString().trim().isEmpty() || phoneNo.text.toString().trim().isEmpty()
                    || selectedCategory == null ||
                    selectedSubCategory == null) {
                co.showToastDialog(getString(R.string.error), getString(R.string.enterAllFields), null)
                return
            }/*  if (binding?.name?.text.toString().isEmpty() || detail.text.toString().trim().isEmpty() || phoneNo.text.toString().trim().isEmpty()
                    || selectedCategory == null ||
                    selectedSubCategory == null || countryId == null) {
                co.showToastDialog(getString(R.string.error), getString(R.string.enterAllFields), null)
                return
            }*/

            adsScreen.visibility = View.GONE
            badScreen.visibility = View.VISIBLE

            createBt.text = getString(R.string.baddelni)
            addOther.visibility = VISIBLE
            cardView30.visibility = GONE
            textView241.visibility = GONE
            badImage.visibility = GONE

            screenMode = ScreenMode.BadliniScreen
        } else {


            /*    if (badName.text.isEmpty() || badCategory.text.isEmpty()) {
                    co.showToastDialog(detail = getString(R.string.enterAllFields), yesNo = null)
                    return
                }*/
            /*   if (sectionSize == 0 || badName.text.isEmpty() || badCategory.text.isEmpty()) {
                co.showToastDialog(detail = getString(R.string.enterAllFields), yesNo = null)
                return
            }*/

            if (!badName.text.isEmpty()) {
                addOther.performClick()
            }


            co.showLoading()
            Api.getApi().updatePost(createProfileBody()).enqueue(object : Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    co.hideLoading()
                    val body = response.body()
                    if (body != null) {

                        val jString = body.string()

                        val jsonObject = JSONObject(jString)
                        if (jsonObject.getString("code") == "0") {
                            co.putStringPrams(AppConstants.AVALIABLE_POSTS, co.getStringPrams(AppConstants.AVALIABLE_POSTS).toInt().minus(1).toString())
                            co.showToastDialog(detail = getString(R.string.postAddedSuccessful), yesNo = object : YesNoInterface {
                                override fun onClickYes() {
                                    onBackPressed()
                                }

                            })

                        } else {
                            co.showToastDialog(detail = jsonObject.getString("msg"), yesNo = null)
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

    }

    private fun createProfileBody(): MultipartBody {

        val builder = MultipartBody.Builder()
        var i = -1

        val type =
                if (baddItemCB.isChecked && sellingItemCB.isChecked) {
                    "2"
                } else if (baddItemCB.isChecked) {
                    "0"
                } else {
                    "1"
                }

        builder.setType(MultipartBody.FORM)
                .addFormDataPart("name", binding?.name?.text.toString())
                .addFormDataPart("description", detail.text.toString().trim())
                .addFormDataPart("category_id", selectedCategory.toString())
                .addFormDataPart("subCategory_id", selectedSubCategory.toString())
                .addFormDataPart("user_id", co.getStringPrams())
                .addFormDataPart("trans", co.getAppLanguage().langCode())
                .addFormDataPart("phone", phoneNo.text.toString().trim())
                .addFormDataPart("country_id", countryId.toString())
                .addFormDataPart("price", baddPrice.text.toString())
                .addFormDataPart("exchange_type", type)
                .addFormDataPart("is_special", if (makeSpecialCB.isChecked) "1" else "0")
                .build()



        oldProductsIds.forEach {
            builder.addFormDataPart("badl[]", it).build()

        }
        oldImageIds.forEach {
            builder.addFormDataPart("other[]", it).build()
        }

        badal_PhoneList.forEach {
            builder.addFormDataPart("baddl_phone[]", it).build()
        }
        badal_DescriptionList.forEach {
            builder.addFormDataPart("baddl_description[]", it).build()
        }




        if (adsImage != null) {
            builder
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("main_image", "main_image.jpg",
                            RequestBody.create(MEDIA_TYPE_FORM, adsImage!!)).build()
        }

        //  builder.addFormDataPart("baddl_name[]", badName.text.toString())

        badal_namesList.forEach {
            i++
            builder.addFormDataPart("baddl_name[]", it)
        }
        builder.build()

        badal_CategoryList.forEach {
            i++
            builder.addFormDataPart("baddl_category_id[]", it)

        }
        builder.build()

        badal_ImageList.forEach {
            i++
            builder
                    .addFormDataPart("baddl_image[]", "baddl_image[$i].jpg",
                            RequestBody.create(MEDIA_TYPE_FORM, it))
        }
        builder.build()

        builder.setType(MultipartBody.FORM)
        subImageList.forEach {
            if (it != null) {
                i++
                builder
                        .addFormDataPart("sub_images[]", "sub_images[$i].jpg",
                                RequestBody.create(MEDIA_TYPE_FORM, adsImage!!))
            }
        }
        builder.build()

        return builder.build()


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    fun getCategoriesData() {
        co.showLoading()
        Api.getApi().getCategoriesAndSubCats(co.getAppLanguage().langCode()).enqueue(object : Callback<Category> {

            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {

                        this@EditPostActivity.categoryList = categories
                        getCountyData()

                    }

                }
                co.hideLoading()
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })

    }

    private fun getData() {

        Api.getApi().singleProduct(pId!!, co.getAppLanguage().langCode()).enqueue(object : Callback<SingleProductResponse> {

            override fun onResponse(call: Call<SingleProductResponse>, response: Response<SingleProductResponse>) {
                val body = response.body()
                body?.apply {
                    if (code!!.isSuccess()) {

                        countryList.forEach {
                            if (it.id == product?.country_id) {
                                countryId = it.id
                                country.text = it.country
                                return@forEach
                            }
                        }







                        product?.user.apply {
                            username.text = this?.name ?: ""
                            this?.phone
                        }

                        val list = mutableListOf<pojoProductDetail>()

                        val pojo = pojoProductDetail(product?.id!!)

                        pojo.imageUrl = product.mainImage?.img!!
                        pojo.name = product.name!!
                        pojo.description = product.description!!

                        list.add(pojo)

                        product.replacements?.forEach {
                            oldProductsIds.add(it.id.toString())

                            val pojo1 = pojoProductDetail(it.id!!)
                            pojo1.imageUrl = it.img!!
                            pojo1.name = it.name!!
                            pojo1.description = it.description!!

                            list.add(pojo1)

                        }

                        val sliderList = mutableListOf<String>()
                        sliderList.add(product.mainImage.img)
                        oldImageIds.add(product.mainImage.img)

                        product.subImages?.forEach {
                            oldImageIds.add(it.id!!.toString())
                            sliderList.add(it.img ?: "")
                        }

                        productImage.setGlideImageNetworkPath(pojo.imageUrl)
                        name.setText(pojo.name)
                        phoneNo.setText(product.phone)
                        detail.setText(pojo.description)

                        selectedCategory = product.category?.id
                        category.text = product.category?.category

                        selectedSubCategory = product.subCategories?.get(0)?.categoryId
                        subCategory.text = product.subCategories?.get(0)?.subCategory

                    }

                }


            }

            override fun onFailure(call: Call<SingleProductResponse>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
            }

        })


    }

}