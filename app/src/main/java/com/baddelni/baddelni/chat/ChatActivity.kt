package com.baddelni.baddelni.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.setGlideUserImage
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*


class ChatActivity : AppCompatActivity() {

    val co by lazy { CommonObjects(this) }
    val roomId by lazy { intent?.extras?.getString("roomId") ?: "GlobalRoom" }
    val myRef by lazy { FirebaseDatabase.getInstance().getReference("ChatHub").child(roomId) }
    //val myRef by lazy { FirebaseDatabase.getInstance().getReference("ChatHub").child("GlobalRoom") }
    val messages = emptyArray<TextMessage>().toMutableList()
    val chatAdapter by lazy { ChatAdapter(this, messages, co.getStringPrams()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatListRecycler.adapter = chatAdapter

        myRef.child(roomId)

        profileImg.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username.text = co.getStringPrams(AppConstants.PERSON_NAME)
        textView22.text = "You are in Community chat feel free to chat"
        linearLayout.setOnClickListener { onBackPressed() }
        //preloadList()
        newMessageReceivedListener()

        button2.setOnClickListener {
            val mess = TextMessage("text", messageTextET.text.toString(), co.getStringPrams().toInt(), Date().time)
            myRef.push().setValue(mess)
            messageTextET.setText("")
        }

    }

    private fun newMessageReceivedListener() {
        myRef.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // If any child is added to the database reference
                messages.add(dataSnapshot.getValue(TextMessage::class.java)!!)
                chatAdapter.notifyDataSetChanged()
                recyclerScrollToLast(true)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                // If any child is updated/changed to the database reference
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                // If any child is removed to the database reference
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // If any child is moved to the database reference
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })

    }

    private fun preloadList() {
        co.showLoading()

        val messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    val value = child.getValue(TextMessage::class.java)
                    messages.add(value!!)
                }
                chatAdapter.notifyDataSetChanged()
                recyclerScrollToLast()

                co.hideLoading()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                databaseError.toException().printStackTrace()
                co.hideLoading()
            }
        }

        myRef.addListenerForSingleValueEvent(messageListener)

    }

    private fun recyclerScrollToLast(smooth: Boolean = false) {
        if (smooth)
            chatListRecycler.smoothScrollToPosition(messages.size - 1)
        else
            chatListRecycler.scrollToPosition(messages.size - 1)


    }

}