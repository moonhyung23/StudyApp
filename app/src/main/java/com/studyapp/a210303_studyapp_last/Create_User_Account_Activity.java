package com.studyapp.a210303_studyapp_last;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Create_User_Account_Activity extends AppCompatActivity implements View.OnClickListener {
    boolean Id_check; //아이디 중복검사 변수
    Map<String, ?> Allkey;
    /*Button*/
    Button btn_Gallery, btn_Camera;//카메라 갤러리 버튼
    Button btn_UserJoin, btn_cancel;//가입하기, 가입취소 버튼


    /*UserData*/
    ImageView iv_Profile;//프로필 이미지
    String str_profile_Uri;//쉐어드에 저장할 String으로 형변환 시킨 프로필 이미지 uri
    EditText ed_id, ed_pw, ed_studyType, ed_nickname;//User데이터
    EditText ed_check_password;//비밀번호확인
    TextView tv_check_pw_message;//비밀번호 확인 메세지

    // 이미지 경로 변수와 요청변수 생성 => 카메라 갤러리 기능 사용하기 위해서
    String mCurrentPhotoPath;
    File filePath;

    /*Key, code, liST*/
    private final int GET_IMAGE_in_Galary = 202;
    final static int TAKE_PICTURE = 1;

    ArrayList<Data_user> list_DatauserData;//유저 정보를 담을 ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);
        list_DatauserData = new ArrayList<>();//유저 정보를 담을 list
        set_findview();
        set_buttonListner();

        ed_id.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        //비밀번호 중복검사에 필요한 메서드
        ed_check_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //비밀번호
                String str_ed_check_pw = String.valueOf(ed_check_password.getText());
                //비밀번호 확인
                String str_ed_pw = String.valueOf(ed_pw.getText());
                //같은 값인 경우
                if (str_ed_check_pw.equals(str_ed_pw)) {
                    tv_check_pw_message.setText("비밀번호가 일치합니다.");
                } else { //아닌 경우
                    tv_check_pw_message.setText("비밀번호가 일치하지 않습니다.");
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tedPermission();//카메라, 갤러리 권한
        try {
            //프로바이더  경로 추가
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp";
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            filePath = File.createTempFile("IMG", ".jpg", dir);
            if (!filePath.exists()) {
                filePath.createNewFile();
                Uri photoUri = FileProvider.getUriForFile(this, "프로젝트 패키지명.provider", filePath);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filePath));
                startActivityForResult(intent, 40);
            }

        } catch (Exception e) {
        }
    }

    public void set_findview() {//findview
        btn_Gallery = findViewById(R.id.btn_gallery);//갤러리 버튼
        btn_UserJoin = findViewById(R.id.btn_UserJoin);//회원가입 버튼
        btn_cancel = findViewById(R.id.btn_cancel_Join);//취소 버튼
        /*User data*/
        tv_check_pw_message = findViewById(R.id.tv_check_pw_message);
        iv_Profile = findViewById(R.id.iv_profile);//프로필 이미지
        ed_id = findViewById(R.id.ed_id_Join);//아이디
        ed_pw = findViewById(R.id.ed_pw_Join);//비밀번호
        ed_check_password = findViewById(R.id.ed_check_pw_Join);//비밀번호 확인
        ed_nickname = findViewById(R.id.ed_nickname_Join);//닉네임
        ed_studyType = findViewById(R.id.ed_studyType_CreateAccount);//공부타입
    }

    public void set_buttonListner() {//버튼 리스너 모음
//        btn_Camera.setOnClickListener(this);
        btn_Gallery.setOnClickListener(this);
        btn_UserJoin.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {//상단: 갤러리, 카메라 버튼
            case R.id.btn_gallery://갤러리 버튼
                Intent intent = new Intent(Intent.ACTION_PICK);//갤러리
                //얻어올 데이터 세팅(이미지 데이터)
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_IMAGE_in_Galary);//이미지 데이터 얻어오기
                break;

            //하단: 가입,  가입취소 버튼
            case R.id.btn_UserJoin://가입 => 유저 정보 데이터 쉐어드에 저장
                /*회원정보 입력 검사*/
                //아이디 입력 검사
                if (String.valueOf(ed_id.getText()).length() == 0) {
                    Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    ed_id.requestFocus();
                    return;
                }
                //비밀번호 입력 검사
                if (String.valueOf(ed_pw.getText()).length() == 0) {
                    Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    ed_pw.requestFocus();
                    return;
                }
                //비밀번호 확인 입력 검사
                if (String.valueOf(ed_check_password.getText()).length() == 0) {
                    Toast.makeText(this, "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
                    ed_check_password.requestFocus();
                    return;
                }
                //하고계신 공부 입력 검사
                if (String.valueOf(ed_studyType.getText()).length() == 0) {
                    Toast.makeText(this, "하고계신공부를 입력하세요", Toast.LENGTH_SHORT).show();
                    ed_studyType.requestFocus();
                    return;
                }
                //닉네임 입력 검사
                if (String.valueOf(ed_nickname.getText()).length() == 0) {
                    Toast.makeText(this, "낙네임을 입력하세요", Toast.LENGTH_SHORT).show();
                    ed_nickname.requestFocus();
                    return;
                }
                //비밀번호 확인 텍스트 String으로 변환
                String str_check_password = String.valueOf(ed_check_password.getText());
                //비밀번호 일치하는지 검사
                if (!String.valueOf(ed_pw.getText()).equals(str_check_password)) {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    ed_check_password.requestFocus();
                    return;
                }

                /*아이디 중복 검사*/
                String id_UserData = String.valueOf(ed_id.getText());//아이디
                String pw_UserData = String.valueOf(ed_pw.getText());//비밀번호
                String studyType = String.valueOf(ed_studyType.getText());//공부타입
//                String content_UserData = "없음";//상태메세지
                String nickname_UserData = String.valueOf(ed_nickname.getText());//닉네임
                Id_check = Id_Redundancy_Check(id_UserData, getApplicationContext());//Id 중복검사 boolean 변수
                if (str_profile_Uri == null) {//프로필 사진 선택 안할 시 없음 처리 => json파싱 시 없으면 error 생김 ㅅㅂ
                    str_profile_Uri = "없음";
                }

                //아이디 중복검사 if문
                if (Id_check) {
                    //유저 정보를 list에 저장
                    //회원가입에 들어가는 유저 정보
                    //ID, PW, 상태메세지, 지역, 프로필이미지, 닉네임, 성별, 공부친구에게 보내는편지, 전체 공부시간, 공부타입
                    list_DatauserData.add(new Data_user(id_UserData, pw_UserData,
                            str_profile_Uri, nickname_UserData, "", "00 : 00 : 00", studyType));

                    try {//저장한 유저정보 List를 쉐어드 프리퍼런스에 json 배열로 파싱해서 저장.
                        /*쉐어드에 저장한 키  => 사용자의 [ID]로 저장*/
                        SharedClass.Save_List_UserData_json(getApplicationContext(), id_UserData, list_DatauserData);
                    } catch (JSONException e) {
                    }

                    //로그인 엑티비티로 이동
                    Toast.makeText(this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
                    startActivity(intent2);
                    finish();

                } else {//아이디 중복검사 실패
                    Toast.makeText(getApplicationContext(), "중복된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_cancel_Join://가입취소
                Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent2);
                finish();
                break;
        }

    }

    //회원가입  아이디 중복검사 메서드
    public boolean Id_Redundancy_Check(String Id, Context context) {
        boolean check = true;
        Allkey = SharedClass.getAll_Sharedkey(context, SharedClass.PREFERENCES_Name_UserData);
        for (Map.Entry<String, ?> entry : Allkey.entrySet()) {//쉐어드에 저장된 모든 키를 비교
            //입력한 아이디와 => 쉐어드에 저장된 키 값이 같은 경우
            //아이디 중복으로 가입 불가. => check = false
            if (Id.equals(entry.getKey())) {
                check = false;//유저 정보 key 값이 하나라도 같은 것이 있을 경우 => false;
            }
        }
        return check;//true일 때만 반환
    }

    @Override
    //뒤로가기 버튼 처리 메서드
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
        startActivity(intent);
        finish();
    }


    @Override//엑티비티에서 데이터 가져오기.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri selectedImageUri;//이미지 uri
        //이미지 동그랗게 하기.
        MultiTransformation option2 = new MultiTransformation(new CenterCrop(), new RoundedCorners(8));
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) { //갤러리에서 이미지 데이터 가져오기
            switch (requestCode) {
                case GET_IMAGE_in_Galary:
                    selectedImageUri = data.getData();//선택한 이미지의 uri를 받아온다.
                    //Glide를 통해서 이미지뷰에 이미지 데이터 넣기
                    //메서드 인자값 : 이미지 uri, 이미지옵션(동그랗게 하기), 이미지뷰
                    Glide.with(getApplicationContext()).load(selectedImageUri).apply(RequestOptions.bitmapTransform(option2)).into(iv_Profile);
                    str_profile_Uri = String.valueOf(selectedImageUri);//Glide에서 사용한 image uri를 String으로 형변환 후에 String 변수에 담기.
                    break;
            }
        }

        switch (requestCode) { //카메라에서 이미지 데이터 가져오기
            case TAKE_PICTURE:
                File file = new File(mCurrentPhotoPath);//파일 경로
                Bitmap bitmap;
                if (Build.VERSION.SDK_INT >= 29) {//sdk version 29이상일 경우
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                    try {
                        bitmap = ImageDecoder.decodeBitmap(source);
                        if (bitmap != null) {
                            iv_Profile.setImageBitmap(bitmap);//이미지 비트맵으로 저장.
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {//sdk version 29이하일 경우
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            iv_Profile.setImageBitmap(bitmap);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    //    사진 촬영후 썸네일만띄워줌.이미지를 파일로저장해야 함
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //    카메라 인텐트실행하는 부분
    private void dispatchTakePictureIntent() {
        //카메라 이동 인텐트 생성
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {//file (try - catch)
                photoFile = createImageFile();//사진 파일 생성
            } catch (IOException ex) {
            }
            if (photoFile != null) {//사진 존재 여부 검사
                //photoFile(찍은 사진)이 있을 경우 사진의 uri를 file프로바이더에서 제공
                Uri photoURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);//인텐트에 사진 uri저장
                startActivityForResult(takePictureIntent, TAKE_PICTURE);//인텐트에 사진 uri를 저장
            }
        }
    }

    //카메라, 갤러리 권한 설정 메서드
    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
}
