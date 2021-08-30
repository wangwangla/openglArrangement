package com.example.example;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        listView = findViewById(R.id.list_view);
        String[] strArr = new String[] {
                "图形uniform传值",
                "图形attribute传值",
                "图形数组结构",
                "图形绑定属性",
                "图形矩阵变换",
                "图片绘制",
                "图片矩阵变换",
                "图片变换",
                "调整亮度",
                "相機",
                "冷色调",
                "暖色调",
                "底片效果",
                "抖动",
                "放大",
                "腐蚀",
                "高斯1",
                "高斯2",
                "灰",
                "灵魂出鞘",
                "毛刺滤镜",
                "HSV",
                "九宫格",
                "周期放大",
                "Alpha测试"

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                Intent intent = new Intent(SelectActivity.this,MainActivity.class);
                intent.putExtra("itemid",i);
                startActivity(intent);
            }
        });
    }


}
