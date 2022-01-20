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

public class Rv_Adapter_MyRoom extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public ArrayList<Data_StudyRoom_Infor> List_studyMyRoom_Infor = new ArrayList<>();
    Context context;

    public Rv_Adapter_MyRoom(Context context) {
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
        return this.List_studyMyRoom_Infor;
    }


    //아이템 추가
    public void addItem(Data_StudyRoom_Infor item) {
        List_studyMyRoom_Infor.add(item);//item:리스트에들어갈데이터객체
    }

    //아이템 삭제
    public void removeItem(int position) {
        List_studyMyRoom_Infor.remove(position);
    }


    //아이템 갖고오기
    public Data_StudyRoom_Infor getItem(int position) {
        return List_studyMyRoom_Infor.get(position);
    }

    //아이템 수정
    public void setItem(int position, Data_StudyRoom_Infor item) {
        List_studyMyRoom_Infor.set(position, item);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_myroom_studyfriend, parent, false);

        return new CustomViewHolder(view);//뷰홀더객체반환
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data_StudyRoom_Infor item = List_studyMyRoom_Infor.get(position);
        //형변환 확인
        if (holder instanceof Rv_Adapter_MyRoom.CustomViewHolder) {
            ((Rv_Adapter_MyRoom.CustomViewHolder) holder).setItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return List_studyMyRoom_Infor.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //뷰홀더에필요한데이터들을적는다.
        int pos;//클릭한아이템뷰의포지션번호
        TextView tv_roomSubject;
        ImageView iv_profile;
        TextView tv_roomContent;
        TextView tv_nickname;
        TextView tv_edit_item;
        TextView tv_remove_item;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //뷰홀더에 필요한 아이템데이터 findview
            tv_nickname = itemView.findViewById(R.id.tv_nickname_myroom);//닉네임
            tv_roomSubject = itemView.findViewById(R.id.tv_roomSubject_myroom);//모집 글 제목
            tv_roomContent = itemView.findViewById(R.id.tv_RoomContent_myroom);//모임 글 내용
            iv_profile = itemView.findViewById(R.id.iv_profile_myroom);//모집 글 지은 사람 프로필 사진
            //편집, 삭제
            tv_edit_item = itemView.findViewById(R.id.tv_editRoom_myroom);//모집 글 편집
            tv_remove_item = itemView.findViewById(R.id.tv_removeRoom_myroom);//모집 글 삭제


            /*버튼을 누르면 번호가 정해지며 각각 번호마다 작성, 삭제, 편집이 다르다.
             *1번 작성
             * 2번 삭제
             * 3번 편집*/
            //편집 텍스트 누를 때 누른 아이템 뷰의 포지션을 돌려준다.
            tv_edit_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Myapplication.num_StudyMyRoom_add_edit_remove = 3;//3번 편집
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

            //삭제 텍스트 누를 때 누른 아이템 뷰의 포지션을 돌려준다.
            tv_remove_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Myapplication.num_StudyMyRoom_add_edit_remove = 2;//2번 삭제
                    //클릭한아이템의포지션을갖고온다.
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {//리스너객체확인
                            //리스너객체에아이템의포지션과뷰를넣어준다.
                            //인터페이스에정의된메소드가클릭한아이템의포지션을전달한다.
                            mListener.onItemClick(v, pos);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //아이템뷰에 binding할 데이터
        public void setItem(Data_StudyRoom_Infor item) {
            tv_roomSubject.setText(item.getRoomName());
            tv_roomContent.setText(item.getRoomContent());
            tv_nickname.setText(item.tv_nickname);
            Glide.with(context).load(Uri.parse(item.iv_profile_uri)).into(iv_profile);
        }
    }
}

