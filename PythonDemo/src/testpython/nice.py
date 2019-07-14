# coding:utf-8
import json

import requests,datetime,time,threading,collections,re
import smtplib
from email.mime.text import MIMEText

from nice_airtest import *


def sendqqEmail(subject,content):
    msg_from='1056396153@qq.com'                                 #发送方邮箱
    passwd='ipegauclyoczbdde'                                   #填入发送方邮箱的授权码
    msg_to='315118616@qq.com'                                  #收件人邮箱

    msg = MIMEText(content)
    msg['Subject'] = subject
    msg['From'] = msg_from
    msg['To'] = msg_to
    try:
        s = smtplib.SMTP_SSL("smtp.qq.com",465)#邮件服务器及端口号
        s.login(msg_from, passwd)
        s.sendmail(msg_from, msg_to, msg.as_string())
        # print ("发送邮件成功")
    except s.SMTPException as e:
        # print ("发送邮件失败")
        pass
    finally:
        s.quit()

def output_value(jsons, key):
    """
    通过参数key，在jsons中进行匹配并输出该key对应的value
    :param jsons: 需要解析的json串
    :param key: 需要查找的key
    :return:
    """
    key_value = ''
    if isinstance(jsons, dict):
        for json_result in jsons.values():
            if key in jsons.keys():
                key_value = jsons.get(key)
            else:
                output_value(json_result, key)
    elif isinstance(jsons, list):
        for json_array in jsons:
            output_value(json_array, key)
    if key_value!='':
        print(str(key)+" = "+str(key_value))

class nice():
    def getPrice(self,url,data):
        h = {
            "Cache-Control": "no-cache",
            "Host": "api.oneniceapp.com",
            "Content-Type": "application/json; charset=utf-8",
            "Content-Length": "123",
            "Connection": "Keep-Alive",
            "Accept": "*/*",
            "Accept-Encoding": "gzip, deflate",
            "Accept-Language": "zh-Hans-CN;q=1, ja-JP;q=0.9",
            "Accept-Encoding": "gzip, deflate",
            "User-Agent": "KKShopping/5.4.2 (iPhone 8; iOS 12.3.1; Scale/2.00)"
        }
        try:
            r = requests.post(url,headers=h,data=data,verify=False,timeout=1)
            return r
        except:
            return False




    def calculatePrice(self,response,name,fullname,chajia):
        try:
            if(response.status_code==200):
                response=r.text
                # print (response)
                # print type(response)
                d = json.loads(response,encoding='utf-8')
                # print (d)
                xianhuo={}
                shangou={}
                for i in d["data"]["list"]:
                    if(i["stock_id"]=="1"):
                        size=str(i["size"])
                        price=str(i["price"])
                        xianhuo[size]=price
                    elif(i["stock_id"]=="128"):
                        size=str(i["size"])
                        price=str(i["price"])
                        shangou[size]=price
                # print "现货"+str(xianhuo)
                # print "闪购"+str(shangou)
                print("."),
                for i in shangou:
                    # print i
                    if i in xianhuo:
                        price1=int(xianhuo.get(i))
                        price2=int(shangou.get(i))
                        if(chajia>0 and (price1-price2)>chajia):
                            subject=name+i+"码闪购价格低于现货价格"+str(price1-price2)+"元,现货价格:"+str(price1)+"元,闪购价格:"+str(price2)
                            content="现货价格:"+str(price1)+"元,闪购价格:"+str(price2)+"\n低"+str(price1-price2)+"元，请速查看app"
                            sendqqEmail(subject, content)
                            print ("-----------------------"+name+i+"码闪购价格低于现货价格"+str(price1-price2)+"元,现货价格:"+str(price1)+"元,闪购价格:"+str(price2)+"-----------------------")
                            #启动airtest购物商品
                            at=nice_airtest()
                            at.buy(fullname,i,price2)
                        elif(chajia==0 and price1>price2):
                            subject=name+i+"码闪购价格低于现货价格"
                            content="现货价格:"+str(price1)+"元,闪购价格:"+str(price2)
                            print (name+i+"码闪购价格低于现货价格")
                            # sendqqemail.sendqqEmail(subject, content)

                        # else:
                        #     # print(".", end='',flush=True)
                        #     print("."),
            elif(r==False):
                print("F"),
            else:
                print ("请求过期或失败")
        except Exception as e:
            # print("F", end='',flush=True)
            print("F"),
        time.sleep(0.5)

if __name__ == "__main__":
    url="http://api.oneniceapp.com/Sneakerpurchase/priceInfos?a_x=0.006302&a_y=-0.013901&a_z=-0.997620&abroad=no&amap_latitude=31.251428&amap_longitude=121.572066&appv=5.4.2.20&ch=AppStore_5.4.2.20&did=be09b9b291195c16a08ba082a1a4f05b&dn=Only%20iPhone&dt=iPhone10%2C1&g_x=-0.013020&g_y=-0.003731&g_z=-0.012055&geoacc=10&im=8B828531-62AC-4D20-A4EC-1B06DC4DCC2B&la=cn&latitude=31.253600&lm=weixin&longitude=121.567834&lp=-1.000000&n_bssid=&n_dns=fe80%3A%3A1&n_ssid=&net=0-0-wifi&osn=iOS&osv=12.3.1&seid=62ba1882f758643933503deb6c1dd9a4&sh=667.000000&sm_dt=201902272149077fd4d01a151205278bbc038390e761e30157f0351da1aed5&sw=375.000000&token=wn-GBGsbbwxESEJvSly_PX-Mmk89ZYUn&ts=1562487032370"

    hurenData='''nice-sign-v1://ea648ff955ec53cc392f82545c06ab7c:1c51069a471c573a/{"id":"132141","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    daogouData='''nice-sign-v1://a615a7d33093849f10969da5c4a2a39f:736d5a5e08525ee6/{"id":"87669","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    heiyeezyData='''nice-sign-v1://c439269145ac860474a0949378d88fa1:b235d822d39e03ed/{"id":"169989","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    heimanData='''nice-sign-v1://941344ef228e71833ad1d559c3c5e21e:538673613e0ccdb2/{"id":"190758","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    offwhitebeika='''nice-sign-v1://0017f0b2b5ef3ac81b45a1f45a5cc16b:a412bcea39391041/{"id":"33400","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    baiyeezy='''nice-sign-v1://143437650dddbf82e02c31421c8a7a06:295eb0e4161c9013/{"id":"1183","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    laomei='''nice-sign-v1://067fabde15d257e5e94a55a0eabd8223:58637c96cc13ac3a/{"id":"99674","token":"wn-GBGsbbwxESEJvSly_PX-Mmk89ZYUn"}'''
    fentianshi='''nice-sign-v1://9cb41e1db0e266d28cc6f4e8e32826c1:8dd9ef08d30b7e9d/{"id":"199600","token":"wn-GBGsbbwxESEJvSly_PX-Mmk89ZYUn"}'''
    fentianshifullname='''YEEZY BOOST 350 V2 2019年版 "SYNTH" 粉天使 亚洲限定'''
    heiyeezyfullname='''YEEZY BOOST 350 V2 2019年版 "BLACK" 黑天使 黑魂'''
    laomeifullname='''YEEZY BOOST 350 V2 2019年版 "CLAY" 粘土 美洲限定'''
    heimanfullname='''YEEZY BOOST 350 V2 2019年版 "BLACK REFLECTIVE" 黑满天星'''
    offwhitebeikafullname='''OFF-WHITE x AIR JORDAN 1 联名 2018年版 "北卡蓝"'''
    baiyeezyfullname='''YEEZY BOOST 350 V2 "CREAM WHITE" 白冰淇淋'''
    hurenfullname='''NIKE SB x AIR JORDAN 1 联名 2019年版 "LA TO CHICAGO" 湖人 芝加哥 刮刮乐'''
    daogoufullname='''TRAVIS SCOTT x AIR JORDAN 1 联名 "CACTUS JACK" 2018年版 反钩 倒钩'''
    n=nice()
    chajia=250
    # r=n.getPrice(url, laomei)
    # n.calculatePrice(r,"美限",laomeifullname,chajia)
    while(True):
        r=n.getPrice(url,heimanData)
        n.calculatePrice(r,"黑满天星",heimanfullname,1000)

        r=n.getPrice(url,daogouData)
        n.calculatePrice(r,"倒钩",daogoufullname,1500)

        r=n.getPrice(url,offwhitebeika)
        n.calculatePrice(r,"offwhite北卡蓝",offwhitebeikafullname,1000)

        r=n.getPrice(url,hurenData)
        n.calculatePrice(r,"湖人AJ",hurenfullname,300)

        r=n.getPrice(url,heiyeezyData)
        n.calculatePrice(r,"黑天使",heiyeezyfullname,chajia)

        r=n.getPrice(url, fentianshi)
        n.calculatePrice(r,"粉天使",fentianshifullname,chajia)

        r=n.getPrice(url,baiyeezy)
        n.calculatePrice(r,"白椰子",baiyeezyfullname,chajia)

        r=n.getPrice(url, laomei)
        n.calculatePrice(r,"美限",laomeifullname,chajia)

        time.sleep(2)
