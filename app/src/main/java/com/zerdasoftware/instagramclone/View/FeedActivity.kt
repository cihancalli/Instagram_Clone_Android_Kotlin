package com.zerdasoftware.instagramclone.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zerdasoftware.instagramclone.R
import com.zerdasoftware.instagramclone.adapter.FeedRecyclerAdapter
import com.zerdasoftware.instagramclone.databinding.ActivityFeedBinding
import com.zerdasoftware.instagramclone.model.Post

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var feedAdapter: FeedRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore
        postArrayList = ArrayList<Post>()
        getData()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecyclerAdapter(postArrayList)
        binding.recyclerView.adapter = feedAdapter
    }

    private  fun getData(){
        db.collection("Post").orderBy("date",Query.Direction.DESCENDING ).addSnapshotListener { value, error ->
            //whereEqualTo("userEmail",auth.currentUser!!.email.toString())
            //Skoru 10dan büyük olanlar whereGreaterThan("score",10)
            //lessThan daha küçük olanları getir
            //notEqualTo
            //orderBy("date") tarihe göre diz
            if (error != null){
                Toast.makeText(this,error.localizedMessage , Toast.LENGTH_LONG).show()
            }else{
                if (value != null){
                    if (!value.isEmpty){
                        val documents = value.documents
                        postArrayList.clear()
                        for (document in documents){
                            //casting
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            val date = document.get("date") as Timestamp

                            val post = Post(userEmail,comment,downloadUrl,date)
                            postArrayList.add(post)
                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.instagram_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_post){
            val intent = Intent(this@FeedActivity, UploadActivity::class.java)
            startActivity(intent)
        }
        else if (item.itemId == R.id.sign_out){
            auth.signOut()
            val intent = Intent(this@FeedActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}