package com.studyapp.a210303_studyapp_last;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Create_StudyGoal_Activity extends AppCompatActivity implements View.OnClickListener {
    EditText ed_StudyGoal;
    ImageView iv_BackIcon;
    Button btn_add;

    @Override
    //다이얼로그로 사용할 엑티비티
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//엑티비티 타이틀 제목 없애기
        setContentView(R.layout.create_study_goal_activity);
        ed_StudyGoal = findViewById(R.id.ed_Create_Study_Goal);
        set_findView();//findview
        set_ButtonListener();//buttonListener


        //2번 공부 목표 편집
        //편집 시 이전에 작성했던 텍스트 보여주기.
        if(Myapplication.num_StudyGoal_edit_add == 2){
            ed_StudyGoal.setText(Myapplication.StudyGoal);
        }
    }

    //필요한 findview 세팅
    public void set_findView() {
        btn_add = findViewById(R.id.btn_add_Studygoal);
        ed_StudyGoal = findViewById(R.id.ed_Create_Study_Goal);
        iv_BackIcon = findViewById(R.id.iv_backicon_Create_Study_goal);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {

        btn_add.setOnClickListener(this);//추가
        iv_BackIcon.setOnClickListener(this);//뒤로가기 아이콘
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backicon_Create_Study_goal://뒤로가기
                finish();
                break;

            case R.id.btn_add_Studygoal://작성
                //editText에서 적은 공부 목표 변수에 담기.
                Myapplication.StudyGoal = String.valueOf(ed_StudyGoal.getText());
                setResult(RESULT_OK);//이동.
                finish();
                break;
        }
    }


}
