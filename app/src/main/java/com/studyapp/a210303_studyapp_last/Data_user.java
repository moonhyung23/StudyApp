package com.studyapp.a210303_studyapp_last;

public class Data_user {
    //사용자 데이터
    public String Id_User = ""; // ID
    public String Pw_User = ""; // PW
    public String content_User = ""; // 상태메세지
    public String iv_profile_User = ""; // 프로필 사진
    public String nickname_User = ""; // 닉네임
    public String Apply_letter_User = ""; // 모집 참여 편지
    public String All_Studyhour = "00 : 00 : 00"; // 전체 공부 시간
    public String studyType = ""; // 공부타입

    public Data_user(String id_User, String pw_User, String iv_profile_User, String nickname_User, String apply_letter_User, String all_Studyhour, String studyType) {
        Id_User = id_User;
        Pw_User = pw_User;
//        this.content_User = content_User;
        this.iv_profile_User = iv_profile_User;
        this.nickname_User = nickname_User;
        Apply_letter_User = apply_letter_User;
        this.All_Studyhour = all_Studyhour;
        this.studyType = studyType;
    }


    public String getAll_Studyhour() {
        return All_Studyhour;
    }

    public void setAll_Studyhour(String all_Studyhour) {
        All_Studyhour = all_Studyhour;
    }

    public String getId_User() {
        return Id_User;
    }

    public void setId_User(String id_User) {
        Id_User = id_User;
    }

    public String getPw_User() {
        return Pw_User;
    }

    public void setPw_User(String pw_User) {
        Pw_User = pw_User;
    }

    public String getContent_User() {
        return content_User;
    }

    public void setContent_User(String content_User) {
        this.content_User = content_User;
    }

    public String getIv_profile_User() {
        return iv_profile_User;
    }

    public void setIv_profile_User(String iv_profile_User) {
        this.iv_profile_User = iv_profile_User;
    }

    public String getNickname_User() {
        return nickname_User;
    }

    public void setNickname_User(String nickname_User) {
        this.nickname_User = nickname_User;
    }

    public String getApply_letter_User() {
        return Apply_letter_User;
    }

    public void setApply_letter_User(String apply_letter_User) {
        Apply_letter_User = apply_letter_User;
    }
}

