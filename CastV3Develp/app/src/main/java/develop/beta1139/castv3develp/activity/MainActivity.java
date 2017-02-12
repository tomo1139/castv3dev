package develop.beta1139.castv3develp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import develop.beta1139.castv3develp.R;
import develop.beta1139.castv3develp.cast.CastWrapper;

public class MainActivity extends AppCompatActivity {

    CastWrapper mCastWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCastWrapper = new CastWrapper(this);

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
