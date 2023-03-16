package com.example.perimetergates.Intel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.perimetergates.R;

import java.util.ArrayList;

public class OrderAda extends ArrayAdapter<OrderMode> {
    public ArrayList<OrderMode> cartel;
    public ArrayList<OrderMode> orderModes;
    public FilteredItems filter;

    public OrderAda(Context context, int id, ArrayList<OrderMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.orderModes = new ArrayList<>();

        this.orderModes.addAll(subjectArrayList);

        this.cartel = new ArrayList<>();

        this.cartel.addAll(subjectArrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.money, null);

            holder = new ViewHolder();


            holder.Mpesa = convertView.findViewById(R.id.mpesa);
            holder.Amount = convertView.findViewById(R.id.amount);
            holder.Status = convertView.findViewById(R.id.status);
            holder.Custm = convertView.findViewById(R.id.customer);
            holder.Datex = convertView.findViewById(R.id.date);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OrderMode subject = orderModes.get(position);
        int pos = position + 1;
        holder.Mpesa.setText(+pos + ".  " + subject.getMpesa());
        pos++;
        holder.Amount.setText(" KSHs." + subject.getAmount());
        holder.Status.setText(subject.getFinance());
        holder.Custm.setText(subject.getName());
        holder.Datex.setText(subject.getDate());
        return convertView;

    }

    public class ViewHolder {
        TextView Mpesa, Amount, Status, Custm, Datex;
    }

    private class FilteredItems extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<OrderMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = cartel.size(); i < l; i++) {
                    OrderMode subject = cartel.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = cartel;

                    filterResults.count = cartel.size();
                }
            }
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            orderModes = (ArrayList<OrderMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = orderModes.size(); i < l; i++)
                add(orderModes.get(i));

            notifyDataSetInvalidated();
        }
    }
}


