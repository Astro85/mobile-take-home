package com.example.myapplication.episode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
    private List<Episode> episodes;
    private OnItemClickedListener onItemClickedListener;

    public EpisodeAdapter(OnItemClickedListener onItemClickedListener, List<Episode> episodes) {
        this.onItemClickedListener = onItemClickedListener;
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(episodes.get(position), onItemClickedListener);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public void addEpisodes(List<Episode> moreEpisodes){
        this.episodes.addAll(moreEpisodes);
        notifyDataSetChanged();
    }

    public interface OnItemClickedListener {
        void onItemClicked(Episode episode);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView episodeNumber;
        private final LinearLayout cell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            title = itemView.findViewById(R.id.episode_title_text_field);
            episodeNumber = itemView.findViewById(R.id.episode_text_field);
            cell = itemView.findViewById(R.id.episode_list_item_cell);
        }

        public void bind(final Episode episode, final OnItemClickedListener listener) {
            episodeNumber.setText(episode.episode);
            title.setText(episode.name);
            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(episode);
                }
            });
        }
    }
}
