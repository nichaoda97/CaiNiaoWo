package com.cainiaowo.tablayoutviewpager2demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cainiaowo.tablayoutviewpager2demo.databinding.FragmentOneBinding
import com.cainiaowo.tablayoutviewpager2demo.databinding.FragmentThreeBinding
import com.cainiaowo.tablayoutviewpager2demo.databinding.FragmentTwoBinding

class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentOneBinding>(
            inflater,
            R.layout.fragment_one,
            container,
            true
        )

        return binding.root
    }

}

class TwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentTwoBinding>(
            inflater,
            R.layout.fragment_two,
            container,
            true
        )

        return binding.root
    }

}

class ThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentThreeBinding>(
            inflater,
            R.layout.fragment_three,
            container,
            true
        )

        return binding.root
    }

}