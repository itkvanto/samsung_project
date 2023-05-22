package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private RecyclerView pdfRecyclerView;
    private PdfAdapter pdfAdapter;

    private FirebaseDatabase firebaseDatabase;
    private List<PdfFile> pdfFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        categorySpinner = findViewById(R.id.categorySpinner);
        pdfRecyclerView = findViewById(R.id.pdfRecyclerView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        pdfFiles = new ArrayList<>();

        setupCategorySpinner();
        setupRecyclerView();
    }

    private void setupCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                loadPdfFiles(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupRecyclerView() {
        pdfAdapter = new PdfAdapter(pdfFiles);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfRecyclerView.setAdapter(pdfAdapter);
    }

    private void loadPdfFiles(String category) {
        firebaseDatabase.getReference(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pdfFiles.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PdfFile pdfFile = snapshot.getValue(PdfFile.class);
                    pdfFiles.add(pdfFile);
                }
                pdfAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    public void openPdfInWebView(String pdfUrl) {
        Intent intent=new Intent(getApplicationContext(),WebActivity.class);
        intent.putExtra("pdf_url",pdfUrl);
        startActivity(intent);
    }
}
