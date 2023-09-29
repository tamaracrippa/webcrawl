package com.axreng.backend.util;

import java.util.ArrayList;

public class MyResponse {
    private String id;
    private String status;
    private ArrayList<String> urls;
    private String message;
    private boolean success;

    public MyResponse(String id, String status, ArrayList<String> urls) {
        this.id = id;
        this.status = status;
        this.urls = urls;
    }

    public MyResponse(String message) {
        this.message = message;
        this.success = true;
    }
}
