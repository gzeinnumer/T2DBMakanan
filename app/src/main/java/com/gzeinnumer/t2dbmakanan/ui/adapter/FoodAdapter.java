package com.gzeinnumer.t2dbmakanan.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzeinnumer.t2dbmakanan.BuildConfig;
import com.gzeinnumer.t2dbmakanan.R;
import com.gzeinnumer.t2dbmakanan.model.DataMakananItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//todo 26. mebuat class Adapter
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private onItemClick click;

    Context context;
    List<DataMakananItem> dataMakananItems;

    public FoodAdapter(Context context, List<DataMakananItem> dataMakananItems) {
        this.context = context;
        this.dataMakananItems = dataMakananItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //todo 29. isi data
        viewHolder.itemDesk.setText(dataMakananItems.get(i).getMakanan());
        viewHolder.itemTime.setText(dataMakananItems.get(i).getInsertTime());
        Picasso.with(context)
                .load(BuildConfig.BASE_URL +"uploads/"+dataMakananItems.get(i).getFotoMakanan())
                .into(viewHolder.itemImages);
        //todo 51. set onclick
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        //todo 28.
        if (dataMakananItems==null) return 0;
        return dataMakananItems.size();
    }

    //todo 52. hasil dari todo 51.
    public interface onItemClick {
        void onItemClick(int position);
    }

    public void setOnClickListener(onItemClick onClick) {
        click = onClick;
    }
    //todo 27. ButteKnife , pada item_food , lalu lencang cread xml, dan pindahkan ke class ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_images)
        ImageView itemImages;
        @BindView(R.id.item_desk)
        TextView itemDesk;
        @BindView(R.id.item_time)
        TextView itemTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
