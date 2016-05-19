package wgz.com.antbaojie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import wgz.com.antbaojie.util.PathMaker;
import wgz.com.antbaojie.util.httpUtil;

public class LoginActivity extends AppCompatActivity {
    private TextView mName,username,userpass,forgetpass;
    private ImageView mFace;
    private LinearLayout login;
    private boolean ifHasName = false;
    private boolean ifHasPass = false;
    private RelativeLayout root;
    private PathMaker pathMaker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mName = (TextView) findViewById(R.id.id_login_name);
        username = (TextView) findViewById(R.id.id_login_username);
        userpass = (TextView) findViewById(R.id.id_login_userpass);
        forgetpass = (TextView) findViewById(R.id.id_login_forgetPass);
        mFace = (ImageView) findViewById(R.id.id_login_face);
        login = (LinearLayout) findViewById(R.id.loginBtn);
        root = (RelativeLayout) findViewById(R.id.id_login_root);
        pathMaker = new PathMaker();
        //强制获取焦点
        username.setFocusable(true);
        username.setFocusableInTouchMode(true);
        username.requestFocus();
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    ifHasName = true;
                }else {
                    ifHasName = false;
                }
            }
        });
        userpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    ifHasPass = true;

                }else {
                    ifHasPass = false;
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifHasPass&&ifHasName){
                    login();

                }else {
                    Snackbar.make(root,"请输入正确的内容!",Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void login() {
        ChackLogin chackLogin = new ChackLogin();
        chackLogin.execute();
        chackLogin.setOnFinishListener(new taskFinishListener() {
            @Override
            public void onSuccess(Object o) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }

            @Override
            public void onFaild() {
                Snackbar.make(root,"登陆失败！",Snackbar.LENGTH_SHORT).show();
            }
        });

    }
    public interface taskFinishListener{
        void onSuccess(Object o);
        void onFaild();

    }
    public class ChackLogin extends AsyncTask{
       private taskFinishListener taskFinishListener;
        public void setOnFinishListener(taskFinishListener taskFinishListener){
            this.taskFinishListener = taskFinishListener;

        }


        @Override
        protected Object doInBackground(Object[] params) {
            //httpUtil.getIS(pathMaker.getPath());
            String result =httpUtil.getStr(pathMaker.getPath(),"UTF-8");
            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            String str = o.toString();
            Log.i("login","loginResult:"+str);
            if (str.equals("1")){
                taskFinishListener.onSuccess(str);
            }else {
                taskFinishListener.onFaild();

            }
        }
    }

}
