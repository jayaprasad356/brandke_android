package Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.brandke.R;

import java.util.ArrayList;

import Models.NotificationModel;

public class NotificationAdapter extends RecyclerView.Adapter<Adaptors.NotificationAdapter.OrderViewHolder> {

    private ArrayList<NotificationModel> orderModelArrayList;
    private Context context;

    public NotificationAdapter(ArrayList<NotificationModel> orderModelArrayList, Context context) {
        this.context = context;
        this.orderModelArrayList  = orderModelArrayList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.notification_model_layout,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        NotificationModel notificationModel = orderModelArrayList.get(position);
        holder.NotificationName.setText(notificationModel.getNotificationName());
        holder.NotificationDescription.setText(notificationModel.getNotificationDescription());
    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }

    protected static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView NotificationName;
        private TextView NotificationDescription;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            NotificationName = itemView.findViewById(R.id.NotificationName);
            NotificationDescription = itemView.findViewById(R.id.NotificationDescription);
        }
    }
}
