package com.example.newfilm.Connect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MyReciver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(context, "Bạn đang sử dụng dữ liệu di động", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(context, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
        }

    }


}
