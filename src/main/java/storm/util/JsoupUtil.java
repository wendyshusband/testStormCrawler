package storm.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JsoupUtil.class);
	public static Element getBody(String page){
		Document doc = Jsoup.parse(page);// 解析html
		return doc.body();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
