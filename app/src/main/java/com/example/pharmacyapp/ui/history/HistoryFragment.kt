package com.example.pharmacyapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapp.databinding.FragmentHistoryBinding
import com.example.pharmacyapp.ui.history.Purchase
import com.example.pharmacyapp.ui.history.PurchaseItem
import java.util.Date

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PurchaseHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadSampleData()
    }

    private fun setupRecyclerView() {
        adapter = PurchaseHistoryAdapter()
        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HistoryFragment.adapter
        }
    }

    private fun loadSampleData() {
        val samplePurchases = listOf(
            Purchase(
                id = "1001",
                date = Date(),
                items = listOf(
                    PurchaseItem("Аспирин", 2, 150.0),
                    PurchaseItem("Витамин C", 1, 300.0)
                ),
                totalAmount = 600.0
            ),
            Purchase(
                id = "1002",
                date = Date(System.currentTimeMillis() - 86400000), // Yesterday
                items = listOf(
                    PurchaseItem("Бинт", 3, 50.0),
                    PurchaseItem("Йод", 1, 120.0)
                ),
                totalAmount = 270.0
            )
        )
        adapter.submitList(samplePurchases)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 