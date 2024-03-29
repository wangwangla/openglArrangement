package com.example.android;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.example.R;

public class HightSelectActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hight_select);
        listView = findViewById(R.id.list_view);
        String[] strArr = new String[]{
                "测试环境(0)",
                "无光（1）",
                "环境光（2）",
                "漫反射（3）",
                "贴图（4）",
                "混合mix（5）",
                "不同材质（6）",
                "3d贴图带光（7）",
                "蓝线挑战纵向（8）",
                "3D贴图（9）",
                "录像(10)",
                "打开手机相册（11）",
                "转场1:混合（12）",
                "转场2:迷糊（13）",
                "转场3:推镜（14）",
                "转场4:拉镜（15）",
                "转场5:漩涡（16）",
                "转场6:左移（17）",
                "转场7:右移（18）",
                "转场8:上移（19）",
                "转场9:下移（20）",
                "转场10:左上移（21）",
                "转场11:右上移（22）",
                "转场12:左下移（23）",
                "转场13:右下移（24）",
                "转场14:翻页（25）",
                "转场15:分割一（26）",
                "转场16:分割二（27）",
                "转场17:分割三（28）",
                "转场18:分割四（29）",
                "转场19:水平翻转（30）",
                "转场20:垂直翻转（31）",
                "转场21:普通（32）",
                "转场2:移动向上（33）",
                "转场3:移动向下（34）",
                "转场4:移动向左（35）",
                "转场5:移动向右（36）",
                "转场6:移动向左上（37）",
                "转场7:移动向右上（38）",
                "转场8:移动向左下（39）",
                "转场9:移动向右下（40）",
                "转场10:抹掉向左（41）",
                "转场11:抹掉向右（42）",
                "转场12:抹掉向上（43）",
                "转场13:抹掉向下（44）",
                "转场14:抹掉左上（45）",
                "转场15:抹掉右上（46）",
                "转场16:抹掉中心（47）",
                "转场17:抹掉圆形（48）",

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Class activity = HightActivity.class;
                Intent intent = new Intent(HightSelectActivity.this, activity);
                intent.putExtra("itemid", i);
                startActivity(intent);
            }
        });
    }
}