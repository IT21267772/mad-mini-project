package com.example.madminiproject.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.madminiproject.*
import com.example.madminiproject.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import java.util.Objects

class AccountFragment : Fragment() {

    private val TAG = "AccountFragment"
    private lateinit var viewProfile: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get an instance of FirebaseAuth
        val firebaseAuth = FirebaseAuth.getInstance()

        // Get the current user
        val currentUser = firebaseAuth.currentUser

        // Get a reference to the current user's data in the Firebase Realtime Database
        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(currentUser?.uid!!)

        // Add a listener to the userRef that listens for changes to the user data
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Retrieve the user object from the dataSnapshot
                val user = dataSnapshot.getValue(User::class.java)

                val name = view.findViewById<TextView>(R.id.acc_name)
                val profilePicture = view.findViewById<CircleImageView>(R.id.acc_profile_pic)

                val imageUri = Uri.parse("https://icons.veryicon.com/png/o/miscellaneous/administration/account-25.png")

                name.text = user?.name

                Picasso.get()
                    .load(imageUri)
                    .into(profilePicture)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here if the read operation is cancelled or fails
                Log.e(TAG, "onCancelled", databaseError.toException())
            }


        })


        //My Profile
        val accSetting1 = view.findViewById<LinearLayout>(R.id.acc_setting_label1)

        accSetting1.setOnClickListener {

        }

        //My Posts
        val accSetting2 = view.findViewById<LinearLayout>(R.id.acc_setting_label2)

        accSetting2.setOnClickListener {
            val intent = Intent(requireContext(), ExploreMyPostListActivity::class.java)
            startActivity(intent)
        }

        //Feedback
        val accSetting3 = view.findViewById<LinearLayout>(R.id.acc_setting_label3)

        accSetting3.setOnClickListener {

        }

        //Trip Expense Calculator
        val accSetting4 = view.findViewById<LinearLayout>(R.id.acc_setting_label4)

        accSetting4.setOnClickListener {

        }

        //Logout
        val accSetting5 = view.findViewById<LinearLayout>(R.id.acc_setting_label5)

        accSetting5.setOnClickListener {
            firebaseAuth.signOut()

            val intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}