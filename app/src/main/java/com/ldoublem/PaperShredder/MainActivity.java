package com.ldoublem.PaperShredder;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ldoublem.PaperShredderlib.PaperShredderView;

public class MainActivity extends AppCompatActivity {
    PaperShredderView mPaperShredderView, mPaperShredderView2;
    int i = 1;
    int j = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPaperShredderView = (PaperShredderView) findViewById(R.id.ps_delete);
        mPaperShredderView.setShrededType(PaperShredderView.SHREDEDTYPE.Piece);
//        mPaperShredderView.setSherderProgress(false);
//        mPaperShredderView.setTitle("清除数据");
//        mPaperShredderView.setTextColor(Color.BLACK);
//        mPaperShredderView.setPaperColor(Color.BLACK);
//        mPaperShredderView.setBgColor(Color.WHITE);
//        mPaperShredderView.setTextShadow(false);
//        mPaperShredderView.setPaperEnterColor(Color.BLACK);


        mPaperShredderView2 = (PaperShredderView) findViewById(R.id.ps_delete2);
        mPaperShredderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1)
                    mPaperShredderView.startAnim(1000);
                else
                    mPaperShredderView.stopAnim();
                i = i * -1;
            }
        });

        mPaperShredderView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (j == 1)
                    mPaperShredderView2.startAnim(1000);
                else
                    mPaperShredderView2.stopAnim();
                j = j * -1;
            }
        });

    }
}
