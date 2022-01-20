package com.studyapp.a210303_studyapp_last;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Create_Room_StudyFriend_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_Create_StudyRoom;
    ImageView iv_backicon_StudyRoom;
    EditText ed_RoomName, ed_Roomcontent;

    final static String List_Key_Room_StudyFriend = "Key_StudyFriend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_room__study_friend_activity);
        set_findView();
        set_ButtonListener();



        //편집시 이전에 썻던 텍스트를 가져온다.
        if (Myapplication.num_StudyMyRoom_add_edit_remove == 3) {//3.편집
            ed_RoomName.setText(Myapplication.RoomName);//이전에 썻던 모집 글 제목 세팅
            ed_Roomcontent.setText(Myapplication.RoomContent);//이전에 썻던 모집 글 내용 세팅
        }
    }

    //필요한 findview 세팅
    public void set_findView() {
        btn_Create_StudyRoom = findViewById(R.id.btn_Add_CreateRoom);//모집글 작성, 수정
        iv_backicon_StudyRoom = findViewById(R.id.iv_backicon_CreateRoom);//뒤로가기 아이콘
        ed_RoomName = findViewById(R.id.ed_RoomName_CreateRoom);//모집 글 제목
        ed_Roomcontent = findViewById(R.id.ed_content_CreateRoom);//모집 글 내용
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_Create_StudyRoom.setOnClickListener(this);
        iv_backicon_StudyRoom.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_Add_CreateRoom:
                //모임 글 작성
                //1번: 작성 2번: 삭제 3번: 편집
                /*모든 모임 글 엑티비티에서 모임 글 작성 이미지 아이콘을 눌러서 왔음*/
                if (Myapplication.num_StudyMyRoom_add_edit_remove == 1) {//1.작성
                    //모집 글 작성 버튼을 누르면 사용자가 입력한 editText를
                    //staic 변수에 저장하고 처음 엑티비티로 돌아온다.
                    Myapplication.RoomName = String.valueOf(ed_RoomName.getText());//모집 글 제 제목
                    Myapplication.RoomContent = String.valueOf(ed_Roomcontent.getText());//모집 글 내용
                }
                //버튼을 누르면 전에 썻던 텍스트를 바꾼다.
                else if (Myapplication.num_StudyMyRoom_add_edit_remove == 3) {//3.편집
                    Myapplication.RoomName = String.valueOf(ed_RoomName.getText());//모집 글 제 제목
                    Myapplication.RoomContent = String.valueOf(ed_Roomcontent.getText());//모집 글 내용
                }
                setResult(RESULT_OK);//이동.
                finish();
                break;

            case R.id.iv_backicon_CreateRoom://뒤로가기
                setResult(RESULT_OK);//이동.
                finish();
                break;
        }
    }
}