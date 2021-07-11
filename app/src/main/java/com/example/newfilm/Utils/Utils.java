package com.example.newfilm.Utils;


import com.example.newfilm.Model.Video;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Video> list = new ArrayList<>();

    public static List<Video> getList() {
        return list;
    }

    public static void setList(List<Video> list) {
        Utils.list = list;
    }
}
