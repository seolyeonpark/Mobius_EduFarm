package com.example.iotmobius;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class ViewHolderMarket extends RecyclerView.ViewHolder {
    String vid;
    TextView title;
    TextView qty;


    private Button btn_send;

Context mcontext;

    public ViewHolderMarket(@NonNull View itemView , Context context) {
        super(itemView);
        mcontext = context;


        title = itemView.findViewById(R.id.name_text_view);
        qty = itemView.findViewById(R.id.profile_name);


        btn_send = itemView.findViewById(R.id.button_send);
        // 버튼 리스너 추가
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼 클릭시 Custom Dialog 호출
                int pos = getAdapterPosition() ;
                if (pos != RecyclerView.NO_POSITION) {
                    CustomDialog dlg = new CustomDialog(mcontext, vid);
                    dlg.show();
                }

            }
        });

    }


    public void onBind(DataMarket data){
        vid = data.getId();
        title.setText(data.getTitle());
        qty.setText(data.getQty());
    }

}
