package com.example.vyspsrivyavasayiadmin;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by project on 2/24/18.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.Holder> implements Filterable {

  ArrayList<UserClass> userList,tempList;
  Context context;

    public UserListAdapter(ArrayList<UserClass> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.user_definition,null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {


    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<UserClass> filters = new ArrayList<>();
                for (int i = 0; i < tempList.size(); i++) {

                }
                results.count = filters.size();
                results.values = filters;
            } else {
                ArrayList<UserClass> dFilters = new ArrayList<>();
                for (int i = 0; i < tempList.size(); i++) {

                }
                results.count = dFilters.size();
                results.values = dFilters;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            userList = (ArrayList<UserClass>) results.values;
            notifyDataSetChanged();
//            recyclerView.scheduleLayoutAnimation();
        }
    };

    public class Holder extends RecyclerView.ViewHolder {
        TextView name;
        TextView email;
        TextView phone;
        TextView area;
        TextView unit;
        TextView address;

        public Holder(View convertView) {
            super(convertView);
            name = convertView.findViewById(R.id.name);
            email = convertView.findViewById(R.id.email);
            phone = convertView.findViewById(R.id.phone);
            area = convertView.findViewById(R.id.area);
            unit = convertView.findViewById(R.id.unit);
            address = convertView.findViewById(R.id.address);
        }
    }

}
