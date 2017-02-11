package develop.beta1139.castv3develp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.cast.framework.CastContext;

import develop.beta1139.castv3develp.R;

public class MainActivity extends AppCompatActivity {

    private CastContext mCastContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCastContext = CastContext.getSharedInstance(this);
    }
}
