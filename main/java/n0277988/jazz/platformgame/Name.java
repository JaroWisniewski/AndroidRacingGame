package n0277988.jazz.platformgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Name extends Activity {
private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_name);
    }

    public void onConfirm(View v){
        EditText Name = (EditText) findViewById(R.id.userName);
        name = Name.getText().toString();
        Intent nameConfirmation = new Intent(this, Main_Menu.class);
        nameConfirmation.putExtra("NAME",name);
        setResult(Activity.RESULT_OK,nameConfirmation);
        finish();

    }
}
