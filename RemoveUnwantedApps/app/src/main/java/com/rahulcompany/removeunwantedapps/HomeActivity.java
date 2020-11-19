package com.rahulcompany.removeunwantedapps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<App> list;
    Button btn;
    private String[] china = new String[]{"tiktok","shareit","kwai","ucbrowser","baidumap","shein","clashofkings","dubatterysaver","helo","likee","youcammakeup","micommunity","cmbrowser","viruscleaner","apusbrowser","remowe","clubfactory","newsdog","beautryplus","wechat","ucnews","qqmil","weibo","xender","qqmusic","qqnewsfeed","bigolive","selfiecity","mailmaster","parallelspace","mivideocall-xiaomi","wesync","esfileexplorer","vivavideo","meitu","vigovideo","newvideostatus","durecorder","vault-hide","cachecleaner","ducleaner","dubrowser","hagoplaywithnewfriends","canscanner","cleanmaster","wondercamera","photowonder","qqplayer","wemeet","sweetselfie","baidutranslate","vmate","qqinternational","qqsecuritycenter","qqlauncher","uvideo","vflystatusvideo","mobilelegends","duprivacy"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        lv = findViewById(R.id.listview);
        list = new ArrayList<>();
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findapp();
                updatelist();
            }
        });
        findapp();
        updatelist();



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                App app = (App) adapterView.getItemAtPosition(i);
                uninstallapp(app);
            }
        });
    }

    private void updatelist() {
        if (list.size() != 0) {

            lv.setAdapter(new CustomAdapter(HomeActivity.this, list));
        } else {
            Intent i = new Intent(HomeActivity.this, RewardActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void uninstallapp(App app) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + app.getPname()));
        startActivity(intent);
    }


    private void findapp() {

        list = new ArrayList<>();
        List<PackageInfo> pinfo = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            int flag=0;
            PackageInfo info = pinfo.get(i);
            if (info.versionName == null || ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)) {
                continue;
            }
            String label=info.applicationInfo.loadLabel(getPackageManager()).toString();

            label=label.toLowerCase();
            label = label.replace(" ", "");
            for (int j = 0; j < china.length; j++) {
                if (label.equals(china[j])) {
                    flag=1;
                    break;
                }
            }

            if (flag == 0) {
                continue;
            }

            App app = new App();
            app.setLabel(info.applicationInfo.loadLabel(getPackageManager()).toString());
            app.setPname(info.packageName);
            app.setIcon(info.applicationInfo.loadIcon(getPackageManager()));
            list.add(app);
        }
    }


}