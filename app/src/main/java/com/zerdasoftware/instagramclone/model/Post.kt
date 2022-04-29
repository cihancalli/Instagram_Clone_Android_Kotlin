package com.zerdasoftware.instagramclone.model

import com.google.firebase.Timestamp

data class Post (val email:String,
            val comment:String,
            val downloadUrl:String,
            val date:Timestamp) {
}