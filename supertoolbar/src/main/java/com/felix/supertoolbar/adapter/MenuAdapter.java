package com.felix.supertoolbar.adapter;

import com.felix.supertoolbar.R;
import com.felix.supertoolbar.config.ContextMenu;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by chaichuanfa on 17/5/27.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.VH> {

    private ContextMenu mContextMenu;

    private OnMenuItemClickCallback mCallback;

    public MenuAdapter(ContextMenu mContextMenu, OnMenuItemClickCallback mCallback) {
        this.mContextMenu = mContextMenu;
        this.mCallback = mCallback;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.felix_toolbar_menu_item_layout, null));
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        String title = mContextMenu.getItemTitles().get(position);
        int icon = 0;
        if (mContextMenu.getItemDrawable() != null) {
            icon = mContextMenu.getItemDrawable().get(position);
        }

        if (icon != 0) {
            holder.menu_icon.setVisibility(View.VISIBLE);
            holder.menu_icon.setImageResource(icon);
        } else {
            holder.menu_icon.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(title)) {
            holder.menu_text.setText(title);
            holder.menu_text.setVisibility(View.VISIBLE);
        } else {
            holder.menu_text.setVisibility(View.GONE);
        }
        holder.menu_text.setTextColor(mContextMenu.getTextColor());
        if (position == mContextMenu.getItemTitles().size() - 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
            if (mContextMenu.getDividerColor() != 0) {
                holder.divider.setBackgroundColor(mContextMenu.getDividerColor());
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onMenuItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContextMenu.getItemTitles().size();
    }

    public class VH extends RecyclerView.ViewHolder {

        public final ImageView menu_icon;

        public final TextView menu_text;

        public final View divider;

        public VH(View itemView) {
            super(itemView);
            menu_icon = (ImageView) itemView.findViewById(R.id.mMenuIcon);
            menu_text = (TextView) itemView.findViewById(R.id.mMenuText);
            divider = itemView.findViewById(R.id.mMenuDivider);
        }
    }
}
