package com.mark.ss;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mListView;
    private TextView mSearchButton;
    private AutoCompleteTextView mEditText;
    private Spinner mSpinner;
    private TextView emptyView;

    private String currentType;
    private String inputText;

    private SearchHistoryDao dao;

    private String TAG = "TAG";
    private final int DATA_OK = 100;
    private final int DATA_NOT_EXIST = 101;

    private MyAdapter adapter;
    private ArrayAdapter<String> arrayAdapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DATA_OK:
                    List<Data> data = (List<Data>) msg.obj;
                    setAdapter(data);
                    if (data.size()>0){
                        saveSearchHistory();
                    }
                    break;
                case DATA_NOT_EXIST:
                    String message = (String) msg.obj;
                    emptyView.setText(message);
                    break;
            }

        }
    };
    private List<History> historyList;

    private void setAdapter(List<Data> data) {
        adapter.setDatas(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getHistory();
        currentType = getValues()[0];
        initView();
        setListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_about){
            startActivity(new Intent(this,AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getHistory(){
        dao = new SearchHistoryDao(this);
        historyList = dao.query();
    }

    private void setListener() {
        mSearchButton.setOnClickListener(this);
        mSpinner.setOnItemSelectedListener(listener);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getArray());
        mEditText.setAdapter(arrayAdapter);
        mEditText.setOnItemClickListener(itemClickListener);
        mListView.setAdapter(adapter = new MyAdapter(this));
        mListView.setEmptyView(emptyView);
        mListView.setOnItemClickListener(listItemClickListener);
    }

    private String[] getArray() {
        String[] array = new String[historyList.size()];
        for (int i=0;i<historyList.size();i++){
            History history = historyList.get(i);
            array[i] = history.getNumber();
        }
        return array;
    }

    private void initView() {
//        mEditText = (EditText) findViewById(R.id.edit_input_main);
        mEditText = (AutoCompleteTextView) findViewById(R.id.edit_input_main);
        mListView = (ListView) findViewById(R.id.list);
        mSearchButton = (TextView) findViewById(R.id.btn_search);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        emptyView = (TextView) findViewById(R.id.text_emptyView);
    }

    @Override
    public void onClick(View view) {
        getData();

        inputText = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(inputText)){
            mToast("请输入运单号后再试");
            return;
        }

        if (!HttpUtils.isNetworkAvailable(this)){
            mToast("当前无可用网络，请连接网络后重试");
            return;
        }

        request();
    }

    private void saveSearchHistory(){
        if (dao != null){
            int index = indexOfArray(getValues(), currentType);
            if (index!=-1){
                History history = new History(inputText, getKeys()[index], currentType);
                if (historyList!=null && !historyList.contains(history)){
                    historyList.add(history);
                    arrayAdapter.notifyDataSetChanged();
                }
                dao.insert(history);
            }
        }
    }

    private AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            currentType = getValues()[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            currentType = getValues()[0];
        }
    };

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object item = parent.getAdapter().getItem(position);
            if (item instanceof String){
                String content = (String) item;
                for(int i=0;i<historyList.size();i++){
                    History history = historyList.get(i);
                    if (content.equals(history.getNumber())){
                        String typeCode = history.getTypeCode();
                        currentType = typeCode;
                        String typeName = history.getTypeName();
                        int index = indexOfArray(getKeys(), typeName);
                        mSpinner.setSelection(index);
                    }
                }
            }
        }
    };

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object item = parent.getAdapter().getItem(position);
            if (item instanceof Data){
                Data data = (Data) item;
                Intent intent = new Intent(getBaseContext(),DetailActivity.class);
                intent.putExtra("content",data.getContext());
                startActivity(intent);
            }
        }
    };

    private void request() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = API.getSearchUrl(currentType, inputText);
                    String response = HttpUtils.request(url);
                    List<Data> dataList = parse(response);
                    Message message = Message.obtain();
                    message.what = DATA_OK;
                    message.obj = dataList;
                    mHandler.sendMessage(message);
                    Log.d(TAG, "run: "+response );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private List<Data> parse(String json){
        if (TextUtils.isEmpty(json)){
            return null;
        }

        List<Data> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            int status = object.optInt("status");
            int state = object.optInt("state");
            if (status == 200){
                JSONArray array = object.getJSONArray("data");
                for (int i=0;i<array.length();i++){
                    Data data = new Data();
                    JSONObject obj = array.getJSONObject(i);
                    data.setTime(obj.optString("time"));
                    data.setContext(obj.optString("context"));
                    data.setFtime(obj.optString("ftime"));
                    data.setLocation(obj.optString("location"));
                    list.add(data);
                }
                return list;
            }else{
                String msg = object.optString("message");
                Message message = Message.obtain();
                message.what = DATA_NOT_EXIST;
                message.obj = msg;
                mHandler.sendMessage(message);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void mToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void mToast(int res){
        mToast(getString(res));
    }

    private List<Map<String,String>> getData(){
        String[] types = getKeys();
        String[] values = getValues();
        List<Map<String,String>> list = new ArrayList<>();
        for (int i=0;i<types.length;i++){
            Map<String,String> map = new HashMap<>();
            map.put(types[i],values[i]);
            list.add(map);
        }
        return list;
     }

    private String[] getValues(){
        return getResources().getStringArray(R.array.values);
    }

    private String[] getKeys(){
        return getResources().getStringArray(R.array.keys);
    }

    private int indexOfArray(String[] array ,String value){
        for (int i=0;i<array.length;i++){
            if (array[i].equals(value)){
                return i;
            }
        }
        return -1;
    }

}
