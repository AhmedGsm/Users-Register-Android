package com.android.example.personal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegistrationInterface {
    @GET("insert.php")
    Call<Person> insertPerson(
            @Field("name")String name,
            @Field("email")String email,
            @Field("password")String password,
            @Field("age")String age,
            @Field("occupation")String occupation
    );
}
