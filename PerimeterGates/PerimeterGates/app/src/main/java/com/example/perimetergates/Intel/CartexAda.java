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

public class CartexAda extends ArrayAdapter<CartMode> {
    public ArrayList<CartMode> MainList;
    public ArrayList<CartMode> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;

    public CartexAda(Context context, int id, ArrayList<CartMode> subjectArrayList) {

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

            convertView = vi.inflate(R.layout.nice_to, null);

            holder = new ViewHolder();//cate,qty,unt,tot
            holder.Category = convertView.findViewById(R.id.cate);
            holder.priced = convertView.findViewById(R.id.unt);
            holder.Quantity = convertView.findViewById(R.id.qty);
            holder.Total = convertView.findViewById(R.id.tot);
            holder.imageView = convertView.findViewById(R.id.theProduct);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartMode subject = SubjectListTemp.get(position);
        float qty = Float.parseFloat(subject.getQuantity());
        float pric = Float.parseFloat(subject.getPrice());
        double sum = qty * pric;
        String mess = "";
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
        holder.Category.setText(subject.getCategory());
            holder.priced.setText("KSHs" + subject.getPrice());
            holder.Quantity.setText(mess);
            holder.Total.setText("KSHs. " + String.format("%.0f", sum));
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(convertView).load(SubjectListTemp.get(position).getImage()).into(holder.imageView);

        return convertView;

    }

    public class ViewHolder {
        TextView Quantity;
        TextView Total;
        TextView Category;
        TextView priced;
        ImageView imageView;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<CartMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    CartMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<CartMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}

