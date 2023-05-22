package com.example.myapplication;

public class PdfFile {
    private String title;
    private String url;
    private boolean isLoading;

    public PdfFile() {

    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
    public PdfFile(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}