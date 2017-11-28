package com.gve.testapplication.ListOfRepoFeature.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gve.testapplication.ListOfRepoFeature.data.Repository;
import com.gve.testapplication.R;
import com.gve.testapplication.core.injection.qualifiers.ForActivity;
import com.gve.testapplication.core.recyclerview.DisplayableItem;
import com.gve.testapplication.core.recyclerview.ViewHolderBinder;
import com.gve.testapplication.core.recyclerview.ViewHolderFactory;

import javax.inject.Inject;

public class RepositoryViewHolder extends RecyclerView.ViewHolder {

    private TextView repoNameTV;
    private TextView repoDescriptionTv;
    private TextView repoOwnerLoginTv;

    public RepositoryViewHolder(final View itemView) {
        super(itemView);
        repoNameTV = itemView.findViewById(R.id.repo_row_name);
        repoDescriptionTv = itemView.findViewById(R.id.repo_row_description);
        repoOwnerLoginTv = itemView.findViewById(R.id.repo_row_login_owner);
    }

    private void bind(@NonNull final Repository repository) {
        repoNameTV.setText(repoNameTV.getResources().getString(R.string.repo_name,
                repository.getName()));
        repoDescriptionTv.setText(repoDescriptionTv.getResources()
                .getString(R.string.repo_ddescritpion, repository.getDescription()));
        repoOwnerLoginTv.setText(repoOwnerLoginTv.getResources()
                .getString(R.string.repo_owner_login, repository.getLoginOwner()));

    }

    public static class RepoCardHolderFactory extends ViewHolderFactory {

        @Inject
        RepoCardHolderFactory(@NonNull @ForActivity final Context context) {
            super(context);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder createViewHolder(@NonNull final ViewGroup parent) {
            return new RepositoryViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.repo_recycler_row, parent, false));
        }
    }

    public static class RepoCardHolderBinder implements ViewHolderBinder {

        @Inject
        RepoCardHolderBinder() { }

        @Override
        public void bind(@NonNull final RecyclerView.ViewHolder viewHolder,
                         @NonNull final DisplayableItem item) {
            RepositoryViewHolder castedViewHolder = RepositoryViewHolder.class.cast(viewHolder);
            Repository viewEntity = Repository.class.cast(item.model());
            castedViewHolder.bind(viewEntity);
        }
    }
}
