package cu.cubava.libreoffice;

/**
 * Created by Rohit on 23/01/16.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.net.Proxy;
import android.net.http.SslError;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.Proxy;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.widget.ProgressBar;

import java.util.HashSet;
import java.util.List;

public class WebViewResposive extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    AlertDialog.Builder altDialog;

    public static String FACEBOOK_URL = "https://www.facebook.com/LibreOfficecuba";
    public static String FACEBOOK_PAGE_ID = "LibreOfficecuba";
    private ProgressBar progress;
    String NetworkAlert="Verifique su conexión a Internet";
    boolean doubleBackToExitPressedOnce = false;
    String intialUrl= "http://libreoffice.cubava.cu/";
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        altDialog = new AlertDialog.Builder(this);
        altDialog.setCancelable(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        wv = (WebView)findViewById(R.id.webView1);
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);

        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv.getSettings().setAppCacheEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setSavePassword(true);
        settings.setSaveFormData(true);

        wv.loadUrl("http://libreoffice.cubava.cu/");
        wv.setWebViewClient(new MywebViewClient());

        //Status bar color change
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        checkPermission(); // Permission Check for External Storage

        loadWebview();

    }



    public  void loadWebview(){

        Networkcheck activeNetwork = new Networkcheck(this);
        boolean isConnected = activeNetwork.isConnectingToInternet();

        System.out.println("Conexión : " + isConnected); // Network Check

        if (isConnected == true) {  //Network Check
            // Making the Web-View Responsive
            progress.setVisibility(View.VISIBLE);
            wv.setVisibility(View.VISIBLE);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.requestFocusFromTouch();
            wv.setWebChromeClient(new WebChromeClient());
            wv.setWebViewClient(new WebViewClient());
            wv.getSettings().setAllowFileAccess(true);
            wv.requestFocusFromTouch();
            wv.reload();
            wv.setWebChromeClient(new WebChromeClient());
            wv.getSettings().setAllowFileAccess(true);
            wv.getSettings().setBuiltInZoomControls(true);
            wv.getSettings().setSupportZoom(true);
            wv.getSettings().setDisplayZoomControls(false);
            wv.getSettings().setUseWideViewPort(true);
            wv.getSettings().setLoadWithOverviewMode(true);
            wv.getSettings().setSupportMultipleWindows(true);
            wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            wv.getSettings().setAppCacheEnabled(true);
            wv.setHorizontalScrollBarEnabled(true);
            wv.setVerticalScrollBarEnabled(true);
            wv.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 10; Pixel 2 XL) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.143 Mobile Safari/537.36");
            wv.clearCache(true);
            wv.clearHistory();
            wv.getSettings().setDomStorageEnabled(true);
            wv.getSettings().setLoadWithOverviewMode(true);
            wv.getSettings().setDatabaseEnabled(true);
            wv.getSettings().setDomStorageEnabled(true);

            wv.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    // TODO Auto-generated method stub
                    super.onLoadResource(view, url);
                }


                @Override
                public void onPageStarted(WebView view, String url,
                                          Bitmap favicon) {
                    // TODO Auto-generated method stub
                    super.onPageStarted(view, url, favicon);

                }

                @Override
                public void onPageFinished(WebView view, final String url) {
                    System.out.println("URL en la página terminada: " + url);
                    progress.setVisibility(View.GONE);
                    super.onPageFinished(view, url);








                }
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                }

            });

            wv.loadUrl(intialUrl);

            //Download Fucntionality
            wv.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String s, String s1, String s2, String s3, long l) { //url, userAgent,contentDescription, mimetype, contentLength

                    DownloadManager.Request db_request=new DownloadManager.Request(Uri.parse(s));
                    db_request.allowScanningByMediaScanner();
                    db_request.setNotificationVisibility(
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    String fileName = URLUtil.guessFileName(s,s1,s3);
                    db_request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);

                    DownloadManager dManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dManager.enqueue(db_request);


                }
            });

        } else {
            Alert(NetworkAlert);
        }
    }


    public  void Alert(String Alert){
        progress.setVisibility(View.GONE);
        AlertDialog alertDialog = new AlertDialog.Builder(WebViewResposive.this).create();
        alertDialog.setTitle("Alerta");
        alertDialog.setMessage(Alert);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cerrar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Inténtalo de nuevo",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        loadWebview();  //<- Call the Web view load again

                    }
                });


        alertDialog.show();
    }

    protected void checkPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    // show an alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Permiso de almacenamiento");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    WebViewResposive.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    123
                            );
                        }
                    });
                    builder.setNeutralButton("Cancelar",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    // Request permission
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            123
                    );
                }
            }else {
                // Permission already granted
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case 123:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission granted
                }else {
                    // Permission denied
                }
            }
        }
    }
    
    
    
    
    


    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_android) {
            // Handle the camera action
            wv.loadUrl("http://libreoffice.cubava.cu/");
        } else if (id == R.id.nav_html) {
            wv.loadUrl("https://libreoffice.cubava.cu/sobre-mi/");

        } else if (id == R.id.nav_php) {
            wv.loadUrl("https://libreoffice.cubava.cu/categor%C3%ADa/tutoriales/");

        } else if (id == R.id.nav_phone) {
            wv.loadUrl("https://libreoffice.cubava.cu/categor%C3%ADa/trucos/");

        } else if (id == R.id.nav_health) {
            wv.loadUrl("https://libreoffice.cubava.cu/categor%C3%ADa/manuales/");

        } else if (id == R.id.sobre_mi) {
            Intent i = new Intent(WebViewResposive.this, Acercade.class);
            startActivity(i);
        } else if (id == R.id.nav_money) {
            openFbUrl("LibreOfficecuba");

        }else if (id == R.id.nav_login) {
            //wv.loadUrl("https://twitter.com/LibreOfficeCuba");
            openTwitterUrl("LibreOfficeCuba");
        }else if (id == R.id.nav_telegram) {
           // wv.loadUrl("https://telegram.me/libreoffice_es");
            Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/libreoffice_es"));
            startActivity(telegram);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MywebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()== KeyEvent.ACTION_DOWN){
            switch(keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if(wv.canGoBack()){
                        wv.goBack();
                    }
                    else{
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void openFbUrl(String username){
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(username);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    public String getFacebookPageURL(String username) {
        String FACEBOOK_URL = "https://www.facebook.com/"+username;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + username;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    protected void openTwitterUrl(String username){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name="+username));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/#!/"+username)));
        }
    }


}