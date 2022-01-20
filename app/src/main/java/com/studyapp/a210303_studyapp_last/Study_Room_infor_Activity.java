package com.studyapp.a210303_studyapp_last;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.ArrayList;

public class Study_Room_infor_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_apply;
    ImageView iv_backIcon;
    TextView tv_roomName, tv_roomContent, tv_nickname, tv_studyType;//방 제목, 방 내용
    String Listkey;
    Data_StudyRoom_Infor data_studyRoom;
    Data_StudyRoom_Infor data_studyRoomId;
    Data_StudyRoom_Infor data_All_studyRoom;//전체 모집글 정보
    Data_StudyRoom_Infor data_My_studyRoom_number;//내가 가입 신청을 한 모집글 정보

    ArrayList<Data_StudyRoom_Infor> List_StudyRoom_AllInfor_Shared;//전체 모임글 데이터 리스트
    ArrayList<Data_StudyRoom_Infor> List_Apply_RoomNumber_StudyRoom_before;//내가 가입한 방 번호 리스트
    ArrayList<Data_StudyRoom_Infor> List_Apply_MyStudyRoom_Update;//내가 가입한 방 번호 리스트
    ArrayList<Data_StudyRoom_Infor> List_Receive_UserData_StudyRoom;//내가 만든 모집글에 참여한 유저 데이터 리스트
    ArrayList<Data_user> List_user;//유저정보


    //내가 보낸 모집글에 필요한 변수;
    String roomName = "";
    String roomContent = "";
    String profile = "";
    String nickname = "";
    String studyType = "";

    public static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_infor_activity);
        set_findView();
        set_ButtonListener();
        Listkey = SharedClass.ListKey_StudyRoomDATA;//쉐어드에 저장한 데이터리스트의 키
        List_Apply_MyStudyRoom_Update = new ArrayList<>();//내가 가입한 모집글을 업데이트할 리스트를 생성
        /*1)쉐어드에서 전체 모임방 데이터 리스트 불러오기*/
        try {
            List_StudyRoom_AllInfor_Shared = SharedClass.get_List_StudyRoom_Infor_json(getApplicationContext(), Listkey, SharedClass.PREFERENCES_Name_Room_infor);
            System.out.println("json객체 불러오기 성공_RoomInfor");
        } catch (JSONException e) {
            System.out.println("json객체 불러오기 실패_RoomInfor");
        }


        //쉐어드에서 내 정보 리스트를 불러온다
        try {
            List_user = SharedClass.get_List_UserData_Json(getApplicationContext(), Myapplication.UserId);
        } catch (JSONException e) {
        }

        /*2)쉐어드에서 내가 가입한 방의 번호들을 저장한 리스트를 갖고온다*/
        try {
            //이전에 내가 가입했던 모집글의 리스트
            List_Apply_RoomNumber_StudyRoom_before = SharedClass.get_List_Apply_StudyRoom_Infor_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_Apply_UserId_StudyRoom_Data);
            Log.d("onCreate_studyRoom_infor_Activity", "내가 가입한 방 번호 리스트 갖고오기 성공");
        } catch (JSONException e) {
            Log.d("onCreate_studyRoom_infor_Activity", "내가 가입한 방 번호 리스트 갖고오기 실패");
        }

        //***내가 가입신청을 한 모집글의 데이터를 전체 모임의 데이터와 비교해서 갱신시켜준다.
        //이유: 내가 신청한 모집 글을 작성자가 모집글을 삭제한 경우
        //내가 가입한 모집글을 삭제해주어야 하기 때문에
        /*1)리사이클러뷰에 아이템뷰에 내가 보낸 모집글 데이터를 입력한다.*/
        //1) 2중 for문을 돌려서 전체 방 정보중에 내 방 번호리스트에 있는 객체만 불러온다.
        for (int i = 0; i < List_StudyRoom_AllInfor_Shared.size(); i++) {
            data_All_studyRoom = List_StudyRoom_AllInfor_Shared.get(i);
            for (int j = 0; j < List_Apply_RoomNumber_StudyRoom_before.size(); j++) {
                data_My_studyRoom_number = List_Apply_RoomNumber_StudyRoom_before.get(j);
                //2) 전체 모집 글 번호와 내가 가입신청한 모집글 번호와 같은 객체를 찾아서
                //내가 보낸 모집글 정보를 입력한다.
                if (data_All_studyRoom.roomNumber == data_My_studyRoom_number.roomNumber) {
                    List_Apply_MyStudyRoom_Update.add(new Data_StudyRoom_Infor(data_All_studyRoom.roomName,
                            data_All_studyRoom.roomContent, data_All_studyRoom.room_Maker_Id,
                            data_All_studyRoom.roomNumber, data_All_studyRoom.iv_profile_uri,
                            data_All_studyRoom.tv_nickname, data_All_studyRoom.studyType
                            , data_All_studyRoom.room_letter
                    ));
                }
            }
        }



        /*엑티비티에 있는 방 정보 데이터를 입력해주기*/
        //전체 모집글 정보 리스트 비교
        for (int i = 0; i < List_StudyRoom_AllInfor_Shared.size(); i++) {
            data_studyRoom = List_StudyRoom_AllInfor_Shared.get(i);
            //클릭한 방의 id와 방을 만든사람의 id가 일치하는 것만 찾기
            if (Myapplication.ClickItemId.equals(data_studyRoom.room_Maker_Id)) {
                //방정보 데이터 입력
                tv_roomName.setText(data_studyRoom.roomName);//방 제목 입력
                tv_roomContent.setText(data_studyRoom.roomContent);//방 내용 입력
                tv_nickname.setText(data_studyRoom.tv_nickname);//클릭한 방의 만든사람 닉네임
                tv_studyType.setText(data_studyRoom.studyType);//클릭한 방의 만든사람 공부타입
                //내가 클릭한 모집 글의 번호를 변수에 저장
                Myapplication.RoomNumber = data_studyRoom.roomNumber;
                //내가 클릭한 모집 글의 만든사람ID를 변수에 저장
                Myapplication.MakerUserId = data_studyRoom.room_Maker_Id;
                roomName = data_All_studyRoom.roomName;//클릭한 방이름
                roomContent = data_All_studyRoom.roomContent;//클릭한 방 내용
                nickname = data_All_studyRoom.tv_nickname;//클릭한 방의 만든사람 닉네임
                studyType = data_All_studyRoom.studyType;//클릭한 방의 만든사람 공부타입
            }
        }

        //방 번호를 string으로 형변환해서 쉐어드의 키로 지정한다.
        String Roomnumber_key = String.valueOf(Myapplication.RoomNumber);
        /*3)쉐어드에서 내가 받은 신청글  리스트를 갖고온다.*/
        try {
            List_Receive_UserData_StudyRoom = SharedClass.get_List_StudyRoom_Joiner_json(getApplicationContext(), Roomnumber_key, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
            Log.d("onCreate_studyRoom_infor_Activity", "reverList_갖고오기 성공");
        } catch (JSONException e) {
            Log.d("onCreate_studyRoom_infor_Activity", "reverList_갖고오기 실패");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {//갱신한 내가 가입한 모집글 리스트를 쉐어드에 저장한다.
            SharedClass.Save_List_Apply_StudyRoom_Infor_json(getApplicationContext(), Myapplication.UserId,
                    List_Apply_MyStudyRoom_Update, SharedClass.PREFERENCES_Apply_UserId_StudyRoom_Data);
        } catch (JSONException e) {
        }

    }

    //필요한 findview 세팅
    public void set_findView() {
        btn_apply = findViewById(R.id.btn_Apply_Roominfor);
        iv_backIcon = findViewById(R.id.iv_backicon_Roominfor);
        tv_roomName = findViewById(R.id.tv_roomName_Roominfor);
        tv_roomContent = findViewById(R.id.tv_content_Roominfor);
        tv_studyType = findViewById(R.id.tv_StudyType_Roominfor);
        tv_nickname = findViewById(R.id.tv_nickname_Roominfor);
    }

    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_apply.setOnClickListener(this);
        iv_backIcon.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {

            //actviityforresult 사용
            case R.id.btn_Apply_Roominfor://모집 신청 버튼
                Myapplication.Create_StudyRoom_check = 0;
                /*1.모집 글 신청 조건*/
                //1)내가 처음 모집 글에 신청한 상황
                //2)나의 모집글이 아닌 다른 사람의 모집글 정보에 들어왔을 때.

                /* 중요! 내가 클릭한 모집글의 id == 내 id 가 같은 경우*/
                //1)내 모집글에 들어온 경우
                if (Myapplication.ClickItemId.equals(Myapplication.UserId)) {
                    Toast.makeText(this, "내 모집글에는 가입 신청 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;//메서드 종료
                }

                //2)내가 이미 가입 신청을 한 모집글의 경우
                for (int i = 0; i < List_Apply_MyStudyRoom_Update.size(); i++) {
                    data_studyRoomId = List_Apply_MyStudyRoom_Update.get(i);

                    /*  중요! 클릭한 모집 글의 번호 == 내가 가입한 모집글 리스트 중의 번호가 같은 경우*/
                    //2-1)내가 이미 가입한 모집 글을 선택한 경우
                    if (Myapplication.ClickItemId.equals(data_studyRoomId.room_Maker_Id)) {
                        Toast.makeText(this, "내가 이미 신청한 모집글에는 신청할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        return;//메서드 종료
                    }
                }

                //편지 엑티비티로 가서 모집글에 신청 글을 남긴다.
                Intent intent = new Intent(getApplicationContext(), Send_Letter_Activity.class);
                startActivityForResult(intent, REQUEST_CODE);
                setResult(RESULT_OK);//이동.
                break;

            case R.id.iv_backicon_Roominfor://뒤로가기
                Myapplication.Create_StudyRoom_check = 0;
                Intent intent2 = new Intent(getApplicationContext(), All_StudyRoom_Activity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }


    @Override
    //다른 엑티비티에서 저장한 데이터를 갖고오는 메서드
    /*2-2)쉐어드에 자신이 가입신청을 한 방의 번호를 저장한다.*/
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //엑티비티에서 갖고온 데이터가 있을 때만 실행
        //엑티비티에서 갖고온 모집 신청 글이 = ""값이 아닐경우만 실행
        //Create_StudyRoom_check == 1번 : 모집글 작성
        if (requestCode == REQUEST_CODE && !Myapplication.User_Letter.equals("") && Myapplication.Create_StudyRoom_check == 1) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            try {
                /* 1)Send_모임신청서 리스트 쉐어드저장 */
                //1)모임 신청 버튼을 누르면 모임 글의 번호가 저장된다.
                //***갱신된 리스트에 넣어준다.
                //방내용은 클릭한 방의 정보
                //방의 참여한 사용자의 정보는 내 사용자 정보
                List_Apply_MyStudyRoom_Update.add(new Data_StudyRoom_Infor(roomName, roomContent,
                        Myapplication.ClickItemId, Myapplication.RoomNumber, List_user.get(0).iv_profile_User,
                        List_user.get(0).nickname_User, List_user.get(0).studyType, Myapplication.User_Letter));


                //2)저장한 리스트를 쉐어드에 Json으로 저장한다.
                //key 값은 로그인한 사용자의 ID
//                2-3)쉐어드에 자신이 가입신청한 방 번호 저장.
                //1)방 만든사람 id, 2)방 번호, 3)신청 글
                //**갱신된 내가 보낸 모집글 리스트를 쉐어드에를 저장한다.
                SharedClass.Save_List_Apply_StudyRoom_Infor_json(getApplicationContext(), Myapplication.UserId,
                        List_Apply_MyStudyRoom_Update, SharedClass.PREFERENCES_Apply_UserId_StudyRoom_Data);

                /* 2)내가 받은 모임신청서 리스트를 쉐어드에 저장  */
                //1)모임 신청을 누르면 모임을 참여한 사람의 데이터를 저장한다.
                //클릭한 방의 방 번호 중요
                //신청한 사람의 id, 신청자 프로필사진, 신청자 닉네임, 신청자 공부타입, 신청자가 쓴 편지
                List_Receive_UserData_StudyRoom.add(new Data_StudyRoom_Infor(Myapplication.UserId, Myapplication.MakerUserId,
                        List_user.get(0).iv_profile_User, List_user.get(0).nickname_User, List_user.get(0).studyType,
                        Myapplication.RoomNumber, Myapplication.User_Letter));

                //2)내가 받은 모임신청서 리스트 쉐어드에 저장
                //방 번호를 키 값으로 사용하기 위해서 String으로 형변환해서 저장한다.
                String str_roomNumber = String.valueOf(Myapplication.RoomNumber);
                //쉐어드에 내가 받은 모집글 리스트 저장.
                SharedClass.Save_List_StudyRoom_Joiner_json(getApplicationContext(), str_roomNumber,
                        List_Receive_UserData_StudyRoom, SharedClass.PREFERENCES_Receive_StudyRoom_Data);

                Toast.makeText(this, "공부 친구 신청이 완료되었습니다.", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
            }
        } else {
            Toast.makeText(this, "하고싶은 말을 적어주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
