package br.com.cpg.moviesproject.view.viewholder;

import android.view.View;
import android.widget.TextView;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.DetailsInterface;
import br.com.cpg.moviesproject.model.bean.HeaderBean;

public class HeaderViewHolder extends DetailsBaseViewHolder {
    private final TextView mTitle;

    public HeaderViewHolder(View itemView) {
        super(itemView);

        mTitle = itemView.findViewById(R.id.tv_header_title);
    }

    public void bind(DetailsInterface detailsBean) {
        HeaderBean headerBean = (HeaderBean) detailsBean;
        mTitle.setText(headerBean.getTitle());
    }
}
