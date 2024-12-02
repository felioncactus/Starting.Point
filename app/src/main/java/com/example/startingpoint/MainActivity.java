package com.example.startingpoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "UserPreferences";
    private static final String KEY_TOPICS = "SelectedTopics";
    private static final int DELAY_BETWEEN_REQUESTS = 2000;

    private ViewPager2 viewPager;
    private FactAdapter adapter;
    private List<Fact> factList = new ArrayList<>();
    private WikipediaAPI wikipediaAPI;

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("User-Agent", "StartingPointTermProject/1.0")
                            .build();
                    return chain.proceed(request);
                }
            })
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("FirstLaunch", true);

        if (isFirstLaunch) {
            Intent intent = new Intent(this, TopicSelectionActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        viewPager = findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        adapter = new FactAdapter(factList, this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

        // Restore the saved position
        int savedPosition = prefs.getInt("CurrentPosition", 0);
        viewPager.setCurrentItem(savedPosition);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/api/rest_v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wikipediaAPI = retrofit.create(WikipediaAPI.class);

        List<String> savedTopics = getSavedTopics();
        fetchFacts(savedTopics);
    }

    private void fetchFacts(List<String> topics) {
        for (String topic : topics) {
            String formattedTopic = topic.replace(" ", "_");

            wikipediaAPI.getRelatedPages(formattedTopic).enqueue(new Callback<RelatedPages>() {
                @Override
                public void onResponse(Call<RelatedPages> call, Response<RelatedPages> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<RelatedPages.RelatedPage> relatedPages = response.body().getPages();

                        for (RelatedPages.RelatedPage page : relatedPages) {
                            String imageUrl = page.getThumbnail() != null ? page.getThumbnail().getSource() : null;

                            String displayTitle = page.getTitle().replace("_", " ");

                            Fact fact = new Fact(
                                    displayTitle,  // Display the title with spaces
                                    page.getExtract(),
                                    page.getContentUrls().getDesktop().getPage(),
                                    imageUrl
                            );

                            factList.add(fact);
                            adapter.notifyItemInserted(factList.size() - 1);
                        }
                        new Handler().postDelayed(() -> {
                            fetchFacts(topics);
                        }, DELAY_BETWEEN_REQUESTS);
                    } else {
                        Toast.makeText(MainActivity.this, "Error fetching related pages", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RelatedPages> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private List<String> getSavedTopics() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> topicSet = prefs.getStringSet(KEY_TOPICS, new HashSet<>());
        return new ArrayList<>(topicSet);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("CurrentPosition", viewPager.getCurrentItem());
        editor.apply();
    }

}
