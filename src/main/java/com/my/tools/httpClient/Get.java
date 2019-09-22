package com.my.tools.httpClient;

import com.alibaba.common.lang.StringUtil;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * @author wq
 * @date 2019/9/18
 */
public class Get {
    public static void main(String[] args) throws Exception{
        HttpConnectionManagerParams connParams = new HttpConnectionManagerParams();
        connParams.setDefaultMaxConnectionsPerHost(256);
        connParams.setMaxTotalConnections(512);
        connParams.setConnectionTimeout(10 * 1000);
        connParams.setSoTimeout(30 * 1000);
        connParams.setStaleCheckingEnabled(true);

        HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        httpConnectionManager.setParams(connParams);
        HttpClient client = new HttpClient();

        GetMethod get = new GetMethod();
        get.setURI(new URI("http://baidu.com", false));

        int statusCode = client.executeMethod(get);
        if (statusCode == HttpStatus.SC_OK) {
            String chaset = get.getResponseCharSet();
            if (StringUtil.isBlank(chaset)) {
                chaset = "utf-8";
            }
            System.out.println(new String(get.getResponseBody(), chaset));
        }
    }

}
