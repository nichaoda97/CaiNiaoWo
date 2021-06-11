package com.cainiaowo.navigationdemo.ui.notifications

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

class NotificationsDetailFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(
            "NotificationsDetailFragment",
            "--> NotificationsDetailFragment${hashCode()} onCreate"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = "$it Detail 界面"
        })
        Log.i(
            "NotificationsDetailFragment",
            "--> NotificationsDetailFragment${hashCode()} onCreateView"
        )
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(
            "NotificationsDetailFragment",
            "--> NotificationsDetailFragment${hashCode()} onDestroy"
        )
    }
}