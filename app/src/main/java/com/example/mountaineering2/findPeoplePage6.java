package com.example.mountaineering2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class findPeoplePage6 extends AppCompatActivity {
    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] ={R.drawable.yushan,R.drawable.jalishan,R.drawable.guguan,R.drawable.huhwanshan,
            R.drawable.baydawushan,R.drawable.namguashan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_people_page6);

        //抓取String.xml裡的資料
        s1 = getResources().getStringArray(R.array.groupHistoryPage6);
        s2 = getResources().getStringArray(R.array.descriptionPage6);


        recyclerView = findViewById(R.id.groupHistoryPage6);


        findPeoplePage6.MyAdapter myAdapter = new findPeoplePage6.MyAdapter(this, s1, s2, images);
        Log.v("joe", "new myAdapterPage6");

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public class MyAdapter extends RecyclerView.Adapter<findPeoplePage6.MyAdapter.MyViewHolder> {

        private String data1[], data2[];
        private int images[];
        private Context context;

        public MyAdapter(Context ct, String s1[], String s2[], int img[]) {
            context = ct;
            data1 = s1;
            data2 = s2;
            images = img;
        }

        @NonNull
        @Override
        public findPeoplePage6.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //create出 my_row 版面
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_rowpage6, parent, false);
            findPeoplePage6.MyAdapter.MyViewHolder vh = new findPeoplePage6.MyAdapter.MyViewHolder(view);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull findPeoplePage6.MyAdapter.MyViewHolder holder, int position) {

            //產生資料
            holder.myText1.setText(data1[position]);
            holder.myText2.setText(data2[position]);
            holder.myImage.setImageResource(images[position]);


            //觸發mainLayout listener
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, findPeoplePage7.class);
                    intent.putExtra("data1Page6", data1[position]);
                    intent.putExtra("data2Page6", data2[position]);
                    intent.putExtra("myImagePage6", images[position]);
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
                myText1 = itemView.findViewById(R.id.myText1Page6);
                myText2 = itemView.findViewById(R.id.myText2Page6);
                myImage = itemView.findViewById(R.id.myImageViewPage6);
                mainLayout = itemView.findViewById(R.id.mainLayoutPage6);
            }
        }
    }
}