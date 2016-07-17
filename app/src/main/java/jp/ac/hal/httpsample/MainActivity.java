package jp.ac.hal.httpsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private ShopArrayAdapter arrayAdapter;
    private ArrayList<Shop> list;
    private ProgressBar progress;
    private ListView lv;
    private TextView tv;
    private int offset = 0;
    final int limit = 100;
    boolean loading = false;
    //private int max_item = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.listView);
        tv = (TextView)findViewById(R.id.textView);

        list = new ArrayList<Shop>();
        arrayAdapter = new ShopArrayAdapter(MainActivity.this,R.layout.shop_list_row, list);
        progress = new ProgressBar(this);
        lv.addFooterView(progress);
        lv.setAdapter(arrayAdapter);
        lv.setOnScrollListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView)parent;
                Shop shop =  (Shop)listView.getItemAtPosition(position);

                Toast.makeText(MainActivity.this,""+shop.getId(), Toast.LENGTH_SHORT).show();

            }
        });

        }
    public void getJson(){
    AsyncJsonLoader asyncJsonLoader = new AsyncJsonLoader(new AsyncJsonLoader.AsyncCallback() {
        @Override
        public void preExecute() {

        }

        @Override
        public void postExecute(JSONObject result) {
            if (result == null) {
                showLoadError(2);
                return;
            }
            try {
                JSONArray eventArray = result.getJSONArray("shops");
                for (int i = 0; i < eventArray.length(); i++) {
                    JSONObject eventObj = eventArray.getJSONObject(i);
                    JSONObject event = eventObj.getJSONObject("shop");
                    Shop shop = new Shop(event.getInt("id"),event.getString("name"),
                            event.getString("address"),event.getDouble("lat"),event.getDouble("lng"));
                    arrayAdapter.add(shop);
                }
                if(0==list.size()){
                    showLoadError(1);
                }
                arrayAdapter.notifyDataSetChanged();
                tv.setText(list.size()+"件");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void progressUpdate(int progress) {
        }
        @Override
        public void cancel() {
        }
    });

        String url = "http://pasuco234test.dip.jp/pasuco234test/ShinjukumeshiServlet?offset="+offset+"&limit="+limit;

        asyncJsonLoader.execute(url);
        offset++;
    }
    private void showLoadError(int code) {
        String errMsg ="エラー";
        if(code==1){
            errMsg ="結果なし";
        }else{
            errMsg ="エラー";
        }
        Toast.makeText( MainActivity.this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem , int visibleItemCount, int totalItemCount) {

        if(loading){
            return;
        }

        if(totalItemCount!= 0 && totalItemCount == firstVisibleItem + visibleItemCount) {
              loading =true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    getJson();
                    loading = false;
                }
            }).start();

            restoreListPosition();
        }
    }
    private void restoreListPosition() {
        int position = lv.getFirstVisiblePosition();
        int yOffset = lv.getChildAt(0).getTop();
        lv.setSelectionFromTop(position, yOffset);
    }
}

