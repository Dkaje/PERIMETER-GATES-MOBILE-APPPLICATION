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

public class MoreAda extends ArrayAdapter<StoreMo> {
    public ArrayList<StoreMo> MainList;
    public ArrayList<StoreMo> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;

    public MoreAda(Context context, int id, ArrayList<StoreMo> subjectArrayList) {

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

            convertView = vi.inflate(R.layout.insider, null);

            holder = new ViewHolder();
            holder.SubjectQuantity = convertView.findViewById(R.id.appPName);
            holder.subjecType = convertView.findViewById(R.id.appType);
            holder.imageView = convertView.findViewById(R.id.theProduct);
            holder.textME = convertView.findViewById(R.id.myText);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StoreMo subject = SubjectListTemp.get(position);
        holder.SubjectQuantity.setText(subject.getCategory());
        holder.textME.setText("Click here to approve product");
        holder.subjecType.setText(subject.getType()+"\nQuantity: "+subject.getQuantity()+" units");
        Glide.with(convertView).load(subject.getImage()).into(holder.imageView);
        return convertView;

    }

    public class ViewHolder {
        TextView SubjectQuantity, subjecType;
        ImageView imageView;
        TextView textME;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<StoreMo> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    StoreMo subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<StoreMo>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}
