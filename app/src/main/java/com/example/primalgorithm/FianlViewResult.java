package com.example.primalgorithm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class FianlViewResult extends View {
    GlobalValue globalValue = new GlobalValue();
    Paint mPaint = new Paint();
    String next = "Next";
    int[][] primTable = new int[7][7];  // 프림테이블
    int x=0, y=0;   //터치시 입력되는 x, y 좌표
    int[][] xy = new int[2][7]; //xy[0][]: 점의 x좌표, xy[1][]: 점의 y좌표
    int[][] startXYend = new int [21][6];   // [0]: 시작점의 x좌표, [1]: 시작점의 y좌표, [2]: 시작점의 노드번호, [3]: 끝점의 x좌표, [4]: 끝점의 y좌표, [5]: 끝점의 노드번호
    TextView[] nodeText = new TextView[7];
    TextView[] lineText = new TextView[21];
    FinalView finalView;

    public FianlViewResult(Context context, AttributeSet attr){
        super(context, attr);
        finalView = (FinalView) context;
    }

    private void makeNodeText(int nodeTextCount){
        nodeText[nodeTextCount] = new TextView(finalView);
        ConstraintLayout.LayoutParams lp2 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nodeText[nodeTextCount].setLayoutParams(lp2);
        nodeText[nodeTextCount].setText(Integer.toString(nodeTextCount));
        nodeText[nodeTextCount].setX(globalValue.getXY(0, nodeTextCount)-10);
        nodeText[nodeTextCount].setY(globalValue.getXY(1, nodeTextCount)-30);
        ((ConstraintLayout)this.getParent()).addView(nodeText[nodeTextCount]);
        if(nodeTextCount < 6) nodeTextCount++;
    }

    private void makeLineText(int lineTextCount){
        lineText[lineTextCount] = new TextView(finalView);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lineText[lineTextCount].setLayoutParams(lp);
        lineText[lineTextCount].setText(Integer.toString(globalValue.getPrimTable(globalValue.getStartXYend(lineTextCount, 2), globalValue.getStartXYend(lineTextCount, 5))));
        lineText[lineTextCount].setX((globalValue.getStartXYend(lineTextCount, 0)+globalValue.getStartXYend(lineTextCount, 3))/2-10);
        lineText[lineTextCount].setY((globalValue.getStartXYend(lineTextCount, 1)+globalValue.getStartXYend(lineTextCount, 4))/2-30);
        ((ConstraintLayout)this.getParent()).addView(lineText[lineTextCount]);
        if(lineTextCount < 21) lineTextCount++;
    }


    @Override
    public void onDraw(Canvas canvas){

        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.GREEN);   //그려질 색상 설정
        mPaint.setStrokeWidth(10f);     //그려질 두께 설정
        canvas.drawColor(Color.WHITE);     //캔버스 색상설정
        for (int i = 0; i < 7; i++) {       //터치한 곳에 점 찍기
            canvas.drawRect(globalValue.getXY(0,i)-40, globalValue.getXY(1,i)+40, globalValue.getXY(0,i)+40, globalValue.getXY(1,i)-40, mPaint);
            makeNodeText(i);
        }

        for(int i = 0; i < 7; i++){
            Log.d("testtesttest!", "dist " + globalValue.getDist(i));
        }

        for(int j = 1; j < 21; j++){    // 선 긋기
            if(globalValue.getStartXYend(j, 0) != 0 && globalValue.getStartXYend(j, 1) != 0 && globalValue.getStartXYend(j, 3) != 0 && globalValue.getStartXYend(j, 4) != 0){
                Log.d("testtesttest!", "ㅅㅂ1 " + globalValue.getStartXYend(j, 2));
                Log.d("testtesttest!", "ㅅㅂ2 " + globalValue.getStartXYend(j, 5));
                for(int i = 1; i < 7; i++){
                    if((globalValue.getStartXYend(j, 2) == i && globalValue.getStartXYend(j, 5) == globalValue.getDist(i)) | (globalValue.getStartXYend(j, 2) == globalValue.getDist(i) && globalValue.getStartXYend(j, 5) == i)){
                        canvas.drawLine(globalValue.getStartXYend(j, 0), globalValue.getStartXYend(j, 1), globalValue.getStartXYend(j, 3), globalValue.getStartXYend(j, 4), mPaint);
                        Log.d("testtesttest!", "ㅅㅂㅅㅂ " + i);
                        Log.d("testtesttest!", "ㅅㅂㅅㅂ " + globalValue.getDist(i));
                        makeLineText(j);
                        break;
                    }
                }
            }
        }
    }
}
