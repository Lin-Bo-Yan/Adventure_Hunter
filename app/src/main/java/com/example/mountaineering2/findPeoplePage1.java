package com.example.mountaineering2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class findPeoplePage1 extends AppCompatActivity {

    private RecyclerView recyclerView;

    private String s1[], s2[];
    private int images[] = {R.drawable.yushan, R.drawable.jalishan, R.drawable.guguan, R.drawable.huhwanshan,
            R.drawable.baydawushan, R.drawable.namguashan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_people_page1);

        //抓取String.xml裡的資料
        s1 = getResources().getStringArray(R.array.noYetJoinTitle);
        s2 = getResources().getStringArray(R.array.descriptionPage1);


        recyclerView = findViewById(R.id.noYetJoinTitlePage1);


        MyAdapter myAdapter = new MyAdapter(this, s1, s2, images);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private String data1[], data2[];
        private String data1All[], data2All[];
        private int images[];
        private Context context;

        public MyAdapter(Context ct, String s1[], String s2[], int img[]) {
            context = ct;
            data1 = s1;
            data2 = s2;
            images = img;
            data1All = s1;
            data2All = s2;

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //create出 my_row 版面
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_row, parent, false);
            MyViewHolder vh = new MyViewHolder(view);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            //產生資料
            holder.myText1.setText(data1[position]);
            holder.myText2.setText(data2[position]);
            holder.myImage.setImageResource(images[position]);


            //觸發mainLayout listener
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, findPeoplePage2.class);
                    intent.putExtra("data1", data1[position]);
                    intent.putExtra("data2", data2[position]);
                    intent.putExtra("myImage", images[position]);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return images.length;
        }




        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView myText1, myText2;
            private ImageView myImage;
            private ConstraintLayout mainLayout;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                //抓取id
                myText1 = itemView.findViewById(R.id.myText1);
                myText2 = itemView.findViewById(R.id.myText2);
                myImage = itemView.findViewById(R.id.myImageView);
                mainLayout = itemView.findViewById(R.id.mainLayout);
            }
        }
    }

}