package com.example.primalgorithm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class PrimTree extends AppCompatActivity {

    TextView[] textView = new TextView[7];
    int[] from = new int[7];
    int[] S = new int[7];
    int[] dist = new int[7];
    int[] predist = new int[7];
    int[][] primTable = new int[7][7];
    int m = 0;
    int node = 0;
    int start = 0;
    Boolean bool;
    Boolean exeBreak = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prim_tree);

        bool = false;
        final GlobalValue globalValue = (GlobalValue) getApplication();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                primTable[i][j] = globalValue.getPrimTable(i, j);
                if (i != j && primTable[i][j] == 0) primTable[i][j] = 999;
            }
        }

        while(exeBreak){
            Prim(primTable);
            bool = true;
        }

        if(bool) {
            for(int i = 1; i < 7; i++){
                textView[i] = (TextView) findViewById(getResources().getIdentifier("textView"+i,"id","com.example.primalgorithm"));
                textView[i].setText(Integer.toString(from[i]));
                globalValue.setDist(i, from[i]);
            }
        }

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PrimTree.this, FinalView.class);
                startActivity(intent);
            }
        });

    }

    public void Prim(int[][] primTable){
        if(start > 0){
            for (int i = 0; i < 7; i++) {   // start = 시작노드
                predist[i] = dist[i];
            }
        }
        for (int i = 0; i < 7; i++) {   // start = 시작노드
            dist[i] = primTable[start][i];
        }
        if(start > 0){
            distChange(dist, predist);
        }

        m = isMin(dist);                //배열에서 최소 값 찾기
        node = findMinNode(dist, m);       //최소값 가지는 인덱스 찾기
        for(int i = 0; i < 7; i++){
           if(primTable[i][node] == m){
               from[node] = i;
               start = node;
               break;
           }
        }
    }

    public void distChange(int[] dist, int[] predist) {
        int sum = 0;
        for (int i = 0; i < 7; i++) {
            if (dist[i] != 0 && predist[i] < dist[i]) {
                dist[i] = predist[i];
            }
            sum += dist[i];
        }
        if(sum == 0) exeBreak = false;
    }

    public int isMin(int[] dist){
        int min = 0;
        int count = 0;
        for(int i = 0; i < 7; i++){
            if(dist[i] != 0 && dist[i] != 999){
               S[count] = dist[i];
               count++;
           }
        }
        Arrays.sort(S);

        for(int i = 0; i < 7; i++){
            if(S[i] != 0){
                min = S[i];
                break;
            }
        }
       return min;
    }

    public int findMinNode(int[] dist, int min){
        int j = 0;
        for(int i = 0; i < 7; i++){
            if(dist[i] == min){
                j = i;
                dist[i] = 0;
                break;
            }
        }
        return j;
    }

}
