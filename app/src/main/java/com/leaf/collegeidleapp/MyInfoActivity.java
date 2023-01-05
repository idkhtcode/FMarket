package com.leaf.collegeidleapp;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leaf.collegeidleapp.bean.User;
import com.leaf.collegeidleapp.util.StudentTrans;
import java.util.LinkedList;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 我的个人信息活动类
 * @author : autumn_leaf
 */
public class MyInfoActivity extends AppCompatActivity {

    TextView tvStuName,tvStuMajor,tvStuPhone,tvStuEmail,tvStuAddress,tvStuYear;
    String username;
    LinkedList<User> list = new LinkedList<>();
    StudentTrans studentTrans=new StudentTrans();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        Button btnBack = findViewById(R.id.btn_back);
        //返回点击事件,销毁当前界面
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //从bundle中获取用户账号/学号
        final TextView tvUserNumber = findViewById(R.id.tv_stu_number);
        username = this.getIntent().getStringExtra("stu_number1");
        tvUserNumber.setText(username);

        tvStuName = findViewById(R.id.tv_stu_name);
        tvStuMajor = findViewById(R.id.tv_stu_major);
        tvStuPhone = findViewById(R.id.tv_stu_phone);
        tvStuEmail = findViewById(R.id.tv_stu_email);
        tvStuAddress = findViewById(R.id.tv_stu_address);
        tvStuYear = findViewById(R.id.tv_stu_year);
        OkHttpGet();

        Button btnModifyInfo = findViewById(R.id.btn_modify_info);
        //跳转到修改用户信息界面
        btnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ModifyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stu_number2",tvUserNumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //刷新按钮点击事件
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpGet();
            }
        });

    }

    public void OkHttpGet()
    {
        new Thread(){
            @Override
            public void run() {
                try {
                    RequestBody formBody = new FormBody.Builder()
                            .add("username", username)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.126:8081/getUser")
                            .post(formBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();
                    System.out.println("s = " + s);
                    list=studentTrans.StudentTrans("["+s+"]");
//                    User user1 = JSON.parseObject(s, User.class);
                    if(list != null) {
                        for(User user : list) {
                            tvStuName.setText(user.getUsername());
                            tvStuMajor.setText(user.getMajor());
                            tvStuYear.setText(user.getYear());
//                            tvStuPhone.setText(user.get);
                            tvStuEmail.setText(user.getEmail());
//                            tvStuAddress.setText(user.getAddress());
                        }
                    }else {
                        tvStuName.setText("暂未填写");
                        tvStuMajor.setText("暂未填写");
                        tvStuPhone.setText("暂未填写");
                        tvStuEmail.setText("暂未填写");
                        tvStuAddress.setText("暂未填写");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
