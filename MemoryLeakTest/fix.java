LiveFragment.java

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d();
        NoticeManager.getInstance(getActivity()).removeExpiredRequestCodesFromFile();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
        mLocalBroadcastManager.unregisterReceiver(mConnectivityActionReceiver);

        mPlayerController.detachMediaPlayer();
        mPlayerController.setOnClickClosedCaptionListenr(null);
        mPlayerController.bindPlayerPreparedStatusListener(null);
        mPlayerController.releasePlayer();

        mUpperProgramListAdapter.release();
        mUpperProgramListAdapter = null;

        mUpperProgramListContainer.removeAllViews();
        mUpperProgramListContainer.setAdapter(null);
        mUpperProgramListContainer.setLayoutManager(null);
        mUpperProgramListContainer = null;

        mLowerProgramListAdapter.release();
        mLowerProgramListAdapter = null;

        mLowerProgramListContainer.removeAllViews();
        mLowerProgramListContainer.setAdapter(null);
        mLowerProgramListContainer.setLayoutManager(null);
        mLowerProgramListContainer = null;
    }

LiveAdapter.java
    public void release() {
        mProgramItems.clear();
        notifyDataSetChanged();
        mContext = null;
        mLiveFragment = null;
        mProgramItems = null;
    }

printHeap

    private void printHeap() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                Log.v("Runtime", "==============================");
                Log.v("Runtime", "totalMemory[KB] = " + (int)(runtime.totalMemory()/1024) + " " + (int)(runtime.totalMemory()/1024/1024) + " [MB]");
                Log.v("Runtime", "freeMemory[KB] = " + (int)(runtime.freeMemory()/1024) + " " + (int)(runtime.freeMemory()/1024/1024) + " [MB]");
                Log.v("Runtime", "usedMemory[KB] = " + (int)( (runtime.totalMemory() - runtime.freeMemory())/1024) + " " + (int)((runtime.totalMemory() - runtime.freeMemory())/1024/1024) + " [MB]");
                Log.v("Runtime", "maxMemory[KB] = " + (int)(runtime.maxMemory()/1024) + " " + (int)(runtime.maxMemory()/1024/1024) + " [MB]");
                Log.v("Runtime", "==============================");

                printHeap();
            }
        }, 1000);
    }

PlayerController.java

    public void releasePlayer() {
        stopDebugViewTimer();
        mHandler.removeCallbacks(null);
        mMainPlaybackEventBinder.unbind(mMediaPlayer, mPlaybackEventListener);
        mMediaPlayer.removeEventListener(MediaPlayer.Event.QOS, mQOSEventListener);
        mMediaPlayer.release();
        mMediaPlayer = null;
        //AnalyticsUtil.trackPlayEndAction(mContext);
        mTimeOfPlayer.cancel();

        mCastButton.setOnClickListener(null);
        mBtnPlayPause.setOnClickListener(null);
        mPlayerControllerContainer.setOnClickListener(null);
        FrameLayout playerContainer = (FrameLayout) findViewById(R.id.player_container);
        playerContainer.setOnClickListener(null);
        mFullViewFrame.setOnClickListener(null);
        mBtnCC.setOnClickListener(null);
        mSystemUiHider.setOnVisibilityChangeListener(null);
        mPlayerStatusListener = null;
        CustomFrameLayout playerFlameLayout = (CustomFrameLayout) findViewById(R.id.program_mediaplayer_holder);
        playerFlameLayout.removeOnSizeChangeEventListener(null);

        mContext = null;
    }
