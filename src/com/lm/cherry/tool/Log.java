package com.lm.cherry.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;

/**
 * 
 * ����:LM
 * ��������:2015-1-5����10:45:11
 * ��Ŀ����:cherry
 * ������:com.lm.cherry.util
 * �ļ���:Log.java
 */
public class Log {
	public static final int LOG_DEBUG=0;
	public static final int LOG_INFO=1;
	public static final int LOG_ERROR=2;
	private int logLevel=1;
	private boolean enableLog=true;
	private String logOut="syso";
	private String logPath="/log.log";
	private String realLogPath=logPath;
	private String workPath="";
	FileWriter output =null;
	private boolean ifSyso=false;
	private static final DateFormat format = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss");
	public Log(Entry.LogC log){
		try {
			if(log!=null){
				init(log.getLogLevel(), "true".equals(log.getEnableLog())?true:false, log.getLogOut(), log.getLogPath());
			}else{
				enableLog=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ʼ����־�Ĳ���
	 * ����:LM
	 * ��������:2015-1-5����11:27:21
	 * @param logLevel ��־�ļ���
	 * @param enableLog �Ƿ�������־
	 * @param logOut ��־�����
	 * @param logPath ��־����ļ�
	 * @throws Exception 
	 */
	public void init(String logLevel,boolean enableLog,String logOut,String logPath) throws Exception{
		this.logLevel=logLevel.equals("debug")?LOG_DEBUG:(logLevel.equals("error")?LOG_ERROR:LOG_INFO);
		this.enableLog=enableLog;
		this.logOut=logOut;
		this.logPath=logPath;
		this.workPath=WorkConfig.getWorkConfPath();
		//���������־ ������� ����file��� ���Դ����ļ�
		if(this.enableLog && this.logOut.indexOf("file")>=0){
			parseLogPath();
			output=new FileWriter(new File(this.realLogPath), true);
		}
		if(this.enableLog && this.logOut.indexOf("syso")>=0){
			this.ifSyso=true;
		}
		
	}
	/**
	 * �ر���־���
	 * ����:LM
	 * ��������:2015-1-5����11:48:00
	 * @throws IOException
	 */
	public void shutdown() throws IOException{
		if(this.output!=null){
			this.output.close();
		}
	}
	/**
	 * �滻��־�ļ�����Ϣ
	 * ����:LM
	 * ��������:2015-1-5����11:42:25
	 */
	public void parseLogPath(){
		String logFile=this.logPath;
		if(this.logPath.startsWith("/")){
			logFile=workPath+this.logPath;
		}
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);		
		logFile=logFile.replaceAll("!yyyy!",calendar.get(Calendar.YEAR)+"");
		logFile=logFile.replaceAll("!MM!",(calendar.get(Calendar.MONTH)+1)+"");
		logFile=logFile.replaceAll("!dd!",calendar.get(Calendar.DATE)+"");
		logFile=logFile.replaceAll("!hh!",calendar.get(Calendar.HOUR)+"");
		logFile=logFile.replaceAll("!mm!",calendar.get(Calendar.MINUTE)+"");
		logFile=logFile.replaceAll("!ss!",calendar.get(Calendar.SECOND)+"");
		this.realLogPath=logFile;
	}
	/**
	 * ��ȡ��־����ĸ���� 
	 * ����:LM
	 * ��������:2015-1-5����11:55:35
	 * @param model
	 * @return
	 */
	public String getModel(int model){
		return (model==0?"DEBUG":(model==1?"INFO":"ERROR"));
	}
	/**
	 * ����̨���
	 * ����:LM
	 * ��������:2015-2-3����09:24:54
	 * @param msg
	 */
	public void syso(String msg){
		System.out.println(msg);
	}
	/**
	 * �жϵ�ǰ�Ƿ�֧������� model�� ���綨���model�� info  ��������debug �򷵻�false
	 * ����:LM
	 * ��������:2015-3-4����01:24:27
	 * @param model
	 * @return
	 */
	public boolean ifSupportModel(int model){
		if(model>=logLevel){
			return true;
		}
		return false;
	}
	/**
	 * �����־
	 * ����:LM
	 * ��������:2015-1-5����11:55:46
	 * @param msg
	 * @param model
	 */
	private synchronized void log(String msg,int model){
		if(enableLog){
			try {
				msg=format.format(new Date())+" ["+getModel(model)+"] "+msg;
				if(ifSupportModel(model)){
					if(output!=null){
						output.write(msg+"\r\n");
						output.flush();
					}
					if(ifSyso){
						System.out.println(msg);
					}
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public  void debug(String msg){
		log(msg,Log.LOG_DEBUG);
	}
	public  void info(String msg){
		log(msg,Log.LOG_INFO);
	}
	public  void error(String msg){
		log(msg,Log.LOG_ERROR);
	}
}
