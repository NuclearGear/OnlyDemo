package com.guzi.apitest.protobuf;

import com.googlecode.protobuf.format.JsonFormat;
import toutiao_ssp.api.ToutiaoBidding;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        //推荐流请求
        String jsonFormat ="{\n  \"request_id\": \"20161208170906010003024014504905\",\"dsp_id\":11,\n  \"api_version\": \"2.1\",\n  \"adslots\": [\n    {\n      \"id\": \"3ff46df0cc8c49cd\",\n      \"banner\": [\n        {\n          \"width\": 580,\n          \"height\": 240,\n          \"pos\": 2,\n          \"sequence\": \"3\"\n        }\n      ],\n      \"ad_type\": [\n        4,\n        3,\n        1,\n        2,\n        11\n      ],\n      \"bid_floor\": 660,\n      \"channel_id\": 3189398998\n    }\n  ],\n  \"app\": {\n    \"id\": \"1\",\n    \"name\": \"news_article\",\n    \"ver\": \"590\"\n  },\n  \"device\": {\n    \"dnt\": false,\n    \"ua\": \"\",\n    \"ip\": \"223.74.30.85\",\n    \"geo\": {\n      \"lat\": 23.2591,\n      \"lon\": 116.08233,\n      \"city\": \"揭阳\"\n    },\n    \"device_id\": \"861868035432200\",\n    \"make\": \"unknown\",\n    \"model\": \"m1 metal\",\n    \"os\": \"android\",\n    \"osv\": \"5.1\",\n    \"connection_type\": 2,\n    \"device_type\": 1,\n    \"android_id\": \"3b693920ca500ad5\"\n  },\n  \"user\": {\n    \"id\": \"55040445411\",\n    \"yob\": \"18\",\n    \"gender\": 2\n  }\n}";

        ToutiaoBidding.BidRequest.Builder builder = ToutiaoBidding.BidRequest.newBuilder();
        JsonFormat.merge(jsonFormat, builder);
        ToutiaoBidding.BidRequest contents = builder.build();

        String json = JsonFormat.printToString(contents);

        System.out.println(json);
    }

}
