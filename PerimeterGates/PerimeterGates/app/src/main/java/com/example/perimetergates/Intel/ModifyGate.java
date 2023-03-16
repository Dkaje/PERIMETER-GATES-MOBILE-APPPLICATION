package com.example.perimetergates.Intel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.perimetergates.R;

import java.util.ArrayList;

public class ModifyGate extends ArrayAdapter<GateMode> {
    public ArrayList<GateMode> MainList;
    public ArrayList<GateMode> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;

    public ModifyGate(Context context, int id, ArrayList<GateMode> subjectArrayList) {

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

            convertView = vi.inflate(R.layout.modify, null);

            holder = new ViewHolder();
            holder.SubjectQuantity = convertView.findViewById(R.id.appPName);
            holder.priceTag = convertView.findViewById(R.id.appPrice);
            holder.imageView = convertView.findViewById(R.id.theProduct);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GateMode subject = SubjectListTemp.get(position);
        holder.SubjectQuantity.setText(subject.getType());
        holder.priceTag.setText("@KES. " + subject.getPrice());
        Glide.with(convertView).load(subject.getImage()).into(holder.imageView);
        return convertView;

    }

    public class ViewHolder {
        TextView SubjectQuantity;
        TextView priceTag;
        ImageView imageView;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<GateMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    GateMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<GateMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}
