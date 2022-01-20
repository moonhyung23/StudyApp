package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class Receive_Application_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_backicon;
    Button btn_Send_Application;
    RecyclerView rv_Receive_Application;
    Rv_Adapter_Receive_Application rv_adapter_receive_application;
    ArrayList<Data_StudyRoom_Infor> List_receive_app_shared;
    ArrayList<Data_StudyRoom_Infor> List_AllStudy_infor_shared;
    ArrayList<Data_StudyRoom_Infor> List_AllStudy_infor_recycle;
    Data_StudyRoom_Infor data_Receive_app_shared;
    Data_StudyRoom_Infor data_Receive_app_recycler;
    int myRoomNum;
    String MyRoomNum_key = "";

    @Override
    /*모집글의 신청한 사람의 정보를 보여주는 Activity*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_application_activity);
        set_findView();
        set_ButtonListener();

        //리사이클러뷰 세팅
        Rv_Adapter_Receive_Application rv_adapter_receive_application;
        rv_Receive_Application = findViewById(R.id.rv_Receive);//리사이클러뷰 findView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_adapter_receive_application = new Rv_Adapter_Receive_Application(getApplicationContext());
        rv_Receive_Application.setLayoutManager(linearLayoutManager);//linearlayout 세팅
        rv_Receive_Application.setAdapter(rv_adapter_receive_application);//adapter 세팅

        /*1)전체 모집글 리스트 쉐어드에서 가져오기*/
        try {
            List_AllStudy_infor_shared = SharedClass.get_List_StudyRoom_Infor_json(getApplicationContext(), SharedClass.ListKey_StudyRoomDATA, SharedClass.PREFERENCES_Name_Room_infor);
        } catch (JSONException e) {
        }

        /*내가 만든 방의 방 번호를 불러오기 위해서*/
        //전체 모집글 정보의 갯수만큼 반복
        for (int i = 0; i < List_AllStudy_infor_shared.size(); i++) {
            //방을 하나만 만들 수 있어서 id로 구분이 가능
            //1)전체 모집글의 만든사람 id
            //2)로그인한 사용자의 id(내가 만든 방의 id)
            if (List_AllStudy_infor_shared.get(i).room_Maker_Id.equals(Myapplication.UserId)) {
                //내가 만든 방의 방 번호를 가져온다.
                myRoomNum = List_AllStudy_infor_shared.get(i).roomNumber;
                //방 번호는 키 값 이므로 String으로 형변환 시킨다.
                MyRoomNum_key = String.valueOf(myRoomNum);
            }
        }


        /*3) 쉐어드에서 방 번호를 key 값으로 내가 받은 신청서 리스트 가져오기
         * key: 방 번호 */
        try {
            //내가 받은 신청서 정보 리스트 불러오기
            List_receive_app_shared = SharedClass.get_List_StudyRoom_Joiner_json(getApplicationContext(), MyRoomNum_key, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
        } catch (JSONException e) {
        }

        //내가 받은 신청서 정보가 있을 때만 리사이클러뷰에 입력.
        if (List_receive_app_shared != null) {
            /*4) 받은 신청서 정보들을 리사이클러뷰의 아이템뷰에 입력하기.*/
            for (int i = 0; i < List_receive_app_shared.size(); i++) {
                data_Receive_app_shared = List_receive_app_shared.get(i);
                rv_adapter_receive_application.addItem(new Data_StudyRoom_Infor(data_Receive_app_shared.room_Joiner_Id,
                        data_Receive_app_shared.room_Maker_Id, data_Receive_app_shared.iv_profile_roomJoiner,
                        data_Receive_app_shared.NickName_room_Joiner, data_Receive_app_shared.studyType_roomJoiner,
                        data_Receive_app_shared.roomNumber, data_Receive_app_shared.room_letter));
            }
        }


        //리사이클러뷰 아이템뷰의 선택하기를 클릭했을 때.
        rv_adapter_receive_application.setOnItemClickListener(new Rv_Adapter_Receive_Application.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //리사이클러뷰에 클릭한 아이템의 사용자가 보낸 편지
                data_Receive_app_recycler = rv_adapter_receive_application.getItem(position);
                Myapplication.intent_Letter = data_Receive_app_recycler.room_letter;//클릭한 아이템뷰에 저장된 편지를 변수에 저장.
                Myapplication.itemview_position = position; //클릭한 아이템뷰의 포지션을 변수에 저장.
                Myapplication.Sender_Id = data_Receive_app_recycler.room_Joiner_Id;//모집글에 신청한 사람의 ID
                //엑티비티 이동
                Intent intent = new Intent(getApplicationContext(), Receive_Choice_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //필요한 findview 세팅
    public void set_findView() {
        iv_backicon = findViewById(R.id.iv_backicon_Receive);
        btn_Send_Application = findViewById(R.id.btn_Send_application);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        iv_backicon.setOnClickListener(this);//
        btn_Send_Application.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Send_application://보낸 친구 신청 글 엑티비티로 이동
                Intent intent = new Intent(getApplicationContext(), Send_Application_StudyFriend_Activity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.iv_backicon_Receive://뒤로가기
                Intent intent2 = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }


}
