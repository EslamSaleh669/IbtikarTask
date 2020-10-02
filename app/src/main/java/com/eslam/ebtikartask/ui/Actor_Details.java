package com.eslam.ebtikartask.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eslam.ebtikartask.di.service.DownloadService;
import com.eslam.ebtikartask.R;
import com.squareup.picasso.Picasso;


public class Actor_Details extends AppCompatActivity {



    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Title = "title";
    public static final String overView = "overview";
    public static final String Date = "date";
    public static final String Vote_Count = "vcount";
    public static final String VoteAvg = "vavg";
    public static final String Language = "language";
    public static final String Image = "img";
    SharedPreferences sharedpreferences;

    Button button;
    public final Context context = this;
    ProgressDialog progressDialog ;


    TextView MyTitle , Overview , ReleasedDate , V_Count , V_Avg , Lang ;
    ImageView ActorImg  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor__details);

        init();
}



    void init (){

        progressDialog = new ProgressDialog(Actor_Details.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        button = findViewById(R.id.download);
        MyTitle = findViewById(R.id.dtitle);
        Overview = findViewById(R.id.doverview);
        ReleasedDate = findViewById(R.id.ddate);
        V_Count = findViewById(R.id.dvote_count);
        V_Avg = findViewById(R.id.dvote_avg);
        Lang = findViewById(R.id.dlanguage);
        ActorImg = findViewById(R.id.dimage);



        MyTitle.setText( sharedpreferences.getString(Title,""));
        Overview.setText( sharedpreferences.getString(overView,""));
        ReleasedDate.setText( sharedpreferences.getString(Date,""));
        V_Count.setText( sharedpreferences.getString(Vote_Count,""));
        V_Avg.setText( sharedpreferences.getString(VoteAvg,""));
        Lang.setText( sharedpreferences.getString(Language,""));

        Picasso.with(this)
                .load(sharedpreferences.getString(Image,""))
                .resize(250, 200)
                .centerCrop()
                .into(ActorImg);



            button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                take_Permission();


                Intent intent = new Intent(context, DownloadService.class);
                    intent.putExtra("url", sharedpreferences.getString(Image,""));
                    intent.putExtra("receiver", new DownloadReceiver(new Handler()));
                    startService(intent);

            }
        });
    }

    void take_Permission(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


            }
        }
    }

    private class DownloadReceiver extends ResultReceiver {

        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            super.onReceiveResult(resultCode, resultData);

            if (resultCode == DownloadService.UPDATE_PROGRESS) {

                int progress = resultData.getInt("progress");
                progressDialog.setProgress(progress);
                progressDialog.setMessage("Images Is Downloading");
                progressDialog.show();

                if (progress == 100) {

                    progressDialog.dismiss();

                }
            }
        }
    }


}




