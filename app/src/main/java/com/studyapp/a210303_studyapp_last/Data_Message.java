package com.studyapp.a210303_studyapp_last;

public class Data_Message {
    String send_message = "";//보낸 메세지
    String send_Time = "";//보낸 시간
    String send_Id = "";//보낸 사람id
    String send_profile = "";//보낸사람 프로필 이미지
    String send_Nickname = "";//보낸 사람 닉네임
    String message_num = "";//채팅방 번호
    String send_Date = "";//보낸 날짜
    String firstMessage = "";//처음보낸 문자메세지 확인
    int view_Type_num = 0;//뷰 타입 번호



    public Data_Message(String send_message, String send_Time, String send_Id, String send_profile, String send_Nickname, String send_Date, String firstMessage) {
        this.send_message = send_message;
        this.send_Time = send_Time;
        this.send_Id = send_Id;
        this.send_profile = send_profile;
        this.send_Nickname = send_Nickname;
        this.send_Date = send_Date;
        this.firstMessage = firstMessage;//오늘날짜로 보낸 문자 확인 변수
    }

    //이전 테스트용


    //현재 사용 중




    public int getView_Type_num() {
        return view_Type_num;
    }

    public void setView_Type_num(int view_Type_num) {
        this.view_Type_num = view_Type_num;
    }

    public String getSend_profile() {
        return send_profile;
    }

    public void setSend_profile(String send_profile) {
        this.send_profile = send_profile;
    }

    public String getSend_message() {
        return send_message;
    }

    public void setSend_message(String send_message) {
        this.send_message = send_message;
    }

    public String getSend_Time() {
        return send_Time;
    }

    public void setSend_Time(String send_Time) {
        this.send_Time = send_Time;
    }

    public String getSend_Id() {
        return send_Id;
    }

    public void setSend_Id(String send_Id) {
        this.send_Id = send_Id;
    }

    public String getSend_Nickname() {
        return send_Nickname;
    }

    public void setSend_Nickname(String send_Nickname) {
        this.send_Nickname = send_Nickname;
    }

    public String getMessage_num() {
        return message_num;
    }

    public void setMessage_num(String message_num) {
        this.message_num = message_num;
    }

}
