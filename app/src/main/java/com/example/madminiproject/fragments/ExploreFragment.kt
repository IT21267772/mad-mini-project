package com.example.madminiproject.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madminiproject.*
import com.example.madminiproject.R
import com.example.madminiproject.adapters.ExploreAdapter
import com.example.madminiproject.models.Post
import com.google.firebase.database.*

class ExploreFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var database: DatabaseReference
    private val TAG = "ExploreFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        // Get a reference to RecyclerView and set its layout manager
        recyclerView = view.findViewById(R.id.exploreRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Add item decoration
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerView.addItemDecoration(ExploreItemSpacingDecoration(spacingInPixels))

        // Initialize ExploreAdapter and set it as RecyclerView's adapter
        exploreAdapter = ExploreAdapter(ArrayList()) { post -> onItemClick(post) }
        recyclerView.adapter = exploreAdapter

        init()
        getTaskFromFirebase()

        return view
    }

    // Function to initialize Firebase Database reference
    private fun init() {
        database = FirebaseDatabase.getInstance().reference.child("posts")
    }

    // Function to fetch data from Firebase
    private fun getTaskFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postList = ArrayList<Post>()
                for (userSnapshot in snapshot.children) {
                    for (postSnapshot in userSnapshot.children) {
                        val post = postSnapshot.getValue(Post::class.java)
                        post?.let { postList.add(it) }
                    }
                }

                // Update ExploreAdapter with fetched data
                exploreAdapter.updateData(postList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled", error.toException())
            }
        })
    }

    // Function to handle item click
    private fun onItemClick(post: Post) {
        // Update number of views in Firebase Database and start ExploreViewPostActivity
        database.child(post.userId).child(post.postId).child("views").setValue(post.views + 1)
        val intent = Intent(activity, ExploreViewPostActivity::class.java)
        intent.putExtra("image", post.image)
        intent.putExtra("title", post.title)
        intent.putExtra("location", post.location)
        intent.putExtra("author", post.author)
        intent.putExtra("views", post.views)
        intent.putExtra("content", post.content)
        startActivity(intent)
    }
}