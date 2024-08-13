package com.example.assignment3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {

    private lateinit var historyListView: ListView
    private val historyList: MutableList<String> = mutableListOf()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyListView = view.findViewById(R.id.historyListView)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, historyList)
        historyListView.adapter = adapter

        return view
    }

    fun addHistory(movieType: String) {
        historyList.add(movieType)
        adapter.notifyDataSetChanged()
    }
}