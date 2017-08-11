package com.wenld.coustomlayoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wenld.multitypeadapter.CommonAdapter;
import com.wenld.multitypeadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView rlv;
    MyLayoutManager layoutManager = new MyLayoutManager(3);
//    GridLayoutManager layoutManager = new GridLayoutManager(this, 8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlv = (RecyclerView) findViewById(R.id.rlv_activity_main);
        rlv.setLayoutManager(layoutManager);
        CommonAdapter adapter = new CommonAdapter<String>(this, String.class, R.layout.item_card, 7) {
            int[] COLORS = {0xff00FFFF, 0xffDEB887, 0xff5F9EA0,
                    0xff7FFF00, 0xff6495ED, 0xffDC143C,
                    0xff008B8B, 0xff006400, 0xff2F4F4F,
                    0xffFF69B4, 0xffFF00FF, 0xffCD5C5C,
                    0xff90EE90, 0xff87CEFA, 0xff800000};

            int randomColor() {
                return COLORS[new Random().nextInt(COLORS.length)];
            }

            int randomColor(int pos) {
                return COLORS[pos%COLORS.length ];
            }

            @Override
            protected void convert(ViewHolder holder, final String o, int i) {
                long start = System.currentTimeMillis();
                holder.setText(R.id.text, o);
                CardItemView cardItemView = holder.getView(R.id.item);
                cardItemView.setCardColor(randomColor(i));
                cardItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), o, Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("   convert", "   onbindVIew " + (System.currentTimeMillis() - start) + "   " + i);
            }
        };
//        CardAdapter adapter = new CardAdapter();
        rlv.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item_" + i);
        }
        adapter.setItems(list);
    }

//    public static class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
//        List<String> datas;
//        int[] COLORS = {0xff00FFFF, 0xffDEB887, 0xff5F9EA0,
//                0xff7FFF00, 0xff6495ED, 0xffDC143C,
//                0xff008B8B, 0xff006400, 0xff2F4F4F,
//                0xffFF69B4, 0xffFF00FF, 0xffCD5C5C,
//                0xff90EE90, 0xff87CEFA, 0xff800000};
//
//        int randomColor() {
//            return COLORS[new Random().nextInt(COLORS.length)];
//        }
//
//        public void setDatas(List<String> datas) {
//            this.datas = datas;
//        }
//
//        @Override
//        public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            long start = System.currentTimeMillis();
//            CardAdapter.ViewHolder viewHolder = new CardAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent,false));
//            Log.e("   onCreateViewHolder", "   CreateVIew " + (System.currentTimeMillis() - start) );
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            long start = System.currentTimeMillis();
//
////            holder.cardItemView.setCardColor(randomColor());
//            holder.tv.setText(datas.get(position));
//            Log.e("   onBindViewHolder", position+"   onbindVIew " + (System.currentTimeMillis() - start) );
//        }
//
//        @Override
//        public int getItemCount() {
//            return datas.size();
//        }
//
//        static class ViewHolder extends RecyclerView.ViewHolder {
//            public CardItemView cardItemView;
//            public TextView tv;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
////                cardItemView = (CardItemView) itemView.findViewById(R.id.item);
//                tv = (TextView) itemView.findViewById(R.id.text);
//            }
//        }
//    }
}
