package com.hjy.read.loginregisterdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hjy.read.R;
import com.hjy.read.loginregisterdemo.mdoel.User;
import com.hjy.read.loginregisterdemo.mdoel.UserLab;
import static com.hjy.read.loginregisterdemo.mdoel.UserLab.setUsercheck;

public class DetailActivity extends AppCompatActivity {

    private TextView mTextView;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        //实例化组件
        initElements();
        //显示数据
        showData();
        boolean resetusercheck=false;
        setUsercheck(resetusercheck);//已经登陆成功后重置密码用户名检测参数，保证下次继续检查
    }

    private void initElements(){
        mTextView = (TextView) findViewById(R.id.activity_detail_tv);
    }

    private void showData(){

        mUser = UserLab.get(DetailActivity.this,"","").getUser();
        mTextView.setText("用户："+mUser.getNickname()+" 登陆成功！\n"+"手机号："+mUser.getPhoneNum()+" \n"+"密码："+mUser.getPassword());

    }

}
