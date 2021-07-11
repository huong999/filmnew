package com.example.newfilm.adim;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.ActivityReportBinding;

public class ReportActivity extends AppCompatActivity {
    private SqlHelper sqlHelper;
    private ActivityReportBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        sqlHelper = new SqlHelper(this);
        binding.txtProgress.setText(sqlHelper.getAllAccount().size() + "");
       // binding.filmdaitap.setText(sqlHelper.get);
    }
}
