package com.studyapp.a210303_studyapp_last;

public class Data_StudyRoom_Infor {
    //방 정보
    String roomName = "";//방 제목
    String roomContent = "";//방 내용
    //사용자 데이터
    String studyType = "";//만든사람 공부 종류
    String studyType_roomJoiner = "";//참여한사람 공부 종류
    String room_Maker_Id = "";//방 만든 사람 id
    String room_Joiner_Id = "";//방 참여한 사람 id
    String NickName_room_Maker = "";//방 만든 사람 id
    String NickName_room_Joiner = "";//방 참여한 사람 id
    String iv_profile_roomMaker = ""; //만든사람이미지
    String iv_profile_roomJoiner = ""; //만든사람이미지
    String iv_profile_uri = ""; //방만든사람 프로필사진 uri
    String tv_nickname = ""; //방만든사람 닉네임
    String room_letter = ""; //모집 참여 편지
    int StudyTime;// 공부 친구의 공부시간
    int roomNumber = 0;//방 번호
    String extra = "";//나중에 지울 것 생성자 겹쳐서 임시로 넣은 변수


    //공부 친구 정보 7
    public Data_StudyRoom_Infor(String studyType, String room_Joiner_Id, String nickName_room_Joiner, String roomContent, int studyTime, int roomNumber, String profile) {
        this.studyType = studyType;//공부 종류
        this.room_Joiner_Id = room_Joiner_Id;//친구의 공부시간
        NickName_room_Joiner = nickName_room_Joiner;//친구의 닉네임
        this.roomContent = roomContent;//생성자 겹쳐서 넣어준 변수
        StudyTime = studyTime;//친구의 공부시간
        this.roomNumber = roomNumber;//친구의 방번호
        this.iv_profile_uri = profile;//프로필사진
    }

    //모임 방 정보 5
    public Data_StudyRoom_Infor(String roomName, String roomContent, String studyType, String room_Maker_Id, int roomNumber) {
        this.roomName = roomName;
        this.roomContent = roomContent;
        this.studyType = studyType;
        this.room_Maker_Id = room_Maker_Id;
        this.roomNumber = roomNumber;
    }


    //내가 받은 공부친구 모집글 7
    public Data_StudyRoom_Infor(String room_Joiner_Id, String room_Maker_Id, String profile, String nickname, String StudyType, int roomNumber, String roomLetter) {
        this.room_Joiner_Id = room_Joiner_Id;//참여한 사람 id
        this.room_Maker_Id = room_Maker_Id;//방 만든사람 id
        this.iv_profile_roomJoiner = profile;//참여한 사람 프로필사진
        this.NickName_room_Joiner = nickname;//참여한 사람 닉네임
        this.studyType_roomJoiner = StudyType;//참여한 사람 공부타입
        this.roomNumber = roomNumber;//방 번호
        this.room_letter = roomLetter;//참여한 사람이 보낸 편지
    }


    //모집글 정보 생성자 7
    public Data_StudyRoom_Infor(String roomName, String roomContent, String room_Maker_Id, int roomNumber, String profile_uri, String Nickname, String studyType) {
        this.roomName = roomName;
        this.roomContent = roomContent;
        this.room_Maker_Id = room_Maker_Id;
        this.roomNumber = roomNumber;
        this.iv_profile_uri = profile_uri;
        this.tv_nickname = Nickname;
        this.studyType = studyType;
    }

    // 내가 보낸 모집글 생성자 8
    public Data_StudyRoom_Infor(String roomName, String roomContent, String room_Maker_Id, int roomNumber, String profile_uri, String Nickname, String studyType, String letter) {
        this.roomName = roomName;
        this.roomContent = roomContent;
        this.room_Maker_Id = room_Maker_Id;
        this.roomNumber = roomNumber;
        this.iv_profile_uri = profile_uri;
        this.tv_nickname = Nickname;
        this.studyType = studyType;
        this.room_letter = letter;
    }

    public int getStudyTime() {
        return StudyTime;
    }

    public void setStudyTime(int studyTime) {
        StudyTime = studyTime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRoom_Joiner_Id() {
        return room_Joiner_Id;
    }

    public void setRoom_Joiner_Id(String room_Joiner_Id) {
        this.room_Joiner_Id = room_Joiner_Id;
    }

    public String getNickName_room_Maker() {
        return NickName_room_Maker;
    }

    public void setNickName_room_Maker(String nickName_room_Maker) {
        NickName_room_Maker = nickName_room_Maker;
    }

    public String getNickName_room_Joiner() {
        return NickName_room_Joiner;
    }

    public void setNickName_room_Joiner(String nickName_room_Joiner) {
        NickName_room_Joiner = nickName_room_Joiner;
    }

    public String getIv_profile_roomJoiner() {
        return iv_profile_roomJoiner;
    }

    public void setIv_profile_roomJoiner(String iv_profile_roomJoiner) {
        this.iv_profile_roomJoiner = iv_profile_roomJoiner;
    }

    public String getIv_profile_roomMaker() {
        return iv_profile_roomMaker;
    }

    public void setIv_profile_roomMaker(String iv_profile_roomMaker) {
        this.iv_profile_roomMaker = iv_profile_roomMaker;
    }

    public String getRoom_letter() {
        return room_letter;
    }

    public void setRoom_letter(String room_letter) {
        this.room_letter = room_letter;
    }


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomContent() {
        return roomContent;
    }

    public void setRoomContent(String roomContent) {
        this.roomContent = roomContent;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getRoom_Maker_Id() {
        return room_Maker_Id;
    }

    public void setRoom_Maker_Id(String room_Maker_Id) {
        this.room_Maker_Id = room_Maker_Id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}

