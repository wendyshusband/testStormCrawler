package storm.bolt;

import com.mongodb.BasicDBObject;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.bson.Document;
import org.bson.conversions.Bson;
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
        String url = tuple.getString(1);
        storeData(data,url);
    }

    static {
        Bson bson = new BasicDBObject(config.getValue("index"),1);//创建唯一索引
        MongoManager.createIndex(config.getValue("dbName"), config.getValue("collectionName"),bson);
    }

    private void storeData(String data,String url) {
        try {
            MongoManager manager = new MongoManager();
            Document document = new Document();
            document.put("article", data);
            document.put("url",url);
            manager.insertOne(document, config.getValue("dbName"), config.getValue("collectionName"));

        }catch (Exception e){
            System.err.println("please check !");
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
