package com.ssg.testt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

import com.ssg.testt.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private GestureDetector gd;
    private WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setActivity(this);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        binding.scroll.setDencity(metrics.density);
        binding.scroll.setHeader(binding.headConst);
        binding.scroll.setItem(binding.benner);
        binding.scroll.setItemline(binding.shopName);
        binding.scroll.setTopConst(binding.topConst);
        binding.scroll.setHeadertitle(binding.headTitle);
        binding.scroll.setHeadDetail(binding.headDetail);
        binding.scroll.setHeadReview(binding.headReview);
        binding.scroll.setHeadQna(binding.headQna);
        binding.scroll.setHeadAlpha(binding.headAlpha);
        binding.scroll.setChoice(binding.choiceConst);
        binding.scroll.setHeadConst(binding.headConst);
        binding.scroll.setItemDetailConst(binding.itemDetailConst);
        binding.scroll.setQnaConst(binding.qnaConst);
        binding.scroll.setLogoConst(binding.logoConst);


        binding.itemInfo.itemInfoWeb.setWebViewClient(new WebViewClient());
        webSettings=binding.itemInfo.itemInfoWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.itemInfo.itemInfoWeb.loadUrl("https://www.ssg.com/item/itemView.ssg?itemId=1000020816172&siteNo=6004&salestrNo=6005&tlidSrchWd=%EB%9E%91%EB%B0%A9&srchPgNo=1&src_area=ssglist");





    }
}