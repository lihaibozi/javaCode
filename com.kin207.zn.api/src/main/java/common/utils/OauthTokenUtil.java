package common.utils;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import com.kin207.zn.AppConst;

import common.utils.wechat.WxConfigUtil;

import java.util.HashMap;
import java.util.Map;
/**
 * 微信工具类
 * 1.校验合法性<br>
 * 2.获取OauthAccessToken<br>
 * 3.获取jsticket<br>
 * 4.执行微信线程<br>
 * 5.获取jsapi相关参数map<br>
 */
public class OauthTokenUtil {

    private static final Logger logger = Logger.getLogger(OauthTokenUtil.class);
    private static String appid = AppConst.APPID;
    private static String secret = AppConst.SECRET;
    private static String host = AppConst.APP_URL;
    // 缓存添加的时间
    public static String cacheAddTime = null;
    // token,ticket缓存
    public static Map<String, OauthAccessToken> TOKEN_TICKET_CACHE = new HashMap<String, OauthAccessToken>();
    // token对应的key
    private static final String TOKEN = "token";
    // ticket对应的key
    private static final String TICKET = "ticket";

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        OauthTokenUtil.appid = appid;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        OauthTokenUtil.host = host;
    }

    /**
     * 获取access_token
     * @return
     */
    public static OauthAccessToken getOauthAccessToken(String code) {
         String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        OauthAccessToken tockenTicketCache = getTokenTicket(TOKEN);
        OauthAccessToken oauthAccessToken = null;

        if (tockenTicketCache != null && (System.currentTimeMillis() - tockenTicketCache.getAddTime() <= tockenTicketCache.getExpiresIn() * 1000)) {// 缓存存在并且没过期
            logger.info("==========缓存中token已获取时长为：" + (System.currentTimeMillis() - tockenTicketCache.getAddTime()) + "毫秒，可以重新使用");
            return tockenTicketCache;
        }
        logger.info("==========缓存中token不存在或已过期===============");
        JSONObject access_token_json = WxConfigUtil.httpRequest(url, "GET", null);
        if(access_token_json.get("errcode").equals("40029")){
            return oauthAccessToken;
        }
        // 如果请求成功
        if (null != access_token_json && !"".equals(access_token_json)) {
            oauthAccessToken = new OauthAccessToken();
            oauthAccessToken.setToken(JSONObject.fromObject(access_token_json).getString("access_token"));
            oauthAccessToken.setExpiresIn(JSONObject.fromObject(access_token_json).getInt("expires_in") / 2);
            oauthAccessToken.setOpenId(JSONObject.fromObject(access_token_json).getString("openid"));
            logger.info("==========tocket缓存过期时间为:" + oauthAccessToken.getExpiresIn() * 1000 + "毫秒");
            oauthAccessToken.setAddTime(System.currentTimeMillis());
            updateOauthAccessToken(code, oauthAccessToken);
        }
        return oauthAccessToken;
    }

    /**
     * 从缓存中读取token或者ticket
     *
     * @return
     */
    private static OauthAccessToken getTokenTicket(String key) {
        if (TOKEN_TICKET_CACHE != null && TOKEN_TICKET_CACHE.get(key) != null) {
            logger.info("==========从缓存中获取到了" + key + "成功===============");
            return TOKEN_TICKET_CACHE.get(key);
        }
        return null;
    }

    /**
     * 更新缓存中token或者ticket
     *
     * @return
     */
    private static void updateOauthAccessToken(String key, OauthAccessToken accessTocken) {
        if (TOKEN_TICKET_CACHE != null && TOKEN_TICKET_CACHE.get(key) != null) {
            TOKEN_TICKET_CACHE.remove(key);
            logger.info("==========从缓存中删除" + key + "成功===============");
        }
        TOKEN_TICKET_CACHE.put(key, accessTocken);
            cacheAddTime = String.valueOf(accessTocken.getAddTime());// 更新缓存修改的时间
            logger.info("==========更新缓存中" + key + "成功===============");
    }
}