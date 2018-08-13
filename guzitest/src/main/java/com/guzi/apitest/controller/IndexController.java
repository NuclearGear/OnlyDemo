package com.guzi.apitest.controller;

import ads_serving.proto.AiqiyiBidding;

import com.google.openrtb.OpenRtb;
import com.google.openrtb.youdao.OpenRtbYDExtForDsp;
import com.google.protobuf.ExtensionRegistry;
import com.googlecode.protobuf.format.JsonFormat;
import com.guzi.apitest.utils.HttpRequest;

import de.googleadx.RealtimeBidding;
import de.proto.baidu.BaiduBidding;
import de.proto.mvad.MaxBiddingV35;
import de.proto.tanx.TanxBidding;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import toutiao_ssp.api.ToutiaoBidding;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
    	String exchageName = request.getParameter("exchageName");
        String jsonFormat = request.getParameter("request");
        if(StringUtils.isEmpty(jsonFormat)){
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
        }
/*        switch(exchageName){
        case "":
            System.out.println("0");
            break;
        case "mediamax":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break;
        case "adx":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break;
        case "tanx":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break;
        case "baidu":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break;    
        case "max":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break;       
        case "inmobi":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break;     
        case "yex":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break;
        case "ifeng":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break; 
        case "oppo":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break; 
        case"aiqiyi":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
        case "toutiao":
            jsonFormat ="{\"id\":\"HWN7a3sVm27cSSj50G6\",\"tmax\":100,\"site\":{\"id\":\"1843\",\"domain\":\"http://www.pptv.com\",\"page\":\"https://images.sohu.com/bill/s2018/materials/sn/0316/1531.html\",\"publisher\":{\"id\":\"3733\"},\"allyessitetype\":\"M01N02v2012.1\",\"allyespageform\":\"R10101v2012.1\"},\"device\":{\"dnt\":0,\"ua\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\",\"os\":\"ios\",\"ip\":\"114.92.105.81\",\"language\":\"zh\",\"js\":1,\"devicetype\":2,\"connectiontype\":0,\"h\":100,\"w\":640,\"pxratio\":1},\"user\":{\"id\":\"r%23gA0fUW0etbExgA0wUo0NfY\",\"cver\":2},\"imp\":[{\"id\":\"1\",\"https_flag\":0,\"banner\":{\"w\":640,\"h\":100,\"pos\":0,\"allyesadformat\":[0,1,2],\"allyesadform\":\"100\"},\"tagid\":\"3733-640100\",\"bidfloor\":0.01,\"bidfloorcur\":\"CNY\",\"audittype\":true,\"cbidfloor\":0.01}],\"cur\":[\"CNY\"],\"app\":{\"id\":\"101\",\"cat\":[\"29999\"],\"bundle\":\"com.suning.yigou\",\"publisher\":{\"id\":\"123\"}}}";
            model.addAttribute("request",jsonFormat);
            break;
        case "huawei":
            jsonFormat ="{123}";
            model.addAttribute("request",jsonFormat);
            break; 
        default:
            System.out.println("default");
            break;
    }*/

        return "index";
    }

    @RequestMapping("/send")
    @ResponseBody
    public String send(HttpServletRequest request, Model model){
        String ip = request.getParameter("ip");
        String sk = request.getParameter("sk");
        String dbname = request.getParameter("dbname");
        String path = request.getParameter("path");
        String exchageName = request.getParameter("exchageName");
        String jsonFormat = request.getParameter("request");
        String url = "http://"+ip+":"+sk+path;
        String result  = null;

        switch(exchageName){
            case "":
                System.out.println("0");
                break;
            case "mediamax":
                result =HttpRequest.sendPost(url, jsonFormat);
                break;
            case "adx":
                result = adx(jsonFormat, url);
                break;
            case "tanx":
                result = tanx(jsonFormat, url);
                break;
            case "baidu":
                result = baidu(jsonFormat, url);
                break;    
            case "max":
                result = max(jsonFormat, url);
                break;       
            case "inmobi":
            	result =HttpRequest.sendPost(url, jsonFormat);
                break;     
            case "yex":
                result = yex(jsonFormat, url);
                break;
            case "ifeng":
            	result =HttpRequest.sendPost(url, jsonFormat);
                break; 
            case "oppo":
            	result =HttpRequest.sendPost(url, jsonFormat);
                break; 
            case"aiqiyi":
            	result = aiqiyi(jsonFormat, url);
            case "toutiao":
                result = toutiao(jsonFormat, url);
                break;
            case "huawei":
            	result =HttpRequest.sendPost(url, jsonFormat);
                break; 
            default:
                System.out.println("default");
                break;
        }
    
        return result;
    }
 
    private String adx(String jsonFormat, String url) {
        if(StringUtils.isEmpty(jsonFormat)){
            jsonFormat ="{\"id\": \"ab486187585896169783f318a67ad882\",\"user\": {\"id\": \"0aece3ac590b9b5a4c9b861dd0e05704\"},\"site\": {\"id\": 2,\"content\": {\"url\": \"www.pps.tv\",\"keyword\": [\"配音语种\",\"琅琊榜2：刘昊然续写长林风骨\",\"富二代\",\"爱情\",\"内地\",\"内地剧场\",\"言情剧\",\"青春剧\",\"女神\",\"类型\",\"剧情\",\"欢喜冤家\",\"浪漫\",\"偶像剧\",\"海上牧云记：引燃魔幻九州\",\"小说改编\",\"现代\",\"校园\",\"国语\",\"剧情\",\"剧情\",\"剧情\",\"地区\",\"时代\",\"励志\",\"剧情\",\"都市\",\"鲜肉\"],\"len\": 2820,\"album_id\": 205901901,\"channel_id\": 2}},\"device\": {\"ua\": \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)\",\"ip\": \"139.170.253.103\",\"geo\": {\"country\": 86,\"metro\": 8663,\"city\": 866325},\"connection_type\": 0,\"platform_id\": 12,\"app_version\": \"6.2.57.5300\"},\"imp\": [{\"id\": \"0\",\"video\": {\"ad_zone_id\": 1000000000669,\"linearity\": 2,\"protocol\": 3,\"ad_type\": 6},\"bidfloor\": 300,\"campaign_id\": 81000447,\"floor_price\": [{\"industry\": 400000000,\"price\": 1000},{\"industry\": 100000000,\"price\": 300},{\"industry\": 300000000,\"price\": 400},{\"industry\": 600000000,\"price\": 400}]}], \"125\": [1]}";
        }

        RealtimeBidding.BidRequest.Builder builder = RealtimeBidding.BidRequest.newBuilder();
        try {
            JsonFormat.merge(jsonFormat, builder);
        } catch (JsonFormat.ParseException e) {
            e.printStackTrace();
        }
        RealtimeBidding.BidRequest contents = builder.build();

        return HttpRequest.sendGet(url,contents.toByteArray());
    }
    
    private String tanx(String jsonFormat, String url) {
        if(StringUtils.isEmpty(jsonFormat)){
            jsonFormat ="{\"id\": \"ab486187585896169783f318a67ad882\",\"user\": {\"id\": \"0aece3ac590b9b5a4c9b861dd0e05704\"},\"site\": {\"id\": 2,\"content\": {\"url\": \"www.pps.tv\",\"keyword\": [\"配音语种\",\"琅琊榜2：刘昊然续写长林风骨\",\"富二代\",\"爱情\",\"内地\",\"内地剧场\",\"言情剧\",\"青春剧\",\"女神\",\"类型\",\"剧情\",\"欢喜冤家\",\"浪漫\",\"偶像剧\",\"海上牧云记：引燃魔幻九州\",\"小说改编\",\"现代\",\"校园\",\"国语\",\"剧情\",\"剧情\",\"剧情\",\"地区\",\"时代\",\"励志\",\"剧情\",\"都市\",\"鲜肉\"],\"len\": 2820,\"album_id\": 205901901,\"channel_id\": 2}},\"device\": {\"ua\": \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)\",\"ip\": \"139.170.253.103\",\"geo\": {\"country\": 86,\"metro\": 8663,\"city\": 866325},\"connection_type\": 0,\"platform_id\": 12,\"app_version\": \"6.2.57.5300\"},\"imp\": [{\"id\": \"0\",\"video\": {\"ad_zone_id\": 1000000000669,\"linearity\": 2,\"protocol\": 3,\"ad_type\": 6},\"bidfloor\": 300,\"campaign_id\": 81000447,\"floor_price\": [{\"industry\": 400000000,\"price\": 1000},{\"industry\": 100000000,\"price\": 300},{\"industry\": 300000000,\"price\": 400},{\"industry\": 600000000,\"price\": 400}]}], \"125\": [1]}";
        }

        TanxBidding.BidRequest.Builder builder = TanxBidding.BidRequest.newBuilder();
        try {
            JsonFormat.merge(jsonFormat, builder);
        } catch (JsonFormat.ParseException e) {
            e.printStackTrace();
        }
        TanxBidding.BidRequest contents = builder.build();

        return HttpRequest.sendGet(url,contents.toByteArray());
    }
  
    private String baidu(String jsonFormat, String url) {
        if(StringUtils.isEmpty(jsonFormat)){
            jsonFormat ="{\"id\": \"ab486187585896169783f318a67ad882\",\"user\": {\"id\": \"0aece3ac590b9b5a4c9b861dd0e05704\"},\"site\": {\"id\": 2,\"content\": {\"url\": \"www.pps.tv\",\"keyword\": [\"配音语种\",\"琅琊榜2：刘昊然续写长林风骨\",\"富二代\",\"爱情\",\"内地\",\"内地剧场\",\"言情剧\",\"青春剧\",\"女神\",\"类型\",\"剧情\",\"欢喜冤家\",\"浪漫\",\"偶像剧\",\"海上牧云记：引燃魔幻九州\",\"小说改编\",\"现代\",\"校园\",\"国语\",\"剧情\",\"剧情\",\"剧情\",\"地区\",\"时代\",\"励志\",\"剧情\",\"都市\",\"鲜肉\"],\"len\": 2820,\"album_id\": 205901901,\"channel_id\": 2}},\"device\": {\"ua\": \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)\",\"ip\": \"139.170.253.103\",\"geo\": {\"country\": 86,\"metro\": 8663,\"city\": 866325},\"connection_type\": 0,\"platform_id\": 12,\"app_version\": \"6.2.57.5300\"},\"imp\": [{\"id\": \"0\",\"video\": {\"ad_zone_id\": 1000000000669,\"linearity\": 2,\"protocol\": 3,\"ad_type\": 6},\"bidfloor\": 300,\"campaign_id\": 81000447,\"floor_price\": [{\"industry\": 400000000,\"price\": 1000},{\"industry\": 100000000,\"price\": 300},{\"industry\": 300000000,\"price\": 400},{\"industry\": 600000000,\"price\": 400}]}], \"125\": [1]}";
        }

        BaiduBidding.BidRequest.Builder builder = BaiduBidding.BidRequest.newBuilder();
        try {
            JsonFormat.merge(jsonFormat, builder);
        } catch (JsonFormat.ParseException e) {
            e.printStackTrace();
        }
        BaiduBidding.BidRequest contents = builder.build();

        return HttpRequest.sendGet(url,contents.toByteArray());
    }
    
    private String max(String jsonFormat, String url) {
        if(StringUtils.isEmpty(jsonFormat)){
            jsonFormat ="{\"id\": \"ab486187585896169783f318a67ad882\",\"user\": {\"id\": \"0aece3ac590b9b5a4c9b861dd0e05704\"},\"site\": {\"id\": 2,\"content\": {\"url\": \"www.pps.tv\",\"keyword\": [\"配音语种\",\"琅琊榜2：刘昊然续写长林风骨\",\"富二代\",\"爱情\",\"内地\",\"内地剧场\",\"言情剧\",\"青春剧\",\"女神\",\"类型\",\"剧情\",\"欢喜冤家\",\"浪漫\",\"偶像剧\",\"海上牧云记：引燃魔幻九州\",\"小说改编\",\"现代\",\"校园\",\"国语\",\"剧情\",\"剧情\",\"剧情\",\"地区\",\"时代\",\"励志\",\"剧情\",\"都市\",\"鲜肉\"],\"len\": 2820,\"album_id\": 205901901,\"channel_id\": 2}},\"device\": {\"ua\": \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)\",\"ip\": \"139.170.253.103\",\"geo\": {\"country\": 86,\"metro\": 8663,\"city\": 866325},\"connection_type\": 0,\"platform_id\": 12,\"app_version\": \"6.2.57.5300\"},\"imp\": [{\"id\": \"0\",\"video\": {\"ad_zone_id\": 1000000000669,\"linearity\": 2,\"protocol\": 3,\"ad_type\": 6},\"bidfloor\": 300,\"campaign_id\": 81000447,\"floor_price\": [{\"industry\": 400000000,\"price\": 1000},{\"industry\": 100000000,\"price\": 300},{\"industry\": 300000000,\"price\": 400},{\"industry\": 600000000,\"price\": 400}]}], \"125\": [1]}";
        }

        MaxBiddingV35.BidRequest.Builder builder = MaxBiddingV35.BidRequest.newBuilder();
        try {
            JsonFormat.merge(jsonFormat, builder);
        } catch (JsonFormat.ParseException e) {
            e.printStackTrace();
        }
        MaxBiddingV35.BidRequest contents = builder.build();

        return HttpRequest.sendGet(url,contents.toByteArray());
    }

    private String yex(String jsonFormat, String url) {
        if(StringUtils.isEmpty(jsonFormat)){
            jsonFormat ="{\n\"id\": \"2693dff1-fb33-4c42-b403-597258d0dcd3\",\n\"imp\": [{\n  \"id\": \"1\",\n  \"tagid\": \"fcf685be1d378879098a428ee5aa4ace\",\n  \"bidfloor\": 1100.0,\n  \"bidfloorcur\": \"CNY\",\n  \"secure\": true,\n  \"native\" :{\n    \"request_native\" :{\n      \"ver\": \"1\",\n      \"plcmtcnt\": 3,\n      \"assets\": [\n\t  {\n        \"id\": 1,\n        \"required\": true,\n        \"img\": {\n          \"w\": 200,\n          \"h\": 200\n        },\n        \"com.google.openrtb.youdao.sasset\": {\n          \"id\": 1,\n          \"required\": true,\n          \"img\": {\n            \"w\": 54,\n            \"h\": 54\n          }\n        }\n      },\n\t  {\n        \"id\": 2,\n        \"required\": true,\n        \"title\": {\n\t\t\t     \"len\": 20\n        },\n        \"com.google.openrtb.youdao.sasset\": {\n          \"id\": 2,\n          \"required\": true,\n          \"title\": {\n            \"len\": 10\n          }\n        }\n      }\n\t  ]\n    }\n  },\n  \"com.google.openrtb.youdao.ssid\": 11\n}],\n\"app\": {\n  \"id\": \"2500\",\n  \"name\": \"-iOS\",\n  \"bundle\": \"897003024\",\n  \"publisher\": {\n    \"id\": \"728\"\n  },\n  \"com.google.openrtb.youdao.ydAppCategory\": 603\n},\n\"device\": {\n  \"dnt\": false,\n  \"ua\": \"Mozilla/5.0 (iPhone; CPU iPhone OS 11_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E216 MailMaster/6.4.1.1188\",\n  \"ip\": \"139.222.182.224\",\n  \"geo\": {\n  },\n  \"didmd5\": \"46def0107a5a393f581cb5da4bb13cf3\",\n  \"make\": \"Apple\",\n  \"model\": \"iPhone\",\n  \"os\": \"IOS\",\n  \"osv\": \"11.3\",\n  \"connectiontype\": 2,\n  \"ifa\": \"63B422F7-6071-44EE-B2D7-1AC688D3F82A\",\n  \"com.google.openrtb.youdao.sdkVersion\": \"2.2.0\"\n},\n\"at\": 2,\n\"tmax\": 200,\n\"test\": false,\n\"com.google.openrtb.youdao.tfc\": true\n}";
        }
        ExtensionRegistry registry  = ExtensionRegistry.newInstance();
        registry.add(OpenRtbYDExtForDsp.ssid);
        registry.add(OpenRtbYDExtForDsp.battri);
        registry.add(OpenRtbYDExtForDsp.did);
        registry.add(OpenRtbYDExtForDsp.dpid);
        registry.add(OpenRtbYDExtForDsp.tfc);
        registry.add(OpenRtbYDExtForDsp.dataAssetType);


        OpenRtb.BidRequest.Builder builder = OpenRtb.BidRequest.newBuilder();
        try {
            JsonFormat.merge(jsonFormat, registry, builder);
        } catch (JsonFormat.ParseException e) {
            e.printStackTrace();
        }
        OpenRtb.BidRequest contents = builder.build();

        return HttpRequest.sendGet(url,contents.toByteArray());
    }
    
    private String aiqiyi(String jsonFormat, String url) {
        if(StringUtils.isEmpty(jsonFormat)){
            jsonFormat ="{\"id\": \"ab486187585896169783f318a67ad882\",\"user\": {\"id\": \"0aece3ac590b9b5a4c9b861dd0e05704\"},\"site\": {\"id\": 2,\"content\": {\"url\": \"www.pps.tv\",\"keyword\": [\"配音语种\",\"琅琊榜2：刘昊然续写长林风骨\",\"富二代\",\"爱情\",\"内地\",\"内地剧场\",\"言情剧\",\"青春剧\",\"女神\",\"类型\",\"剧情\",\"欢喜冤家\",\"浪漫\",\"偶像剧\",\"海上牧云记：引燃魔幻九州\",\"小说改编\",\"现代\",\"校园\",\"国语\",\"剧情\",\"剧情\",\"剧情\",\"地区\",\"时代\",\"励志\",\"剧情\",\"都市\",\"鲜肉\"],\"len\": 2820,\"album_id\": 205901901,\"channel_id\": 2}},\"device\": {\"ua\": \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)\",\"ip\": \"139.170.253.103\",\"geo\": {\"country\": 86,\"metro\": 8663,\"city\": 866325},\"connection_type\": 0,\"platform_id\": 12,\"app_version\": \"6.2.57.5300\"},\"imp\": [{\"id\": \"0\",\"video\": {\"ad_zone_id\": 1000000000669,\"linearity\": 2,\"protocol\": 3,\"ad_type\": 6},\"bidfloor\": 300,\"campaign_id\": 81000447,\"floor_price\": [{\"industry\": 400000000,\"price\": 1000},{\"industry\": 100000000,\"price\": 300},{\"industry\": 300000000,\"price\": 400},{\"industry\": 600000000,\"price\": 400}]}], \"125\": [1]}";
        }

        AiqiyiBidding.BidRequest.Builder builder = AiqiyiBidding.BidRequest.newBuilder();
        try {
            JsonFormat.merge(jsonFormat, builder);
        } catch (JsonFormat.ParseException e) {
            e.printStackTrace();
        }
        AiqiyiBidding.BidRequest contents = builder.build();

        return HttpRequest.sendGet(url,contents.toByteArray());
    }

    private String toutiao(String jsonFormat, String url) {
        if(StringUtils.isEmpty(jsonFormat)){
            jsonFormat ="{\n  \"request_id\": \"20161208170906010003024014504905\",\"dsp_id\":11,\n  \"api_version\": \"2.1\",\n  \"adslots\": [\n    {\n      \"id\": \"3ff46df0cc8c49cd\",\n      \"banner\": [\n        {\n          \"width\": 580,\n          \"height\": 240,\n          \"pos\": 2,\n          \"sequence\": \"3\"\n        }\n      ],\n      \"ad_type\": [\n        4,\n        3,\n        1,\n        2,\n        11\n      ],\n      \"bid_floor\": 660,\n      \"channel_id\": 3189398998\n    }\n  ],\n  \"app\": {\n    \"id\": \"1\",\n    \"name\": \"news_article\",\n    \"ver\": \"590\"\n  },\n  \"device\": {\n    \"dnt\": false,\n    \"ua\": \"\",\n    \"ip\": \"223.74.30.85\",\n    \"geo\": {\n      \"lat\": 23.2591,\n      \"lon\": 116.08233,\n      \"city\": \"揭阳\"\n    },\n    \"device_id\": \"861868035432200\",\n    \"make\": \"unknown\",\n    \"model\": \"m1 metal\",\n    \"os\": \"android\",\n    \"osv\": \"5.1\",\n    \"connection_type\": 2,\n    \"device_type\": 1,\n    \"android_id\": \"3b693920ca500ad5\"\n  },\n  \"user\": {\n    \"id\": \"55040445411\",\n    \"yob\": \"18\",\n    \"gender\": 2\n  }\n}";
        }

        ToutiaoBidding.BidRequest.Builder builder = ToutiaoBidding.BidRequest.newBuilder();
        try {
            JsonFormat.merge(jsonFormat, builder);
        } catch (JsonFormat.ParseException e) {
            e.printStackTrace();
        }
        ToutiaoBidding.BidRequest contents = builder.build();

        return HttpRequest.sendGet(url,contents.toByteArray());
    }
}
