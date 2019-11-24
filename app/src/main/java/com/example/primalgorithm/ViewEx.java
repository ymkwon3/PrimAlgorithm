package com.example.primalgorithm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ViewEx extends View {

    Point p1, p2, p3, p4;   //선분 2개를 나타낼 점 4개

    Paint mPaint = new Paint();
    int[][] primTable = new int[7][7];  // 프림테이블
    int x=0, y=0;   //터치시 입력되는 x, y 좌표
    int[][] xy = new int[2][7];
    int c = 0;  //카운트(1)
    String result = "RESULT";    //버튼 이름
    Button button;
    MainActivity mainActivity;

    public ViewEx(Context context, AttributeSet attr){
        super(context, attr);
        mPaint.setColor(Color.BLACK);   //그려질 색상 설정
        mPaint.setStrokeWidth(30f);     //그려질 두께 설정
        mainActivity = (MainActivity)context;
    }

    @Override
    public void onDraw(Canvas canvas){
        final Canvas mCanvas = canvas;  // 캔버스 생성

        canvas.drawColor(Color.WHITE);
        button = new Button(mainActivity);

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);
        button.setText(result);
        ((ConstraintLayout)this.getParent()).addView(button);

        mCanvas.drawColor(Color.WHITE);     //캔버스 색상설정

            for (int i = 0; i < 7; i++) {       //터치한 곳에 점 찍기
                mCanvas.drawRect(xy[0][i]-50, xy[1][i]+50, xy[0][i]+50, xy[1][i]-50, mPaint);
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //터치시 점의 좌표 입력과 동시에 점배열, 선분배열에 값 저장
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = (int) event.getX();
            y = (int) event.getY();
            if (c < 7) {
                xy[0][c] = x;
                xy[1][c] = y;
            }
            c++;
        }
        invalidate();
        return true;
    }
}