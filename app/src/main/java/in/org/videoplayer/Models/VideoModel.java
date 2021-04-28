package in.org.videoplayer.Models;

import android.graphics.Bitmap;
import android.net.Uri;

public class VideoModel {
    private String name;
    private String size;
    private String duration;
    private String date;
    private Uri data;
    private Bitmap thumbnail;

    public VideoModel() {

    }

    public VideoModel(String name, String size, String duration, String date, Uri data, Bitmap thumbnail) {
        this.name = name;
        this.size = size;
        this.duration = duration;
        this.date = date;
        this.data = data;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

}
