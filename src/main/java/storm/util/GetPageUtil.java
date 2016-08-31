package storm.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * Created by Richard on 2016-08-30.
 */
public class GetPageUtil {

    private DefaultHttpClient httpClient = HttpConnectionManager.getHttpClient();

    public GetPageUtil() {

    }

    public static HttpGet pretendBrowserHeaderGet(HttpGet httpGet){
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Accept-Encoding", "identity");//编码方式！！
        httpGet.setHeader("Connection", "keep-alive");
        return null;
    }

    public static HttpPost pretendBrowserHeaderPost(HttpPost httpPost){
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64;rv:43.0) Gecko/20100101 Firefox/43.0");
        httpPost.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpPost.addHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate");
        httpPost.addHeader("Connection", "keep-alive");
        return httpPost;
    }

    public String getAPage(String url){
        String content="";
        HttpGet get = new HttpGet(url);
        try {
            HttpConnectionManager.setHandleRedirect(httpClient, true);
            HttpResponse response = httpClient.execute(get);
//			checkHttpResponse(response);
            HttpEntity entity = response.getEntity();
            if(entity!=null)
                content = EntityUtils.toString(entity);
            get.abort();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return content;
    }
    public  String getAPage(String url,String cookieStr,boolean ifPretendBrowse){
        String content="";
        HttpGet get = new HttpGet(url);
        if(StringUtils.isNotBlank(cookieStr))
            get.setHeader("Cookie", cookieStr);
        if(ifPretendBrowse)
            get = pretendBrowserHeaderGet(get);
        try {
            HttpConnectionManager.setHandleRedirect(httpClient, false);
            HttpResponse response = httpClient.execute(get);
            checkHttpResponse(response);
            HttpEntity entity = response.getEntity();
            if(entity!=null)
                content = EntityUtils.toString(entity);
            get.abort();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return content;
    }

    public static void checkHttpResponse(HttpResponse response){
        System.out.println(response.getStatusLine());
        System.out.println(response.getParams());
        System.out.println(response.getProtocolVersion());

        Header[] allHeaders = response.getAllHeaders();
        for(Header header:allHeaders){
            System.out.println(header);
        }
    }


    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }
    public static void main(String[] args) throws Exception {
 		GetPageUtil gpu = new GetPageUtil();
 		String page = gpu.getAPage("http://dxy.com/faq");
// 		String page = getAPage("http://weibo.cn/1649173367/info", "SUB=_2A257waXwDeThGeNG61cU8izNyjiIHXVW1r44rDV6PUJbrdAKLXSnkWpLHetS_RW_km6fXBFD7XaHvf0qLwWsWQ..; _T_WM=512579fa3c9d3d5d039a62b0d1099659; gsid_CTandWM=4uKd52341Zf9zzNNj77SBomhs1Q; ", true);
// 		checkMwCookieUseable("SUB=_2A257waXwDeThGeNG61cU8izNyjiIHXVW1r44rDV6PUJbrdAKLXSnkWpLHetS_RW_km6fXBFD7XaHvf0qLwWsWQ..; _T_WM=512579fa3c9d3d5d039a62b0d1099659; gsid_CTandWM=4uKd52341Zf9zzNNj77SBomhs1Q; ");
 		System.out.println(page);
    }
}
