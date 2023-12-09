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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.auliaAnugrahAzizJBusRD.R;
import com.auliaAnugrahAzizJBusRD.jbus_android.array_adapter.BusArrayAdapter;
import com.auliaAnugrahAzizJBusRD.jbus_android.model.Bus;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.BaseApiService;
import com.auliaAnugrahAzizJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private BusArrayAdapter busArrayAdapter;
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 6;
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView;
    private HorizontalScrollView pageScroll = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        busListView = findViewById(R.id.listView);

        busArrayAdapter = new BusArrayAdapter(mContext, new ArrayList<>());
        busListView.setAdapter(busArrayAdapter);

        handleGetAllBus();

    }

    private void paginationFooter() {
        int val = listSize % pageSize;
        val = (val == 0) ? 0 : 1;
        noOfPages = listSize / pageSize + val;
        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }
        for (int i = 0; i < noOfPages; i++) {
            btns[i] = new Button(this);
            btns[i].setText(Integer.toString(i + 1));
            btns[i].setTextColor(getResources().getColor(R.color.black));
            btns[i].setTextSize(16);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    private void goToPage(int index) {
        for (int i = 0; i < noOfPages; i++) {
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

        busArrayAdapter.clear();
        busArrayAdapter.addAll(paginatedList);
    }

    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Bus> filteredList = filterBusList(listBus, newText);
                busArrayAdapter.clear();
                busArrayAdapter.addAll(filteredList);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private List<Bus> filterBusList(List<Bus> listBus, String query) {
        query = query.toLowerCase().trim();
        List<Bus> filteredList = new ArrayList<>();
        for (Bus bus : listBus) {
            if (bus.name.trim().toLowerCase().contains(query)) {
                filteredList.add(bus);
            }
        }
        return filteredList;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.account_profile) {
            moveActivity(this, AboutMeActivity.class);
        } else if (item.getItemId() == R.id.payment) {
            moveActivity(this, PaymentActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleGetAllBus() {
        mApiService.getAllBus().enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Bus> busList = response.body();

                if (busList != null && !busList.isEmpty()) {
                    runOnUiThread(() -> {
                        listBus.clear();
                        listBus.addAll(busList);
                        busArrayAdapter.notifyDataSetChanged();
                        listSize = listBus.size();
                        paginationFooter();
                        goToPage(currentPage);

                        prevButton.setOnClickListener(v -> {
                            currentPage = (currentPage != 0) ? currentPage - 1 : 0;
                            goToPage(currentPage);
                        });

                        nextButton.setOnClickListener(v -> {
                            currentPage = (currentPage != noOfPages - 1) ? currentPage + 1 : currentPage;
                            goToPage(currentPage);
                        });
                    });
                } else {
                    Toast.makeText(mContext, "No buses found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
