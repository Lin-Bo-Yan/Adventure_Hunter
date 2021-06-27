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
import android.os.Handler;
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

public class Join_Group_Screen extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    String myResponse;

    public ArrayList<String> moviesList, moviesList2, images, dateList, groupList, peopleList, desList;
    ArrayList<Integer> groupId;


    LoadingDialog loadingDialog = new LoadingDialog(Join_Group_Screen.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_screen);
        recyclerView = findViewById(R.id.recyclerView);


        dateList = new ArrayList<>();
        groupList = new ArrayList<>();
        moviesList = new ArrayList<>();
        moviesList2 = new ArrayList<>();
        images = new ArrayList<>();
        groupId = new ArrayList<>();
        peopleList = new ArrayList<>();
        desList = new ArrayList<>();
        reciveGroupInfo();

        loding();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void loding() {
        loadingDialog.startLoadingDiolog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        },3000);
    }

    public void reciveGroupInfo() {
        SharedPreferences sp =getSharedPreferences("MyUser", MODE_PRIVATE);
        String url1 = sp.getString("url",null);
        String urlId =sp.getString("ID", "42");
        OkHttpClient client = new OkHttpClient();
        String url = url1+"/api/groups/join/"+urlId;

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

                    Join_Group_Screen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parseJson(myResponse);
                        }
                    });
                }
            }
        });

    }

    public void parseJson(String myResponse) {

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

                moviesList.add(mountain_name);
                moviesList2.add(points);
                images.add(image);
                dateList.add(start_date);
                groupList.add(name);
                peopleList.add(total_num);
                desList.add(description);
                groupId.add(id);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("joe","images== "+images);

        recyclerAdapter = new RecyclerAdapter(this, moviesList, moviesList2, images, dateList, groupList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

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
        ArrayList<String> moviesList, moviesList2, moviesListAll, images, dateList, groupList;

        public RecyclerAdapter(Context context, ArrayList<String> moviesList, ArrayList<String> moviesList2, ArrayList<String> images, ArrayList<String> dateList, ArrayList<String> groupList) {
            this.context = context;
            this.moviesList = moviesList;
            this.moviesList2 = moviesList2;
            this.dateList = dateList;
            this.groupList = groupList;
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
            holder.dateText.setText(dateList.get(position));
            holder.groupText.setText(groupList.get(position));


            Picasso.get().load(images.get(position)).into(holder.imageView) ;


            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Join_Group_Screen_Page2.class);
                    intent.putExtra("mountain", moviesList.get(position));
                    intent.putExtra("point", moviesList2.get(position));
                    intent.putExtra("myImage", images.get(position));
                    intent.putExtra("date", dateList.get(position));
                    intent.putExtra("groupName", groupList.get(position));
                    intent.putExtra("people", peopleList.get(position));
                    intent.putExtra("des", desList.get(position));


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

            //Automatic on background thread
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                ArrayList<String> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(moviesListAll);
                } else {
                    for (String movie : moviesListAll) {
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
            TextView textView, rowCountTextView, dateText, groupText;
            ConstraintLayout mainLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
                rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
                mainLayout = itemView.findViewById(R.id.mainLayout2);
                dateText = itemView.findViewById(R.id.textView25);
                groupText = itemView.findViewById(R.id.textView26);

                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), moviesList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}