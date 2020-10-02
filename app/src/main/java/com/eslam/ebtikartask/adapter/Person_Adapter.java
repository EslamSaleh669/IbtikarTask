package com.eslam.ebtikartask.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


 import com.eslam.ebtikartask.Pojo_Model.ResultsItem;
import com.eslam.ebtikartask.R;
import com.makeramen.roundedimageview.RoundedImageView;
 import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Person_Adapter extends RecyclerView.Adapter<Person_Adapter.VHolder> {


    private List<ResultsItem> data;
    final String Img_Domain = "https://image.tmdb.org/t/p/original/" ;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Title = "title";
    public static final String overView = "overview";
    public static final String Date = "date";
    public static final String Vote_Count = "vcount";
    public static final String VoteAvg = "vavg";
    public static final String Language = "language";
    public static final String Image = "img";

    SharedPreferences sharedpreferences;
    Context context ;
    private Person_Adapter.ClickListener clickListener;


    @Inject
    public Person_Adapter(Person_Adapter.ClickListener clickListener , Context context1) {
        this.clickListener = clickListener;
        this.context = context1 ;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_row,parent,false);
       return new VHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, final int position) {
        holder.actorName.setText(data.get(position).getName());
        holder.actordepartment.setText(data.get(position).getKnownForDepartment());
        holder.popularity.setText(String.valueOf(data.get(position).getPopularity()));
        final String img = data.get(position).getProfilePath().substring(1,data.get(position).getProfilePath().length()) ;

        Picasso.with(context)
                .load(Img_Domain+img)
                .resize(150, 200)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.actorimage);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.launchIntent(position);

                sharedpreferences = context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Title, data.get(position).getKnownFor().get(0).getTitle());
                editor.putString(overView,data.get(position).getKnownFor().get(0).getOverview());
                editor.putString(Date, data.get(position).getKnownFor().get(0).getReleaseDate());
                editor.putString(Vote_Count, String.valueOf(data.get(position).getKnownFor().get(0).getVoteCount()));
                editor.putString(VoteAvg, String.valueOf(data.get(position).getKnownFor().get(0).getVoteAverage()));
                editor.putString(Language, data.get(position).getKnownFor().get(0).getOriginalLanguage());
                String movie_img = data.get(position).getKnownFor().get(0)
                        .getBackdropPath().substring(1,data.get(position).getKnownFor().get(0).getBackdropPath().length()) ;
                editor.putString(Image,Img_Domain+movie_img);
                editor.apply();


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{

        private TextView actorName;
        private TextView actordepartment;
        private TextView popularity;
        RoundedImageView actorimage ;
        private LinearLayout linearLayout;

        public VHolder(View itemView) {
            super(itemView);


            actorName = itemView.findViewById(R.id.actorName);
            actordepartment = itemView.findViewById(R.id.actordepartment);
            popularity = itemView.findViewById(R.id.popularity);
            actorimage = itemView.findViewById(R.id.actorimage);
            linearLayout = itemView.findViewById(R.id.linear);


        }
    }

    public interface ClickListener {
        void launchIntent(int pos);
    }

    public void setData(List<ResultsItem> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
