package storm.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.jsoup.nodes.Element;
import storm.util.GetPageUtil;
import storm.util.JsoupUtil;
import storm.util.ParseUtil;

/**
 * Created by Richard on 2016-08-30.
 */
public class FetcherBolt extends BaseBasicBolt {
    private static ParseUtil parse = new ParseUtil();
    public FetcherBolt(){

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String url = tuple.getString(0);
        String page = crawler(url);
        Element data = parser(page);
        basicOutputCollector.emit(new Values(data));
    }



    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("data"));
    }

    private String crawler(String url) {
        GetPageUtil get = new GetPageUtil();
        String page = get.getAPage(url);
        return page;
    }

    private Element parser(String page) {
        Element body = JsoupUtil.getBody(page);
        Element data = body.select("div.editor-style").first();
        //System.out.println(data);
        return null;
    }

    public static void main(String[] args) {
        FetcherBolt a = new FetcherBolt();
        a.parser(a.crawler("http://dxy.com/faq/3498"));
    }
}
