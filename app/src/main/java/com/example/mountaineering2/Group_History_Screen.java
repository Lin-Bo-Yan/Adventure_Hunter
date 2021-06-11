package com.example.mountaineering2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class Group_History_Screen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<String> moviesList;
    private ArrayList<String> moviesList2;


    String s1[], s2[];
    int images[] = {R.drawable.yushan, R.drawable.jalishan, R.drawable.guguan, R.drawable.huhwanshan,
            R.drawable.baydawushan, R.drawable.namguashan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_history_screen);

        //抓取String.xml裡的資料
        s1 = getResources().getStringArray(R.array.groupHistoryGroup_History_Screen);
        s2 = getResources().getStringArray(R.array.descriptionGroup_History_Screen);
        moviesList = new ArrayList<>();
        moviesList2 = new ArrayList<>();
        moviesList.add("合歡群峰");
        moviesList.add("北大武山");
        moviesList.add("玉山");
        moviesList.add("谷關七雄");
        moviesList.add("合歡群峰");
        moviesList.add("北大武山");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。");


        recyclerView = findViewById(R.id.groupHistoryGroup_History_Screen);


        myAdapter = new Group_History_Screen.MyAdapter(this, moviesList, moviesList2, images);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
    public class MyAdapter extends RecyclerView.Adapter<Group_History_Screen.MyAdapter.MyViewHolder> implements Filterable {

        Context context;
        ArrayList<String> moviesList;
        ArrayList<String> moviesList2;
        ArrayList<String> moviesListAll;
        int images[];

        public MyAdapter(Context context, ArrayList<String> moviesList, ArrayList<String> moviesList2, int images[]) {
            this.context = context;
            this.moviesList = moviesList;
            this.moviesList2=moviesList2;
            this.images = images;
            moviesListAll = new ArrayList<>();
            moviesListAll.addAll(moviesList);
        }

        @Override
        public Filter getFilter() {
            return myFilter;
        }
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<String> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(moviesListAll);
                } else {
                    for (String movie: moviesListAll) {
                        if (movie.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                moviesList.clear();
                moviesList.addAll((Collection<? extends String>) filterResults.values);
                notifyDataSetChanged();
            }
        };

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), moviesList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();

            }
        }

        @NonNull
        @Override
        public Group_History_Screen.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //create出 my_row 版面
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.row_group_history_screen, parent, false);
            Group_History_Screen.MyAdapter.MyViewHolder vh = new Group_History_Screen.MyAdapter.MyViewHolder(view);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull Group_History_Screen.MyAdapter.MyViewHolder holder, int position) {

            //產生資料
            holder.myText1.setText(moviesList.get(position));
            holder.myText2.setText(moviesList2.get(position));
            holder.myImage.setImageResource(images[position]);

            //觸發mainLayout listener
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Group_History_Screen_Page2.class);
                    intent.putExtra("data1Page6", moviesList.get(position));
                    intent.putExtra("data2Page6", moviesList2.get(position));
                    intent.putExtra("myImagePage6", images[position]);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }
}