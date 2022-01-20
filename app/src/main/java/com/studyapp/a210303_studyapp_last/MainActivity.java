package com.studyapp.a210303_studyapp_last;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /* 스터디플래너 어플 최종  */

    private Rv_adapter_StudyPlan Adapter_StudyPlan;
    private RecyclerView rv_StudyPlan;
    private LinearLayoutManager linearLayoutManager;
    Data_StudyPlan dataStudyPlan;
    Data_StudyPlan dataStudyNumber;
    TextView tv_all_StudyTime;
    TextView tv_date;
    Button btn_friend, btn_check_StudyTime, btn_rank;//하단 메뉴 버튼
    ImageButton btn_Menu; //버튼 메뉴
    PopupMenu popupMenu;
    ImageView iv_add_Study_goal;
    ArrayList<Data_StudyPlan> List_StudyGoal_Shared;
    ArrayList<Data_StudyPlan> List_StudyGoal;
    ArrayList<Data_Measure_StudyTime> List_measure_Shared;//전체 공부시간 통계리스트
    ArrayList<Data_user> List_UserData;
    int StudyGoal_Number; //쉐어드에서 갖고온 공부 목표를 저장하는 방 번호
    int Reset_Count = 0;//초기화 번호
    int Save_Count = 0;//처음 공부시간 저장 숫자
    int subject_count = 0;//날짜가 변했는지 체크하는 변수(과목별 공부시간)
    String Goal_Number;//공부 목표를 저장하는 방 번호
    public static final int REQUEST_CODE = 100;
    Map<String, ?> allKey;
    TimeZone tz;//한국표준시에 맞추기
    String first_StudyTime = "";//처음 공부한 시간
    String study_subject_name = "";
    String end_measure_time = "";
    String str_Today_Main;

    int hour_allstudyTime_shared;
    // 분만 변수에 저장
    int minutes_allstudyTime_shared;
    // 초만 변수에 저장
    int seconds_allstudyTime_shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        set_findView();//1번 //find view 세팅
        set_ButtonListener();//3번: 버튼리스너 세팅
        setRecyclerView();//리사이클러뷰 세팅

        //오늘 날짜 변수에 저장 => 공부시간 통계리스트의 키
        Myapplication.today_key = Myapplication.getTodayDate(tz);

        //쉐어드를 저장하는 공부 목표를 저장하는 키 번호를 갖고오는 메서드
        StudyGoal_Number = SharedClass.getInt(getApplicationContext(), SharedClass.Goal_Number_KEY, SharedClass.PREFERENCES_RoomNum);

        str_Today_Main = Myapplication.getTodayDate2(tz);
        tv_date.setText(str_Today_Main);

        //전체 공부시간을 불러오기 위해
        //쉐어드에서 유저정보 리스트 불러오기
        try {
            List_UserData = SharedClass.get_List_UserData_Json(getApplicationContext(), Myapplication.UserId);
        } catch (JSONException e) {
        }


        //** 참고 **
        //TODAY1
        //년도 + 월 + 일
        //년도를 int 형변환 후 String 형변환하지 않은 이유
        //01, 02, 03이런 식의 값이 나올일이 없기 때문에


        try {
            List_measure_Shared = SharedClass.get_List_MeasureTime_json(getApplicationContext(), Myapplication.today_key, SharedClass.PREFERENCES_Measure_StudyTime);
        } catch (JSONException e) {
        }

        //쉐어드에 내 아이디로 저장된 전체 공부시간 정보가 업는 경우 체크
        for (int i = 0; i < List_measure_Shared.size(); i++) {
            if (List_measure_Shared.get(i).made_id.equals(Myapplication.UserId)) {
                Reset_Count = 1;
            }
        }

        //전체 공부시간 초기화
        //Reset_Count = 1이 아닐 때 공부시간, 전체 공부시간 초기화
        if (Reset_Count != 1) {
            List_UserData.get(0).All_Studyhour = "00 : 00 : 00";

            //전체 공부시간이 00 : 00 : 00이 된것을 저장.
            //쉐어드에 유저 정보 리스트 저장 => 전체 공부 시간이 00 : 00 : 00 으로저장되어있음.
            try {
                SharedClass.Save_List_UserData_json(getApplicationContext(), Myapplication.UserId, List_UserData);
            } catch (JSONException e) {
            }
        }

        /* 전체 공부시간에 쉐어드에서 갖고온 데이터를 입력한다.*/
        tv_all_StudyTime.setText(List_UserData.get(0).All_Studyhour);


        //쉐어드에서 공부 목표 리스트 불러오기
        //키 값: 본인 ID
        try {
            List_StudyGoal_Shared = SharedClass.get_List_StudyGoal_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_StudyGoal_Data);
        } catch (JSONException e) {
            Log.d("MainActivity", "공부목표리스트 불러오기 실패");
        }


        //쉐어드에서 갖고온 데이터가 있을 때만.
        if (List_StudyGoal_Shared != null) {
            for (int i = 0; i < List_StudyGoal_Shared.size(); i++) {
                //쉐어드에 오늘 날짜로 저장된 공부시간이 있을 때
                //날짜가 바뀌지 않았음
                //오늘 날짜와 공부과목 저장 날짜가 같은 것만 리사이클러뷰에 입력
                if (List_StudyGoal_Shared.get(i).save_Date.equals(Myapplication.today_key)) {
                    //리사이클러뷰에 쉐어드에서 갖고온 공부 목표 리스트 입력
                    dataStudyPlan = List_StudyGoal_Shared.get(i);
                    Adapter_StudyPlan.addItem(new Data_StudyPlan(dataStudyPlan.Study_goal, dataStudyPlan.Study_time, dataStudyPlan.Goal_Number,
                            dataStudyPlan.save_Date, dataStudyPlan.studyType));
                    Adapter_StudyPlan.notifyDataSetChanged();
                    //쉐어드에 오늘 날짜로 저장된 공부시간이 있을 때 변수에 번호 1 체크
                    subject_count = 1;
                }
            }

            //쉐어드에 오늘 날짜로 저장된 공부시간이 없을 때
            //어제 날짜 과목별 공부정보의 갯수 만큼 과목정보 생성하고 공부시간 정보 0으로 초기화.
            if (subject_count != 1) {
                //마지막으로 공부과목을 저장한 날짜의 공부정보의 갯수 만큼
                for (int i = 0; i < List_StudyGoal_Shared.size(); i++) {
                    //마지막으로 저장한 과목정보 날짜를 얻는다 => 인덱스의 마지막 번호.만든날짜.
                    String save_Date = List_StudyGoal_Shared.get(List_StudyGoal_Shared.size() - 1).save_Date;
                    //마지막으로 저장한 과목정보 날짜인 과목들만
                    //내 아이디로 저장된 공부 과목 정보중에 마지막으로 저장이 되었던 날짜와 같은 객체를 아이템뷰에 입력
                    if (save_Date.equals(List_StudyGoal_Shared.get(i).save_Date)) {
                        //과목정보에 오늘날짜로 과목정보 리스트에 추가한다.
                        //과목이름, 과목공부시간, 과목번호, 과목저장 날짜
                        //과목 공부시간 = 0 과목저장 날짜 = 오늘날짜.
                        Adapter_StudyPlan.addItem(new Data_StudyPlan(List_StudyGoal_Shared.get(i).Study_goal,
                                "00 : 00 : 00", List_StudyGoal_Shared.get(i).Goal_Number,
                                Myapplication.today_key, List_UserData.get(0).studyType));
                    }
                }


                //쉐어드에 저장할 리스트에
                //리사이클러뷰에 입려된 데이터 리스트 입력하기.
                List_StudyGoal = Adapter_StudyPlan.getList();
                for (int i = 0; i < List_StudyGoal.size(); i++) {
                    List_StudyGoal_Shared.add(new Data_StudyPlan(List_StudyGoal.get(i).Study_goal,
                            List_StudyGoal.get(i).Study_time, List_StudyGoal.get(i).Goal_Number,
                            List_StudyGoal.get(i).save_Date, List_UserData.get(0).studyType));
                }
                subject_count = 0;
            }

            //과목별 공부 정보 리스트 쉐어드에 저장
            try {
                SharedClass.Save_List_StudyGoal_json(getApplicationContext(), Myapplication.UserId, List_StudyGoal_Shared, SharedClass.PREFERENCES_StudyGoal_Data);
            } catch (JSONException e) {
            }
        }


        //아이템뷰 클릭 리스너
        //옵션 이미지를 클릭하면 수정, 삭제 팝업 뷰가 나온다.
        Adapter_StudyPlan.setOnItemClickListener(new Rv_adapter_StudyPlan.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {//인터페이스로 아이템뷰의 position 전달
                //어댑터에서 지정한 아이템뷰 클릭 번호
                //공부 시간 측정 버튼을 누르면 1번이 입력된다.
                if (Myapplication.Num_option_play_Studygoal == 1) {//옵션버튼 1번
                    Myapplication.Num_option_play_Studygoal = 0;
                    dataStudyPlan = Adapter_StudyPlan.getItem(position);//선택한 position에 맞는 아이템뷰 데이터 객체 생성
                    //팝업 뷰 생성 => 수정, 삭제
                    popupMenu = new PopupMenu(getApplicationContext(), v);
                    getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                    //팝업 뷰 클릭 리스너 (수정, 삭제 버튼 있음)
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuitem) {
                            switch (menuitem.getItemId()) {
                                //1)수정 버튼 클릭시
                                case R.id.action_menu_edit://팝업메뉴의 수정버튼 클릭
                                    //2번 : 리사이클러뷰에 아이템뷰 수정
                                    Myapplication.num_StudyGoal_edit_add = 2;//2번 편집
                                    //클릭한 아이템뷰의 텍스트를 static 변수에 담아서 공부 목표 엑티비티로 이동한다.
                                    Myapplication.StudyGoal = dataStudyPlan.Study_goal;//공부 목표

//                                    클릭한 아이템뷰 데이터를 수정 되지않아야 할 변수들은 미리 변수에 저장해놓는다.
                                    Myapplication.StudyTime = dataStudyPlan.Study_time;//공부시간
                                    Myapplication.saveDate = dataStudyPlan.save_Date;//과목별 처음공부시작날짜
                                    Myapplication.itemview_position = position;//아이템뷰 번호


                                    //공부 목표 추가 엑티비티로 이동
                                    Intent intent = new Intent(getApplicationContext(), Create_StudyGoal_Activity.class);
                                    //아이템뷰를 클릭 했을 때의 포지션을 변수에 담는다.
                                    Myapplication.itemview_position = position;
                                    startActivityForResult(intent, REQUEST_CODE);
                                    break;

                                //2)팝업메뉴의 삭제 버튼 클릭
                                case R.id.action_menu_remove:
                                    //1)쉐어드에서 갖고온 리스트에서 클릭한 아이템 객체 삭제
                                    //아이템 리스트를 불러온다.
                                    List_StudyGoal = Adapter_StudyPlan.getList();
                                    //쉐어드에서 불러온 리스트를 비교한다.
                                    for (int i = 0; i < List_StudyGoal_Shared.size(); i++) {
                                        //쉐어드에서 불러온 리스트 안에 객체의 과목만든날짜가 오늘날짜인 것만 가져온다.
                                        if (List_StudyGoal_Shared.get(i).save_Date.equals(Myapplication.today_key)) {
                                            //아이템뷰 리스트를 비교한다.
                                            //아이템뷰 리스트와 쉐어드에서 불러온 리스트의 객체들 중  클릭한 객체와 과목번호가 같은 것만 찾는다.
                                            if (List_StudyGoal_Shared.get(i).Goal_Number == Adapter_StudyPlan.getItem(position).Goal_Number) {
                                                //클릭한 객체의 날짜와 과목번호가 같은 것을 삭제한다.
                                                List_StudyGoal_Shared.remove(i);
                                            }
                                        }
                                    }
                                    //2)아이템 리스트에서 클릭한 객체(아이템)를 삭제.
                                    Adapter_StudyPlan.removeItem(position);
                                    Adapter_StudyPlan.notifyDataSetChanged();

                                    //3)클락한 아이템이 삭제된 쉐어드에서 불러온 리스트를 다시 쉐어드에 저장
                                    try {
                                        SharedClass.Save_List_StudyGoal_json(getApplicationContext(), Myapplication.UserId, List_StudyGoal_Shared, SharedClass.PREFERENCES_StudyGoal_Data);
                                    } catch (JSONException e) {
                                    }

                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();//팝업 메뉴 보이기.

                    //3)공부시간 측정 엑티비티로 이동
                    //어댑터에서 지정한 아이템뷰 클릭 번호
                    //공부 시간 측정 버튼을 누르면 3번이 입력된다.
                } else if (Myapplication.Num_option_play_Studygoal == 3) {//공부 시간 측정 버튼 3번
                    //엑티비티 이동
                    Myapplication.Num_option_play_Studygoal = 0;

                    //*** 공부시간 측정 시작 시간 ***
                    SimpleDateFormat study_Start = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss", Locale.KOREA);
                    tz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 표준시 설정
                    study_Start.setTimeZone(tz);//한국표준시간에 맞추기

                    //공부시간 측정 시작 시간 String변수에 저장.
                    //공부 시간 측정 시작 시간을 쉐어드에 키 값으로 저장한다.
                    //공부시간을 측정을 시작했던 시간을 얻어오기 위해서 사용
                    Myapplication.studyStart_Time = study_Start.format(new Date());//공부시간 측정 시작 시간(시,분,초)

                    //어제 공부시간 측정에 사용할 기준시간(24시) 변수에 저장.
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss");
                    Myapplication.str_24_date = LocalDateTime.now().plusDays(0).with(LocalTime.MAX).format(formatter);// 24시


                    //오늘 날짜로 저장된 쉐어드
                    //1이 아닐때 처음 공부를 시작했던 시간을 쉐어드에 저장한다.
                    //처음 공부시작 시간의 포맷
                    for (int i = 0; i < List_measure_Shared.size(); i++) {
                        //내아이디로 저장된  전체 공부시간 객체만 첫번쨰로 공부한 시간을 리스트에 저장한다.
                        if (Myapplication.UserId.equals(List_measure_Shared.get(i).made_id)) {
                            Save_Count = 1;//초기화 번호 ==> 1번을 경우 초기화 x
                        }
                    }


                    //쉐어드에 내아이디로 저장된 전체 공부시간 객체가 없는 경우
                    //처음 공부한 시간을 저장한다.
                    if (Save_Count != 1) {
                        SimpleDateFormat formatter_first = new SimpleDateFormat("HH시 mm분");
                        formatter_first.setTimeZone(tz);
                        //처음 공부시작 시간 String 변수에 저장
                        first_StudyTime = formatter_first.format(new Date());
                        List_measure_Shared.add(new Data_Measure_StudyTime("", first_StudyTime, "", Myapplication.UserId, Myapplication.UserNickname, "", 0, 0));
                    }


                    //전체 공부시간 통계리스트를 저장한다.
                    try {
                        SharedClass.Save_List_MeasureTime_json(getApplicationContext(), Myapplication.today_key, List_measure_Shared, SharedClass.PREFERENCES_Measure_StudyTime);
                    } catch (JSONException e) {
                    }

                    //쉐어드에 유저 정보 리스트 저장 => 전체 공부 시간이 저장되어있음.
                    try {
                        SharedClass.Save_List_UserData_json(getApplicationContext(), Myapplication.UserId, List_UserData);
                    } catch (JSONException e) {
                    }


                    //클릭한 아이템의 인덱스 => 쉐어드에서 저장한 리스트의 인덱스
                    //공부 시간 측정 버튼을 누를시 3번이 입력된다.
                    for (int i = 0; i < List_StudyGoal_Shared.size(); i++) {
                        //오늘 날짜인 것만 찾기
                        if (List_StudyGoal_Shared.get(i).save_Date.equals(Myapplication.today_key)) {
                            //클릭한 아이템의 과목번호와 쉐어드에서 불러온 리스트의 과목번호가 같은 객체를 찾기
                            if (List_StudyGoal_Shared.get(i).Goal_Number == Adapter_StudyPlan.getItem(position).Goal_Number) {
                                Myapplication.StudyGoal_index = i;//클릭한 아이템의 공부 목표 방번호를 갖고온다
                            }
                        }
                    }

                    Intent intent = new Intent(getApplicationContext(), Measure_StudyTime_Activity.class);
                    startActivity(intent);
                    finish();
                }

            }

        });


    }

    //버튼 클릭 리스너
    @Override
    public void onClick(View v) {
        //하단 메뉴 버튼 클릭 이벤트
        switch (v.getId()) {
            case R.id.btn_friend_main://공부 친구 엑티비티 이동
                Intent intent = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_rank_home://공부랭킹 엑티비티 이동
                Intent intent2 = new Intent(getApplicationContext(), Rank_Activity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.btn_Check_StudyTime://공부시간 통계 버튼
                Intent intent5 = new Intent(getApplicationContext(), Check_MyStudyTime_Actvity.class);
                startActivity(intent5);
                finish();
                break;

            case R.id.iv_Add_Studygoal: ///공부 목표 추가 엑티비티로 이동하는 버튼 (이미지 아이콘)
                Intent intent3 = new Intent(getApplicationContext(), Create_StudyGoal_Activity.class);
                //수정과 추가를 구분하기 위해서
                //1번 : 리사이클러뷰에 아이템뷰 추가
                //2번 : 리사이클러뷰에 아이템뷰 수정
                Myapplication.num_StudyGoal_edit_add = 1;//1번 추가
                startActivityForResult(intent3, REQUEST_CODE);//공부 목표 데이터를 받아온다.
                break;

            case R.id.btn_menu_MainActivity://메뉴 버튼
                Intent intent4 = new Intent(getApplicationContext(), Menu_Activity.class);
                startActivity(intent4);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //엑티비티에서 갖고온 데이터가 있을 때만 실행
        if (requestCode == REQUEST_CODE && Myapplication.StudyGoal != null) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            //공부 목표 추가 및 편집 if 문
            if (Myapplication.num_StudyGoal_edit_add == 1) {//1번 추가
                //리사이클러뷰에 데이터 추가
                //공부 목표, 공부 시간, 방 번호
                //방 번호는 쉐어드에서 따로 갖고온 변수를 저장한다. 중복을 피하기 위해서.
                Adapter_StudyPlan.addItem(new Data_StudyPlan(Myapplication.StudyGoal, "00 : 00 : 00", StudyGoal_Number, Myapplication.today_key, List_UserData.get(0).studyType));
                Adapter_StudyPlan.notifyDataSetChanged();

                /*1)공부목표 추가한 것 쉐어드에서 불러온 리스트에 저장하기*/
                List_StudyGoal_Shared.add(new Data_StudyPlan(Myapplication.StudyGoal, "00 : 00 : 00", StudyGoal_Number, Myapplication.today_key, List_UserData.get(0).studyType));

                //쉐어드에 방 번호를 ++1 증가 시켜서 저장한다.
                SharedClass.saveInt(getApplicationContext(), SharedClass.Goal_Number_KEY, ++StudyGoal_Number, SharedClass.PREFERENCES_RoomNum);

            } else if (Myapplication.num_StudyGoal_edit_add == 2) {//2번 편집
                //리사이클러뷰 아이템 데이터 편집
                //클릭한 아이템의 포지션을 변수에 담는다.
                int position = Myapplication.itemview_position;
                //리사이클러뷰에 데이터 수정
                //문제점
                Adapter_StudyPlan.setItem(position, new Data_StudyPlan(Myapplication.StudyGoal, Myapplication.StudyTime, Adapter_StudyPlan.getItem(position).Goal_Number,
                        Myapplication.saveDate, List_UserData.get(0).studyType));
                Adapter_StudyPlan.notifyDataSetChanged();

                //아이템뷰 리스트를 불러온다 (리사이클러뷰)
                List_StudyGoal = Adapter_StudyPlan.getList();
                for (int i = 0; i < List_StudyGoal_Shared.size(); i++) {
                    //오늘 날짜와 같은 객체만 불러온다
                    if (List_StudyGoal_Shared.get(i).save_Date.equals(Myapplication.today_key)) {
                        //아이템뷰 리스트 전체와 쉐어드에서 불러온 리스트의 객체중에 오늘날짜인 객체와 비교
                        for (int j = 0; j < List_StudyGoal.size(); j++) {
                            //오늘 날짜와 같은 객체 중에 아이템뷰 리스트 객체들과 과목번호가 같은 것만 쉐어드에서 불러온 리스트에서 수정한다.
                            if (List_StudyGoal_Shared.get(i).Goal_Number == List_StudyGoal.get(j).Goal_Number) {
                                //과목이름과 날짜가 아이템뷰 객체와 같은 객체를 수정
                                List_StudyGoal_Shared.set(i, new Data_StudyPlan(List_StudyGoal.get(j).Study_goal, List_StudyGoal.get(j).Study_time, List_StudyGoal.get(j).Goal_Number,
                                        List_StudyGoal.get(j).save_Date, List_UserData.get(0).studyType));
                            }
                        }
                    }
                }
            }

            //쉐어드에 내 공부 목표 데이터 저장.
            //추가나 수정한 후에 데이터 쉐어드에 저장.
            try {
                //쉐어드에 공부 목표 리스트 저장.
                SharedClass.Save_List_StudyGoal_json(getApplicationContext(), Myapplication.UserId, List_StudyGoal_Shared, SharedClass.PREFERENCES_StudyGoal_Data);
            } catch (JSONException e) {
            }
        }
    }


    //find view
    public void set_findView() {
        tv_all_StudyTime = findViewById(R.id.tv_all_Study_Time);//전체 공부시간
        iv_add_Study_goal = findViewById(R.id.iv_Add_Studygoal);//공부 목표 추가 이미지 아이콘
        btn_friend = findViewById(R.id.btn_friend_main);//하단 공부친구 버튼
        btn_rank = findViewById(R.id.btn_rank_home);//하단 채팅방 기능
        rv_StudyPlan = findViewById(R.id.rv_studyPlan);//리사이클러뷰
        btn_Menu = findViewById(R.id.btn_menu_MainActivity);//메뉴 버튼
        btn_check_StudyTime = findViewById(R.id.btn_Check_StudyTime);//공부시간 통계 버튼
        tv_date = findViewById(R.id.tv_date);
    }

    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //하단 메뉴 버튼 리스너
        iv_add_Study_goal.setOnClickListener(this);
        btn_friend.setOnClickListener(this);
        btn_rank.setOnClickListener(this);
        btn_Menu.setOnClickListener(this);
        btn_check_StudyTime.setOnClickListener(this);
    }


    public void setRecyclerView() { //리사이클러뷰 세팅
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Adapter_StudyPlan = new Rv_adapter_StudyPlan(getApplicationContext());
        rv_StudyPlan.setLayoutManager(linearLayoutManager);//linearlayout 세팅
        rv_StudyPlan.setAdapter(Adapter_StudyPlan);//adapter 세팅
    }
}


