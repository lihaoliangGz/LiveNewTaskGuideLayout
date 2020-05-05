package com.cvbmgh.application;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cvbmgh.guidelayout.LiveNewWelfareGuideLayout;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout root;
    private TextView tvRoomName;
    private TextView tvOnlineCount;
    private ImageView ivZPB;
    private TextView tvPraiseNum;

    private LiveNewWelfareGuideLayout guideLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
        tvRoomName = findViewById(R.id.tvRoomName);
        tvOnlineCount = findViewById(R.id.tvOnlineCount);
        ivZPB = findViewById(R.id.ivZPB);
        tvPraiseNum = findViewById(R.id.tvPraiseNum);

        tvRoomName.setText("王者荣誉");
        tvOnlineCount.setText("123人在线");
        tvPraiseNum.setText("9812395");
        ivZPB.setVisibility(View.VISIBLE);

        guideLayout = new LiveNewWelfareGuideLayout(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        root.addView(guideLayout, lp);

        guideLayout.setBackgroundColor(0xb2000000);

    }
}
