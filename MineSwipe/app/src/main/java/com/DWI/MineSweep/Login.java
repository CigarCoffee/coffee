package com.DWI.MineSweep;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.io.File;

public class Login extends Activity
	{
		final String TAG="Mine";
		int wwidth,wheight;
		String filename="config.txt";
		Bitmap bk=null;
		Boolean extramode=false;

		@Override
		protected void onCreate ( Bundle savedInstanceState )
			{

				super.onCreate(savedInstanceState);
				if (getActionBar() != null)
					{
						getActionBar().hide();
					}
				File config=new File(getFilesDir()+"/config.txt");
				FileHelper h=new FileHelper(this).setFilename("record.txt");
				if(config.exists()) extramode=true;


				setContentView(R.layout.log);

				final SweetAlertDialog log2=new SweetAlertDialog(this).setTitleText("更新").setContentText("  地图可以不用输入默认匹配咯～超内存优化～游戏也可以正常通了～ ps:游戏中按返回键可以重新设置地图 如果你有返回键的话(笑)");
//				final SweetAlertDialog log1=new SweetAlertDialog(this).setTitleText("生日快乐～")
//					.setContentText("");

				wwidth = getWindowManager().getDefaultDisplay().getWidth();
				wheight = getWindowManager().getDefaultDisplay().getHeight();
				try
					{
						ShimmerFrameLayout shi=(ShimmerFrameLayout)findViewById(R.id.ShimmerFrameLayout);
						shi.setDuration(1500);
						shi.setRepeatMode(ObjectAnimator.RESTART);
						shi.startShimmerAnimation();
						BitmapFactory.Options op=new BitmapFactory.Options();
						op.inPreferredConfig = Bitmap.Config.RGB_565;
						bk = BitmapFactory.decodeResource(getResources(), R.drawable.back, op);
						bk = Bitmap.createScaledBitmap(bk, wwidth, wheight, true);
						RelativeLayout lay=(RelativeLayout)findViewById(R.id.logRelativeLayout);
						lay.setBackground(new BitmapDrawable(bk));
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}
				int j=(wheight - 80) / (wwidth / 15);
				TextView view =(TextView) findViewById(R.id.logEdit_width);
				view.setHint("最佳宽度为15哦");
				view = (TextView)findViewById(R.id.logEdit_height);
				view.setHint("最佳高度为" + j + "哦～");
				try{
					if(getIntent().getExtras().get("ins").equals("back")) {}
			
					}catch(Exception e){
						e.printStackTrace();
							//log1.show();
							log2.show();
					}

				



			}
		public void click_Ok ( View v )
			{
				Intent intent=new Intent(Login.this, MainActivity.class);
				String width,hight;

				int i=15,j=(wheight - 80) / (wwidth / 15) - 1;
				TextView view =(TextView) findViewById(R.id.logEdit_width);
		        width = view.getText().toString();
				view = (TextView)findViewById(R.id.logEdit_height);
				hight = view.getText().toString();
				try
					{
						i = Integer.parseInt(width);
						j = Integer.parseInt(hight);
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}

				if (extramode||i * j >= 100)
					{
						intent.putExtra("width", i);
						intent.putExtra("height", j);
						YoYo.with(Techniques.FadeOut)
						    .duration(1500).playOn(findViewById(R.id.logButton1));
						startActivity(intent);
						Log.d(TAG, "start mainactivity");
						finish();
					}
				else
					{
						new AlertDialog.Builder(this)
							.setMessage("宽高应为2位数，别把自己屏幕撑爆了233(づ ●─● )づ")
							.setCancelable(false)
							.setPositiveButton("ok", new DialogInterface.OnClickListener() {
									@Override
									public void onClick ( DialogInterface dialog, int which )
										{
										}
								})
							.create()
							.show();
					}
					
			}

		@Override
		protected void onDestroy ( )
			{
				// TODO: Implement this method
				bk.recycle();
				System.gc();
				super.onDestroy();
			}


	}
