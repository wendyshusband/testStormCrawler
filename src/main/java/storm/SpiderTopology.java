package storm;

import org.apache.storm.topology.TopologyBuilder;
import storm.bolt.FetcherBolt;
import storm.bolt.StoreBolt;
import storm.spout.URLSpout;

/**
 * Created by Richard on 2016-08-31.
 */
public class SpiderTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout",new URLSpout());
        builder.setBolt("fetcher",new FetcherBolt()).shuffleGrouping("spout");
        builder.setBolt("store",new StoreBolt()).shuffleGrouping("fetcher");
    }

}
