package com.studyapp.a210303_studyapp_last;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Rv_Adapter_StudyRoom extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public ArrayList<Data_StudyRoom_Infor> List_StudyRoom = new ArrayList<>();
    Context context;

    public Rv_Adapter_StudyRoom(Context context) {
        this.context = context;
    }

    OnItemClickListener mListener = null;//아이템 클릭 리스너

    //아이템 클릭 인터페이스
    public interface OnItemClickListener {//아이템 클릭 리스너를 전달해주는 메서드를 갖고있음

        void onItemClick(View v, int position);
    }

    //메서드를 통해서 아이템 클릭 인터페이스를 전달해준다.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
        //Listener 객체를 생성.
    }

    //데이터 리스트를 반환하는 메서드
    public ArrayList<Data_StudyRoom_Infor> getList() {
        return this.List_StudyRoom;
    }

    //아이템 추가
    public void addItem(Data_StudyRoom_Infor item) {
        List_StudyRoom.add(item);//item:리스트에들어갈데이터객체
    }

    //아이템 삭제
    public void removeItem(int position) {
        List_StudyRoom.remove(position);
    }


    //아이템 갖고오기
    public Data_StudyRoom_Infor getItem(int position) {
        return List_StudyRoom.get(position);
    }

    //아이템 수정
    public void setItem(int position, Data_StudyRoom_Infor item) {
        List_StudyRoom.set(position, item);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_studyfriend_infor, parent, false);

        return new CustomViewHolder(view);//뷰홀더객체반환
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data_StudyRoom_Infor item = List_StudyRoom.get(position);

        //형변환 확인
        if (holder instanceof Rv_Adapter_StudyRoom.CustomViewHolder) {
            ((Rv_Adapter_StudyRoom.CustomViewHolder) holder).setItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return List_StudyRoom.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //뷰홀더에필요한데이터들을적는다.
        int pos;//클릭한아이템뷰의포지션번호
        TextView tv_nickname;//만든 사람 닉네임
        TextView tv_RoomName;//모집 글 제목
        TextView tv_RoomContent;//모집 글 내용
        TextView tv_studyType;//만든사람 공부타입
        ImageView iv_profile;//아이템에 들어갈 이미지

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //뷰홀더에 필요한 아이템데이터 findview
            tv_RoomName = itemView.findViewById(R.id.tv_roomSubject_All_Studyroom);//모집글 제목
            tv_RoomContent = itemView.findViewById(R.id.tv_roomContent_All_Studyroom);//모집글 내용
            iv_profile = itemView.findViewById(R.id.iv_profile_All_Studyroom);//모집 글 만든사람 프로필사진
            tv_nickname = itemView.findViewById(R.id.tv_nickname_All_Studyroom);//만든사람 닉네임
            tv_studyType = itemView.findViewById(R.id.tv_StudyType_All_Studyroom);//만든사람 공부타입

            //아이템뷰클릭리스너
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //클릭한아이템의포지션을갖고온다.
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {//리스너객체확인
                            //리스너객체에아이템의포지션과뷰를넣어준다.
                            //인터페이스에정의된메소드가클릭한아이템의포지션을전달한다.
                            mListener.onItemClick(v, pos);
                            notifyItemChanged(pos);
                        }
                    }
                }
            });
        }

        //아이템뷰에 binding할 데이터
        public void setItem(Data_StudyRoom_Infor item) {
            //리사이클러뷰의 아이템에 데이터가 입력되는 부분
            tv_RoomName.setText(item.getRoomName());//모집글 이름
            tv_RoomContent.setText(item.getRoomContent());//모집글 내용
            tv_nickname.setText(item.tv_nickname);//만든사람 닉네임
            tv_studyType.setText(item.studyType);//만든사람 공부타입
            Glide.with(context).load(Uri.parse(item.iv_profile_uri)).into(iv_profile);//만든사람 프로필사진
        }
    }
}
