package com.eslam.ebtikartask.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.eslam.ebtikartask.MyApplication;
import com.eslam.ebtikartask.Pojo_Model.Person;
import com.eslam.ebtikartask.Pojo_Model.ResultsItem;
import com.eslam.ebtikartask.R;
import com.eslam.ebtikartask.adapter.Person_Adapter;
import com.eslam.ebtikartask.di.component.ApplicationComponent;
import com.eslam.ebtikartask.di.component.DaggerMainActivityComponent;
import com.eslam.ebtikartask.di.component.MainActivityComponent;
import com.eslam.ebtikartask.di.module.MainActivityContextModule;
import com.eslam.ebtikartask.di.qualifier.ActivityContext;
import com.eslam.ebtikartask.di.qualifier.ApplicationContext;

import com.eslam.ebtikartask.retrofit.APIInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;



import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Person_Adapter.ClickListener {

    private RecyclerView recyclerView;
    MainActivityComponent mainActivityComponent;
    boolean isLoading = false;
    public static int size = 0 ;
    public static int pagenum = 1;
    List<ResultsItem> MyData = new ArrayList<>()  ;


    @Inject
    public Person_Adapter person_adapter;

    @Inject
    public APIInterface apiInterface;

    @Inject
    @ApplicationContext
    public Context mContext;

    @Inject
    @ActivityContext
    public Context activityContext;


    ProgressDialog progressDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();

        mainActivityComponent.injectMainActivity(this);
        initScrollListener();

    }

    void Load_Data (){

        progressDialog =  new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Observable<Person> observable =  apiInterface.getPeople("f6000212a2d0e53e9b3180c0289c63d8",String.valueOf(pagenum))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observer<Person> observer = new Observer<Person>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Person person) {
                MyData = person.getResults();
                progressDialog.dismiss();
                populateRecyclerView();
            }

            @Override
            public void onError(@NonNull Throwable e) {

                Log.d("OnError",e.getMessage());
                progressDialog.dismiss();

            }

            @Override
            public void onComplete() {


            }
        };

        observable.subscribe(observer);
        pagenum = pagenum+1 ;


    }

    private void initScrollListener() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        Load_Data();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerViewe, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == size) {

                        Load_Data();
                        isLoading = true;
                    }
                }
            }
        });

    }



    private void populateRecyclerView() {
        person_adapter.setData(MyData);
        size = MyData.size()-1 ;
        recyclerView.setAdapter(person_adapter);

    }

    @Override
    public void launchIntent(int pos) {
        startActivity(new Intent(activityContext, Actor_Details.class));
    }

}
