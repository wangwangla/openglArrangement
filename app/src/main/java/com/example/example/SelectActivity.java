package com.example.example;

import android.content.Intent;
import android.os.Bundle;

import com.example.example.learn.egl.EGLActivity;
import com.example.example.learn.egl.EGLRender;
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
                "图形uniform传值（0）",
                "图形attribute传值（1）",
                "图形数组结构（2）",
                "图形绑定属性（3）",
                "图形矩阵变换（4）",
                "图片绘制（5）",
                "图片矩阵变换（6）",
                "图片变换（7）",
                "调整亮度（8）",
                "相機（9）",
                "冷色调（10）",
                "暖色调（11）",
                "底片效果（12）",
                "抖动（13）",
                "放大（14）",
                "腐蚀（15）",
                "高斯1（16）",
                "高斯2（17）",
                "灰（18）",
                "灵魂出鞘（19）",
                "毛刺滤镜（20）",
                "HSV（21）",
                "九宫格（22）",
                "周期放大（23）",
                "Alpha测试（24）",
                "Blend（25）",
                "深度测试（26）",
                "模板测试（27）",
                "EGL使用（28）",
                "回讀數據（29）"

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                if (i == 28){
                    Intent intent = new Intent(SelectActivity.this, EGLActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                    intent.putExtra("itemid", i);
                    startActivity(intent);
                }
            }
        });
    }


}
