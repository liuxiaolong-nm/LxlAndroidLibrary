package com.cyhz.extend.activity.album;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaolong on 16/3/11.
 */
public class ExtAlbumModel {
    private String title;
    private String path;
    private String time;
    private String parentName;
    private String width;
    private String height;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "{" +
                " \"title\":" + "\"" + title + "\"" +
                ", \"path\":" + "\"" + path + "\"" +
                ", \"time\":" + "\"" + time + "\"" +
                ", \"parentName\":" + "\"" + parentName + "\"" +
                ", \"width\":" + "\"" + width + "\"" +
                ", \"height\":" + "\"" + height + "\"" +
                "}";
    }

    /**
     * 字符串变成AlbumModel对象。
     * @param string
     * @return
     * @throws JSONException
     */
    public static ExtAlbumModel stringTo(String string) throws JSONException {
        JSONObject json = new JSONObject(string);
        ExtAlbumModel albumModel = new ExtAlbumModel();
        albumModel.setTitle(json.getString("title"));
        albumModel.setPath(json.getString("path"));
        albumModel.setTime(json.getString("time"));
        albumModel.setParentName(json.getString("parentName"));
        albumModel.setWidth(json.getString("width"));
        albumModel.setHeight(json.getString("height"));
        return albumModel;
    }

    /**
     * List<AlbumModel> --> List<String>.
     * @param models
     * @return
     */
    public static List<String> toStrings(List<ExtAlbumModel> models){
        List<String> strings = new ArrayList<>();
        for (ExtAlbumModel m : models){
            strings.add(m.toString());
        }
        return strings;
    }

    /**
     * List<String> strings --> List<AlbumModel>.
     * @param strings
     * @return
     */
    public static List<ExtAlbumModel> stringsTo(List<String> strings){
        List<ExtAlbumModel> models = new ArrayList<>();
        for (String m : strings){
            try {
                models.add(ExtAlbumModel.stringTo(m));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return models;
    }
}