package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;

import java.util.ArrayList;

public class StudyFriend_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_home, btn_search_StudyFriend, btn_apply_StudyFriend, btn_checkStudyTime, btn_rank;
    Button btn_remove_MyFriend;//친구 끊기
    RecyclerView rv_StudyFriend;
    Rv_Adapter_MyStudyFriend rv_Adapter_StudyFriend;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Data_StudyRoom_Infor> List_MyFriend;//나의 공부친구 리스트
    ArrayList<Data_StudyRoom_Infor> List_Your_Friend;//상대방의 공부친구 리스트
    ArrayList<Data_Message> List_Message;//채팅 내역 리스트
    Data_StudyRoom_Infor data_MyStudyFriend_infor;
    AlertDialog dialog;//다이얼로그

    String JoinerId;//상대방의 id => 공부친구 리스트 키 값.
//    AdView mAdView;//구글광고

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studyfriend_activity);
        set_findView();
        set_ButtonListener();

        //리사이클러뷰 세팅
        rv_StudyFriend = findViewById(R.id.rv_myfriend);//리사이클러뷰 findView
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_Adapter_StudyFriend = new Rv_Adapter_MyStudyFriend(getApplicationContext());
        rv_StudyFriend.setLayoutManager(linearLayoutManager);//linearlayout 세팅
        rv_StudyFriend.setAdapter(rv_Adapter_StudyFriend);//adapter 세팅
//        초기화
     /*   MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        //광고삽입
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");*/


        //쉐어드에 내 공부 친구 리스트 불러오기
        try {
            List_MyFriend = SharedClass.get_List_MyStudyFriend_Infor_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_MyStudyFriend_Data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //쉐어드에 내 채팅 내역 리스트 불러오기
        //key: 내 공부친구 리스트의 번호
        if (List_MyFriend.size() != 0) {
            try {
                List_Message = SharedClass.get_List_message_json(getApplicationContext(), String.valueOf(List_MyFriend.get(0).roomNumber), SharedClass.PREFERENCES_Message);
            } catch (JSONException e) {
            }
        }


        //불러온 공부 친구 리스트  리사이클러뷰에 공부친구 정보 입력
        for (int i = 0; i < List_MyFriend.size(); i++) {
            data_MyStudyFriend_infor = List_MyFriend.get(i);
            //***임시로 닉네임에다가 참여자의 id를 넣었음
            //room content => 공부 친구 아이템뷰에 마지막으로 보낸 문자메세지 입력

            //채팅 내역이 있는 경우
            //마지막으로 보낸 메세지 => 마지막 인덱스 => size() - 1
            if (List_Message.size() != 0) {
                rv_Adapter_StudyFriend.addItem(new Data_StudyRoom_Infor(data_MyStudyFriend_infor.studyType, data_MyStudyFriend_infor.room_Joiner_Id,
                        data_MyStudyFriend_infor.NickName_room_Joiner, (List_Message.get(List_Message.size() - 1).send_message), data_MyStudyFriend_infor.StudyTime
                        , data_MyStudyFriend_infor.roomNumber, data_MyStudyFriend_infor.iv_profile_uri));
            } else {//채팅 내역이 없는경우
                rv_Adapter_StudyFriend.addItem(new Data_StudyRoom_Infor(data_MyStudyFriend_infor.studyType, data_MyStudyFriend_infor.room_Joiner_Id,
                        data_MyStudyFriend_infor.NickName_room_Joiner, "", data_MyStudyFriend_infor.StudyTime, data_MyStudyFriend_infor.roomNumber,
                        data_MyStudyFriend_infor.iv_profile_uri));
            }
            rv_Adapter_StudyFriend.notifyDataSetChanged();
        }


        dialog = getDialog();//다이어로그 객체 생성.

        //리사이클러뷰 아이템 클릭 리스너
        rv_Adapter_StudyFriend.setOnItemClickListener(new Rv_Adapter_MyStudyFriend.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //엑티비티 이동
                Intent intent = new Intent(getApplicationContext(), Message_Aciticity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        try {

            //공부친구 리스트 쉐어드에 저장.
            SharedClass.Save_List_MyStudyFriend_Infor_json(getApplicationContext(), Myapplication.UserId, List_MyFriend
                    , SharedClass.PREFERENCES_MyStudyFriend_Data);
        } catch (JSONException e) {
        }

    }

    //필요한 findview 세팅
    public void set_findView() {
        btn_checkStudyTime = findViewById(R.id.btn_Check_StudyTime_friend);
        btn_apply_StudyFriend = findViewById(R.id.btn_apply_StudyFriendList);
//        btn_chat = findViewById(R.id.btn_chating_friend);
        btn_home = findViewById(R.id.btn_home_friend);
        btn_search_StudyFriend = findViewById(R.id.btn_search_StudyFriend);
        btn_remove_MyFriend = findViewById(R.id.btn_Remove_MyFriend);//친구 끊기
        btn_rank = findViewById(R.id.btn_rank_studyFriend);
    }

    //버튼 리스너 세팅
    public void set_ButtonListener() {
        btn_rank.setOnClickListener(this);
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_apply_StudyFriend.setOnClickListener(this);
//        btn_chat.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        btn_search_StudyFriend.setOnClickListener(this);
        btn_remove_MyFriend.setOnClickListener(this);
        btn_checkStudyTime.setOnClickListener(this);
    }

    //공부친구 삭제 다이얼로그
    public AlertDialog getDialog() {
        //thems에서 xml파일을 하나 만들어서 입력한다
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        //다이얼로그에  들어갈 레이아웃 xml 파일 입력
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.remove_dialog, null, false);
        builder.setView(view);

        //다이어 로그에 들어갈 view 추가
        Button btn_remove_StudyRoom_dialog = view.findViewById(R.id.btn_remove_myStudyRoom);//친구 삭제 버튼
        Button btn_cancel_StudyRoom_dialog = view.findViewById(R.id.btn_cancel_myStudyRoom);//친구 삭제 취소 버튼

        btn_remove_StudyRoom_dialog.setOnClickListener(this);
        btn_cancel_StudyRoom_dialog.setOnClickListener(this);


        //취소하기 버튼클릭이벤트
        btn_cancel_StudyRoom_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//취소 버튼 누르면 메뉴 버튼으로 이동
                //엑티비티 이동
                Intent intent = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //삭제하기 버튼클릭이벤트
        btn_remove_StudyRoom_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//취소 버튼 누르면 메뉴 버튼으로 이동
                if (List_MyFriend.size() != 0) {//공부 친구가 있을 때만 코드 진행.
                    //클릭한 아이템의 참여한 사람 id를 내 친구 리스트하고 비교한다.
                    //비교후에 같은 아이디를 내 친구 리스트에서 삭제한다.
                    for (int i = 0; i < List_MyFriend.size(); i++) {

                        try { //상대방의 공부 친구 리스트 쉐어드에서 불러오기.
                            List_Your_Friend = SharedClass.get_List_MyStudyFriend_Infor_json(getApplicationContext(), List_MyFriend.get(i).room_Joiner_Id, SharedClass.PREFERENCES_MyStudyFriend_Data);
                        } catch (JSONException e) {
                        }

                        JoinerId = List_MyFriend.get(i).room_Joiner_Id;//상대방의 공부친구 리스트 변수에 저장.
                        //상대방의 공부친구 리스트에 공부친구 삭제
                        List_Your_Friend.remove(i);
                        //내 공부친구 리스트에 공부친구 삭제
                        List_MyFriend.remove(i);
                        Toast.makeText(StudyFriend_Activity.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    //1.다시 쉐어드에 공부친구 리스트 저장
                    //1)내 공부친구 리스트 쉐어드에 저장.
                    try {
                        SharedClass.Save_List_MyStudyFriend_Infor_json(getApplicationContext(), Myapplication.UserId, List_MyFriend
                                , SharedClass.PREFERENCES_MyStudyFriend_Data);
                    } catch (JSONException e) {
                    }

                    //2)상대방의 공부친구 리스트 쉐어드에 저장
                    try {
                        SharedClass.Save_List_MyStudyFriend_Infor_json(getApplicationContext(), JoinerId, List_Your_Friend
                                , SharedClass.PREFERENCES_MyStudyFriend_Data);
                    } catch (JSONException e) {
                    }


                } else {//공부친구가 없을 때
                    Toast.makeText(getApplicationContext(), "현재 공부 친구가 없어서 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }


                //엑티비티 이동
                Intent intent = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog = builder.create();//다이어로그 생성
        //버튼 클릭 이벤트 지정
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rank_studyFriend://랭킹
                Intent intent = new Intent(getApplicationContext(), Rank_Activity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_home_friend://메인화면 이동
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.btn_Check_StudyTime_friend://공부시간 통계
                Intent intent5 = new Intent(getApplicationContext(), Check_MyStudyTime_Actvity.class);
                startActivity(intent5);
                finish();
                break;


            case R.id.btn_search_StudyFriend://공부 친구 찾기 엑티비티 이동
                Intent intent3 = new Intent(getApplicationContext(), All_StudyRoom_Activity.class);
                startActivity(intent3);
                finish();
                break;

            case R.id.btn_apply_StudyFriendList://공부 친구 신청 목록으로 이동.
                Intent intent4 = new Intent(getApplicationContext(), Send_Application_StudyFriend_Activity.class);
                startActivity(intent4);
                finish();
                break;

            case R.id.btn_Remove_MyFriend://친구 끊기
                //다이얼로그가 실행
                dialog.show();
                break;


            default:
                break;
        }
    }
}

