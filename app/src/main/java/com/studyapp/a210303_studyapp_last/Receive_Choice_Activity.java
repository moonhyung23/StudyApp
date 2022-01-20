package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.ArrayList;

/*상세보기 엑티비티*/
public class Receive_Choice_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_agree, btn_disagree;
    ImageView iv_backIcon;
    TextView tv_letter;
    int My_Item_RoomNum;//클릭한 아이템의 모집 글 번호
    String My_Item_RoomNum_key;//클릭한 아이템의 모집 글 번호
    ArrayList<Data_StudyRoom_Infor> List_All_StudyRoom_infor;//전체 모집글 리스트
    ArrayList<Data_StudyRoom_Infor> List_Sender_Application;//보낸 모집글 리스트
    ArrayList<Data_StudyRoom_Infor> List_Receiver_Application;//받은 모집글 리스트
    ArrayList<Data_StudyRoom_Infor> List_MyFriend_App_Me;//나의 친구 데이터 리스트
    ArrayList<Data_StudyRoom_Infor> List_MyFriend_App_You;//상대방의 친구 데이터 리스트
    ArrayList<Data_user> List_user_me;//내정보 리스트
    ArrayList<Data_user> List_user_you;//상대방 정보 리스트
    Data_StudyRoom_Infor data_Sender_Studyroom;
    Data_StudyRoom_Infor data_Receiver_Studyroom;
    Data_StudyRoom_Infor data_Receiver_Studyroom_My;
    String roomJoinerId;//모집 참여자 id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_detail_check_activity);
        set_findView();
        set_ButtonListener();

        //상대방이 보낸 편지.
        tv_letter.setText(Myapplication.intent_Letter);

        /*1)쉐어드에서 전체 모임 신청글 리스트 불러오기*/
        try {
            List_All_StudyRoom_infor = SharedClass.get_List_StudyRoom_Infor_json(getApplicationContext(), SharedClass.ListKey_StudyRoomDATA, SharedClass.PREFERENCES_Name_Room_infor);
        } catch (JSONException e) {
        }

        /* 2)쉐어드에서 보낸 사람의 모임 신청글 리스트 불러오기*/
        try {
            //key: 모집 신청서를 보낸 사람의 Id
            List_Sender_Application = SharedClass.get_List_Apply_StudyRoom_Infor_json(getApplicationContext(), Myapplication.Sender_Id, SharedClass.PREFERENCES_Apply_UserId_StudyRoom_Data);
        } catch (JSONException e) {
        }


        //1)쉐어드에 내가 받은 모집글 리스트의 key 값인 방번호를 갖고오기 위해서
        //2)전체 모집글에서 내가 만든 모집글을 for문을 돌려 갖고오고
        //3)내가 만든 모집글에서 방번호를 갖고온다.
        for (int i = 0; i < List_All_StudyRoom_infor.size(); i++) {
            //2-2)내 아이디로 만들어진 모집 글을 찾고 모집글의 방번호를 알아낸다.
            if (List_All_StudyRoom_infor.get(i).room_Maker_Id.equals(Myapplication.UserId)) {
                My_Item_RoomNum = List_All_StudyRoom_infor.get(i).roomNumber;//내 id로된 모집 글 방 번호

                //2-3)내 id로된 모집글의 방번호를 통해서 내가 받은 모집 글 리스트를 삭제한다.
                My_Item_RoomNum_key = String.valueOf(My_Item_RoomNum);//key로 사용하기 위해서 String으로 형변환한다.
            }
        }

        /*3) 받은 모집글에서 참여한 사람의 신청 글 삭제*/
        try {
            //2-2)쉐어드에서 내가 받은 모집 참여자 리스트 불러오기.
            //key: 내가 만든 모집 글 번호 => My_Item_RoomNum_key
            List_Receiver_Application = SharedClass.get_List_StudyRoom_Joiner_json(getApplicationContext(), My_Item_RoomNum_key, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
        } catch (JSONException e) {
        }


        /*4)쉐어드에서 나의 친구 정보 리스트 불러오기*/
        try {
            //나의 친구 리스트
            //key: 모집 신청서를 보낸 사람의 Id
            List_MyFriend_App_Me = SharedClass.get_List_MyStudyFriend_Infor_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_MyStudyFriend_Data);
        } catch (JSONException e) {
        }


    }


    //필요한 findview 세팅
    public void set_findView() {
        btn_agree = findViewById(R.id.btn_agree_detailchcek);
        btn_disagree = findViewById(R.id.btn_disagree_detailchcek);
        iv_backIcon = findViewById(R.id.iv_backicon_detailcheck);
        tv_letter = findViewById(R.id.tv_letter_detailchcek);
    }

    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_agree.setOnClickListener(this);//
        btn_disagree.setOnClickListener(this);
        iv_backIcon.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_agree_detailchcek://수락
                //전체 방 리스트만 수정하면 참여한 모집글 리스트는 저절로 갱신된다.
                //내가 보낸 모집글 확인 엑티비티에서 2중 포문을 사용해서 전체 방 리스트에 없는 참여한 모집글은 저절로 갱신되기 때문
                //Send_Application 엑티비티 참고


                /*1.나의 공부친구 리스트에 참여한 사람 추가
                 * 공부친구 리스트에 먼저 추가하고 나서 내 방을 삭제해야 한다.*/
                //1-1)
                //내가 수락버튼을 누른사람의  아이템 포지션을 갖고온다 그러기 위해서
                //내가 받은 신청서리스트를 for문을 돌려서 신청한 사람의 id를 받아온다.
                //아이템 포지션의 참여자의 id와 내가 받은 신청서 리스트의 참여자의 id와 일치하는 것을
                //내 친구 리스트 정보에 추가한다.


                //내가 수락버튼을 누르면 상대방의 친구리스트에도 추가가 되어야 하기 때문에
                //상대방의 친구 리스트도 쉐어드에서 가져온다.
                for (int i = 0; i < List_Receiver_Application.size(); i++) {
                    data_Receiver_Studyroom_My = List_Receiver_Application.get(i);//리스트에 객체반환
                    //내가 받은 모임 신청서 리스트 == 모임 신청한 사람의 id 비교
                    //내가 선택한 상대방의 id와 일치하는 것만 찾기 (1개)
                    if (data_Receiver_Studyroom_My.room_Joiner_Id.equals(List_Receiver_Application.get(Myapplication.itemview_position).room_Joiner_Id)) {
                        try {
                            //상대방의 친구 리스트
                            //key: 모집 신청서를 보낸 사람의 Id
                            List_MyFriend_App_You = SharedClass.get_List_MyStudyFriend_Infor_json(getApplicationContext(), List_Receiver_Application.get(Myapplication.itemview_position).room_Joiner_Id, SharedClass.PREFERENCES_MyStudyFriend_Data);
                        } catch (JSONException e) {
                        }

                        //내 유저정보 리스트를 쉐어드에서불러온다.
                        try {
                            List_user_me = SharedClass.get_List_UserData_Json(getApplicationContext(), Myapplication.UserId);
                        } catch (JSONException e) {
                        }
                        //상대방 유저정보 리스트를 쉐어드에서불러온다.
                        try {
                            List_user_you = SharedClass.get_List_UserData_Json(getApplicationContext(), data_Receiver_Studyroom_My.room_Joiner_Id);
                        } catch (JSONException e) {
                        }
                        roomJoinerId = data_Receiver_Studyroom_My.room_Joiner_Id;//참여한 사람 id 변수에 저장
                        //내가 받은 친구 신청목록의 방번호를 리스트에 저장.
                        //내 정보 공부친구 리스트에 저장
                        //상대방의 공부타입, 닉네임, 프로필사진
                        List_MyFriend_App_Me.add(new Data_StudyRoom_Infor(List_user_you.get(0).studyType, data_Receiver_Studyroom_My.room_Joiner_Id,
                                List_user_you.get(0).nickname_User, "", 0, data_Receiver_Studyroom_My.roomNumber,
                                List_user_you.get(0).iv_profile_User));//내 친구 리스트에 데이터 추가
                        //상대방 정보 공부친구 리스트에 저장.
                        //내 공부타입, 닉네임, 프로필사진
                        List_MyFriend_App_You.add(new Data_StudyRoom_Infor(List_user_me.get(0).studyType, Myapplication.UserId,//내아이디 추가
                                List_user_me.get(0).nickname_User, "", 0, data_Receiver_Studyroom_My.roomNumber,
                                List_user_me.get(0).iv_profile_User));//상대방 친구 리스트에 데이터 추가

                        //1-2)추가한 공부 친구 정보 내아이디로 쉐어드에 저장한다.
                        try {
                            SharedClass.Save_List_MyStudyFriend_Infor_json(getApplicationContext(), Myapplication.UserId,//내 친구 리스트
                                    List_MyFriend_App_Me, SharedClass.PREFERENCES_MyStudyFriend_Data);
                        } catch (JSONException e) {
                        }
                        //1-3)추가한 공부 친구 정보를 상대방 아아디로 저장한다.
                        try {//List_Receiver_Application.get(Myapplication.itemview_position).room_Joiner_Id == 상대방 id
                            SharedClass.Save_List_MyStudyFriend_Infor_json(getApplicationContext(), List_Receiver_Application.get(Myapplication.itemview_position).room_Joiner_Id,
                                    List_MyFriend_App_You, SharedClass.PREFERENCES_MyStudyFriend_Data);//상대 친구 리스트
                        } catch (JSONException e) {
                        }
                    }
                }


                /*2.전체 모집 글에서 삭제 => 2.보낸 모집글에서도 자동으로 삭제*/
                //2-1)전체 모집글 리스트에서 내가 만든 모집글 삭제
                for (int i = 0; i < List_All_StudyRoom_infor.size(); i++) {
                    //2-2)내 아이디로 만들어진 모집 글을 찾고 모집글의 방번호를 알아낸다.
                    if (List_All_StudyRoom_infor.get(i).room_Maker_Id.equals(Myapplication.UserId)) {
                        My_Item_RoomNum = List_All_StudyRoom_infor.get(i).roomNumber;//내 id로된 모집 글 방 번호

                        //2-3)내 id로된 모집글의 방번호를 통해서 내가 받은 모집 글 리스트를 삭제한다.
                        My_Item_RoomNum_key = String.valueOf(My_Item_RoomNum);//key로 사용하기 위해서 String으로 형변환한다.

                        //2-4)내가 받은 모집글이 저장된 리스트를 쉐어드에서 삭제한다.
                        //key: 내 id로된 모집글의 방번호
                        SharedClass.remove_key(getApplicationContext(), My_Item_RoomNum_key, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
                        //2-5)전체 모집글 정보 리스트에서 내 id로 만들어진 모집글을 삭제한다.
                        List_All_StudyRoom_infor.remove(i);//내 id로된 모집 글을 전체 모집 글 리스트에서 삭제
                    }
                }

                //2-2)상대방이 만든 모집글 삭제
                //전체 모집글 리스트 비교
                for (int i = 0; i < List_All_StudyRoom_infor.size(); i++) {
                    //모집글 참여한 사람의 id와 같은 id를 찾는다.
                    if (List_All_StudyRoom_infor.get(i).room_Joiner_Id.equals(roomJoinerId)) {
                        //상대방이 만든 모집 글 정보를  전체 모집 글 리스트에서 삭제
                        List_All_StudyRoom_infor.remove(i);
                    }
                }


                //2-6)클릭한 아이템을 삭제한 전체 모집 글 리스트를 다시 쉐어드에 저장
                try {
                    SharedClass.Save_List_StudyRoom_Infor_json(getApplicationContext(), SharedClass.ListKey_StudyRoomDATA,
                            List_All_StudyRoom_infor, SharedClass.PREFERENCES_Name_Room_infor);
                } catch (JSONException e) {
                }


                Intent intent = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                Toast.makeText(this, "공부친구가 생겼습니다 축하드립니다", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;

            case R.id.btn_disagree_detailchcek://거절

                //공통) 쉐어드에서 받은 모집 신청서리스트를 갖고오기 위한 키 값을 찾는다.
                for (int i = 0; i < List_All_StudyRoom_infor.size(); i++) {
                    //내 아이디로 만들어진 모집 글을 찾는다.
                    //조건: 전체 모집글의 만든사람 ID == 내 아이디
                    if (List_All_StudyRoom_infor.get(i).room_Maker_Id.equals(Myapplication.UserId)) {
                        My_Item_RoomNum = List_All_StudyRoom_infor.get(i).roomNumber;//내 id로된 모집 글 방 번호
                        My_Item_RoomNum_key = String.valueOf(My_Item_RoomNum);//내가 만든 모집글의 번호
                    }
                }


                /*1)신청한 사람의 모집 신청글 리스트에서 내 모집글의 번호와 맞는 것을 삭제*/
                //1-1)신청한 사람의 모집 신청
                for (int i = 0; i < List_Sender_Application.size(); i++) {
                    data_Sender_Studyroom = List_Sender_Application.get(i);
                    //참여자의 모집 신청글의 방 번호 == 내가 만든 모집글의 방 번호
                    if (data_Sender_Studyroom.roomNumber == My_Item_RoomNum) {
                        List_Sender_Application.remove(i);//참여자의 모집 참여글에서 삭제.
                    }
                }


                //1-2) 삭제한 부분을 갱신하기 위해서
                // 신청한 사람의 모집글 리스트를 쉐어드에 저장
                //key ==> Sedner_id: 신청한 사람의 id
                try {
                    SharedClass.Save_List_Apply_StudyRoom_Infor_json(getApplicationContext(), Myapplication.Sender_Id, List_Sender_Application, SharedClass.PREFERENCES_Apply_UserId_StudyRoom_Data);
                } catch (JSONException e) {
                }


                //2-3)거절 버튼을 누른 모집 참여자의 id를 내가 받은 모집 참여 신청서 리스트와 비교
                for (int i = 0; i < List_Receiver_Application.size(); i++) {
                    //클릭한 모집 참여자의 id와 같은 id를 갖고있는 객체를
                    // 내가 받은 모집 참여 신청서의 리스트에서 삭제
                    data_Receiver_Studyroom = List_Receiver_Application.get(i);
                    //조건: 모집 참여자의 id == 거절 버튼을 누른 모집 참여자의 id
                    if (data_Receiver_Studyroom.room_Joiner_Id.equals(List_Receiver_Application.get(Myapplication.itemview_position).room_Joiner_Id)) {
                        List_Receiver_Application.remove(i);//참여자 정보 삭제
                    }
                }

                //2-4)삭제가된 부분을 갱신하기 위해서
                //내가 받은 모집 글 참여 신청서를 쉐어드에 다시 저장.
                try {
                    SharedClass.Save_List_StudyRoom_Joiner_json(getApplicationContext(), My_Item_RoomNum_key, List_Receiver_Application, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
                } catch (JSONException e) {
                }


                Intent intent2 = new Intent(getApplicationContext(), Receive_Application_Activity.class);
                Toast.makeText(this, "힝ㅠ 아쉽네용... ", Toast.LENGTH_SHORT).show();
                startActivity(intent2);
                finish();
                break;


            case R.id.iv_backicon_detailcheck://뒤로가기
                Intent intent3 = new Intent(getApplicationContext(), Receive_Application_Activity.class);
                startActivity(intent3);
                finish();
                break;

        }
    }

}