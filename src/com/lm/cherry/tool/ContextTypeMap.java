package com.lm.cherry.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileTypeMap;
import javax.swing.JFileChooser;
/**
 * context Type的操作类
 * 作者:LM
 * 创建日期:2015-1-28下午02:11:24
 * 项目名称:cherry
 * 包名称:com.lm.cherry.util
 * 文件名:ContextTypeMap.java
 */
public class ContextTypeMap extends HashMap{
		/**
		 * 枚举到所有的mini context类型
		 */
	
	    static Map<String,String> ctmMap=new HashMap<String,String>();
	    static{
	    	ctmMap.put(".*", "application/octet-stream");

	    	ctmMap.put(".001","application/x-001");

	    	ctmMap.put(".301","application/x-301");

	    	ctmMap.put(".323","text/h323");

	    	ctmMap.put(".906","application/x-906");

	    	ctmMap.put(".907","drawing/907");

	    	ctmMap.put(".a11","application/x-a11");

	    	ctmMap.put(".acp","audio/x-mei-aac");

	    	ctmMap.put(".ai","application/postscript");

	    	ctmMap.put(".aif","audio/aiff");

	    	ctmMap.put(".aifc","audio/aiff");

	    	ctmMap.put(".aiff","audio/aiff");

	    	ctmMap.put(".anv","application/x-anv");

	    	ctmMap.put(".asa","text/asa");

	    	ctmMap.put(".asf","video/x-ms-asf");

	    	ctmMap.put(".asp","text/asp");

	    	ctmMap.put(".asx","video/x-ms-asf");

	    	ctmMap.put(".au","audio/basic");

	    	ctmMap.put(".avi","video/avi");

	    	ctmMap.put(".awf","application/vnd.adobe.workflow");

	    	ctmMap.put(".biz","text/xml");

	    	ctmMap.put(".bmp","application/x-bmp");

	    	ctmMap.put(".bot","application/x-bot");

	    	ctmMap.put(".c4t","application/x-c4t");

	    	ctmMap.put(".c90","application/x-c90");

	    	ctmMap.put(".cal","application/x-cals");

	    	ctmMap.put(".cat","application/vnd.ms-pki.seccat");

	    	ctmMap.put(".cdf","application/x-netcdf");

	    	ctmMap.put(".cdr","application/x-cdr");

	    	ctmMap.put(".cel","application/x-cel");

	    	ctmMap.put(".cer","application/x-x509-ca-cert");

	    	ctmMap.put(".cg4","application/x-g4");

	    	ctmMap.put(".cgm","application/x-cgm");

	    	ctmMap.put(".cit","application/x-cit");

	    	ctmMap.put(".class","java/*");

	    	ctmMap.put(".cml","text/xml");

	    	ctmMap.put(".cmp","application/x-cmp");

	    	ctmMap.put(".cmx","application/x-cmx");

	    	ctmMap.put(".cot","application/x-cot");

	    	ctmMap.put(".crl","application/pkix-crl");

	    	ctmMap.put(".crt","application/x-x509-ca-cert");

	    	ctmMap.put(".csi","application/x-csi");

	    	ctmMap.put(".css","text/css");

	    	ctmMap.put(".cut","application/x-cut");

	    	ctmMap.put(".dbf","application/x-dbf");

	    	ctmMap.put(".dbm","application/x-dbm");

	    	ctmMap.put(".dbx","application/x-dbx");

	    	ctmMap.put(".dcd","text/xml");

	    	ctmMap.put(".dcx","application/x-dcx");

	    	ctmMap.put(".der","application/x-x509-ca-cert");

	    	ctmMap.put(".dgn","application/x-dgn");

	    	ctmMap.put(".dib","application/x-dib");

	    	ctmMap.put(".dll","application/x-msdownload");

	    	ctmMap.put(".doc","application/msword");

	    	ctmMap.put(".dot","application/msword");

	    	ctmMap.put(".drw","application/x-drw");

	    	ctmMap.put(".dtd","text/xml");

	    	ctmMap.put(".dwf","Model/vnd.dwf");

	    	ctmMap.put(".dwf","application/x-dwf");

	    	ctmMap.put(".dwg","application/x-dwg");

	    	ctmMap.put(".dxb","application/x-dxb");

	    	ctmMap.put(".dxf","application/x-dxf");

	    	ctmMap.put(".edn","application/vnd.adobe.edn");

	    	ctmMap.put(".emf","application/x-emf");

	    	ctmMap.put(".eml","message/rfc822");

	    	ctmMap.put(".ent","text/xml");

	    	ctmMap.put(".epi","application/x-epi");

	    	ctmMap.put(".eps","application/x-ps");

	    	ctmMap.put(".eps","application/postscript");

	    	ctmMap.put(".etd","application/x-ebx");

	    	ctmMap.put(".exe","application/x-msdownload");

	    	ctmMap.put(".fax","image/fax");

	    	ctmMap.put(".fdf","application/vnd.fdf");

	    	ctmMap.put(".fif","application/fractals");

	    	ctmMap.put(".fo","text/xml");

	    	ctmMap.put(".frm","application/x-frm");

	    	ctmMap.put(".g4","application/x-g4");

	    	ctmMap.put(".gbr","application/x-gbr");

	    	ctmMap.put(".gcd","application/x-gcd");

	    	ctmMap.put(".gif","image/gif");

	    	ctmMap.put(".gl2","application/x-gl2");

	    	ctmMap.put(".gp4","application/x-gp4");

	    	ctmMap.put(".hgl","application/x-hgl");

	    	ctmMap.put(".hmr","application/x-hmr");

	    	ctmMap.put(".hpg","application/x-hpgl");

	    	ctmMap.put(".hpl","application/x-hpl");

	    	ctmMap.put(".hqx","application/mac-binhex40");

	    	ctmMap.put(".hrf","application/x-hrf");

	    	ctmMap.put(".hta","application/hta");

	    	ctmMap.put(".htc","text/x-component");

	    	ctmMap.put(".htm","text/html");

	    	ctmMap.put(".html","text/html");

	    	ctmMap.put(".htt","text/webviewhtml");

	    	ctmMap.put(".htx","text/html");

	    	ctmMap.put(".icb","application/x-icb");

	    	ctmMap.put(".ico","image/x-icon");

	    	//ctmMap.put(".ico","application/x-ico");

	    	ctmMap.put(".iff","application/x-iff");

	    	ctmMap.put(".ig4","application/x-g4");

	    	ctmMap.put(".igs","application/x-igs");

	    	ctmMap.put(".iii","application/x-iphone");

	    	ctmMap.put(".img","application/x-img");

	    	ctmMap.put(".ins","application/x-internet-signup");

	    	ctmMap.put(".isp","application/x-internet-signup");

	    	ctmMap.put(".IVF","video/x-ivf");

	    	ctmMap.put(".java","java/*");

	    	ctmMap.put(".jfif","image/jpeg");

	    	ctmMap.put(".jpe","image/jpeg");

	    	ctmMap.put(".jpe","application/x-jpe");

	    	ctmMap.put(".jpeg","image/jpeg");

	    	ctmMap.put(".jpg","image/jpeg");

	    	//ctmMap.put(".jpg","application/x-jpg");

	    	ctmMap.put(".js","application/x-javascript");

	    	ctmMap.put(".jsp","text/html");

	    	ctmMap.put(".la1","audio/x-liquid-file");

	    	ctmMap.put(".lar","application/x-laplayer-reg");

	    	ctmMap.put(".latex","application/x-latex");

	    	ctmMap.put(".lavs","audio/x-liquid-secure");

	    	ctmMap.put(".lbm","application/x-lbm");

	    	ctmMap.put(".lmsff","audio/x-la-lms");

	    	ctmMap.put(".ls","application/x-javascript");

	    	ctmMap.put(".ltr","application/x-ltr");

	    	ctmMap.put(".m1v","video/x-mpeg");

	    	ctmMap.put(".m2v","video/x-mpeg");

	    	ctmMap.put(".m3u","audio/mpegurl");

	    	ctmMap.put(".m4e","video/mpeg4");

	    	ctmMap.put(".mac","application/x-mac");

	    	ctmMap.put(".man","application/x-troff-man");

	    	ctmMap.put(".math","text/xml");

	    	ctmMap.put(".mdb","application/msaccess");

	    	ctmMap.put(".mdb","application/x-mdb");

	    	ctmMap.put(".mfp","application/x-shockwave-flash");

	    	ctmMap.put(".mht","message/rfc822");

	    	ctmMap.put(".mhtml","message/rfc822");

	    	ctmMap.put(".mi","application/x-mi");

	    	ctmMap.put(".mid","audio/mid");

	    	ctmMap.put(".midi","audio/mid");

	    	ctmMap.put(".mil","application/x-mil");

	    	ctmMap.put(".mml","text/xml");

	    	ctmMap.put(".mnd","audio/x-musicnet-download");

	    	ctmMap.put(".mns","audio/x-musicnet-stream");

	    	ctmMap.put(".mocha","application/x-javascript");

	    	ctmMap.put(".movie","video/x-sgi-movie");

	    	ctmMap.put(".mp1","audio/mp1");

	    	ctmMap.put(".mp2","audio/mp2");

	    	ctmMap.put(".mp2v","video/mpeg");

	    	ctmMap.put(".mp3","audio/mp3");

	    	ctmMap.put(".mp4","video/mpeg4");

	    	ctmMap.put(".mpa","video/x-mpg");

	    	ctmMap.put(".mpd","application/vnd.ms-project");

	    	ctmMap.put(".mpe","video/x-mpeg");

	    	ctmMap.put(".mpeg","video/mpg");

	    	ctmMap.put(".mpg","video/mpg");

	    	ctmMap.put(".mpga","audio/rn-mpeg");

	    	ctmMap.put(".mpp","application/vnd.ms-project");

	    	ctmMap.put(".mps","video/x-mpeg");

	    	ctmMap.put(".mpt","application/vnd.ms-project");

	    	ctmMap.put(".mpv","video/mpg");

	    	ctmMap.put(".mpv2","video/mpeg");

	    	ctmMap.put(".mpw","application/vnd.ms-project");

	    	ctmMap.put(".mpx","application/vnd.ms-project");

	    	ctmMap.put(".mtx","text/xml");

	    	ctmMap.put(".mxp","application/x-mmxp");

	    	ctmMap.put(".net","image/pnetvue");

	    	ctmMap.put(".nrf","application/x-nrf");

	    	ctmMap.put(".nws","message/rfc822");

	    	ctmMap.put(".odc","text/x-ms-odc");

	    	ctmMap.put(".out","application/x-out");

	    	ctmMap.put(".p10","application/pkcs10");

	    	ctmMap.put(".p12","application/x-pkcs12");

	    	ctmMap.put(".p7b","application/x-pkcs7-certificates");

	    	ctmMap.put(".p7c","application/pkcs7-mime");

	    	ctmMap.put(".p7m","application/pkcs7-mime");

	    	ctmMap.put(".p7r","application/x-pkcs7-certreqresp");

	    	ctmMap.put(".p7s","application/pkcs7-signature");

	    	ctmMap.put(".pc5","application/x-pc5");

	    	ctmMap.put(".pci","application/x-pci");

	    	ctmMap.put(".pcl","application/x-pcl");

	    	ctmMap.put(".pcx","application/x-pcx");

	    	ctmMap.put(".pdf","application/pdf");

	    	ctmMap.put(".pdf","application/pdf");

	    	ctmMap.put(".pdx","application/vnd.adobe.pdx");

	    	ctmMap.put(".pfx","application/x-pkcs12");

	    	ctmMap.put(".pgl","application/x-pgl");

	    	ctmMap.put(".pic","application/x-pic");

	    	ctmMap.put(".pko","application/vnd.ms-pki.pko");

	    	ctmMap.put(".pl","application/x-perl");

	    	ctmMap.put(".plg","text/html");

	    	ctmMap.put(".pls","audio/scpls");

	    	ctmMap.put(".plt","application/x-plt");

	    	ctmMap.put(".png","image/png");

	    	//ctmMap.put(".png","application/x-png");

	    	ctmMap.put(".pot","application/vnd.ms-powerpoint");

	    	ctmMap.put(".ppa","application/vnd.ms-powerpoint");

	    	ctmMap.put(".ppm","application/x-ppm");

	    	ctmMap.put(".pps","application/vnd.ms-powerpoint");

	    	ctmMap.put(".ppt","application/vnd.ms-powerpoint");

	    	ctmMap.put(".ppt","application/x-ppt");

	    	ctmMap.put(".pr","application/x-pr");

	    	ctmMap.put(".prf","application/pics-rules");

	    	ctmMap.put(".prn","application/x-prn");

	    	ctmMap.put(".prt","application/x-prt");

	    	ctmMap.put(".ps","application/x-ps");

	    	ctmMap.put(".ps","application/postscript");

	    	ctmMap.put(".ptn","application/x-ptn");

	    	ctmMap.put(".pwz","application/vnd.ms-powerpoint");

	    	ctmMap.put(".r3t","text/vnd.rn-realtext3d");

	    	ctmMap.put(".ra","audio/vnd.rn-realaudio");

	    	ctmMap.put(".ram","audio/x-pn-realaudio");

	    	ctmMap.put(".ras","application/x-ras");

	    	ctmMap.put(".rat","application/rat-file");

	    	ctmMap.put(".rdf","text/xml");

	    	ctmMap.put(".rec","application/vnd.rn-recording");

	    	ctmMap.put(".red","application/x-red");

	    	ctmMap.put(".rgb","application/x-rgb");

	    	ctmMap.put(".rjs","application/vnd.rn-realsystem-rjs");

	    	ctmMap.put(".rjt","application/vnd.rn-realsystem-rjt");

	    	ctmMap.put(".rlc","application/x-rlc");

	    	ctmMap.put(".rle","application/x-rle");

	    	ctmMap.put(".rm","application/vnd.rn-realmedia");

	    	ctmMap.put(".rmf","application/vnd.adobe.rmf");

	    	ctmMap.put(".rmi","audio/mid");

	    	ctmMap.put(".rmj","application/vnd.rn-realsystem-rmj");

	    	ctmMap.put(".rmm","audio/x-pn-realaudio");

	    	ctmMap.put(".rmp","application/vnd.rn-rn_music_package");

	    	ctmMap.put(".rms","application/vnd.rn-realmedia-secure");

	    	ctmMap.put(".rmvb","application/vnd.rn-realmedia-vbr");

	    	ctmMap.put(".rmx","application/vnd.rn-realsystem-rmx");

	    	ctmMap.put(".rnx","application/vnd.rn-realplayer");

	    	ctmMap.put(".rp","image/vnd.rn-realpix");

	    	ctmMap.put(".rpm","audio/x-pn-realaudio-plugin");

	    	ctmMap.put(".rsml","application/vnd.rn-rsml");

	    	ctmMap.put(".rt","text/vnd.rn-realtext");

	    	ctmMap.put(".rtf","application/msword");

	    	ctmMap.put(".rtf","application/x-rtf");

	    	ctmMap.put(".rv","video/vnd.rn-realvideo");

	    	ctmMap.put(".sam","application/x-sam");

	    	ctmMap.put(".sat","application/x-sat");

	    	ctmMap.put(".sdp","application/sdp");

	    	ctmMap.put(".sdw","application/x-sdw");

	    	ctmMap.put(".sit","application/x-stuffit");

	    	ctmMap.put(".slb","application/x-slb");

	    	ctmMap.put(".sld","application/x-sld");

	    	ctmMap.put(".slk","drawing/x-slk");

	    	ctmMap.put(".smi","application/smil");

	    	ctmMap.put(".smil","application/smil");

	    	ctmMap.put(".smk","application/x-smk");

	    	ctmMap.put(".snd","audio/basic");

	    	ctmMap.put(".sol","text/plain");

	    	ctmMap.put(".sor","text/plain");

	    	ctmMap.put(".spc","application/x-pkcs7-certificates");

	    	ctmMap.put(".spl","application/futuresplash");

	    	ctmMap.put(".spp","text/xml");

	    	ctmMap.put(".ssm","application/streamingmedia");

	    	ctmMap.put(".sst","application/vnd.ms-pki.certstore");

	    	ctmMap.put(".stl","application/vnd.ms-pki.stl");

	    	ctmMap.put(".stm","text/html");

	    	ctmMap.put(".sty","application/x-sty");

	    	ctmMap.put(".svg","text/xml");

	    	ctmMap.put(".swf","application/x-shockwave-flash");

	    	ctmMap.put(".tdf","application/x-tdf");

	    	ctmMap.put(".tg4","application/x-tg4");

	    	ctmMap.put(".tga","application/x-tga");

	    	ctmMap.put(".tif","image/tiff");

	    	ctmMap.put(".tif","application/x-tif");

	    	ctmMap.put(".tiff","image/tiff");

	    	ctmMap.put(".tld","text/xml");

	    	ctmMap.put(".top","drawing/x-top");

	    	ctmMap.put(".torrent","application/x-bittorrent");

	    	ctmMap.put(".tsd","text/xml");

	    	ctmMap.put(".txt","text/plain");

	    	ctmMap.put(".uin","application/x-icq");

	    	ctmMap.put(".uls","text/iuls");

	    	ctmMap.put(".vcf","text/x-vcard");

	    	ctmMap.put(".vda","application/x-vda");

	    	ctmMap.put(".vdx","application/vnd.visio");

	    	ctmMap.put(".vml","text/xml");

	    	ctmMap.put(".vpg","application/x-vpeg005");

	    	ctmMap.put(".vsd","application/vnd.visio");

	    	ctmMap.put(".vsd","application/x-vsd");

	    	ctmMap.put(".vss","application/vnd.visio");

	    	ctmMap.put(".vst","application/vnd.visio");

	    	ctmMap.put(".vst","application/x-vst");

	    	ctmMap.put(".vsw","application/vnd.visio");

	    	ctmMap.put(".vsx","application/vnd.visio");

	    	ctmMap.put(".vtx","application/vnd.visio");

	    	ctmMap.put(".vxml","text/xml");

	    	ctmMap.put(".wav","audio/wav");

	    	ctmMap.put(".wax","audio/x-ms-wax");

	    	ctmMap.put(".wb1","application/x-wb1");

	    	ctmMap.put(".wb2","application/x-wb2");

	    	ctmMap.put(".wb3","application/x-wb3");

	    	ctmMap.put(".wbmp","image/vnd.wap.wbmp");

	    	ctmMap.put(".wiz","application/msword");

	    	ctmMap.put(".wk3","application/x-wk3");

	    	ctmMap.put(".wk4","application/x-wk4");

	    	ctmMap.put(".wkq","application/x-wkq");

	    	ctmMap.put(".wks","application/x-wks");

	    	ctmMap.put(".wm","video/x-ms-wm");

	    	ctmMap.put(".wma","audio/x-ms-wma");

	    	ctmMap.put(".wmd","application/x-ms-wmd");

	    	ctmMap.put(".wmf","application/x-wmf");

	    	ctmMap.put(".wml","text/vnd.wap.wml");

	    	ctmMap.put(".wmv","video/x-ms-wmv");

	    	ctmMap.put(".wmx","video/x-ms-wmx");

	    	ctmMap.put(".wmz","application/x-ms-wmz");

	    	ctmMap.put(".wp6","application/x-wp6");

	    	ctmMap.put(".wpd","application/x-wpd");

	    	ctmMap.put(".wpg","application/x-wpg");

	    	ctmMap.put(".wpl","application/vnd.ms-wpl");

	    	ctmMap.put(".wq1","application/x-wq1");

	    	ctmMap.put(".wr1","application/x-wr1");

	    	ctmMap.put(".wri","application/x-wri");

	    	ctmMap.put(".wrk","application/x-wrk");

	    	ctmMap.put(".ws","application/x-ws");

	    	ctmMap.put(".ws2","application/x-ws");

	    	ctmMap.put(".wsc","text/scriptlet");

	    	ctmMap.put(".wsdl","text/xml");

	    	ctmMap.put(".wvx","video/x-ms-wvx");

	    	ctmMap.put(".xdp","application/vnd.adobe.xdp");

	    	ctmMap.put(".xdr","text/xml");

	    	ctmMap.put(".xfd","application/vnd.adobe.xfd");

	    	ctmMap.put(".xfdf","application/vnd.adobe.xfdf");

	    	ctmMap.put(".xhtml","text/html");

	    	ctmMap.put(".xls","application/vnd.ms-excel");

	    	ctmMap.put(".xls","application/x-xls");

	    	ctmMap.put(".xlw","application/x-xlw");

	    	ctmMap.put(".xml","text/xml");

	    	ctmMap.put(".xpl","audio/scpls");

	    	ctmMap.put(".xq","text/xml");

	    	ctmMap.put(".xql","text/xml");

	    	ctmMap.put(".xquery","text/xml");

	    	ctmMap.put(".xsd","text/xml");

	    	ctmMap.put(".xsl","text/xml");

	    	ctmMap.put(".xslt","text/xml");

	    	ctmMap.put(".xwd","application/x-xwd");

	    	ctmMap.put(".x_b","application/x-x_b");

	    	ctmMap.put(".x_t","application/x-x_t");

	    }
	    /**
	     * 获取文件后缀的文件类型
	     * 作者:LM
	     * 创建日期:2015-1-28下午02:09:00
	     * @param fileExt
	     * @return
	     */
	    public static String getContextType(String fileExt){
	    	if(SystemUtils.isEmpty(fileExt)){
	    		return ctmMap.get(".*");
	    	}
	    	String contentType=ctmMap.get("."+fileExt.toLowerCase());
	    	if(!SystemUtils.isEmpty(contentType)){
	    		return contentType;
	    	}
	    	return ctmMap.get(".*");
	    }
	    /**
	     * 获取文件名对应的contextType
	     * 作者:LM
	     * 创建日期:2015-1-28下午02:13:10
	     * @param fileName
	     * @return
	     */
	    public static String getFileType(String fileName){
	    	if(fileName.indexOf(".")>=0){
	    		return getContextType(fileName.split("\\.")[1]);
	    	}
	    	return getContextType(null);
	    }
	    /**
	     * 获取文件的mini类型 默认从web.xml配置中获取
	     * @param fileName
	     * @param exts
	     * @return
	     */
	    public static String getFileType(String fileName,List<Map<String,String>> exts){
	    	String fileExt=fileName.split("\\.")[1];
	    	for(Map<String,String> mss:exts){
	    		String extension=mss.get("extension");
	    		if(extension.equals(fileExt)){
	    			String contextType=mss.get("mimeType");
	    			return contextType;
	    		}
	    	}
	    	return null;
	    }
	    public static void main(String[] args) throws IOException {
	    	//获取类型 普通的类型 正确 找不到的 统一是 application/octet-stream
	    	String tp=FileTypeMap.getDefaultFileTypeMap().getContentType("a.jsp");
	    	System.out.println(tp);
	    	FileInputStream f=new FileInputStream("c:/a.jpg");
	    	//获取文件类型 只支持网络流 不支持文件流
	    	tp=URLConnection.guessContentTypeFromStream(f);
	    	System.out.println(tp);
	    	//普通的图片 网页啊 txt啊 可以有结果  其他返回null
	    	tp=URLConnection.guessContentTypeFromName("a.jsp");
	    	System.out.println(tp);
	    	JFileChooser  s=new JFileChooser();
	    	tp=s.getDescription(new File("c:/a.jpg"));
	    	System.out.println(tp);
		}

}
