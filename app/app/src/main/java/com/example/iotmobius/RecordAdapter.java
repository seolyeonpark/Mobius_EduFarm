package com.example.iotmobius;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Base64;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.CustomViewHolder>{
    private ArrayList<Record> arrayList;
    private Context context;
    public RecordAdapter(Context context, ArrayList<Record> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_view, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(arrayList.get(position).getImage());
        }
        State state = arrayList.get(position).getState();
        holder.record_date.setText(arrayList.get(position).getDate());
        holder.record_image.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0, bytes.length));
        holder.record_title.setText(arrayList.get(position).getTitle());
        holder.record_intext.setText(arrayList.get(position).getIntext());
        holder.record_sun.setText(""+state.getLight());
        holder.record_water.setText(""+state.getSoil());
        holder.record_temp.setText(""+state.getTemp());
        holder.record_humid.setText(""+state.getHumid());
        holder.record_comment.setText(arrayList.get(position).getComments());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 글 상세 + 댓글 받아오기 String comments =;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList) ? arrayList.size() : 0;

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView record_date;
        protected TextView record_sun;
        protected TextView record_water;
        protected TextView record_temp;
        protected TextView record_humid;

        protected ImageView record_image;
        protected TextView record_title;
        protected TextView record_intext;
        protected TextView record_comment;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.record_date = (TextView) itemView.findViewById(R.id.record_date);
            this.record_sun = (TextView) itemView.findViewById(R.id.record_sun);
            this.record_water = (TextView) itemView.findViewById(R.id.record_water);
            this.record_temp = (TextView) itemView.findViewById(R.id.record_temp);
            this.record_humid = (TextView) itemView.findViewById(R.id.record_humid);

            this.record_image = (ImageView) itemView.findViewById(R.id.record_iv);
            this.record_title = (TextView) itemView.findViewById(R.id.record_title);
            this.record_intext = (TextView) itemView.findViewById(R.id.record_intext);
            this.record_comment = (TextView) itemView.findViewById(R.id.record_comment);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
