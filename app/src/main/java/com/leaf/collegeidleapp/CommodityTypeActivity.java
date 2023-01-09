package com.leaf.collegeidleapp;


import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.leaf.collegeidleapp.adapter.AllCommodityAdapter;
import com.leaf.collegeidleapp.bean.Commodity;
import com.leaf.collegeidleapp.util.TransJson;

import java.util.LinkedList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 不同类型商品信息的活动类
 * @author autumn_leaf
 */
public class CommodityTypeActivity extends AppCompatActivity {

    TextView tvCommodityType;
    ListView lvCommodityType;
    List<Commodity> commodities = new LinkedList<>();
    String type;
    TransJson transJson=new TransJson();

    AllCommodityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_type);
        //返回事件
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCommodityType = findViewById(R.id.tv_type);
        lvCommodityType = findViewById(R.id.list_commodity);

        adapter = new AllCommodityAdapter(getApplicationContext());
        //根据不同的状态显示不同的界面
        int status = this.getIntent().getIntExtra("status",0);
        String searchContent = this.getIntent().getStringExtra("searchContent");
        tvCommodityType.setText(searchContent);
        //根据不同类别显示不同的商品信息
        type=tvCommodityType.getText().toString();
        OkHttpGet();
    }


    public void OkHttpGet()
    {
        new Thread(){
            @Override
            public void run() {
                try {
                    RequestBody formBody = new FormBody.Builder()
                        .add("name", type)
                        .build();
                    System.out.println("type = " + type);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                        .url("http://10.0.2.2:8081/search")
                        .post(formBody)
                        .build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();
                    commodities=transJson.ttransJSON(s);
                    adapter.setData(commodities);
                    lvCommodityType.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
