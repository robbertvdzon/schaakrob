package com.vdzon.java.robotclient;

import com.vdzon.java.robitapi.RobotAansturing;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class RobotAansturingClient implements RobotAansturing {

  private final CloseableHttpClient httpClient = HttpClients.createDefault();
  private String host = "";

  public RobotAansturingClient(String host) {
    this.host = host;
  }

  @Override
  public void movetoVlak(String vlak) {
    post(host+"/api/movevlak",vlak);
  }

  @Override
  public void moveto(int x, int y) {
    post(host+"/api/move",x+","+y);
  }

  @Override
  public void homeVert() {
    get(host+"/api/home_vert");
  }

  @Override
  public void homeHor() {
    get(host+"/api/home_hor");
  }

  @Override
  public void sleep() {
    get(host+"/api/sleep");
  }

  @Override
  public void clamp() {
    get(host+"/api/clamp");
  }

  @Override
  public void release() {
    get(host+"/api/release");
  }

  @Override
  public void rebuild() {
    get(host+"/api/rebuild");
  }

  @Override
  public void restart() {
    get(host+"/api/restart");
  }

  @Override
  public String getA8() {
    return  get(host+"/api/a8");
  }

  @Override
  public void setA8(String pos) {
    post(host+"/api/a8", pos);
  }

  @Override
  public String getH1() {
    return  get(host+"/api/h1");
  }

  @Override
  public void setH1(String pos) {
    post(host+"/api/h1", pos);
  }

  @Override
  public String getDemoString() {
    return  get(host+"/api/demo");
  }

  @Override
  public void setDemoString(String demoString) {
    post(host+"/api/demo", demoString);
  }

  @Override
  public void runDemoOnce() {
    get(host+"/api/startdemoonce");
  }

  @Override
  public void runDemoLoop() {
    get(host+"/api/startdemoloop");
  }

  @Override
  public void stopDemo() {
    get(host+"/api/stopdemo");
  }


  private String get(String url) {
    try {
      System.out.println("call:"+url);
      HttpGet request = new HttpGet(url);
      try (CloseableHttpResponse response = httpClient.execute(request)) {
        System.out.println(response.getStatusLine().toString());
        HttpEntity entity = response.getEntity();
//        Header headers = entity.getContentType();
//        System.out.println(headers);
//

        if (entity != null) {
          // return it as a String
          String result = EntityUtils.toString(entity);
          System.out.println("Body:"+result);
          return result;
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null; // Dit mooi oplossen!
  }

  private String post(String url, String body) {
    try {
      System.out.println("call post:"+url);
      HttpPost request = new HttpPost(url);
      request.setEntity(new StringEntity(body));
      try (CloseableHttpResponse response = httpClient.execute(request)) {
        System.out.println(response.getStatusLine().toString());
        HttpEntity entity = response.getEntity();
//        Header headers = entity.getContentType();
//        System.out.println(headers);
//
        if (entity != null) {
          // return it as a String
          return EntityUtils.toString(entity);
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null; // Dit mooi oplossen!
  }


}
