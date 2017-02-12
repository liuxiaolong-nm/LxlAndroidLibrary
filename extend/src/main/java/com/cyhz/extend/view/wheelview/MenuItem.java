package com.cyhz.extend.view.wheelview;


/**
 * Created by Ex_dog on 14-7-31.
 */
public class MenuItem {
    private String id;
    private String title;
    private int res = -1;
    private String extend;

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public MenuItem(){

    }

    public MenuItem(String id){

    }

    public MenuItem(String id, String title, int res) {
        this.id = id;
        this.res = res;
        this.title = title;
    }

    public MenuItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getTitle() {
        return title;
    }

    public int getRes() {
        return res;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MenuItem{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", res=").append(res);
        sb.append('}');
        return sb.toString();
    }
}
