package com.luvizatnika1501512.utsmobprog.myojs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.luvizatnika1501512.utsmobprog.myojs.service.CustomVolleyRequest;
import com.luvizatnika1501512.utsmobprog.myojs.view.Deskripsi;
import com.luvizatnika1501512.utsmobprog.myojs.model.Paper;
import com.luvizatnika1501512.utsmobprog.myojs.R;

import java.util.List;

public class AdapterPaper extends RecyclerView.Adapter<AdapterPaper.HolderData> {

    private ImageLoader imageLoader;
    private LayoutInflater mInflater;
    private List<Paper> mItems ;
    private Context context;
    private int position;
    private static final int SIZE = 20;

    public AdapterPaper (Context context, List<Paper> items) {
        this.context = context;
        this.mItems = items;
    }



    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
        return new HolderData(layout);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holder, final int position) {
        final Paper p = mItems.get(position);


        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(p.getFoto(), ImageLoader.getImageListener(holder.icons, R.drawable.ic_book, R.drawable.ic_book));

        holder.tvJudul.setText(p.getJudul());
        holder.tvAuthor.setText(p.getAuthor());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetail = new Intent(view.getContext(), Deskripsi.class);
                goDetail.putExtra("JUDUL", mItems.get(position).getJudul());
                goDetail.putExtra("AUTHOR", mItems.get(position).getAuthor());
                goDetail.putExtra("FOTO", mItems.get(position).getFoto());
                goDetail.putExtra("TAHUN_RILIS", mItems.get(position).getTahun());
                goDetail.putExtra("JUMLAH_HALAMAN", mItems.get(position).getJumlah());
                goDetail.putExtra("BAHASA", mItems.get(position).getBahasa());
                goDetail.putExtra("DESKRIPSI", mItems.get(position).getDeskripsi());
                view.getContext().startActivity(goDetail);
            }
        });

        holder.p = p;

    }


    class HolderData extends RecyclerView.ViewHolder{
        ImageView icons;
        TextView tvJudul, tvAuthor;
        Paper p;
        CardView card;

        HolderData(@NonNull View itemView) {
            super(itemView);
            icons = itemView .findViewById(R.id.icon);
            tvJudul = itemView .findViewById(R.id.judul);
            tvAuthor = itemView .findViewById(R.id.author);
            card = itemView.findViewById(R.id.card_view);
        }
    }
}
