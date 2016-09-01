package storm.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.util.GetPageUtil;
import storm.util.JsoupUtil;
import storm.util.ParseUtil;
import storm.util.ReadConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 2016-08-31.
 */
public class URLSpout extends BaseRichSpout{
    SpoutOutputCollector _collector;
    private static final Logger LOG = LoggerFactory.getLogger(URLSpout.class);
    public static ReadConfig config = new ReadConfig("crawl.properties",true);
    private String startURL = config.getValue("start_url");
    private static ParseUtil parse = new ParseUtil();
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("url"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        _collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        Utils.sleep(1000);
        GetPageUtil get = new GetPageUtil();
        String page =get.getAPage(startURL);
        List<String> urlSet = parser(page);
        Iterator itr = urlSet.iterator();
        while(itr.hasNext()){
            _collector.emit(new Values(itr.next()));
        }
    }

    private List<String > parser(String page) {
        List<String> urlSet = new ArrayList<String>();
        try {
            Element body = JsoupUtil.getBody(page);
            Elements table = body.select("table.faq-table").select("tr").select("td");
            for (int i = 0; i < table.size(); i++) {
                String url = "http://dxy.com" + table.get(i).select("a").attr("href");
                urlSet.add(url);
            }
        }catch (Exception e){

        }
        return urlSet;
    }

    public void test(){
        GetPageUtil get = new GetPageUtil();
        String page =get.getAPage(startURL);
        List<String> urlSet = parser(page);
       // System.out.println(page+"王文冲");
    }

    public static void main(String[] args) {
        URLSpout s = new URLSpout();
        s.test();
    }
}
