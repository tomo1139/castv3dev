package develop.beta1139.castv3develp.cast;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import develop.beta1139.castv3develp.D;

/**
 * Created by tomo on 2017/02/19.
 */

class CustomChannel implements Cast.MessageReceivedCallback {
    private static final String CAST_NAMESPACE = "urn:x-cast:develop.beta1139.castv3develp.cast:android-phone";

    public String getNamespace() {
        return CAST_NAMESPACE;
    }
    @Override
    public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
        D.p("message: " + message);
    }

    public void sendMessage(CastSession castSession, String message) {
        D.p("message: " + message);
        try {
            castSession.sendMessage(getNamespace(), message)
                    .setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status result) {
                                    D.p("result: " + result.toString());
                                }
                            });
        } catch (Exception e) {
            D.p(e.toString());
        }
    }
}

