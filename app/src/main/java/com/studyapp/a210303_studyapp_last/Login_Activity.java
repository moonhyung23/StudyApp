package com.studyapp.a210303_studyapp_last;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_Login, btn_CreateAccount;
    EditText ed_LoginId, ed_LoginPw;
    ArrayList<Data_user> list_DatauserData;
    boolean LoginCheck;
    Map<String, ?> map;
    //네이버 API 사이트에서 오픈API 이용신청을 통해 본인의 어플리케이션을 등록한 후 발급받은 ID,SECRET과 본인 어플의 이름 지정
    private static String OAUTH_CLIENT_ID = "q9f1CFzcom6O67oKTeSv";
    private static String OAUTH_CLIENT_SECRET = "_lfRdamI2o";
    private static String OAUTH_CLIENT_NAME = "열공승천4";

    OAuthLogin mOAuthLoginModule;
    private static Context mContext;
    OAuthLoginButton mOAuthLoginButton;
    OAuthLoginHandler mOAuthLoginHandler;
    String NaverId_key = "";
    int check_naver_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        set_findview();
        set_buttonListner();


        map = SharedClass.getAll_Sharedkey(getApplicationContext(), SharedClass.PREFERENCES_Name_UserData);

        //네이버 API 로그인
        mContext = getApplicationContext();
        mOAuthLoginModule = OAuthLogin.getInstance();
        //로그인 모듈에 값을 초기화해준다.
        mOAuthLoginModule.init(
                mContext
                , OAUTH_CLIENT_ID
                , OAUTH_CLIENT_SECRET
                , OAUTH_CLIENT_NAME);

        //로그인 핸들러를 정의한다. 로그인 핸들러는 로그인 창에서 로그인이 완료되거나 취소될 때 자동으로 호출된다.
        mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    //TODO 로그인에 성공했을 경우,
                    // 보통아래 코드를 통하여 접근 토큰, 갱신 토큰, 접근 토큰 만료 기간, 토큰 타입 등을 얻는다.
                    // 접근 토큰은 사용자의 인증을 대신하며 오픈 API를 사용하기 위한 인증값으로 사용된다.
                    // 사용자의 정보도 이 접근
                    // 토큰을 이용하여 받아올 수 있다.
                    // 접근 토큰의 기간이 만료되면 인증 토큰을 새로 발급받거나 갱신 토큰을 이용해 접근 토큰을 갱신해야 한다.

                    String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                    long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                    String tokenType = mOAuthLoginModule.getTokenType(mContext);
                    //네이버에서 로그인 정보 가져오기
                    new ProfileTask().execute(accessToken);
                    //네이버에서 가져온 id를 쉐어드의 키 값으로 사용자 정보 리스트를 불러온다.
                    //사용자 정보리스트에서 id를 변수에 담는다.

                    //로그인시 내 아이디가 없으면
                    //로그아웃 => 정보권한

                } else {
                    //TODO 로그인에 실패했을 경우, 아래 코드를 통해 로그인 실패 에러 코드와 실패 이유를 얻을 수 있다.
                    String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                    String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                    Log.d("onCreate", "로그인 실패");
                    Log.d("errorDesc", errorDesc);
                    Log.d("errorDesc", errorCode);
                }
            }
        };

        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);//로그인 핸들러

    }

    class ProfileTask extends AsyncTask<String, Void, String> {
        String result;

        @Override
        protected String doInBackground(String... strings) {
            String token = strings[0];// 네이버 로그인 접근 토큰;
            String header = "Bearer " + token; // Bearer 다음에 공백 추가
            try {
                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                result = response.toString();
                br.close();
                System.out.println(response.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
            //result 값은 JSONObject 형태로 넘어옵니다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                //넘어온 result 값을 JSONObject 로 변환해주고, 값을 가져오면 되는데요.
                // result 를 Log에 찍어보면 어떻게 가져와야할 지 감이 오실거에요.
                JSONObject object = new JSONObject(result);
                if (object.getString("resultcode").equals("00")) {
                    JSONObject jsonObject = new JSONObject(object.getString("response"));
                    //네이버에서 아이디키 값을 가져온다.
                    Myapplication.Naver_Login_Id_Key = jsonObject.getString("id");

                    //쉐어드에 저장된 전체 키와 비교하기.
                    for (Map.Entry<String, ?> allKey : map.entrySet()) {
                        //유저정보 리스트에서 네이버 id_key와 같은 키를 찾는다.
                        if (Myapplication.Naver_Login_Id_Key.equals(allKey.getKey())) {
                            //같은 키가 있는 경우 변수로 표시
                            check_naver_id = 1;
                        }
                    }

                    //네이버 아이디키가 유저정보 리스트에 있는 경우 바로 메인엑티비티로 이동
                    if (check_naver_id == 1) {
                        Myapplication.UserId = Myapplication.Naver_Login_Id_Key;
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {//없는 경우 회원가입 엑티비티로 이동
                        //네이버로그인 정보에서 가져온 id키를 변수에 저장.
                        //naver로그인 성공시 처음인 경우 네이버 로그인 전용 회원가입 정보 입력 엑티비티로 이동
                        Intent intent = new Intent(getApplicationContext(), Create_Naver_Account_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void set_buttonListner() {//버튼 리스너 모음
        btn_Login.setOnClickListener(this);
        btn_CreateAccount.setOnClickListener(this);
    }

    public void set_findview() {//findview
        //핸들러 세팅을 마지막에 해줘야 에러가 안난다. 중요!!!!
        //네이버 로그인 버튼을 찾고 핸들러를 세팅해준다.
        mOAuthLoginButton = findViewById(R.id.buttonOAuthLoginImg);//네이버 로그인 버튼
        ed_LoginId = findViewById(R.id.ed_LoginId);//사용자가 입력한 id
        ed_LoginPw = findViewById(R.id.ed_LoginPw);//사용자가 입력한 pw
        btn_Login = findViewById(R.id.btn_Login);//로그인 버튼
        btn_CreateAccount = findViewById(R.id.btn_create_account);//회원가입버튼
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Login://로그인 버튼 => 메인화면 이동
                String input_Id = String.valueOf(ed_LoginId.getText());//입력한 id
                String input_Pw = String.valueOf(ed_LoginPw.getText());//입력한 pw

                try {//로그인 검사 => 성공시 => true 반환
                    LoginCheck = Login_Check(input_Id, input_Pw, getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (LoginCheck) {//로그인에 성공한 경우 => LoginCheck = true
                    Toast.makeText(this, "로그인 성공.", Toast.LENGTH_SHORT).show();
                    //메인화면으로 이동
                    Myapplication.UserId = input_Id;//사용자 아이디 변수에 저장
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {//로그인 실패
                    if (input_Id.equals("") && input_Pw.equals("")) {//아이디 패스워드 둘다 입력하지 않은 경우
                        Toast.makeText(this, "아이디와 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else {//아이디 비밀번호가 틀린 경우
                        Toast.makeText(this, "아이디 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btn_create_account://회원가입 버튼
                SharedClass.remove_key(getApplicationContext(), "37991110", SharedClass.PREFERENCES_Name_UserData);
                Intent intent2 = new Intent(getApplicationContext(), Create_User_Account_Activity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

    public boolean Login_Check(String id_key, String pw, Context context) throws JSONException {
        boolean LoginCheck = false;

        if (!id_key.equals("") && !pw.equals("")) {//아이디 패스워드 중 하나라도 null이 아닌 경우
            //입력한 id를 키 값으로 쉐어드에 저장된 UserData list를 불러오기
            list_DatauserData = SharedClass.get_List_UserData_Json(context, id_key);
            if (list_DatauserData.size() != 0) {//리스트의 값이 null아닌 경우.
                //쉐어드에 저장된 UserData의 id, pw와 입력한 id, pw가 일치하는지 검사하는 if문
                //num_UserData =0 => UserData를 저장한 List의 인덱스 번호.
                String Shared_Id = list_DatauserData.get(Myapplication.num_UserData).Id_User;//쉐어드에 저장된 Id
                String Shared_pw = list_DatauserData.get(Myapplication.num_UserData).Pw_User;//쉐어드에 저장된 pw

                Myapplication.UserNickname = list_DatauserData.get(Myapplication.num_UserData).nickname_User;//사용자 닉네임 변수에 저장

                if (id_key.equals(Shared_Id) && pw.equals(Shared_pw)) {//아이디 패스워드가 일치하는 경우
                    LoginCheck = true;//로그인 성공 => LoginCheck true
                }
            }
        }
        return LoginCheck;
    }


}