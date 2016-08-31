package storm.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import redis.clients.jedis.JedisPool;
import storm.util.ReadConfig;
import storm.util.RedisPool;

import java.util.Map;

/**
 * Created by Richard on 2016-08-29.
 */
public class UrlSpout extends BaseRichSpout{
    SpoutOutputCollector _collector;
    private ReadConfig config = new ReadConfig("crawl.properties", true);
    private static JedisPool redisPool = RedisPool.getPool();
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

    }
}
