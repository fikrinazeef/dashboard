package my.com.tm.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import my.dashboard.R;

public class ActualFiber extends AppCompatActivity {


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
        ArrayList<ModelActualFiber> listactual1 = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("fiber");

            for(int i = 0; i<result.length(); i++){

                //GatedModel gatedModel = new GatedModel();
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString("newfiber");
                String b = jo.getString("oldfiber");
                String c = jo.getString("state");
                String d = jo.getString("migdate");
                String e = jo.getString("onplan");

                ModelActualFiber gatedModel = new ModelActualFiber();

                gatedModel.setNewc(a);
                gatedModel.setOld(b);
                gatedModel.setRegion(c);
                gatedModel.setMigdate(d);
                gatedModel.setPlan(e);

                listactual1.add(gatedModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final AdapterActualFiber sanoAdapter = new AdapterActualFiber(getApplicationContext(),R.layout.listnewactual,listactual1);
        final ListView sanoview = (ListView) findViewById(R.id.list);
        sanoview.setAdapter(sanoAdapter);

        listView.setAdapter(sanoAdapter);


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
                String s = rh.sendGetRequest(Config.URL_GET_ACTUAL_FIBER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}

