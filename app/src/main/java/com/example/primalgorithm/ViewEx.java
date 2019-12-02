package com.example.primalgorithm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;



public class ViewEx extends View {

    GlobalValue globalValue = new GlobalValue();
    Paint mPaint = new Paint();
    AlertDialog.Builder ad;
    int[][] primTable = new int[7][7];  // 프림테이블
    int x=0, y=0;   //터치시 입력되는 x, y 좌표
    int[][] xy = new int[2][7]; //xy[0][]: 점의 x좌표, xy[1][]: 점의 y좌표
    int[][] startXYend = new int [22][6];   // [0]: 시작점의 x좌표, [1]: 시작점의 y좌표, [2]: 시작점의 노드번호, [3]: 끝점의 x좌표, [4]: 끝점의 y좌표, [5]: 끝점의 노드번호
    int c = 0;  //카운트
    int lineCount = 0;  // 선분개수
    String result = "RESULT";    //버튼 이름
    String value;
    int valueInt = 0;
    Boolean nodeClick = false;
    Boolean lineClick = false;
    Button button;
    TextView[] nodeText = new TextView[7];
    TextView[] lineText = new TextView[22];
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
        globalValue.setStartXYend(lineCount, 0, xy[0][i]);
        startXYend[lineCount][1] = xy[1][i];
        globalValue.setStartXYend(lineCount, 1, xy[1][i]);
        startXYend[lineCount][2] = i;
        globalValue.setStartXYend(lineCount, 2, i);
    }

    private void ChangeEndPoint(int[][] xy, int i) {

        startXYend[lineCount][3] = xy[0][i];
        globalValue.setStartXYend(lineCount, 3, xy[0][i]);
        startXYend[lineCount][4] = xy[1][i];
        globalValue.setStartXYend(lineCount, 4, xy[1][i]);
        startXYend[lineCount][5] = i;
        globalValue.setStartXYend(lineCount, 5, i);

        ad = new AlertDialog.Builder(mainActivity);
        ad.setTitle("가중치 입력");
        final EditText et = new EditText(mainActivity);
        if(et.getParent() != null) ((ViewGroup)et.getParent()).removeView(et);
        ad.setView(et);
        ad.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                value = et.getText().toString();
                valueInt = Integer.parseInt(value);
                primTable[startXYend[lineCount][2]][startXYend[lineCount][5]] = valueInt;
                globalValue.setPrimTable(startXYend[lineCount][2], startXYend[lineCount][5], valueInt);
                Log.d("ㄷㅊㅅㅇ", "dd12121 " + globalValue.getPrimTable(startXYend[lineCount][2], startXYend[lineCount][5]));
                primTable[startXYend[lineCount][5]][startXYend[lineCount][2]] = valueInt;
                globalValue.setPrimTable(startXYend[lineCount][5], startXYend[lineCount][2], valueInt);
                makeLineText(lineCount);
                dialogInterface.dismiss();
            }
        });
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad.show();
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

    private void makeLineText(int lineTextCount){
        lineText[lineTextCount] = new TextView(mainActivity);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lineText[lineTextCount].setLayoutParams(lp);
        lineText[lineTextCount].setText(Integer.toString(primTable[startXYend[lineTextCount][2]][startXYend[lineTextCount][5]]));
        lineText[lineTextCount].setX((startXYend[lineTextCount][0]+startXYend[lineTextCount][3])/2-10);
        lineText[lineTextCount].setY((startXYend[lineTextCount][1]+startXYend[lineTextCount][4])/2-30);
        ((ConstraintLayout)this.getParent()).addView(lineText[lineTextCount]);
        if(lineTextCount < 21) lineTextCount++;
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
                canvas.drawRect(xy[0][i]-40, xy[1][i]+40, xy[0][i]+40, xy[1][i]-40, mPaint);
                makeNodeText(i);
            }
        }

        for(int j = 1; j < 22; j++){    // 선 긋기
            if(startXYend[j][0] != 0 && startXYend[j][1] != 0 && startXYend[j][3] != 0 && startXYend[j][4] != 0){
                canvas.drawLine(startXYend[j][0], startXYend[j][1], startXYend[j][3], startXYend[j][4], mPaint);
            }
        }

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mainActivity, PrimTable.class);
                mainActivity.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //터치시 점의 좌표 입력과 동시에 점배열, 선분배열에 값 저장

        super.onTouchEvent(event);
        x = (int) event.getX();
        y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                for(int i = 0; i < 7; i++) {
                    if (xy[0][i] - 40 <= x && xy[0][i] + 40 >= x && xy[1][i] - 40 <= y && xy[1][i] + 40 >= y) {
                        nodeClick = true;
                        lineClick = true;
                        ChangeStartPoint(xy, i);
                        break;
                    }
                }
                if(!nodeClick && c <7){
                    xy[0][c] = x;
                    globalValue.setXY(0, c, x);
                    xy[1][c] = y;
                    globalValue.setXY(1, c, y);
                    c++;
                    lineClick = false;
                    invalidate();
                }
                nodeClick = false;
                return true;
            case MotionEvent.ACTION_MOVE: return true;
            case MotionEvent.ACTION_UP:
                if(lineClick){
                    for(int i = 0; i < 7; i++) {
                        if (xy[0][i] - 40 <= x && xy[0][i] + 40 >= x && xy[1][i] - 40 <= y && xy[1][i] + 40 >= y) {
                            ChangeEndPoint(xy, i);
                            invalidate();
                            break;
                        }
                    }
                    break;
                }
                return true;
        }
        return false;
    }
}