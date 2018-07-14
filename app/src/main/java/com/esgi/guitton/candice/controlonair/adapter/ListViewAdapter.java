package com.esgi.guitton.candice.controlonair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.esgi.guitton.candice.controlonair.R;
import com.esgi.guitton.candice.controlonair.models.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thomas Ecalle on 14/07/2018.
 */
public class ListViewAdapter extends BaseAdapter
{

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Contact> contacts = null;
    private ArrayList<Contact> arraylist;

    public ListViewAdapter(Context context, List<Contact> contacts)
    {
        mContext = context;
        this.contacts = contacts;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Contact>();
        this.arraylist.addAll(contacts);
    }

    public class ViewHolder
    {
        TextView name;
    }

    @Override
    public int getCount()
    {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position)
    {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        final ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(contacts.get(position).getName());
        return view;
    }

    // Filter Class
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        contacts.clear();
        if (charText.length() == 0)
        {
            contacts.addAll(arraylist);
        }
        else
        {
            for (Contact contact : arraylist)
            {
                if (contact.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    contacts.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }
}