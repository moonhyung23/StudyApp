package com.studyapp.a210303_studyapp_last;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Rv_adapter_StudyPlan extends RecyclerView.Adapter<Rv_adapter_StudyPlan.CustomViewHolder> {

    public ArrayList<Data_StudyPlan> list_Data_StudyPlan = new ArrayList<>();
    Context context;

    public Rv_adapter_StudyPlan(Context context) {
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

    //어댑터에 있는 리스트에 데이터를 추가하는 메소드
    public void addItem(Data_StudyPlan item) {
        list_Data_StudyPlan.add(item); //item : 리스트에 들어갈 데이터 객체
    }

    //아이템 삭제
    public void removeItem(int position) {
        list_Data_StudyPlan.remove(position);
    }

    //아이템 리스트 가져오기
    public ArrayList<Data_StudyPlan> getList() {
        return this.list_Data_StudyPlan;
    }


    //List에 있는 객체를 position에 따라 갖고오는 메소드
    public Data_StudyPlan getItem(int position) {
        return list_Data_StudyPlan.get(position);
    }

    //List에 있는 아이템뷰  데이터 수정
    public void setItem(int position, Data_StudyPlan item) {
        list_Data_StudyPlan.set(position, item);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_goal_study, parent, false);

        return new CustomViewHolder(view);//공부 계획 추가 시 생성되는 아이템 뷰

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Data_StudyPlan item = list_Data_StudyPlan.get(position);
        holder.setItem(item);
    }


    @Override
    public int getItemCount() {
        return list_Data_StudyPlan.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        int pos;
        TextView tv_Study_goal;
        TextView tv_Study_Time;
        ImageView iv_playIcon;
        ImageView iv_OptionIcon;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Study_goal = itemView.findViewById(R.id.tv_Studygoal_rv);
            tv_Study_Time = itemView.findViewById(R.id.tv_Study_Time_rv);
            iv_playIcon = itemView.findViewById(R.id.iv_playicon_rv);
            iv_OptionIcon = itemView.findViewById(R.id.iv_option_rv);

            iv_playIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Myapplication.Num_option_play_Studygoal = 3;//공부시간측정 아이콘
                    //클릭한 아이템의 포지션을 갖고온다.
                    pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        //아이템에 포지션에 리스너 객체가 있는 경우
                        //리스너에 객체가 있는 경우
                        if (mListener != null) {
                            //리스너 객체가 있는 경우
                            //리스너 객체에 아이템의 포지션과 뷰를 넣어준다.
                            //인터페이스에 정의된 메소드가 아이템의 포지션을 알려준다.
                            mListener.onItemClick(v, pos);
                            notifyItemChanged(pos);
                        }
                    }
                }
            });
            //옵션 아이콘 클릭 리스너
            iv_OptionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Myapplication.Num_option_play_Studygoal = 1;//옵션아이콘 클릭
                    //클릭한 아이템의 포지션을 갖고온다.
                    pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        //아이템에 포지션에 리스너 객체가 있는 경우
                        //리스너에 객체가 있는 경우
                        if (mListener != null) {
                            //리스너 객체가 있는 경우
                            //리스너 객체에 아이템의 포지션과 뷰를 넣어준다.
                            //인터페이스에 정의된 메소드가 아이템의 포지션을 알려준다.
                            mListener.onItemClick(v, pos);
                            notifyItemChanged(pos);
                        }
                    }
                }
            });
        }

        public void setItem(Data_StudyPlan item) {//공부 계획 추가시 생성되는 아이템 데이터
            tv_Study_goal.setText(item.getStudy_goal());//공부 목표
            tv_Study_Time.setText(item.getStudy_time());//과목별 공부시간.
            Glide.with(context).load(R.drawable.play_icon).into(iv_playIcon);//공부 시작 아이콘
            Glide.with(context).load(R.drawable.option_icon).into(iv_OptionIcon);//옵션 아이콘
        }
    }
}
