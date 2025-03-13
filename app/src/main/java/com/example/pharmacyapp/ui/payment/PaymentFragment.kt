package com.example.pharmacyapp.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pharmacyapp.databinding.FragmentPaymentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up credit card change button
        binding.creditCardChangeButton.setOnClickListener {
            showAddCardDialog()
        }

        // Set up QR code scan button
        binding.qrScanButton.setOnClickListener {
            // TODO: Implement QR code scanning
            showQRCodeDialog()
        }
    }

    private fun showAddCardDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add New Card")
            .setMessage("This feature will be available soon")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showQRCodeDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("QR Code Scanner")
            .setMessage("This feature will be available soon")
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 