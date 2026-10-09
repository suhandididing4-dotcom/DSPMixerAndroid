package com.ad1ng.digitalmixer;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.PresetReverb;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {
    int bg=Color.rgb(7,15,25), panel=Color.rgb(15,29,43), orange=Color.rgb(255,119,24), white=Color.WHITE, muted=Color.rgb(170,190,205);
    LinearLayout page; TextView track,status; MediaPlayer player; Equalizer eq; BassBoost bass; PresetReverb reverb; int session; final int PICK=101;
    GradientDrawable shape(int c,int stroke){GradientDrawable d=new GradientDrawable();d.setColor(c);d.setCornerRadius(22);if(stroke!=0)d.setStroke(2,stroke);return d;}
    TextView label(String s,int size,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);if(bold)t.setTypeface(null,Typeface.BOLD);t.setPadding(2,5,2,5);return t;}
    LinearLayout col(){LinearLayout l=new LinearLayout(this);l.setOrientation(1);return l;} LinearLayout row(){LinearLayout l=new LinearLayout(this);l.setGravity(Gravity.CENTER_VERTICAL);return l;}
    Button btn(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(white);b.setBackground(shape(Color.rgb(31,48,64),orange));return b;}
    void card(LinearLayout parent,LinearLayout content){content.setPadding(14,10,14,10);content.setBackground(shape(panel,Color.rgb(33,57,76)));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,7,0,7);parent.addView(content,p);}
    public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(bg);getWindow().setNavigationBarColor(bg);build();}
    void build(){
        ScrollView sc=new ScrollView(this);page=col();page.setPadding(16,12,16,20);page.setBackgroundColor(bg);sc.addView(page);
        page.addView(label("AD 1 NG",32,orange,true));page.addView(label("DIGITAL MIXER • DSP AUDIO PROCESSOR",12,muted,true));page.addView(label("Mixer • Equalizer • Effects • Music Player",14,white,false));
        LinearLayout music=col();music.addView(label("MUSIC PLAYER",18,white,true));track=label("Belum ada lagu dipilih",14,muted,false);music.addView(track);
        LinearLayout controls=row();Button pick=btn("Pilih Lagu");pick.setOnClickListener(v->pickSong());Button play=btn("▶ / ❚❚");play.setOnClickListener(v->toggle());Button stop=btn("Stop");stop.setOnClickListener(v->stop());controls.addView(pick,new LinearLayout.LayoutParams(0,-2,1));controls.addView(play,new LinearLayout.LayoutParams(0,-2,1));controls.addView(stop,new LinearLayout.LayoutParams(0,-2,1));music.addView(controls);status=label("Status: siap",12,orange,true);music.addView(status);card(page,music);
        LinearLayout presets=col();presets.addView(label("PRESET MUSIK",18,white,true));presetRows(presets,new String[]{"Flat","Dangdut","Pop","Rock","EDM","Reggae","Bass Boost"});presets.addView(label("PRESET VOKAL",18,white,true));presetRows(presets,new String[]{"Natural","Male","Female","Bright","Warm","Radio","Deep"});card(page,presets);
        LinearLayout e=col();e.addView(label("EQUALIZER",18,white,true));e.addView(label("Kontrol band EQ perangkat jika didukung",12,muted,false));String[] bands={"Bass","Low","Mid","High","Treble"};for(int i=0;i<5;i++)addBand(e,bands[i],i);card(page,e);
        LinearLayout fx=col();fx.addView(label("EFEK AUDIO",18,white,true));Switch bs=new Switch(this);bs.setText("Bass Boost");bs.setTextColor(white);bs.setOnCheckedChangeListener((v,on)->{try{if(bass!=null)bass.setEnabled(on);}catch(Exception ex){}});fx.addView(bs);Switch rv=new Switch(this);rv.setText("Preset Reverb");rv.setTextColor(white);rv.setOnCheckedChangeListener((v,on)->{try{if(reverb!=null)reverb.setEnabled(on);}catch(Exception ex){}});fx.addView(rv);fx.addView(label("Delay dan compressor belum diimplementasikan pada versi ini.",12,muted,false));card(page,fx);
        LinearLayout out=col();out.addView(label("OUTPUT & LIVE",18,white,true));out.addView(label("Output mengikuti speaker, headset, Bluetooth atau USB yang dipilih Android dan didukung HP.",13,white,false));out.addView(label("Aplikasi memproses lagu di dalam player. Audio TikTok/YouTube dari aplikasi lain tidak bisa dijamin diproses karena batasan Android. Routing langsung ke OBS/TikTok belum tersedia.",13,muted,false));card(page,out);setContentView(sc);
    }
    void presetRows(LinearLayout p,String[] names){for(int i=0;i<names.length;i+=2){LinearLayout r=row();for(int j=i;j<Math.min(i+2,names.length);j++){String n=names[j];Button b=btn(n);b.setOnClickListener(v->preset(n));r.addView(b,new LinearLayout.LayoutParams(0,-2,1));}p.addView(r);}}
    void addBand(LinearLayout p,String name,int index){LinearLayout c=col();c.addView(label(name,13,white,false));SeekBar s=new SeekBar(this);s.setMax(100);s.setProgress(50);s.setProgressTintList(android.content.res.ColorStateList.valueOf(orange));s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){public void onProgressChanged(SeekBar x,int val,boolean user){try{if(user&&eq!=null){short[] range=eq.getBandLevelRange();short band=(short)Math.min(eq.getNumberOfBands()-1,index);eq.setBandLevel(band,(short)(range[0]+(range[1]-range[0])*val/100));}}catch(Exception ex){}}public void onStartTrackingTouch(SeekBar x){}public void onStopTrackingTouch(SeekBar x){}});c.addView(s);p.addView(c);}
    void pickSong(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("audio/*");i.addCategory(Intent.CATEGORY_OPENABLE);startActivityForResult(i,PICK);}
    protected void onActivityResult(int r,int res,Intent d){super.onActivityResult(r,res,d);if(r==PICK&&res==RESULT_OK&&d!=null&&d.getData()!=null)startSong(d.getData());}
    void startSong(Uri uri){releaseFx();if(player!=null)player.release();try{player=new MediaPlayer();player.setDataSource(this,uri);player.setOnPreparedListener(mp->{session=mp.getAudioSessionId();initFx();mp.start();track.setText("Memutar: "+uri.getLastPathSegment());status.setText("Status: memutar lagu");});player.setOnCompletionListener(mp->status.setText("Status: lagu selesai"));player.setOnErrorListener((mp,w,x)->{status.setText("Tidak dapat memutar file ini");return true;});player.prepareAsync();track.setText("Memuat lagu…");}catch(Exception e){status.setText("Gagal membuka audio");}}
    void initFx(){try{eq=new Equalizer(0,session);eq.setEnabled(true);bass=new BassBoost(0,session);bass.setStrength((short)500);bass.setEnabled(false);reverb=new PresetReverb(0,session);reverb.setPreset(PresetReverb.PRESET_MEDIUMROOM);reverb.setEnabled(false);}catch(Exception e){status.setText("Player aktif; efek tidak didukung perangkat");}}
    void preset(String n){if(eq==null){status.setText("Pilih dan putar lagu dahulu");return;}try{short count=eq.getNumberOfBands();short[] range=eq.getBandLevelRange();for(short b=0;b<count;b++){int v=0;if(n.equals("Bass Boost")||n.equals("Dangdut")||n.equals("EDM"))v=b<2?700:(b>count-3?-100:0);else if(n.equals("Rock"))v=(b==0||b==count-1)?250:0;else if(n.equals("Bright")||n.equals("Female"))v=b>count/2?250:0;else if(n.equals("Warm")||n.equals("Male")||n.equals("Deep"))v=b<count/3?200:0;else if(n.equals("Radio"))v=(b==0||b==count-1)?-400:100;v=Math.max(range[0],Math.min(range[1],v));eq.setBandLevel(b,(short)v);}if(bass!=null)bass.setEnabled(n.equals("Bass Boost")||n.equals("Dangdut")||n.equals("EDM"));status.setText("Preset dipilih: "+n);}catch(Exception e){status.setText("Preset tidak didukung HP ini");}}
    void toggle(){if(player==null){pickSong();return;}if(player.isPlaying()){player.pause();status.setText("Status: dijeda");}else{player.start();status.setText("Status: memutar");}}
    void stop(){if(player!=null){player.stop();player.release();player=null;releaseFx();track.setText("Pemutaran dihentikan");status.setText("Status: siap");}}
    void releaseFx(){try{if(eq!=null)eq.release();}catch(Exception e){}eq=null;try{if(bass!=null)bass.release();}catch(Exception e){}bass=null;try{if(reverb!=null)reverb.release();}catch(Exception e){}reverb=null;}
    protected void onDestroy(){if(player!=null)player.release();releaseFx();super.onDestroy();}
}
