package com.my.tools;



import com.alibaba.common.lang.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.tools.JavaCompiler;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wq105907
 * @date 2018/11/5
 */
public class Main {

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
        get.setURI(new URI("http://zonemng.alipay.com/rest/originElasticRule?version=", false));

        int statusCode = client.executeMethod(get);
        if (statusCode == HttpStatus.SC_OK) {
            String chaset = get.getResponseCharSet();
            if (StringUtil.isBlank(chaset)) {
                chaset = "utf-8";
            }
            System.out.println(new String(get.getResponseBody(), chaset));
        }
    }

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }

        stack1.push(node);
    }

    public int pop() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        return stack2.pop();
    }
}

