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

public class FeedAda extends ArrayAdapter<FeedMode> {
    public ArrayList<FeedMode> MainList;
    public ArrayList<FeedMode> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;

    public FeedAda(Context context, int id, ArrayList<FeedMode> subjectArrayList) {

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

            convertView = vi.inflate(R.layout.rat_us, null);

            holder = new ViewHolder();
            holder.SubjectQuantity = convertView.findViewById(R.id.appPName);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FeedMode subject = SubjectListTemp.get(position);
        holder.SubjectQuantity.setText("CustomerID: " + subject.getCustomer() + "\nName: " + subject.getName() + "\nPhone: " + subject.getPhone() + "\nRating: " + subject.getRating() + "\nMessage: " + subject.getMessage() + "\nDateSent: " + subject.getReg_date());
        return convertView;

    }

    public class ViewHolder {
        TextView SubjectQuantity;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<FeedMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    FeedMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<FeedMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}
