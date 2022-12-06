package com.godslew.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.godslew.home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: HomeFragmentArgs by navArgsNoCache()

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it.plus(args.text)
        }
        Log.d("godslew", "onCreate")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    private fun onViewCreated() {
        val isOpen = args.isOpen
        if (isOpen) {
            open()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        requireArguments().putAll(args.copy(isOpen = false).toBundle())
        super.onSaveInstanceState(outState)
    }

    private fun open() {
        val url = "https://example.com/"
        // ブラウザ起動でページ表示
        // ブラウザ起動でページ表示
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        Log.d("MultiM Module", args.text)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}