package org.cloud.usercenter;

import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月8日 上午11:32:38
 */
@Slf4j
public class RedisUtil {
	
	//protected final Log logger = LogFactory.getLog(getClass());
	
	private static JedisPoolConfig config = null;
	
	private static JedisPool jedisPool = null;
	
	private static final String RDDIS_IP = "172.16.189.201";
	private static final int RDDIS_PORT = 6379;
	private static final String REDIS_MASTER_AUTH = "ASDQWE123";
	
	static{
		config = new JedisPoolConfig();
		//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(true);
		//设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		//是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(true);
		//MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
		config.setJmxNamePrefix("pool");
		//是否启用后进先出, 默认true
		config.setLifo(true);
		//最大空闲连接数, 默认8个
		config.setMaxIdle(100000);
		//最大连接数, 默认30个
		config.setMaxTotal(1000000);
		//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
		config.setMaxWaitMillis(5*1000);
		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis(1800000);
		//最小空闲连接数, 默认0
		config.setMinIdle(500);
		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		config.setNumTestsPerEvictionRun(3);
		//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
//		config.setSoftMinEvictableIdleTimeMillis(1800000);
		//在获取连接的时候检查有效性, 默认false
		config.setTestOnBorrow(false);
		//在空闲时检查有效性, 默认false
		config.setTestWhileIdle(false);
		//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		config.setTimeBetweenEvictionRunsMillis(-1);
		jedisPool = new JedisPool(config, RDDIS_IP,RDDIS_PORT, 10 * 1000,REDIS_MASTER_AUTH);
	}
	
	/**
	 * 获取jedispool
	 * @return
	 */
	private static JedisPool getJedisPool(){
		return jedisPool;
	}
	
	/*#############################   键(key) 命令 #################################*/
	
	/**
	 * 返回key的集合
	 * 支持正则表达式
	 * @param pattern
	 * @return
	 */
	public static Set<String> keys(String pattern){
		Set<String> rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.keys(pattern);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
			
		}
		return rs;
	}
	
	/**
	 * 字符串删除
	 * @param key
	 */
	public static long del(String... key){
		long rs = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.del(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
			
		}
		return rs;
	}
	
	/**
	 * 对key设置多少秒后过期
	 * @param key
	 * @param seconds
	 * @return
	 */
	public static long expire(String key, int seconds){
		long expire = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				expire = jedis.expire(key, seconds);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return expire;
	}
	
	public static long expireAt(String key, long unixTime){
		long expire = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				expire = jedis.expireAt(key, unixTime);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return expire;
	}
	
	public static long pexpireAt(String key, long millisecondsTimestamp){
		long expire = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				expire = jedis.pexpireAt(key, millisecondsTimestamp);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return expire;
	}
	
	/**
	 * 以秒为单位返回 key 的剩余过期时间
	 * @param key
	 * @return
	 */
	public static long ttl(String key){
		long expire = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				expire = jedis.ttl(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return expire;
	}
	
	/*########################################    哈希(Hash) 命令          ##########################################################################*/
	
	/**
	 * 哈希存储
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hset(String key,String field,String value){
		long rs = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.hset(key, field, value);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	
	/**
	 * 哈希获取
	 * @param key
	 * @param field
	 * @return
	 */
	public static String hget(String key,String field){
		String rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.hget(key, field);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		
		return rs;
	}
	
	/**
	 * 哈希是否存在
	 * @param key
	 * @param field
	 * @return
	 */
	public static Boolean hexists(String key,String field){
		Boolean rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.hexists(key,field);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	/**
	 * 哈希删除多个域
	 * @param key
	 * @param fields
	 * @return
	 */
	public static long hdel(String key, String... fields){
		long rs = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.hdel(key, fields);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 哈希获取多个域
	 * @param key
	 * @return
	 */
	public static Set<String> hkeys(String key){
		Set<String> rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.hkeys(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 返回哈希的值（values）
	 * @param key
	 * @return
	 */
	public static List<String> hvals(String key){
		List<String> rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.hvals(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	/**
	 * 哈希长度
	 * @param key
	 * @return
	 */
	public static Long hlen(String key){
		Long rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.hlen(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	 /*##################################################  列表(List) 命令   ######################################################################*/
	
	/**
	 * list 左存储
	 * @param key
	 * @param value
	 * @return
	 */
	public static long lpush(String key,String value){
		long rs = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.lpush(key,value);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	/**
	 * list 左存储 取值
	 * @param key
	 * @return
	 */
	public static String rpop(String key){
		String rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.rpop(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	/*############################################  字符串(String) 命令  ######################################*/
	/**
	 * 字符串存储
	 * @param key
	 * @param value
	 * @return
	 */
	public static String set(String key,String value){
		String rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.set(key,value);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 批量字符串存储
	 * @param keysvalues
	 */
	public static String mset(String... keysvalues){
		String rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.mset(keysvalues);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 字符串批量获取
	 * @param keys
	 * @return
	 */
	public static List<String> mget(String...keys){
		List<String> rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.mget(keys);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	public static String get(String key){
		String rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.get(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	
	/*##########################　　有序集合(sorted set) 命令　　　##########################　　*/
	
	/**
	 * 排序集合
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static long zadd(String key, double score, String member){
		long rs = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.zadd(key, score, member);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 有序列表删除
	 * @param key
	 * @param members
	 * @return
	 */
	public static long zrem(String key, String...members){

		long rs = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.zrem(key, members);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 有序列表倒叙返回
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> zrevrange(String key, long start, long end){
		Set<String> rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.zrevrange(key, start, end);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 返回排序集合
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> zrange(String key,long start,long end){
		Set<String> rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.zrange(key, start, end);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	/*＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃　set集合命令（无序）　＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃*/
	
	
	/**
	 * set集合存储，一次可以插入多个value
	 * @param key
	 * @param members
	 * @return
	 */
	public static Long sadd(String key, String... members){
		Long rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.sadd(key, members);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 返回set集合 values 数据
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key){
		Set<String> rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.smembers(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 返回set集合的长度
	 * @param key
	 * @return
	 */
	public static Long scard(String key){
		Long rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.scard(key);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 判断member 是否是存在key集合（set）中
	 * @param key
	 * @param member
	 * @return
	 */
	public static Boolean sismember(String key,String member){
		Boolean rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.sismember(key, member);
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	/*＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃　服务器相关信息 命令　＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃*/
	
	/**
	 * 获取服务信息
	 * @return
	 */
	public static String info(){
		String rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.info();
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	/**
	 * 返回redis客户端
	 * @return
	 */
	public static Jedis getJedis(){
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
		}catch(Exception e){
			log.error("getJedis error...");
			log.info(e.getMessage(), e);
		}
		return jedis;
	}
	
	/**
	 * 删除当前数据库的所有key
	 * @return
	 */
	public static String flushDB(){
		String rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.flushDB();
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	/**
	 * 删除所有数据库的所有key
	 * @return
	 */
	public static String flushAll(){
		String rs = null;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.flushAll();
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	/**
	 * 返回当前选中数据库中键的数量
	 * @return
	 */
	public static long dbSize(){
		long rs = 0;
		Jedis jedis = null;
		JedisPool jedisPool = null;
		try{
			jedisPool = getJedisPool();
			jedis = jedisPool.getResource();
			if(jedis != null){
				rs = jedis.dbSize();
			}
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return rs;
	}
	
	
	
	public static void main(String[] args) {
//		RedisUtil jedisClient = new RedisUtil();
////		jedisClient.flushDB();
//		
//		jedisClient.hset("key", "field", "value");
////		
//		System.out.println(jedisClient.hget("key", "field"));
//		
//		for(int i=0;i<1000000;i++){
//			jedisClient.getJedis();
//		}
//		System.out.println("33");
		
//		RedisUtil.set("20", "11");
//		Long a = System.currentTimeMillis()+3600*1000;
//		RedisUtil.expireAt("20", a/1000);
		System.out.println(RedisUtil.ttl("20"));
		
//		RedisUtil.set("testA", "1");
//		RedisUtil.expire("testA", 20);
//		System.out.println(RedisUtil.ttl("testA"));
		
		//设置库存大小
//		jedisClient.set(ConstantCommons.ACCOUNT_KEY, "5000");
//		System.out.println(jedisClient.get(ConstantCommons.ACCOUNT_KEY));
		
		
//		System.out.println();
//		
//		
//		jedisClient.hset("ABC","1","1");
//		
//		System.out.println(jedisClient.hget("ABC","2"));
		
		//删除以购人key
//		System.out.println(jedisClient.hlen(ConstantCommons.ALREADY_ROB));
//		Set<String> fields = jedisClient.hkeys(ConstantCommons.ALREADY_ROB);
//		String [] array = fields.toArray(new String[]{});
//		jedisClient.hdel(ConstantCommons.ALREADY_ROB, array);
//		System.out.println(jedisClient.hlen(ConstantCommons.ALREADY_ROB));
//		

		//测试watch下的key 被改变时，tx.exec() 返回null
//		JedisPool pool = jedisClient.getJedisPool();
//		Jedis jedis = pool.getResource();
//		System.out.println(jedis.watch("tx2","key"));
//		Transaction tx = jedis.multi();
//		tx.get("tx2");
//		tx.hget("key", "field99");
//		List<Object> list = tx.exec();
//		for(Object o: list){
//			System.out.println(o);
//		}
//		System.out.println(jedis.unwatch());
		
		//测试有序排序
//		jedisClient.zadd("roo", 1, "a1");
//		jedisClient.zadd("roo", 3, "a3");
//		jedisClient.zadd("roo", 2, "a2");
//		jedisClient.zadd("roo", 2, "a2");
//		System.out.println(jedisClient.zrevrange("roo", 0, 10));
//		jedisClient.del("roo");
//		System.out.println(jedisClient.zrevrange("roo", 0, 10));
		
//		System.out.println(jedisClient.set("a1","b1"));
//		System.out.println(jedisClient.set("a2","b2"));
//		System.out.println(jedisClient.mset("a3","b3","a4","b4"));
//		System.out.println(jedisClient.mget("a4","a3","a2","a1"));
//		
//		jedisClient.hset("a5", "field", "value");
//		jedisClient.hset("a51", "field", "value");
//		jedisClient.lpush("a6", "value");
//		System.out.println(jedisClient.keys("a*"));
//		System.out.println(jedisClient.hkeys("a5"));
//		jedisClient.del(jedisClient.keys("*").toArray(new String[] {}));
//		System.out.println(jedisClient.keys("*"));
//		jedisClient.flushDB();
//		System.out.println(jedisClient.dbSize());
		
//		jedisClient.hset("a", "field1", "value1");
//		jedisClient.hset("a", "field2", "value2");
//		System.out.println(jedisClient.hkeys("a"));
//		System.out.println(jedisClient.hvals("a"));
//		System.out.println(jedisClient.hexists("a", "field1"));
//		System.out.println(jedisClient.hexists("a", "field3"));
//		System.out.println(jedisClient.keys("*"));
//		System.out.println(jedisClient.flushDB());
//		System.out.println(jedisClient.flushAll());
//		System.out.println(jedisClient.keys("*"));
		
	}
}

