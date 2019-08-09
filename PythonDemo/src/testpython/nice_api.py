# coding:utf-8
import json

import requests,datetime,time,threading,collections,re
import smtplib
from email.mime.text import MIMEText
import datetime
import urllib3
urllib3.disable_warnings()

h = {
    "Cache-Control": "no-cache",
    "Host": "api.oneniceapp.com",
    "Content-Type": "application/json; charset=utf-8",
    "Content-Length": "123",
    "Connection": "Keep-Alive",
    "Accept": "*/*",
    "Accept-Encoding": "gzip, deflate",
    "Accept-Language": "zh-Hans-CN;q=1, ja-JP;q=0.9",
    "User-Agent": "KKShopping/5.4.2 (iPhone 8; iOS 12.3.1; Scale/2.00)"
}

def nicebuy():
    url='''https://api.oneniceapp.com/Sneakerpurchase/pub?sign=af495f1f6328a2301681bc7c1b31fc1b&token=ujt9rjESVVTNgieTokeuP6We0yEJmsYA&did=77e71f31fc1dec1e7eaebf9f6f822aa3&osn=android&osv=6.0&appv=5.4.3&net=0-0-wifi&longitude=121.45174698690086&latitude=31.184714693907296&ch=main&lp=50.0&geoacc=50.0&geotype=wifi&ver=1&ts=1564381572299&sw=1080&sh=1920&la=zh-CN&seid=de9d1e86151ac7401349175dd43d9abf&src=&tpid=&pre_module_id=&module_id=&lm=mobile&abroad=no&country=&sm_dt=201907051933014593e25f7ddd86177727b95cc95e1c140171e2bb8719a5d7&n_ssid=%22PingAnNet_BX%22&n_bssid=00:fe:c8:f0:70:aa&n_dns=43.254.104.76&n_lac=0&n_tac=&n_cid=0&n_mcc=0&n_mnc=0&n_bsss=-1&n_misi=unknown&n_ssn&n_sci=&n_son=&g_x=0.0&g_y=0.0&g_z=0.0&a_x=5.269&a_y=6.307&a_z=4.943&dt=ZTE%20BV0701&dn=ZTE%20BV0701_8507&im=A00000568D4FA1&dvi=753f3f2a559523f9ea6dba752c8c7e0cf70629c2c62299751f04af52afd57a25'''
    data='''nice-sign-v1://222db92049e709336b07ef0a178108af:vh5je5r1pon41f6j/{"id":"169989","size_id":"10101","stock_id":"128","price":"3369","pay_type":"alipay","address_id":"","unique_token":"8VwgFuDfZr-28d787b0b99012de655efe302df3af1a-iLx1JS09XYBGpPjd","sale_id":"","need_storage":"yes","coupon_id":"","substitute_id":"","batch":"0"}'''
    # data2='''nice-sign-v1://332a8fe23663a9d14f1aa049b746b30e:bhbqu9tq0fwlp4r6/{"id":"199600","size_id":"10109","stock_id":"128","price":"2269","pay_type":"alipay","address_id":"","unique_token":"jerioX9vVR-31243735471a665b89b2cf7680aa70a8-XLirMSRAvgFQzf3j","sale_id":"","need_storage":"yes","coupon_id":"","substitute_id":"","batch":"0"}'''
    r = requests.post(url, headers=h, data=data, verify=False, timeout=3)
    print(r.status_code)
    print(r.text)


def getprice():
    url="http://api.oneniceapp.com/Sneakerpurchase/priceInfos?a_x=0.006302&a_y=-0.013901&a_z=-0.997620&abroad=no&amap_latitude=31.251428&amap_longitude=121.572066&appv=5.4.2.20&ch=AppStore_5.4.2.20&did=be09b9b291195c16a08ba082a1a4f05b&dn=Only%20iPhone&dt=iPhone10%2C1&g_x=-0.013020&g_y=-0.003731&g_z=-0.012055&geoacc=10&im=8B828531-62AC-4D20-A4EC-1B06DC4DCC2B&la=cn&latitude=31.253600&lm=weixin&longitude=121.567834&lp=-1.000000&n_bssid=&n_dns=fe80%3A%3A1&n_ssid=&net=0-0-wifi&osn=iOS&osv=12.3.1&seid=62ba1882f758643933503deb6c1dd9a4&sh=667.000000&sm_dt=201902272149077fd4d01a151205278bbc038390e761e30157f0351da1aed5&sw=375.000000&token=wn-GBGsbbwxESEJvSly_PX-Mmk89ZYUn&ts=1562487032370"
    heiyeezyData='''nice-sign-v1://c439269145ac860474a0949378d88fa1:b235d822d39e03ed/{"id":"169989","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    r = requests.post(url, headers=h, data=heiyeezyData, verify=False, timeout=3)
    print(r.status_code)
    print(r.text)
    if(r.status_code==200):
        response=r.text
        # print (response)
        # print (type(response))
        d = json.loads(response,encoding='utf-8')
        # print (d)
        xianhuo={}
        shangou={}
        sizeid={}
        try:
            for i in d["data"]["list"]:
                if(i["stock_id"]=="1"):
                    size=str(i["size"])
                    price=str(i["price"])
                    xianhuo[size]=price
                elif(i["stock_id"]=="128"):
                    size=str(i["size"])
                    price=str(i["price"])
                    s=str(i["size_id"])
                    shangou[size]=price
                    sizeid[size]=s
        except:
            for i in d["data"]["tab_list"]:
                for x in i["list"]:
                    # print (x)
                    if (x["stock_id"] == "1"):
                        size = str(x["size"])
                        price = str(x["price"])
                        xianhuo[size] = price
                    elif (x["stock_id"] == "128"):
                        size = str(x["size"])
                        price = str(x["price"])
                        s = str(i["size_id"])
                        shangou[size] = price
                        sizeid[size] = s

        print ("现货"+str(xianhuo))
        print ("闪购"+str(shangou))
        print(str(sizeid))
if __name__ == "__main__":
    nicebuy()
    # getprice()