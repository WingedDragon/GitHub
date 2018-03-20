package com.example.dong.github.Net.API.Service

import com.example.dong.github.Net.API.Data.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by 振兴 on 2018.2.19.
 */
interface APIService {

    //https://api.github.com/user?access_token=429c040b3e92a297965ba643546b01e23217525c
    @GET("user")
    fun getLoginUserInformation():Observable<LoginUserInformation>

    //"repository_search_url":
    // "https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}"
    @GET("search/repositories")
    fun getSearchRepositoriesResult(@Query("q") key:String,@Query("page") page:Int,
                        @Query("per_page") per_page:Int,@Query("sort") sort:String,
                        @Query("order") order:String):Observable<SearchResult>

    //https://api.github.com/users/{user}/repos{?type,page,per_page,sort}
    @GET("users/{user}/repos")
    fun getUserRepositoriesResult(@Path("user") user:String,@Query("type") type:String,
                                  @Query("page") page: Int,@Query("per_page") per_page: Int,
                                  @Query("sort") sort: String):Observable<MutableList<UserRepositories>>

    //https://api.github.com/repos/hmkcode/Android
    @GET("repos/{owner}/{name}")
    fun getRepositoriesDetails(@Path("owner") owner:String,@Path("name") name:String):Observable<RepositoriesDetails>

    //https://api.github.com/repos/open-android/Android/stargazers
    @GET("repos/{owner}/{name}/stargazers")
    fun getRepositoriesStargazers(@Path("owner") owner: String, @Path("name") name: String, @Query("page") page: Int):Observable<List<Stargazers>>

    //GET /repos/:owner/:repo/events
    @GET("repos/{owner}/{name}/events")
    fun getRepositorisEvents(@Path("owner") owner: String,@Path("name") name: String,@Query("page") page:Int):Observable<List<RepositoriesEvents>>

    //GET /repos/:owner/:repo/readme
    @GET("repos/{owner}/{name}/readme")
    fun getReadMe(@Path("owner") owner: String,@Path("name") name: String):Observable<ReadMe>

    //GET /repos/:owner/:repo/issues
    @GET("repos/{owner}/{name}/issues")
    fun getRepositorisIssues(@Path("owner") owner: String,@Path("name") name: String,@Query("page") page:Int):Observable<List<RepositoriesIssues>>

    //GET /repos/:owner/:repo/contents/:path
    @GET("repos/{owner}/{name}/contents/{path}")
    fun getProjectIndex(@Path("owner") owner: String, @Path("name") name: String, @Path("path") path: String):Observable<MutableList<ProjectIndex>>

    @GET("repos/{owner}/{name}/contents/{path}")
    fun getFileContent(@Path("owner") owner: String, @Path("name") name: String, @Path("path") path: String):Observable<FileContent>

    @PUT("/user/starred/{owner}/{repo}")
    fun StaredRepositors(@Path("owner") owner: String,@Path("repo") name: String):Observable<Response<ResponseBody>>

    @GET("/user/starred/{owner}/{repo}")
    fun isStaredRepositors(@Path("owner") owner: String,@Path("repo") name: String):Observable<Response<ResponseBody>>

    @DELETE("/user/starred/{owner}/{repo}")
    fun deleteStaredRepositors(@Path("owner") owner: String,@Path("repo") name: String):Observable<Response<ResponseBody>>

    //"https://github.com/owncloud/android/archive/master.zip"
    @Streaming
    @GET
    fun DownloadFile(@Header("RANGE") range:String, @Url url:String):Observable<ResponseBody>

    @GET("/users/{username}/starred")
    fun getStarredRepositories(@Path("username") username:String,@Query("sort") sort: String,
                               @Query("direction") direction:String,@Query("page") page: Int):Observable<ArrayList<CloneRepositories>>
}