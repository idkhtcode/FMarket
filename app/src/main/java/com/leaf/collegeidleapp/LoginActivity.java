package com.leaf.collegeidleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录界面Activity类
 * @author : autumn_leaf
 */
public class LoginActivity extends AppCompatActivity {

    EditText EtStuNumber,EtStuPwd;
    private String username;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvRegister = findViewById(R.id.tv_register);
        //跳转到注册界面
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        EtStuNumber = findViewById(R.id.et_username);
        EtStuPwd = findViewById(R.id.et_password);
        EtStuNumber.setText("87");
        EtStuPwd.setText("87");
        System.out.println(EtStuNumber.getText());
        Button btnLogin = findViewById(R.id.btn_login);
        //点击登录
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable()
                {
                    public void run()
                    {
                        RequestBody formBody = new FormBody.Builder()
                                .add("username", EtStuNumber.getText().toString())
                                .add("password", EtStuPwd.getText().toString())
                                .build();
                        OkHttpClient client = new OkHttpClient();

                        //发送账号信息
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2:8081/login")
                                    .post(formBody)
                                    .build();
                        Response response = null;
                        String s = null;
                        try {
                            response = client.newCall(request).execute();
                            s= response.body().string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
//                        String s = "2";
//                            int res = Integer.valueOf(s);
                        System.out.println(s);
                        //根据后端反应确定是否登陆成功
                        if (s.equals("failure"))
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(), "账号不存在或密码错误",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else
                        {
                            //登陆成功跳转到主页面
                            if (s.equals("success"))
                            {
                                //接收学生信息数据
//                                OkHttpClient okHttpClient = new OkHttpClient();
//                                final Request request2 = new Request.Builder()
//                                        .url("http://10.0.2.2:8081/getGoods")
//                                        .get()
//                                        .build();
//                                Call call = okHttpClient.newCall(request2);
//                                call.enqueue(new Callback() {
//                                    @Override
//                                    public void onFailure(Call call, IOException e) {
//                                    }
//
//                                    @Override
//                                    public void onResponse(Call call, Response response) throws IOException {
//                                        String s1 =response.body().string();
//                                        System.out.println(s1);
//                                    }
//                                });


                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this, MainActivity.class);

                                Bundle bundle = new Bundle();
                                username = EtStuNumber.getText().toString();
                                bundle.putString("username",username);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Toast.makeText(getApplicationContext(), "登陆成功",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });
    }

    //检查输入是否符合要求
    public boolean CheckInput() {
        String StuNumber = EtStuNumber.getText().toString();
        String StuPwd = EtStuPwd.getText().toString();
        if(StuNumber.trim().equals("")) {
            Toast.makeText(LoginActivity.this,"学号不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StuPwd.trim().equals("")) {
            Toast.makeText(LoginActivity.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
