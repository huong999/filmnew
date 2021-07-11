package com.example.newfilm.Fragment;

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

import com.example.newfilm.Model.Account;
import com.example.newfilm.R;
import com.example.newfilm.SQL.SqlHelper;
import com.example.newfilm.databinding.FragmentRegistrationBinding;


public class RegistrationFragment extends Fragment {
    FragmentRegistrationBinding binding;
    SqlHelper sqlHelper;
    public static RegistrationFragment newInstance() {

        Bundle args = new Bundle();

        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration,container,false);
        sqlHelper = new SqlHelper(getContext());
        binding.edtRePassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.edtPassWord.getText().toString();
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
                String phone = binding.edtUserName.getText().toString();
                String password = binding.edtPassWord.getText().toString();
                String repassword = binding.edtRePassWord.getText().toString();
                String fullname = binding.edtFullName.getText().toString();
                String address = binding.edtAddress.getText().toString();
                if(phone.length()>0&&password.length()>0&&repassword.equals(password)&&address.length()>0){
                    binding.layoutCheckCode.setVisibility(View.VISIBLE);
                    binding.btnRegister.setText(getString(R.string.confirm));
                    String code = binding.edtConfirm.getText().toString();
                    if(code.length()>0){
                        Fragment fragment = LoginFragmrnt.newInstance();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.dangnhap, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        Toast.makeText(getContext(),getString(R.string.check_sucess_true), Toast.LENGTH_SHORT).show();
                        sqlHelper.InsertAccount(new Account(phone,password,fullname,address));
                    }else{
                        Toast.makeText(getContext(),getString(R.string.check_confirm_code), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getContext(),getString(R.string.check_sucess_false), Toast.LENGTH_SHORT).show();
                }

            }
        });
        return binding.getRoot();
    }
}
