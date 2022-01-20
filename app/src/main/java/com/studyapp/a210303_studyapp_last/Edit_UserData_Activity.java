package com.studyapp.a210303_studyapp_last;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Edit_UserData_Activity extends AppCompatActivity implements View.OnClickListener {
    EditText ed_Userdata;//수정할 데이터 입력
    Button btn_edit;//수정 버튼
    ImageView iv_back;//뒤로가기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_data_activity);
        set_findView();
        set_ButtonListener();

        //1번 닉네임 수정
        if (Myapplication.editUserDate_Num == 1) {
            //수정해야할 닉네임 세팅
            ed_Userdata.setText(Myapplication.editUserData);
        }
        //2번 공부타입 수정
        if (Myapplication.editUserDate_Num == 2) {
            //수정해야할 공부타입 세팅
            ed_Userdata.setText(Myapplication.editUserData);
        }
    }

    //필요한 findview 세팅
    public void set_findView() {
        ed_Userdata = findViewById(R.id.ed_userData);
        btn_edit = findViewById(R.id.btn_edit_userData);
        iv_back = findViewById(R.id.iv_backicon_edit_userData);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_edit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_userData://수정 버튼
                //수정한 유저정보 변수에 입력
                Myapplication.editUserData = String.valueOf(ed_Userdata.getText());
                Myapplication.editUserDate_Num = 0;//수정 구분 번호 초기화
                setResult(RESULT_OK);
                finish();
                break;

            case R.id.iv_backicon_edit_userData://뒤로가기 버튼
                finish();
                break;
        }
    }


}