package develop.beta1139.castv3develp.cast;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.widget.MiniControllerFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.images.WebImage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import develop.beta1139.castv3develp.D;
import develop.beta1139.castv3develp.R;

/**
 * Created by tomo on 2017/02/12.
 */

public class CastWrapper {
    private CastContext mCastContext;
    private MediaRouteButton mMediaRouteButton;

    private CastSession mCastSession;
    private SessionManagerListener mSessionManagerListener;
    private CastStateListener mCastStateListener;

    private IntroductoryOverlay mIntroductoryOverlay;

    private Activity mActivity;
    private Context mContext;

    private CustomChannel mCustomChannel;

    public CastWrapper(Activity activity) {
        mActivity = activity;
        mContext = mActivity.getApplicationContext();
        init();
    }

    private void init() {
        mCastContext = CastContext.getSharedInstance(mContext);
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();

        mMediaRouteButton = (MediaRouteButton) mActivity.findViewById(R.id.media_route_button);
        CastButtonFactory.setUpMediaRouteButton(mContext, mMediaRouteButton);

        mCastStateListener = new CastStateListener() {
            @Override
            public void onCastStateChanged(int newState) {
                if (newState == CastState.NO_DEVICES_AVAILABLE) {
                    D.p("");
                    mMediaRouteButton.setVisibility(View.GONE);
                } else {
                    D.p("");
                    mMediaRouteButton.setVisibility(View.VISIBLE);
                    showIntroductoryOverlay();
                }
            }
        };

        mCustomChannel = new CustomChannel();

        setupCastListener();
    }

    public void onResume() {
        mCastContext.addCastStateListener(mCastStateListener);
        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(mContext).getSessionManager().getCurrentCastSession();
            try {
                if (mCastSession != null) {
                    D.p("setMessageReceivedCallbacks");
                    mCastSession.setMessageReceivedCallbacks(mCustomChannel.getNamespace(), mCustomChannel);
                }
            } catch (IOException e) {
                D.p("setMessageReceivedCallbacks error");
            }
        }
    }

    public void onPause() {
        mCastContext.removeCastStateListener(mCastStateListener);
        mCastContext.getSessionManager().removeSessionManagerListener(
                mSessionManagerListener, CastSession.class);
    }

    private void setupCastListener() {
        mSessionManagerListener = new SessionManagerListener<CastSession>() {

            @Override
            public void onSessionEnded(CastSession session, int error) {
                D.p("");
                onApplicationDisconnected();
            }

            @Override
            public void onSessionResumed(CastSession session, boolean wasSuspended) {
                D.p("");
                onApplicationConnected(session);
            }

            @Override
            public void onSessionResumeFailed(CastSession session, int error) {
                D.p("");
                onApplicationDisconnected();
            }

            @Override
            public void onSessionStarted(CastSession session, String sessionId) {
                D.p("");
                onApplicationConnected(session);
            }

            @Override
            public void onSessionStartFailed(CastSession session, int error) {
                D.p("");
                onApplicationDisconnected();
            }

            @Override
            public void onSessionStarting(CastSession session) {
                D.p("");
            }

            @Override
            public void onSessionEnding(CastSession session) {
                D.p("");
            }

            @Override
            public void onSessionResuming(CastSession session, String sessionId) {
                D.p("");
            }

            @Override
            public void onSessionSuspended(CastSession session, int reason) {
                D.p("");
            }

            private void onApplicationConnected(CastSession castSession) {
                D.p("");
                mCastSession = castSession;
                try {
                    if (mCastSession != null) {
                        D.p("setMessageReceivedCallbacks");
                        mCastSession.setMessageReceivedCallbacks(mCustomChannel.getNamespace(), mCustomChannel);
                    }
                } catch (IOException e) {
                    D.p("setMessageReceivedCallbacks error");
                }
                load();
            }

            private void onApplicationDisconnected() {
                D.p("");
            }
        };
    }

    public void load() {
        if (mCastSession == null) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
        if (remoteMediaClient == null) {
            return;
        }

        MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
        movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, "sub title");
        movieMetadata.putString(MediaMetadata.KEY_TITLE, "title");
        movieMetadata.addImage(new WebImage(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/480x270/DesigningForGoogleCast2-480x270.jpg")));
        movieMetadata.addImage(new WebImage(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/780x1200/DesigningForGoogleCast-887x1200.jpg")));
        //final MediaInfo mediaInfo = new MediaInfo.Builder("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/hls/DesigningForGoogleCast.m3u8")

        MediaTrack englishSubtitle = new MediaTrack.Builder(1 /* ID */,
                MediaTrack.TYPE_TEXT)
                .setName("English")
                .setSubtype(MediaTrack.SUBTYPE_CAPTIONS)
                .setLanguage("en-US")
                .build();

        List<MediaTrack> list = new ArrayList<>();
        list.add(englishSubtitle);

        final MediaInfo mediaInfo = new MediaInfo.Builder("http://playertest.longtailvideo.com/adaptive/captions/playlist.m3u8")
                .setStreamType(MediaInfo.STREAM_TYPE_LIVE)
                .setContentType("application/x-mpegurl")
                .setMetadata(movieMetadata)
                .setMediaTracks(list)
                .build();
        AppCompatActivity activity = (AppCompatActivity) mActivity;
        android.support.v4.app.Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.castMiniController);
        MiniControllerFragment miniControllerFragment = (MiniControllerFragment) fragment;
        LinearLayout linearLayout = (LinearLayout) miniControllerFragment.getView();
        final RelativeLayout relativeLayout = (RelativeLayout) linearLayout.getChildAt(1);
        for (int i=0; i<relativeLayout.getChildCount(); i++) {
            D.p("view(" + i + "): " + relativeLayout.getChildAt(i));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.icon_view);
                Picasso.with(mContext).load(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/480x270/DesigningForGoogleCast2-480x270.jpg")).into(imageView);
            }
        }, 5000);

        remoteMediaClient.load(mediaInfo, true, 0);
    }

    public static boolean checkGooglePlayServices(final Activity activity) {
        final int googlePlayServicesCheck = GooglePlayServicesUtil.isGooglePlayServicesAvailable(
                activity);
        switch (googlePlayServicesCheck) {
            case ConnectionResult.SUCCESS:
                return true;
            default:
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(googlePlayServicesCheck,
                        activity, 0);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        activity.finish();
                    }
                });
                dialog.show();
        }
        return false;
    }

    private void showIntroductoryOverlay() {
        if (mIntroductoryOverlay != null) {
            mIntroductoryOverlay.remove();
        }
        if ((mMediaRouteButton != null) && mMediaRouteButton.getVisibility() == View.VISIBLE) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mIntroductoryOverlay = new IntroductoryOverlay.Builder(
                            mActivity, mMediaRouteButton)
                            .setTitleText("Introducing Cast")
                            .setOverlayColor(R.color.colorPrimary)
                            .setSingleTime()
                            .setOnOverlayDismissedListener(
                                    new IntroductoryOverlay.OnOverlayDismissedListener() {
                                        @Override
                                        public void onOverlayDismissed() {
                                            mIntroductoryOverlay = null;
                                        }
                                    })
                            .build();
                    mIntroductoryOverlay.show();
                }
            });
        }
    }

    public void enableCaption() {
        if (mCastSession != null) {
            mCustomChannel.sendMessage(mCastSession, "enableCaption");
        }
    }

    public void disableCaption() {
        if (mCastSession != null) {
            mCustomChannel.sendMessage(mCastSession, "disableCaption");
        }
    }
}
