package br.com.cpg.moviesproject.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.cpg.moviesproject.model.bean.DetailsInterface;

public abstract class DetailsBaseViewHolder extends RecyclerView.ViewHolder {

    DetailsBaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(DetailsInterface detailsBean);
}
