package com.deyuan.service.impl;

import com.deyuan.service.AuthService;
import com.deyuan.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @User LiChengLong
 * @Date 2021/8/1910:47
 * @Jia Jia开开心心
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${auth.ttl}")
    private long ttl;

    /**
     * 申请令牌
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    @Override
    public AuthToken applyToken(String username, String password, String clientId, String clientSecret) {

        //1. 采用客户端负载均衡，从eureka获取认证服务的ip 和端口
        ServiceInstance serviceInstance = loadBalancerClient.choose("USER-AUTH");
        URI uri = serviceInstance.getUri();
        //申请令牌地址
        String authUrl = uri + "/oauth/token";

        //2、header信息，包括了http basic认证信息, 封装请求头信息
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        //进行Base64编码,并将编码后的认证数据放到头文件中
        String httpbasic = getHttpBasic(clientId, clientSecret);
        headers.add("Authorization", httpbasic);

        //3、指定认证类型、账号、密码, 封装请求体信息
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type","password");
        body.add("username", username);
        body.add("password", password);
        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        //4. 指定 restTemplate当遇到400或401响应时候也不要抛出异常，也要正常返回值
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //当响应的值为400或401时候也要正常响应，不要抛出异常
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    //异常代号不是400, 401, 正常抛出异常
                    super.handleError(response);
                }
            }
        });

        //5. 远程调用申请令牌
        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
        //6. 获取响应体
        Map tokenMap = exchange.getBody();

        //7. 判断令牌是否申请成功
        if (tokenMap == null ||
                tokenMap.get("access_token") == null ||
                tokenMap.get("refresh_token") == null ||
                tokenMap.get("jti") == null) {
            throw new RuntimeException("申请令牌失败");
        }

        //8. 封装自定义令牌对象
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String) tokenMap.get("access_token"));
        authToken.setRefreshToken((String) tokenMap.get("refresh_token"));
        authToken.setJti((String) tokenMap.get("jti"));

        //9. 短令牌作为key, 长令牌作为value存入Redis中
        stringRedisTemplate.boundValueOps(authToken.getJti()).set(authToken.getAccessToken(),ttl, TimeUnit.SECONDS);
        return authToken;
    }

    private String getHttpBasic(String clientId, String clientSecret) {

        String value = clientId+":"+clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        return "Basic "+new String(encode);
    }
}
