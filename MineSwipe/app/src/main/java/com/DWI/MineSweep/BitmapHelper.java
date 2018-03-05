package com.DWI.MineSweep;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

public class BitmapHelper
{
	
	private Context mcontext=null;
	private BitmapFactory.Options option=null;
	private Bitmap mbitmap;
	private int scaled_width;
	private int scaled_height;

	public BitmapHelper ( Context mcontext)
			{
				this.mcontext = mcontext;
			}

	private BitmapHelper ()
			{
		}
	public Context getcontext ( )
		{
			return mcontext;
		}

	public BitmapHelper setOption ( BitmapFactory.Options option )
		{
			this.option = option;
			return this;
		}

	public BitmapFactory.Options getOption ( )
		{
			return option;
		}

	public BitmapHelper setbitmap ( Bitmap mbitmap )
		{
			this.mbitmap = mbitmap;
			return this;
		}

	public Bitmap getbitmap ( )
		{
			return mbitmap;
		}
	
	public BitmapFactory.Options getdefaultoptions(){
		BitmapFactory.Options op=new BitmapFactory.Options();
		op.inPreferredConfig=Bitmap.Config.RGB_565;
		return op;
	}
	public Bitmap getscaledbitmap(){
		Bitmap bit;
		if(mcontext!=null&&mbitmap!=null){
			DisplayMetrics m=mcontext.getResources().getDisplayMetrics();
			scaled_width=m.widthPixels;
			scaled_height=m.heightPixels;
			bit=Bitmap.createScaledBitmap(mbitmap,scaled_width,scaled_height,true);
		    return bit;
		}
		return null;
	}
	
	public void recycle(){
		mcontext=null;
	}
}
