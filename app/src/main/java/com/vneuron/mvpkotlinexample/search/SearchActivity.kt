package com.vneuron.mvpkotlinexample.search

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vneuron.mvpkotlinexample.R
import com.vneuron.mvpkotlinexample.model.RemoteDataSource
import com.vneuron.mvpkotlinexample.model.Movie
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface, SearchAdapter.ItemClickListener {

    private lateinit var query:String
    private lateinit var presenter: SearchPresenter
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupPresenter()
        val intent = intent
        query = intent.getStringExtra(QUERY_TITLE).toString()
    }

    private fun setupPresenter() {
        val dataSource = RemoteDataSource()
        presenter = SearchPresenter(this, dataSource)
        rvSearch.layoutManager = LinearLayoutManager(this)
    }

    override fun initSearchRecyclerView(moviesList: List<Movie>) {
        searchAdapter = SearchAdapter(applicationContext, moviesList, this)
        rvSearch.apply {
            adapter = searchAdapter
        }
        searchAdapter.notifyDataSetChanged()
    }

    override fun showProgressBar() {
        search_progress_bar.visibility = View.VISIBLE
        rvSearch.visibility = View.GONE
    }

    override fun hideProgressBar() {
        search_progress_bar.visibility = View.GONE
        rvSearch.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        presenter.getSearchResults(query)
        showProgressBar()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    override fun showError(error: String) {
        showMessage(error)
    }

    override fun onItemClickListener(view: View, position: Int) {
        Log.d(TAG, "onItemClickListener: $position")
        showSaveDialog()
    }

    private fun showSaveDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_add_movie)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val yesBtn = dialog.findViewById<Button>(R.id.btnYes)
        val noBtn = dialog.findViewById<Button>(R.id.btnNo)
        yesBtn.setOnClickListener {
            showMessage("Yes")
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            showMessage("No")
            dialog.dismiss()
        }
        dialog.show()
    }


    companion object{
        private const val TAG = "SearchActivity"
        const val QUERY_TITLE = "query_title"
        const val QUERY_RELEASE_DATE = "query_release_date"
    }
}