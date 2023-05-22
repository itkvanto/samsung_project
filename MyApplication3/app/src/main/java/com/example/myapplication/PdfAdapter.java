package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.os.Handler;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> {

    private List<PdfFile> pdfFiles;

    public PdfAdapter(List<PdfFile> pdfFiles) {
        this.pdfFiles = pdfFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_pdf, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PdfFile pdfFile = pdfFiles.get(position);
        holder.pdfTitle.setText(pdfFile.getTitle());

        holder.itemView.setOnClickListener(v -> {
            holder.progressBar.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Имитация длительной операции

                    ((MainActivity) holder.itemView.getContext()).openPdfInWebView(pdfFile.getUrl());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.progressBar.setVisibility(View.GONE);
                }
            }, 2000);
        });

    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView pdfIcon;
        public TextView pdfTitle;
        public ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            pdfIcon = itemView.findViewById(R.id.pdfIcon);
            pdfTitle = itemView.findViewById(R.id.pdfTitle);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
