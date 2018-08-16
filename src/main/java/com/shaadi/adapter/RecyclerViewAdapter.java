package com.shaadi.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shaadi.app.R;
import com.shaadi.custom.SweetAlertDialogBox;
import com.shaadi.custom.SwipeableRecyclerViewTouchListener;
import com.shaadi.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private SwipeableRecyclerViewTouchListener swipeTouchListener;

    public void setOnSwipeTouchListener(SwipeableRecyclerViewTouchListener swipeTouchListener) {
        this.swipeTouchListener = swipeTouchListener;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername, textViewAge, textViewEmail, textViewCity;
        ImageButton dismissButton, viewProfileButton;
        ImageView imageViewUserImage;

        ItemViewHolder(View view) {
            super(view);
            textViewUsername = (TextView) view.findViewById(R.id.user_name);
            textViewAge = (TextView) view.findViewById(R.id.user_age);
            textViewEmail = (TextView) view.findViewById(R.id.user_email);
            textViewCity = (TextView) view.findViewById(R.id.user_city);
            dismissButton = (ImageButton) view.findViewById(R.id.dismissButton);
            imageViewUserImage = (ImageView) view.findViewById(R.id.user_image);
            viewProfileButton = (ImageButton) view.findViewById(R.id.viewProfileButton);
        }
    }


    private List<User> userList;

    private Context context;

    public RecyclerViewAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_row_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, int i) {
        viewHolder.textViewUsername.setText(userList.get(i).getFirstName().substring(0,1).toUpperCase() + userList.get(i).getFirstName().substring(1) + " " +
                userList.get(i).getLastName().substring(0,1).toUpperCase() + userList.get(i).getLastName().substring(1));
        viewHolder.textViewAge.setText(userList.get(i).getAge()+" yrs");
        viewHolder.textViewEmail.setText(userList.get(i).getEmail());
        viewHolder.textViewCity.setText(userList.get(i).getCity().substring(0,1).toUpperCase() + userList.get(i).getCity().substring(1));
        final int index = i;
        viewHolder.viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialogBox().showOkDialogBox("You clicked on " + userList.get(index).getTitleName().substring(0,1).toUpperCase() + userList.get(index).getTitleName().substring(1) + " " + userList.get(index).getFirstName().substring(0,1).toUpperCase() + userList.get(index).getFirstName().substring(1) + " " +
                        userList.get(index).getLastName().substring(0,1).toUpperCase() + userList.get(index).getLastName().substring(1), (Activity) context);
            }
        });
        Picasso.get()
                .load(userList.get(i).getPictureLargeUrl())
                .into(viewHolder.imageViewUserImage);

        viewHolder.dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                ViewCompat.setTranslationX(viewHolder.itemView, 100);
                ViewCompat.setAlpha(viewHolder.itemView, 0);
                swipeTouchListener.performDismiss(viewHolder.itemView, viewHolder.getLayoutPosition());*/

                ViewCompat.animate(viewHolder.itemView)
                        .translationX(viewHolder.itemView.getWidth())
                        .alpha(0)
                        .setDuration(context.getResources().getInteger(
                                android.R.integer.config_shortAnimTime))
                        .setListener(new ViewPropertyAnimatorListener() {
                            @Override
                            public void onAnimationStart(View view) {
                                // Do nothing.
                            }

                            @Override
                            public void onAnimationEnd(View view) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                    swipeTouchListener.performDismiss(viewHolder.itemView, viewHolder.getLayoutPosition());
                                }
                            }

                            @Override
                            public void onAnimationCancel(View view) {
                                // Do nothing.
                            }
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return (null != userList ? userList.size() : 0);
    }


}