package com.example.pharmacyapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapp.databinding.FragmentHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

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

        // Sample purchase history data
        val purchases = listOf(
            Purchase(
                id = "1",
                date = Date(),
                items = listOf(
                    PurchaseItem(
                        name = "Paracetamol",
                        quantity = 2,
                        price = 5.99
                    ),
                    PurchaseItem(
                        name = "Vitamin C",
                        quantity = 1,
                        price = 12.99
                    )
                ),
                totalAmount = 24.97
            )
        )

        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PurchaseHistoryAdapter(purchases)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Purchase(
    val id: String,
    val date: Date,
    val items: List<PurchaseItem>,
    val totalAmount: Double
)

data class PurchaseItem(
    val name: String,
    val quantity: Int,
    val price: Double
) 