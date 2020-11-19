package com.rahulcompany.lifelinesappstore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Random;

public class AppDetailActivity extends AppCompatActivity implements View.OnClickListener {

    AppDataHome obj;
    ImageView app_icon,sc1,sc2,sc3,sc4,sc5;
    TextView app_title,app_rating,app_rating2,app_reviewsnum,app_reviewsnum2,app_size,app_download;
    TextView app_about,reviewname1,reviewname2,reviewname3,rdet1,rdet2,rdet3;
    TextView devcom,app_email,app_privacy,whatsapplink;
    Button writereview;

    ProgressBar pro1,pro2,pro3,pro4, pro5;

    ShimmerFrameLayout shimmerFrameLayout;
    HorizontalScrollView forshi;

    Button install;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_app_detail);
        setSupportActionBar(toolbar);


        init();
        setvalues();


        showloadingofsc();
    }

    private void showloadingofsc() {
        forshi.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerFrameLayout.setVisibility(View.GONE);
                forshi.setVisibility(View.VISIBLE);
            }
        },7000);
    }

    private void setvalues() {
        Glide.with(AppDetailActivity.this).load(obj.getIconurl()).into(app_icon);
        app_title.setText(obj.getName());
        app_rating.setText(obj.getRating());
        app_rating2.setText(obj.getRating());
        app_reviewsnum.setText(obj.getReviewsnum() + " Reviews");
        app_reviewsnum2.setText(obj.getReviewsnum() + " Reviews");
        app_size.setText(obj.getSize());
        app_download.setText(obj.getDownloads());

        Glide.with(AppDetailActivity.this).load(obj.getSc1url()).into(sc1);
        Glide.with(AppDetailActivity.this).load(obj.getSc2url()).into(sc2);
        Glide.with(AppDetailActivity.this).load(obj.getSc3url()).into(sc3);
        Glide.with(AppDetailActivity.this).load(obj.getSc4url()).into(sc4);
        Glide.with(AppDetailActivity.this).load(obj.getSc5url()).into(sc5);


        app_about.setText(obj.getAbout());

        reviewname1.setText(obj.getReviewname1());
        reviewname2.setText(obj.getReviewname2());
        reviewname3.setText(obj.getReviewname3());

        rdet1.setText(obj.getReviewdetail1());
        rdet2.setText(obj.getReviewdetail2());
        rdet3.setText(obj.getReviewdetail3());

        devcom.setText(obj.getDevcom());
        app_email.setOnClickListener(this);
        app_privacy.setOnClickListener(this);

        writereview.setOnClickListener(this);
        //todo : progressbar random number

        Random r = new Random();

        pro1.setProgress(r.nextInt(10));
        pro2.setProgress(r.nextInt(60-30)+30);
        pro3.setProgress(r.nextInt(60-30)+30);
        pro4.setProgress(r.nextInt(100-50)+50);
        pro5.setProgress(r.nextInt(100-50)+50);

        whatsapplink.setOnClickListener(this);




    }

    private void init() {
        obj= (AppDataHome) getIntent().getSerializableExtra("AppDataHome");
        Log.d("obj", obj.getApkurl());
        app_icon = findViewById(R.id.app_icon);
        app_title = findViewById(R.id.app_title);
        app_rating = findViewById(R.id.app_rating);
        app_rating2 = findViewById(R.id.app_rating2);

        app_reviewsnum = findViewById(R.id.app_reviewsnum);
        app_reviewsnum2 = findViewById(R.id.app_reviewsnum2);

        app_size = findViewById(R.id.app_size);
        app_download = findViewById(R.id.app_download);

        sc1 = findViewById(R.id.sc1);
        sc2 = findViewById(R.id.sc2);
        sc3 = findViewById(R.id.sc3);
        sc4 = findViewById(R.id.sc4);
        sc5 = findViewById(R.id.sc5);

        sc1.setOnClickListener(this);
        sc2.setOnClickListener(this);
        sc3.setOnClickListener(this);
        sc4.setOnClickListener(this);
        sc5.setOnClickListener(this);

        app_about = findViewById(R.id.app_about);

        reviewname1 = findViewById(R.id.app_reviewname1);
        reviewname2 = findViewById(R.id.app_reviewname2);
        reviewname3 = findViewById(R.id.app_reviewname3);

        rdet1 = findViewById(R.id.app_reviewdet1);
        rdet2 = findViewById(R.id.app_reviewdet2);
        rdet3 = findViewById(R.id.app_reviewdet3);

        devcom = findViewById(R.id.app_devcom);
        app_email = findViewById(R.id.app_email);
        app_privacy = findViewById(R.id.app_privacy);

        writereview = findViewById(R.id.writereview);

        pro1 = findViewById(R.id.pro1);
        pro2 = findViewById(R.id.pro2);
        pro3 = findViewById(R.id.pro3);
        pro4 = findViewById(R.id.pro4);
        pro5 = findViewById(R.id.pro5);

        shimmerFrameLayout = findViewById(R.id.shimmerframe);
        forshi = findViewById(R.id.sampleforshimmer);

        whatsapplink = findViewById(R.id.whatsapplink);

        install = findViewById(R.id.install);
        install.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sc1:
                loadimageondialog(obj.getSc1url());
                break;
            case R.id.sc2:
                loadimageondialog(obj.getSc2url());
                break;
            case R.id.sc3:
                loadimageondialog(obj.getSc3url());
                break;
            case R.id.sc4:
                loadimageondialog(obj.getSc4url());
                break;
            case R.id.sc5:
                loadimageondialog(obj.getSc5url());
                break;
            case R.id.writereview:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{obj.getDevemail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Review on " + obj.getName() + " APK");
                startActivity(intent);
                break;

            case R.id.app_email:
                Intent intent1 = new Intent(Intent.ACTION_SENDTO);
                intent1.setData(Uri.parse("mailto:"));
                intent1.putExtra(Intent.EXTRA_EMAIL, new String[]{obj.getDevemail()});
                startActivity(intent1);
                break;
            case R.id.app_privacy:
                loadprivacy();
                break;

            case R.id.whatsapplink:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(obj.getDevweb()));
                startActivity(i);
                break;

            case R.id.install:
                if (ContextCompat.checkSelfPermission(AppDetailActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(AppDetailActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    new downloadtask(obj.getSizenum(), obj.getApkurl(), AppDetailActivity.this).execute();
                } else {
                    ActivityCompat.requestPermissions(AppDetailActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }

                break;
        }
    }


    void loadimageondialog(String url) {
        Dialog d = new Dialog(AppDetailActivity.this);
        d.setCancelable(true);
        d.setContentView(R.layout.dialog_imageshow);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView pre = d.findViewById(R.id.preview);
        Glide.with(AppDetailActivity.this).load(url).into(pre);
        d.show();
    }

    void loadprivacy() {
        String policy="\n" +
                "                                 Apache License\n" +
                "                           Version 2.0, May 2020\n" +

                "\n" +
                "   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION\n" +
                "\n" +
                "   1. Definitions.\n" +
                "\n" +
                "      \"License\" shall mean the terms and conditions for use, reproduction,\n" +
                "      and distribution as defined by Sections 1 through 9 of this document.\n" +
                "\n" +
                "      \"Licensor\" shall mean the copyright owner or entity authorized by\n" +
                "      the copyright owner that is granting the License.\n" +
                "\n" +
                "      \"Legal Entity\" shall mean the union of the acting entity and all\n" +
                "      other entities that control, are controlled by, or are under common\n" +
                "      control with that entity. For the purposes of this definition,\n" +
                "      \"control\" means (i) the power, direct or indirect, to cause the\n" +
                "      direction or management of such entity, whether by contract or\n" +
                "      otherwise, or (ii) ownership of fifty percent (50%) or more of the\n" +
                "      outstanding shares, or (iii) beneficial ownership of such entity.\n" +
                "\n" +
                "      \"You\" (or \"Your\") shall mean an individual or Legal Entity\n" +
                "      exercising permissions granted by this License.\n" +
                "\n" +
                "      \"Source\" form shall mean the preferred form for making modifications,\n" +
                "      including but not limited to software source code, documentation\n" +
                "      source, and configuration files.\n" +
                "\n" +
                "      \"Object\" form shall mean any form resulting from mechanical\n" +
                "      transformation or translation of a Source form, including but\n" +
                "      not limited to compiled object code, generated documentation,\n" +
                "      and conversions to other media types.\n" +
                "\n" +
                "      \"Work\" shall mean the work of authorship, whether in Source or\n" +
                "      Object form, made available under the License, as indicated by a\n" +
                "      copyright notice that is included in or attached to the work\n" +
                "      (an example is provided in the Appendix below).\n" +
                "\n" +
                "      \"Derivative Works\" shall mean any work, whether in Source or Object\n" +
                "      form, that is based on (or derived from) the Work and for which the\n" +
                "      editorial revisions, annotations, elaborations, or other modifications\n" +
                "      represent, as a whole, an original work of authorship. For the purposes\n" +
                "      of this License, Derivative Works shall not include works that remain\n" +
                "      separable from, or merely link (or bind by name) to the interfaces of,\n" +
                "      the Work and Derivative Works thereof.\n" +
                "\n" +
                "      \"Contribution\" shall mean any work of authorship, including\n" +
                "      the original version of the Work and any modifications or additions\n" +
                "      to that Work or Derivative Works thereof, that is intentionally\n" +
                "      submitted to Licensor for inclusion in the Work by the copyright owner\n" +
                "      or by an individual or Legal Entity authorized to submit on behalf of\n" +
                "      the copyright owner. For the purposes of this definition, \"submitted\"\n" +
                "      means any form of electronic, verbal, or written communication sent\n" +
                "      to the Licensor or its representatives, including but not limited to\n" +
                "      communication on electronic mailing lists, source code control systems,\n" +
                "      and issue tracking systems that are managed by, or on behalf of, the\n" +
                "      Licensor for the purpose of discussing and improving the Work, but\n" +
                "      excluding communication that is conspicuously marked or otherwise\n" +
                "      designated in writing by the copyright owner as \"Not a Contribution.\"\n" +
                "\n" +
                "      \"Contributor\" shall mean Licensor and any individual or Legal Entity\n" +
                "      on behalf of whom a Contribution has been received by Licensor and\n" +
                "      subsequently incorporated within the Work.\n" +
                "\n" +
                "   2. Grant of Copyright License. Subject to the terms and conditions of\n" +
                "      this License, each Contributor hereby grants to You a perpetual,\n" +
                "      worldwide, non-exclusive, no-charge, royalty-free, irrevocable\n" +
                "      copyright license to reproduce, prepare Derivative Works of,\n" +
                "      publicly display, publicly perform, sublicense, and distribute the\n" +
                "      Work and such Derivative Works in Source or Object form.\n" +
                "\n" +
                "   3. Grant of Patent License. Subject to the terms and conditions of\n" +
                "      this License, each Contributor hereby grants to You a perpetual,\n" +
                "      worldwide, non-exclusive, no-charge, royalty-free, irrevocable\n" +
                "      (except as stated in this section) patent license to make, have made,\n" +
                "      use, offer to sell, sell, import, and otherwise transfer the Work,\n" +
                "      where such license applies only to those patent claims licensable\n" +
                "      by such Contributor that are necessarily infringed by their\n" +
                "      Contribution(s) alone or by combination of their Contribution(s)\n" +
                "      with the Work to which such Contribution(s) was submitted. If You\n" +
                "      institute patent litigation against any entity (including a\n" +
                "      cross-claim or counterclaim in a lawsuit) alleging that the Work\n" +
                "      or a Contribution incorporated within the Work constitutes direct\n" +
                "      or contributory patent infringement, then any patent licenses\n" +
                "      granted to You under this License for that Work shall terminate\n" +
                "      as of the date such litigation is filed.\n" +
                "\n" +
                "   4. Redistribution. You may reproduce and distribute copies of the\n" +
                "      Work or Derivative Works thereof in any medium, with or without\n" +
                "      modifications, and in Source or Object form, provided that You\n" +
                "      meet the following conditions:\n" +
                "\n" +
                "      (a) You must give any other recipients of the Work or\n" +
                "          Derivative Works a copy of this License; and\n" +
                "\n" +
                "      (b) You must cause any modified files to carry prominent notices\n" +
                "          stating that You changed the files; and\n" +
                "\n" +
                "      (c) You must retain, in the Source form of any Derivative Works\n" +
                "          that You distribute, all copyright, patent, trademark, and\n" +
                "          attribution notices from the Source form of the Work,\n" +
                "          excluding those notices that do not pertain to any part of\n" +
                "          the Derivative Works; and\n" +
                "\n" +
                "      (d) If the Work includes a \"NOTICE\" text file as part of its\n" +
                "          distribution, then any Derivative Works that You distribute must\n" +
                "          include a readable copy of the attribution notices contained\n" +
                "          within such NOTICE file, excluding those notices that do not\n" +
                "          pertain to any part of the Derivative Works, in at least one\n" +
                "          of the following places: within a NOTICE text file distributed\n" +
                "          as part of the Derivative Works; within the Source form or\n" +
                "          documentation, if provided along with the Derivative Works; or,\n" +
                "          within a display generated by the Derivative Works, if and\n" +
                "          wherever such third-party notices normally appear. The contents\n" +
                "          of the NOTICE file are for informational purposes only and\n" +
                "          do not modify the License. You may add Your own attribution\n" +
                "          notices within Derivative Works that You distribute, alongside\n" +
                "          or as an addendum to the NOTICE text from the Work, provided\n" +
                "          that such additional attribution notices cannot be construed\n" +
                "          as modifying the License.\n" +
                "\n" +
                "      You may add Your own copyright statement to Your modifications and\n" +
                "      may provide additional or different license terms and conditions\n" +
                "      for use, reproduction, or distribution of Your modifications, or\n" +
                "      for any such Derivative Works as a whole, provided Your use,\n" +
                "      reproduction, and distribution of the Work otherwise complies with\n" +
                "      the conditions stated in this License.\n" +
                "\n" +
                "   5. Submission of Contributions. Unless You explicitly state otherwise,\n" +
                "      any Contribution intentionally submitted for inclusion in the Work\n" +
                "      by You to the Licensor shall be under the terms and conditions of\n" +
                "      this License, without any additional terms or conditions.\n" +
                "      Notwithstanding the above, nothing herein shall supersede or modify\n" +
                "      the terms of any separate license agreement you may have executed\n" +
                "      with Licensor regarding such Contributions.\n" +
                "\n" +
                "   6. Trademarks. This License does not grant permission to use the trade\n" +
                "      names, trademarks, service marks, or product names of the Licensor,\n" +
                "      except as required for reasonable and customary use in describing the\n" +
                "      origin of the Work and reproducing the content of the NOTICE file.\n" +
                "\n" +
                "   7. Disclaimer of Warranty. Unless required by applicable law or\n" +
                "      agreed to in writing, Licensor provides the Work (and each\n" +
                "      Contributor provides its Contributions) on an \"AS IS\" BASIS,\n" +
                "      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or\n" +
                "      implied, including, without limitation, any warranties or conditions\n" +
                "      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A\n" +
                "      PARTICULAR PURPOSE. You are solely responsible for determining the\n" +
                "      appropriateness of using or redistributing the Work and assume any\n" +
                "      risks associated with Your exercise of permissions under this License.\n" +
                "\n" +
                "   8. Limitation of Liability. In no event and under no legal theory,\n" +
                "      whether in tort (including negligence), contract, or otherwise,\n" +
                "      unless required by applicable law (such as deliberate and grossly\n" +
                "      negligent acts) or agreed to in writing, shall any Contributor be\n" +
                "      liable to You for damages, including any direct, indirect, special,\n" +
                "      incidental, or consequential damages of any character arising as a\n" +
                "      result of this License or out of the use or inability to use the\n" +
                "      Work (including but not limited to damages for loss of goodwill,\n" +
                "      work stoppage, computer failure or malfunction, or any and all\n" +
                "      other commercial damages or losses), even if such Contributor\n" +
                "      has been advised of the possibility of such damages.\n" +
                "\n" +
                "   9. Accepting Warranty or Additional Liability. While redistributing\n" +
                "      the Work or Derivative Works thereof, You may choose to offer,\n" +
                "      and charge a fee for, acceptance of support, warranty, indemnity,\n" +
                "      or other liability obligations and/or rights consistent with this\n" +
                "      License. However, in accepting such obligations, You may act only\n" +
                "      on Your own behalf and on Your sole responsibility, not on behalf\n" +
                "      of any other Contributor, and only if You agree to indemnify,\n" +
                "      defend, and hold each Contributor harmless for any liability\n" +
                "      incurred by, or claims asserted against, such Contributor by reason\n" +
                "      of your accepting any such warranty or additional liability.\n" +
                "\n" +
                "   END OF TERMS AND CONDITIONS\n" +
                "\n" +
                "   APPENDIX: How to apply the Apache License to your work.\n" +
                "\n" +
                "      To apply the Apache License to your work, attach the following\n" +
                "      boilerplate notice, with the fields enclosed by brackets \"[]\"\n" +
                "      replaced with your own identifying information. (Don't include\n" +
                "      the brackets!)  The text should be enclosed in the appropriate\n" +
                "      comment syntax for the file format. We also recommend that a\n" +
                "      file or class name and description of purpose be included on the\n" +
                "      same \"printed page\" as the copyright notice for easier\n" +
                "      identification within third-party archives.\n" +
                "\n" +
                "   Copyright [2020] [Rahul's & Company]\n" +
                "\n" +
                "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "   you may not use this file except in compliance with the License.\n" +
                "   You may obtain a copy of the License at\n" +


                "\n" +
                "   Unless required by applicable law or agreed to in writing, software\n" +
                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "   See the License for the specific language governing permissions and\n" +
                "   limitations under the License.";
        Dialog d = new Dialog(AppDetailActivity.this);
        d.setContentView(R.layout.dialog_privacy_policy);
        d.setCancelable(true);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView privacy = d.findViewById(R.id.privacytext);
        privacy.setText(policy);
        d.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            int temp=0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    temp=1;
                }
            }

            if (temp == 1) {
                ActivityCompat.requestPermissions(AppDetailActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

            } else {
                new downloadtask(obj.getSizenum(), obj.getApkurl(), AppDetailActivity.this).execute();

            }
        }
    }


    //todo : install operation
    //todo: json data adding

}
