package com.example.curriculum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button school_button;
    private Button table_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        school_button=(Button) findViewById(R.id.school_button);
        table_button=(Button)findViewById(R.id.table_button);
        //点击学校首页进入自制浏览器页面
        school_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SchoolActivity.class);
                startActivity(intent);
            }
        });
        //长按学校首页进入系统浏览器
        school_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openWebPage("http://www.hbut.edu.cn");
                return false;
            }
        });
        //点击进入课表页面
        table_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CourseActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openWebPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}