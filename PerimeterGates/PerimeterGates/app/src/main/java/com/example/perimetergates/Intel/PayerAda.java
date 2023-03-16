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

public class PayerAda extends ArrayAdapter<PayerMode> {
    public ArrayList<PayerMode> MainList;
    public ArrayList<PayerMode> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;

    public PayerAda(Context context, int id, ArrayList<PayerMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
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

        PayerMode subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Mpesa.setText(+pos + ".  " + subject.getMpesa());
        pos++;
        holder.Amount.setText(" KSHs." + subject.getAmount());
        holder.Status.setText(subject.getStatus());
        holder.Custm.setText(subject.getName());
        holder.Datex.setText(subject.getReg_date());
        return convertView;

    }

    public class ViewHolder {
        TextView Mpesa, Amount, Status, Custm, Datex;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<PayerMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    PayerMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<PayerMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }
}


