package com.wenld.coustomlayoutmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wenld.multitypeadapter.CommonAdapter;
import com.wenld.multitypeadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView rlv;
    MyLayoutManager layoutManager = new MyLayoutManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlv = (RecyclerView) findViewById(R.id.rlv_activity_main);
        rlv.setLayoutManager(layoutManager);
        CommonAdapter adapter= new CommonAdapter<String>(this, String.class, R.layout.item_card) {
            int[] COLORS = {0xff00FFFF, 0xffDEB887, 0xff5F9EA0,
                    0xff7FFF00, 0xff6495ED, 0xffDC143C,
                    0xff008B8B, 0xff006400, 0xff2F4F4F,
                    0xffFF69B4, 0xffFF00FF, 0xffCD5C5C,
                    0xff90EE90, 0xff87CEFA, 0xff800000};

            int randomColor() {
                return COLORS[new Random().nextInt(COLORS.length)];
            }

            @Override
            protected void convert(ViewHolder holder, final String o, int i) {
                holder.setText(R.id.text, o);
                CardItemView cardItemView = holder.getView(R.id.item);
                cardItemView.setCardColor(randomColor());
                cardItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), o, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        rlv.setAdapter(adapter);
        List<String> list=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("item_"+i);
        }
        adapter.setItems(list);
    }
}
