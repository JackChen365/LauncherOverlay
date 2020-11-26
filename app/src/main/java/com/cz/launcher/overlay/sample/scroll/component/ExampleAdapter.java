package com.cz.launcher.overlay.sample.scroll.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cz.launcher.overlay.sample.R;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {

    private Context context;
    private ArrayList<ExampleItem> exampleList;
    private ArrayList<ExampleItem> exampleListFull;


    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList) {
        this.context = context;
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        View cardViewParent;

        ExampleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
            cardViewParent = itemView.findViewById(R.id.cardViewParent);
        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,
                parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {
        ExampleItem exampleItem = exampleList.get(position);

        holder.imageView.setImageResource(exampleItem.getImageResource());
        holder.textView1.setText(exampleItem.getText1());
        holder.textView2.setText(exampleItem.getText2());

//        holder.cardViewParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, exampleList.get(position).getText1() + " Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ExampleItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ExampleItem item : exampleListFull) {
                    if (item.getText1().toLowerCase().contains(filterPattern) || item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            exampleList.clear();
            exampleList.addAll((ArrayList<ExampleItem>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
