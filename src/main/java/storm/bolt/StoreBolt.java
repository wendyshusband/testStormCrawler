package storm.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.bson.Document;
import storm.util.MongoManager;
import storm.util.ReadConfig;

/**
 * Created by Richard on 2016-08-31.
 */
public class StoreBolt extends BaseBasicBolt {
    private static ReadConfig config = new ReadConfig("crawl.properties", true);
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String data =tuple.getString(0);
        storeData(data);
    }

    private void storeData(String data) {
        MongoManager manager = new MongoManager();
        Document document = new Document();
        document.put("article",data);
        manager.insertOne(document, config.getValue("dbName"), config.getValue("collectionName"));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
