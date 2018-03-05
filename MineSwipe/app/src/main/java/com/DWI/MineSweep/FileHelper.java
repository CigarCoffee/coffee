package com.DWI.MineSweep;
import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper
	{
		protected String filename="";
		protected String filepath="";
		protected Context context;

		public FileHelper (Context context )
			{
				this.context = context;
			}

		public FileHelper ( String filename, Context context )
			{
				this.filename = filename;
				this.context = context;
			}

		public FileHelper setFilename ( String filename )
			{
				this.filename = filename;
				return this;
			}

		public String getFilename ( )
			{
				return filename;
			}

		public FileHelper setFilepath ( String filepath )
			{
				this.filepath = filepath;
				return this;
			}

		public String getFilepath ( )
			{
				return filepath;
			}
		private boolean write ( String contenr, Context context ) throws IOException
			{
				if (filepath != "")
					{
						File file=new File(filepath + "/" + filename);
						if (!file.exists()) file.mkdirs();
					}
				try
					{
						FileOutputStream output=context.openFileOutput(filename, context.MODE_APPEND);
					    output.write(contenr.getBytes());
						output.close();
					}
				catch (FileNotFoundException e)
					{
						e.printStackTrace();
						return false;
					}
				return true;
			}
		public boolean write ( String content ) throws IOException
			{
				if (!write(content, context)) return false;
				return true;
			}
		private String read(Context context) {
				
				try
					{
						FileInputStream inputStream= context.openFileInput(filename);
						ByteArrayOutputStream outStream=new ByteArrayOutputStream();             //缓存输出流
						byte[] buffer =new byte[1024];                                           //创建字节数组
						int len=0;
						while((len=inputStream.read(buffer))!=-1){                              //循环读取数据并且将数据写入到缓存输出流中
								outStream.write(buffer, 0, len);
							}
						return new String(outStream.toByteArray());
						}
				catch (Exception e)
					{e.printStackTrace();}                 //创建输入流
				return "error";
			}
		public String read() {
				try
					{
						if (!read(context).equals(""))
							{
								return read(context);
					 }
							
					}
				catch (Exception e)
					{e.printStackTrace();}
			    return "error";
			}

	}
