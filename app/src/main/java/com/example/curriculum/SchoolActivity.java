package com.example.curriculum;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SchoolActivity extends AppCompatActivity {
    private WebView webView;
    private EditText urlText;
    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        webView = (WebView) findViewById(R.id.web_view);
        urlText = (EditText) findViewById(R.id.url_text);
        goButton = (Button) findViewById(R.id.go_button);
        //设置JavaScript可用
        webView.getSettings().setJavaScriptEnabled(true);
        //处理JavaScript对话框
        webView.setWebChromeClient(new WebChromeClient());
        //处理各种通知和请求事件,如果不适用该句代码,将使用内置浏览器访问网页
        webView.setWebViewClient(new WebViewClient());
        urlText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (!"".equals(urlText.getText().toString())) {
                        openBrowser();
                    } else {
                        showDialog();
                    }
                }
                return false;
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(urlText.getText().toString())) {
                    openBrowser();
                } else {
                    showDialog();
                }
            }
        });
    }

    private void openBrowser() {
        webView.loadUrl(urlText.getText().toString());
        Toast.makeText(this, "正在加载", Toast.LENGTH_LONG).show();
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("网页浏览器")
                .setMessage("请输入访问的网址")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("WebView", "单机确定按钮");
                    }
                }).show();
    }

}