package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class Send_Application_StudyFriend_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_backicon;
    LinearLayoutManager layoutManager_other_room;
    RecyclerView rv_Apply_other_room;
    Rv_Adapter_Send_Application rv_adapter_sendApplication;
    ArrayList<Data_StudyRoom_Infor> List_My_StudyRoom_Number;
    ArrayList<Data_StudyRoom_Infor> List_Receive_StudyRoomData;
    ArrayList<Data_StudyRoom_Infor> List_All_StudyRoomData;
    ArrayList<Data_StudyRoom_Infor> List_My_StudyRoom_Number_recycler;
    Data_StudyRoom_Infor data_All_studyRoom;
    Data_StudyRoom_Infor data_My_studyRoom_number;
    Button btn_Receive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_application_studyfriend_activity);
        set_findView();
        set_ButtonListener();

        //방 번호를 저장하고 있는 리스트를 쉐어드에서 불러온다
        try {
            List_My_StudyRoom_Number = SharedClass.get_List_Apply_StudyRoom_Infor_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_Apply_UserId_StudyRoom_Data);
            Log.d("onCreate_Apply_List", "방 번호 리스트 불러오기 성공");
        } catch (JSONException e) {
            Log.d("onCreate_Apply_List", "방 번호 리스트 불러오기 실패");
        }
        //전체 방 정보 리스트를 쉐어드에서 불러온다.
        try {
            List_All_StudyRoomData = SharedClass.get_List_StudyRoom_Infor_json(getApplicationContext(), SharedClass.ListKey_StudyRoomDATA, SharedClass.PREFERENCES_Name_Room_infor);
            Log.d("onCreate_Apply_List", "전체 방 정보 리스트 불러오기 성공");
        } catch (JSONException e) {
            Log.d("onCreate_Apply_List", "전체 방 정보 리스트 불러오기 실패");
        }


        //리사이클러뷰 세팅
        rv_Apply_other_room = findViewById(R.id.rv_apply_other_room);//리사이클러뷰 findView
        layoutManager_other_room = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_adapter_sendApplication = new Rv_Adapter_Send_Application(getApplicationContext());
        rv_Apply_other_room.setLayoutManager(layoutManager_other_room);//linearlayout 세팅
        rv_Apply_other_room.setAdapter(rv_adapter_sendApplication);//adapter 세팅


        //쉐어드에 저장된 데이터 리스트의 값이 null 일때는 불러오지 않는다.
        //1)전체 모임 방 정보와 내가 가입한 모임 방 정보가 둘다 null값이 아닐 경우
        if (List_All_StudyRoomData != null && List_My_StudyRoom_Number != null) {

            /*1)리사이클러뷰에 아이템뷰에 내가 보낸 모집글 데이터를 입력한다.*/
            //1) 2중 for문을 돌려서 전체 방 정보중에 내 방 번호리스트에 있는 객체만 불러온다.
            for (int i = 0; i < List_All_StudyRoomData.size(); i++) {
                data_All_studyRoom = List_All_StudyRoomData.get(i);
                for (int j = 0; j < List_My_StudyRoom_Number.size(); j++) {
                    data_My_studyRoom_number = List_My_StudyRoom_Number.get(j);
                    //2) 전체 모집 글 번호와 내가 가입신청한 모집글 번호와 같은 객체를 찾는다.
                    //ex) i0 ==> j0 j1 j2 j3 j4 비교  i1 ==> j0 j1 j2 j3 j4 비교
                    if (data_All_studyRoom.roomNumber == data_My_studyRoom_number.roomNumber) {
                        //3)모집글 번호가 같은 객체를 리사이클러뷰의 아이템뷰에 입력한다.
                        rv_adapter_sendApplication.addItem(new Data_StudyRoom_Infor(data_All_studyRoom.roomName,
                                data_All_studyRoom.roomContent, data_All_studyRoom.room_Maker_Id,
                                data_All_studyRoom.roomNumber, data_All_studyRoom.iv_profile_uri,
                                data_All_studyRoom.tv_nickname, data_All_studyRoom.studyType, data_All_studyRoom.room_letter));
                        rv_adapter_sendApplication.notifyDataSetChanged();
                    }
                }
            }
        }


        //삭제 버튼 클릭 이벤트
        rv_adapter_sendApplication.setOnItemClickListener(new Rv_Adapter_Send_Application.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //삭제 버튼을 누른 아이템의 방 번호를 갖고온다.
                int roomnum = rv_adapter_sendApplication.getItem(position).roomNumber;
                String RoomNum_key = String.valueOf(roomnum);
                //내가 받은 모집 참여글 리스트를 불러온다.
                try {
                    List_Receive_StudyRoomData = SharedClass.get_List_StudyRoom_Joiner_json(getApplicationContext(), RoomNum_key, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //받은 모집글 리스트에서
                //보낸 사람의 아이디(내 아이디)와 맞는 모집글을 삭제한다.
                for (int i = 0; i < List_Receive_StudyRoomData.size(); i++) {
                    //받은 모집글 리스트에서 갖고온 참여한 사람의 ID == 모집 글 참여를 보낸 사람의 아이디.
                    if (List_Receive_StudyRoomData.get(i).room_Joiner_Id.equals(Myapplication.UserId)) {
                        List_Receive_StudyRoomData.remove(i);//모집글 참여자 삭제.
                    }
                }

                //받은 모집글 리스트를 쉐어드에 다시 저장
                try {
                    SharedClass.Save_List_StudyRoom_Joiner_json(getApplicationContext(), RoomNum_key, List_Receive_StudyRoomData, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //***클릭한 아이템 삭제
                //2)삭제 버튼을 누른 아이템뷰의 포지션을 받아와서
                //2-1)선택한 리사이클러뷰 아이템뷰를 삭제한다.
                rv_adapter_sendApplication.removeItem(position);
                rv_adapter_sendApplication.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        /*리스트 저장 흐름도
         * 1) 쉐어드에서 보낸 모임 신청글 리스트를 불러온다
         * 2) 불러온 모임 신청글 리스트를 리사이클러뷰 아이템뷰 리스트에 입력한다.
         * 3) 리사이클러뷰 아이템뷰 리스트를 쉐어드에 저장한다.
         * */
        //onCreate에서 저장하면 리사이클러뷰에 불러왔던 리스트가 처음부터 저장된다.
        //3)아이템을 삭제했기 때문에 변경된 사항을
        //3-1)쉐어드에 저장한다.
        List_My_StudyRoom_Number_recycler = rv_adapter_sendApplication.getList();//리사이클러뷰 아이템뷰 리스트.

        try {
            SharedClass.Save_List_Apply_StudyRoom_Infor_json(getApplicationContext(),
                    Myapplication.UserId, List_My_StudyRoom_Number_recycler, SharedClass.PREFERENCES_Apply_UserId_StudyRoom_Data);
        } catch (JSONException e) {
        }


    }

    //필요한 findview 세팅
    public void set_findView() {
        btn_Receive = findViewById(R.id.btn_Receive_application);
        iv_backicon = findViewById(R.id.iv_backicon_ApplyList);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        iv_backicon.setOnClickListener(this);//
        btn_Receive.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Receive_application://내가 받은 모임 신청서 이동 버튼
                Intent intent = new Intent(getApplicationContext(), Receive_Application_Activity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.iv_backicon_ApplyList://뒤로가기
                Intent intent2 = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }



}
