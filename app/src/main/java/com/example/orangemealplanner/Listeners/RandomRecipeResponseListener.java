package com.example.orangemealplanner.Listeners;

import com.example.orangemealplanner.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
