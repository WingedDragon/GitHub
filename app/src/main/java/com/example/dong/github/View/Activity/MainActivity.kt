package com.example.dong.github.View.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.TextView
import com.example.dong.github.Net.API.Data.LoginUserInformation
import com.example.dong.github.Adapter.FragmentAdapter
import com.example.dong.github.View.Base.BaseActivity
import com.example.dong.github.MVP.contract.MainContract
import com.example.dong.github.MVP.presenter.MainPresenter
import com.example.dong.github.R
import com.example.dong.github.Util.*
import com.example.dong.github.View.Fragment.CloneFragment
import com.example.dong.github.View.Fragment.NewsFragment
import com.example.dong.github.View.Fragment.RepositoriesFragment
import com.liji.circleimageview.CircleImageView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() , MainContract.View {

    private val presenter:MainPresenter = MainPresenter(this)
    private val newsFragment = NewsFragment()
    private val repositoriesFragment = RepositoriesFragment()
    private val cloneFragment = CloneFragment()
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitle = arrayListOf<String>("News","Repositories","Starred")
    lateinit var viewPagerAdapter:FragmentPagerAdapter

    private val context:Context

    init {
        context = this
    }

    override fun CallSearchResultActivity(t: String) {
        val intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra("SearchContent",t)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        InitView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu,menu)
        main_searchView.setMenuItem(menu?.findItem(R.id.main_toolbar_search))
        return super.onCreateOptionsMenu(menu)
    }


    override fun Login(t: LoginUserInformation) {
        val headerview = main_navigationView.getHeaderView(0)
        ImageLoad.load(this,t.avatar_url,headerview.findViewById<CircleImageView>(R.id.main_login))
        headerview.findViewById<TextView>(R.id.main_username).text = t.login
        AccountManage.hasLogin = true
        AccountManage.isLogin = false
        AccountManage.Login = t.login
        presenter.saveLoginUserInformation(t)
    }

    override fun InitView() {
        InitPermission()
        InitToolBar()
        InitMaterialSearchView()
        InitNavigationView()
        InitLogin()
        InitFragment()
        InitViewPager()
        InitTabPageIndicator()
        FileManage.initFilePath()
    }

    private fun InitPermission() {
        PermissionManage.SinglePermissionRequest(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe{
                    PermissionManage.hasWriteStorage = it
                }
    }

    private fun InitTabPageIndicator() {
        main_TabPageIndicator.setViewPager(main_ViewPager)
        main_TabPageIndicator.apply {
            isClickable = true
        }
    }

    private fun InitFragment() {
        mFragmentList.apply {
            add(newsFragment)
            add(repositoriesFragment)
            add(cloneFragment)
        }
        viewPagerAdapter = FragmentAdapter(this.supportFragmentManager,mFragmentList,mFragmentTitle)
    }

    private fun InitViewPager() {
        main_ViewPager.apply {
            offscreenPageLimit = 3
            adapter = viewPagerAdapter
            currentItem = 0
        }
        main_ViewPager.setOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    private fun InitLogin() {
        if(AccountManage.hasAccess_Token){
            presenter.Login()
        }
    }

    private fun InitNavigationView() {
        val headerView = main_navigationView.getHeaderView(0)
        if(!AccountManage.hasAccess_Token){
            headerView.findViewById<CircleImageView>(R.id.main_login).setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground))
            headerView.findViewById<TextView>(R.id.main_username).text = "未登录"
        }
        headerView.findViewById<CircleImageView>(R.id.main_login).setOnClickListener {
            if(!AccountManage.hasAccess_Token){
                presenter.getLogin_Code(this)
            }
        }
        main_navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Downloading ->{
                    CloseDrawLayout()
                    Downloading_After()
                    true
                }
                R.id.Setting -> {
                    CloseDrawLayout()
                    Setting_After()
                    true
                }
                R.id.Sign_Out -> {
                    CloseDrawLayout()
                    Sign_Out_After(headerView)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun Downloading_After(){
//        val intent = Intent(this,DownloadManageActivity::class.java)
//        startActivity(intent)
    }

    private fun CloseDrawLayout() {
        main_drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun Setting_After() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun Sign_Out_After(headerView: View) {
        if(AccountManage.hasLogin){
            AccountManage.hasAccess_Token = false
            AccountManage.hasLogin = false
            headerView.findViewById<CircleImageView>(R.id.main_login).setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground))
            headerView.findViewById<TextView>(R.id.main_username).text = "未登录"
            presenter.saveSignOutInformation()
            toast("注销成功")
        }else{
            toast("未登录")
        }
    }

    private fun InitMaterialSearchView() {
        main_searchView.apply {
            setHint("搜索内容")
        }
        main_searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.Search(query)
                return false
            }
        })
    }

    private fun InitToolBar() {
        main_toolbar.setTitle("GitHub")
        setSupportActionBar(main_toolbar)
    }

    override fun onResume() {
        if(AccountManage.isLogin){
            presenter.getAccess_Token()
        }
        super.onResume()
    }

    override fun onBackPressed() {
        if(main_drawerLayout.isDrawerOpen(GravityCompat.START)){
            main_drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            if(main_searchView.isSearchOpen){
                main_searchView.closeSearch()
            }else{
                super.onBackPressed()
            }
        }
    }
}
