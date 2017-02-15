package develop.beta1139.castv3develp.activity;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.cast.framework.media.widget.MiniControllerFragment;

import develop.beta1139.castv3develp.R;
import develop.beta1139.castv3develp.cast.CastWrapper;

public class MainActivity extends AppCompatActivity {

    CastWrapper mCastWrapper;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCastWrapper = new CastWrapper(this);

        mButton = (Button) findViewById(R.id.cast_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCastWrapper.load();
            }
        });

        CastWrapper.checkGooglePlayServices(this);
    }

    @Override
    protected void onResume() {
        mCastWrapper.onResume();

        super.onResume();
    }

    @Override
    protected void onPause() {
        mCastWrapper.onPause();
        super.onPause();
    }

}
