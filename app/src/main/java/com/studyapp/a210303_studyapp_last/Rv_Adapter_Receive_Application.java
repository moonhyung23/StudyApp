package com.studyapp.a210303_studyapp_last;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Rv_Adapter_Receive_Application extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public ArrayList<Data_StudyRoom_Infor> List_StudyRoom_Receive = new ArrayList<>();
    Context context;

    public Rv_Adapter_Receive_Application(Context context) {
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
        List_StudyRoom_Receive.add(item);//item:리스트에들어갈데이터객체
    }

    //아이템 삭제
    public void removeItem(int position) {
        List_StudyRoom_Receive.remove(position);
    }


    //아이템 갖고오기
    public Data_StudyRoom_Infor getItem(int position) {
        return List_StudyRoom_Receive.get(position);
    }

    //아이템 수정
    public void edit_Item(int position, Data_StudyRoom_Infor item) {
        List_StudyRoom_Receive.set(position, item);
    }

    //아이템 리스트 가져오기
    public ArrayList<Data_StudyRoom_Infor> getList() {
        return this.List_StudyRoom_Receive;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_receive_application, parent, false);

        return new CustomViewHolder(view);//뷰홀더객체반환
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data_StudyRoom_Infor item = List_StudyRoom_Receive.get(position);
        //형변환 확인
        if (holder instanceof Rv_Adapter_Receive_Application.CustomViewHolder) {
            ((Rv_Adapter_Receive_Application.CustomViewHolder) holder).setItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return List_StudyRoom_Receive.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //뷰홀더에필요한데이터들을적는다.
        int pos;//클릭한아이템뷰의포지션번호
        ImageView iv_profile_Receive;//프로필사진
        TextView tv_nickname;//닉네임
        TextView tv_roomLetter;//상대방이 보낸 편지
        TextView tv_studyType;//공부타입
        Button btn_detailCheck;//상세보기 버튼

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //뷰홀더에 필요한 아이템데이터 findview
            iv_profile_Receive = itemView.findViewById(R.id.iv_profile_receive);
            tv_nickname = itemView.findViewById(R.id.tv_nickname_receive);
            tv_roomLetter = itemView.findViewById(R.id.tv_roomLetter_receive);
            tv_studyType = itemView.findViewById(R.id.tv_StudyType_receive);
            btn_detailCheck = itemView.findViewById(R.id.btn_check_receive);//상세보기 버튼

            //아이템뷰클릭리스너
            btn_detailCheck.setOnClickListener(new View.OnClickListener() {
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
        //리스트에서 받아온 아이템 객체
        public void setItem(Data_StudyRoom_Infor item) {
            tv_nickname.setText(item.NickName_room_Joiner);
            tv_roomLetter.setText(item.room_letter);
            tv_studyType.setText(item.studyType_roomJoiner);
            Glide.with(context).load(Uri.parse(item.iv_profile_roomJoiner)).into(iv_profile_Receive);
        }
    }
}

