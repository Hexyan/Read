package com.hjy.read.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.hjy.read.R;
import com.hjy.read.ui.base.BasePresenter;
import com.hjy.read.ui.base.MVPBaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;


public class PictureActivity extends MVPBaseActivity {

    public static final String IMG_URL = "img_url";
    public static final String IMG_DESC = "img_desc";
    public static final String TRANSIT_PIC = "picture";

    private String img_url;
    private String img_desc;

    @Bind(R.id.iv_meizhi_pic)
    ImageView iv_meizhi_pic;

    @OnClick(R.id.save_img)
    void saveImg() {
        saveImage();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pic;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        //设置共享元素
        ViewCompat.setTransitionName(iv_meizhi_pic, TRANSIT_PIC);
        Glide.with(this).load(img_url).centerCrop().into(iv_meizhi_pic);
        new PhotoViewAttacher(iv_meizhi_pic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.clear(iv_meizhi_pic);
    }

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.IMG_URL, url);
        intent.putExtra(PictureActivity.IMG_DESC, desc);
        return intent;
    }

    private void parseIntent() {
        img_url = getIntent().getStringExtra(IMG_URL);
        img_desc = getIntent().getStringExtra(IMG_DESC);
    }

    private void saveImage() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        iv_meizhi_pic.buildDrawingCache();
                        Bitmap bitmap = iv_meizhi_pic.getDrawingCache();
                        //将Bitmap 转换成二进制，写入本地
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhigan");
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        File file = new File(dir, img_desc.substring(0, 10) + ".png");
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(byteArray, 0, byteArray.length);
                            fos.flush();
                            //用广播通知相册进行更新相册
                            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri = Uri.fromFile(file);
                            intent.setData(uri);
                            PictureActivity.this.sendBroadcast(intent);
                            Toast.makeText(PictureActivity.this, "保存成功~", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Oups permission denied
                        Toast.makeText(PictureActivity.this, "保存失败~", Toast.LENGTH_LONG).show();
                    }
                });

    }
}
