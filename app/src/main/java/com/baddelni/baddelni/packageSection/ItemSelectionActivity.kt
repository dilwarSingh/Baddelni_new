package com.baddelni.baddelni.packageSection

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.baddelni.baddelni.App
import com.baddelni.baddelni.R
import kotlinx.android.synthetic.main.activity_item_selection.*


class ItemSelectionActivity : AppCompatActivity() {
    val replacementsItems = App.replacementsItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_selection)


        acceptBt.setOnClickListener {

            var str = ""
            replacementsItems?.forEachIndexed { index, replacementsItem ->
                str += if (index == replacementsItems.size - 1) {
                    replacementsItem.id.toString()
                } else {
                    "${replacementsItem.id.toString()},"
                }
            }

            val intent = intent
            intent.putExtra("selectedList", str)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


        val itemsListAdapter = ItemsListAdapter(replacementsItems!!)
        itemRecyclerView.adapter = itemsListAdapter

    }

    override fun onBackPressed() {

        var str = ""
        replacementsItems?.forEachIndexed { index, replacementsItem ->
            str += if (index == replacementsItems.size - 1) {
                replacementsItem.id.toString()
            } else {
                "${replacementsItem.id.toString()},"
            }
        }

        val intent = intent
        intent.putExtra("selectedList", str)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
