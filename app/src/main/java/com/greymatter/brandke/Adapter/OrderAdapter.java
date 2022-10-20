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

import Models.OrderModel;

public class OrderAdapter extends RecyclerView.Adapter<Adaptors.OrderAdapter.OrderViewHolder> {

    private ArrayList<OrderModel> orderModelArrayList;
    private Context context;

    public OrderAdapter(ArrayList<OrderModel> orderModelArrayList, Context context) {
        this.context = context;
        this.orderModelArrayList  = orderModelArrayList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.order_model_layout,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel orderModel = orderModelArrayList.get(position);
        holder.OrderNumber.setText(orderModel.getOrderNumber());
        holder.OrderCount.setText(orderModel.getOrderItemCount());
        holder.OrderName.setText(orderModel.getOrderName());
        holder.OrderDate.setText(orderModel.getOrderPlacedDate());
        holder.OrderAmount.setText(orderModel.getOrderAmount());
        holder.OrderStatus.setText(orderModel.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }

    protected static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView OrderNumber,OrderCount,OrderName,OrderDate,OrderAmount,OrderStatus;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNumber =itemView.findViewById(R.id.tvOrderNumber);
            OrderCount =itemView.findViewById(R.id.tvOrderCount);
            OrderName =itemView.findViewById(R.id.tvOrderName);
            OrderDate =itemView.findViewById(R.id.tvOrderDate);
            OrderAmount =itemView.findViewById(R.id.tvAmount);
            OrderStatus = itemView.findViewById(R.id.tvOderStatus);
        }
    }
}
