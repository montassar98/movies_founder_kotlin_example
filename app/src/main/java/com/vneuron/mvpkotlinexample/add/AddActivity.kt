package com.vneuron.mvpkotlinexample.add

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.search.SearchActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), AddContract.ViewInterface {

    private lateinit var presenter: AddPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupPresenter()
    }

    private fun setupPresenter() {
        presenter = AddPresenter(this)
    }


    override fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        displayMessage(error)
    }

    fun onSearchClicked(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        val queryTitle:String = edtTitle.text.toString()
        val queryReleaseDate:String = edtReleaseDate.text.toString()
        if (queryTitle.isEmpty())
            return
        intent.putExtra(SearchActivity.QUERY_TITLE, queryTitle)
        if (queryReleaseDate.isNotEmpty() && TextUtils.isDigitsOnly(queryReleaseDate))
            intent.putExtra(SearchActivity.QUERY_RELEASE_DATE, queryReleaseDate.toInt())

        startActivity(intent)
    }
}