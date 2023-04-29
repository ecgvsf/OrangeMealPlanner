package com.example.orangemealplanner;

import android.content.Context;

import com.example.orangemealplanner.Listeners.RandomRecipeResponseListener;
import com.example.orangemealplanner.Models.RandomRecipeApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    Context context;
    List<String> tags;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context, List<String> tags) {
        this.tags = tags;
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), tags, "1");
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public  interface  CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("tags") List<String> tags,
                @Query("number") String number
        );
    }
}
