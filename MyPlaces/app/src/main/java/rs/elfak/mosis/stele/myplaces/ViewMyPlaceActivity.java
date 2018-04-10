package rs.elfak.mosis.stele.myplaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rs.elfak.mosis.stele.myplaces.Classes.MyPlace;
import rs.elfak.mosis.stele.myplaces.Classes.MyPlacesData;

public class ViewMyPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_place);

        int position = getIntent().getIntExtra("position", -1);

        if(position>=0)
        {
            MyPlace place = MyPlacesData.getInstance().getPlace(position);
            TextView name = findViewById(R.id.name_text);
            TextView desc = findViewById(R.id.description_text);

            name.setText(place.getName());
            desc.setText(place.getDescription());
        }

        Button btnOK = findViewById(R.id.view_ok_button);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}