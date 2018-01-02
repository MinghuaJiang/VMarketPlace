package edu.virginia.cs.vmarketplace.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 12/29/17.
 */
public class FootViewHolder extends RecyclerView.ViewHolder{
    public ProgressBar progressBar;
    public FootViewHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }
}
