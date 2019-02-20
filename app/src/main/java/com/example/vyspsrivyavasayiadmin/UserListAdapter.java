package com.example.vyspsrivyavasayiadmin;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
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

    ArrayList<UserClass> userList, tempList;
    Context context;

    public UserListAdapter(Context context, ArrayList<UserClass> userList) {
        this.userList = userList;
        this.tempList = userList;
        this.context = context;
        Log.d("temp", String.valueOf(userList.size()));

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.user_definition, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        UserClass uclass = userList.get(position);
        String name = uclass.getName();
        String phone = uclass.getMobile();
        String email = uclass.getEmail();
        String area = uclass.getArea();
        String area_code = uclass.getArea_code(); // Unit
        String address = "Address: " + uclass.getAddress();
        String registered = uclass.getRegistered();
        String timestamp = uclass.getTimestamp();
        String status = uclass.getStatus();
        holder.name.setText(name);
        holder.address.setText(address);
        holder.phone.setText("Contact: " + phone);
        if (!email.equals("")) {
            holder.email.setVisibility(View.VISIBLE);
            holder.email.setText("Email: " + email);
        } else
            holder.email.setVisibility(View.GONE);
        holder.area.setText(Html.fromHtml("Area: <font color=#1e2535><strong>" + area + "</strong></font>"));
        holder.unit.setText(Html.fromHtml("Unit: <font color=#1e2535><strong>" + area_code + "</strong></font>"));
        holder.status.setText(status);


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
                    UserClass uclass = tempList.get(i);
                    if (tempList.get(i).getName().toLowerCase().contains(constraint) || tempList.get(i).getEmail().toLowerCase().contains(constraint)
                            || tempList.get(i).getMobile().toLowerCase().contains(constraint)) {
                        filters.add(uclass);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                ArrayList<UserClass> dFilters = new ArrayList<>();
                for (int i = 0; i < tempList.size(); i++) {
                    UserClass uclass = tempList.get(i);
                    dFilters.add(uclass);
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
        TextView status;

        public Holder(View convertView) {
            super(convertView);
            name = convertView.findViewById(R.id.name);
            email = convertView.findViewById(R.id.email);
            phone = convertView.findViewById(R.id.phone);
            area = convertView.findViewById(R.id.area);
            unit = convertView.findViewById(R.id.unit);
            address = convertView.findViewById(R.id.address);
            status = convertView.findViewById(R.id.status);
        }
    }

}
