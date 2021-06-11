package com.cainiaowo.navigationdemo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cainiaowo.navigationdemo.R

class HomeDetailFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeDetailFragment", "--> HomeDetailFragment${hashCode()} onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = "$it Detail 界面"
        })
        Log.d("HomeDetailFragment", "--> HomeDetailFragment${hashCode()} onCreateView")
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeDetailFragment", "--> HomeDetailFragment${hashCode()} onDestroy")
    }
}