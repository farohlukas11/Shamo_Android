package com.faroh.shamoandroid.view.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.databinding.ActivityTransactionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionActivity : AppCompatActivity() {

    private lateinit var transactionBinding: ActivityTransactionBinding
    private val transactionViewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionBinding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(transactionBinding.root)


    }
}