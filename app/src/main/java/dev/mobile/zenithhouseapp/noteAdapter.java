package dev.mobile.zenithhouseapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mobile.zenithhouseapp.databinding.RecycleviewitemBinding;


public class noteAdapter extends RecyclerView.Adapter<noteAdapter.noteViewHolder> {

    private List<note> listenote;
    private Context context;

    public noteAdapter(Context context, List<note> listenote)
    {
        this.context = context;
        this.listenote = listenote;
    }

    @NonNull
    @Override
    public noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecycleviewitemBinding binding = RecycleviewitemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new noteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final noteViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        note Notes = listenote.get(position);
        holder.bind(Notes);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation de Suppression");
                builder.setMessage("Suppression de cette Note " + listenote.get(position).getId() + " de cette liste");

                builder.setPositiveButton("Oui !", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        noteBDD db = new noteBDD(context);
                        db.deletenotes(listenote.get(position));
                        Toast.makeText(context, "Suppression du note" + position + " " + listenote.get(position).getId() + " avec succès", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                        int positionStart = holder.getAdapterPosition();
                        listenote.remove(positionStart);
                        notifyItemRemoved(positionStart);
                    }
                });

                builder.setNegativeButton("Non !", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                builder.create().show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return listenote.size();
    }

    public static class noteViewHolder extends RecyclerView.ViewHolder
    {
        private final RecycleviewitemBinding binding;

        public noteViewHolder(RecycleviewitemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(note note)
        {
            binding.txtid.setText("ID: " + note.getId());
            binding.txtvsujet.setText("Sujet: " + note.getNom());
            binding.txt.setText("Text: " + note.getText());
        }
    }
}
