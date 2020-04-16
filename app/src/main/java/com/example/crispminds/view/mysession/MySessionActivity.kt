package com.example.crispminds.view.mysession

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.crispminds.R
import com.example.crispminds.adapters.MySessionListAdapter
import com.example.crispminds.databinding.ActivityMySessionBinding
import com.example.crispminds.models.MySessionMainDTO
import com.example.crispminds.models.categorydtos.journal.JournalMainCategoryDTO
import com.example.crispminds.utils.Utils
import com.example.crispminds.view.mysession.viewmodel.MySessionViewModel
import kotlinx.android.synthetic.main.activity_my_session.*


class MySessionActivity : AppCompatActivity(), MySessionListAdapter.OnItemClickListener {

    lateinit var activityMySessionBinding: ActivityMySessionBinding
    lateinit var viewModel: MySessionViewModel
    lateinit var mySessionListAdapter: MySessionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {

        activityMySessionBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_my_session)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.getNavigationIcon()?.setColorFilter(
            getResources().getColor(R.color.colorPrimary),
            PorterDuff.Mode.SRC_ATOP
        )

        viewModel = Utils.obtainBaseObservable(
            this,
            MySessionViewModel::class.java,
            this,
            activityMySessionBinding.root
        )!!
        activityMySessionBinding.mySessionViewModel = viewModel


        mySessionListAdapter = MySessionListAdapter(this)

        activityMySessionBinding.contentMySession.mySessionRecyclerView.adapter =
            mySessionListAdapter

        mySessionListAdapter.setActivity(this)

        viewModel.mySessionMutableLiveDataList.observe(this, Observer {
            it?.let {
                mySessionListAdapter.mySessionList = it
            }
        })

        viewModel.loadLocalData()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            onBackPressed()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemEditClick(position: Int, mySessionItemDetail: MySessionMainDTO) {

    }
}
