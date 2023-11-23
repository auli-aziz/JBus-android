package com.auliaAnugrahAzizJBusRD.jbus_android.request;

import com.auliaAnugrahAzizJBusRD.jbus_android.model.Account;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccountbyId (@Path("id") int id);
}
