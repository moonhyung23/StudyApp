package com.studyapp.a210303_studyapp_last;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Myapplication extends Application {
    static String StudyGoal = "";//공부 목표
    static String StudyTime = "00 : 00 : 00";//공부 시간
    static String Study_All_Studytime = "00 : 00 : 00"; //전체공부시간
    static Context context;
    static int num_UserData = 0;
    static int num_StudyGoal_edit_add = 0;
    static int num_StudyMyRoom_add_edit_remove = 0;
    static int itemview_position = 0;
    static int itemview_Studygoal_Number = 0;//공부시간 측정용
    static int RoomNumber = 0;//클릭한 방의 방 번호
    static int StudyGoal_Num = 0;//클릭한 아이템의 공부 목표 방 번호
    static int StudyGoal_index = 0;//클릭한 아이템의(공부목표) 리스트 인덱스 번호
    static String ClickItemId = "";
    static String MakerUserId = "";
    static String RoomName = "";
    static String RoomContent = "";
    static String User_Letter = "";//사용자가 입력한 모집 참여 편지
    static String intent_Letter = "";//상세보기시 보여주는 편지글
    static String Sender_Id = "";//보내는 사람의 Id
    static String today_key = "";//현재날짜
    static int Create_StudyRoom_check = 0;
    static int Num_option_play_Studygoal = 0;
    static String studyStart_Time = "";//공부시간 측정 시작시간
    static String studyEnd_Day = "";//공부시간 측정 종료시간
    static String studyEnd_Time = "";//공부시간 측정 종료시간
    static String str_24_date = "";//공부시간 측정 종료시간
    static String saveDate = "";//과목별 공부시간 측정 시작 날짜.
    static String UserNickname = "";//로그인한 사용자 닉네임.
    static String UserId = "";//로그인한 사용자 아이디
    static String editUserData = "";//수정하고 하려는 사용자 데이터
    static String Naver_Login_Id_Key = "";//네이버 로그인 id 키
    static int editUserDate_Num;//닉네임, 공부타입 수정 구분해주는 변수


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    //context를 다른 클래스에서도 사용할 수 있게 반환해주는 메서드
    public static Context getAppContext() {
        return context;
    }

    //버튼 숨기기
    public static void btn_Invisible(Button button) {
        button.setVisibility(View.INVISIBLE);
    }

    //이미지뷰 숨기기
    public static void iv_Invisible(ImageView iv) {
        iv.setVisibility(View.INVISIBLE);
    }

    //이미지뷰 보이기
    public static void iv_visible(ImageView iv) {
        iv.setVisibility(View.VISIBLE);
    }

    //버튼 보이기
    public static void btn_visible(Button button) {
        button.setVisibility(View.VISIBLE);
    }

    //한국 표준시로 변환
    //String => Date객체로 변환
    public static Date convertKoreanTime(Date date, SimpleDateFormat simple) {
        //한국 표준시를 가져온다
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        //날짜 형식을 받아온다
        //받아온 날짜 형식에 한국 표준시를 입력한다.
        simple.setTimeZone(tz);
        //한국 표준시로 변경한 날짜 형식을  String변수에 담는다.
        String str_Today = simple.format(new Date());
        /*한국 표준시로 바꾸어도 Date 에서 parsing 하면 영국 표준시로 바꾸기 때문에
         * TimeZone 객체를 사용하여 한국 표준시 - 영국 표준시 = 차이값(시간차이)을 더한다. */
        try {
            //date 객체로 파싱한다.
            date = simple.parse(str_Today);
        } catch (ParseException e) {
        }
      /*  // TimeZone을 통해 시간차이 계산 (썸머타임 고려 getRawOffset 대신 getOffset 함수 활용)
        int offset = tz.getOffset(date.getTime());
        //이전에 구했던 시간 + 시간차이
        long koreanTime = date.getTime() + offset;

        date.setTime(koreanTime);*/
        return date;
    }

    //오늘날짜를 포맷에 맞게 반환해주는 메서드
    public static String str_convertKoreanTime(SimpleDateFormat format) {
        //한국 표준시를 가져온다
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        //날짜 형식을 받아온다
        //받아온 날짜 형식에 한국 표준시를 입력한다.
        format.setTimeZone(tz);
        //한국 표준시로 변경한 날짜 형식을  String변수에 담는다.
        String str_Today = format.format(new Date());
        return str_Today;
    }


    //String객체를 date객체로 파싱해주는 메서드
    public static Date convertTime(Date date, String str_time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss", Locale.KOREA);
        try {
            //date 객체로 파싱한다.
            date = simpleDateFormat.parse(str_time);
        } catch (ParseException e) {
        }
        return date;
    }


    public static String getTodayDate(TimeZone tz) {
        tz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 표준시 설정

        //공부시간 측정을 시작한 현재 날짜를 갖고온다.
        SimpleDateFormat dfDate1 = new SimpleDateFormat("yyyy", Locale.KOREA);//년도
        SimpleDateFormat dfDate2 = new SimpleDateFormat("MM", Locale.KOREA);//월
        SimpleDateFormat dfDate3 = new SimpleDateFormat("dd", Locale.KOREA);//일

        dfDate1.setTimeZone(tz);
        dfDate2.setTimeZone(tz);
        dfDate3.setTimeZone(tz);

        String TODAY1 = dfDate1.format(new Date());//년
        String TODAY2 = dfDate2.format(new Date());//월
        String TODAY3 = dfDate3.format(new Date());//일
        //int 형변환 => (월, 일)만
        int month = Integer.parseInt(TODAY2);
        int Day = Integer.parseInt(TODAY3);
        //***오늘의 날짜를 담은 변수***
        //Stirng으로 형변환 후 변수에 저장 (월, 일)만
        String TodayDate = (TODAY1 + String.valueOf(month) + String.valueOf(Day ));
        return TodayDate;
    }


    public static String getTodayDate2(TimeZone tz) {
        tz = TimeZone.getTimeZone("Asia/Seoul");  // TimeZone에 표준시 설정

        //공부시간 측정을 시작한 현재 날짜를 갖고온다.
        SimpleDateFormat dfDate1 = new SimpleDateFormat("yyyy", Locale.KOREA);//년도
        SimpleDateFormat dfDate2 = new SimpleDateFormat("MM", Locale.KOREA);//월
        SimpleDateFormat dfDate3 = new SimpleDateFormat("dd", Locale.KOREA);//일

        dfDate1.setTimeZone(tz);
        dfDate2.setTimeZone(tz);
        dfDate3.setTimeZone(tz);

        String TODAY1 = dfDate1.format(new Date());//년
        String TODAY2 = dfDate2.format(new Date());//월
        String TODAY3 = dfDate3.format(new Date());//일
        //int 형변환 => (월, 일)만
        int month = Integer.parseInt(TODAY2);
        int Day = Integer.parseInt(TODAY3);
        //***오늘의 날짜를 담은 변수***
        //Stirng으로 형변환 후 변수에 저장 (월, 일)만
        String TodayDate = (TODAY1 + " : " + String.format("%02d", month) + " : " + String.format("%02d", Day));
        return TodayDate;
    }

    //달력을 더해주고 빼주는 메서드
    public static String AddDate(String str_date, SimpleDateFormat dtFormat, int year, int month, int day) {
        //날짜형식 지정
        Date dt = null;
        try {
            dt = dtFormat.parse(str_date);
        } catch (ParseException e) {
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DATE, day);
        return dtFormat.format(cal.getTime());
    }

}

