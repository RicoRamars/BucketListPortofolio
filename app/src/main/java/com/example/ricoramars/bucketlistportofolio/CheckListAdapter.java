package com.example.ricoramars.bucketlistportofolio;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckListHolder> {
    private List<CheckListItem> checkListItems = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CheckListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.checklist_item, viewGroup, false);
        return new CheckListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListHolder checkListHolder, int i) {
        CheckListItem currentCheckListItem = checkListItems.get(i);
        checkListHolder.textViewTitle.setText(currentCheckListItem.getName());
        checkListHolder.checkBoxItem.setChecked(currentCheckListItem.getCompleted());
        checkListHolder.textViewDescription.setText(currentCheckListItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return checkListItems.size();
    }

    public void setCheckListItems(List<CheckListItem> checkListItems) {
        this.checkListItems = checkListItems;
        notifyDataSetChanged();
    }

    public CheckListItem getCheckListItemAt(int position) {
        return checkListItems.get(position);
    }

    class CheckListHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBoxItem;
        private TextView textViewTitle;
        private TextView textViewDescription;

        public CheckListHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxItem = itemView.findViewById(R.id.checkbox_completed);
            textViewTitle = itemView.findViewById(R.id.text_name);
            textViewDescription = itemView.findViewById(R.id.text_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(checkListItems.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CheckListItem checkListItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
