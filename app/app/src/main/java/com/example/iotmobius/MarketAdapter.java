package com.example.iotmobius;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        // adapter에 들어갈 list 입니다.
        private ArrayList<DataMarket> listData = new ArrayList<>();
        Context mContext;




    public MarketAdapter(Context context) {
        mContext = context;

    }

    @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_item, parent, false);
            return new ViewHolderMarket(view, mContext);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ViewHolderMarket)holder).onBind(listData.get(position));
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }

        void addItem(DataMarket data) {
            // 외부에서 item을 추가시킬 함수입니다.
            listData.add(data);
        }
}


