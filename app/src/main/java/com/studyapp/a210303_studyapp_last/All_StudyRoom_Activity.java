package com.studyapp.a210303_studyapp_last;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class All_StudyRoom_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_myStudyRoom;
    int RoomNumber;//방번호

    ArrayList<Data_StudyRoom_Infor> List_My_studyFriend_infor;//나의 공부친구 목록
    ArrayList<Data_user> List_userdata;//사용자 정보 리스트

    int itemSize;//리스트에 저장된 아이템의 갯수
    ImageView iv_Add_StudyRoom;//모집 글쓰기 이미지 아이콘;
    ImageView iv_backIcon_StudyRoom;//뒤로가기 아이콘;

    RecyclerView rv_item_FindFriend_infor;//리사이클러뷰
    Rv_Adapter_StudyRoom rv_adapter_Studyroom;//리사이클러뷰 어댑터
    LinearLayoutManager linearLayoutManager_StudyRoom;
    ArrayList<Data_StudyRoom_Infor> List_StudyRoom_Infor_Shared;

    Data_StudyRoom_Infor data_studyRoom_infor;//그룹 데이터
    ArrayList<Data_StudyRoom_Infor> List_StudyRoom_Recycler;

    public static final int REQUEST_CODE = 100;
    String Listkey;

    //공부를 친구를 찾는 엑티비티
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_studyroom_activity);
        Listkey = SharedClass.ListKey_StudyRoomDATA;//쉐어드에 저장한 데이터리스트의 키
        set_findView();
        set_ButtonListener();
        //리사이클러뷰 세팅
        linearLayoutManager_StudyRoom = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_adapter_Studyroom = new Rv_Adapter_StudyRoom(getApplicationContext());
        rv_item_FindFriend_infor.setLayoutManager(linearLayoutManager_StudyRoom);//linearlayout 세팅
        rv_item_FindFriend_infor.setAdapter(rv_adapter_Studyroom);//adapter 세팅
        //리사이클러뷰 아이템 리스트 불러오기
        List_StudyRoom_Recycler = rv_adapter_Studyroom.getList();

        //사용자 정보 리스트 쉐어드에서 불러오기
        try {
            List_userdata = SharedClass.get_List_UserData_Json(getApplicationContext(), Myapplication.UserId);
        } catch (JSONException e) {
        }

        try {//쉐어드에서 모임방 데이터 리스트 불러오기
            List_StudyRoom_Infor_Shared = SharedClass.get_List_StudyRoom_Infor_json(getApplicationContext(), Listkey, SharedClass.PREFERENCES_Name_Room_infor);
            System.out.println("json객체 불러오기 성공_All_StudyRoom");
        } catch (JSONException e) {
            System.out.println("json객체 불러오기 실패_All_StudyRoom");
        }


        //리사이클러뷰의 아이템뷰에 데이터 추가하기
        //쉐어드에 저장된 데이터 리스트에 데이터가 있을 때만.
        if (List_StudyRoom_Infor_Shared.size() != 0) {
            for (int i = 0; i < List_StudyRoom_Infor_Shared.size(); i++) {
                data_studyRoom_infor = List_StudyRoom_Infor_Shared.get(i);//쉐어드 데이터 객체 생성

                //데이터 추가.
                rv_adapter_Studyroom.addItem(new Data_StudyRoom_Infor(data_studyRoom_infor.roomName, data_studyRoom_infor.roomContent
                        , data_studyRoom_infor.room_Maker_Id, data_studyRoom_infor.roomNumber, data_studyRoom_infor.iv_profile_uri,
                        data_studyRoom_infor.tv_nickname, data_studyRoom_infor.studyType));
                rv_adapter_Studyroom.notifyDataSetChanged();
            }
        }

        try { /*쉐어드에 모임 글 정보 저장*/
            SharedClass.Save_List_StudyRoom_Infor_json(getApplicationContext(), Listkey, List_StudyRoom_Recycler, SharedClass.PREFERENCES_Name_Room_infor);
            System.out.println("모임 글 정보 저장 완료_All_StudyRoom");
        } catch (JSONException e) {
            System.out.println("모임 글 정보 저장 실패_All_StudyRoom");
        }

        /* 나의 친구목록을 갖고온다.*/
        try {
            List_My_studyFriend_infor = SharedClass.get_List_MyStudyFriend_Infor_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_MyStudyFriend_Data);
        } catch (JSONException e) {
            e.printStackTrace();
        }


//리사이클러뷰 아이템 클릭 리스너
        rv_adapter_Studyroom.setOnItemClickListener(new Rv_Adapter_StudyRoom.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {//클릭이벤트
                //모임방 엑티비티로 이동
                //클릭한 아이템뷰의 객체를 갖고온다
                data_studyRoom_infor = List_StudyRoom_Recycler.get(position);
                Myapplication.ClickItemId = data_studyRoom_infor.room_Maker_Id;//클릭한 아이템의 Id
                Intent intent = new Intent(getApplicationContext(), Study_Room_infor_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //필요한 findview 세팅
    public void set_findView() {
        rv_item_FindFriend_infor = findViewById(R.id.rv_StudyRoom);//리사이클러뷰 findView
        btn_myStudyRoom = findViewById(R.id.btn_myStudyroom);//내가 쓴 모집글로 이동
        //뒤로가기 이미지
        iv_backIcon_StudyRoom = findViewById(R.id.iv_backicon_Find_StudyFriend);
        //모집 글 쓰기 이미지
        iv_Add_StudyRoom = findViewById(R.id.iv_Add_StudyRoom_StudyFriend);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_myStudyRoom.setOnClickListener(this);//내가 쓴 모집글 엑티비티로 이동
        iv_backIcon_StudyRoom.setOnClickListener(this);//뒤로가기
        iv_Add_StudyRoom.setOnClickListener(this);//모집 글 추가
    }

    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_Add_StudyRoom_StudyFriend://모임 글 작성으로 이동

                //공부친구가 있는 경우 작성 불가
                if (List_My_studyFriend_infor.size() != 0) {
                    return;
                }

                //모임 글 작성을 2개 작성한 경우 방지
                for (int i = 0; i < List_StudyRoom_Recycler.size(); i++) {
                    data_studyRoom_infor = List_StudyRoom_Recycler.get(i);
                    if (data_studyRoom_infor.room_Maker_Id.equals(Myapplication.UserId)) {
                        return;
                    }
                }
                //공부 친구가 없는 경우에만
                Intent intent = new Intent(getApplicationContext(), Create_Room_StudyFriend_Activity.class);
                startActivityForResult(intent, REQUEST_CODE);
                //num_StudyRoom 변수로 모집 글 작성, 수정을 구분한다.
                //1번: 작성  2번: 수정
                Myapplication.num_StudyMyRoom_add_edit_remove = 1;//1번 모집글 작성
                break;
            case R.id.btn_myStudyroom://내가 쓴 모집글 엑티비티 이동
                Intent intent2 = new Intent(getApplicationContext(), MyStudyRoom_Activity.class);
                startActivityForResult(intent2, REQUEST_CODE);
                finish();
                break;

            case R.id.iv_backicon_Find_StudyFriend://뒤로가기
                Intent intent3 = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent3);
                finish();
                break;
        }
    }

    @Override
    //다른 엑티비티에서 저장한 데이터를 갖고오는 메서드
    /*리사이클러뷰 아이템뷰에 데이터 추가*/
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Myapplication.num_StudyMyRoom_add_edit_remove == 1) {//1번 추가
            //엑티비티에서 갖고온 데이터가 있을 때만 실행
            if (requestCode == REQUEST_CODE && !Myapplication.RoomName.equals("")) {
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }


                //사용자가 로그인을 할 때 입력한 id를 계속 쉐어드에 저장하기 떄문에 id 변경 했을 때도 가능함.
                //Myapplication ID => 로그인할 때 입력한 ID
                /* !!! 리사이클러뷰의 아이템뷰에 추가한 모임 방 정보를 저장한다.*/

                //방번호를 쉐어드에서 갖고온 번호로 저장
                //list size로 저장하면 삭제시 번호가 중복된다.
                RoomNumber = SharedClass.getInt(getApplicationContext(), SharedClass.StudyRoomNum_key, SharedClass.PREFERENCES_RoomNum);


                //만든사람의 프로필사진과 닉네임 공부타입이 입력
                rv_adapter_Studyroom.addItem(new Data_StudyRoom_Infor(Myapplication.RoomName, Myapplication.RoomContent, Myapplication.UserId, RoomNumber,
                        List_userdata.get(0).iv_profile_User, List_userdata.get(0).nickname_User, List_userdata.get(0).studyType));
                rv_adapter_Studyroom.notifyDataSetChanged();

                //저장한 방번호를 쉐어드에 +1 증가 시켜서 저장
                SharedClass.saveInt(getApplicationContext(), SharedClass.StudyRoomNum_key, ++RoomNumber, SharedClass.PREFERENCES_RoomNum);


                /*쉐어드에 모임 글 정보 저장*/
                try {
                    SharedClass.Save_List_StudyRoom_Infor_json(getApplicationContext(), Listkey, List_StudyRoom_Recycler, SharedClass.PREFERENCES_Name_Room_infor);
                    System.out.println("모임 글 정보 저장 완료_All_StudyRoom");
                } catch (JSONException e) {
                    System.out.println("모임 글 정보 저장 실패_All_StudyRoom");
                }
                //쉐어드에 데이터 저장

            }
            //갖고온 데이터 입력
        }
    }
}