package Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.brandke.R;

import java.util.ArrayList;

import Models.CartModel;
import Models.WalletModel;

public class WalletAdaptor extends RecyclerView.Adapter<WalletAdaptor.viewholder> {

    private ArrayList<WalletModel> walletModelArrayList;
    private Context context;

    public WalletAdaptor(ArrayList<WalletModel> walletModelArrayList, Context context) {
        this.walletModelArrayList = walletModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_model_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        WalletModel model = walletModelArrayList.get(position);
        holder.Amount.setText(model.getAmount());
        holder.Date.setText(model.getDate());
        holder.Status.setText(model.getStatus());
    }

    @Override
    public int getItemCount() {
        return walletModelArrayList.size();
    }
    protected class viewholder extends RecyclerView.ViewHolder {
        private TextView Amount;
        private TextView Date;
        private TextView Status;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            Amount = itemView.findViewById(R.id.tvAmount);
            Date = itemView.findViewById(R.id.tvDate);
            Status = itemView.findViewById(R.id.tvStatus);
        }
    }
}
