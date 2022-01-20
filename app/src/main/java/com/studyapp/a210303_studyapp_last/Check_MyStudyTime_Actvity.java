package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Check_MyStudyTime_Actvity extends AppCompatActivity implements View.OnClickListener {
    private static final float FONT_SIZE = 20;//과목별 공부시간, 이름 글자크기
    Button btn_home, btn_friend, btn_rank;
    TextView tv_Today, tv_StudyTime, tv_startTime, tv_endTime;
    CalendarView cv;
    //헷깔릴까봐 2개 놓은 것임.
    ArrayList<Data_Measure_StudyTime> List_Measure;//전체 공부시간 통계리스트
    ArrayList<Data_Measure_StudyTime> List_Measure_today;//전체 공부시간 통계리스트

    ArrayList<Data_StudyPlan> List_StudyPlan;// 과목별 공부시간이 저장된 공부목표 리스트

    String Date_key = "";
    TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 한국 표준시 설정
    String str_Today = "";
    String str_Today_Measure_Key = "";//공부시간 통계리스트를 불러오는 키 (측정 날짜)
    Date date;
    private LinearLayout li_subject;//과목별 공부시간에 사용될 리니어레이아웃.
    int int_startday;
    int int_startmonth;
    int int_startyear;
    int check_allstudyTime = 0;
    int check_substudyTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_my_study_time_actvity);
        set_findView();
        set_ButtonListener();

        //***과목별 공부시간 ***
        //공부시간 통계리스트를 불러오는 키 (측정한 날짜)
        str_Today_Measure_Key = Myapplication.getTodayDate(tz);


        //*** 전체 공부시간 ***
        //쉐어드에서 측정한 [전체 공부시간] 리스트 갖고오기.
        try {
            List_Measure_today = SharedClass.get_List_MeasureTime_json(getApplicationContext(), str_Today_Measure_Key, SharedClass.PREFERENCES_Measure_StudyTime);
        } catch (JSONException e) {
        }

        //쉐어드에서 [공부 과목정보]리스트 갖고오기.
        try {
            List_StudyPlan = SharedClass.get_List_StudyGoal_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_StudyGoal_Data);
        } catch (JSONException e) {
        }


        //오늘 날짜에 [전체 공부시간] 텍스트뷰에 입력하기
        //오늘 날짜에 전체 공부시간이 리스텡 있는지 확인.
        //오늘 날짜에 공부시간 데이터가 있는 경우만
        if (List_Measure_today != null) {
            //리스트에 저장된 전체 공부시간 중 내 아이디로 저장된 공부시간만 입력한다.
            for (int i = 0; i < List_Measure_today.size(); i++) {
                if (List_Measure_today.get(i).made_id.equals(Myapplication.UserId)) {
                    //클릭한 날짜의 전체공부시간, 공부종료 시간이 있는 경우
                    //클릭한 날짜의 했던 전체 공부시간을 보여준다.
                    tv_StudyTime.setText(List_Measure_today.get(i).Measure_Time);//전체 공부시간
                    tv_startTime.setText(List_Measure_today.get(i).Start_Measure_Time);//공부 시작시간
                    tv_endTime.setText(List_Measure_today.get(i).End_Measure_Time);//공부 종료시간
                }
            }

            //쉐어드에 저장된 공부시간이 없을 경우
            if (List_Measure_today.size() == 0) {
                tv_startTime.setText("");//공부 시작시간
                tv_StudyTime.setText("");//전체 공부시간
                tv_endTime.setText("");//공부 종료시간
            }
        }

        //오늘날짜의[과목별 공부시간 입력]
        if (List_StudyPlan != null) {
            //공부 과목 리스트를 비교
            for (int i = 0; i < List_StudyPlan.size(); i++) {
                //오늘 날짜인 과목정보만 갖고온다
                if (List_StudyPlan.get(i).save_Date.equals(Myapplication.today_key)) {
                    //과목별 공부시간 텍스트뷰를 추가한다.
                    tv_subject(List_StudyPlan.get(i).Study_goal, List_StudyPlan.get(i).Study_time);
                }
            }
        }


        //*** 처음 캘린더뷰 안에 날짜 표시 오늘 날짜로 바꾸기. ***
        SimpleDateFormat format_today = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss", Locale.KOREA);
        date = Myapplication.convertKoreanTime(date, format_today);
        cv.setDate(date.getTime());


        //*** 오늘 날짜 텍스트 뷰에 입력하기 ***
        //현재 날짜 포맷
        SimpleDateFormat format_today_2 = new SimpleDateFormat("yyyy:MM:dd", Locale.KOREA);
        //한국 표준시로 지정
        format_today_2.setTimeZone(tz);
        //String변수에 담기
        str_Today = format_today_2.format(new Date());
        //현재 날짜 년도, 월, 일별로 나누주기
        int_startyear = Integer.parseInt(str_Today.substring(0, 4));//요일을 저장한 변수
        int_startmonth = Integer.parseInt(str_Today.substring(5, 7));//요일을 저장한 변수
        int_startday = Integer.parseInt(str_Today.substring(8, 10));//요일을 저장한 변수
        //나눈 년도, 월, 일 현재날짜 텍스트뷰에 입력하기기
        tv_Today.setText(String.format("%d / %d / %d", int_startyear, int_startmonth, int_startday));


        //***클릭한 달력의 날짜, 전체 공부시간, 과목별 공부시간, 공부시작, 공부종료 시간 확인하기 ***
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                li_subject.removeAllViews();//리니어레이아웃 뷰 초기화
                //클릭한 날짜를 보여준다
                Date_key = String.valueOf(year) + String.valueOf(month + 1) + String.valueOf(dayOfMonth);
                tv_Today.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                //쉐어드에서 전체 공부시간 리스트를 불러온다.
                //key : 선택한 날짜
                try {
                    List_Measure = SharedClass.get_List_MeasureTime_json(getApplicationContext(), Date_key, SharedClass.PREFERENCES_Measure_StudyTime);
                } catch (JSONException e) {
                }

                //*** 선택한 날짜의 전체 공부시간 불러오기 ***
                if (List_Measure != null) {
                    //리스트에 저장된 전체 공부시간 중 내 아이디로 저장된 공부시간만 입력한다.
                    for (int i = 0; i < List_Measure.size(); i++) {
                        if (List_Measure.get(i).made_id.equals(Myapplication.UserId)) {
                            //클릭한 날짜의 전체공부시간, 공부종료 시간이 있는 경우
                            //클릭한 날짜의 했던 전체 공부시간을 보여준다.
                            tv_StudyTime.setText(List_Measure.get(i).Measure_Time);//전체 공부시간
                            tv_startTime.setText(List_Measure.get(i).Start_Measure_Time);//전체 공부시간
                            tv_endTime.setText(List_Measure.get(i).End_Measure_Time);//공부 종료시간
                        }
                    }


                    //쉐어드에 저장된 공부시간이 없을 경우
                    if (List_Measure.size() == 0) {
                        tv_StudyTime.setText("");//전체 공부시간
                        tv_endTime.setText("");//공부 종료시간
                        tv_startTime.setText("");//공부시작시간
                    }
                }

                //선택한 날짜의 [과목별 공부시간 정보 입력]
                if (List_StudyPlan != null) {
                    //과목별 공부시간 정보 리스트 비교
                    for (int i = 0; i < List_StudyPlan.size(); i++) {
                        //오늘 날짜인 것만 찾기
                        if (List_StudyPlan.get(i).save_Date.equals(Date_key)) {
                            //과목별 공부시간 텍스브튜 추가
                            tv_subject(List_StudyPlan.get(i).Study_goal, List_StudyPlan.get(i).Study_time);
                        }
                    }
                }
            }
        });
    }

    //과목별 공부시간 텍스트뷰 생성 메서드
    public void tv_subject(String subject_Name, String Subject_time) {
        //TextView 생성
        TextView view1 = new TextView(this);
        view1.setText(subject_Name);
        view1.setTextSize(FONT_SIZE);
        view1.setTextColor(Color.BLACK);

        //textview의 레리아웃 설정
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (0, ViewGroup.LayoutParams.WRAP_CONTENT);
        view1.setLayoutParams(lp);
        lp.weight = 1;
        lp.leftMargin = 200;
        lp.topMargin = 30;

        TextView view2 = new TextView(this);
        view2.setText(Subject_time);
        view2.setTextSize(FONT_SIZE);
        view2.setTextColor(Color.BLACK);

        //textview2의 레리아웃 설정
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams
                (0, ViewGroup.LayoutParams.WRAP_CONTENT);
        view2.setLayoutParams(lp2);
        lp2.weight = 1;
        lp2.topMargin = 30;

        //리니어 레이아웃 생성
        LinearLayout parentLL = new LinearLayout(this);
        //리니어 레이아웃의 사이즈 설정
        parentLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //정렬 방향 설정
        parentLL.setOrientation(LinearLayout.HORIZONTAL);
        //리니어 레이아웃 안에 텍스트뷰 추가
        parentLL.addView(view1);
        parentLL.addView(view2);


        //부모 뷰에 리니어레이아웃 추가.
        li_subject.addView(parentLL);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rank_check://채팅방
                Intent intent = new Intent(getApplicationContext(), Rank_Activity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_home_check://메인화면
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.btn_friend_check://공부친구
                Intent intent3 = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent3);
                finish();
                break;
        }
    }


    //필요한 findview 세팅
    public void set_findView() {
        tv_startTime = findViewById(R.id.startTime_check);//공부시작시간
        tv_endTime = findViewById(R.id.endTime_check);//공부종료시간
        li_subject = findViewById(R.id.li_subject_text);//과목별 공부시간에 사용될 리니어 레이아웃
        cv = findViewById(R.id.calendarView_check);//캘린더뷰
        tv_Today = findViewById(R.id.tv_date_check);//오늘 날짜
        tv_StudyTime = findViewById(R.id.tv_studyTime_Check);//오늘 공부시간
        //하단 메뉴
        btn_rank = findViewById(R.id.btn_rank_check);//채팅방
        btn_home = findViewById(R.id.btn_home_check);//홈
        btn_friend = findViewById(R.id.btn_friend_check);//친구
    }

    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_rank.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        btn_friend.setOnClickListener(this);
    }
}
