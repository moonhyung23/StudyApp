package com.studyapp.a210303_studyapp_last;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

import static com.studyapp.a210303_studyapp_last.Myapplication.itemview_position;

public class MyStudyRoom_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_all_StudyRoom;//모든 모집 글 이동 버튼
    Button btn_remove_StudyRoom_dialog;//아이템 삭제 버튼_다이얼로그
    Button btn_cancel_StudyRoom_dialog;//아이템 삭제취소 버튼_다이얼로그

    String nickname;
    String studyType;
    String profile;

    String RoomNum_key; //내가 받은 모집글 리스트의 키

    ImageView backicon_Mystudyroom;
    RecyclerView rv_MystudyRoom;
    LinearLayoutManager linearLayoutManager;
    Rv_Adapter_MyRoom rv_adapter_MystudyRoom;

    /*쉐어드*/

    //key: Sharedclass에서 정한 리스트 키
    ArrayList<Data_StudyRoom_Infor> List_AllStudyRoom_Infor_Shared;//쉐어드에서 갖고온 전체 모집글 리스트
    //key: 모집 참여자의 id
    ArrayList<Data_StudyRoom_Infor> List_Send_StudyRoom_Shared;//쉐어드에서 갖고온 사용자가 받은 모집글 리스트
    //key: 방 번호
    ArrayList<Data_StudyRoom_Infor> List_Receive_StudyRoom_Shared;//쉐어드에서 갖고온 사용자가 보낸 모집글 리스트

    /*리사이클러뷰*/
    ArrayList<Data_StudyRoom_Infor> List_MyStudyRoom_infor_Recycler;//리사이클러뷰에 데이터 입력용 리스트
    Data_StudyRoom_Infor data_studyRoom_shared;


    String Listkey;
    String tv_roomName;//수정할 모집 글 이름
    String tv_roomContent;//수정할 모집 글 내용
    String tv_roomMakerId;//수정할 모집 글 내용
    int tv_roomNumber;//수정할 모집 글 내용


    AlertDialog dialog;
    public static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_study_room_activitiy);
        set_findView();
        dialog = getDialog();//삭제 할 것이냐고 묻는 다이얼로그
        set_ButtonListener();

        Listkey = SharedClass.ListKey_StudyRoomDATA;//쉐어드에 저장한 데이터리스트의 키
        String Userid = Myapplication.UserId;
        //리사이클러뷰 세팅
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_adapter_MystudyRoom = new Rv_Adapter_MyRoom(getApplicationContext());
        rv_MystudyRoom.setLayoutManager(linearLayoutManager);//linearlayout 세팅
        rv_MystudyRoom.setAdapter(rv_adapter_MystudyRoom);//adapter 세팅
        List_MyStudyRoom_infor_Recycler = rv_adapter_MystudyRoom.getList();


        //전체 모집 글 정보 불러오기
        try {
            List_AllStudyRoom_Infor_Shared = SharedClass.get_List_StudyRoom_Infor_json(getApplicationContext(), Listkey, SharedClass.PREFERENCES_Name_Room_infor);
            System.out.println("쉐어드 데이터 불러오기 성공_MyStudyRoom");
        } catch (JSONException e) {
            System.out.println("쉐어드 데이터 불러오기 실패__MyStudyRoom");
        }


        //전체 모집글 정보에서 내가 만든 모집 글 정보의 데이터만 리사이클러뷰에 입력하기.
        if (List_AllStudyRoom_Infor_Shared != null) {
            //전체 모집글 비교
            for (int i = 0; i < List_AllStudyRoom_Infor_Shared.size(); i++) {
                data_studyRoom_shared = List_AllStudyRoom_Infor_Shared.get(i);
                //내 아이디로 저장된 모집글 정보만 찾는다.
                if (data_studyRoom_shared.room_Maker_Id.equals(Myapplication.UserId)) {
                    //쉐어드에서 불러온 데이터를  리사이클러뷰의 아이템뷰에 저장
                    rv_adapter_MystudyRoom.addItem(new Data_StudyRoom_Infor(data_studyRoom_shared.roomName, data_studyRoom_shared.roomContent,
                            data_studyRoom_shared.room_Maker_Id, data_studyRoom_shared.roomNumber, data_studyRoom_shared.iv_profile_uri,
                            data_studyRoom_shared.tv_nickname, data_studyRoom_shared.studyType));
                    rv_adapter_MystudyRoom.notifyDataSetChanged();

                    //리스트에는 내가 만든 방 정보만 있기 때문에 가능
                    //수정해야할 텍스트 따로 변수에 저장.
                    tv_roomName = data_studyRoom_shared.roomName;//내가만든 모집글 이름
                    tv_roomContent = data_studyRoom_shared.roomContent;//내가만든 모집 글 내용
                    tv_roomMakerId = data_studyRoom_shared.room_Maker_Id;//내가만든 모집 글 만든 사람id
                    tv_roomNumber = data_studyRoom_shared.roomNumber;//내가만든 모집 글 방 번호
                    nickname = data_studyRoom_shared.tv_nickname;//닉네임
                    studyType = data_studyRoom_shared.studyType;//공부타입
                    profile = data_studyRoom_shared.iv_profile_uri;//프로필사진
                }
            }
        }
        RoomNum_key = String.valueOf(tv_roomNumber); //키 값으로 사용하기 위해서 형변환

        /* 내가 받은 모집 글 정보 쉐어드에서 갖고오기*/
        try {
            List_Receive_StudyRoom_Shared = SharedClass.get_List_StudyRoom_Joiner_json(getApplicationContext(), RoomNum_key, SharedClass.PREFERENCES_Receive_StudyRoom_Data);
        } catch (JSONException e) {
        }


        rv_adapter_MystudyRoom.setOnItemClickListener(new Rv_Adapter_MyRoom.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //클릭한 아이템의 포지션 반환.
                itemview_position = position;
                //편집이나 삭제 텍스트를 눌렀을 때
                //번호는 Myadapter에서 static변수로 지정했음
                /*1번 작성 2번 삭제 3번 편집*/
                if (Myapplication.num_StudyMyRoom_add_edit_remove == 3) {//3번 편집
                    Myapplication.RoomName = tv_roomName;//수정 할 모집글 이름
                    Myapplication.RoomContent = tv_roomContent;//수정할 모집글 내용
                    Intent intent = new Intent(getApplicationContext(), Create_Room_StudyFriend_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                    //아이템뷰 에서 삭제 버튼을 누른경우
                }
                //팝업 뷰안에 있는 삭제버튼  삭제 버튼을 누를경우 ==2번이 입력된다.
                else if (Myapplication.num_StudyMyRoom_add_edit_remove == 2) {//2번 삭제

                    //다이어로그에서 삭제 버튼을 누른 경우
                    btn_remove_StudyRoom_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            /*1) 전체 모집글에서 삭제하려는 모집 글 삭제*/
                            //쉐어드에 저장된 리스트에서 내가만든 작성글의 아이디가 내 id인 것만 삭제
                            for (int i = 0; i < List_AllStudyRoom_Infor_Shared.size(); i++) {
                                if (List_MyStudyRoom_infor_Recycler.size() != 0) {//데이터가 삭제된 경우에는 종료
                                    data_studyRoom_shared = List_AllStudyRoom_Infor_Shared.get(i);//쉐어드에서 같고온 리스트안에 담긴 객체
                                    //쉐어드에 저장된 만든 사람 Id 중에 내 아이디인 것만 삭제.
                                    if (data_studyRoom_shared.room_Maker_Id.equals(List_MyStudyRoom_infor_Recycler.get(0).room_Maker_Id)) {
                                        //리사이클러뷰 전체 모집글 리스트에서 아이템 삭제 (내 id로 저장된)
                                        rv_adapter_MystudyRoom.removeItem(position);
                                        //쉐어드에 저장된 전체 모집글 리스트에도 아이템 삭제
                                        List_AllStudyRoom_Infor_Shared.remove(i);
                                        rv_adapter_MystudyRoom.notifyDataSetChanged();
                                    }
                                }
                            }
                            /*2) 쉐어드에 저장된 받은 모집글 리스트 삭제 key: 방 번호 */
                            SharedClass.remove_key(getApplicationContext(), RoomNum_key, SharedClass.PREFERENCES_Receive_StudyRoom_Data);


                            //리사이클러뷰의 아이템뷰에 리스트가 null 아닌 경우에만.
                            //삭제된 것을 수정한 전체 모집 글 정보 리스트를 다시 쉐어드에 저장
                            if (List_MyStudyRoom_infor_Recycler != null) {
                                try {
                                    SharedClass.Save_List_StudyRoom_Infor_json(getApplicationContext(), Listkey, List_AllStudyRoom_Infor_Shared, SharedClass.PREFERENCES_Name_Room_infor);
                                    System.out.println("쉐어드에 데이터 저장 성공_MyStudyRoom_Remove");
                                } catch (JSONException e) {
                                    System.out.println("쉐어드에 데이터 저장 실패_MyStudyRoom_Remove");
                                }
                            }
                            dialog.dismiss();
                        }
                    });


                    //삭제 취소 버튼
                    btn_cancel_StudyRoom_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }


    //필요한 findview 세팅
    public void set_findView() {
        rv_MystudyRoom = findViewById(R.id.rv_mystudyroom_item);//리사이클러뷰 findView
        btn_all_StudyRoom = findViewById(R.id.btn_all_studyroom);
        backicon_Mystudyroom = findViewById(R.id.iv_backicon_Mystudy_room);
    }

    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_all_StudyRoom.setOnClickListener(this);
        backicon_Mystudyroom.setOnClickListener(this);
        btn_remove_StudyRoom_dialog.setOnClickListener(this);
        btn_cancel_StudyRoom_dialog.setOnClickListener(this);
    }

    public AlertDialog getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        //다이얼로그에  들어갈 레이아웃 xml 파일 입력
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.remove_dialog, null, false);
        builder.setView(view);
        //다이어 로그에 들어갈 view 추가
        btn_remove_StudyRoom_dialog = view.findViewById(R.id.btn_remove_myStudyRoom);
        btn_cancel_StudyRoom_dialog = view.findViewById(R.id.btn_cancel_myStudyRoom);

        dialog = builder.create();//다이어로그 생성
        //버튼 클릭 이벤트 지정
        return dialog;
    }

    @Override
    //다른 엑티비티에서 저장한 데이터를 갖고오는 메서드
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //수정한 텍스트를 갖고온다.

        //엑티비티에서 갖고온 데이터가 있을 때만 실행
        if (requestCode == REQUEST_CODE && Myapplication.RoomName != null) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            //편집해온 데이터를 리사이클러뷰의 아이템뷰에 수정한다.
            //수정해야할 변수 : 1)모임이름 2)모임 정보
            rv_adapter_MystudyRoom.setItem(itemview_position, new Data_StudyRoom_Infor(Myapplication.RoomName, Myapplication.RoomContent, Myapplication.UserId,
                    tv_roomNumber, profile, nickname, studyType));
            rv_adapter_MystudyRoom.notifyDataSetChanged();
            //쉐어드에 있는 데이터 리스트도 수정한다.
            for (int i = 0; i < List_AllStudyRoom_Infor_Shared.size(); i++) {
                data_studyRoom_shared = List_AllStudyRoom_Infor_Shared.get(i);
                //모임방이 하나여서 가능..
                //쉐어드에 저장된 모임글 내 모임글만  수정한다. (만든사람 id를 이용해서)
                if (data_studyRoom_shared.room_Maker_Id.equals(Myapplication.UserId)) {
                    //수정해야할 변수 : 1)모임이름 2)모임 정보
                    List_AllStudyRoom_Infor_Shared.set(i, new Data_StudyRoom_Infor(Myapplication.RoomName, Myapplication.RoomContent, Myapplication.UserId,
                            tv_roomNumber, profile, nickname, studyType));
                }
            }
            //
            //쉐어드에서 불러왔던 리스트를 수정했으니 쉐어드에 다시 리스트를 저장한다.
            if (List_MyStudyRoom_infor_Recycler != null) {
                try {
                    SharedClass.Save_List_StudyRoom_Infor_json(getApplicationContext(), Listkey, List_AllStudyRoom_Infor_Shared, SharedClass.PREFERENCES_Name_Room_infor);
                    System.out.println("쉐어드에 데이터 저장 성공_MyStudyRoom_Remove");
                } catch (JSONException e) {
                    System.out.println("쉐어드에 데이터 저장 실패_MyStudyRoom_Remove");
                }
            }
        }
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all_studyroom://,전체 모집 글 엑티비티로 이동
                Intent intent = new Intent(getApplicationContext(), All_StudyRoom_Activity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.iv_backicon_Mystudy_room://뒤로가기
                Intent intent2 = new Intent(getApplicationContext(), All_StudyRoom_Activity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}