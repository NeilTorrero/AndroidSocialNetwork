package com.example.androidsocialnetwork.Threads;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ThreaduploadImageCloud extends Thread{
    private String result;

    public ThreaduploadImageCloud (String s) {
        result = s;
    }

    @Override
    public void run() {
        try {
            Map config = new HashMap();
            config.put("cloud_name", "di9vxufjy");
            config.put("api_key", "239948647138554");
            config.put("api_secret", "xmfnQH8n_TKcAKhlp2nc2pH3GoE");

            Cloudinary cloudinary = new Cloudinary(config);
            Map uploadResult  = cloudinary.uploader().upload(result, ObjectUtils.emptyMap());
            String url = uploadResult.get("url").toString();
            //We have now the url of the image uploaded
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
