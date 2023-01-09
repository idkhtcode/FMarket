package com.leaf.collegeidleapp;


import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.leaf.collegeidleapp.adapter.MyCommodityAdapter;
import com.leaf.collegeidleapp.bean.Commodity;
import com.leaf.collegeidleapp.bean.User;
import com.leaf.collegeidleapp.util.StudentTrans;
import com.leaf.collegeidleapp.util.TransJson;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 我的发布物品Activity类
 */
public class MyRecommendation extends AppCompatActivity {

    ListView lvMyCommodity;
    TextView tvStuYear;

    MyCommodityAdapter adapter;
    List<Commodity> commodities = new ArrayList<>();
    TransJson transJson=new TransJson();

    LinkedList<User> list = new LinkedList<>();
    StudentTrans studentTrans=new StudentTrans();
    String username = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommendation);
        TextView tvBack = findViewById(R.id.tv_back);
        //点击返回销毁当前界面
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvStuYear = findViewById(R.id.tv_stu_year);
        year2semester(this.getIntent().getStringExtra("stu_id"));
        lvMyCommodity = findViewById(R.id.lv_my_commodity);
        username = this.getIntent().getStringExtra("stu_id");
        adapter = new MyCommodityAdapter(getApplicationContext());

        new Thread(){
            @Override
            public void run() {
                try {
                    RequestBody formBody = new FormBody.Builder()
                            .add("username", username)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8081/bookRecommend")
                            .post(formBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();
                    System.out.println("s = " + s);
                    commodities=transJson.ttransJSON(s);

                    adapter.setData(commodities);

                    lvMyCommodity.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //刷新界面点击事件
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            RequestBody formBody = new FormBody.Builder()
                                    .add("username", username)
                                    .build();
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2:8081/bookRecommend")
                                    .post(formBody)
                                    .build();
                            Response response = client.newCall(request).execute();
                            String s = response.body().string();
                            System.out.println("s = " + s);
                            commodities=transJson.ttransJSON(s);

                            adapter.setData(commodities);

                            lvMyCommodity.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
    public void year2semester(String username) {
        new Thread(){
            @Override
            public void run() {
                String year = null;
                try {
                    System.out.println("username is = " + username);
                    RequestBody formBody = new FormBody.Builder()
                            .add("username", username)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8081/getUser")
                            .post(formBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();
                    list=studentTrans.StudentTrans("["+s+"]");
//                    User user1 = JSON.parseObject(s, User.class);
                    if(list != null) {
                        for(User user : list) {
                            year = user.getYear();
                        }
                        String res;
                        Calendar calendar = Calendar.getInstance();
                        int nowYear = calendar.get(Calendar.YEAR);
                        int nowMonth = calendar.get(Calendar.MONTH);
                        if (nowYear > Integer.parseInt(year)) {
                            int temp = nowYear - Integer.parseInt(year);
                            if (temp <= 4) {
                                String[] Class = {"大一", "大二", "大三", "大四"};
                                res = Class[temp - 1];
                                if (nowMonth < 9) {
                                    res += "下";
                                    tvStuYear.setText(res);
                                } else {
                                    res = Class[temp] + "上";
                                    tvStuYear.setText(res);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
