# coding:utf-8
import json

import requests,datetime,time,threading,collections,re
import smtplib
from email.mime.text import MIMEText

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
    def getPrice(self,url,data,name,chajia=0):
        global x
        x = True
        h = {
            "Host": "api.oneniceapp.com",
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
        r = requests.post(url,headers=h,data=data,verify=False)
        if(r.status_code==200):
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
            for i in shangou:
                # print i
                if i in xianhuo:
                    price1=int(xianhuo.get(i))
                    price2=int(shangou.get(i))
                    if(chajia==0):
                        if(price1>price2):
                            subject=name+i+"码闪购价格低于现货价格"
                            content="现货价格:"+str(price1)+"元,闪购价格:"+str(price2)
                            print (name+i+"码闪购价格低于现货价格")
                            # sendqqemail.sendqqEmail(subject, content)
                            x=False
                    else:
                        if((price1-price2)>chajia):
                            subject=name+i+"码闪购价格低于现货价格"+str(price1-price2)+"元,现货价格:"+str(price1)+"元,闪购价格:"+str(price2)
                            content="现货价格:"+str(price1)+"元,闪购价格:"+str(price2)+"\n低"+str(price1-price2)+"元，请速度查看app"
                            sendqqEmail(subject, content)
                            print ("-----------------------"+name+i+"码闪购价格低于现货价格"+str(price1-price2)+"元,现货价格:"+str(price1)+"元,闪购价格:"+str(price2)+"-----------------------")
                            x=False
        else:
            print ("请求过期或失败")
        time.sleep(2)

if __name__ == "__main__":
    url="http://115.182.19.34/Sneakerpurchase/priceInfos?a_x=0.006302&a_y=-0.013901&a_z=-0.997620&abroad=no&amap_latitude=31.251428&amap_longitude=121.572066&appv=5.4.2.20&ch=AppStore_5.4.2.20&did=be09b9b291195c16a08ba082a1a4f05b&dn=Only%20iPhone&dt=iPhone10%2C1&g_x=-0.013020&g_y=-0.003731&g_z=-0.012055&geoacc=10&im=8B828531-62AC-4D20-A4EC-1B06DC4DCC2B&la=cn&latitude=31.253600&lm=weixin&longitude=121.567834&lp=-1.000000&n_bssid=&n_dns=fe80%3A%3A1&n_ssid=&net=0-0-wifi&osn=iOS&osv=12.3.1&seid=62ba1882f758643933503deb6c1dd9a4&sh=667.000000&sm_dt=201902272149077fd4d01a151205278bbc038390e761e30157f0351da1aed5&sw=375.000000&token=wn-GBGsbbwxESEJvSly_PX-Mmk89ZYUn&ts=1562487032370"
    hurenData='''nice-sign-v1://ea648ff955ec53cc392f82545c06ab7c:1c51069a471c573a/{"id":"132141","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    daogouData='''nice-sign-v1://a615a7d33093849f10969da5c4a2a39f:736d5a5e08525ee6/{"id":"87669","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    heiyeezyData='''nice-sign-v1://c439269145ac860474a0949378d88fa1:b235d822d39e03ed/{"id":"169989","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    heimanData='''nice-sign-v1://941344ef228e71833ad1d559c3c5e21e:538673613e0ccdb2/{"id":"190758","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    offwhitebeika='''nice-sign-v1://0017f0b2b5ef3ac81b45a1f45a5cc16b:a412bcea39391041/{"id":"33400","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    baiyeezy='''nice-sign-v1://143437650dddbf82e02c31421c8a7a06:295eb0e4161c9013/{"id":"1183","token":"G4eWahyWI3XU4JwHgsX5PITkAsHGK5F0"}'''
    laomei='''nice-sign-v1://067fabde15d257e5e94a55a0eabd8223:58637c96cc13ac3a/{"id":"99674","token":"wn-GBGsbbwxESEJvSly_PX-Mmk89ZYUn"}'''
    fentianshi='''nice-sign-v1://9cb41e1db0e266d28cc6f4e8e32826c1:8dd9ef08d30b7e9d/{"id":"199600","token":"wn-GBGsbbwxESEJvSly_PX-Mmk89ZYUn"}'''
    n=nice()
    chajia=300
    # n.getPrice(url, laomei, "老美限", chajia)


    while(True):
        n.getPrice(url,heimanData,"黑满天星",2000)
        n.getPrice(url,hurenData,"湖人AJ",500)
        n.getPrice(url,daogouData,"倒钩",2000)
        n.getPrice(url,heiyeezyData,"黑天使",chajia)
        n.getPrice(url,offwhitebeika,"offwhite北卡蓝",1000)
        n.getPrice(url,baiyeezy,"白椰子",chajia)
        n.getPrice(url, laomei, "老美限", chajia)
        n.getPrice(url, fentianshi, "粉天使", chajia)
        time.sleep(4)