# -*- encoding=utf8 -*-

from airtest.core.api import *
from poco.drivers.android.uiautomation import AndroidUiautomationPoco
from sendqqEmail import *

poco = AndroidUiautomationPoco(use_airtest_input=True, screenshot_each_action=False)
class nice_airtest():
    def buy(self,name,size,price):
        if poco(text=name).exists():
            poco(text=name).click()
        else:
            poco("com.nice.main:id/fragment").offspring("android.view.ViewGroup").offspring("android:id/list").child("android.widget.RelativeLayout")[5].swipe([0.0444, -0.7622])
            sleep(0.2)
            poco(text=name).click()
        poco("com.nice.main:id/sdv_cover").click()
        poco("com.nice.main:id/tv_buy").click()

        if poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).exists():
            goodsPrice=poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).parent().offspring("com.nice.main:id/ll_price").offspring("com.nice.main:id/tv_price").get_text().replace("¥","")
            if(goodsPrice==price):
                poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).click()
        else:
            for i in range(10):
                try:
                    poco("android.support.v7.widget.RecyclerView").child("android.widget.RelativeLayout")[4].swipe([0.0444, -0.5555])
                except Exception as e:
                    poco("android.support.v7.widget.RecyclerView").child("android.widget.RelativeLayout")[4].swipe([0.0444, -0.5555])
                sleep(0.2)
                if poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).exists():
                    goodsPrice=poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).parent().offspring("com.nice.main:id/ll_price").offspring("com.nice.main:id/tv_price").get_text().replace("¥","")
                    if(int(goodsPrice)<=int(price)):
                        poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).click()
                        #  购买下单
                        poco("com.nice.main:id/tv_storage_title").click()
                        poco("com.nice.main:id/btn_submit").click()
                        poco("com.nice.main:id/btn_confirm").click()
                        subject="已自动下单"+name+size+"码，请速确认是否购买成功"
                        sendqqEmail(subject,subject)
                    else:
                        print("价格不满足预期,返回商品页")
                        poco("com.nice.main:id/titlebar_icon").click()
                        poco("com.nice.main:id/iv_back").click()
                        poco("com.nice.main:id/titlebar_icon").click()
                        poco("com.nice.main:id/txt_want").click()






if __name__ == "__main__":
    # name='''YEEZY BOOST 350 V2 2019年版 "SYNTH" 粉天使 亚洲限定'''
    #name='''YEEZY BOOST 350 V2 2019年版 "BLACK" 黑天使 黑魂'''
    #name='''YEEZY BOOST 350 V2 2019年版 "CLAY" 粘土 美洲限定'''
    #name='''YEEZY BOOST 350 V2 2019年版 "BLACK REFLECTIVE" 黑满天星'''
    #name='''OFF-WHITE x AIR JORDAN 1 联名 2018年版 "北卡蓝"'''
    # name='''YEEZY BOOST 350 V2 "CREAM WHITE" 白冰淇淋'''
    # name='.''NIKE SB x AIR JORDAN 1 联名 2019年版 "LA TO CHICAGO" 湖人 芝加哥 刮刮乐'''
    name='''TRAVIS SCOTT x AIR JORDAN 1 联名 "CACTUS JACK" 2018年版 反钩 倒钩'''
    size="42.5"
    b=nice_airtest()
    b.buy("粉天使",size,"1998")