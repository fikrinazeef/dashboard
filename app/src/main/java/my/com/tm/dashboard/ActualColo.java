package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import my.dashboard.R;

public class ActualColo extends AppCompatActivity {


    private ListView listView;
    EditText editext;

    View myView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_subb);

        listView = (ListView) findViewById(R.id.list);

        getJSON();

    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ACTUAL_COLO);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_STATEACTUALCOLO);
                String b = jo.getString(Config.TAG_SITEACTUALCOLO);
                String c = jo.getString(Config.TAG_ACTUALABBRCOLO);
                String d = jo.getString(Config.TAG_ACTUALOLDCOLO);




                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_STATEACTUALCOLO,a);
                employees.put(Config.TAG_SITEACTUALCOLO,b);
                employees.put(Config.TAG_ACTUALABBRCOLO,c);
                employees.put(Config.TAG_ACTUALOLDCOLO,d);



                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.listactualall,
                new String[]{Config.TAG_STATEACTUALCOLO,Config.TAG_SITEACTUALCOLO,Config.TAG_ACTUALABBRCOLO,Config.TAG_ACTUALOLDCOLO},

                new int[]{R.id.dua, R.id.satu,  R.id.tiga, R.id.empat
                });

        listView.setAdapter(adapter);

    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getApplicationContext(),"Loading Data","Wait...",false,false);
                //    loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                //   loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                JSON_STRING = s;
                //  showData();
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_GET_ACTUAL_COLO);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
