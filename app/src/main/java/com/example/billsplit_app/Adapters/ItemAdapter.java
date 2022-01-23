package com.example.billsplit_app.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billsplit_app.Dish;
import com.example.billsplit_app.InternalFiles;
import com.example.billsplit_app.MainActivity;
import com.example.billsplit_app.R;

import org.json.JSONException;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private TextView indivTotal;
    private View thisView;
    private Context context;
    private double indivTotalNum = 0.0;
    public com.example.billsplit_app.Adapters.SharedAdapter SharedAdapter = new SharedAdapter();

    public ItemAdapter(Context context, TextView total, double indivTotalNum) {
        this.indivTotal = total;
        this.context = context;
        this.indivTotalNum = indivTotalNum;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageButton delete_button;
        private TextView name_str;
        private TextView price_str;
        private ImageButton expand_collapse_button;
        private TextView shared_with_text;
        private ImageButton alcohol_image;
        private ImageButton alcohol_checkmark;
        private RecyclerView SharedRecyclerView;
        private Boolean IsCollapsed = false;
        private Boolean alcoholImageClicked = false;
        private Boolean alcoholTaxApplicable = false;
        public RecyclerView sharedRecyclerView;


        private TextWatcher textWatcherName = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        private TextWatcher textWatcherPrice = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        @SuppressLint("ResourceType")
        public ItemViewHolder(final View view) {
            super(view);
            thisView = view;
            delete_button = view.findViewById(R.id.delete_button);
            name_str = view.findViewById(R.id.dish_name);
            price_str = view.findViewById(R.id.dish_price);
            expand_collapse_button = view.findViewById(R.id.expand_collapse_button);
            shared_with_text = view.findViewById(R.id.shared_with_text);
            alcohol_image = view.findViewById(R.id.alcohol_image);
            alcohol_checkmark = view.findViewById(R.id.alcohol_check);
            SharedRecyclerView = view.findViewById(R.id.shared_profile_list_view);

            setupRecyclerView(view.getContext(), SharedRecyclerView);
            sharedRecyclerView = view.findViewById(R.layout.item_shared_profile);


        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.name_str.removeTextChangedListener(holder.textWatcherName);
        holder.price_str.removeTextChangedListener(holder.textWatcherPrice);
        Dish dishItem = MainActivity.dishList.get(position);
        String name = dishItem.getName();
        String price = dishItem.getPrice();

        holder.name_str.setText(name);
        holder.price_str.setText(price);


        if (dishItem.isCollapsed()) {
            holder.expand_collapse_button.setBackgroundResource(R.drawable.expand_button);
            holder.SharedRecyclerView.setVisibility(View.GONE);
            holder.shared_with_text.setVisibility(View.GONE);
            holder.alcohol_image.setVisibility(View.GONE);
        }
        else {
            holder.expand_collapse_button.setBackgroundResource(R.drawable.collapse_button);
            holder.SharedRecyclerView.setVisibility(View.VISIBLE);
            holder.shared_with_text.setVisibility(View.VISIBLE);
            holder.alcohol_image.setVisibility(View.VISIBLE);
        }

        if (dishItem.isAlcoholic()) {
            System.out.println(position);
            holder.alcohol_image.setBackgroundResource(R.drawable.alcohol_checked);
            holder.alcohol_checkmark.setVisibility(View.VISIBLE);
            holder.alcoholImageClicked = true;
            holder.alcoholTaxApplicable = true;
        }
        else {
            holder.alcohol_image.setBackgroundResource(R.drawable.alcohol_unchecked);
            holder.alcohol_checkmark.setVisibility(View.GONE);
            holder.alcoholImageClicked = false;
        }

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dishList.remove(dishItem);
                updateIndivTotal();
                indivTotal.setText("$ " + String.format("%.2f", indivTotalNum));
                notifyDataSetChanged();
            }
        });

        holder.expand_collapse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dishItem.isCollapsed()) {
                    dishItem.setCollapsed(true);
                } else {
                    dishItem.setCollapsed(false);
                }
                notifyDataSetChanged();
            }
        });

        holder.alcohol_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dishItem.isAlcoholic()) {
                    dishItem.setAlcoholic(true);
                } else {
                    dishItem.setAlcoholic(false);
                }
                updateIndivTotal();
                notifyDataSetChanged();
            }
        });

        holder.textWatcherName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dishItem.setName(s.toString());
            }
        };

        holder.textWatcherPrice = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.pricesChanged = true;
                checkEditCostTextValid(s,holder,thisView);
                if (!s.toString().isEmpty()) {
                    dishItem.setPrice(s.toString());
                }
                else {
                    dishItem.setPrice("0.0");
                }
                updateIndivTotal();
                indivTotal.setText("$ " + String.format("%.2f", indivTotalNum));
            }
        };

        holder.name_str.addTextChangedListener(holder.textWatcherName);
        holder.price_str.addTextChangedListener(holder.textWatcherPrice);

        SharedAdapter.adapterDish(dishItem);
        SharedAdapter.notifyDataSetChanged();

        updateIndivTotal();
        indivTotal.setText("$ " + String.format("%.2f", indivTotalNum));
    }

    @Override
    public int getItemCount() {
        return MainActivity.dishList.size();
    }

    private void setupRecyclerView(Context context, RecyclerView SharedRecyclerView) {
        SharedRecyclerView.setAdapter(SharedAdapter);
        SharedRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
    }

    private void checkEditCostTextValid(Editable s, ItemViewHolder holder, View view) {
        String s1 = s.toString();

        if (s1.contains(".")) {
            if (s1.substring(s1.indexOf(".")).length() > 3) {
                holder.price_str.setText(s1.substring(0,s1.indexOf(".")+3));
                Toast.makeText(context, "Please only enter up to two decimal places!", Toast.LENGTH_LONG).show();
                closeKeyboard(view);
            }
        }
    }

    private void updateIndivTotal() {
        indivTotalNum = 0;
        for (Dish d : MainActivity.dishList) {
            if (!d.getPrice().isEmpty()) {
                indivTotalNum += Double.parseDouble(d.getPrice());
            }
        }
    }

    private void closeKeyboard(View v) {
        if (v != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
