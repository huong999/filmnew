package com.example.newfilm.Fragment;

import android.content.Context;
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

import com.example.newfilm.Activity.StartApp;
import com.example.newfilm.MainActivity;
import com.example.newfilm.Model.Account;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.adim.ReportActivity;
import com.example.newfilm.databinding.FragmentLoginBinding;
import com.example.newfilm.databinding.FragmentforgotpassBinding;

import java.util.ArrayList;
import java.util.List;

import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_ADDRESS;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_FULL_NAME;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_ID;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_PHONE;
import static com.example.newfilm.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.newfilm.Model.AccountAttribute.SHARE_PRE_NAME;


public class LoginFragmrnt extends Fragment {
    FragmentLoginBinding binding;
    List<Account> list;
    SqlHelper sqlHelper;
    SharedPreferences sharedPreferences1;

    public static LoginFragmrnt newInstance() {

        Bundle args = new Bundle();
        LoginFragmrnt fragment = new LoginFragmrnt();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        list = new ArrayList<>();
        sqlHelper = new SqlHelper(getContext());
        list = sqlHelper.getAllAccount();
        sharedPreferences1 = getContext().getSharedPreferences("c", Context.MODE_PRIVATE);
        binding.edtUserName.setText(sharedPreferences1.getString("ph", ""));
        binding.edtPasWord.setText(sharedPreferences1.getString("pa", ""));
        binding.checkbox.setChecked(sharedPreferences1.getBoolean("checkbox", false));

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtUserName.getText().toString();
                String password = binding.edtPasWord.getText().toString();
                if (username.length() > 0 && password.length() > 0) {
                    boolean check = false;
                    for (Account x : list) {
                        if (x.getPhone().equals(username) && x.getPassword().equals(password)) {
                            Intent i = new Intent(getContext(), StartApp.class);
                            startActivity(i);

                            if (binding.checkbox.isChecked()) {
                                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                editor1.putString("ph", username);
                                editor1.putString("pa", password);
                                editor1.putBoolean("checkbox", true);
                                editor1.commit();
                            } else {
                                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                editor1.remove("ph");
                                editor1.remove("pa");
                                editor1.remove("checkbox");
                                editor1.commit();

                            }
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(ACCOUNT_STATUS, true);
                            editor.putString(ACCOUNT_FULL_NAME, x.getFullName());
                            editor.putString(ACCOUNT_PHONE, x.getPhone());
                            editor.putString(ACCOUNT_ADDRESS, x.getAddress());
                            editor.putInt(ACCOUNT_ID, x.getId());
                            editor.apply();
                            check = true;
                            Toast.makeText(getContext(), getString(R.string.login_sucess), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (!check) {
                        Toast.makeText(getContext(), getString(R.string.check_login_fail), Toast.LENGTH_SHORT).show();
                    }

                }   else {
                    Toast.makeText(getContext(), getString(R.string.check_null), Toast.LENGTH_SHORT).show();
                }
                if (username.equals("0911328013") && password.equals("12345678")) {
                    Intent i = new Intent(getContext(), ReportActivity.class);
                    startActivity(i);
                }

            }
        });
        binding.tvForgetPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = fragmentForgotPass.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.dangnhap, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.newAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = RegistrationFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.dangnhap, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        binding.btnBackToHomeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StartApp.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();


    }
}

