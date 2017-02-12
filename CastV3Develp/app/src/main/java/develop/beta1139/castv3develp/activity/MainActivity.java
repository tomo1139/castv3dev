package develop.beta1139.castv3develp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.MediaRouteButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.MediaUtils;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.images.WebImage;

import develop.beta1139.castv3develp.D;
import develop.beta1139.castv3develp.R;

public class MainActivity extends AppCompatActivity {

    private CastContext mCastContext;
    private MediaRouteButton mMediaRouteButton;

    private CastSession mCastSession;
    private SessionManager mSessionManager;
    private SessionManagerListener mSessionManagerListener;
    private PlaybackLocation mLocation;
    private PlaybackState mPlaybackState;
    private CastStateListener mCastStateListener;
    //private MenuItem mediaRouteMenuItem;

    private IntroductoryOverlay mIntroductoryOverlay;

    public enum PlaybackLocation {
        LOCAL,
        REMOTE
    }

    public enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        D.p("");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCastStateListener = new CastStateListener() {
            @Override
            public void onCastStateChanged(int newState) {
                if (newState == CastState.NO_DEVICES_AVAILABLE) {
                    mMediaRouteButton.setVisibility(View.GONE);
                } else {
                    D.p("");
                    mMediaRouteButton.setVisibility(View.VISIBLE);
                    showIntroductoryOverlay();
                }
            }
        };
        mMediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), mMediaRouteButton);

        setupCastListener();
        mCastContext = CastContext.getSharedInstance(this);
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(this, savedInstanceState);
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.browse, menu);
        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu,
                R.id.media_route_menu_item);
        return true;
    }
    */

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
                loadRemoteMedia(0, true);
            }

            private void onApplicationDisconnected() {
                D.p("");
                updatePlaybackLocation(PlaybackLocation.LOCAL);
                mPlaybackState = PlaybackState.IDLE;
                mLocation = PlaybackLocation.LOCAL;
            }
        };
    }

    private void updatePlaybackLocation(PlaybackLocation location) {
        D.p("");
        mLocation = location;
        if (location == PlaybackLocation.LOCAL) {
            if (mPlaybackState == PlaybackState.PLAYING
                    || mPlaybackState == PlaybackState.BUFFERING) {
            } else {
            }
        } else {
        }
    }

    private void loadRemoteMedia(int position, boolean autoPlay) {
        D.p("");
        if (mCastSession == null) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
        if (remoteMediaClient == null) {
            return;
        }
        remoteMediaClient.addListener(new RemoteMediaClient.Listener() {
            @Override
            public void onStatusUpdated() {
                D.p("");
                /*
                Intent intent = new Intent(MainActivity.this, ExpandedControlsActivity.class);
                startActivity(intent);
                remoteMediaClient.removeListener(this);
                */
            }

            @Override
            public void onMetadataUpdated() {
                D.p("");
            }

            @Override
            public void onQueueStatusUpdated() {
                D.p("");
            }

            @Override
            public void onPreloadStatusUpdated() {
                D.p("");
            }

            @Override
            public void onSendingRemoteMediaRequest() {
                D.p("");
            }
        });
        remoteMediaClient.load(buildMediaInfo(), autoPlay, position);
    }

    private MediaInfo buildMediaInfo() {
        MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

        movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, "sub title");
        movieMetadata.putString(MediaMetadata.KEY_TITLE, "title");
        movieMetadata.addImage(new WebImage(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/480x270/DesigningForGoogleCast2-480x270.jpg")));
        movieMetadata.addImage(new WebImage(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/780x1200/DesigningForGoogleCast-887x1200.jpg")));

        return new MediaInfo.Builder("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/hls/DesigningForGoogleCast.m3u8")
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType("application/x-mpegur")
                .setMetadata(movieMetadata)
                .build();
    }

    @Override
    protected void onResume() {
        mCastContext.addCastStateListener(mCastStateListener);
        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(this).getSessionManager()
                    .getCurrentCastSession();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        mCastContext.removeCastStateListener(mCastStateListener);
        mCastContext.getSessionManager().removeSessionManagerListener(
                mSessionManagerListener, CastSession.class);
        super.onPause();
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
                            MainActivity.this, mMediaRouteButton)
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
}
