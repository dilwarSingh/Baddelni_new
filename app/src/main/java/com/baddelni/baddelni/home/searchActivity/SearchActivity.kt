package com.baddelni.baddelni.home.searchActivity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.setGlideUserImage
import com.baddelni.baddelni.home.searchActivity.response.SearchResponse
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.NetworkCode
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity() {

    data class filterData(val id: String, @StringRes val stringId: Int)

    val filterList by lazy {
        listOf(
                filterData("0", R.string.baddelni),
                filterData("1", R.string.selling_items),
                filterData("2", R.string.all)
        )
    }
    var selectedId = 0;
    var selectedFilter = ""

    val co by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        profileImg3.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username3.text = co.getStringPrams(AppConstants.PERSON_NAME)
        backBt3.setOnClickListener { onBackPressed() }

        searchProducts("", selectedFilter)

        loadTv.setOnClickListener {
            searchET2.setText("")
            searchProducts("", "")
        }

        notificationsBt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 3)
            startActivity(intent)
        }
        settingsBt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 4)
            startActivity(intent)
        }

        val tv = object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchProducts(searchET2.text.toString().trim(), selectedFilter)
                    return true
                }
                return false
            }
        }

        searchET2.setOnEditorActionListener(tv)

        searchET2.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= searchET2.right - searchET2.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {

                        MaterialDialog
                                .Builder(this@SearchActivity)
                                .itemsCallbackSingleChoice(selectedId) { _, _, postion, _ ->
                                    selectedId = postion
                                    selectedFilter = filterList[postion].id
                                    searchProducts(searchET2.text.toString().trim(), selectedFilter)
                                    true
                                }
                                .items(filterList.map { getString(it.stringId) })
                                .build()
                                .show()
                        return true
                    }
                }
                return false
            }
        })

    }

    private fun searchProducts(searchText: String, filter: String?) {

        val api = if (filter == null)
            Api.getApi().productSearch(
                    co.getStringPrams(),
                    searchText,
                    co.getAppLanguage().langCode()
            )
        else Api.getApi().productSearch(
                co.getStringPrams(),
                searchText,
                filter,
                co.getAppLanguage().langCode()
        )
        co.showLoading()
        api.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val body = response.body()
                co.hideLoading()
                if (body?.code == NetworkCode.SUCCESS) {
                    if (body.data.isEmpty()) {
                        noViewLay.visibility = VISIBLE
                        searchTextTv.text = "${getString(R.string.find)} '${searchText}'"
                    } else {
                        noViewLay.visibility = GONE
                    }
                    recycler.adapter = AdapterSearch(this@SearchActivity, body.data)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }


        })
    }
}
