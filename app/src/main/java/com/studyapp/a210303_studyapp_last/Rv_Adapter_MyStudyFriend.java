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

public class Rv_Adapter_MyStudyFriend extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    public ArrayList<Data_StudyRoom_Infor> List_Study_RoomInfor = new ArrayList<>();
    Context context;

    public Rv_Adapter_MyStudyFriend(Context context) {
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


    //아이템 추가
    public void addItem(Data_StudyRoom_Infor item) {
        List_Study_RoomInfor.add(item);//item:리스트에들어갈데이터객체
    }

    //아이템 삭제
    public void removeItem(int position) {
        List_Study_RoomInfor.remove(position);
    }


    //아이템 갖고오기
    public Data_StudyRoom_Infor getItem(int position) {
        return List_Study_RoomInfor.get(position);
    }

    //아이템 수정
    public void edit_Item(int position, Data_StudyRoom_Infor item) {
        List_Study_RoomInfor.set(position, item);
    }

    //아이템 리스트 가져오기
    public ArrayList<Data_StudyRoom_Infor> getList() {
        return this.List_Study_RoomInfor;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_myfriend_infor, parent, false);

        return new CustomViewHolder(view);//뷰홀더객체반환
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data_StudyRoom_Infor item = List_Study_RoomInfor.get(position);
        //형변환 확인
        if (holder instanceof Rv_Adapter_MyStudyFriend.CustomViewHolder) {
            ((Rv_Adapter_MyStudyFriend.CustomViewHolder) holder).setItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return List_Study_RoomInfor.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //뷰홀더에필요한데이터들을적는다.
        int pos;//클릭한아이템뷰의포지션번호
        TextView tv_StudyType;
        TextView tv_nickName;//닉네임
        TextView tv_room_message;//마지막으로보낸메세지
        ImageView iv_profile;//프로필사진


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //뷰홀더에 필요한 아이템데이터 findview
            tv_StudyType = itemView.findViewById(R.id.tv_StudyType_myFriend);//아이템에 들어갈 텍스트
            iv_profile = itemView.findViewById(R.id.iv_profile_myFriend);//아이템에 들어갈 이미지
            tv_nickName = itemView.findViewById(R.id.tv_nickname_myFriend);//닉네임
            tv_room_message = itemView.findViewById(R.id.tv_RoomMessage_myFriend);//마지막으로 보낸 메세지.

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
            tv_room_message.setText(item.roomContent);//마지막으로 보낸 메세지
            tv_StudyType.setText(item.getStudyType());
            tv_nickName.setText(item.NickName_room_Joiner);
            Glide.with(context).load(Uri.parse(item.iv_profile_uri)).into(iv_profile);//만든사람 프로필사진
        }
    }
}

