package com.lm.cherry.server.javaee;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspFactory;

import org.apache.jasper.JspC;
import org.apache.jasper.runtime.JspFactoryImpl;
import org.apache.jasper.security.SecurityClassLoad;

import com.lm.cherry.conf.Entry;
import com.lm.cherry.conf.WorkConfig;
import com.lm.cherry.tool.CherryException;

/**
 * ͨ�������ļ������ü���app
 * ����:2015-5-19
 * ����:LM
 */
public class JspLoader {
	/**
	 * �����ļ���һ�α����ص��¼� �ж��ļ��Ƿ��޸Ĺ�
	 */
	private static Map<String,Long> jspModifyDate=new HashMap<String,Long>();
	/**
	 * ����jsp�Ķ���ʵ��
	 */
	private static Map<String,Object> jspObjects=new HashMap<String,Object>();
	/**
	 * ��ǰ�Ѿ����ص�jsp�ļ�
	 */
	private static Map<String,Jsp> jspList=new HashMap<String,Jsp>();
	
	
	/**
	 * ����j2eeloader����
	 */
	private Entry.J2EEApp ea;
	private JspC jspc = null; 
	private boolean ifInit=false;
	/**
	 * ��ʼ��jsp������
	 * @param ea
	 * @throws Exception
	 */
	public JspLoader(Entry.J2EEApp ea) throws Exception{
		this.ea=ea;
		this.jspc=new JspC();
	}
	/**
	 * ��ʼ��jsp�Ĵ�����
	 * @throws Exception
	 */
	public void init() throws Exception{
		if(!ifInit){
			String workPath=WorkConfig.getWorkConfPath();
			String compileOutPath=workPath+"/tmp/"+ea.getPath();
			//���༭��jsp��Ŀ¼���ص������·����
			ea.getJ2eeLoder().getCherryLoder().addClassPath(compileOutPath);
			//����ͨ��apache��JspFactoryImpl������jsp
			JspFactoryImpl factory = new JspFactoryImpl();
		    SecurityClassLoad.securityClassLoad(ea.getJ2eeLoder().getCherryLoder());
		    JspFactory.setDefaultFactory(factory);
		    ifInit=true;
		}
	}
	/**
	 * ����jsp ����jsp��������
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Object load(String url) throws Exception{
		init();
		String workPath=WorkConfig.getWorkConfPath();//��ȡweb�������ĸ�Ŀ¼
		String compileOutPath=workPath+"/tmp/"+ea.getPath();//��web�������ĸ�Ŀ¼�� ��һ�����������Ƶ�Ŀ¼
		File file=new File(compileOutPath);
		if(!file.exists()){
			file.mkdirs();
		}
		if(!url.endsWith(".jsp")){//�ж������url�Ƿ���jsp��β ��������׳��쳣
			throw new CherryException("��jsp�ļ����ܽ���");
		}
		String jspFile=url.substring(url.indexOf(ea.getPath())+ea.getPath().length());;//����·����ȥ�������� ��ʾjsp������·��
		File oriJsp=new File(ea.getDir()+jspFile);//��ȡ��Ŀ�ľ���·������jsp��·�� ����jsp���ڵ�λ����
		Object jspObject=null;//ʵ�������jsp��Ķ��� 
		Jsp jsp=jspList.get(url);
		if(jsp==null){
			jsp=new Jsp(jspFile,ea.getDir());
			jspList.put(url, jsp);
		}
		//���jsp����0 ��ֹͬʱ����������ͬ������ͬʱ���� jsp�ı�������
		synchronized (jsp) {
			if(jspModifyDate.containsKey(url)){//�жϵ�ǰurl�Ƿ��Ѿ����ع�jsp������
				Long preDate=jspModifyDate.get(url);//���ع� ȡ�����ص�ʱ���
				if(oriJsp.lastModified()>preDate){//����ļ�������޸����� ����ʱ��� ��˵���ļ����޸Ĺ� ��Ҫ���±���
					jspc.setUriroot(ea.getDir());//webӦ�õ�rootĿ¼  
			        jspc.setOutputDir(compileOutPath);//.java�ļ���.class�ļ������Ŀ¼  
			        jspc.setJspFiles(jspFile);//Ҫ�����jsp  
			        jspc.setCompile(true);//�Ƿ���� false��ָ���Ļ�ֻ����.java�ļ�  
			        jspc.execute();  
			        jspModifyDate.put(url, oriJsp.lastModified());//������� ���뻺����
				}else{
					jspObject=jspObjects.get(url);//���û���޸Ĺ� ֱ��ȡ�������е�jspObject 
				}
			}else{//�����ǰû����� ֱ�ӱ��� ���뻺����
				jspc.setUriroot(ea.getDir());//webӦ�õ�rootĿ¼  
		        jspc.setOutputDir(compileOutPath);//.java�ļ���.class�ļ������Ŀ¼  
		        jspc.setJspFiles(jspFile);//Ҫ�����jsp  
		        jspc.setCompile(true);//�Ƿ���� false��ָ���Ļ�ֻ����.java�ļ�  
		        jspc.execute();
		        jspModifyDate.put(url, oriJsp.lastModified());
			}
		}
		if(jspObject==null){//���û�л�ȡ��jsp���� ����ͨ�������classloader���ص�ǰjsp��������class��
			String jspClassStr="org.apache.jsp"+jspFile.replace(".", "_").replace("/", ".");
			//URL[] jarUrl=ea.getJ2eeLoder().getServletContext().getResourcePaths(ea.getDir()+"/WEB-INF/lib");
			URLClassLoader ucl=new URLClassLoader(new URL[]{new File(compileOutPath).toURL()},ea.getJ2eeLoder().getCherryLoder());//ͨ��URLClassLoader���� 
			
			Class jspClass=ucl.loadClass(jspClassStr);//������
			jspObject=jspClass.newInstance();//ʵ��������
			jspObjects.put(url,jspObject);//���õ������� �´� ���û�б��޸ľ�ֱ�Ӵӻ����ȡ
		}
		return jspObject;
	}
	
}
