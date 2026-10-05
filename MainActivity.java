package com.suhandididing4.dspmixer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private MediaPlayer player;
    private TextView status;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        buildUi();
    }

    private int dp(float v) {
        return (int)(v * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView label(String text, int size) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextColor(Color.WHITE);
        t.setTextSize(size);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(4), dp(4), dp(4), dp(4));
        return t;
    }

    private void buildUi() {
        LinearLayout base = new LinearLayout(this);
        base.setOrientation(LinearLayout.VERTICAL);
        base.setBackgroundColor(Color.rgb(11,14,18));

        status = label("EqualizerTube V2  •  READY", 14);
        status.setGravity(Gravity.CENTER_VERTICAL);
        status.setPadding(dp(12),0,dp(12),0);
        base.addView(status, new LinearLayout.LayoutParams(-1, dp(42)));

        LinearLayout controls = new LinearLayout(this);
        controls.setGravity(Gravity.CENTER_VERTICAL);

        Button open = new Button(this);
        open.setText("OPEN AUDIO");
        open.setOnClickListener(v -> pickAudio());
        controls.addView(open, new LinearLayout.LayoutParams(dp(125), dp(48)));

        Button play = new Button(this);
        play.setText("PLAY");
        play.setOnClickListener(v -> { if (player != null) player.start(); });
        controls.addView(play, new LinearLayout.LayoutParams(dp(85), dp(48)));

        Button pause = new Button(this);
        pause.setText("PAUSE");
        pause.setOnClickListener(v -> { if (player != null && player.isPlaying()) player.pause(); });
        controls.addView(pause, new LinearLayout.LayoutParams(dp(95), dp(48)));

        Button stop = new Button(this);
        stop.setText("STOP");
        stop.setOnClickListener(v -> { if (player != null) { player.pause(); player.seekTo(0); } });
        controls.addView(stop, new LinearLayout.LayoutParams(dp(85), dp(48)));

        base.addView(controls, new LinearLayout.LayoutParams(-1, dp(54)));

        HorizontalScrollView scroll = new HorizontalScrollView(this);
        LinearLayout mixer = new LinearLayout(this);
        mixer.setPadding(dp(8), dp(4), dp(8), dp(12));

        String[] names = {"MUSIC","MIC","AUX","FX","USB","VOCAL","BUS","MASTER"};
        for (String name : names) mixer.addView(channel(name));

        scroll.addView(mixer);
        base.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        setContentView(base);
    }

    private View channel(String name) {
        LinearLayout ch = new LinearLayout(this);
        ch.setOrientation(LinearLayout.VERTICAL);
        ch.setGravity(Gravity.CENTER_HORIZONTAL);
        ch.setPadding(dp(8), dp(6), dp(8), dp(6));
        ch.setBackgroundColor(Color.rgb(25,29,35));

        TextView title = label(name, 14);
        ch.addView(title, new LinearLayout.LayoutParams(dp(105), dp(34)));

        TextView meter = label("▮\n▮\n▮\n▮\n▮", 15);
        meter.setTextColor(Color.rgb(80,210,120));
        ch.addView(meter, new LinearLayout.LayoutParams(dp(105), 0, 1));

        SeekBar fader = new SeekBar(this);
        fader.setMax(100);
        fader.setProgress(72);
        fader.setRotation(-90);
        ch.addView(fader, new LinearLayout.LayoutParams(dp(105), dp(145)));

        TextView gain = label("GAIN  0 dB", 11);
        ch.addView(gain, new LinearLayout.LayoutParams(dp(105), dp(30)));

        LinearLayout eq = new LinearLayout(this);
        String[] bands = {"LOW","MID","HIGH"};
        for (String band : bands) {
            Button b = new Button(this);
            b.setText(band);
            b.setTextSize(9);
            b.setOnClickListener(v -> status.setText(name + "  •  " + band + " EQ"));
            eq.addView(b, new LinearLayout.LayoutParams(0, dp(42), 1));
        }
        ch.addView(eq, new LinearLayout.LayoutParams(dp(105), dp(48)));

        LinearLayout ms = new LinearLayout(this);
        Button mute = new Button(this);
        mute.setText("MUTE");
        mute.setTextSize(9);
        mute.setOnClickListener(v -> v.setSelected(!v.isSelected()));
        ms.addView(mute, new LinearLayout.LayoutParams(0, dp(42), 1));
        Button solo = new Button(this);
        solo.setText("SOLO");
        solo.setTextSize(9);
        ms.addView(solo, new LinearLayout.LayoutParams(0, dp(42), 1));
        ch.addView(ms, new LinearLayout.LayoutParams(dp(105), dp(48)));

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(dp(121), dp(330));
        p.setMargins(dp(4),0,dp(4),0);
        ch.setLayoutParams(p);
        return ch;
    }

    private void pickAudio() {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType("audio/*");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(i, 100);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                if (player != null) player.release();
                player = MediaPlayer.create(this, uri);
                status.setText("Loaded  •  " + uri.getLastPathSegment());
            } catch (Exception e) {
                status.setText("Audio error: " + e.getMessage());
            }
        }
    }

    @Override protected void onDestroy() {
        if (player != null) player.release();
        super.onDestroy();
    }
}
