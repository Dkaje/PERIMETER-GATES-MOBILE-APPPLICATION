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

public class BudaAda extends ArrayAdapter<CartYangu> {
    public ArrayList<CartYangu> MainList;
    public ArrayList<CartYangu> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;
    String selectedID = "";

    public BudaAda(Context context, int id, ArrayList<CartYangu> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.follow_up, null);
            holder = new ViewHolder();
            holder.Detail = convertView.findViewById(R.id.rtItem);
            holder.UnitPrice = convertView.findViewById(R.id.rtDesc);
            holder.Quantity = convertView.findViewById(R.id.rtAmount);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartYangu subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Detail.setText(+pos + ".  " + subject.getCategory());
        pos++;
        holder.UnitPrice.setText(subject.getQuantity() + "@ KSH" + subject.getPrice());
        holder.Quantity.setText("KSH" + String.format("%.1f", Float.parseFloat(subject.getPrice()) * Float.parseFloat(subject.getQuantity())));

        return convertView;

    }

    public class ViewHolder {
        TextView Detail;
        TextView Quantity;
        TextView UnitPrice;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<CartYangu> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    CartYangu subject = MainList.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            SubjectListTemp = (ArrayList<CartYangu>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}

