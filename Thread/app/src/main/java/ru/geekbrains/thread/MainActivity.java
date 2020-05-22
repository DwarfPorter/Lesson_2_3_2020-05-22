package ru.geekbrains.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private int counterThread = 0;
    private TextView textView;
    private TextView textIndicator;
    private EditText seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initUIButton();
        initThreadButton();
    }

    private void initView() {
        textView = findViewById(R.id.textView);
        textIndicator = findViewById(R.id.textIndicator);
        seconds = findViewById(R.id.editText);
    }

    private void initUIButton() {
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sec = Integer.parseInt(seconds.getText().toString());
                String result = Long.toString(calculate(sec));
                textIndicator.setText("В главном потоке");
                textView.setText(result);
            }
        });
    }

    private void initThreadButton(){
        Button button = findViewById(R.id.calcThreadBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterThread++;
                final int numberThread = counterThread;
                final int secs = Integer.parseInt(seconds.getText().toString());
                textIndicator.setText(String.format("%sСтартуем поток %d сек %d\n",
                        textIndicator.getText().toString(), numberThread, secs));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int sec = Integer.parseInt(seconds.getText().toString());
                        final String result = Long.toString(calculate(sec));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textIndicator.setText(String.format("%sИз потока %d\n", textIndicator.getText().toString(),
                                        numberThread));
                                textView.setText(result);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private long calculate(int seconds) {
        Date date = new Date();
        long diffInSec;
        do{
            Date currentDate = new Date();
            long diffInMs = currentDate.getTime() - date.getTime();
            diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
        }while(diffInSec < seconds);
        return diffInSec;
    }
}
