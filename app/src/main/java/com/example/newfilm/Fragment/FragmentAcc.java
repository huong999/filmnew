package com.example.newfilm.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.newfilm.Activity.DangNhap;
import com.example.newfilm.R;
import com.example.newfilm.databinding.FragmentaccBinding;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_FULL_NAME;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_PHONE;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.newfilm.Model.AccountAttribute.SHARE_PRE_NAME;

public class FragmentAcc extends Fragment {

    FragmentaccBinding binding;

    public static FragmentAcc newInstance() {
        Bundle bundle = new Bundle();
        FragmentAcc acc = new FragmentAcc();
        acc.setArguments(bundle);
        return acc;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentacc, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        String fullname = sharedPreferences.getString(ACCOUNT_FULL_NAME, "");
        String phone = sharedPreferences.getString(ACCOUNT_PHONE, "");
        //  String address = sharedPreferences.getString(ACCOUNT_ADDRESS, "");
        if (getStatus()) {
            binding.usernameTextView.setText(fullname);
            binding.changepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = fragmentChangePass.newInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            binding.dx.setText("Đăng xuất");
        } else {
            binding.usernameTextView.setText(" ");
            binding.changepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            binding.dx.setText("Đăng nhập");
        }
        binding.dx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getStatus()) {
                    binding.usernameTextView.setText("");
                    binding.dx.setText("Đăng nhập");
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(ACCOUNT_STATUS, false);
                    editor.apply();
                    Toast.makeText(getContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), DangNhap.class);
                    startActivity(intent);
                }
            }
        });

        return binding.getRoot();
    }


    public boolean getStatus() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }
}
