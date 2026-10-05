package com.suhandigital.equalizertube;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.Locale;

public class MainActivity extends Activity {
    LinearLayout root, mixer;
    TextView status;

    int bg = Color.rgb(14, 18, 23);
    int panel = Color.rgb(25, 31, 38);
    int panel2 = Color.rgb(32, 39, 48);
    int text = Color.rgb(235, 240, 245);
    int muted = Color.rgb(160, 171, 183);
    int accent = Color.rgb(54, 169, 255);

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        buildUi();
    }

    TextView tv(String s, float sp, int c) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(sp);
        t.setTextColor(c);
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setPadding(12, 8, 12, 8);
        return t;
    }

    GradientDrawable bg(int color, float radius) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(radius);
        return g;
    }

    LinearLayout box(int color, int pad) {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(pad, pad, pad, pad);
        l.setBackground(bg(color, 18));
        return l;
    }

    void addWeight(View v, float w) {
        root.addView(v, new LinearLayout.LayoutParams(0, -2, w));
    }

    void buildUi() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bg);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(10, 10, 10, 18);
        root.setBackgroundColor(bg);
        scroll.addView(root);
        setContentView(scroll);

        LinearLayout head = box(panel, 10);
        TextView title = tv("EqualizerTube V2", 22, text);
        title.setTypeface(null, 1);
        head.addView(title);
        status = tv("● Sistem Aktif   •   DSP Ready", 13, Color.rgb(100, 220, 130));
        head.addView(status);
        root.addView(head);

        Space sp = new Space(this);
        root.addView(sp, new LinearLayout.LayoutParams(1, 8));

        LinearLayout transport = box(panel, 8);
        TextView now = tv("♪  No Copyright Music - Feel Good", 16, text);
        transport.addView(now);
        LinearLayout buttons = new LinearLayout(this);
        buttons.setGravity(Gravity.CENTER);
        String[] bs = {"⏮", "▶", "⏸", "⏹", "⏭"};
        for (String x : bs) {
            Button bt = new Button(this);
            bt.setText(x);
            bt.setTextColor(text);
            bt.setTextSize(18);
            bt.setBackground(bg(panel2, 14));
            buttons.addView(bt, new LinearLayout.LayoutParams(0, 52, 1));
            if (x.equals("▶")) bt.setOnClickListener(v -> status.setText("● Memutar • DSP Aktif"));
            if (x.equals("⏹")) bt.setOnClickListener(v -> status.setText("● Berhenti • DSP Ready"));
        }
        transport.addView(buttons);
        root.addView(transport);

        TextView mh = tv("CHANNEL MIXER", 14, muted);
        mh.setPadding(4, 16, 4, 8);
        root.addView(mh);

        mixer = new LinearLayout(this);
        mixer.setOrientation(LinearLayout.VERTICAL);
        String[] channels = {"Music", "Mic 1", "Mic 2", "System", "Game", "Browser", "AUX 1", "AUX 2", "Master"};
        for (String ch : channels) addChannel(ch);
        root.addView(mixer);

        TextView eh = tv("EFFECTS (DSP)", 14, muted);
        eh.setPadding(4, 16, 4, 8);
        root.addView(eh);

        LinearLayout effects = box(panel, 10);
        TextView tabs = tv("EQ     Compressor     Gate     Limiter", 14, accent);
        effects.addView(tabs);
        TextView eq = tv("Parametric EQ\nLow     +2.0 dB     •     Mid     0.0 dB     •     High     +1.5 dB", 15, text);
        effects.addView(eq);
        SeekBar low = new SeekBar(this); low.setProgress(62);
        SeekBar mid = new SeekBar(this); mid.setProgress(50);
        SeekBar high = new SeekBar(this); high.setProgress(58);
        effects.addView(tv("Low", 12, muted)); effects.addView(low);
        effects.addView(tv("Mid", 12, muted)); effects.addView(mid);
        effects.addView(tv("High", 12, muted)); effects.addView(high);
        root.addView(effects);

        TextView rh = tv("OUTPUT / OBS", 14, muted);
        rh.setPadding(4, 16, 4, 8);
        root.addView(rh);

        LinearLayout out = box(panel, 10);
        out.addView(tv("Virtual Output (Untuk OBS)", 16, text));
        Switch sw = new Switch(this);
        sw.setText("Aktif");
        sw.setTextColor(text);
        sw.setChecked(true);
        out.addView(sw);
        out.addView(tv("Device: Android Audio Output", 13, muted));
        out.addView(tv("Catatan: versi ini adalah fondasi DSP/equalizer Android yang buildable.", 12, muted));
        root.addView(out);
    }

    void addChannel(String name) {
        LinearLayout c = box(panel, 8);
        TextView h = tv(name, 16, name.equals("Master") ? Color.rgb(90, 220, 170) : text);
        h.setTypeface(null, 1);
        c.addView(h);

        SeekBar gain = new SeekBar(this);
        gain.setProgress(50);
        c.addView(tv("Gain   0.0 dB", 12, muted));
        c.addView(gain);

        SeekBar fader = new SeekBar(this);
        fader.setProgress(name.equals("Master") ? 80 : 65);
        c.addView(tv("Level", 12, muted));
        c.addView(fader);

        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        Button mute = new Button(this); mute.setText("M");
        Button solo = new Button(this); solo.setText("S");
        TextView val = tv(name.equals("Master") ? "-1.0 dB" : "-6.0 dB", 13, accent);
        row.addView(mute, new LinearLayout.LayoutParams(0, 48, 1));
        row.addView(solo, new LinearLayout.LayoutParams(0, 48, 1));
        row.addView(val, new LinearLayout.LayoutParams(0, 48, 2));
        c.addView(row);
        mute.setOnClickListener(v -> {
            boolean on = !mute.isSelected();
            mute.setSelected(on);
            mute.setText(on ? "M ON" : "M");
            val.setText(on ? "-∞ dB" : (name.equals("Master") ? "-1.0 dB" : "-6.0 dB"));
        });
        mixer.addView(c, new LinearLayout.LayoutParams(-1, -2));
        Space gap = new Space(this);
        mixer.addView(gap, new LinearLayout.LayoutParams(1, 6));
    }
}
