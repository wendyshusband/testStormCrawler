package storm.util;

/**
 * Created by Richard on 2016-08-29.
 */
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

public class RedisUtil {
    private Jedis jedis = RedisPool.getJedis();
    public static Logger logger = Logger.getLogger(RedisUtil.class);

    public void addUid(String uid) {
        if (!jedis.sismember("mws.uid.allSet", uid)) {
            jedis.sadd("mws.uid.allSet", uid);
            jedis.rpush("mws.uid.crawlQue", uid);
        }
    }

    public void addWid(String wid) {
        if (!jedis.sismember("mws.wid.allSet", wid)) {
            jedis.sadd("mws.wid.allSet", wid);
            jedis.rpush("mws.wid.crawlQue", wid);
        }
    }

    public void addUidToCrawlQue(String uid) {
        jedis.rpush("mws.uid.crawlQue", uid);
    }

    public void addWidToCrawlQue(String wid) {
        jedis.rpush("mws.wid.crawlQue", wid);
    }

    // 队列pop操作
    public String popUidFromCrawlQue() {
        if (jedis.llen("mws.uid.crawlQue") > 0) {
            return jedis.lpop("mws.uid.crawlQue");
        } else
            return null;
    }

    public String popWidFromCrawlQue() {
        if (jedis.llen("mws.wid.crawlQue") > 0) {
            return jedis.lpop("mws.wid.crawlQue");
        } else
            return null;
    }

    public String popUserInfoPageFromPageQue() {
        if (jedis.llen("mws.page.userInfoQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
            return jedis.lpop("mws.page.userInfoQue");
        } else
            return null;
    }

    public String popUserWeiboPageFromPageQue() {
        if (jedis.llen("mws.page.userWeiboQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
            return jedis.lpop("mws.page.userWeiboQue");
        } else
            return null;
    }

    public String popUserFansPageFromPageQue() {
        if (jedis.llen("mws.page.userFansQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
            return jedis.lpop("mws.page.userFansQue");
        } else
            return null;
    }

    public String popUserFollowPageFromPageQue() {
        if (jedis.llen("mws.page.userFollowQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
            return jedis.lpop("mws.page.userFollowQue");
        } else
            return null;
    }

    public String popWeiboCommentPageFromPageQue() {
        if (jedis.llen("mws.page.weiboCommentQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
            return jedis.lpop("mws.page.weiboCommentQue");
        } else
            return null;
    }

    public String popWeiboRepostPageFromPageQue() {
        if (jedis.llen("mws.page.weiboRepostQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
            return jedis.lpop("mws.page.weiboRepostQue");
        } else
            return null;
    }

    // 添加page操作
    public void addUserInfoPage(String page) {
        jedis.rpush("mws.page.userInfoQue", page);
    }

    public void addUserWeiboPage(String page) {
        jedis.rpush("mws.page.userWeiboQue", page);
    }

    public void addUserFansPage(String page) {
//		System.out.println("fansPage"+page);
        jedis.rpush("mws.page.userFansQue", page);
    }

    public void addUserFollowPage(String page) {
        jedis.rpush("mws.page.userFollowQue", page);
    }

    public void addWeiboCommentPage(String page) {
        jedis.rpush("mws.page.weiboCommentQue", page);
    }

    public void addWeiboRepostPage(String page) {
        jedis.rpush("mws.page.weiboRepostQue", page);

    }

    public boolean isQueEmpty(String queName) {
        return jedis.llen(queName) == 0;
    }

    public static void main(String args[]) {

        System.out.println();
    }

}

