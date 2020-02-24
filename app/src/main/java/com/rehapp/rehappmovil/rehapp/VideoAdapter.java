package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<ExerciseRoutinesViewModel> exercises;
    Activity activity;
    FragmentManager fragment;
    LayoutInflater inflater;
    OnVideoClickListener onVideoClickListener;

    public VideoAdapter() { }

    public VideoAdapter(Activity activity, List<ExerciseRoutinesViewModel> exercises, FragmentManager fragment,OnVideoClickListener onVideoClickListener) {
        this.exercises = exercises;
        this.activity = activity;
        this.exercises = exercises;
        inflater = activity.getLayoutInflater();
        this.fragment=fragment;
        this.onVideoClickListener=onVideoClickListener;
    }


    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_view,viewGroup,false);

        return  new VideoAdapter.VideoViewHolder(view,onVideoClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder videoViewHolder, int i) {
        videoViewHolder.webView.loadData(exercises.get(i).getHtml(),"text/html","utf-8");
        videoViewHolder.tvRoutineName.setText(exercises.get(i).getExercise_routine_name());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView webView;
        ImageView ibtnDetail;
        TextView tvFullScreenVideo;
        TextView tvRoutineName;
        OnVideoClickListener onVideoClickListener;

        public VideoViewHolder(View itemView,final OnVideoClickListener onVideoClickListener){
            super(itemView);


            webView=itemView.findViewById(R.id.webView);
            ibtnDetail=itemView.findViewById(R.id.ibtnDetail);
            tvFullScreenVideo=itemView.findViewById(R.id.tvFullScreenVideo);
            tvRoutineName=itemView.findViewById(R.id.tvRoutineName);

            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);

                    webView.loadUrl("file:///android_asset/error.html");
                }
            });
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAppCacheEnabled(true);


            this.onVideoClickListener=onVideoClickListener;

            ibtnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    onVideoClickListener.onVideoClick(position,1);
                }
            });

            tvFullScreenVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    onVideoClickListener.onVideoClick(position,2);
                }
            });

        }
    }

    public interface  OnVideoClickListener{
        void onVideoClick(int position,int option);
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=this.fragment.beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
