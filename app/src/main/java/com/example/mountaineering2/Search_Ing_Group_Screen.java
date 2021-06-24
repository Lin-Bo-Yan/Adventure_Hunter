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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

    public class Search_Ing_Group_Screen extends AppCompatActivity {

         RecyclerView recyclerView;
         MyAdapter myAdapter;
        private ArrayList<String> moviesList;
        private ArrayList<String> moviesList2;
        public ArrayList<String> images;
        String myResponse;
        ArrayList<Integer> groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ing_group_screen);

        //抓取String.xml裡的資料

        moviesList = new ArrayList<>();
        moviesList2 = new ArrayList<>();
        images = new ArrayList<>();
        groupId = new ArrayList<>();
        recyclerView = findViewById(R.id.groupIngSearch_Ing_Group);

        reciveIngGroup();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

        private void reciveIngGroup() {
            SharedPreferences sp =getSharedPreferences("MyUser", MODE_PRIVATE);
            String url1 = sp.getString("url",null);
            String urlId =sp.getString("ID", "42");
            OkHttpClient client = new OkHttpClient();
            String url = url1+"/api/groups/ing/"+urlId;

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        myResponse = response.body().string();

                        Log.v("joe", "Json" + myResponse);

                        Search_Ing_Group_Screen.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                parseJson(myResponse);
                            }
                        });
                    }
                }
            });


        }

        private void parseJson(String myResponse) {

            try {
                JSONArray jsonArray = new JSONArray(myResponse);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Integer id = jsonObject.getInt("id");
                    String creator_id = jsonObject.getString("creator_id");
                    String name = jsonObject.getString("name");
                    String slug = jsonObject.getString("slug");
                    String description = jsonObject.getString("description");
                    String status = jsonObject.getString("status");
                    String parent_id = jsonObject.getString("parent_id");
                    String enable_forum = jsonObject.getString("enable_forum");
                    String date_created = jsonObject.getString("date_created");
                    String start_date = jsonObject.getString("start_date");
                    String mountain_name = jsonObject.getString("mountain_name");
                    String total_num = jsonObject.getString("total_num");
                    String image = jsonObject.getString("image");
                    String attendee = jsonObject.getString("attendee");
                    String points = jsonObject.getString("points");
                    String start_time = jsonObject.getString("start_time");
                    String finish_time = jsonObject.getString("finish_time");
                    String total_time = jsonObject.getString("total_time");
                    String start_lat = jsonObject.getString("start_lat");
                    String start_lng = jsonObject.getString("start_lng");
                    String finish_lat = jsonObject.getString("finish_lat");
                    String finish_lng = jsonObject.getString("finish_lng");

                    moviesList.add(name);
                    moviesList2.add(description);
                    images.add(image);
                    groupId.add(id);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v("joe","images== "+images);
            myAdapter = new MyAdapter(this, moviesList, moviesList2, images);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(myAdapter);

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
        public class MyAdapter extends RecyclerView.Adapter<Search_Ing_Group_Screen.MyAdapter.MyViewHolder> implements Filterable {

            private static final String TAG = "MyAdapter";
            Context context;
            ArrayList<String> moviesList;
            ArrayList<String> moviesList2;
            ArrayList<String> moviesListAll;
            ArrayList<String> images;



            private  MyAdapter(Context context, ArrayList<String> moviesList, ArrayList<String> moviesList2, ArrayList<String> images) {
                this.context = context;
                this.moviesList = moviesList;
                this.moviesList2=moviesList2;
                this.images = images;
                moviesListAll = new ArrayList<>();
                moviesListAll.addAll(moviesList);
            }

            @NonNull
            @Override
            public Search_Ing_Group_Screen.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                //create出 my_row 版面
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.row_search_ing_group_screen, parent, false);
                Search_Ing_Group_Screen.MyAdapter.MyViewHolder vh = new Search_Ing_Group_Screen.MyAdapter.MyViewHolder(view);

                return vh;
            }

            @Override
            public void onBindViewHolder(@NonNull Search_Ing_Group_Screen.MyAdapter.MyViewHolder holder, int position) {

                //產生資料
                holder.myText1.setText(moviesList.get(position));
                holder.myText2.setText(moviesList2.get(position));

                Picasso.get().load(images.get(position)).into(holder.myImage);


                //觸發mainLayout listener
                holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Search_Ing_Group_Screen_Page2.class);
                        intent.putExtra("data1Page4", moviesList.get(position));
                        intent.putExtra("data2Page4", moviesList2.get(position));
                        intent.putExtra("myImagePage4", images.get(position));
                        intent.putExtra("groupId", groupId.get(position));
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

            class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

                TextView myText1, myText2;
                ImageView myImage;
                ConstraintLayout mainLayout;

                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);

                    //抓取id
                    myText1 = itemView.findViewById(R.id.myText1Page4);
                    myText2 = itemView.findViewById(R.id.myText2Page4);
                    myImage = itemView.findViewById(R.id.myImageViewPage4);
                    mainLayout = itemView.findViewById(R.id.mainLayoutPage4);

                    itemView.setOnClickListener(this);
                }

                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), moviesList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }