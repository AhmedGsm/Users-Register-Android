package com.android.example.personal;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegistrationInterface {
    @FormUrlEncoded
    @POST("insert.php")
    Call<Person> insertPerson(
            @Field("name")String name,
            @Field("email")String email,
            @Field("password")String password,
            @Field("age")String age,
            @Field("occupation")String occupation
    );
}
