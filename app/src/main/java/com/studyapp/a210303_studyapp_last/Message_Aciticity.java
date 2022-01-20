package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Message_Aciticity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_send_message, iv_backicon;
    //공부 친구 끼리 보낸 메세지 리스트
    ArrayList<Data_Message> List_Message_shared;
    ArrayList<Data_Message> List_Message;
    //공부 친구 정보리스트
    ArrayList<Data_StudyRoom_Infor> List_StudyFriend;
    ArrayList<Data_user> List_user;
    Data_StudyRoom_Infor data_friend;
    Data_Message data_message;
    RecyclerView rv_message;
    Rv_adapter_Message rv_adapter_message;
    LinearLayoutManager layoutManager;
    EditText ed_sendMessage;//내가 보낸 문자메세지
    String str_send_message; //내가 보낸 문자메세지 string
    String message_List_KEY;//문자 메세지 정보 리스트의 키
    TimeZone tz;//한국표준시간
    String send_time; //보낸 시간
    Date date;
    String str_todaydate;
    int checkDate;//오늘날짜로 보낸 문자가 있는지 확인하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_aciticity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        set_findView();
        set_ButtonListener();
        //처음 => 문자 보내기 버튼 숨기기
        Myapplication.iv_Invisible(iv_send_message);

        //문자메세지 리스트의 키 값이 있는 공부친구 정보 리스트를 쉐어드에서 불러온다.
        try {
            //key : 내아이디
            List_StudyFriend = SharedClass.get_List_MyStudyFriend_Infor_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_MyStudyFriend_Data);
        } catch (JSONException e) {
        }

        //사용자 정보리스트 불러오기
        try {
            List_user = SharedClass.get_List_UserData_Json(getApplicationContext(), Myapplication.UserId);
        } catch (JSONException e) {
        }

        //리사이클러뷰 세팅
        rv_message = findViewById(R.id.rv_Message);//리사이클러뷰 findView
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_adapter_message = new Rv_adapter_Message(getApplicationContext());
        rv_message.setLayoutManager(layoutManager);//linearlayout 세팅
        rv_message.setAdapter(rv_adapter_message);//adapter 세팅

        //***공부친구를 한명 밖에 만들 수 없기 때문에 가능 ***
        //무조건 0번 인덱스에 처음에 저장되기 때문에.
        message_List_KEY = String.valueOf(List_StudyFriend.get(0).roomNumber);
        //쉐어드에서 문자메세지 리스트 불러오기
        try {
            List_Message_shared = SharedClass.get_List_message_json(getApplicationContext(), message_List_KEY, SharedClass.PREFERENCES_Message);
        } catch (JSONException e) {
        }


        if (List_Message_shared != null) {
            //이전에 보냈던 문자메세지 리사이클러뷰에 입력하기
            for (int i = 0; i < List_Message_shared.size(); i++) {
                //보낸 문자메세지, 보낸 사람 id
                rv_adapter_message.addItem(new Data_Message(List_Message_shared.get(i).send_message, List_Message_shared.get(i).send_Time,
                        List_Message_shared.get(i).send_Id, List_Message_shared.get(i).send_profile, List_Message_shared.get(i).send_Nickname,
                        List_Message_shared.get(i).send_Date, List_Message_shared.get(i).firstMessage));
            }
            rv_adapter_message.notifyDataSetChanged();
        }

        //보낸 메세지가 있을 때
        if (rv_adapter_message.getSize() != 0) {
            rv_message.smoothScrollToPosition(rv_adapter_message.getSize() - 1);
        }

        ed_sendMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력된 글자수가 있으면 버튼 보이기
                if (s.length() == 0) {
                    //입력된 글자가 없으면 버튼 안보이기.
                    Myapplication.iv_Invisible(iv_send_message);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                //보내기 이미지 보이기
                if (s.length() > 0) {
                    Myapplication.iv_visible(iv_send_message);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 입력이 끝났을 때
                //버튼 숨기기
                if (s.length() == 0) {
                    Myapplication.iv_Invisible(iv_send_message);
                }
            }
        });
    }

    //필요한 findview 세팅
    public void set_findView() {
        iv_backicon = findViewById(R.id.iv_backicon_Message);
        iv_send_message = findViewById(R.id.iv_sendMessage);
        ed_sendMessage = findViewById(R.id.ed_view_Message);
    }

    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        iv_backicon.setOnClickListener(this);//
        iv_send_message.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sendMessage://메세지 보내기

                //메세지 보낸 시간 포맷
                SimpleDateFormat study_Start = new SimpleDateFormat("보낸시간 HH:mm", Locale.KOREA);
                //메세지 보낸 날짜 포맷
                SimpleDateFormat format_date = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);

                tz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 표준시 설정
                study_Start.setTimeZone(tz);//한국표준시간에 맞추기
                format_date.setTimeZone(tz);
                str_todaydate = format_date.format(new Date());//보낸 날짜
                send_time = study_Start.format(new Date());//보낸 시간

                str_send_message = String.valueOf(ed_sendMessage.getText());//입력한 문자메세지
                //내가 보낸 문자메세지 리사이클러뷰 아이템에 입력
                //보낸 메세지, 보낸 사람아이디, 보낸 시간
                try {
                    List_Message_shared = SharedClass.get_List_message_json(getApplicationContext(), message_List_KEY, SharedClass.PREFERENCES_Message);
                } catch (JSONException e) {
                }

                /*오늘 날짜에 처음으로 문자를 보내는 경우 구분하기
                 * 1) 쉐어드에 저장된 채팅 내역 리스트에서 보낸 날짜 == 오늘 날짜를 비교한다.
                 * 2) 보낸 날짜와 == 오늘날짜가 하나라도 같은 것이 있으면 ==> checkDate변수가 1이 된다.
                 * 3) 보낸날짜와 == 오늘날짜가 일치하는 것이 없으면 ==> checkDate 변수가 0이 된다.
                 * 4) 0이 되면 리사이클러뷰 어댑터의 binding 할때 오늘날짜TextView를 보이게 한다. 반대의 경우 오늘날짜를 숨긴다.
                 *
                 * */

                /*내가 보낸 메세지 상대방이 보낸 메세지 구분하기
                 * 1)리사이클러뷰 어댑터 클래스에의 getItemviewType메서드에서 item.send_Id.equals(내아이디)
                 * 2) true => 1번 xml(내가 보낸 메세지)
                 * 3) false => 2번 xml(다른 사람이 보낸 메세지)
                 * 4)위와 같이 뷰타입에 따라서 다른 layout이 생성되게 한다.
                 * */


                //오늘 날짜에 보낸 메세지가 있는지 확인
                for (int i = 0; i < List_Message_shared.size(); i++) {
                    //오늘 날짜로 보낸 문자가 있는 경우
                    if (str_todaydate.equals(List_Message_shared.get(i).send_Date)) {
                        checkDate = 1;//오늘 날짜로 보낸 문자가 있는지 확인하는 변수에 체크
                    }
                }

                /*메세지 리사이클러뷰에 입력*/
                //1)처음 메세지를 보낸 경우
                //채팅내역List ==0
                if (List_Message_shared.size() == 0) {
                    rv_adapter_message.addItem(new Data_Message(str_send_message, send_time, Myapplication.UserId,
                            List_user.get(0).iv_profile_User, List_user.get(0).nickname_User, str_todaydate, "1"));
                    rv_adapter_message.notifyDataSetChanged();
                } else {//1-2)처음 메세지를 보내지 않는 경우
                    //2-1)오늘 날짜로 보낸 문자가 있는 경우
                    //firstMessage = 0
                    if (checkDate == 1) {
                        rv_adapter_message.addItem(new Data_Message(str_send_message, send_time, Myapplication.UserId, List_user.get(0).iv_profile_User,
                                List_user.get(0).nickname_User, str_todaydate, "0"));
                        rv_adapter_message.notifyDataSetChanged();
                    } else {
                        //2-2)오늘 날짜로 보낸 문자가 없는 경우
                        //firstMessage = 1
                        rv_adapter_message.addItem(new Data_Message(str_send_message, send_time, Myapplication.UserId, List_user.get(0).iv_profile_User,
                                List_user.get(0).nickname_User, str_todaydate, "1"));
                        rv_adapter_message.notifyDataSetChanged();
                    }
                }

                checkDate = 0;//다시 초기화


                //내가 보낸 문자 정보 리스트 갖고오기
                List_Message = rv_adapter_message.getList();
                //내가 보낸 문자 메세지 리스트에 저장후에 쉐어드에 저장
                try {
                    SharedClass.Save_List_message_json(getApplicationContext(), message_List_KEY, List_Message, SharedClass.PREFERENCES_Message);
                } catch (JSONException e) {
                }
                //보낸 후에 내가 보낸 메세지 없애기
                ed_sendMessage.setText("");
                rv_message.smoothScrollToPosition(rv_adapter_message.getSize() - 1);
                break;

            case R.id.iv_backicon_Message://뒤로가기
                Intent intent2 = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }


}