package com.example.pharmacyapp.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.FragmentPaymentBinding
import com.example.pharmacyapp.utils.QRCodeGenerator
import org.json.JSONObject
import java.util.UUID

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
        setupUI()
        generateQRCode()
    }

    private fun setupUI() {
        with(binding) {
            paymentTitle.text = getString(R.string.payment_title)
            qrDescription.text = getString(R.string.qr_code_description)
            qrInstructions.text = getString(R.string.qr_code_instructions)
        }
    }

    private fun generateQRCode() {
        // Генерируем уникальный ID для платежа
        val paymentId = UUID.randomUUID().toString()
        
        // Создаем данные для QR-кода
        val paymentData = createPaymentData(paymentId)
        
        // Генерируем QR-код
        val qrCodeBitmap = QRCodeGenerator.generateQRCode(paymentData, 1024)
        
        // Отображаем QR-код
        binding.qrCodeImage.setImageBitmap(qrCodeBitmap)
    }

    private fun createPaymentData(paymentId: String): String {
        // Создаем JSON с данными для оплаты
        val paymentInfo = JSONObject().apply {
            put("paymentId", paymentId)
            put("merchantId", "PHARMACY_APP")
            put("timestamp", System.currentTimeMillis())
            put("currency", "RUB")
        }
        return paymentInfo.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 