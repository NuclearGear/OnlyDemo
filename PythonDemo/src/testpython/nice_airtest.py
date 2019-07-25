# -*- encoding=utf8 -*-

from airtest.core.api import *
from poco.drivers.android.uiautomation import AndroidUiautomationPoco

import sendqqEmail

poco = AndroidUiautomationPoco(use_airtest_input=True, screenshot_each_action=False)

class nice_airtest():

    def waitFor(self,By,value,timeout=5):
        try:
            if(By=="text"):
                element = poco(text=value).wait(timeout)
            elif(By=="name"):
                element = poco(name=value).wait(timeout)
            elif(By == "type"):
                element = poco(type=value).wait(timeout)
            elif(By == "xpath"):
                element = poco(value).wait(timeout)
            elif(By == "contains"):
                element = poco(textMatches='.*'+value+'.*').wait(timeout)
            else:
                raise Exception
        except Exception as e:
            raise Exception("参数不正确:"+By+"="+value)
        return  element

    def waitForVisibleOrNot(self, By, value,timeout=5):
        if self.waitFor(By, value,timeout).exists():
            return True
        else:
            return False

    def buy(self,name,size,price):
        try:
            if (poco(text="想要的nice好货").exists()):
                if self.waitForVisibleOrNot("contains",name,1):
                    self.waitFor("contains", name,1).click()
                else:
                    swipe((150, 800), (150, 0))
                    sleep(0.2)
                    if self.waitForVisibleOrNot("contains", name, 1):
                        self.waitFor("contains", name, 1).click()
                    else:
                        swipe((150, 800), (150, 0))
                        sleep(0.2)
                        self.waitFor("contains", name).click()

            else:
                stop_app("com.nice.main")
                start_app("com.nice.main")
                poco(text="我").click()
                poco("com.nice.main:id/txt_want").click()
                sleep(2)
                if self.waitForVisibleOrNot("contains",name,4):
                    self.waitFor("contains", name,2).click()
                else:
                    swipe((150, 800), (150, 0))
                    sleep(0.2)
                    if self.waitForVisibleOrNot("contains", name, 1):
                        self.waitFor("contains", name, 1).click()
                    else:
                        swipe((150, 800), (150, 0))
                        sleep(0.2)
                        self.waitFor("contains", name).click()

            poco("com.nice.main:id/sdv_cover").click()
            poco("com.nice.main:id/tv_buy").click()

            if poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).exists():
                goodsPrice=poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).parent().offspring("com.nice.main:id/ll_price").offspring("com.nice.main:id/tv_price").get_text().replace("¥","")
                if(int(goodsPrice)<=int(price)):
                    poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).click()
                    #  购买下单
                    poco("com.nice.main:id/tv_storage_title").click()
                    poco("com.nice.main:id/btn_submit").click()
                    poco("com.nice.main:id/btn_confirm").click()
                    print("已自动下单" + name + size + "码,价格:" + goodsPrice)
                    subject = "已自动下单" + name + size + "码,价格:" + goodsPrice + ",请速确认是否购买成功"
                    sendqqEmail.sendqqEmail(subject, subject)
                    stop_app("com.nice.main")
                    start_app("com.nice.main")
                    poco(text="我").click()
                    poco("com.nice.main:id/txt_want").click()
                else:
                    print("价格不满足预期,返回商品页")
                    stop_app("com.nice.main")
                    start_app("com.nice.main")
                    poco(text="我").click()
                    poco("com.nice.main:id/txt_want").click()
            else:
                for i in range(14):
                    swipe((150, 800), (150, 0))
                    sleep(0.2)
                    if poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).exists():
                        goodsPrice=poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).parent().offspring("com.nice.main:id/ll_price").offspring("com.nice.main:id/tv_price").get_text().replace("¥","")
                        if(int(goodsPrice)<=int(price)):
                            poco("android.support.v7.widget.RecyclerView").offspring("com.nice.main:id/rdv_desc").sibling("android.widget.RelativeLayout").offspring(text=size).click()
                            #  购买下单
                            poco("com.nice.main:id/tv_storage_title").click()
                            poco("com.nice.main:id/btn_submit").click()
                            poco("com.nice.main:id/btn_confirm").click()
                            print("已自动下单"+name+size+"码,价格:"+goodsPrice)
                            subject="已自动下单"+name+size+"码,价格:"+goodsPrice+",请速确认是否购买成功"
                            sendqqEmail.sendqqEmail(subject,subject)
                            stop_app("com.nice.main")
                            start_app("com.nice.main")
                            poco(text="我").click()
                            poco("com.nice.main:id/txt_want").click()
                            break
                        else:
                            print("价格不满足预期,返回商品页")
                            stop_app("com.nice.main")
                            start_app("com.nice.main")
                            poco(text="我").click()
                            poco("com.nice.main:id/txt_want").click()
                            break
        except Exception as e:
            print("购买失败，返回初始页")
            stop_app("com.nice.main")
            start_app("com.nice.main")
            poco(text="我").click()
            poco("com.nice.main:id/txt_want").click()




if __name__ == "__main__":
    # name='''YEEZY BOOST 350 V2 2019年版 "SYNTH" 粉天使 亚洲限定'''
    name='''黑天使'''
    #name='''YEEZY BOOST 350 V2 2019年版 "CLAY" 粘土 美洲限定'''
    #name='''YEEZY BOOST 350 V2 2019年版 "BLACK REFLECTIVE" 黑满天星'''
    #name='''OFF-WHITE x AIR JORDAN 1 联名 2018年版 "北卡蓝"'''
    # name='''YEEZY BOOST 350 V2 "CREAM WHITE" 白冰淇淋'''
    # name='''NIKE SB x AIR JORDAN 1 联名 2019年版 "LA TO CHICAGO" 湖人 芝加哥 刮刮乐'''
    # name='''TRAVIS SCOTT x AIR JORDAN 1 联名 "CACTUS JACK" 2018年版 反钩 倒钩'''
    size="42"
    b=nice_airtest()
    b.buy(name,size,"998")
    # sendqqEmail.sendqqEmail(name,name)