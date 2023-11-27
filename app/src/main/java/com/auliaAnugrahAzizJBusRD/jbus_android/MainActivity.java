package com.auliaAnugrahAzizJBusRD.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public BusArrayAdapter busArrayAdapter;
    private TextView loginNow = null; // ga kepake
    ListView listView;
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 12;
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null; // ga kepake
    private HorizontalScrollView pageScroll = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseApiService mApiService = UtilsApi.getApiService();

        busArrayAdapter = new BusArrayAdapter(this, Bus.sampleBusList(20));

        listView = findViewById(R.id.listView);
        listView.setAdapter(busArrayAdapter);

        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        busListView = findViewById(R.id.listView);

        listBus = Bus.sampleBusList(60);
        listSize = listBus.size();

        paginationFooter();
        goToPage(currentPage);

        prevButton.setOnClickListener(v -> {
            currentPage = (currentPage != 0)? currentPage - 1 : 0;
            goToPage(currentPage);
        });
        nextButton.setOnClickListener(v -> {
            currentPage = (currentPage != noOfPages - 1)? currentPage + 1 : currentPage;
            goToPage(currentPage);
        });
    }

    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0:1;
        noOfPages = listSize / pageSize + val;
        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity =
                    Gravity.CENTER;
        }
        for (int i = 0; i < noOfPages; i++) {
            btns[i]=new Button(this);
//            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(Integer.toString(i+1));
// ganti dengan warna yang kalian mau
            btns[i].setTextColor(getResources().getColor(R.color.black));
            btns[i].setTextSize(16);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150,150);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    private void goToPage(int index) {
        for (int i = 0; i< noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }

    private void viewPaginatedList(List<Bus> listBus, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);

        busArrayAdapter = (BusArrayAdapter) listView.getAdapter();
        busArrayAdapter.clear();
        busArrayAdapter.addAll(paginatedList);
    }


    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) /
                2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        if(item.getItemId() == R.id.account_profile) {
            Intent aboutMeIntent = new Intent(this, AboutMeActivity.class);
            startActivity(aboutMeIntent);
        } else if (item.getItemId() == R.id.payment){
            Toast.makeText(this, "Payment Clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}