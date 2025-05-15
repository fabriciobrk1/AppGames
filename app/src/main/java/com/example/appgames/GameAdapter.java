package com.example.appgames;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<Game> gameList;
    private Context context;

    public GameAdapter(List<Game> gameList, Context context) {
        this.gameList = gameList;
        this.context = context;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.gameName.setText(game.getName());
        holder.gameImage.setImageResource(game.getImageResource());
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView gameName;
        ImageView gameImage;

        public GameViewHolder(View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.gameName);
            gameImage = itemView.findViewById(R.id.gameImage);
        }
    }
}
