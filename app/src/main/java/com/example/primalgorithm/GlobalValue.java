package com.example.primalgorithm;

import android.app.Application;
import android.util.Log;

public class GlobalValue extends Application {
    static int[][] primTable = new int[7][7];  // 프림테이블
    static int[][] xy = new int[2][7]; //xy[0][]: 점의 x좌표, xy[1][]: 점의 y좌표
    static int[][] startXYend = new int [21][6];   // [0]: 시작점의 x좌표, [1]: 시작점의 y좌표, [2]: 시작점의 노드번호, [3]: 끝점의 x좌표, [4]: 끝점의 y좌표, [5]: 끝점의 노드번호
    static int[] dist = new int[7];

    public void setPrimTable(int i, int j, int k){
        this.primTable[i][j] = k;
    }
    public int getPrimTable(int i, int j){
        return this.primTable[i][j];
    }

    public void setXY(int i, int j, int k){
        this.xy[i][j] = k;
    }
    public int getXY(int i, int j){
        return this.xy[i][j];
    }

    public void setStartXYend(int i, int j, int k){
        this.startXYend[i][j] = k;
    }
    public int getStartXYend(int i, int j){
        return this.startXYend[i][j];
    }


    public void setDist(int i, int k){
        this.dist[i] = k;
    }
    public int getDist(int i){
        return this.dist[i];
    }
}
