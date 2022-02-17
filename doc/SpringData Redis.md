# Spring Data Redis

# 一. Spring Data Redis简介

**Redis**是一个基于内存的数据结构存储系统，它可以用作数据库或者缓存。它支持多种类型的数据
结构，这些数据结构类型分别为**String**（字符串）、**List**（列表）、**Set**（集合）、**Hash**（散列）和
**Zset**（有序集合）。
**SpringData Redis**的作用是通过一段简单的配置即可访问redis服务，它的底层是对java提供的
redis开发包(比如jedis等)进行了高度封装，主要提供了如下功能：
连接池自动管理，提供了一个高度封装的RedisTemplate类,基于这个类的对象可以对redis进行各
种操作
针对jedis客户端中大量api进行了归类封装,将同一类型操作封装为operation接口
**ValueOperations**：简单字符串类型数据操作
**SetOperations：set**类型数据操作
**ZSetOperations：zset**类型数据操作
**HashOperations：map**类型的数据操作
**ListOperations：list**类型的数据操作



# 二. Spring Data Redis快速入门

## 2.1 引入依赖

```xml
<dependency>
	<dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-redis</artifactId>
        <version>2.1.8.RELEASE</version>
	</dependency>
</dependency>
```

## 2.2 配置Redis配置类

```java
@Configuration
public class JedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setPort(6379);

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired JedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 配置具体的序列化方式
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        /*
         * key采用String序列化的方式
         * value序列化的方式采用JSON
         * hash的key采用String的序列化方式
         * hash的value序列化采用JSON
         */
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
```

## 2.3 测试

```java
@Test
public void test0() {
    //存入数据
    redisTemplate.opsForValue().set("name", "heima");
    //查询数据
    String name = (String) redisTemplate.opsForValue().get("name");
    System.out.println(name);
}
```

## 2.4 Spring Data Redis序列化器

这时候会发现，存入的数据并不是简单的字符串，而是一些类似于二进制的数据，这是怎么回事呢？
原来，SpringData Redis在保存数据的时候，底层有一个序列化器在工作，它会将要保存的数据
（键和值）按照一定的规则进行序列化操作后再进行存储。spring-data-redis提供如下几种序列化器：
**StringRedisSerializer**: 简单的字符串序列化
**GenericToStringSerializer**: 可以将任何对象泛化为字符串并序列化
**Jackson2JsonRedisSerializer**: 序列化对象为json字符串
**GenericJackson2JsonRedisSerializer**:功能同上,但是更容易反序列化
**OxmSerializer**: 序列化对象为xml字符串
**JdkSerializationRedisSerializer**: 序列化对象为二进制数据
**RedisTemplate**默认使用的是**JdkSerializationRedisSerializer**对数据进行序列化。
那么如何选择自己想要的序列化器呢？SpringData提供了两种方式：

1. 通过配置文件配置
2. 通过RedisTemplate设定

