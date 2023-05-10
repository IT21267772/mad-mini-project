package com.example.madminiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madminiproject.adapters.ParentAdapter
import com.example.madminiproject.databinding.ActivityMainBinding
import com.example.madminiproject.fragments.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    // Define variables for layout binding, Firebase authentication, and a log tag
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "MainActivity"
    private lateinit var databaseReference: DatabaseReference

    // Define a variable to store the currently displayed fragment
    private var currentFragment: Fragment? = null

    private lateinit var recyclerView: RecyclerView
    private val parentList = ArrayList<ParentItem>()

    // Define the onCreate method for this activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = findViewById(R.id.parentRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addDataToList()
        val adapter = ParentAdapter(parentList)
        recyclerView.adapter = adapter

        // Inflate the activity's layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Restore the current fragment state if available, or load the HomeFragment by default
        if (savedInstanceState != null) {
            // Restore the current fragment state
            currentFragment =
                supportFragmentManager.getFragment(savedInstanceState, "CURRENT_FRAGMENT")
        } else {
            // Load the HomeFragment by default
            currentFragment = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, currentFragment!!)
                .commit()
        }


        // Set a listener for when the user selects a bottom navigation menu item
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // If the Home menu item is selected, load the HomeFragment
                R.id.homeId -> {
                    if (currentFragment !is HomeFragment) {
                        currentFragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, currentFragment!!).commit()
                    }
                }
                // If the Packages menu item is selected, load the PackagesFragment
                R.id.packagesId -> {
                    if (currentFragment !is PackagesFragment) {
                        currentFragment = PackagesFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, currentFragment!!).commit()
                    }
                }
                // If the Explore menu item is selected, load the ExploreFragment
                R.id.exploreId -> {
                    if (currentFragment !is ExploreFragment) {
                        currentFragment = ExploreFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, currentFragment!!).commit()
                    }
                }
                // If the Account menu item is selected, load the appropriate account fragment based on the user's type
                R.id.accountId -> {

                    // Get the current user and their account type from Firebase
                    val currentUser = firebaseAuth.currentUser
                    val typeRef = FirebaseDatabase.getInstance().reference.child("users")
                        .child(currentUser?.uid!!).child("type")

                    // Set a listener for when the account type is retrieved from Firebase
                    typeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            // Get the account type value and load the appropriate account fragment
                            val type = dataSnapshot.getValue(String::class.java)

                            if (type.equals("Hotel Owner")) {
                                if (currentFragment !is AccountHotelOwnerFragment) {
                                    currentFragment = AccountHotelOwnerFragment()
                                    supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame_layout, currentFragment!!).commit()
                                }
                            } else if (type.equals("Traveler")) {
                                if (currentFragment !is AccountFragment) {
                                    currentFragment = AccountFragment()
                                    supportFragmentManager.beginTransaction()
                                        .replace(R.id.frame_layout, currentFragment!!).commit()
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle errors here if the read operation is cancelled or fails
                            Log.e(TAG, "onCancelled", databaseError.toException())
                        }
                    })
                }

                else -> {
                }
            }
            true
        }

    }

    private fun addDataToList() {

        val childItems1 = ArrayList<ChildItem>()
        childItems1.add(ChildItem("img1", R.drawable.drawable_cabana_kithulgala))
        childItems1.add(ChildItem("img2", R.drawable.drawable_cabana_kithulgala))
        childItems1.add(ChildItem("img3", R.drawable.drawable_cabana_kithulgala))
        childItems1.add(ChildItem("img4", R.drawable.drawable_cabana_kithulgala))

        parentList.add(ParentItem("img5", R.drawable.drawable_cabana_kithulgala, childItems1))

        val childItem2 = ArrayList<ChildItem>()
        childItem2.add(ChildItem("img6", R.drawable.drawable_cabana_kithulgala))
        childItem2.add(ChildItem("img7", R.drawable.drawable_cabana_kithulgala))
        childItem2.add(ChildItem("img8", R.drawable.drawable_cabana_kithulgala))
        parentList.add(
            ParentItem(
                "Historical Sites",
                R.drawable.drawable_cabana_kithulgala,
                childItem2
            )
        )
        val childItem3 = ArrayList<ChildItem>()
        childItem3.add(ChildItem("img9", R.drawable.drawable_cabana_kithulgala))
        childItem3.add(ChildItem("img10", R.drawable.drawable_cabana_kithulgala))
        childItem3.add(ChildItem("img11", R.drawable.drawable_cabana_kithulgala))
        parentList.add(
            ParentItem(
                "Coastal Area",
                R.drawable.drawable_cabana_kithulgala,
                childItem3
            )
        )
        val childItem4 = ArrayList<ChildItem>()
        childItem4.add(ChildItem("img12", R.drawable.drawable_cabana_kithulgala))
        childItem4.add(ChildItem("img13", R.drawable.drawable_cabana_kithulgala))
        childItem4.add(ChildItem("img14", R.drawable.drawable_cabana_kithulgala))
        parentList.add(
            ParentItem(
                "Wild Life Safari",
                R.drawable.sigiriya,
                childItem4
            )
        )
        val childItem5 = ArrayList<ChildItem>()
        childItem5.add(ChildItem("img15", R.drawable.sigiriya))
        childItem5.add(ChildItem("img16", R.drawable.sigiriya))
        childItem5.add(ChildItem("img17", R.drawable.sigiriya))
        childItem5.add(ChildItem("img18", R.drawable.sigiriya))
        parentList.add(
            ParentItem(
                "Beach Side",
                R.drawable.drawable_cabana_kithulgala,
                childItem5
            )
        )
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current fragment state
        supportFragmentManager.putFragment(outState, "CURRENT_FRAGMENT", currentFragment!!)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore the current fragment state
        currentFragment = supportFragmentManager.getFragment(savedInstanceState, "CURRENT_FRAGMENT")
    }
}
