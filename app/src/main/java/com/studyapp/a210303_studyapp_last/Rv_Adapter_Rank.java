package com.studyapp.a210303_studyapp_last;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Rv_Adapter_Rank extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<Data_Measure_StudyTime> List_Rank_StudyTime = new ArrayList<>();
    Context context;

    public Rv_Adapter_Rank(Context context) {
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
    public void addItem(Data_Measure_StudyTime item) {
        List_Rank_StudyTime.add(item);//item:리스트에들어갈데이터객체
    }

    //아이템 삭제
    public void removeItem(int position) {
        List_Rank_StudyTime.remove(position);
    }
    //아이템 전체 삭제
    public void clearItem() {
        List_Rank_StudyTime.clear();
    }


    //아이템 갖고오기
    public Data_Measure_StudyTime getItem(int position) {
        return List_Rank_StudyTime.get(position);
    }

    //아이템 수정
    public void edit_Item(int position, Data_Measure_StudyTime item) {
        List_Rank_StudyTime.set(position, item);
    }

    //아이템 리스트 가져오기
    public ArrayList<Data_Measure_StudyTime> getList() {
        return this.List_Rank_StudyTime;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_rank, parent, false);

        return new CustomViewHolder(view);//뷰홀더객체반환
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data_Measure_StudyTime item = List_Rank_StudyTime.get(position);
        //형변환 확인
        if (holder instanceof Rv_Adapter_Rank.CustomViewHolder) {
            ((Rv_Adapter_Rank.CustomViewHolder) holder).setItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return List_Rank_StudyTime.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //뷰홀더에필요한데이터들을적는다.
        int pos;//클릭한아이템뷰의포지션번호
        TextView tv_ranking;//등수
        TextView tv_nickname;//닉네임
        TextView tv_allstudyTime;//전체공부시간
        TextView tv_studyType;//공부타입


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //뷰홀더에 필요한 아이템데이터 findview
            tv_ranking = itemView.findViewById(R.id.tv_Ranking_rank);
            tv_nickname = itemView.findViewById(R.id.tv_nickname_Rank);
            tv_allstudyTime = itemView.findViewById(R.id.tv_studyTime_rank);
            tv_studyType = itemView.findViewById(R.id.tv_StudyType_Rank);

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
        public void setItem(Data_Measure_StudyTime item) {
            tv_ranking.setText(String.valueOf(item.rank));//등수
            tv_nickname.setText(item.nickname_rank);//닉네임
            tv_allstudyTime.setText(item.Measure_Time);//공부시간
            tv_studyType.setText(item.studyType);//공부타입
        }

    }
}

