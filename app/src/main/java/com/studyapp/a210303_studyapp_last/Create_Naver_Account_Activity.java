package com.studyapp.a210303_studyapp_last;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;

import java.util.ArrayList;

public class Create_Naver_Account_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_join, btn_cancel, btn_gallery;
    EditText ed_nickname, ed_studyType;
    ImageView iv_profile;
    String str_profile_Uri = "";//쉐어드에 저장할 String으로 형변환 시킨 프로필 이미지 uri
    private final int GET_IMAGE_in_Galary = 202;
    ArrayList<Data_user> List_User;//유저 정보를 담을 ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_naver_account_activity);
        set_findView();
        set_ButtonListener();
        List_User = new ArrayList<>();//유저 데이터 리스트
        ed_studyType.requestFocus();//하고있는 공부 포커스

        //키보드 화면 뛰우기
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    //필요한 findview 세팅
    public void set_findView() {
        btn_join = findViewById(R.id.btn_UserJoin_NaverAccount);
        btn_cancel = findViewById(R.id.btn_cancel_NaverAccount);
        btn_gallery = findViewById(R.id.btn_gallery_NaverAccount);
        ed_nickname = findViewById(R.id.ed_nickname_NaverAccount);
        ed_studyType = findViewById(R.id.ed_studyType_NaverAccount);
        iv_profile = findViewById(R.id.iv_profile_NaverAccount);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_join.setOnClickListener(this);//
        btn_cancel.setOnClickListener(this);
        btn_gallery.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_UserJoin_NaverAccount://가입버튼
//                로그인 정보 입력검사
                //하고계신 공부 입력 검사
                if (String.valueOf(ed_studyType.getText()).length() == 0) {
                    Toast.makeText(this, "하고계신공부를 입력하세요", Toast.LENGTH_SHORT).show();
                    ed_studyType.requestFocus();
                    return;
                }

                //닉네임 입력 검사
                if (String.valueOf(ed_nickname.getText()).length() == 0) {
                    Toast.makeText(this, "낙네임을 입력하세요", Toast.LENGTH_SHORT).show();
                    ed_nickname.requestFocus();
                    return;
                }

                //입력검사 통과
                //리스트에 회원 정보 데이터 저장
                //입력한 닉네임을 아이디로 저장한다.

                //입력한 비밀번호
                String pw_UserData = "NaverLogin_key";
                //입력한 닉네임
                String nickname_UserData = String.valueOf(ed_nickname.getText());
                //입력한 공부타입
                String studyType = String.valueOf(ed_studyType.getText());
                //입력한 아이디는 네이버로 회원가입시 나오는 id_key를 변수에 담은 값을 저장
                List_User.add(new Data_user(Myapplication.Naver_Login_Id_Key, pw_UserData,
                        str_profile_Uri, nickname_UserData, "", "00 : 00 : 00", studyType));

                //쉐어드에 유저정보 리스트 저장
                try {
                    SharedClass.Save_List_UserData_json(getApplicationContext(), Myapplication.Naver_Login_Id_Key, List_User);
                } catch (JSONException e) {
                }

                Toast.makeText(this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                finish();
                break;


            case R.id.btn_cancel_NaverAccount://취소버튼
                Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.btn_gallery_NaverAccount://갤러리 버튼
                Intent intent3 = new Intent(Intent.ACTION_PICK);//갤러리
                //얻어올 데이터 세팅(이미지 데이터)
                intent3.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent3, GET_IMAGE_in_Galary);//이미지 데이터 얻어오기
                break;
        }
    }


    @Override//엑티비티에서 데이터 가져오기.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //갤러리에서 이미지 받아오기
        Uri selectedImageUri;//이미지 uri
        //이미지 동그랗게 하기.
        MultiTransformation option2 = new MultiTransformation(new CenterCrop(), new RoundedCorners(8));
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) { //갤러리에서 이미지 데이터 가져오기
            switch (requestCode) {
                case GET_IMAGE_in_Galary:
                    selectedImageUri = data.getData();//선택한 이미지의 uri를 받아온다.
                    //Glide를 통해서 이미지뷰에 이미지 데이터 넣기
                    //메서드 인자값 : 이미지 uri, 이미지옵션(동그랗게 하기), 이미지뷰
                    Glide.with(getApplicationContext()).load(selectedImageUri).apply(RequestOptions.bitmapTransform(option2)).into(iv_profile);
                    str_profile_Uri = String.valueOf(selectedImageUri);//Glide에서 사용한 image uri를 String으로 형변환 후에 String 변수에 담기.
                    break;
            }
        }
    }


}
