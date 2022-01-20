package com.studyapp.a210303_studyapp_last;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SharedClass {
    private static final String DEFAULT_VALUE_STRING = "없음";
    private static final int DEFAULT_VALUE_INT = 0;
    //공부 친구 모집글 방 번호를 저장하는 키 값
    public static String StudyRoomNum_key = "StudyRoomNum_key";
    //공부 목표 방 번호를 저장하는 키 값
    public static String StudyGoalNum_key = "StudyGoalNum_key";
    //공부 처음 시작 시간을 쉐어드에 저장하는 키 값
    public static String firstStudyTime_key = "firstStudyTime_key";
    //
    public static String Goal_Number_KEY = "Goal_Number_KEY";

    /*쉐어드 프리퍼런스 키 값*/
    //1)사용자 정보 키 값
    public static final String Id_User_key = "Id_User_key"; // ID
    public static final String Pw_User_key = "Pw_User_key"; // PW
    //    public static final String content_User_key = "content_User_key"; // 상태메세지
    public static final String iv_profile_User_key = "iv_profile_User_key"; // 프로필 사진
    public static final String nickname_User_key = "nickname_User_key"; // 닉네임
    public static final String Apply_Letter_key = "Apply_Letter_key"; // 모집 참여 편지
    public static final String All_Studyhour_key = "All_Studyhour_key"; // 모집 참여 편지
    public static final String studyType_key = "studyType_key"; // 모집 참여 편지
    //    public static final String date_birth_User_key = "date_birth_User_key"; // 생년월일
//    public static final String gender_User_key = "gender_User_key";//성별
//    public static final String location_User_key = "location_User_key"; //지역

    //2)모집글  정보 키 값
    public static final String room_Name = "room_Name"; // 방 제목
    public static final String room_Number = "room_Number"; // 방 번호
    public static final String room_content = "room_content"; // 방 내용
    public static final String room_Maker_ID = "room_Maker_ID"; //만든사람 ID
    public static final String room_Maker_Nickname = "room_Maker_Nickname"; // 만든사람 닉네임
    public static final String room_Maker_StudyType = "room_Maker_StudyType"; // 만든사람 공부타입
    public static final String room_Maker_Profile = "room_Maker_Profile"; // 만든사람 프로필사진

    //2-1) 모집글의 번호를 저장한 json객체 키 값
    public static final String room_Name_apply = "room_Name_apply"; //방이름
    public static final String room_content_apply = "room_content_apply"; //방 내용
    public static final String room_MakerId_apply = "room_MakerId_apply"; //만든사람 ID
    public static final String room_Number_apply = "room_Number_apply"; // 방 번호
    public static final String room_profile_apply = "room_profile_apply"; //프로필 사진
    public static final String room_nickname_apply = "room_nickname_apply"; //닉네임
    public static final String room_studyType_apply = "room_studyType_apply"; //공부타입
    public static final String room_letter_apply = "room_letter_apply"; //편지

    //2-2) 내 모집글의 참여한 사용자의 정보를 저장한 json객체 키 값.
    public static final String Id_Joiner = "Id_Joiner"; //신청자 id
    public static final String Profile_Joiner = "Profile_Joiner"; // 신청자 프로필사진
    public static final String nickname_Joiner = "nickname_Joiner"; //신청자 닉네임
    public static final String RoomNumber_Joiner = "RoomNumber_Joiner"; //신청한 방 번호
    public static final String RoomMakerId_Joiner = "RoomMakerId_Joiner"; //만든사람 id
    public static final String studyType_Joiner = "studyType_Joiner"; //참여한 사람의 공부타입
    public static final String letter_Joiner = "letter_Joiner"; //작성한 글

    //3-1)쉐어드에 내 공부친구 데이터를 저장할 때 사용하는 json 객체 키 값
    public static final String id_MyFriend_KEY = "id_MyFriend"; //공부친구 id
    public static final String profile_MyFriend_KEY = "profile_MyFriend"; // 공부친구 프로필사진
    public static final String nickname_MyFriend_KEY = "nickname_MyFriend"; //공부친구 닉네임
    public static final String roomNumber_MyFriend_KEY = "roomNumber_MyFriend"; //공부친구 방 번호
    public static final String studyType_MyFriend_KEY = "studyType_MyFriend"; //공부친구 공부타입
    public static final String study_Time_MyFriend_KEY = "study_Time_MyFriend"; //공부친구 공부시간

    //6-1)공부 목표 리스트를 쉐어드에 저장할 때 사용하는 JSON 객체의 키 값
    public static final String Study_goal_KEY_goal = "Study_goal_KEY_goal"; //공부목표
    public static final String Study_time_KEY_goal = "Study_time_KEY_goal"; // 공부시간
    public static final String Goal_Number_KEY_goal = "Goal_Number_KEY_goal"; //리사이클러뷰 아이템 번호
    public static final String save_Date_key_goal = "save_Date_key_goal"; // 만든사람 id
    public static final String studyType_key_goal = "studyType_key_goal"; // 공부타입

    //7)전체공부 시간 통계를 쉐어드에 저장할 때 사용하는 Json객체의 키 값
//    public static final String List_StudyTime_Key = "List_StudyTime_Key"; // 측정한 공부시간 리스트의 키
//    public static final String Start_Date_Key = "Start_Date"; // 공부 시작 날짜
    public static final String Measure_Time_Key = "Start_Time"; // 측정한시간
    public static final String Start_Measure_Key = "Start_Measure_Key"; // 공부시작시간
    public static final String End_Measure_Key = "End_Measure_Key"; // 공부종료시간
    public static final String All_Made_id_key = "All_Made_id_key"; // 만든사람 id
    public static final String All_nickname_key = "All_nickname_key"; // 닉네임
    public static final String All_studyType_key = "All_studyType_key"; // 공부타입
    public static final String All_Rank_score_key = "All_Rank_score_key"; // 등수에 사용될 공부시간
    public static final String All_Rank_key = "All_Rank_key"; // 등수


    //8)문자메세지 리스트를 쉐어드에 저장할 때 사용하는 json 객체의 키 값
    public static final String message_key = "message_key"; // 메세지
    public static final String send_time_key = "send_time_key"; // 보낸시간
    public static final String send_Id_key = "send_Id_key"; // 보낸 사람 id
    public static final String send_profile_key = "send_profile_key"; // 보낸 사람 프로필사진
    public static final String send_nickname_key = "send_nickname_key"; // 보낸사람 닉네임
    public static final String send_date_key = "send_date_key"; // 보낸날짜
    public static final String firstMessage_key = "firstMessage_key"; // 처음보낸문자임을 표시하는 변수;


    /*전체 방 정보 리스트 키*/
    //json 으로 저장한 list key
    public static final String ListKey_StudyRoomDATA = "ListKey_StudyRoomDATA";

    /*쉐어드 프리퍼런스 이름 */
    //1)유저 정보 리스트 쉐어드 프리퍼런스
    public static final String PREFERENCES_Name_UserData = "PREFERENCES_UserData";
    //2)전체 모집글 정보 리스트 쉐어드 프리퍼런스
    public static final String PREFERENCES_Name_Room_infor = "PREFERENCES_Room_infor";
    //3)내가 보낸 방 리스트 쉐어드 프리퍼런스
    public static final String PREFERENCES_Apply_UserId_StudyRoom_Data = "PREFERENCES_Send_Application_StudyRoom_Data";
    //4)내가 받은 공부친구 신청 리스트 쉐어드 프리퍼런스
    public static final String PREFERENCES_Receive_StudyRoom_Data = "PREFERENCES_Receive_Application_StudyRoom_Data";
    //5)공부 친구의 데이터를 저장하는 쉐어드프리퍼런스
    public static final String PREFERENCES_MyStudyFriend_Data = "PREFERENCES_StudyFriend_Data";
    //6)공부 목표와 공부시간 데이터를 저장하는 쉐어드프리퍼런스
    public static final String PREFERENCES_StudyGoal_Data = "PREFERENCES_StudyGoal_Data";
    //7)공부시간 통계를 저장하는 쉐어드 프리퍼런스
    //일별 공부시간, 공부 시간측정 날짜
    public static final String PREFERENCES_Measure_StudyTime = "PREFERENCES_Measure_StudyTime";
    //8)과목별 공부한 시간을  저장하는 쉐어드 프리퍼런스
//    public static final String PREFERENCES_Measure_Subject_StudyTime = "PREFERENCES_Measure_Subject_StudyTime";
    //9)방 번호 (key)를 저장하는 쉐어드 프리퍼런스
    //공부 목표, 공부 친구 방
    public static final String PREFERENCES_RoomNum = "PREFERENCES_RoomNum";
    //10)공부처음 시작 시간을 저장하는 쉐어드 프리퍼런스
//    public static final String PREFERENCES_firstStudyTime = "PREFERENCES_firstStudyTime";
    //11)문자메세지를 저장하는 쉐어드 프리퍼런스
    public static final String PREFERENCES_Message = "PREFERENCES_Message";


    //쉐어드 프리퍼런스 불러오기  메소드
    public static SharedPreferences get_PREFERENCES(Context context, String preferenceName) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }


    //쉐어드 프리퍼런스에 저장된 키를 전부 불러오는 메서드
    public static Map<String, ?> getAll_Sharedkey(Context context, String Shared_Name) {
        SharedPreferences prefs = get_PREFERENCES(context, Shared_Name);//쉐어드 프리퍼런스 불러오기
        Map<String, ?> AllKey = prefs.getAll();//Map에 쉐어드에 저장된 모든 키 담기.
        return AllKey;
    }

    /*쉐어드에 데이터를 저장하고 불러오는 메서드*/
    //1-1)회원정보를 쉐어드에 저장하는  저장하는 메서드
    public static void Save_List_UserData_json(Context context, String Listkey, ArrayList<Data_user> list_DatauserData) throws JSONException {
        //arraylist의 저장된 데이터를 json 객체로 만들기
        JSONArray jarray = new JSONArray();  //객체를 묶어줄 배열 생성
        for (int i = 0; i < list_DatauserData.size(); i++) {//리스트 사이즈 만큼 json 배열안의 json 객체에 저장.
            JSONObject jobject = new JSONObject();

            jobject.put(Id_User_key, list_DatauserData.get(i).Id_User);//id
            jobject.put(Pw_User_key, list_DatauserData.get(i).Pw_User);//pw
//            jobject.put(content_User_key, list_DatauserData.get(i).content_User);//상태메세지
            jobject.put(iv_profile_User_key, list_DatauserData.get(i).iv_profile_User);//profile이미지
            jobject.put(nickname_User_key, list_DatauserData.get(i).nickname_User);//닉네임
            jobject.put(Apply_Letter_key, list_DatauserData.get(i).Apply_letter_User);//모집 참여 편지
            jobject.put(All_Studyhour_key, list_DatauserData.get(i).All_Studyhour);//전체 공부시간
            jobject.put(studyType_key, list_DatauserData.get(i).studyType);//전체 공부시간

            jarray.put(jobject);//json 배열안에 json 객체를 저장.
            //사용자 정보를 저장하는 쉐어드 프리퍼런스에 저장.
        }
        Save_String(context, Listkey, String.valueOf(jarray), PREFERENCES_Name_UserData);
    }

    //1-2)회원정보를 불러오는 메서드
    public static ArrayList<Data_user> get_List_UserData_Json(Context context, String Listkey) throws JSONException {
        ArrayList<Data_user> list_DatauserData = new ArrayList<>();//사용자 데이터를 list 생성.
        //get_String으로 리턴 받은 변수가 "기본 값"이 아닐때
        if (!get_String(context, Listkey, PREFERENCES_Name_UserData).equals(DEFAULT_VALUE_STRING)) {//쉐어드에 저장된 유저 데이터 확인.
            String str_List = get_String(context, Listkey, PREFERENCES_Name_UserData);//쉐어드에서 String으로 저장한 json 배열 불러오기.

            JSONArray jarray = new JSONArray(str_List);//json 배열에 쉐어드에 저장한 스트링을 담는다.
            for (int i = 0; i < jarray.length(); i++) {//json 배열에 담긴 데이터의 갯수만큼 반복
                //json 배열에 담긴 json 객체를 원래 list에 저장되어있던 변수 타입으로 변환 후
                //리스트에 담는다.
                JSONObject jobject = jarray.getJSONObject(i);
                String Id_User = jobject.getString(Id_User_key);
                String Pw_User = jobject.getString(Pw_User_key);
//                String content_User = jobject.getString(content_User_key);//사용자 상태메세지
                String iv_profile_User = jobject.getString(iv_profile_User_key);//사용자 프로필 사진
                String nickname_User = jobject.getString(nickname_User_key);//사용자 닉네임
                String Apply_letter = jobject.getString(Apply_Letter_key);//모집 참여 편지
                String All_Studyhour = jobject.getString(All_Studyhour_key);//전체 공부 시간
                String studyType = jobject.getString(studyType_key);//전체 공부 시간
                //리스트에 json 배열에 있던 json 객체들을 담는다.
                list_DatauserData.add(new Data_user(Id_User, Pw_User, iv_profile_User
                        , nickname_User, Apply_letter, All_Studyhour, studyType));
            }
        } else if (list_DatauserData == null) {
            Toast.makeText(context, "쉐어드에 저장한 유저 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return list_DatauserData;//json 배열에 저장된 유저 데이터를 옮긴 리스트를 반환.
    }


    //2-1) 모집 글 정보를 쉐어드에 저장하는 메서드
    //쉐어드에 json으로 ArrayList 저장
    public static void Save_List_StudyRoom_Infor_json(Context context, String Listkey, ArrayList<Data_StudyRoom_Infor> List_StudyRoom_Infor, String 저장할쉐어드이름) throws JSONException {
        //arraylist의 저장된 데이터를 json 객체로 만들기
        JSONArray jarray = new JSONArray();  //객체를 묶어줄 배열 생성
        for (int i = 0; i < List_StudyRoom_Infor.size(); i++) {//리스트 사이즈 만큼 json 배열안의 json 객체에 저장.
            JSONObject jobject = new JSONObject();//데이터를 담기위해 json객체 생성

            //ArrayList에 있는 데이터 갯수 만큼를 json 객체에 담기.
            jobject.put(room_Name, List_StudyRoom_Infor.get(i).roomName);//모집 글 제목
            jobject.put(room_content, List_StudyRoom_Infor.get(i).roomContent);//모집 글 내용
            jobject.put(room_Maker_ID, List_StudyRoom_Infor.get(i).room_Maker_Id);//만든사람 id
            jobject.put(room_Number, List_StudyRoom_Infor.get(i).roomNumber);//모집 글 번호
            jobject.put(room_Maker_Nickname, List_StudyRoom_Infor.get(i).tv_nickname);//만든사람 닉네임
            jobject.put(room_Maker_StudyType, List_StudyRoom_Infor.get(i).studyType);//만든사람 공부타입
            jobject.put(room_Maker_Profile, List_StudyRoom_Infor.get(i).iv_profile_uri);//만든사람 프로필사진
            //json배열안에 json 객체 저장
            jarray.put(jobject);
        }
        //쉐어드에 json배열을 String으로 형변환 후 저장
        Save_String(context, Listkey, String.valueOf(jarray), 저장할쉐어드이름);
    }

    //2-2) 쉐어드에서  모집 글 정보를 불러오는 메서드
    //쉐어드에 저장된 json배열 ArrayList로 불러오기
    public static ArrayList<Data_StudyRoom_Infor> get_List_StudyRoom_Infor_json(Context context, String Listkey, String 불러올쉐어드이름) throws JSONException {
        ArrayList<Data_StudyRoom_Infor> List_StudyRoom_Infor = new ArrayList<>();//불러올데이터리스트를생성한다.
        /*쉐어드에 데이터가 저장되어 있지 않을 경우에 대한 에러 예방 */
        //쉐어드에 String으로 불러온 json 배열이 없을 경우 기본값을 반환하기 때문에
        //불러온 String값이 기본 값이 아닌 경우만 if문을 통과할 수 있다.
        if (!get_String(context, Listkey, 불러올쉐어드이름).equals(DEFAULT_VALUE_STRING)) {
            String Json배열 = get_String(context, Listkey, 불러올쉐어드이름);//쉐어드에서 String으로 저장한 json 배열 불러오기.

            JSONArray jarray = new JSONArray(Json배열);//Json 배열에 쉐어드에서 불러온 JSON배열을 담는다.
            for (int i = 0; i < jarray.length(); i++) {//Json 배열에 담긴 데이터의 갯수만큼 반복
                //Json배열에 담긴 json객체를 갖고온 후 json객체에 저장된 변수 값을 String변수에 담는다.
                JSONObject jobject = jarray.getJSONObject(i);
                String RoomName = jobject.getString(room_Name);//방제목
                String Room_MakerID = jobject.getString(room_Maker_ID);//만든사람 ID
                String RoomContent = jobject.getString(room_content);
                int RoomNumber = jobject.getInt(room_Number);
                String iv_profile_uri = jobject.getString(room_Maker_Profile);
                String tv_nickname = jobject.getString(room_Maker_Nickname);
                String studyType = jobject.getString(room_Maker_StudyType);

                //담은 String 변수를 데이터 리스트에 각각 넣는다.
                //JSON배열에 있는 Json객체의 갯수만큼 반복한다.
                List_StudyRoom_Infor.add(new Data_StudyRoom_Infor(RoomName, RoomContent, Room_MakerID, RoomNumber, iv_profile_uri, tv_nickname, studyType));
            }
        } else if (List_StudyRoom_Infor == null) {//쉐어드에 저장된 데이터가 없을 경우
            Toast.makeText(context, "쉐어드에 저장한 유저 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return List_StudyRoom_Infor;//json 배열에 저장된 유저 데이터를 옮긴 리스트를 반환.
    }

    //3-1) 내가 보낸 신청서 리스트를 저장하는 메서드
    //listkey : 모임 참여한 사람의 id
    //쉐어드에 json으로 ArrayList 저장
    public static void Save_List_Apply_StudyRoom_Infor_json(Context context, String UserId, ArrayList<Data_StudyRoom_Infor> List_Apply_StudyRoom_Infor, String 저장할쉐어드이름) throws JSONException {
        //arraylist의 저장된 데이터를 json 객체로 만들기
        JSONArray jarray = new JSONArray();  //객체를 묶어줄 배열 생성
        for (int i = 0; i < List_Apply_StudyRoom_Infor.size(); i++) {//리스트 사이즈 만큼 json 배열안의 json 객체에 저장.
            JSONObject jobject = new JSONObject();//데이터를 담기위해 json객체 생성

            //ArrayList에 있는 데이터 갯수 만큼를 json 객체에 담기.
            jobject.put(room_Name_apply, List_Apply_StudyRoom_Infor.get(i).roomName);//방 제목
            jobject.put(room_content_apply, List_Apply_StudyRoom_Infor.get(i).roomContent);
            jobject.put(room_MakerId_apply, List_Apply_StudyRoom_Infor.get(i).room_Maker_Id);//만든사람 ID
            jobject.put(room_Number_apply, List_Apply_StudyRoom_Infor.get(i).roomNumber);//방 번호
            jobject.put(room_profile_apply, List_Apply_StudyRoom_Infor.get(i).iv_profile_uri);// 방 이미지
            jobject.put(room_nickname_apply, List_Apply_StudyRoom_Infor.get(i).tv_nickname);//만든 닉네임
            jobject.put(room_studyType_apply, List_Apply_StudyRoom_Infor.get(i).studyType);
            jobject.put(room_letter_apply, List_Apply_StudyRoom_Infor.get(i).room_letter);
            //json배열안에 json 객체 저장
            jarray.put(jobject);
            //쉐어드에 json배열을 String으로 형변환 후 저장
        }
        Save_String(context, UserId, String.valueOf(jarray), 저장할쉐어드이름);
    }

    //3-2) 내가 보낸 모임 신청서 리스트를 불러오는 메서드
    //불러온 모임 방 번호로 모임 방 데이터를 갖고온다.
    //listkey : 모임 참여한 사람의 id
    public static ArrayList<Data_StudyRoom_Infor> get_List_Apply_StudyRoom_Infor_json(Context context, String UserId, String 불러올쉐어드이름) throws JSONException {
        ArrayList<Data_StudyRoom_Infor> List_Apply_StudyRoom_Infor = new ArrayList<>();//불러올데이터리스트를생성한다.

        /*쉐어드에 데이터가 저장되어 있지 않을 경우에 대한 에러 예방 */
        //쉐어드에 String으로 불러온 json 배열이 없을 경우 기본값을 반환하기 때문에
        //불러온 String값이 기본 값이 아닌 경우만 if문을 통과할 수 있다.
        if (!get_String(context, UserId, 불러올쉐어드이름).equals(DEFAULT_VALUE_STRING)) {
            String Json배열 = get_String(context, UserId, 불러올쉐어드이름);//쉐어드에서 String으로 저장한 json 배열 불러오기.

            JSONArray jarray = new JSONArray(Json배열);//Json 배열에 쉐어드에서 불러온 JSON배열을 담는다.
            for (int i = 0; i < jarray.length(); i++) {//Json 배열에 담긴 데이터의 갯수만큼 반복
                //Json배열에 담긴 json객체를 갖고온 후 json객체에 저장된 변수 값을 String변수에 담는다.
                JSONObject jobject = jarray.getJSONObject(i);
                String str_Name_apply = jobject.getString(room_Name_apply);
                String str_content_apply = jobject.getString(room_content_apply);
                String str_MakerId_apply = jobject.getString(room_MakerId_apply);
                int str_Number_apply = jobject.getInt(room_Number_apply);
                String str_profile_apply = jobject.getString(room_profile_apply);
                String str_nickname_apply = jobject.getString(room_nickname_apply);
                String str_studyType_apply = jobject.getString(room_studyType_apply);
                String str_letter_apply = jobject.getString(room_letter_apply);

                //담은 String 변수를 데이터 리스트에 각각 넣는다.
                //JSON배열에 있는 Json객체의 갯수만큼 반복한다.
                List_Apply_StudyRoom_Infor.add(new Data_StudyRoom_Infor(str_Name_apply, str_content_apply, str_MakerId_apply, str_Number_apply,
                        str_profile_apply, str_nickname_apply, str_studyType_apply, str_letter_apply));
            }
        } else if (List_Apply_StudyRoom_Infor == null) {//쉐어드에 저장된 데이터가 없을 경우
            Toast.makeText(context, "쉐어드에 저장한 유저 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return List_Apply_StudyRoom_Infor;//json 배열에 저장된 유저 데이터를 옮긴 리스트를 반환.
    }

    //4-1)내가 받은 모임 신청서 리스트를 저장하는 메서드
    //쉐어드에 json으로 ArrayList 저장
    public static void Save_List_StudyRoom_Joiner_json(Context context, String RoomNumber, ArrayList<Data_StudyRoom_Infor> List_StudyRoom_Infor, String 저장할쉐어드이름) throws JSONException {
        //arraylist의 저장된 데이터를 json 객체로 만들기
        JSONArray jarray = new JSONArray();  //객체를 묶어줄 배열 생성
        for (int i = 0; i < List_StudyRoom_Infor.size(); i++) {//리스트 사이즈 만큼 json 배열안의 json 객체에 저장.
            JSONObject jobject = new JSONObject();//데이터를 담기위해 json객체 생성

            //ArrayList에 있는 데이터 갯수 만큼를 json 객체에 담기.
            jobject.put(Id_Joiner, List_StudyRoom_Infor.get(i).room_Joiner_Id);//참여한 사람id
            jobject.put(RoomMakerId_Joiner, List_StudyRoom_Infor.get(i).room_Maker_Id);//참여한 방의 만든사람 id
            jobject.put(Profile_Joiner, List_StudyRoom_Infor.get(i).iv_profile_roomJoiner);//참여한 사람 프로필사진
            jobject.put(nickname_Joiner, List_StudyRoom_Infor.get(i).NickName_room_Joiner);//참여한 사람 닉네임
            jobject.put(studyType_Joiner, List_StudyRoom_Infor.get(i).studyType_roomJoiner);//참여한 사람의 공부타입
            jobject.put(RoomNumber_Joiner, List_StudyRoom_Infor.get(i).roomNumber);//참여한 방 번호
            jobject.put(letter_Joiner, List_StudyRoom_Infor.get(i).room_letter);//참여할 때 쓴 편지

            //json배열안에 json 객체 저장
            jarray.put(jobject);
        }
        Save_String(context, RoomNumber, String.valueOf(jarray), 저장할쉐어드이름);
        //쉐어드에 json배열을 String으로 형변환 후 저장
    }

    //4-2)내가 받은 모임 신청서 리스트를 불러오는 메서드
    //쉐어드에 저장된 json배열 ArrayList로 불러오기
    public static ArrayList<Data_StudyRoom_Infor> get_List_StudyRoom_Joiner_json(Context context, String RoomNumber, String 불러올쉐어드이름) throws JSONException {
        ArrayList<Data_StudyRoom_Infor> List_StudyRoom_Joiner = new ArrayList<>();//불러올데이터리스트를생성한다.

        /*쉐어드에 데이터가 저장되어 있지 않을 경우에 대한 에러 예방 */
        //쉐어드에 String으로 불러온 json 배열이 없을 경우 기본값을 반환하기 때문에
        //불러온 String값이 기본 값이 아닌 경우만 if문을 통과할 수 있다.
        if (!get_String(context, RoomNumber, 불러올쉐어드이름).equals(DEFAULT_VALUE_STRING)) {
            String Json배열 = get_String(context, RoomNumber, 불러올쉐어드이름);//쉐어드에서 String으로 저장한 json 배열 불러오기.

            JSONArray jarray = new JSONArray(Json배열);//Json 배열에 쉐어드에서 불러온 JSON배열을 담는다.
            for (int i = 0; i < jarray.length(); i++) {//Json 배열에 담긴 데이터의 갯수만큼 반복
                //Json배열에 담긴 json객체를 갖고온 후 json객체에 저장된 변수 값을 String변수에 담는다.
                JSONObject jobject = jarray.getJSONObject(i);
                String str_Id_Joiner = jobject.getString(Id_Joiner);//참여한사람
                String str_RoomMakerId_Joiner = jobject.getString(RoomMakerId_Joiner);//만든사람
                String str_Profile_Joiner = jobject.getString(Profile_Joiner);//참여자 프로필사진
                String str_nickname_Joiner = jobject.getString(nickname_Joiner);//참여자 닉네임
                String str_studyType_Joiner = jobject.getString(studyType_Joiner);//참여자 공부타입
                int int_RoomNumber_Joiner = jobject.getInt(RoomNumber_Joiner);//방번호
                String str_letter_Joiner = jobject.getString(letter_Joiner);//참여자가 보낸 편지

                //담은 String 변수를 데이터 리스트에 각각 넣는다.
                //JSON배열에 있는 Json객체의 갯수만큼 반복한다.
                List_StudyRoom_Joiner.add(new Data_StudyRoom_Infor(str_Id_Joiner, str_RoomMakerId_Joiner, str_Profile_Joiner, str_nickname_Joiner,
                        str_studyType_Joiner, int_RoomNumber_Joiner, str_letter_Joiner));
            }
        } else if (List_StudyRoom_Joiner == null) {//쉐어드에 저장된 데이터가 없을 경우
            Toast.makeText(context, "쉐어드에 저장한 유저 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return List_StudyRoom_Joiner;//json 배열에 저장된 유저 데이터를 옮긴 리스트를 반환.
    }


    //    5-1)쉐어드에 내 공부 친구 정보를 저장하는 메서드
    public static void Save_List_MyStudyFriend_Infor_json(Context context, String UserId, ArrayList<Data_StudyRoom_Infor> List_MyStudyFriend, String 저장할쉐어드이름) throws JSONException {
        //arraylist의 저장된 데이터를 json 객체로 만들기
        JSONArray jarray = new JSONArray();  //객체를 묶어줄 배열 생성
        for (int i = 0; i < List_MyStudyFriend.size(); i++) {//리스트 사이즈 만큼 json 배열안의 json 객체에 저장.
            JSONObject jobject = new JSONObject();//데이터를 담기위해 json객체 생성

            //ArrayList에 있는 데이터 갯수 만큼를 json 객체에 담기.
            jobject.put(studyType_MyFriend_KEY, List_MyStudyFriend.get(i).studyType);
            jobject.put(id_MyFriend_KEY, List_MyStudyFriend.get(i).room_Joiner_Id);
            jobject.put(nickname_MyFriend_KEY, List_MyStudyFriend.get(i).NickName_room_Joiner);
            jobject.put(study_Time_MyFriend_KEY, List_MyStudyFriend.get(i).StudyTime);
            jobject.put(roomNumber_MyFriend_KEY, List_MyStudyFriend.get(i).roomNumber);
            jobject.put(profile_MyFriend_KEY, List_MyStudyFriend.get(i).iv_profile_uri);

            //json배열안에 json 객체 저장
            jarray.put(jobject);
            //쉐어드에 json배열을 String으로 형변환 후 저장
        }
        Save_String(context, UserId, String.valueOf(jarray), 저장할쉐어드이름);
    }

    //5-2) 쉐어드에 내 공부 친구 정보를 불러오는 메서드
    public static ArrayList<Data_StudyRoom_Infor> get_List_MyStudyFriend_Infor_json(Context context, String UserId, String 불러올쉐어드이름) throws JSONException {
        ArrayList<Data_StudyRoom_Infor> List_MyStudyFriend = new ArrayList<>();//불러올데이터리스트를생성한다.

        /*쉐어드에 데이터가 저장되어 있지 않을 경우에 대한 에러 예방 */
        //쉐어드에 String으로 불러온 json 배열이 없을 경우 기본값을 반환하기 때문에
        //불러온 String값이 기본 값이 아닌 경우만 if문을 통과할 수 있다.
        if (!get_String(context, UserId, 불러올쉐어드이름).equals(DEFAULT_VALUE_STRING)) {
            String Json배열 = get_String(context, UserId, 불러올쉐어드이름);//쉐어드에서 String으로 저장한 json 배열 불러오기.

            JSONArray jarray = new JSONArray(Json배열);//Json 배열에 쉐어드에서 불러온 JSON배열을 담는다.
            for (int i = 0; i < jarray.length(); i++) {//Json 배열에 담긴 데이터의 갯수만큼 반복
                //Json배열에 담긴 json객체를 갖고온 후 json객체에 저장된 변수 값을 String변수에 담는다.
                JSONObject jobject = jarray.getJSONObject(i);
                String studyType_MyFriend = jobject.getString(studyType_MyFriend_KEY);
                String id_MyFriend = jobject.getString(id_MyFriend_KEY);
                String nickname_MyFriend = jobject.getString(nickname_MyFriend_KEY);
                int study_Time_MyFriend = jobject.getInt(study_Time_MyFriend_KEY);
                int roomNumber_MyFriend = jobject.getInt(roomNumber_MyFriend_KEY);
                String profile_MyFriend = jobject.getString(profile_MyFriend_KEY);

                //담은 String 변수를 데이터 리스트에 각각 넣는다.
                //JSON배열에 있는 Json객체의 갯수만큼 반복한다.
                //"" 나중에 채워줄 공간.... 생성자 중복 문제로 지정함.
                List_MyStudyFriend.add(new Data_StudyRoom_Infor(studyType_MyFriend, id_MyFriend, nickname_MyFriend, "", study_Time_MyFriend,
                        roomNumber_MyFriend, profile_MyFriend));
            }
        } else if (List_MyStudyFriend == null) {//쉐어드에 저장된 데이터가 없을 경우
            Toast.makeText(context, "쉐어드에 저장한 유저 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return List_MyStudyFriend;//json 배열에 저장된 유저 데이터를 옮긴 리스트를 반환.
    }

    //6-1)공부 목표 리스트 쉐어드에 저장
    public static void Save_List_StudyGoal_json(Context context, String UserId, ArrayList<Data_StudyPlan> List_StudyGoal, String 저장할쉐어드이름) throws JSONException {
        //arraylist의 저장된 데이터를 json 객체로 만들기
        JSONArray jarray = new JSONArray();  //객체를 묶어줄 배열 생성
        for (int i = 0; i < List_StudyGoal.size(); i++) {//리스트 사이즈 만큼 json 배열안의 json 객체에 저장.
            JSONObject jobject = new JSONObject();//데이터를 담기위해 json객체 생성

            //ArrayList에 있는 데이터 갯수 만큼를 json 객체에 담기.
            jobject.put(Study_goal_KEY_goal, List_StudyGoal.get(i).Study_goal);//공부 목표
            jobject.put(Study_time_KEY_goal, List_StudyGoal.get(i).Study_time);//공부 시간
            jobject.put(Goal_Number_KEY_goal, List_StudyGoal.get(i).Goal_Number);//리사이클러뷰 아이템 번호
            jobject.put(save_Date_key_goal, List_StudyGoal.get(i).save_Date);//과목 측정 날짜.
            jobject.put(studyType_key_goal, List_StudyGoal.get(i).studyType);//공부타입

            //json배열안에 json 객체 저장
            jarray.put(jobject);
        }
        //쉐어드에 json배열을 String으로 형변환 후 저장
        Save_String(context, UserId, String.valueOf(jarray), 저장할쉐어드이름);
    }

    //6-2)공부 목표 리스트 쉐어드에 불러오기
    public static ArrayList<Data_StudyPlan> get_List_StudyGoal_json(Context context, String UserId, String 불러올쉐어드이름) throws JSONException {
        ArrayList<Data_StudyPlan> List_StudyGoal = new ArrayList<>();//불러올데이터리스트를생성한다.

        /*쉐어드에 데이터가 저장되어 있지 않을 경우에 대한 에러 예방 */
        //쉐어드에 String으로 불러온 json 배열이 없을 경우 기본값을 반환하기 때문에
        //불러온 String값이 기본 값이 아닌 경우만 if문을 통과할 수 있다.
        if (!get_String(context, UserId, 불러올쉐어드이름).equals(DEFAULT_VALUE_STRING)) {
            String Json배열 = get_String(context, UserId, 불러올쉐어드이름);//쉐어드에서 String으로 저장한 json 배열 불러오기.

            JSONArray jarray = new JSONArray(Json배열);//Json 배열에 쉐어드에서 불러온 JSON배열을 담는다.
            for (int i = 0; i < jarray.length(); i++) {//Json 배열에 담긴 데이터의 갯수만큼 반복
                //Json배열에 담긴 json객체를 갖고온 후 json객체에 저장된 변수 값을 String변수에 담는다.
                JSONObject jobject = jarray.getJSONObject(i);
                String Study_goal = jobject.getString(Study_goal_KEY_goal);
                String Study_time = jobject.getString(Study_time_KEY_goal);
                int Goal_Number = jobject.getInt(Goal_Number_KEY_goal);
                String save_Date = jobject.getString(save_Date_key_goal);
                String studyType = jobject.getString(studyType_key_goal);

                //담은 String 변수를 데이터 리스트에 각각 넣는다.
                //JSON배열에 있는 Json객체의 갯수만큼 반복한다.
                List_StudyGoal.add(new Data_StudyPlan(Study_goal, Study_time, Goal_Number, save_Date, studyType));
            }
        } else if (List_StudyGoal == null) {//쉐어드에 저장된 데이터가 없을 경우
            Toast.makeText(context, "쉐어드에 저장한 유저 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return List_StudyGoal;//json 배열에 저장된 공부 목표 데이터를 리스트화 시킨 것을 반환
    }


    //7-1)공부 시간 통계 리스트 쉐어드에 저장
    public static void Save_List_MeasureTime_json(Context context, String Date, ArrayList<Data_Measure_StudyTime> List_MeasureTime, String 저장할쉐어드이름) throws JSONException {
        //arraylist의 저장된 데이터를 json 객체로 만들기
        JSONArray jarray = new JSONArray();  //객체를 묶어줄 배열 생성
        for (int i = 0; i < List_MeasureTime.size(); i++) {//리스트 사이즈 만큼 json 배열안의 json 객체에 저장.
            JSONObject jobject = new JSONObject();//데이터를 담기위해 json객체 생성

            //ArrayList에 있는 데이터 갯수 만큼를 json 객체에 담기.
            jobject.put(Measure_Time_Key, List_MeasureTime.get(i).Measure_Time);//측정된 공부 시간
            jobject.put(Start_Measure_Key, List_MeasureTime.get(i).Start_Measure_Time);//공부 시작 시간
            jobject.put(End_Measure_Key, List_MeasureTime.get(i).End_Measure_Time);//공부 종료 시간
            jobject.put(All_Made_id_key, List_MeasureTime.get(i).made_id);//만든사람id
            jobject.put(All_nickname_key, List_MeasureTime.get(i).nickname_rank);//닉네임
            jobject.put(All_studyType_key, List_MeasureTime.get(i).studyType);//공부타입
            jobject.put(All_Rank_score_key, List_MeasureTime.get(i).ranking_score_rank);//등수 측정에 사용될 공부시간
            jobject.put(All_Rank_key, List_MeasureTime.get(i).rank);//등수


            //json배열안에 json 객체 저장
            jarray.put(jobject);
        }
        //쉐어드에 json배열을 String으로 형변환 후 저장
        Save_String(context, Date, String.valueOf(jarray), 저장할쉐어드이름);
    }

    //7-2)공부 시간 통계 리스트 쉐어드에 불러오기
    public static ArrayList<Data_Measure_StudyTime> get_List_MeasureTime_json(Context context, String Date, String 불러올쉐어드이름) throws JSONException {
        ArrayList<Data_Measure_StudyTime> List_MeasureTime = new ArrayList<>();//불러올데이터리스트를생성한다.
        /*쉐어드에 데이터가 저장되어 있지 않을 경우에 대한 에러 예방 */
        //쉐어드에 String으로 불러온 json 배열이 없을 경우 기본값을 반환하기 때문에
        //불러온 String값이 기본 값이 아닌 경우만 if문을 통과할 수 있다.
        if (!get_String(context, Date, 불러올쉐어드이름).equals(DEFAULT_VALUE_STRING)) {
            String Json배열 = get_String(context, Date, 불러올쉐어드이름);//쉐어드에서 String으로 저장한 json 배열 불러오기.

            JSONArray jarray = new JSONArray(Json배열);//Json 배열에 쉐어드에서 불러온 JSON배열을 담는다.
            for (int i = 0; i < jarray.length(); i++) {//Json 배열에 담긴 데이터의 갯수만큼 반복
                //Json배열에 담긴 json객체를 갖고온 후 json객체에 저장된 변수 값을 String변수에 담는다.
                JSONObject jobject = jarray.getJSONObject(i);
                String str_Measure_Time = jobject.getString(Measure_Time_Key);
                String Start_Measure = jobject.getString(Start_Measure_Key);
                String End_Measure = jobject.getString(End_Measure_Key);
                String made_id = jobject.getString(All_Made_id_key);
                String nickname = jobject.getString(All_nickname_key);
                String studyType = jobject.getString(All_studyType_key);
                int Rank_score = jobject.getInt(All_Rank_score_key);
                int studyRank = jobject.getInt(All_Rank_key);//랭킹


                //담은 String 변수를 데이터 리스트에 각각 넣는다.
                //JSON배열에 있는 Json객체의 갯수만큼 반복한다.
                List_MeasureTime.add(new Data_Measure_StudyTime(str_Measure_Time, Start_Measure, End_Measure, made_id, nickname, studyType, Rank_score, studyRank));
            }
        } else if (List_MeasureTime == null) {//쉐어드에 저장된 데이터가 없을 경우
            Toast.makeText(context, "쉐어드에 저장한 유저 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return List_MeasureTime;//json 배열에 저장된 유저 데이터를 옮긴 리스트를 반환.
    }


    //8-1)문자메세지 리스트를 쉐어드에 저장하는 메서드
    //쉐어드에 json으로 ArrayList 저장
    public static void Save_List_message_json(Context context, String RoomNumber, ArrayList<Data_Message> List_message, String 저장할쉐어드이름) throws JSONException {
        //arraylist의 저장된 데이터를 json 객체로 만들기
        JSONArray jarray = new JSONArray();  //객체를 묶어줄 배열 생성
        for (int i = 0; i < List_message.size(); i++) {//리스트 사이즈 만큼 json 배열안의 json 객체에 저장.
            JSONObject jobject = new JSONObject();//데이터를 담기위해 json객체 생성

            //ArrayList에 있는 데이터 갯수 만큼를 json 객체에 담기.
            jobject.put(message_key, List_message.get(i).send_message);//메세지
            jobject.put(send_time_key, List_message.get(i).send_Time);//보낸시간
            jobject.put(send_Id_key, List_message.get(i).send_Id);//보낸사람id
            jobject.put(send_profile_key, List_message.get(i).send_profile);//보낸사람 프로필사진
            jobject.put(send_nickname_key, List_message.get(i).send_Nickname);//보낸 사람 닉네임
            jobject.put(send_date_key, List_message.get(i).send_Date);//보낸 날짜
            jobject.put(firstMessage_key, List_message.get(i).firstMessage);//오늘 날짜로 보낸 문자메세지 확인


            //json배열안에 json 객체 저장
            jarray.put(jobject);
        }
        //쉐어드에 json배열을 String으로 형변환 후 저장
        Save_String(context, RoomNumber, String.valueOf(jarray), 저장할쉐어드이름);
    }

    //8-2)문자메세지 리스트를 쉐어드에 불러오는 메서드
    //쉐어드에 저장된 json배열 ArrayList로 불러오기
    public static ArrayList<Data_Message> get_List_message_json(Context context, String RoomNumber, String 불러올쉐어드이름) throws JSONException {
        ArrayList<Data_Message> List_message = new ArrayList<>();//불러올데이터리스트를생성한다.

        /*쉐어드에 데이터가 저장되어 있지 않을 경우에 대한 에러 예방 */
        //쉐어드에 String으로 불러온 json 배열이 없을 경우 기본값을 반환하기 때문에
        //불러온 String값이 기본 값이 아닌 경우만 if문을 통과할 수 있다.
        if (!get_String(context, RoomNumber, 불러올쉐어드이름).equals(DEFAULT_VALUE_STRING)) {
            String Json배열 = get_String(context, RoomNumber, 불러올쉐어드이름);//쉐어드에서 String으로 저장한 json 배열 불러오기.

            JSONArray jarray = new JSONArray(Json배열);//Json 배열에 쉐어드에서 불러온 JSON배열을 담는다.
            for (int i = 0; i < jarray.length(); i++) {//Json 배열에 담긴 데이터의 갯수만큼 반복
                //Json배열에 담긴 json객체를 갖고온 후 json객체에 저장된 변수 값을 String변수에 담는다.
                JSONObject jobject = jarray.getJSONObject(i);
                String str_message = jobject.getString(message_key);
                String str_send_time = jobject.getString(send_time_key);
                String str_send_id = jobject.getString(send_Id_key);
                String str_send_profile = jobject.getString(send_profile_key);
                String str_send_nickname = jobject.getString(send_nickname_key);
                String str_send_date = jobject.getString(send_date_key);
                String str_firstMessage = jobject.getString(firstMessage_key);

                //담은 String 변수를 데이터 리스트에 각각 넣는다.
                //JSON배열에 있는 Json객체의 갯수만큼 반복한다.
                List_message.add(new Data_Message(str_message, str_send_time, str_send_id, str_send_profile, str_send_nickname, str_send_date, str_firstMessage));
            }
        } else if (List_message == null) {//쉐어드에 저장된 데이터가 없을 경우
            Toast.makeText(context, "쉐어드에 저장한 유저 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return List_message;//json 배열에 저장된 유저 데이터를 옮긴 리스트를 반환.
    }


    // 쉐어드에 String 값 저장하는 메서드
    public static void Save_String(Context context, String key, String value, String preferenceName) {
        // 쉐어드 객체를 메소드를 통해서 생성
        SharedPreferences prefs = get_PREFERENCES(context, preferenceName);
        //쉐어드에 데이터를 저장하기 위한 Editor 객체 생성
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);//쉐어드에 String 저장.
        editor.commit();// 데이터 저장  잊지 말기.
    }

    // 쉐어드에 저장한 String을 불러오는 메서드
    public static String get_String(Context context, String key, String preferenceName) {
        SharedPreferences prefs = get_PREFERENCES(context, preferenceName);
        String value = prefs.getString(key, DEFAULT_VALUE_STRING);//값이없는 경우 "없음" 처리
        return value;
    }

    //쉐어드에 int 저장
    public static void saveInt(Context context, String key, int value, String preferenceName) {
        SharedPreferences prefs = get_PREFERENCES(context, preferenceName);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);//저장
        editor.commit();
    }


    //쉐어드에서 int 불러오기
    public static int getInt(Context context, String key, String preferenceName) {

        SharedPreferences prefs = get_PREFERENCES(context, preferenceName);
        int value = prefs.getInt(key, DEFAULT_VALUE_INT);//저장
        return value;
    }

    // 쉐어드에 키로 저장된 데이터 삭제
    public static void remove_key(Context context, String key, String preferenceName) {
        SharedPreferences pref = get_PREFERENCES(context, preferenceName);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);

        editor.commit();
    }

    //4. 쉐어드에 저장된 데이터 전체 삭제
    public static void Shared_clear(Context context, String preferenceName) {
        SharedPreferences pref = get_PREFERENCES(context, preferenceName);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();

        editor.commit();
    }
}

//이미지 uri 쉐어드에 저장 및 불러오기
       /*public static ArrayList<User> get_List_UserData_Json(Context context, String Listkey) throws JSONException {
    }*/
   /* public static void Save_ImageUri(Context context, String imageUrl) {
        SharedPreferences pref = get_PREFERENCES(context, PREFERENCES_Image_uri);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("imageUrl", imageUrl);
        // Commit the edits!
        editor.commit();
    }

    public static String get_ImageUri(Context context) {
        SharedPreferences preferences_Image = get_PREFERENCES(context, PREFERENCES_Image_uri);
        String Imageuri = preferences_Image.getString("imageUrl", null);
        return Imageuri;
    }*/
