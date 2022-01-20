package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class Menu_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_backIcon;//뒤로가기
    ImageView edit_NickName;//닉네임 수정
    ImageView edit_studyType;//카테고리 수정
    ImageView iv_profile_option;//카테고리 수정
    //    ImageView edit_Content;//상태메시지
    Button btn_Logout;//로그 아웃
    Button btn_Remove_Account;//회원 탈퇴
    Button btn_edit_profile;//프로필사진 수정
    TextView tv_nickname, tv_studyType;

    ArrayList<Data_user> List_UserData;
    String str_uri;
    Uri i;//프로필사진 uri;
    String str_profile_Uri = "";//uri을 String으로 형변환
    //수정데이터를 구분하는 변수
    private final int GET_IMAGE_in_Galary = 202;//갤러리
    private final int Edit_Nickname_code = 203;//닉네임수정
    private final int Edit_StudyType_code = 204;//공부타입 수정

    String nickname_menu = "";
    String studyType_menu = "";
    Data_user data_user;
    ArrayList<Data_Measure_StudyTime> List_measure;//전체 공부시간 통계 리스트
    ArrayList<Data_Message> List_Message;//채팅 내역 리스트
    ArrayList<Data_StudyRoom_Infor> List_StudyFriend;//공부친구 리스트
    ArrayList<Data_StudyRoom_Infor> List_receive;//내가 받은 공부친구 신청목록 리스트
    ArrayList<Data_StudyRoom_Infor> List_studyRoom_infor;//공부친구 모집글 리스트

    Map<String, ?> map;
    String str_roomNum = "";
    String str_joinerId = "";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate_Menu", "확인");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        set_findView();
        set_ButtonListener();


        //쉐어드에 사용자 정보 리스트를 불러온다.
        try {
            List_UserData = SharedClass.get_List_UserData_Json(getApplicationContext(), Myapplication.UserId);
        } catch (JSONException e) {
        }
        //유저 정보 리스트에서 사용할 데이터들을 변수에 저장한다.
        if (List_UserData != null) {
            nickname_menu = List_UserData.get(0).nickname_User;
            studyType_menu = List_UserData.get(0).studyType;
            //사용자 정보 리스트에 있는 유저 정보데이터를 뷰에 입력한다.
            //String으로 저장된 프로필사진 리스트에서 uri를 가져온다.
            str_uri = List_UserData.get(0).iv_profile_User;
            //String => uri 변환
            i = Uri.parse(str_uri);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume_Menu", "확인");
        //onCreate에서 가져온 변수들을 onResume에서 입력한다.
        tv_nickname.setText(nickname_menu);//닉네임
        tv_studyType.setText(studyType_menu);//하고계신 공부
        //glide 사용해서 uri넣어서 프로필 이미지 로딩
        Glide.with(getApplicationContext()).load(i).into(iv_profile_option);//이미지 로딩

        //프로필 사진 uri를 쉐어드에 저장할 수 있게 String으로 변환
        str_profile_Uri = String.valueOf(i);
    }

    @Override
    protected void onStop() {
        Log.d("onStop_Menu", "확인");
        super.onStop();
        //유저정보 리스트에 데이터가 있을 때만 저장.
        if (List_UserData.size() != 0) {
            data_user = List_UserData.get(0);
            //onResume에서 수정한 데이터들을 다시 유저정보리스트에 저장한다.
            //수정한 데이터 : 1)프로필 사진 2)하고계신 공부 3)닉네임
            List_UserData.set(0, new Data_user(data_user.Id_User, data_user.Pw_User, str_profile_Uri,
                    nickname_menu, data_user.Apply_letter_User, data_user.All_Studyhour, studyType_menu));
            //쉐어드에서 가져온 유저정보 리스트를 다시 쉐어드에 저장
            try {
                SharedClass.Save_List_UserData_json(getApplicationContext(), Myapplication.UserId, List_UserData);
            } catch (JSONException e) {
            }
        }
    }

    //필요한 findview 세팅
    public void set_findView() {
        iv_backIcon = findViewById(R.id.iv_backicon_Menu);//뒤로가기
        tv_nickname = findViewById(R.id.tv_nickname_option);//닉네임
        tv_studyType = findViewById(R.id.tv_studyType_option);//하고있는 공부
        edit_NickName = findViewById(R.id.iv_edit_nickname);//닉네임 수정
        edit_studyType = findViewById(R.id.iv_edit_studyType);//공부 카테고리 수정
        btn_Logout = findViewById(R.id.btn_Logout_Account);//로그아웃
        btn_Remove_Account = findViewById(R.id.btn_remove_Account);//계정 삭제
        btn_edit_profile = findViewById(R.id.btn_profile_menu);//프로필사진 수정
        iv_profile_option = findViewById(R.id.iv_profile_menu);//프로필사진
//        edit_Content = findViewById(R.id.iv_edit_Content);//상태메시지
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        iv_backIcon.setOnClickListener(this);//
        edit_NickName.setOnClickListener(this);
        edit_studyType.setOnClickListener(this);
        btn_Logout.setOnClickListener(this);
        btn_Remove_Account.setOnClickListener(this);
        btn_edit_profile.setOnClickListener(this);
//        edit_Content.setOnClickListener(this);
    }

    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_profile_menu://프로필 사진 수정 버튼
                Intent intent = new Intent(Intent.ACTION_PICK);//갤러리
                //얻어올 데이터 세팅(이미지 uri)
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_IMAGE_in_Galary);//이미지 데이터 얻어오기
                break;

            case R.id.iv_edit_nickname://닉네임 수정
                Intent intent2 = new Intent(getApplicationContext(), Edit_UserData_Activity.class);
                Myapplication.editUserData = nickname_menu;//수정할 닉네임 변수에 저장
                Myapplication.editUserDate_Num = 1;//닉네임 수정 (1번)
                startActivityForResult(intent2, Edit_Nickname_code);
                break;

            case R.id.iv_edit_studyType://공부타입 수정
                Intent intent3 = new Intent(getApplicationContext(), Edit_UserData_Activity.class);
                Myapplication.editUserData = studyType_menu;//수정할 공부타입 변수에 저장
                Myapplication.editUserDate_Num = 2;// 공부타입 수정 (2번)
                startActivityForResult(intent3, Edit_StudyType_code);
                break;

            case R.id.btn_Logout_Account://로그아웃
                Intent intent4 = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent4);
                Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.btn_remove_Account://회원 탈퇴
                //1)내 유저정보 리스트를 쉐어드에서 삭제
                //key: 내 아이디
                SharedClass.remove_key(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_Name_UserData);
                List_UserData.clear();

                //2)내 공무목표 리스트 쉐어드에서 삭제
                //key: 내 아이디
                SharedClass.remove_key(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_StudyGoal_Data);

                //3)내가보낸 공부 친구 신청 리스트 삭제
                //key: 내 아이디
                SharedClass.remove_key(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_Apply_UserId_StudyRoom_Data);

                //4)내 전체공부시간 통계리스트 쉐어드에서 삭제
                //key: 날짜
                //공부통계리스트의 전체 키를 불러온다.
                map = SharedClass.getAll_Sharedkey(getApplicationContext(), SharedClass.PREFERENCES_Measure_StudyTime);
                //저장된 키의 갯수만큼 반복한다.
                for (Map.Entry<String, ?> allKey : map.entrySet()) {
                    try {
                        //전체 공부시간 통계리스트를 날짜별로 불러온다.
                        List_measure = SharedClass.get_List_MeasureTime_json(getApplicationContext(), allKey.getKey(), SharedClass.PREFERENCES_Measure_StudyTime);
                    } catch (JSONException e) {
                    }

                    //전체 공부시간 리스트 비교
                    for (int i = 0; i < List_measure.size(); i++) {
                        //내 아이디로 만들어진 공부시간 기록 삭제
                        if (List_measure.get(i).made_id.equals(Myapplication.UserId)) {
                            List_measure.remove(i);
                        }
                    }

                    //쉐어드에서 가져온 전체 공부시간 통계리스트 저장
                    //쉐어드에 저장된 키의 갯수만큼 쉐어드에서 다시 저장.
                    try {
                        SharedClass.Save_List_MeasureTime_json(getApplicationContext(), allKey.getKey(), List_measure, SharedClass.PREFERENCES_Measure_StudyTime);
                    } catch (JSONException e) {
                    }
                }

                //5)내 채팅내역 리스트 쉐어드에서 삭제
                //key: 방번호
                //쉐어드에서 내 공부친구 리스트를 불러온다.
                try {
                    List_StudyFriend = SharedClass.get_List_MyStudyFriend_Infor_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_MyStudyFriend_Data);
                } catch (JSONException e) {
                }
                //내 공부친구 리스트에서 내아이디로  저장된 공부친구 번호를 찾아서
                //공부친구 1명이어서 0번째 인덱스를 찾으면 가능
                //String변수에 담는다.
                if (List_StudyFriend.size() != 0) {
                    str_roomNum = String.valueOf(List_StudyFriend.get(0).roomNumber);
                }
                //String 으로 담은 변수를 키 값으로 쉐어드에서 채팅내역 리스트를 불러온다.
                try {
                    List_Message = SharedClass.get_List_message_json(getApplicationContext(), str_roomNum, SharedClass.PREFERENCES_Message);
                } catch (JSONException e) {
                }
                //불러온 내 채팅내역 리스트 초기화 한다.
                List_Message.clear();

                //쉐어드에서 가져온 채팅내역 리스트 저장
                try {
                    SharedClass.Save_List_message_json(getApplicationContext(), str_roomNum, List_Message, SharedClass.PREFERENCES_Message);
                } catch (JSONException e) {
                }

                //6)내가 받은 공부 친구 신청 리스트 삭제
                //key: 방번호
                //내아이디가 키 값인 공부친구 리스트를 쉐어드에서 불러온다.
                try {
                    List_receive = SharedClass.get_List_StudyRoom_Joiner_json(getApplicationContext(), str_roomNum, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
                } catch (JSONException e) {
                }

                //받은 모집글 리스트 비교
                for (int i = 0; i < List_receive.size(); i++) {
                    //내 아이디로 저장된 모집글 리스트를 찾는다.
                    if (List_receive.get(i).room_Maker_Id.equals(Myapplication.UserId)) {
                        //내 아이디로 만들어진 모집글 삭제
                        List_receive.remove(i);
                    }
                }

                //쉐어드에서 가져온 내가 받은 공부친구 신청 글 리스트 다시 저장
                try {
                    SharedClass.Save_List_StudyRoom_Joiner_json(getApplicationContext(), str_roomNum, List_receive, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
                } catch (JSONException e) {
                }

                //7)내 공부친구 리스트 쉐어드에서 삭제

                //상대방의 id를 변수에 저장한다.
                if(List_StudyFriend.size() != 0){
                    str_joinerId = String.valueOf(List_StudyFriend.get(0).room_Joiner_Id);
                }

                //상대방과 나 둘다 친구삭제
                //key: 내 아이디
                SharedClass.remove_key(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_MyStudyFriend_Data);
                //key: 상대방 아이디
                SharedClass.remove_key(getApplicationContext(), str_joinerId, SharedClass.PREFERENCES_MyStudyFriend_Data);

                //8)내 공부친구 모집글 리스트 쉐어드에서 삭제
                //key: 리스트키 (쉐어드)
                //전체 모집글 정볼를 불러온다.
                try {
                    List_studyRoom_infor = SharedClass.get_List_StudyRoom_Infor_json(getApplicationContext(), SharedClass.ListKey_StudyRoomDATA, SharedClass.PREFERENCES_Name_Room_infor);
                } catch (JSONException e) {
                }
                //모집글 정보를 비교한다.
                for (int i = 0; i < List_studyRoom_infor.size(); i++) {
                    //방 만든 사람의 id가  내 id인 것만 갖고온다.
                    if (List_studyRoom_infor.get(i).room_Maker_Id.equals(Myapplication.UserId)) {
                        //내 id로 만들어진 방을 삭제한다.
                        List_studyRoom_infor.remove(i);
                    }
                }

                //공부친구 모집글 리스트 쉐어드에 저장
                try {
                    SharedClass.Save_List_StudyRoom_Infor_json(getApplicationContext(), SharedClass.ListKey_StudyRoomDATA, List_studyRoom_infor, SharedClass.PREFERENCES_Name_Room_infor);
                } catch (JSONException e) {
                }

                Toast.makeText(this, "아이디가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                //내 유저정보리스트 데이터 삭제
                //삭제안하면 onStop에서 다시 저장되기 때문

                //로그인 엑티비티로 이동
                Intent intent5 = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent5);
                finish();
                break;

            case R.id.iv_backicon_Menu://뒤로가기
                Intent intent6 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent6);
                finish();
                break;

        }
    }

    //갤러리에서 데이터 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_IMAGE_in_Galary://프로필 사진 수정
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {

                    //갤러리에서 선택한 이미지의 uri데이터를 받아온다.
                    i = data.getData();
                    //받아온 uri 데이터를 onResume에서 입력한다.
                }
                break;

            case Edit_Nickname_code://닉네임 수정
                if (resultCode == RESULT_OK) {
                    //수정한 닉네임을 텍스트뷰에 입력
                    nickname_menu = Myapplication.editUserData;
                }
                break;

            case Edit_StudyType_code://공부 타입 수정
                if (resultCode == RESULT_OK) {
                    //수정한 공부타입을 텍스트뷰에 입력
                    studyType_menu = Myapplication.editUserData;
                }
                break;
        }
    }
}