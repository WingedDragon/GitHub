package com.example.dong.github.View.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.example.dong.github.Net.API.Data.RepositoriesDetails
import com.example.dong.github.View.Base.BaseActivity
import com.example.dong.github.MVP.contract.RepositoriesDetailsContract
import com.example.dong.github.MVP.presenter.RepositoriesDetailsPresenter
import com.example.dong.github.R
import kotlinx.android.synthetic.main.activity_repositories_details.*

class RepositoriesDetailsActivity : BaseActivity(),RepositoriesDetailsContract.View {

    val presenter:RepositoriesDetailsPresenter = RepositoriesDetailsPresenter(this)
    var owner:String = ""
    var name:String = ""

    @SuppressLint("SetTextI18n")
    override fun update(t: RepositoriesDetails) {
        repositories_details_Name.text = t.name
        Glide.with(this).load(t.owner.avatar_url).into(repositories_details_Picture)
        repositories_details_Detail.text = t.description
        repositories_details_Stargazers.text = t.stargazers_count.toString()
        repositories_details_Forks.text = t.forks_count.toString()
        repositories_details_Watchers.text = t.subscribers_count.toString()
        repositories_details_Private.text = if (t.isPrivateX) "Private" else "Public"
        repositories_details_Language.text = if(t.language != null) t.language else "null"
        repositories_details_Issue.text = "${t.open_issues_count} Issue"
        repositories_details_Owner.text = t.owner.login
        repositories_details_Date.text = t.created_at.subSequence(0,10)
        repositories_details_Size.text = if(t.size >= 1000) "${t.size / 100 / 10}.${t.size / 100 % 10} KB" else "${t.size} B"
        repositories_details_container.visibility = View.VISIBLE
        IsRefresh(false)
    }

    override fun initRecyclerView() {
    }

    override fun initClickListener() {
        repositories_details_Stargazers_container.setOnClickListener {
            val intent = Intent(this, RepositoriesStargazersActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_Watchers_container.setOnClickListener {
            val intent = Intent(this, RepositoriesStargazersActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_Forks_container.setOnClickListener {
            val intent = Intent(this, RepositoriesStargazersActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_Owner_container.setOnClickListener {
            val intent = Intent(this, ReadMeActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_Events_container.setOnClickListener {
            val intent = Intent(this, RepositoriesEventsActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_Issues_container.setOnClickListener {
            val intent = Intent(this, ReadMeActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_ReadMe_container.setOnClickListener {
            val intent = Intent(this, ReadMeActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_Commits_container.setOnClickListener {
            val intent = Intent(this, ReadMeActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_PullRequests_container.setOnClickListener {
            val intent = Intent(this, ReadMeActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_Source_container.setOnClickListener {
            val intent = Intent(this, SourceActivity::class.java)
            intent.putExtra("owner",owner)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        repositories_details_Star.setOnClickListener {
            if(repositories_details_Star.text == "Stared"){
                presenter.StaredRepoisitors(owner, name)
            }else{
                presenter.DeleteStaredRepoisitories(owner,name)
            }
        }
        repositories_details_Fork.setOnClickListener {

        }
        repositories_details_Clone.setOnClickListener {
//            presenter.DownloadFile(owner,name,"https://github.com/$owner/$name/archive/master.zip")
        }
    }

    override fun isStaredSuccess(b: Boolean) {
        if(b){
            repositories_details_Star.text = getString(R.string.unstared)
        }
    }

    override fun deleteStaredSuccess(b: Boolean) {
        if(b){
            repositories_details_Star.text = getString(R.string.stared)
        }
    }

    override fun setStaredable(b: Boolean) {
        if(b){
            repositories_details_Star.text = getString(R.string.stared)
        }else{
            repositories_details_Star.text = getString(R.string.unstared)
        }
    }

    override fun initSwipRefreshLayout() {
        BindSwipeRefreshLayout(repositories_details_SwipeRefreshLayout)
        BindSwipeRefreshLayout(repositories_details_SwipeRefreshLayout)
        repositories_details_SwipeRefreshLayout.isEnabled = true
        repositories_details_SwipeRefreshLayout.setOnRefreshListener {
            presenter.getRepositoriesDetails(owner,name)
        }
    }

    override fun InitView() {
        initIntent()
        initSwipRefreshLayout()
        initRecyclerView()
        initClickListener()
        initButton()
        initData()
    }

    private fun initButton() {
        presenter.initButton(owner,name)
    }

    private fun initIntent() {
        owner = intent.getStringExtra("owner")
        name = intent.getStringExtra("name")
    }

    private fun initData() {
        IsRefresh(true)
        presenter.getRepositoriesDetails(owner,name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        makeActionOverflowMenuShown()
        setContentView(R.layout.activity_repositories_details)

        InitView()
    }

    private fun makeActionOverflowMenuShown() {
        try{
            val config = ViewConfiguration.get(this)
            val menuKeyField = ViewConfiguration::class.java.getDeclaredField("sHasPermanentMenuKey")
            if(menuKeyField != null){
                menuKeyField.isAccessible = true
                menuKeyField.setBoolean(config,false)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.repositories_details_menu_Stared -> {
                toast("Stared")
                true
            }
            R.id.repositories_details_Fork -> {
                toast("Fork")
                true
            }
            R.id.repositories_details_Clone -> {
                toast("Clone")
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
