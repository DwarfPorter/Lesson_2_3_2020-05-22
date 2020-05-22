package ru.geekbrains.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Processor {

    private final ProcessListener processListener;

    public Processor(ProcessListener processListener){
        this.processListener = processListener;
    }

    public void process(final int secs){
        final Handler handler = new Handler(Looper.myLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = Long.toString((calculate(secs)));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                            processListener.onFinish(result);
                    }
                });
            }
        }).start();
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

    public interface ProcessListener{
        void onFinish(String param);
    }
}
