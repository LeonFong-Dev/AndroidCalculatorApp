package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculatorapp.databinding.ActivityMainBinding
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Step 2: Inflate binding and set the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Step 3: Preserve inset padding logic (optional, for edge-to-edge layout)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val opperations = arrayOf("Add", "Subtract", "Multiply", "Divide")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        for (operation in opperations) {
            adapter.add(operation)
        }


        binding.button2.setOnClickListener {
            val num1Text = binding.editTextText.text.toString()
            val num2Text = binding.editTextText2.text.toString()

            if (num1Text.isEmpty() || num2Text.isEmpty()) {
                binding.textView.text = "Please enter both numbers"
                return@setOnClickListener
            }

            val num1 = num1Text.toDouble()
            val num2 = num2Text.toDouble()

            val operation = binding.spinner.selectedItem.toString()

            val result = when (operation) {
                "Add" -> num1 + num2
                "Subtract" -> num1 - num2
                "Multiply" -> num1 * num2
                "Divide" -> {
                    if (num2 == 0.0) {
                        binding.textView.text = "Cannot divide by 0"
                        return@setOnClickListener
                    } else {
                        num1 / num2
                    }
                }

                else -> {
                    binding.textView.text = "Unknown operation"
                    return@setOnClickListener
                }
            }

            // Step 5: Show result
            binding.textView.text = "$result"
        }
    }
}