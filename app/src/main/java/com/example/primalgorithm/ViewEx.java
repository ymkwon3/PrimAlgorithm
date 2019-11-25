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
    int[][] xy = new int[2][7]; //xy[0][]: 점의 x좌표, xy[1][]: 점의 y좌표
    int[] startXY = new int[2];
    int[] endXY = new int[2];
    int c = 0;  //카운트
    String result = "RESULT";    //버튼 이름
    Button button;
    MainActivity mainActivity;

    public ViewEx(Context context, AttributeSet attr){
        super(context, attr);
        mainActivity = (MainActivity)context;
    }

    private void ChangeStartPoint(MotionEvent event) {
        x = (int)event.getX();
        y = (int)event.getY();


    }

    private void ChangeEndPoint(MotionEvent event) {
        x = (int)event.getX();
        y = (int)event.getY();
        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){

        canvas.drawColor(Color.WHITE);
        button = new Button(mainActivity);

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);
        button.setText(result);
        ((ConstraintLayout)this.getParent()).addView(button);

        mPaint.setColor(Color.BLACK);   //그려질 색상 설정
        mPaint.setStrokeWidth(30f);     //그려질 두께 설정
        canvas.drawColor(Color.WHITE);     //캔버스 색상설정
        for (int i = 0; i < 7; i++) {       //터치한 곳에 점 찍기
            if(xy[0][i] != 0 && xy[1][i] != 0){
                canvas.drawRect(xy[0][i]-20, xy[1][i]+20, xy[0][i]+20, xy[1][i]-20, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //터치시 점의 좌표 입력과 동시에 점배열, 선분배열에 값 저장
        super.onTouchEvent(event);

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                if(c<7){
                    xy[0][c] = (int) event.getX();
                    xy[1][c] = (int) event.getY();
                    c++;
                    invalidate();
                }else ChangeStartPoint(event); break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP: ChangeEndPoint(event); break;
        }
        return true;
    }
}