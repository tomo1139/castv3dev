package develop.beta1139.castv3develp.cast.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.MediaRouteControllerDialog;
import android.view.LayoutInflater;
import android.view.View;

import develop.beta1139.castv3develp.R;

/**
 * Created by tomo on 2017/02/25.
 */

public class CustomMediaRouteControllerDialog extends MediaRouteControllerDialog {
    public CustomMediaRouteControllerDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateMediaControlView(Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_media_route_controller_dialog_fragment, null);
        return customView;
    }
}
