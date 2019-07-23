# -*- encoding=utf8 -*-
#广告位素材
import inspect
import os
from airtest.core.cv import Template

_parentPath = os.path.abspath(os.path.dirname(inspect.getfile(inspect.currentframe())) + os.path.sep + ".")
_rootPath = os.path.abspath(os.path.dirname(_parentPath) + os.path.sep + ".")

heathHomePageBoothMaterial={
        "APP启动页广告": Template(_rootPath+"\BoothMaterial\SP002.jpg"),
        "顶通健康面": Template(_rootPath+"\BoothMaterial\SP001.jpg"),
        "健康首页悬浮球广告": Template(_rootPath+"\BoothMaterial\MP007.jpg"),
        "健康首页顶部Banner": Template(_rootPath+"\BoothMaterial\MP009.jpg"),
        "首页公共营销Banner": Template(_rootPath+"\BoothMaterial\MP006.jpg"),
        "健康首页中部轮播广告": Template(_rootPath+"\BoothMaterial\MP001.jpg"),
        "健康首页头条信息流": Template(_rootPath+"\BoothMaterial\MP013.jpg")
    }

MedicalHomePage={
    "顶通医疗面": Template(_rootPath+"\BoothMaterial\SP005.jpg"),
    "医疗首页悬浮球广告": Template(_rootPath+"\BoothMaterial\MP014.jpg"),
    "医疗免费版首页顶部Banner": Template(_rootPath+"\BoothMaterial\MP011.jpg")
}

heathHeadlinesPageBoothMaterial={
    "健康头条信息流":Template(_rootPath+"\BoothMaterial\TT001.jpg"),
    "头条信息流列表页悬浮球":Template(_rootPath+"\BoothMaterial\TT030.jpg"),
    "头条焦点图":Template(_rootPath+"\BoothMaterial\TT006.jpg"),
    "头条文章详情页悬浮球":Template(_rootPath+"\BoothMaterial\TT029.jpg"),
    "头条内容页中部Banner":Template(_rootPath+"\BoothMaterial\TT002.jpg"),
    "头条资讯详情页精彩推荐":Template(_rootPath+"\BoothMaterial\TT004.jpg"),
    "头条资讯详情页优惠券广告":Template(_rootPath+"\BoothMaterial\TT061.jpg"),
    "头条资讯详情页中部医美入口":Template(_rootPath+"\BoothMaterial\TT022.jpg"),
    "头条通频道顶部运营球":Template(_rootPath+"\BoothMaterial\TT060.jpg"),
    "直播栏目中部横幅Banner":Template(_rootPath+"\BoothMaterial\LV342.jpg"),
    "短视频列表轮播banner":Template(_rootPath+"\BoothMaterial\LV021.jpg"),
}

heathShoppingMallPageBoothMaterial={
    "商城顶部轮播banner":Template(_rootPath+"\BoothMaterial\ML001")
}

searchPageBoothMaterial={
    "主搜Banner":Template(_rootPath+"\BoothMaterial\SE001.jpg"),
    "搜索商品广告位":Template(_rootPath+"\BoothMaterial\SE030.jpg"),
    "头条资讯搜索原生广告位":Template(_rootPath+"\BoothMaterial\SE011.jpg")
}

LandingPageMaterial={
    "baiduLandingPageMaterial":Template(_rootPath+"\\BoothMaterial\\baiduLandingPage.jpg"),
}


