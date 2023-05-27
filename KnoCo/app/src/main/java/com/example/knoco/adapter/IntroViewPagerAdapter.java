package com.example.knoco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.knoco.R;
import com.example.knoco.model.IntroData;

import java.util.List;

public class IntroViewPagerAdapter extends RecyclerView.Adapter<IntroViewPagerAdapter.IntroViewPagerHolder> {

    private List<IntroData> l ;

    public IntroViewPagerAdapter(List<IntroData> l) {
        this.l = l;
    }

    @NonNull
    @Override
    public IntroViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IntroViewPagerHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_intro,parent,false
                )

        );
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewPagerHolder holder, int position) {

        holder.SetData(l.get(position));
    }

    @Override
    public int getItemCount() {
        return l.size();
    }

    class IntroViewPagerHolder extends RecyclerView.ViewHolder{
      private TextView Title ;
      private TextView des ;
      private ConstraintLayout cl ;

      IntroViewPagerHolder(@NonNull View itemView){
          super(itemView);
          Title = itemView.findViewById(R.id.title);
          des = itemView.findViewById(R.id.description);
          cl = (ConstraintLayout) itemView.findViewById(R.id.item);


      }
      void SetData(IntroData i){
          Title.setText(i.getTitle());
          des.setText(i.getDescription());
          cl.setBackgroundResource(i.getScreenImg());



      }


  }
}
