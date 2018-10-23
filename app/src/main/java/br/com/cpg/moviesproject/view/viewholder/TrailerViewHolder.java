package br.com.cpg.moviesproject.view.viewholder;

import android.view.View;
import android.widget.TextView;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.DetailsInterface;
import br.com.cpg.moviesproject.model.bean.TrailerBean;

public class TrailerViewHolder extends DetailsBaseViewHolder {
    private TextView mTrailerName;

    public TrailerViewHolder(View itemView, final TrailerClickHandler clickHandler) {
        super(itemView);

        mTrailerName = itemView.findViewById(R.id.tv_trailer_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickHandler != null) {
                    clickHandler.onClick(getAdapterPosition());
                }
            }
        });
    }

    public void bind(DetailsInterface detailsBean) {
        TrailerBean trailerBean = (TrailerBean) detailsBean;

        mTrailerName.setText(trailerBean.getName());
    }

    public interface TrailerClickHandler {
        void onClick(int position);
    }
}
