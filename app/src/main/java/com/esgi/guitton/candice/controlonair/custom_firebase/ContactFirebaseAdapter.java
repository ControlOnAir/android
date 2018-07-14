package com.esgi.guitton.candice.controlonair.custom_firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.view_holder.ContactViewHolder;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class ContactFirebaseAdapter extends FirebaseRecyclerAdapter<Contact, ContactViewHolder>
{
    private ContactViewHolder.OnContactClickListener onContactClickListener;
    private static final String TAG = "PeopleListAdapter";

    public ContactFirebaseAdapter(FirebaseRecyclerOptions<Contact> options, ContactViewHolder.OnContactClickListener onContactClickListener)
    {
        super(options, true);
        this.onContactClickListener = onContactClickListener;
    }

    public interface RecycleItemClick
    {
        void onItemClick(String userId, Contact contact, int position);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);

        return new ContactViewHolder(view, onContactClickListener);
    }

    @Override
    protected void onBindViewHolder(ContactViewHolder holder, int position, Contact model)
    {
        holder.bind(model);
    }

    @Override
    protected void onChildUpdate(Contact model,
                                 ChangeEventType type,
                                 DataSnapshot snapshot,
                                 int newIndex,
                                 int oldIndex)
    {
        super.onChildUpdate(model, type, snapshot, newIndex, oldIndex);
    }

    @Override
    protected boolean filterCondition(Contact model, String filterPattern)
    {
        return model.getName().toLowerCase().contains(filterPattern);
    }

}
