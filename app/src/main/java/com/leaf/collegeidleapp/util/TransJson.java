package com.leaf.collegeidleapp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leaf.collegeidleapp.bean.Commodity;

import java.util.ArrayList;
import java.util.List;

public  class TransJson {
    private String argss;
    public  TransJson() {

    }
    public TransJson(String argss) {
        this.argss=argss;
    }


    public List<Commodity>  ttransJSON(String args) {
        List<Commodity> list =new ArrayList<>();
        String jsonArray=args;
        //需要使用的JSON的parseArray方法，将jsonArray解析为object类型的数组
        JSONArray objects = JSON.parseArray(jsonArray);
        System.out.println("objects.size() = " + objects.size());
        for(int i=0;i<objects.size();i++){
            //通过数组下标取到object，使用强转转为JSONObject，之后进行操作
            JSONObject object = (JSONObject) objects.get(i);
            Commodity commodity=new Commodity();

            Integer id=object.getInteger("id");
            String username = object.getString("username");
            String phone = object.getString("contact");
            String price= object.getString("price");
            String description= object.getString("remark");
            String name= object.getString("name");
            String category= object.getString("goodsType");
            byte[] picture= object.getBytes("picture");

            commodity.setId(id);
            commodity.setName(name);
//            commodity.setUsername(username);
            commodity.setContact(phone);
            commodity.setPrice(Double.parseDouble(price));
            commodity.setRemark(description);
//            commodity.setTitle(title);
            commodity.setGoodsType(category);
//            commodity.setPicture(picture);
            list.add(commodity);
        }
        return list;
    }

}