package com.example.dong.github.View.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import com.example.dong.github.Net.API.Data.ProjectIndex
import com.example.dong.github.View.Base.BaseActivity
import com.example.dong.github.Adapter.SourceRecyclerViewAdapter
import com.example.dong.github.MVP.contract.SourceContract
import com.example.dong.github.MVP.presenter.SourcePresenter
import com.example.dong.github.R
import kotlinx.android.synthetic.main.activity_source.*

class SourceActivity : BaseActivity(),SourceContract.View {

    private lateinit var adapter: SourceRecyclerViewAdapter
    private val presenter = SourcePresenter(this)
    private var owner = ""
    private var name = ""
    private var path = ""
    private val path_list = ArrayList<String>()
    private val type_list = ArrayList<Int>()

    init {
        path_list.add("")
        type_list.add(0)
    }

    override fun loadchangPath(s: String, i: Int) {
        this.path = "$s/"
        path_list.add(s)
        type_list.add(i)
    }

    override fun backchangPath() {
        path_list.removeAt(path_list.lastIndex)
        type_list.removeAt(type_list.lastIndex)
        this.path = path_list.last()
    }

    override fun loadFile(owner: String, name: String, path: String) {
        val intent = Intent(this, ReadFileActivity::class.java)
        intent.putExtra("owner",owner)
        intent.putExtra("name",name)
        intent.putExtra("path",path)
        startActivity(intent)
    }

    override fun InitView() {
        initIntent()
        initSwipeRefreshLayout()
        initData()
    }

    override fun initRecyclerView(t: MutableList<ProjectIndex>) {
        adapter = SourceRecyclerViewAdapter(t)
        source_RecyclerView.adapter = adapter
        source_RecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        source_RecyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        adapter.openLoadAnimation()
        adapter.isUpFetchEnable = false
        adapter.setOnItemClickListener { adapter, view, position ->
            val data = adapter.data[position] as ProjectIndex
            when(data.itemType){
                0 -> {
                    presenter.loadDir(owner,name,path,data.name,false)
                }
                1 -> {
                    presenter.loadFile(owner,name,path,data.name)
                }
            }
        }
    }

    private fun initSwipeRefreshLayout() {
        BindSwipeRefreshLayout(source_SwipeRefreshLayout)
        source_SwipeRefreshLayout.isEnabled = true
        source_SwipeRefreshLayout.setOnRefreshListener {
            presenter.initData(owner,name,path)
        }
    }

    override fun updata(t: MutableList<ProjectIndex>) {
        adapter.setNewData(t)
    }

    private fun initData() {
        presenter.initData(owner,name,path)
    }

    private fun initIntent() {
        owner = intent.getStringExtra("owner")
        name = intent.getStringExtra("name")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_source)

        InitView()
    }

    override fun onBackPressed() {
        if(path_list.size == 1){
            finish()
        }else{
            val temp = path_list.get(path_list.size - 2)
            if(type_list.get(type_list.size - 2) == 0){
                presenter.loadDir(owner,name,temp,"",true)
            }
        }
    }
}
