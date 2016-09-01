package storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import storm.bolt.FetcherBolt;
import storm.bolt.StoreBolt;
import storm.spout.URLSpout;

/**
 * Created by Richard on 2016-08-31.
 */
public class SpiderTopology {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException, InterruptedException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout",new URLSpout(),1);
        builder.setBolt("fetcher",new FetcherBolt(),1).shuffleGrouping("spout");
        builder.setBolt("store",new StoreBolt(),2).shuffleGrouping("fetcher");
        Config conf = new Config();
        conf.setDebug(true);
        if (args != null && args.length > 0) {
            conf.setNumWorkers(90);
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        }
        else {
            conf.setMaxTaskParallelism(13);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
            Thread.sleep(10000);
            cluster.shutdown();
        }
    }

}
