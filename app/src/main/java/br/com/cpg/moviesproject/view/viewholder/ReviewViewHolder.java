package br.com.cpg.moviesproject.view.viewholder;

import android.view.View;
import android.widget.TextView;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.DetailsInterface;
import br.com.cpg.moviesproject.model.bean.ReviewBean;

public class ReviewViewHolder extends DetailsBaseViewHolder {
    private final TextView mReviewAuthor;
    private final TextView mReviewText;

    public ReviewViewHolder(View itemView) {
        super(itemView);

        mReviewAuthor = itemView.findViewById(R.id.tv_review_author);
        mReviewText = itemView.findViewById(R.id.tv_review_text);

    }

    public void bind(DetailsInterface detailsBean) {
        ReviewBean reviewBean = (ReviewBean) detailsBean;

        mReviewAuthor.setText(reviewBean.getAuthor());
        mReviewText.setText(reviewBean.getContent());
    }
}
