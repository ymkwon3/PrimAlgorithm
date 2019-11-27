package com.example.primalgorithm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

public class ViewEx extends View {

    Paint mPaint = new Paint();
    int[][] primTable = new int[7][7];  // 프림테이블
    int x=0, y=0;   //터치시 입력되는 x, y 좌표
    int[] nodeID = new int[7];
    int[][] xy = new int[2][7]; //xy[0][]: 점의 x좌표, xy[1][]: 점의 y좌표
    int[][] startXYend = new int [22][4];   // [0]: 시작점의 x좌표, [1]: 시작점의 y좌표, [2]: 끝점의 x좌표, [3]: 끝점의 y좌표
    int c = 0;  //카운트
    int lineCount = 0;  // 선분개수
    String result = "RESULT";    //버튼 이름
    Boolean nodeClick = false;
    Button button;
    TextView[] nodeText = new TextView[7];
    MainActivity mainActivity;

    public ViewEx(Context context, AttributeSet attr){
        super(context, attr);
        mainActivity = (MainActivity)context;
    }

    private void ChangeStartPoint(int[][] xy, int i) {
        if(lineCount < 21){
            Log.d("123", "Log.= " + lineCount);
            lineCount++;
        }
        startXYend[lineCount][0] = xy[0][i];
        startXYend[lineCount][1] = xy[1][i];
    }

    private void ChangeEndPoint(int[][] xy, int i) {
        startXYend[lineCount][2] = xy[0][i];
        startXYend[lineCount][3] = xy[1][i];
    }

    private void makeNodeText(int nodeTextCount){
        nodeText[nodeTextCount] = new TextView(mainActivity);
        ConstraintLayout.LayoutParams lp2 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nodeText[nodeTextCount].setLayoutParams(lp2);
        nodeText[nodeTextCount].setText(Integer.toString(nodeTextCount+1));
        nodeText[nodeTextCount].setX(xy[0][nodeTextCount]-10);
        nodeText[nodeTextCount].setY(xy[1][nodeTextCount]-30);
        ((ConstraintLayout)this.getParent()).addView(nodeText[nodeTextCount]);
        if(nodeTextCount < 6) nodeTextCount++;
    }

    @Override
    public void onDraw(Canvas canvas){

        canvas.drawColor(Color.WHITE);
        button = new Button(mainActivity);

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);
        button.setText(result);
        ((ConstraintLayout)this.getParent()).addView(button);

        mPaint.setColor(Color.YELLOW);   //그려질 색상 설정
        mPaint.setStrokeWidth(10f);     //그려질 두께 설정
        canvas.drawColor(Color.WHITE);     //캔버스 색상설정
        for (int i = 0; i < 7; i++) {       //터치한 곳에 점 찍기
            if(xy[0][i] != 0 && xy[1][i] != 0){
                canvas.drawRect(xy[0][i]-30, xy[1][i]+30, xy[0][i]+30, xy[1][i]-30, mPaint);
                makeNodeText(i);
            }
        }

        for(int j = 1; j < 22; j++){
            if(startXYend[j][0] != 0 && startXYend[j][1] != 0 && startXYend[j][2] != 0 && startXYend[j][3] != 0){
                canvas.drawLine(startXYend[j][0], startXYend[j][1], startXYend[j][2], startXYend[j][3], mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //터치시 점의 좌표 입력과 동시에 점배열, 선분배열에 값 저장

        super.onTouchEvent(event);
        x = (int) event.getX();
        y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                for(int i = 0; i < 7; i++) {
                    if (xy[0][i] - 30 <= x && xy[0][i] + 30 >= x && xy[1][i] - 30 <= y && xy[1][i] + 30 >= y) {
                        nodeClick = true;
                        ChangeStartPoint(xy, i);
                        break;
                    }
                }
                if(!nodeClick && c <7){
                    xy[0][c] = x;
                    xy[1][c] = y;
                    c++;
                    invalidate();
                }
                nodeClick = false;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                    for(int i = 0; i < 7; i++) {
                        if (xy[0][i] - 30 <= x && xy[0][i] + 30 >= x && xy[1][i] - 30 <= y && xy[1][i] + 30 >= y) {
                            ChangeEndPoint(xy, i);
                            invalidate();
                            break;
                    }
                }
                break;
        }
        return true;
    }
}