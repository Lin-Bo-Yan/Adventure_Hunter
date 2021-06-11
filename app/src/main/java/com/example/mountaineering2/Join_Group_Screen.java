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

public class Join_Group_Screen extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    ArrayList<String> moviesList;
    ArrayList<String> moviesList2;

    private int images[] = {R.drawable.yushan, R.drawable.jalishan, R.drawable.guguan, R.drawable.huhwanshan,
            R.drawable.baydawushan, R.drawable.namguashan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_screen);

        moviesList = new ArrayList<>();
        moviesList2 = new ArrayList<>();
        moviesList.add("合歡群峰");
        moviesList.add("北大武山");
        moviesList.add("玉山");
        moviesList.add("谷關七雄");
        moviesList.add("合歡群峰");
        moviesList.add("北大武山");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。"+"\n可獲得之點數= 100 點");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。"+"\n可獲得之點數= 200 點");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。"+"\n可獲得之點數= 300 點");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。"+"\n可獲得之點數= 400 點");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。"+"\n可獲得之點數= 300 點");
        moviesList2.add("標高 3742 公尺，中央山脈第三高峰，山姿雄偉，為台灣「五嶽」之一。"+"\n可獲得之點數= 100 點");



        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(this, moviesList, moviesList2, images);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

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
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {


        private static final String TAG = "RecyclerAdapter";
        Context context;
        ArrayList<String> moviesList;
        ArrayList<String> moviesList2;
        ArrayList<String> moviesListAll;
        int images[];

        public RecyclerAdapter(Context context, ArrayList<String> moviesList, ArrayList<String> moviesList2, int images[]) {
            this.context = context;
            this.moviesList = moviesList;
            this.moviesList2=moviesList2;
            this.images = images;
            moviesListAll = new ArrayList<>();
            moviesListAll.addAll(moviesList);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.row_join_group_screen, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.rowCountTextView.setText(moviesList2.get(position));
            holder.textView.setText(moviesList.get(position));
            holder.imageView.setImageResource(images[position]);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Join_Group_Screen_Page2.class);
                    intent.putExtra("data1", moviesList.get(position));
                    intent.putExtra("data2", moviesList2.get(position));
                    intent.putExtra("myImage", images[position]);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        @Override
        public Filter getFilter() {

            return myFilter;
        }

        Filter myFilter = new Filter() {

            //Automatic on background thread
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

            //Automatic on UI thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                moviesList.clear();
                moviesList.addAll((Collection<? extends String>) filterResults.values);
                notifyDataSetChanged();
            }
        };



        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView imageView;
            TextView textView, rowCountTextView;
            ConstraintLayout mainLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
                rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
                mainLayout = itemView.findViewById(R.id.mainLayout2);

                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), moviesList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}