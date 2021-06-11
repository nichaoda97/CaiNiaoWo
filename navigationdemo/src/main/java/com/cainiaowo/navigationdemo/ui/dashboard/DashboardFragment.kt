package com.cainiaowo.navigationdemo.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cainiaowo.navigationdemo.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("DashboardFragment", "DashboardFragment${hashCode()} onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        textView.setOnClickListener {
            findNavController().navigate(R.id.dashboardDetailFragment)
        }
        Log.w("DashboardFragment", "DashboardFragment${hashCode()} onCreateView")
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("DashboardFragment", "DashboardFragment${hashCode()} onDestroy")
    }
}