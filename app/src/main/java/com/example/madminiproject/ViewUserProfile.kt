package com.example.madminiproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.madminiproject.databinding.ActivityViewUserProfileBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ViewUserProfile : AppCompatActivity() {

    private lateinit var binding : ActivityViewUserProfileBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewData.setOnClickListener {
            val userName: String = binding.getName.text.toString()
            if (userName.isNotEmpty()) {
                readData(userName)
            } else {
                Toast.makeText(this, "please enter username", Toast.LENGTH_SHORT).show()
            }
        }
    }
        private fun readData(userName: String){
            database = FirebaseDatabase.getInstance().getReference("users")
            database.child(userName).get().addOnSuccessListener {
                if(it.exists()){
                    var email = it.child("email").value
                    var name = it.child("name").value
                    var type = it.child("type").value

                    Toast.makeText(this, "Data read successfully", Toast.LENGTH_SHORT).show()

                    binding.getName.text = name.toString()
                    binding.getownership.text = type.toString()
                    binding.getEmail.text = email.toString()
                }else{
                    Toast.makeText(this, "Username doesn't exists", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Operation failed", Toast.LENGTH_SHORT).show()
            }
        }


    }
