package com.example.dong.github.Net.API.Data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Objects;

/**
 * Created by 振兴 on 2018.3.6.
 */

public class ProjectIndex implements MultiItemEntity{
    /**
     * name : .classpath
     * path : .classpath
     * sha : 72895a2990ddc9c76eaa2b4da3bf89bb9650896b
     * size : 466
     * url : https://api.github.com/repos/owncloud/android/contents/.classpath?ref=master
     * html_url : https://github.com/owncloud/android/blob/master/.classpath
     * git_url : https://api.github.com/repos/owncloud/android/git/blobs/72895a2990ddc9c76eaa2b4da3bf89bb9650896b
     * download_url : https://raw.githubusercontent.com/owncloud/android/master/.classpath
     * type : file
     * _links : {"self":"https://api.github.com/repos/owncloud/android/contents/.classpath?ref=master","git":"https://api.github.com/repos/owncloud/android/git/blobs/72895a2990ddc9c76eaa2b4da3bf89bb9650896b","html":"https://github.com/owncloud/android/blob/master/.classpath"}
     */

    private String name;
    private String path;
    private String sha;
    private int size;
    private String url;
    private String html_url;
    private String git_url;
    private String download_url;
    private String type;
    private LinksBean _links;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinksBean get_links() {
        return _links;
    }

    public void set_links(LinksBean _links) {
        this._links = _links;
    }

    @Override
    public int getItemType() {
        if(Objects.equals(type, "file")){
            return 1;
        }else {
            return 0;
        }
    }

    public static class LinksBean {
        /**
         * self : https://api.github.com/repos/owncloud/android/contents/.classpath?ref=master
         * git : https://api.github.com/repos/owncloud/android/git/blobs/72895a2990ddc9c76eaa2b4da3bf89bb9650896b
         * html : https://github.com/owncloud/android/blob/master/.classpath
         */

        private String self;
        private String git;
        private String html;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getGit() {
            return git;
        }

        public void setGit(String git) {
            this.git = git;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }
    }
}
