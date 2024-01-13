package dev.mobile.zenithhouseapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.feedViewHolder>
{

    List<feeds> listefeed;
    Context context;

    public FeedAdapter(Context context,List<feeds> listefeed)
    {
        this.listefeed = listefeed;
        this.context = context;
    }


    @NonNull
    @Override
    public feedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater. from (parent.getContext()).inflate(R.layout.rcvitem, parent, false);
        feedViewHolder feedViewHolder = new feedViewHolder(view);
        return feedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.feedViewHolder holder, @SuppressLint("RecyclerView") int position)
    {

        feeds  fed = listefeed.get (position);
        holder.id.setText (String.valueOf(fed.getId()));
        holder.name.setText (fed.getName());
        holder.phone.setText (fed.getNumber());
        holder.feed.setText (fed.getFeed());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, listefeed.get(position).getName()+" "+listefeed.get(position).getId(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.listefeed.size();
    }

    public class feedViewHolder extends RecyclerView.ViewHolder
    {
        TextView id, name, phone, feed;
        public feedViewHolder (View itemView)
        {
            super (itemView);
            id = itemView.findViewById(R.id.id);
            name =itemView.findViewById(R.id.txtnom);
            phone = itemView.findViewById(R.id.txtpho);
            feed = itemView.findViewById(R.id.txtfeed);
        }
    }
}