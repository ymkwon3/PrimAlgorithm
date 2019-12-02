package com.example.primalgorithm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PrimTable extends AppCompatActivity {
    private TextView[][] textView = new TextView[7][7];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prim_table);
        final GlobalValue globalValue = (GlobalValue) getApplication();

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                textView[i][j] = (TextView) findViewById(getResources().getIdentifier("textView"+i+j,"id","com.example.primalgorithm"));
                textView[i][j].setText(Integer.toString(globalValue.getPrimTable(i, j)));
                Log.d("ㄷㅊㅅㅇ", "dd " + globalValue.getPrimTable(i, j));
            }
        }
    }

}
