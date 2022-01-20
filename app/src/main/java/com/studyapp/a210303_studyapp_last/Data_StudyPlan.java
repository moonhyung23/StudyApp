package com.studyapp.a210303_studyapp_last;

public class Data_StudyPlan {

    //공부 계획에 필요한 데이터
    String playButton_icon_uri; //공부 시작 아이콘
    String Study_goal; //공부할 목표
    String Study_time; //공부시간
    String option_icon; //옵션 아이콘
    String tv_add_Study_goal; //공부 목표
    String iv_add_Study_goal_icon; //공부 목표 생성 이미지 아이콘
    String save_Date; //공부 과목 측정시작 날짜
    String studyType; //내 공부타입
    int Goal_Number;//리사이클러뷰 아이템 번호.

    public Data_StudyPlan(String study_goal, String study_time, int goal_Number, String save_Date, String studyType) {
        Study_goal = study_goal;
        Study_time = study_time;
        Goal_Number = goal_Number;
        this.save_Date = save_Date;//공부 과목 측정 시작 날짜.
        this.studyType = studyType;//공부 과목 측정 시작 날짜.
    }


    public int getGoal_Number() {
        return Goal_Number;
    }

    public void setGoal_Number(int goal_Number) {
        Goal_Number = goal_Number;
    }

    public String getPlayButton_icon_uri() {
        return playButton_icon_uri;
    }

    public void setPlayButton_icon_uri(String playButton_icon_uri) {
        this.playButton_icon_uri = playButton_icon_uri;
    }

    public String getStudy_goal() {
        return Study_goal;
    }

    public void setStudy_goal(String study_goal) {
        Study_goal = study_goal;
    }

    public String getStudy_time() {
        return Study_time;
    }

    public void setStudy_time(String study_time) {
        Study_time = study_time;
    }

    public String getOption_icon() {
        return option_icon;
    }

    public void setOption_icon(String option_icon) {
        this.option_icon = option_icon;
    }

    public String getTv_add_Study_goal() {
        return tv_add_Study_goal;
    }

    public void setTv_add_Study_goal(String tv_add_Study_goal) {
        this.tv_add_Study_goal = tv_add_Study_goal;
    }

    public String getIv_add_Study_goal_icon() {
        return iv_add_Study_goal_icon;
    }

    public void setIv_add_Study_goal_icon(String iv_add_Study_goal_icon) {
        this.iv_add_Study_goal_icon = iv_add_Study_goal_icon;
    }
}
