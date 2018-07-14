package com.esgi.guitton.candice.controlonair.custom_firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.view_holder.ConversationViewHolder;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class ConversationFirebaseAdapter extends FirebaseRecyclerAdapter<Conversation, ConversationViewHolder>
{
    private ConversationViewHolder.OnConversationClickListener onConversationClickListener;
    private static final String TAG = "ConversationAdapter";

    public ConversationFirebaseAdapter(FirebaseRecyclerOptions<Conversation> options, ConversationViewHolder.OnConversationClickListener onConversationClickListener)
    {
        super(options, true);
        this.onConversationClickListener = onConversationClickListener;
    }

    public interface RecycleItemClick
    {
        void onItemClick(String userId, Conversation Conversation, int position);
    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_list_item, parent, false);

        return new ConversationViewHolder(view, onConversationClickListener);
    }

    @Override
    protected void onBindViewHolder(ConversationViewHolder holder, int position, Conversation model)
    {
        holder.bind(model);
    }

    @Override
    protected void onChildUpdate(Conversation model,
                                 ChangeEventType type,
                                 DataSnapshot snapshot,
                                 int newIndex,
                                 int oldIndex)
    {
        super.onChildUpdate(model, type, snapshot, newIndex, oldIndex);
    }

    @Override
    protected boolean filterCondition(Conversation model, String filterPattern)
    {
        return model.toString().toLowerCase().contains(filterPattern);
    }

}
