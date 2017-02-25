package develop.beta1139.castv3develp.cast.custom;

import android.content.Context;
import android.support.v7.app.MediaRouteActionProvider;

/**
 * Created by tomo on 2017/02/25.
 */

public class CustomMediaRouteActionProvider extends MediaRouteActionProvider {

    public CustomMediaRouteActionProvider(Context context) {
        super(context);
        setDialogFactory(CustomMediaRouteDialogFactory.getDefault());
    }
}
