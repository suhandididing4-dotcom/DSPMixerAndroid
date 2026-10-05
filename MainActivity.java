package com.equalizertube.dspmixer;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.io.IOException;

public class MainActivity extends Activity {
    LinearLayout root, channels; MediaPlayer player; TextView status; SeekBar master;
    final int BG=Color.rgb(8,11,13), PANEL=Color.rgb(20,25,29), TEXT=Color.rgb(230,235,238), CYAN=Color.rgb(0,216,255);
    int dp(float n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
    TextView label(String s,int size){ TextView t=new TextView(this); t.setText(s); t.setTextColor(TEXT); t.setTextSize(size); t.setGravity(Gravity.CENTER); t.setPadding(dp(4),dp(3),dp(4),dp(3)); return t; }
    Button btn(String s){ Button b=new Button(this); b.setText(s); b.setTextColor(TEXT); b.setTextSize(11); b.setAllCaps(false); b.setBackgroundResource(com.equalizertube.dspmixer.R.drawable.button_bg); b.setPadding(0,0,0,0); return b; }
    @Override public void onCreate(Bundle b){ super.onCreate(b); getWindow().setStatusBarColor(BG); build(); }
    void build(){
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(BG); root.setPadding(dp(8),dp(5),dp(8),dp(5)); setContentView(root);
        LinearLayout top=new LinearLayout(this); top.setGravity(Gravity.CENTER_VERTICAL); root.addView(top,new LinearLayout.LayoutParams(-1,dp(48)));
        TextView title=label("EQUALIZERTUBE  V2",18); title.setTextColor(CYAN); title.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT); top.addView(title,new LinearLayout.LayoutParams(0,-1,1));
        status=label("STOPPED",11); status.setTextColor(Color.LTGRAY); top.addView(status,new LinearLayout.LayoutParams(dp(90),-1));
        Button open=btn("OPEN AUDIO"); open.setOnClickListener(v->openAudio()); top.addView(open,new LinearLayout.LayoutParams(dp(105),dp(42)));
        Button play=btn("▶ PLAY"); play.setOnClickListener(v->togglePlay()); top.addView(play,new LinearLayout.LayoutParams(dp(90),dp(42)));
        Button stop=btn("■ STOP"); stop.setOnClickListener(v->stop()); top.addView(stop,new LinearLayout.LayoutParams(dp(85),dp(42)));
        channels=new LinearLayout(this); channels.setOrientation(LinearLayout.HORIZONTAL); root.addView(channels,new LinearLayout.LayoutParams(-1,0,1));
        addChannel("MUSIC"); addChannel("MIC"); addChannel("AUX"); addChannel("FX"); addMaster();
        TextView foot=label("3-BAND CHANNEL EQ  •  MUTE / SOLO  •  MASTER  •  AUDIO FILE PLAYER",10); foot.setTextColor(Color.GRAY); root.addView(foot,new LinearLayout.LayoutParams(-1,dp(28)));
    }
    void addChannel(String name){
        LinearLayout p=panel(); channels.addView(p,new LinearLayout.LayoutParams(0,-1,1));
        p.addView(label(name,14),new LinearLayout.LayoutParams(-1,dp(30)));
        LinearLayout buttons=new LinearLayout(this); Button mute=btn("MUTE"); Button solo=btn("SOLO"); buttons.addView(mute,new LinearLayout.LayoutParams(0,dp(34),1)); buttons.addView(solo,new LinearLayout.LayoutParams(0,dp(34),1)); p.addView(buttons);
        SeekBar gain=verticalSeek(); p.addView(gain,new LinearLayout.LayoutParams(-1,0,1));
        TextView val=label("0 dB",11); p.addView(val,new LinearLayout.LayoutParams(-1,dp(24))); gain.setProgress(50); gain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){public void onProgressChanged(SeekBar s,int x,boolean f){val.setText((x-50)+" dB");} public void onStartTrackingTouch(SeekBar s){} public void onStopTrackingTouch(SeekBar s){}});
        addEq(p,"LOW"); addEq(p,"MID"); addEq(p,"HIGH");
    }
    void addEq(LinearLayout p,String n){ LinearLayout row=new LinearLayout(this); row.setGravity(Gravity.CENTER_VERTICAL); row.addView(label(n,8),new LinearLayout.LayoutParams(dp(30),dp(30))); SeekBar sb=new SeekBar(this); sb.setProgress(50); row.addView(sb,new LinearLayout.LayoutParams(0,dp(30),1)); p.addView(row); }
    void addMaster(){ LinearLayout p=panel(); channels.addView(p,new LinearLayout.LayoutParams(0,-1,1.1f)); p.addView(label("MASTER",14),new LinearLayout.LayoutParams(-1,dp(30))); master=verticalSeek(); master.setProgress(80); p.addView(master,new LinearLayout.LayoutParams(-1,0,1)); p.addView(label("LEVEL",10),new LinearLayout.LayoutParams(-1,dp(22))); TextView meter=label("▮▮▮▮▮▮▮▮\n▮▮▮▮▮▮▮▮",10); meter.setTextColor(Color.rgb(0,220,150)); p.addView(meter,new LinearLayout.LayoutParams(-1,dp(60))); }
    LinearLayout panel(){ LinearLayout p=new LinearLayout(this); p.setOrientation(LinearLayout.VERTICAL); p.setPadding(dp(4),dp(3),dp(4),dp(3)); p.setBackgroundResource(R.drawable.panel_bg); return p; }
    SeekBar verticalSeek(){ SeekBar s=new SeekBar(this); s.setRotation(-90); s.setMax(100); s.setProgress(50); return s; }
    void openAudio(){ Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT); i.setType("audio/*"); i.addCategory(Intent.CATEGORY_OPENABLE); startActivityForResult(i,9); }
    @Override protected void onActivityResult(int r,int c,Intent d){super.onActivityResult(r,c,d); if(r==9&&c==RESULT_OK&&d!=null){try{if(player!=null)player.release(); player=new MediaPlayer(); player.setDataSource(this,d.getData()); player.prepare(); status.setText("READY");}catch(Exception e){status.setText("ERROR");}}}
    void togglePlay(){ if(player==null){status.setText("OPEN AUDIO");return;} if(player.isPlaying()){player.pause();status.setText("PAUSED");}else{player.start();status.setText("PLAYING");} }
    void stop(){ if(player!=null){player.pause();player.seekTo(0);} status.setText("STOPPED"); }
    @Override protected void onDestroy(){if(player!=null)player.release();super.onDestroy();}
}
