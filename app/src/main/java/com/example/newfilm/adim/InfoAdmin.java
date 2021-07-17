package com.example.newfilm.adim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.newfilm.R;
import com.example.newfilm.databinding.ActivityAccAdBinding;

public class InfoAdmin extends Fragment {
    ActivityAccAdBinding binding;

    public static InfoAdmin newInstance() {
        Bundle bundle = new Bundle();
        InfoAdmin acc = new InfoAdmin();
        acc.setArguments(bundle);
        return acc;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_acc_ad, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
