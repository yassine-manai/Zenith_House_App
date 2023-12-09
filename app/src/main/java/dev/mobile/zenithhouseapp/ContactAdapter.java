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


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> listeContact;
    private Context context;

    public ContactAdapter(Context context, List<Contact> listeContact)
    {
        this.context = context;
        this.listeContact = listeContact;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecycleviewitemBinding binding = RecycleviewitemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        Contact contact = listeContact.get(position);
        holder.bind(contact);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation de Suppression");
                builder.setMessage("Suppression de cet contact " + listeContact.get(position).getNom() + " de cette liste");

                builder.setPositiveButton("Oui !", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ContactBDD db = new ContactBDD(context);
                        db.deleteContact(listeContact.get(position));
                        Toast.makeText(context, "Suppression du note" + position + " " + listeContact.get(position).getNumber() + " avec succès", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                        int positionStart = holder.getAdapterPosition();
                        listeContact.remove(positionStart);
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
        return listeContact.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder
    {
        private final RecycleviewitemBinding binding;

        public ContactViewHolder(RecycleviewitemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Contact contact)
        {
            binding.txtid.setText("ID: " + contact.getId());
            binding.txtvnom.setText("Sujet: " + contact.getNom());
            binding.txtnumero.setText(": " + contact.getNumber());
        }
    }
}
