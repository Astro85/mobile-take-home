package com.example.myapplication.character;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private List<Character> characters;
    private OnItemClickedListener onItemClickedListener;

    public CharacterAdapter(OnItemClickedListener onItemClickedListener, List<Character> characters) {
        this.onItemClickedListener = onItemClickedListener;
        this.characters = characters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(characters.get(position), onItemClickedListener);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public interface OnItemClickedListener {
        void onItemClicked(Character character);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView status;
        private final LinearLayout cell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.character_name_text_field);
            status = itemView.findViewById(R.id.character_status_text_field);
            cell = itemView.findViewById(R.id.character_list_item_cell);
        }

        public void bind(final Character character, final OnItemClickedListener listener) {
            name.setText(character.name);
            status.setText(character.status);
            if(character.status.equalsIgnoreCase("alive")) {
                //FIXME: move to resource file
                cell.setBackgroundColor(0xff7bd971);
            } else if (character.status.equalsIgnoreCase("dead")) {
                cell.setBackgroundColor(0xffcc5a50);
            } else {
                cell.setBackgroundColor(0xffeef07f);
            }
            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(character);
                }
            });
        }
    }
}
