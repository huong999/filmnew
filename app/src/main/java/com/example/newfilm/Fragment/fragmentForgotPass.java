package com.example.newfilm.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.newfilm.Activity.StartApp;
import com.example.newfilm.Model.Account;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.FragmentforgotpassBinding;

import java.util.ArrayList;
import java.util.List;

public class fragmentForgotPass extends Fragment {
    FragmentforgotpassBinding binding;
    SqlHelper sqlHelper;
    List<Account> list;

    public static fragmentForgotPass newInstance() {
        Bundle bundle = new Bundle();
        fragmentForgotPass fragmentForgotPass = new fragmentForgotPass();
        fragmentForgotPass.setArguments(bundle);
        return fragmentForgotPass;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentforgotpass, container, false);
        sqlHelper = new SqlHelper(getContext());
        final String user = binding.edtUserName.getText().toString();
        list = new ArrayList<>();
        list = sqlHelper.getAllAccount();
        final String pass = binding.newpass.getText().toString();
        // String pass =

         binding.edtRePassWord.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String password = binding.newpass.getText().toString();
                    if(s.toString().equals(password)){
                        binding.layoutedtRepassWord.setError(null);
                    }else{
                        binding.layoutedtRepassWord.setError(getString(R.string.check_equal));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        binding.btnBackToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = LoginFragmrnt.newInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.dangnhap, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.btnRegister.setText("Thay đổi");
                    String phone = binding.edtUserName.getText().toString();
                    String password = binding.edtPassWord.getText().toString();
                    String repassword = binding.edtRePassWord.getText().toString();
                    if(phone.length() <10){
                        binding.btnRegister.setText("Nhập lại sđt");
                    }
                    else if (phone.length() > 0 && password.length() > 8 && repassword.equals(password)) {
                        binding.btnRegister.setText(getString(R.string.confirm));

                    }
                   if (phone.length() == 10){
                       for (int i = 0 ; i<list.size();i++){
                           if (phone.equals(list.get(i).getPhone())){
                               sqlHelper.upDatePass(phone,binding.newpass.getText().toString());
                               Intent in = new Intent(getContext(), DangNhap.class);
                               startActivity(in);
                           }else {
                               binding.btnRegister.setText("Sđt không tồn tai");
                           }
                       }
                   }

                }

            });

        return binding.getRoot();
    }
}
