package com.eslam.ebtikartask.retrofit;


import com.eslam.ebtikartask.Pojo_Model.Person;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {


    @GET("popular/?")
    Observable<Person> getPeople(@Query("api_key") String format , @Query("page") String page) ;


}
