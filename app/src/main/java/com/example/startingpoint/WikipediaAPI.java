package com.example.startingpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WikipediaAPI {
    @GET("page/summary/{title}")
    Call<WikiSummary> getSummary(@Path("title") String title);

    @GET("page/related/{title}")
    Call<RelatedPages> getRelatedPages(@Path("title") String title);

}
