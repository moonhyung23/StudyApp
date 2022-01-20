package com.studyapp.a210303_studyapp_last;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Rank_Activity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_TodayDate, tv_allRank, tv_myRank;
    Button btn_home, btn_studyFriend, btn_studyCheck;
    ImageView iv_left, iv_right;
    ArrayList<Data_Measure_StudyTime> List_measure_shared;//쉐어드에 저장된 전체 공부시간 통계리스트
    ArrayList<Data_Measure_StudyTime> List_measure;//(아이템뷰) 전체 공부시간 통계리스트
    //리사이클러뷰 세팅
    RecyclerView rv_studyRank;
    Rv_Adapter_Rank rv_adapter_rank;
    LinearLayoutManager linearLayoutManager;
    Data_Measure_StudyTime data_measure;
    int day;//요일
    String oldString = "";//변경할 문자열
    String str_today = "";//오늘날짜
    String update_day = ""; //변경된날짜
    StringBuffer sb = new StringBuffer();
    String str_decrease_day = "";
    String str_TodayDate = "";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
    int d_year, d_month, d_day;//감소한 날짜
    int up_year, up_month, up_day;//증가한 날짜
    String List_Measure_key;//전체 공부시간 통계리스트 키
    int checkId;//id가 있는지 체크하는 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank_activity);
        set_findView();
        set_ButtonListener();

        //리사이클러뷰 세팅
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_adapter_rank = new Rv_Adapter_Rank(getApplicationContext());
        rv_studyRank.setLayoutManager(linearLayoutManager);//linearlayout 세팅
        rv_studyRank.setAdapter(rv_adapter_rank);//adapter 세팅


        //전체 공부시간 통계리스트를 쉐어드에서 불러온다
        try {
            List_measure_shared = SharedClass.get_List_MeasureTime_json(getApplicationContext(), Myapplication.today_key, SharedClass.PREFERENCES_Measure_StudyTime);
        } catch (JSONException e) {
        }

        //컬렉션 클래스를 통해 공부시간이 많은 순으로 리스트를 다시 재배열
        Collections.sort(List_measure_shared);
        //전체 공부시간 통계리스트를 비교
        for (int i = 0; i < List_measure_shared.size(); i++) {
            data_measure = List_measure_shared.get(i);
            //리사이클러뷰 아이템뷰에 전체 공부시간 통계리스트의 데이터 입력
            rv_adapter_rank.addItem(new Data_Measure_StudyTime(
                    data_measure.made_id,
                    data_measure.ranking_score_rank,
                    data_measure.Measure_Time,
                    data_measure.nickname_rank,
                    data_measure.studyType, i + 1));
        }

        List_measure = rv_adapter_rank.getList();
        //현재 등수를 텍스트뷰에 입력
        for (int i = 0; i < List_measure.size(); i++) {
            if (List_measure.get(i).made_id.equals(Myapplication.UserId)) {
                tv_myRank.setText((String.format("%01d등", List_measure.get(i).rank)));
            }
        }
        //전체 인원를 텍스트뷰에 입력
        tv_allRank.setText(String.format("%01d명", List_measure.size()));
        //오늘 날짜 텍스트뷰에 입력
        str_today = Myapplication.str_convertKoreanTime(simpleDateFormat);
        //요일이 두자릿수 일 때
        day = Integer.parseInt(str_today.substring(10, 12));
        tv_TodayDate.setText(str_today);
    }

    //필요한 findview 세팅
    public void set_findView() {
        rv_studyRank = findViewById(R.id.rv_rank);//리사이클러뷰 findView
        tv_TodayDate = findViewById(R.id.tv_todayDate_rank);
        tv_allRank = findViewById(R.id.tv_allrank);
        tv_myRank = findViewById(R.id.tv_myrank);
        btn_home = findViewById(R.id.btn_home_rank);
        btn_studyFriend = findViewById(R.id.btn_StudyFriend_rank);
        btn_studyCheck = findViewById(R.id.btn_Check_StudyTime_rank);
        iv_left = findViewById(R.id.iv_left_icon);
        iv_right = findViewById(R.id.iv_right_icon);
    }


    //버튼 리스너 세팅
    public void set_ButtonListener() {
        //버튼 클릭 리스너 인자 값 => 버튼 클릭 인터페이스
        btn_home.setOnClickListener(this);//
        btn_studyFriend.setOnClickListener(this);
        btn_studyCheck.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
    }


    //버튼 클릭 이벤트 지정
    public void onClick(View v) {
        switch (v.getId()) {
            //날짜 이동 왼쪽, 오른쪽
            case R.id.iv_left_icon://왼쪽 이동 (요일 감소)

                /*  1.감소시킨 날짜를 현재날짜 텍스트뷰에 표시*/
                //현재 날짜 텍스트뷰의 텍스트를 갖고온다
                str_TodayDate = String.valueOf(tv_TodayDate.getText());
                //현재 날짜를 감소시킨다.
                str_decrease_day = Myapplication.AddDate(str_TodayDate, simpleDateFormat, 0, 0, -1);
                //감소 시킨 날짜를 다시 현재 날짜 텍스트뷰에 입력
                tv_TodayDate.setText(str_decrease_day);

                /*2.감소된 날짜의 공부시간 랭킹을 리사이클러뷰에 표시*/
                // 시간만 변수에 저장
                d_year = Integer.parseInt(str_decrease_day.substring(0, 4));
                // 분만 변수에 저장
                d_month = Integer.parseInt(str_decrease_day.substring(6, 8));
                // 초만 변수에 저장
                d_day = Integer.parseInt(str_decrease_day.substring(10, 12));
                //공부시간 전체 통계리스트의 키
                List_Measure_key = String.valueOf(d_year) + String.valueOf(d_month) + String.valueOf(d_day);

                //감소된 날짜의 전체 공부시간 통계리스트를 쉐어드에서 불러온다
                try {
                    List_measure_shared = SharedClass.get_List_MeasureTime_json(getApplicationContext(), List_Measure_key, SharedClass.PREFERENCES_Measure_StudyTime);
                } catch (JSONException e) {
                }


                //이전에 있던 공부시간 랭킹 정보를 전부 삭제한다.
                rv_adapter_rank.clearItem();
                rv_adapter_rank.notifyDataSetChanged();


                //컬렉션 클래스를 통해 공부시간이 많은 순으로 리스트를 다시 재배열
                Collections.sort(List_measure_shared);
                //전체 공부시간 통계리스트를 비교
                for (int i = 0; i < List_measure_shared.size(); i++) {
                    data_measure = List_measure_shared.get(i);
                    //리사이클러뷰 아이템뷰에 전체 공부시간 통계리스트의 데이터 입력
                    rv_adapter_rank.addItem(new Data_Measure_StudyTime(data_measure.made_id, data_measure.ranking_score_rank, data_measure.Measure_Time, data_measure.nickname_rank, data_measure.studyType, i + 1));
                    rv_adapter_rank.notifyDataSetChanged();
                }

                //새로 입력된 아이템뷰의 현재 날짜의 공부시간 랭킹 리스트를 얻는다.
                List_measure = rv_adapter_rank.getList();
                //전체 인원 텍스트뷰에 표시
                tv_allRank.setText(String.format("%01d명", List_measure.size()));
                //현재 등수 표시
                for (int i = 0; i < List_measure.size(); i++) {
                    if (List_measure.get(i).made_id.equals(Myapplication.UserId)) {
                        tv_myRank.setText((String.format("%01d등", List_measure.get(i).rank)));
                        checkId = 1;
                    }
                }
                //감소된 날짜에 내가 공부한 시간이 없는 경우
                if (checkId != 1) {
                    tv_myRank.setText("0등");
                }
                //다시 번호 초기화
                checkId = 0;
                break;

            case R.id.iv_right_icon://오른쪽 이동(요일 증가)
                //현재 날짜 텍스트뷰의 텍스트를 갖고온다
                str_TodayDate = String.valueOf(tv_TodayDate.getText());
                //현재 날짜를 감소시킨다.
                str_decrease_day = Myapplication.AddDate(str_TodayDate, simpleDateFormat, 0, 0, 1);
                //감소 시킨 날짜를 다시 현재 날짜 텍스트뷰에 입력
                tv_TodayDate.setText(str_decrease_day);


                /*2.증가된 날짜의 공부시간 랭킹 및 전체인원 내 랭킹을 리사이클러뷰에 표시*/
                // 시간만 변수에 저장
                up_year = Integer.parseInt(str_decrease_day.substring(0, 4));
                // 분만 변수에 저장
                up_month = Integer.parseInt(str_decrease_day.substring(6, 8));
                // 초만 변수에 저장
                up_day = Integer.parseInt(str_decrease_day.substring(10, 12));
                //공부시간 전체 통계리스트의 키
                List_Measure_key = String.valueOf(up_year) + String.valueOf(up_month) + String.valueOf(up_day);

                //감소된 날짜의 전체 공부시간 통계리스트를 쉐어드에서 불러온다
                try {
                    List_measure_shared = SharedClass.get_List_MeasureTime_json(getApplicationContext(), List_Measure_key, SharedClass.PREFERENCES_Measure_StudyTime);
                } catch (JSONException e) {
                }


                //이전에 있던 공부시간 랭킹 정보를 전부 삭제한다.
                rv_adapter_rank.clearItem();
                rv_adapter_rank.notifyDataSetChanged();


                //컬렉션 클래스를 통해 공부시간이 많은 순으로 리스트를 다시 재배열
                Collections.sort(List_measure_shared);
                //전체 공부시간 통계리스트를 비교
                for (int i = 0; i < List_measure_shared.size(); i++) {
                    data_measure = List_measure_shared.get(i);
                    //리사이클러뷰 아이템뷰에 전체 공부시간 통계리스트의 데이터 입력
                    rv_adapter_rank.addItem(new Data_Measure_StudyTime(data_measure.made_id, data_measure.ranking_score_rank, data_measure.Measure_Time, data_measure.nickname_rank, data_measure.studyType, i + 1));
                    rv_adapter_rank.notifyDataSetChanged();
                }

                //새로 입력된 아이템뷰의 현재 날짜의 공부시간 랭킹 리스트를 얻는다.
                List_measure = rv_adapter_rank.getList();
                //전체 인원 텍스트뷰에 표시
                tv_allRank.setText(String.format("%01d명", List_measure.size()));
                //현재 등수 표시
                for (int i = 0; i < List_measure.size(); i++) {
                    if (List_measure.get(i).made_id.equals(Myapplication.UserId)) {
                        tv_myRank.setText((String.format("%01d등", List_measure.get(i).rank)));
                        checkId = 1;//감소된 날짜에 내가 공부한 시간이 있는지 확인.
                    }
                }

                //감소된 날짜에 내가 공부한 시간이 없는 경우
                if (checkId != 1) {
                    tv_myRank.setText("0등");
                }
                //다시 번호 초기화
                checkId = 0;
                break;

            //하단 메뉴버튼
            case R.id.btn_home_rank://홈(메인화면)
                Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent3);
                finish();
                break;

            case R.id.btn_StudyFriend_rank://공부친구
                Intent intent4 = new Intent(getApplicationContext(), StudyFriend_Activity.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.btn_Check_StudyTime_rank://공부시간 통계
                Intent intent5 = new Intent(getApplicationContext(), Check_MyStudyTime_Actvity.class);
                startActivity(intent5);
                finish();
                break;
        }
    }
}
