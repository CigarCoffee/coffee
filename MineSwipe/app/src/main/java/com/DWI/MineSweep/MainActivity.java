package com.DWI.MineSweep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.DWI.MineSweep.R;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.JellyTypes.Jelly;
import com.nightonke.jellytogglebutton.State;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    private static final int START = 0x123;
    private static final int END = 0x124;
	Bitmap bk=null;
	String TAG="Mine";
    boolean flagSwicth = false;
    int time = 0;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == START) {
                time++;
            } else if (msg.what == END) {
                time = 0;
            }
            timeView.setText(time + "S");
        }
    };
    MineView mineView;
    TextView  timeView, num_view,refreshView;
	ShimmerFrameLayout shi;
    Timer timer;
	int width,height;
	private NumberProgressBar progress;
		
		

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null){
            getActionBar().hide();
        }
        setContentView(R.layout.activity_main);
		
		
		init();
		shimmer();
        refreshView = (TextView) findViewById(R.id.refresh_view);
        timeView = (TextView) findViewById(R.id.time_view);
        num_view = (TextView) findViewById(R.id.num_view);
        final JellyToggleButton  flagView = (JellyToggleButton) findViewById(R.id.flag_view);
		flagView.setLeftBackgroundColor("#ff7f92");
		flagView.setRightBackgroundColor("#ca88fb");
		flagView.setAlpha(0.9f);
		flagView.setLeftThumbColor("#ca88fb");
		flagView.setRightThumbColor("#ff7f92");
		flagView.setChecked(false);
		flagView.setText("扫雷","插旗");
		
		flagView.setTextSize(40);
		flagView.setTextColor(Color.WHITE);
		flagView.setJelly(Jelly.RANDOM);
		
		
		progress=(NumberProgressBar)findViewById(R.id.progress_view);
		progress.setProgress(0);

        refreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(END);
				mineView.fresh(width,height);
                
            }
        });
			flagView.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener(){

						@Override
						public void onStateChange ( float process, State state, JellyToggleButton jtb )
							{
								// TODO: Implement this method
								if(state.equals(state.LEFT)||state.equals(state.RIGHT_TO_LEFT)){ flagSwicth=false;
								}else{flagSwicth=true;}
								mineView.setFlagSwitch(flagSwicth);
							}
						
			
		});
        mineView.setSignal(new MineView.Signal() {
            @Override
            public void onStart() {
                if (mineView.getStarted()) {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(START);
                        }
                    }, 1000, 1000);

                }
            }

            @Override
            public void onFinish() {
                timer.cancel();
				
            }

            @Override
            public void onFlag(int num) {
                num_view.setText(num + "");
				int i=width*height/8;
				int j=100/i;
				progress.setProgress((i-num)*j);
            }
        });
		
	

}
	@Override
	protected void onDestroy ( )
		{
			// TODO: Implement this method
			bk.recycle();
			mineView.ondes();
			System.gc();
			super.onDestroy();
		}

	@Override
	public void onBackPressed ( )
		{
			// TODO: Implement this method
			Intent back=new Intent(MainActivity.this,Login.class);
			back.putExtra("ins","back");
			startActivity(back);
			finish();
			super.onBackPressed();
		}
	private void init(){
			LinearLayout lay=(LinearLayout) findViewById(R.id.activity_main);
			bk=BitmapFactory.decodeResource(getResources(),R.drawable.back);
			BitmapHelper bith=new BitmapHelper(this);
			bk=bith.setbitmap(bk).getscaledbitmap();
			bith.setbitmap(bk);
			bk=bith.getscaledbitmap();
			lay.setBackground(new BitmapDrawable(bk));
			bith.recycle();

			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			mineView = (MineView) findViewById(R.id.mine_view);
			width=getIntent().getExtras().get("width");
			height=getIntent().getExtras().get("height");
			if(width*height!=0){
					Log.d(TAG,"map advance");
					mineView.fresh(width,height);

				}
	}
	private void shimmer(){
		shi=(ShimmerFrameLayout)findViewById(R.id.mainshimmer);
		shi.setDuration(2500);
		shi.startShimmerAnimation();
	}
	
    
}
