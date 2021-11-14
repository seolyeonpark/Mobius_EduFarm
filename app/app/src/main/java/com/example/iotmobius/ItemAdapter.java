package com.example.iotmobius;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ItemAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<DataItem> itemlist = new ArrayList<>();
    Context context;
    int pos;


    // 생성자: 생성자에서 데이터 리스트 객체를 전달받음.
    public ItemAdapter(Context context) {
        //this.itemlist = il;
        this.context = context;
        // 어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
        // 선택한 액티비티에 대한 context를 가져올 때 필요하다.
    }

    // 뷰홀더
    // 이 부분에서 super를 통해 상속을 받았다.
    // 이 RecyclerView 에 뷰 holder 에서 상속을 받아서 거기에 아이템 값을 찾아와야 한다.
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, itempoint;
        String usr; //학생이름
        String itm; //구매한아이템id


        // 뷰홀더
        public MyViewHolder(View view) {
            super(view);

            this.itemname = (TextView) view.findViewById(R.id.my_item);

            this.itempoint = (TextView) view.findViewById(R.id.about_item);

            // MyViewHolder 가 리사이클러뷰의 각 뷰 항목을 만드는 역할을 하기 때문에, 여기서 작업을 해야한다.

        }

        public void onBind(DataItem data){
            usr = data.getUser();
            itm = data.getItem();
            itemname.setText(data.getI_name());
            itempoint.setText(data.getPoint());
        }
    }

    // 리스트 뷰가 어댑터에 연결된 다음 이쪽에서 뷰 홀더를 최초로 만들어 냄.
    @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, parent, false); //view연결
            MyViewHolder holder = new MyViewHolder(cardView);
            return holder;

        // 각각의 아이템을 위한 뷰를 담고있는 뷰홀더 객체를 반환한다.
        // (각 아이템을 위한 XML 레이아웃을 이용해 뷰 객체를 만들었고 이걸 뷰홀더에서 참조할 수 있도록 위에 만들어 놓음)
        }

    // 각 아이템들에 대한 실제적인 매칭해주는 곳
    // onBindViewHolder() - position 서로 결합되는 경우 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    // 각각의 아이템을 위한 뷰의 xml 레이아웃 호출(즉, 뷰홀더가 각각의 아이템을 위한 뷰를 담아주기 위한 용도인데, 뷰와 아이템이 합쳐질 때 호출)
    // 적절한 데이터를 가져와서 뷰 소유자의 레이아웃을 채우기 위해서 사용(뷰홀더에 각 아이템의 데이터를 설정해 놓았음.)
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            ((MyViewHolder)holder).onBind(itemlist.get(position));

            context = holder.itemView.getContext();

        }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
        @Override
        public int getItemCount() {
        //  삼항연산자 arrayList 가 null이면 왼쪽꺼 실행 아니면 오른쪽거 실행
            return (itemlist != null ? itemlist.size() : 0);
        }

        void addItem(DataItem data) {
        // 외부에서 item을 추가시킬 함수입니다.
            itemlist.add(data);
        }

}
