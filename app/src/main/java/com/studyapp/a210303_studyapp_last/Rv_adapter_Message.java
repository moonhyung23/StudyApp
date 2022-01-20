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

public class Rv_adapter_Message extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public ArrayList<Data_Message> List_Message = new ArrayList<>();
    Context context;

    public Rv_adapter_Message(Context context) {
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
    public void addItem(Data_Message item) {
        List_Message.add(item);//item:리스트에들어갈데이터객체
    }

    //아이템 삭제
    public void removeItem(int position) {
        List_Message.remove(position);
    }


    //아이템 갖고오기
    public Data_Message getItem(int position) {
        return List_Message.get(position);
    }

    //아이템 갖고오기
    public int getSize() {
        return List_Message.size();
    }

    //아이템 수정
    public void edit_Item(int position, Data_Message item) {
        List_Message.set(position, item);
    }

    //아이템 리스트 가져오기
    public ArrayList<Data_Message> getList() {
        return this.List_Message;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //OnCreateViewHolder 메서드에서 사용
        switch (viewType) {//뷰타입에 따라서 다른 뷰홀더를 생성한다.
            //내가 보낸 메세지
            case 1://뷰홀더 타입 1번
                view = inflater.inflate(R.layout.item_me_message, parent, false);
                return new CustomViewHolder(view);//아이템 뷰 생성

            //상대방이 보낸 메세지
            case 2://뷰홀더 타입 2번
                view = inflater.inflate(R.layout.item_you_message, parent, false);
                return new CustomViewHolder2(view);//아이템 뷰 생성
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data_Message item = List_Message.get(position);
        //OnBindViewHolder메서드 에서 사용
        switch (item.view_Type_num) {
            //내가 보낸 메세지
            case 1://뷰홀더 타입 1번
                //instanceof를 통해 형변환이 가능한지 확인한다.
                //뷰홀더 클래스에 있는 각각의 아이템뷰의 데이터를 갖고오는
                //add_setItem()메서드를 사용하기 위해서 형변환이 필요하다.
                if (holder instanceof CustomViewHolder) {//형 변환 가능 여부 확인
                    ((CustomViewHolder) holder).setItem(item);
                }
                break;
            //상대방이 보낸 메세지
            case 2://뷰홀더 타입 2번
                if (holder instanceof CustomViewHolder2) {//형변환 가능 여부 확인
                    ((CustomViewHolder2) holder).setItem2(item);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return List_Message.size();
    }


    //나의 메세지
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //뷰홀더에필요한데이터들을적는다.
        TextView tv_message;
        TextView tv_send_time_me;
        TextView tv_nickname;
        ImageView iv_profile;
        TextView tv_today_date_me;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //뷰홀더에 필요한 아이템데이터 findview
            tv_message = itemView.findViewById(R.id.tv_send_message_item);//아이템에 들어갈 텍스트
            tv_send_time_me = itemView.findViewById(R.id.tv_time_me_message);
            tv_today_date_me = itemView.findViewById(R.id.tv_TodayDate_Message_me);
//            tv_nickname = itemView.findViewById(R.id.tv_ID);//아이템에 들어갈 텍스트
//            iv_profile = itemView.findViewById(R.id.i);//아이템에 들어갈 이미지


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
        public void setItem(Data_Message item) {
            //오늘 날짜로 보낸 문자가 있는 경우에만
            //오늘 날짜 텍스트 지우기.
            if (item.firstMessage.equals("0")) {
                tv_today_date_me.setVisibility(View.GONE);
            } else {
                //오늘 날짜로 보낸 문자가 없는 경우
                //오늘 날짜 입력
                tv_today_date_me.setVisibility(View.VISIBLE);
                tv_today_date_me.setText(item.send_Date);
            }


            tv_message.setText(item.getSend_message());
            tv_send_time_me.setText(item.getSend_Time());//보낸시간 입력
//            tv_nickname.setText(item.get갖고올데이터);
//            Glide.with(context).load(R.drawable.add_icon).into(iv_profile);
        }
        //뷰홀더를 하나의 리사이클러뷰에 여러개 사용할 때 사용한다.
    }

    //상대방의 메세지
    public class CustomViewHolder2 extends RecyclerView.ViewHolder {
        //뷰홀더에필요한데이터들을적는다.
        TextView tv_message;//문자메세지
        TextView tv_nickname;//닉네임
        ImageView iv_profile;//프로필사진
        TextView tv_send_time_you;//보낸 시간
        TextView tv_today_date_you;//상대방이 보낸 메세지의 보낸날짜
        //        TextView tv_nickname;
//        ImageView iv_profile;

        //받은 사람
        public CustomViewHolder2(@NonNull View itemView) {
            super(itemView);
            //뷰홀더에 필요한 아이템데이터 findview
            tv_message = itemView.findViewById(R.id.tv_receive_message_item);
            tv_send_time_you = itemView.findViewById(R.id.tv_time_you_message);
            tv_today_date_you = itemView.findViewById(R.id.tv_TodayDate_Message_you);
            tv_nickname = itemView.findViewById(R.id.nickname_message_you);
            iv_profile = itemView.findViewById(R.id.iv_profile_you);


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
        public void setItem2(Data_Message item) {

            //오늘 날짜로 보낸 문자가 없는 경우 오늘 날짜를 표시해준다.
            if (item.firstMessage.equals("0")) {
                tv_today_date_you.setVisibility(View.GONE);//텍스트 숨기기
            } else {
                //오늘 날짜로 보낸 문자가 없는 경우
                //오늘 날짜 입력
                tv_today_date_you.setVisibility(View.VISIBLE);//텍스트 보이게하기
                tv_today_date_you.setText(item.send_Date);//오늘 날짜 입력
            }
            //이외 데이터
            tv_message.setText(item.getSend_message());//보낸 메세지 입력
            tv_send_time_you.setText(item.getSend_Time());//보낸시간 입력
            tv_nickname.setText(item.send_Nickname);
            Glide.with(context).load(Uri.parse(item.send_profile)).into(iv_profile);
        }
        //뷰홀더를 하나의 리사이클러뷰에 여러개 사용할 때 사용한다.
    }


    @Override
    public int getItemViewType(int position) {
        //아이템에 포지션을 갖고온다.
        Data_Message item = List_Message.get(position);//선택한 아이템뷰 객체를 생성
        //날짜 표시 xml
        if (item.view_Type_num == 3) {
            return 3;
        }

        //뷰홀더 타입 1번 (내가 보낸 문자 xml)
        //보내는 사람 id == 내 id
        if (item.send_Id.equals(Myapplication.UserId)) {
            item.setView_Type_num(1);
            return 1;
        } else {//뷰홀더 타입 2번 // 받는 사람(상대방이 보낸 문자 xml)
            item.setView_Type_num(2);
            return 2;
        }
    }
}

