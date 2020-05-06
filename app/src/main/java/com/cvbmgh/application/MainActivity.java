package com.cvbmgh.application;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cvbmgh.guidelayout.LiveNewWelfareGuideLayout;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout root;
    private LinearLayout llRoomInfo;
    private TextView tvRoomName;
    private TextView tvOnlineCount;
    private TextView tvInputMsg;
    private TextView tvFollow;
    private ImageView ivZPB;
    private TextView tvPraiseNum;

    private LiveNewWelfareGuideLayout guideLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
        llRoomInfo = findViewById(R.id.llRoomInfo);
        tvRoomName = findViewById(R.id.tvRoomName);
        tvOnlineCount = findViewById(R.id.tvOnlineCount);
        tvInputMsg = findViewById(R.id.tvInputMsg);
        tvFollow = findViewById(R.id.tvFollow);
        ivZPB = findViewById(R.id.ivZPB);
        tvPraiseNum = findViewById(R.id.tvPraiseNum);

        tvRoomName.setText("王");
        tvOnlineCount.setText("1人在线");
        tvPraiseNum.setText("9812395");
        ivZPB.setVisibility(View.VISIBLE);

        //ivZPB.setVisibility(View.GONE);

        root.post(new Runnable() {
            @Override
            public void run() {
                guideLayout = new LiveNewWelfareGuideLayout(MainActivity.this, root, llRoomInfo,
                        tvInputMsg, tvFollow, 30, 30);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                root.addView(guideLayout, lp);
                guideLayout.setOnGuideLayoutFinishListener(new LiveNewWelfareGuideLayout.onGuideLayoutFinishListener() {
                    @Override
                    public void onGuideLayoutFinish() {
                        finish();
                    }
                });
            }
        });


    }
}
