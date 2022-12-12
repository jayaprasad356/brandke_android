package com.kapp.bharat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kapp.bharat.Models.WalletRecyclerModel;
import com.kapp.bharat.R;

import java.util.ArrayList;

public class WalletRecyclerAdapter extends RecyclerView.Adapter<WalletRecyclerAdapter.WalletViewHolder> {

    private Context ctx;
    private ArrayList<WalletRecyclerModel> walletRecyclerModelArrayList;
    WalletRecyclerAdapter(Context ctx, ArrayList<WalletRecyclerModel> walletRecyclerModelArrayList) {
        this.ctx = ctx;
        this.walletRecyclerModelArrayList = walletRecyclerModelArrayList;
    }
    @NonNull
    @Override
    public WalletRecyclerAdapter.WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.wallet_recycler,parent,false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletRecyclerAdapter.WalletViewHolder holder, int position) {
        final WalletRecyclerModel model = walletRecyclerModelArrayList.get(position);
        holder.MobileNumber.setText(model.getMobileNumber());
        holder.AccountNumber.setText(model.getAccountNumber());
        holder.IFSCCode.setText(model.getIFCSCode());
        holder.DOB.setText(model.getDateOfBirth());
        holder.BankId.setText(model.getBankId());
        holder.Address.setText(model.getAddress());
        holder.PinCode.setText(model.getPinCode());
    }

    @Override
    public int getItemCount() {
        return walletRecyclerModelArrayList.size();
    }

    class WalletViewHolder extends RecyclerView.ViewHolder {

        private TextView MobileNumber,AccountNumber,IFSCCode,DOB,BankId,Address,PinCode;
        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            MobileNumber = itemView.findViewById(R.id.MobileNum);
            AccountNumber = itemView.findViewById(R.id.AccountNumber);
            IFSCCode = itemView.findViewById(R.id.IFSC_Code);
            DOB = itemView.findViewById(R.id.DOB);
            BankId = itemView.findViewById(R.id.BankId);
            Address = itemView.findViewById(R.id.Address);
            PinCode = itemView.findViewById(R.id.PinCode);
        }
    }
}
