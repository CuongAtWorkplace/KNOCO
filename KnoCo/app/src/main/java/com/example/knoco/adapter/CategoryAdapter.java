package com.example.knoco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knoco.R;
import com.example.knoco.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private List<Category> mlist ;

    Context context ;
    public CategoryAdapter(Context context , List<Category> mlist) {
        this.mlist = mlist;
        this.context = context ;

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.Cate.setText(mlist.get(position).getCateName());
        holder.imgLayout.setImageResource(mlist.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgLayout ;
        private TextView Cate ;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            Cate = itemView.findViewById(R.id.CateName);
            imgLayout = (ImageView) itemView.findViewById(R.id.FrameImg);
        }
    }

}
