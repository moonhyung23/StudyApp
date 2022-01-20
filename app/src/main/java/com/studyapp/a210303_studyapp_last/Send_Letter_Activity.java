package com.studyapp.a210303_studyapp_last;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Send_Letter_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_add_letter;//편지 작성
    EditText ed_Letter;//작성한 모집 참여 편지
    ImageView iv_backicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_letter_activity);
        set_findView();
        set_ButtonListener();
    }

    //필요한 findview 세팅
    public void set_findView() {
        btn_add_letter = findViewById(R.id.btn_add_letter);
        iv_backicon = findViewById(R.id.iv_backicon_letter);
        ed_Letter = findViewById(R.id.ed_letter);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_add_letter.setOnClickListener(this);
        iv_backicon.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_letter://공부 친구 신청 버튼
                //Create_StudyRoom_check == 1번 일때만 모집글 작성 가능
                Myapplication.Create_StudyRoom_check = 1;
                Myapplication.User_Letter = String.valueOf(ed_Letter.getText());
                setResult(RESULT_OK);
                finish();
                break;

            case R.id.iv_backicon_letter://뒤로가기 버튼
                Myapplication.Create_StudyRoom_check = 0;
                //이전에 적힌 값들이 입력되서 데이터를 입력하지 않아도 데이터를 저장하는 오류 방지.
                Myapplication.User_Letter = ""; //뒤로가는 경우 무조건 값을 "" 값으로 한다.
                setResult(RESULT_OK);
                finish();
                break;
        }
    }


}
