package com.noyon.aivideostudio;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.*;
import android.util.Base64;
import android.content.ContentValues;
import android.provider.MediaStore;
import android.net.Uri;
import android.widget.Toast;
import java.io.OutputStream;
public class MainActivity extends Activity {
 WebView web;
 @Override public void onCreate(Bundle b){super.onCreate(b);web=new WebView(this);setContentView(web);WebSettings s=web.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setMediaPlaybackRequiresUserGesture(false);web.addJavascriptInterface(new Bridge(),"AndroidBridge");web.setWebChromeClient(new WebChromeClient());web.setWebViewClient(new WebViewClient());web.loadUrl("file:///android_asset/index.html");}
 public class Bridge{
  @JavascriptInterface public void saveVideo(String data){try{byte[] bytes=Base64.decode(data,Base64.DEFAULT);ContentValues v=new ContentValues();v.put(MediaStore.MediaColumns.DISPLAY_NAME,"NoyonAI-Video-"+System.currentTimeMillis()+".webm");v.put(MediaStore.MediaColumns.MIME_TYPE,"video/webm");v.put(MediaStore.MediaColumns.RELATIVE_PATH,"Movies/NOYON AI VIDEO STUDIO");Uri u=getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,v);if(u==null)throw new Exception();OutputStream o=getContentResolver().openOutputStream(u);o.write(bytes);o.close();runOnUiThread(()->Toast.makeText(MainActivity.this,"ভিডিও সেভ হয়েছে",Toast.LENGTH_LONG).show());}catch(Exception e){runOnUiThread(()->Toast.makeText(MainActivity.this,"ভিডিও সেভ করা যায়নি",Toast.LENGTH_LONG).show());}}
 }
 @Override public void onBackPressed(){if(web!=null&&web.canGoBack())web.goBack();else super.onBackPressed();}
}