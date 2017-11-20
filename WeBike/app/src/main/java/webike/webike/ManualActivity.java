package webike.webike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import webike.webike.adaptadores.adapter_manual;
import webike.webike.logic.Tip;
import webike.webike.utils.FData;
import webike.webike.utils.Utils;

import static webike.webike.R.array.manual_titles_array;

public class ManualActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FData fData;
    private FirebaseDatabase database;

    ListView manual_list;
    private ArrayList<Tip> current_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        mAuth = FirebaseAuth.getInstance();
        fData = new FData();
        database = FirebaseDatabase.getInstance();

        manual_list = (ListView) findViewById(R.id.lv_manual);
        manual_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(ManualActivity.this, TipActivity.class);
                Bundle bundle = new Bundle();
                Tip tip = (Tip) current_tip.get(position);
                bundle.putSerializable("pub",tip);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
        loadTips();
    }

    public void loadTips(){
        String titulos[] = getResources().getStringArray(R.array.manual_titles_array);
        String descripcion[] = getResources().getStringArray(R.array.manual_array);;

        Tip tip_data[] = new Tip[]{
                new Tip("Tip 1", titulos[0], descripcion[0]),
                new Tip("Tip 2", titulos[1], descripcion[1]),
                new Tip("Tip 3", titulos[2], descripcion[2]),
                new Tip("Tip 4", titulos[3], descripcion[3]),
                new Tip("Tip 5", titulos[4], descripcion[4]),
                new Tip("Tip 6", titulos[5], descripcion[5]),
                new Tip("Tip 7", titulos[6], descripcion[6]),
        };
        current_tip = new ArrayList<Tip>(Arrays.asList(tip_data));
        adapter_manual adapter = new adapter_manual(this,new ArrayList<Tip>(Arrays.asList(tip_data)));
        this.manual_list.setAdapter(adapter);
    }
}
