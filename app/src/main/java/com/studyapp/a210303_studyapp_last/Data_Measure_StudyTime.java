package com.studyapp.a210303_studyapp_last;

public class Data_Measure_StudyTime implements Comparable<Data_Measure_StudyTime> {
    String Measure_Time = "";//전체공부시간
    String Start_Measure_Time = "";//공부시작시간
    String End_Measure_Time = "";//공부종료시간
    String study_subject_Name = "";//공부 과목이름
    String Measure_Subject_Time = "";//과목별 공부시간
    String made_id = "";//만든사람id
    String save_Date = "";//만든사람id

    //전체 공부시간 랭킹에 필요한 추가할 변수
    String nickname_rank = "";//사용자 닉네임
    String studyType = "";//공부타입
    int ranking_score_rank;//랭킹 측정요 점수
    int rank;//등수
    //공부시간 랭킹에 사용할 생성자

    public Data_Measure_StudyTime(String made_id, int rank_score, String measure_Time, String nickname_rank, String studyType, int rank) {
        this.made_id = made_id;//만든사람id
        this.ranking_score_rank = rank_score;//랭킹에 사용될 점수
        Measure_Time = measure_Time;//공부시간
        this.nickname_rank = nickname_rank;//닉네임
        this.studyType = studyType;//공부타입
        this.rank = rank;//등수
    }

    //전체공부시간 저장 생성자
    public Data_Measure_StudyTime(String measure_Time, String start_Measure_Time, String end_Measure_Time, String made_id, String nickname_rank, String studyType, int ranking_score_rank, int rank) {
        Measure_Time = measure_Time;
        Start_Measure_Time = start_Measure_Time;
        End_Measure_Time = end_Measure_Time;
        this.made_id = made_id;
        this.nickname_rank = nickname_rank;
        this.studyType = studyType;
        this.ranking_score_rank = ranking_score_rank;
        this.rank = rank;
    }



    //오름차순 내림차순을 해주는 메서드
    @Override
    public int compareTo(Data_Measure_StudyTime data) {
        //2)숫자
        //숫자는 위의 compareTo()로 안되고 다음과 같이 처리 해야됨
        if (this.ranking_score_rank < data.ranking_score_rank) {
//			return -1; //2-1) 숫자 내림 차순
            return 1; //2-2) 숫자 오름 차순
        } else if (this.ranking_score_rank == data.ranking_score_rank) {
            return 0;
        } else {
//			return 1; //2-1)숫자 내림 차순
            return -1; //2-2)숫자 오름 차순
        }
    }


    public String getStudy_subject_Name() {
        return study_subject_Name;
    }

    public void setStudy_subject_Name(String study_subject_Name) {
        this.study_subject_Name = study_subject_Name;
    }

    public String getMeasure_Subject_Time() {
        return Measure_Subject_Time;
    }

    public void setMeasure_Subject_Time(String measure_Subject_Time) {
        Measure_Subject_Time = measure_Subject_Time;
    }

    public String getMeasure_Time() {
        return Measure_Time;
    }

    public void setMeasure_Time(String measure_Time) {
        Measure_Time = measure_Time;
    }

}

