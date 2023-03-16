package com.example.perimetergates.Intel;

import android.content.Context;
import android.graphics.Color;
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

public class YanguAda extends ArrayAdapter<CartYangu> {
    public ArrayList<CartYangu> MainList;
    public ArrayList<CartYangu> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;

    public YanguAda(Context context, int id, ArrayList<CartYangu> subjectArrayList) {

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

            convertView = vi.inflate(R.layout.view_cart, null);

            holder = new ViewHolder();
            holder.SubjectQuantity = convertView.findViewById(R.id.appPName);
            holder.imageView = convertView.findViewById(R.id.theProduct);
            holder.priced = convertView.findViewById(R.id.appPrice);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartYangu subject = SubjectListTemp.get(position);
        float qty = Float.parseFloat(subject.getQuantity());
        float pric = Float.parseFloat(subject.getPrice());
        double sum = qty * pric;
        String mess = "";
        holder.SubjectQuantity.setText(subject.getCategory() + "\nType: " + subject.getType());
        if (subject.getCategory().equals("Grills")){
            mess=subject.getQuantity()+" grill(s)";
        }else if (subject.getCategory().equals("Gates")){
            mess=subject.getQuantity()+" gate(s)";
        }else if (subject.getCategory().equals("Doors")){
            mess=subject.getQuantity()+" door(s)";
        }else if (subject.getCategory().equals("Windows")){
            mess=subject.getQuantity()+" window(s)";
        }else if (subject.getCategory().equals("Shoe Rack")){
            mess=subject.getQuantity()+" shoe(s)";
        }
            holder.priced.setText("Quantity: " + mess +"\nPrice@Unit KSHs. " + subject.getPrice() + "\nTotal KSHs. " + String.format("%.0f", sum) );
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(convertView).load(SubjectListTemp.get(position).getImage()).into(holder.imageView);

        return convertView;

    }

    public class ViewHolder {
        TextView SubjectQuantity;
        TextView priced;
        ImageView imageView;
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
