package com.example.mynotes

import android.R
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.findNavController
import com.example.mynotes.R.layout.activity_main
import kotlinx.android.synthetic.main.fragment_list.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)



    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.list)

        return navController.navigateUp()|| super.onSupportNavigateUp()
    }
}