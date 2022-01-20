package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Measure_StudyTime_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_Stop;
    //    TextView tv_All_StudyTime;//전체공부시간
    String str_All_StudyTime_shared;//쉐어드에서 불러온 전체공부시간
    String tv_StudyTime_Shared;//쉐어드에서 가져온 과목별 공부시간
    String str_studyEndTime;//공부시간 측정 종료 날짜
    //    String str_studyStartTime;//공부시간 측정 시작 날짜
    TextView tv_StudyTime;
    TextView tv_Subject;
    Handler handler;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    int Seconds, Minutes, MilliSeconds, hour;
    int addTime;
    int seconds_Subject_shared, minutes_Subject_shared, hour_Subject_shared;//쉐어드에서 갖고온 과목별 공부 시간
    int seconds_sub, minutes_sub, hour_sub;//과목별 공부시간
    int all_seconds_sub, all_minutes_sub, all_hour_sub;//전체 공부 시간
    int all_seconds_seconds, all_minutes_seconds, all_hour_seconds;//전체 공부 시간
    int add_seconds;
    int day_startDate;//공부 시작 날자
    int addMinute, addSecond;
    int allStudyTime_rank;//등수 매길때 사용할 전체 공부시간

    String str_allStudyTime_yes = "";//어제 전체 공부시간
    String str_allStudyTime_today = "";//오늘 전체 공부시간
    String subStudyTime = "";//과목별 공부시간
    String Subject = "";//공부과목
    String saveDate = "";//공부과목저장날짜
    int roomNumber;//클릭한 아이템의 번호
    int RoomIndex;//클릭한 아이템이 저장된 리스트의 인덱스 번호
    Map<String, ?> allKey;//쉐어드에 저장된 모든 키 값을 불러온다.

    ArrayList<Data_StudyPlan> List_StudyPlan;//공부 목표 리스트
    ArrayList<Data_user> List_UserData;//유저 아이디 리스트
    ArrayList<Data_Measure_StudyTime> List_Measure_StudyTime;// 전체 공부시간 통계 리스트
    ArrayList<Data_Measure_StudyTime> List_Measure_StudyTime_today = new ArrayList<>();// 전체 어제 공부시간 통계 리스트
    //만약 쉐어드로 갖고오면 계속해서 추가할떄마다 과목들이 추가되어서 골치아파짐.
    Data_StudyPlan data_studyPlan;
    Data_user data_user;
    int Reset_Count = 0;//초기화 번호

    /*
    boolean Second_add = false;
    boolean Minute_hour_add = false;

    boolean Suspend = true;
    boolean Stop = false;
*/

    AlertDialog dialog; //공부 시간 확인 다이얼로그
    Button btn_enter;//다이얼로그 확인버튼
    TextView tv_studySubject_dia;//다디얼로그 공부과목 텍스트
    TextView tv_studyTime_dia;//다디얼로그 공부시간 텍스트

    TimeZone tz;
    Date start_Time_date;
    Date end_Time_date;
    Date time_24_date;
    Date time_0_date;
    int checknum_List;
    int checknum_List_sub;//1)오늘날짜로 저장된 과목별 공부정보가 통계 리스트에  없는 경우
    int checknum_List_sub_measure;//2)과목별 공부정보 통계리스트에 과목이 없는 경우


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate", "시작");
        setContentView(R.layout.measure_study_time_acitivity);
        set_findView();
        set_ButtonListener();

        //쉐어드에서 공부 목표 리스트를 가져온다.
        try {
            List_StudyPlan = SharedClass.get_List_StudyGoal_json(getApplicationContext(), Myapplication.UserId, SharedClass.PREFERENCES_StudyGoal_Data);
        } catch (JSONException e) {
        }

        //전체 공부시간을 불러오기 위해
        //쉐어드에서 유저정보 리스트 불러오기
        try {
            List_UserData = SharedClass.get_List_UserData_Json(getApplicationContext(), Myapplication.UserId);
        } catch (JSONException e) {
        }


        //쉐어드에서 갖고온 전체공부시간을 String변수에 담는다.
        str_All_StudyTime_shared = List_UserData.get(0).All_Studyhour;


        /*과목별 공부시간 측정*/
        if (List_StudyPlan != null) {
            //쉐어드에서 불러온 과목정보 리스트를 비교한다
            for (int i = 0; i < List_StudyPlan.size(); i++) {
                //과목정보 리스트에서 클릭한 객체를 불러온다
                data_studyPlan = List_StudyPlan.get(Myapplication.StudyGoal_index);
                //클릭한 객체의 공부시간을 변수에 저장한다
                tv_StudyTime_Shared = data_studyPlan.Study_time;
                //1)과목별 공부시간 TextView에 입력
                // 과목별 공부시간만 변수에 저장
                hour_Subject_shared = Integer.parseInt(tv_StudyTime_Shared.substring(0, 2));
                // 분만 변수에 저장
                minutes_Subject_shared = Integer.parseInt(tv_StudyTime_Shared.substring(5, 7));
                // 초만 변수에 저장
                seconds_Subject_shared = Integer.parseInt(tv_StudyTime_Shared.substring(10, 12));
                //2)쉐어드에서 가져온 공부 과목 변수에 저장
                Subject = data_studyPlan.Study_goal;//공부 과목
                roomNumber = data_studyPlan.Goal_Number;//과목 번호
                saveDate = data_studyPlan.save_Date;//과목 저장날짜

                //공부 과목 TextView에 입력
                tv_Subject.setText(Subject);
            }
        }


        handler = new Handler();//핸들러
        StartTime = SystemClock.uptimeMillis();//시간을 얻어온다.
        handler.post(runnable);//스탑워치 스레드 시작
    }

    //스톱워치 실행
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("run메서드 시작");
            //타이머 시작시 처음 시간을 0으로 만들기 위해서
            //SystemClock.uptimeMillis() : 현재시간
//            StartTime : 시작시간


            //(경과 시간 - 시작 시간)의 차이를 이용해서 초를 구한다.
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            /*시간을 입력하는 과정
             * 1)밀리세컨이 1000이 되서 1초를 반환한다.
             * 2)1초가 5초가 되면 => 분을 반환한다.
             * 3)분이 5분이 되면 1시간을 반환한다
             * */
            //초
            Seconds = (int) (MillisecondTime / 1000);//1초는 1000ms이기 때문에 /1000을 한다.
            //분
            Minutes = (int) (MillisecondTime / 60000);

            System.out.println("분:" + Minutes);
            //시간
            hour = Minutes / 60; //시간
            //분이 일정값이 되면 시간 올려주기.
            //나누기 했을 때 값이 1미만이면
            //나머지 값이 아닌 분모값을 반환한다.
            //분모: Seconds 분자: 10

            Minutes = Minutes % 60;// 분 초기화
            Seconds = Seconds % 60; // 초 초기화


            //공부시간을 쉐어드에서 갖고온 시, 분, 초에 맞게 입력한다.
            //처음에 쉐어드에서 갖고온 자릿수 그대로 유지 되어야 한다.
            //*측정한 공부시간*
            tv_StudyTime.setText(String.format("%02d", hour) + " : "
                    + String.format("%02d", Minutes) + " : "
                    + String.format("%02d", Seconds));
            handler.postDelayed(this, 0);
        }
    };


    //필요한 findview 세팅
    public void set_findView() {
        iv_Stop = findViewById(R.id.iv_stop_Measure);
//        tv_All_StudyTime = findViewById(R.id.tv_All_studyTime_Measure);
        tv_StudyTime = findViewById(R.id.tv_time_subject_Measure);
        tv_Subject = findViewById(R.id.tv_studySubject_Measure);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        iv_Stop.setOnClickListener(this);//
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_stop_Measure://공부 시간 측정 중지
                //공부시간 측정 종료
                handler.removeCallbacks(runnable);//핸들러 종료
                tz = TimeZone.getTimeZone("Asia/Seoul");
                //공부 시간 계산용 기준시간(24시 , 0시)
                SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss", Locale.KOREA);
                //공부 시작, 종료시간 포맷
                SimpleDateFormat format2 = new SimpleDateFormat("HH시 mm분", Locale.KOREA);
                start_Time_date = Myapplication.convertTime(start_Time_date, Myapplication.studyStart_Time);//공부시간 측정 시작시간_date
                str_studyEndTime = Myapplication.str_convertKoreanTime(format2);//공부시간 측정 종료시간_date

                //통계 리스트에 입력할 공부시작 시간, 공부 종료시간
                //공부시작 시간, 종료시간 String으로 변환



                //전체 공부시간 통계리스트 불러오기
                try {
                    List_Measure_StudyTime = SharedClass.get_List_MeasureTime_json(getApplicationContext(), Myapplication.today_key, SharedClass.PREFERENCES_Measure_StudyTime);
                } catch (JSONException e) {
                }
                /*1.공부시간 측정 시작 일을 subString으로 구한다.*/
                /*2.측정한 공부시간을 전체 공부시간에 더해준다.
                 * 1)공부시간을 요일을 넘겨서 측정한 경우를 생각한다.
                 * */
                Myapplication.studyEnd_Day = Myapplication.getTodayDate(tz);

                //공부시간 측정 시작 시간 == 공부시간 측정 종료 시간
                if (Myapplication.today_key.equals(Myapplication.studyEnd_Day)) {
                    //측정된 과목별 공부시간.
                    //2)측정한 공부한 시간을 String으로 형변환
                    //2-1)SubString을 사용해서 시간, 분, 초로 나누고 int로 형변환시킨다.
                    String str_StudyTime = String.valueOf(tv_StudyTime.getText());
                    //3)현재 공부한 시간을 각각 subString으로 나눈 후에 int로 형변환한다. (계산하기 위해서)
                    hour_sub = Integer.parseInt(str_StudyTime.substring(0, 2));
                    // 분만 변수에 저장
                    minutes_sub = Integer.parseInt(str_StudyTime.substring(5, 7));
                    // 초만 변수에 저장
                    seconds_sub = Integer.parseInt(str_StudyTime.substring(10, 12));


                    /*1)과목별 공부시간 쉐어드에 저장*/
                    //쉐어드에서 갖고온 측정했던 과목별 공부시간
                    hour_Subject_shared = Integer.parseInt(tv_StudyTime_Shared.substring(0, 2));
                    // 분만 변수에 저장
                    minutes_Subject_shared = Integer.parseInt(tv_StudyTime_Shared.substring(5, 7));
                    // 초만 변수에 저장
                    seconds_Subject_shared = Integer.parseInt(tv_StudyTime_Shared.substring(10, 12));

                    //과목별 공부시간을 측정한 과목별 공부시간에 더한다.
                    hour_Subject_shared += hour_sub;//시간
                    minutes_Subject_shared += minutes_sub;//분
                    seconds_Subject_shared += seconds_sub;//초*/
                    //더해진 과목별 공부시간을 String변수에 담는다.
                    subStudyTime = String.format("%02d", hour_Subject_shared) + " : "
                            + String.format("%02d", minutes_Subject_shared) + " : "
                            + String.format("%02d", seconds_Subject_shared);

                    //공부시간이 수정된 과목정보를 리스트에 넣는다.
                    //공부시간만 수정되었음.
                    List_StudyPlan.set(Myapplication.StudyGoal_index, new Data_StudyPlan(Subject, subStudyTime, roomNumber, saveDate , List_UserData.get(0).studyType));

                    //[과목별 공부시간이 저장된 공부목표 리스트 쉐어드에 저장]
                    //과목별 공부시간에 측정한 공부시간을 더한 값을 쉐어드에 저장한다.
                    //쉐어드에 과목별 공부시간이 저장된 공부 목표 리스트 저장.
                    try {
                        SharedClass.Save_List_StudyGoal_json(getApplicationContext(), Myapplication.UserId, List_StudyPlan, SharedClass.PREFERENCES_StudyGoal_Data);
                    } catch (JSONException e) {
                    }

                    /*2)전체 공부시간 저장*/
                    //1)쉐어드에서 갖고온 전체 공부시간에 현재 측정한 공부시간을 더하기 위해서 int로 형변환한다.
                    all_hour_sub = Integer.parseInt(str_All_StudyTime_shared.substring(0, 2));
                    // 분만 변수에 저장
                    all_minutes_sub = Integer.parseInt(str_All_StudyTime_shared.substring(5, 7));
                    // 초만 변수에 저장
                    all_seconds_sub = Integer.parseInt(str_All_StudyTime_shared.substring(10, 12));

                    //전체 공부시간에 측정한 공부시간을 더한다.
                    all_hour_sub += hour_sub;//시간
                    all_minutes_sub += minutes_sub;//분
                    all_seconds_sub += seconds_sub;//초*/


                    all_minutes_seconds = all_hour_sub * 3600;
                    all_hour_seconds = all_minutes_sub * 60;
                    //모든 시간 분 초를 초로 환산한다.
                    add_seconds = all_seconds_sub + all_minutes_seconds + all_hour_seconds;

                    //환산한 초를 다시 시간, 분, 초로 바꾼다.
                    //시간
                    int a_1 = add_seconds / 3600;
                    int a = add_seconds % 3600;
                    //분
                    int b_1 = a / 60;
                    //초
                    int b = a % 60;

                    //4)더해진 공부시간을 쉐어드에 저장하기 위해서 String변수에 담는다.
                    //4-1)전체 공부시간 String으로 형변환
                    //4-2)String 변수에 담기


                    //등수 측정용 전체공부시간을 변수에 저장
                    //정확한 측정을 위해서 초단위로 바꾸어서 더한다.
                    allStudyTime_rank = (a_1 * 3600) + (b_1 * 60) + b;

                    //메인화면에 입력될 전체 공부시간을 저장
                    str_All_StudyTime_shared = String.format("%02d", a_1) + " : "
                            + String.format("%02d", b_1) + " : "
                            + String.format("%02d", b);

//                    1)공부 시간 통계리스트에 전체 공부시간, 공부 시작시간, 공부 종료시간 저장
                    //key: 공부시간 측정 시작 날짜(오늘날짜)
                    for (int i = 0; i < List_Measure_StudyTime.size(); i++) {
                        //만든 사람 id == 로그인한 사용자 id
                        //공부 처음 시작시간만 그대로
                        if (Myapplication.UserId.equals(List_Measure_StudyTime.get(i).made_id)) {
                            List_Measure_StudyTime.set(i, new Data_Measure_StudyTime(str_All_StudyTime_shared, List_Measure_StudyTime.get(i).Start_Measure_Time, str_studyEndTime, Myapplication.UserId,
                                    List_Measure_StudyTime.get(i).nickname_rank, List_UserData.get(0).studyType, allStudyTime_rank, 0));
                        }
                    }


                    //쉐어드에 전체 공부시간 통계 리스트 저장
                    try {
                        SharedClass.Save_List_MeasureTime_json(getApplicationContext(), Myapplication.today_key, List_Measure_StudyTime, SharedClass.PREFERENCES_Measure_StudyTime);
                    } catch (JSONException e) {
                    }

//                    2)사용자 정보에서 전체 공부시간 저장
                    //key: 사용자 id
                    data_user = List_UserData.get(0);
                    List_UserData.set(0, new Data_user(data_user.Id_User, data_user.Pw_User, data_user.iv_profile_User, data_user.nickname_User,
                            data_user.Apply_letter_User, str_All_StudyTime_shared, data_user.studyType));
                    //쉐어드에 전체공부시간이 저장된 사용자 정보 리스트에 [전체공부시간] 저장
                    try {
                        SharedClass.Save_List_UserData_json(getApplicationContext(), Myapplication.UserId, List_UserData);
                    } catch (JSONException e) {
                    }


                } else {//같지 않을 경우 ==> ex) 11시에 측정을 시작하고 요일을 넘겨서 새벽 2시에 종료 한 경우
                    //1) 12시를 기준으로  어제 공부한 시간과 오늘 공부한 시간을 각각 따로 쉐어드에 저장한다.

                    //****년도,월,일을 넣지 않으면 1970년도가 되기 때문에 년도도 넣어준다****.
                    //공부시간 측정 종료시간과 시작시간의 날짜 형식
                    // TimeZone에 표준시 설정
                    format.setTimeZone(tz);

                    //오늘공부 시간 측정 기준시간 구하기
                    //날짜 형식 : 현재년도(YYYY) : 현재월(MM) : 현재일(DD) : 00 : 00 : 00
                    Myapplication.studyEnd_Time = format.format(new Date());
                    String a = Myapplication.studyEnd_Time.substring(0, 4);
                    String b = Myapplication.studyEnd_Time.substring(5, 7);
                    String c = Myapplication.studyEnd_Time.substring(8, 10);
                    String d = Myapplication.studyEnd_Time.substring(11, 13);
                    String e = Myapplication.studyEnd_Time.substring(14, 16);
                    String f = Myapplication.studyEnd_Time.substring(17, 19);
                    d = "00";
                    e = "00";
                    f = "00";

                    String str_today = a + ":" + b + ":" + c + ":" + d + ":" + e + ":" + f;


                    //String 객체를 Date 객체로 파싱
                    //밤 24시, 0시, 공부시간 측정 시작시간, 측정 종료시간
                    time_24_date = Myapplication.convertTime(time_24_date, Myapplication.str_24_date);//밤 24시 //어제 날자 기준
                    time_0_date = Myapplication.convertTime(time_0_date, str_today);//밤 0시 //오늘 날짜 기준

                    //어제 공부한 시간
                    //24시 - 공부 시작 시간
                    long diff = time_24_date.getTime() - start_Time_date.getTime();
                    long hour_yesterday = diff / 3600000 % 60;
                    long minute_yesterday = diff / 60000 % 60;
                    long sec_yesterday = diff / 1000 % 60;


                    //1)쉐어드에서 갖고온 전체 공부시간에 현재 측정한 공부시간을 더하기 위해서 int로 형변환한다.
                    all_hour_sub = Integer.parseInt(str_All_StudyTime_shared.substring(0, 2));
                    // 분만 변수에 저장
                    all_minutes_sub = Integer.parseInt(str_All_StudyTime_shared.substring(5, 7));
                    // 초만 변수에 저장
                    all_seconds_sub = Integer.parseInt(str_All_StudyTime_shared.substring(10, 12));

                    //어제 공부한 시간
                    //전체 공부시간에 어제 측정한 공부 시간을 더한다.
                    all_hour_sub += hour_yesterday;
                    all_minutes_sub += minute_yesterday;
                    all_seconds_sub += sec_yesterday;


                    //어제 공부한 시간 String변수에 저장
                    str_allStudyTime_yes = String.format("%02d", all_hour_sub) + " : "
                            + String.format("%02d", all_minutes_sub) + " : "
                            + String.format("%02d", all_seconds_sub);


                    //리스트에 어제 공부했던 측정 시간 + 오늘 공부한 시간(전체 공부시간) 저장
                    //공부시간 통계 리스트에 저장
                    //쉐어드에 공부시간 통계 리스트 저장 (어제 공부한 시간)
                    try {
                        SharedClass.Save_List_MeasureTime_json(getApplicationContext(), Myapplication.today_key, List_Measure_StudyTime, SharedClass.PREFERENCES_Measure_StudyTime);
                    } catch (JSONException g) {
                    }


                    //*** 오늘 공부했던 시간 저장 ***
                    //날짜를 넘겼으니 전체 공부시간을 0으로 초기화 시킨다.
                    allKey = SharedClass.getAll_Sharedkey(getApplicationContext(), SharedClass.PREFERENCES_Measure_StudyTime);
                    //쉐어드에 저장된 전체 키와 비교하기.
                    //쉐어드에 저장된 키와 : 저장된 전체 키
                    for (Map.Entry<String, ?> allKey : allKey.entrySet()) {
                        //오늘 날짜  == 쉐어드에 저장된 모든 키
                        if (Myapplication.today_key.equals(allKey.getKey())) {
                            //쉐어드에 오늘 날짜로 저장된 키가 있을 때
                            Reset_Count = 1;//초기화 번호 ==> 1번을 경우 초기화 x
                        }
                    }
                    //쉐어드에 오늘날짜로 저장된 키가 없을 때
                    //전체 공부시간 초기화
                    if (Reset_Count != 1) {
                        str_All_StudyTime_shared = "00 : 00 : 00";
                    }


                    //**************오늘 공부한 시간*****************
                    //공부 종료 시간 - 0시 => 오늘 공부한 시간
                    long diff_today = end_Time_date.getTime() - time_0_date.getTime();
                    long hour_today = diff_today / 3600000 % 60;//시간
                    long minute_today = diff_today / 60000 % 60;//분
                    long sec_today = diff_today / 1000 % 60;//초


                    //오늘 공부한 시간
                    //시간만 변수에 저장
                    all_hour_sub = Integer.parseInt(str_All_StudyTime_shared.substring(0, 2));
                    // 분만 변수에 저장
                    all_minutes_sub = Integer.parseInt(str_All_StudyTime_shared.substring(5, 7));
                    // 초만 변수에 저장
                    all_seconds_sub = Integer.parseInt(str_All_StudyTime_shared.substring(10, 12));
                    //**통계 리스트 전체 공부시간 리스트에 저장될 공부시간 **
                    //전체공부시간  + 오늘 공부한 시간 => 오늘 공부한 시간(통계 리스트, 전체 공부시간 리스트에 저장될 공부시간)
                    all_hour_sub += hour_today;
                    all_minutes_sub += minute_today;
                    all_seconds_sub += sec_today;

                    //String변수에 오늘 공부한 시간 저장.
                    str_allStudyTime_today = String.format("%02d", all_hour_sub) + " : "
                            + String.format("%02d", all_minutes_sub) + " : "
                            + String.format("%02d", all_seconds_sub);
                    //공부시간 통계 리스트에 오늘 공부한 시간 저장.
//                    List_Measure_StudyTime_today.add(0, new Data_Measure_StudyTime(str_allStudyTime_today, str_studyStartTime, str_studyEndTime, Myapplication.UserId));

                    //쉐어드에 오늘 날짜로 공부시간 통계 리스트에 오늘 공부한 시간 저장.
                    try {
                        SharedClass.Save_List_MeasureTime_json(getApplicationContext(), Myapplication.studyEnd_Day, List_Measure_StudyTime_today, SharedClass.PREFERENCES_Measure_StudyTime);
                    } catch (JSONException g) {
                    }


                    //쉐어드에 사용자 id로 사용자 리스트에 전체공부시간 입력
                    data_user = List_UserData.get(0);
                    List_UserData.set(0, new Data_user(data_user.Id_User, data_user.Pw_User, data_user.iv_profile_User, data_user.nickname_User,
                            data_user.Apply_letter_User, str_allStudyTime_yes, data_user.studyType));
                    //쉐어드에 전체공부시간이 저장된 사용자 정보 리스트 저장
                    //key : 사용자id
                    try {
                        SharedClass.Save_List_UserData_json(getApplicationContext(), Myapplication.UserId, List_UserData);
                    } catch (JSONException g) {
                    }

                    //오늘 공부한 시간 String 변수에 저장
                    /******4.쉐어드에 전체 공부시간, 과목별 공부시간 저장하기*******/
                    /*1)공부 시간 통계리스트에 전체 공부시간 저장*/
                    //key: 공부시간 측정 시작 날짜(오늘날짜)
                    //쉐어드에 어제 공부시간 통계 리스트 저장
                }



                /*측정한 공부시간을 확인하는 다이얼로그 */
                dialog = getDialog();
                dialog.show();
                tv_studySubject_dia.setText(Subject);//클릭했던 공부과목 입력
                tv_studyTime_dia.setText(tv_StudyTime.getText() + "을 공부하셨습니다.");//공부시간 확인

                /*쉐어드에서 따로 리스트를 불러오지 않고
                 * 리스트를 새로 생성해서 생성한 리스트안에 값을 계속 갱신 시켜주고 쉐어드에 저장한다.
                 * 리스트안에 size는 계속 1이여야 한다.
                 * 키 값은 공부 시간 측정을 시작한 날짜이다.*/


                //확인버튼 누르면 다이어로그 사라지게 하기.
                btn_enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    public AlertDialog getDialog() {
        //thems에서 xml파일을 하나 만들어서 입력한다
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        //다이얼로그에  들어갈 레이아웃 xml 파일 입력
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_check_studytime, null, false);
        builder.setView(view);


        btn_enter = view.findViewById(R.id.btn_enter_dia);
        tv_studySubject_dia = view.findViewById(R.id.tv_subject_dia);
        tv_studyTime_dia = view.findViewById(R.id.tv_studyTime_dia);

        dialog = builder.create();//다이어로그 생성
        //버튼 클릭 이벤트 지정

        return dialog;
    }


}