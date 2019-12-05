package com.example.primalgorithm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PrimTable.this, PrimTableResult.class);
                startActivity(intent);
            }
        });
    }

}
