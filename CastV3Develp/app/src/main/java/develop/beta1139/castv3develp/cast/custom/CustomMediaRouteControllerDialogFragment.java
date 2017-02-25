package develop.beta1139.castv3develp.cast.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.MediaRouteControllerDialog;
import android.support.v7.app.MediaRouteControllerDialogFragment;

/**
 * Created by tomo on 2017/02/25.
 */

public class CustomMediaRouteControllerDialogFragment extends MediaRouteControllerDialogFragment {
    private CustomMediaRouteControllerDialog mDialog;

    public CustomMediaRouteControllerDialogFragment() {
        super();
        setCancelable(true);
    }

    @Override
    public MediaRouteControllerDialog onCreateControllerDialog(Context context, Bundle savedInstanceState) {
        mDialog = new CustomMediaRouteControllerDialog(context);
        mDialog.setVolumeControlEnabled(false);
        return mDialog;
    }

    public CustomMediaRouteControllerDialog getControllerDialog() {
        return mDialog;
    }
}
