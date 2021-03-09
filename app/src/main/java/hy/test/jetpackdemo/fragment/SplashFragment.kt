package hy.test.jetpackdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import hy.test.jetpackdemo.R

class SplashFragment :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_splash,container,false)

        view.findViewById<AppCompatImageView>(R.id.welcome_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splash_to_login)
        }


        return view
    }
}