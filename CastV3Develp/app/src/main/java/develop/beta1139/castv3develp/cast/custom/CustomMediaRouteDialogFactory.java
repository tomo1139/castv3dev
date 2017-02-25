package develop.beta1139.castv3develp.cast.custom;

import android.support.v7.app.MediaRouteDialogFactory;

/**
 * Created by tomo on 2017/02/25.
 */

public class CustomMediaRouteDialogFactory extends MediaRouteDialogFactory {

    private static final CustomMediaRouteDialogFactory sDefault = new CustomMediaRouteDialogFactory();

    public CustomMediaRouteDialogFactory() {
        super();
    }

    public static CustomMediaRouteDialogFactory getDefault() {
        return sDefault;
    }

    @Override
    public CustomMediaRouteControllerDialogFragment onCreateControllerDialogFragment() {
        return new CustomMediaRouteControllerDialogFragment();
    }
}
