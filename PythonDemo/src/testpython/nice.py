# coding:utf-8
import json

import requests,datetime,time,threading,collections,re
import smtplib
from email.mime.text import MIMEText
import datetime
from nice_airtest import nice_airtest
import urllib3
urllib3.disable_warnings()

def sendqqEmail(subject,content,msg_to='315118616@qq.com'):    #收件人邮箱
    msg_from='1056396153@qq.com'                               #发送方邮箱
    passwd='ipegauclyoczbdde'                                  #填入发送方邮箱的授权码

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

def judgeTime(startTime,endTime):
    # 范围时间
    d_time = datetime.datetime.strptime(str(datetime.datetime.now().date()) + startTime, '%Y-%m-%d%H:%M')
    d_time1 = datetime.datetime.strptime(str(datetime.datetime.now().date()) + endTime, '%Y-%m-%d%H:%M')
    # 当前时间
    n_time = datetime.datetime.now()
    # 判断当前时间是否在范围时间内
    if n_time > d_time and n_time < d_time1:
        return True
    else:
        return False

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

def getPrice(url,data):
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
    try:
        r = requests.post(url,headers=h,data=data,verify=False,timeout=3)
        # response = r.text
        # print (response)
        return r
    except:
        return False

def calculatePrice(r,name,chajia):
        try:
            if(r.status_code==200):
                response=r.text
                # print (response)
                # print (type(response))
                d = json.loads(response,encoding='utf-8')
                # print (d)
                xianhuo={}
                shangou={}
                try:
                    for i in d["data"]["list"]:
                        if(i["stock_id"]=="1"):
                            size=str(i["size"])
                            price=str(i["price"])
                            xianhuo[size]=price
                        elif(i["stock_id"]=="128"):
                            size=str(i["size"])
                            price=str(i["price"])
                            shangou[size]=price
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
                                shangou[size] = price
                # print ("现货"+str(xianhuo))
                # print ("闪购"+str(shangou))
                print(".", end='',flush=True)
                for i in shangou:
                    # print i
                    if i in xianhuo:
                        price1=int(xianhuo.get(i))
                        price2=int(shangou.get(i))
                        price3=price1-price2
                        if(chajia>0 and price3>chajia):
                            subject=name+i+"码闪购价格低于现货价格"+str(price3)+"元,现货价格:"+str(price1)+"元,闪购价格:"+str(price2)
                            content="现货价格:"+str(price1)+"元,闪购价格:"+str(price2)+"\n低"+str(price3)+"元，请速查看app"
                            sendqqEmail(subject, content)
                            print ("\n-----------------------"+name+i+"码闪购价格低于现货价格"+str(price3)+"元,现货价格:"+str(price1)+"元,闪购价格:"+str(price2)+"-----------------------\n")
                            #启动airtest
                            sendqqEmail(subject, content, "18964698690@163.com")
                            at = nice_airtest()
                            at.buy(name,i,price2)
                        elif(chajia==0 and price1>price2):
                            subject=name+i+"码闪购价格低于现货价格"
                            content="现货价格:"+str(price1)+"元,闪购价格:"+str(price2)
                            print ("\n"+name+i+"码闪购价格低于现货价格")
                            # sendqqemail.sendqqEmail(subject, content)
                        # else:
                        #     subject=name+i+"码闪购价格高于现货价格"
                        #     content="现货价格:"+str(price1)+"元,闪购价格:"+str(price2)
                        #     sendqqEmail(subject, content, "18964698690@163.com")
                            # print(".", end='',flush=True)
                            # print("."),
            elif(r==False):
                print("F"),
            else:
                print ("请求过期或失败")
        except Exception as e:
            print("F", end='',flush=True)
            # print("F"),

def bot(url,data,name,chajia,sleep=5):
    while (True):
        X=judgeTime("8:00", "23:59")
        if(X):
            while(judgeTime("8:00", "23:59")):
                r=getPrice(url,data)
                calculatePrice(r,name,chajia)
                time.sleep(sleep)

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

    url2='''https://115.182.19.49/Sneakerpurchase/priceInfosV1?sign=b579538aeb155166cb6d4e4ded8af04e&token=ujt9rjESVVTNgieTokeuP6We0yEJmsYA&did=77e71f31fc1dec1e7eaebf9f6f822aa3&osn=android&osv=6.0&appv=5.4.3&net=0-0-wifi&longitude=121.45194050763632&latitude=31.18470913241727&ch=main&lp=50.0&geoacc=50.0&geotype=wifi&ver=1&ts=1563765346773&sw=1080&sh=1920&la=zh-CN&seid=022d00753306894490c6b1b9716e20fe&src=&tpid=&pre_module_id=&module_id=&lm=mobile&abroad=no&country=&sm_dt=201907051933014593e25f7ddd86177727b95cc95e1c140171e2bb8719a5d7&n_ssid=%22PingAnNet_BX%22&n_bssid=00:fe:c8:e5:a1:3a&n_dns=43.254.104.76&n_lac=0&n_tac=&n_cid=0&n_mcc=0&n_mnc=0&n_bsss=-1&n_misi=unknown&n_ssn&n_sci=&n_son=&g_x=0.0&g_y=0.0&g_z=0.0&a_x=1.593&a_y=7.853&a_z=4.962&dt=ZTE%20BV0701&dn=ZTE%20BV0701_8507&im=A00000568D4FA1&dvi=753f3f2a559523f9ea6dba752c8c7e0c0335851568e0abb4bbf3ad0d020c0d7c'''
    sacai='''nice-sign-v1://ecca8b4e94313b201a0aac0ceba0eea9:yyqnxpkswxa1vlad/{"id":"95618"}'''
    heifen='''nice-sign-v1://0b9ed6a0a7222d54decfe2f01510a03c:gq5z0y9pan7qviel/{"id":"154035"}'''
    hongou='''nice-sign-v1://510cce818f9d5cd1168628eff79a3d78:v7tdjkmkvlj56os9/{"id":"103024"}'''
    daogouLOW='''nice-sign-v1://cf274642db4e8358df7eb689084939aa:bocyyj5nsepplna8/{"id":"195274"}'''

    chajia=200
    threads = []
    t1 = threading.Thread(target=bot,args=(url,heimanData,"黑满天星",8000,4))
    threads.append(t1)
    t2 = threading.Thread(target=bot,args=(url,daogouData,"反钩",7000,4))
    threads.append(t2)
    t3 = threading.Thread(target=bot,args=(url,offwhitebeika,"北卡蓝",6000,3))
    threads.append(t3)
    t4 = threading.Thread(target=bot,args=(url,hurenData,"湖人",500,1))
    threads.append(t4)
    t5 = threading.Thread(target=bot,args=(url,heiyeezyData,"黑天使",150,0.5))
    threads.append(t5)
    t6 = threading.Thread(target=bot,args=(url,fentianshi,"粉天使",120,0.5))
    threads.append(t6)
    t7 = threading.Thread(target=bot,args=(url,baiyeezy,"白冰淇淋",150,2))
    threads.append(t7)
    t8 = threading.Thread(target=bot,args=(url,laomei,"美洲限定",150,2))
    threads.append(t8)
    t9 = threading.Thread(target=bot,args=(url2,sacai,"SACAI",1300,2))
    threads.append(t9)
    t10 = threading.Thread(target=bot,args=(url2,heifen,"黑粉",100,2))
    threads.append(t10)
    t11 = threading.Thread(target=bot,args=(url2,hongou,"红钩",100,1))
    threads.append(t11)
    t12 = threading.Thread(target=bot,args=(url2,daogouLOW,"LOW",500,3))
    threads.append(t12)
    for t in threads:
         t.setDaemon(True)
         t.start()
    t.join()

    # r=getPrice(url2, daogouLOW)
    # calculatePrice(r,"LOW",chajia)
    # bot(url2,daogouLOW,"LOW",2000)

    # while (True):
    #     X=judgeTime("07:20", "23:59")
    #     if(X):
    #         while(judgeTime("07:20", "23:59")):
    #             r=getPrice(url,heimanData)
    #             calculatePrice(r,"黑满天星",3000)
    #
    #             r=getPrice(url,daogouData)
    #             calculatePrice(r,"倒钩",3000)
    #
    #             r=getPrice(url,offwhitebeika)
    #             calculatePrice(r,"北卡蓝",3000)
    #
    #             r=getPrice(url,hurenData)
    #             calculatePrice(r,"湖人",650)
    #
    #             r=getPrice(url,heiyeezyData)
    #             calculatePrice(r,"黑天使",450)
    #
    #             r=getPrice(url, fentianshi)
    #             calculatePrice(r,"粉天使",chajia)
    #
    #             r=getPrice(url,baiyeezy)
    #             calculatePrice(r,"白冰激凌",chajia)
    #
    #             r=getPrice(url, laomei)
    #             calculatePrice(r,"美洲限定",chajia)
    #
    #
    #             r=getPrice(url2, sacai)
    #             calculatePrice(r,"SACAI",chajia)
    #
    #             r=getPrice(url2, heifen)
    #             calculatePrice(r,"黑粉",chajia)
    #
    #             r = getPrice(url2, hongou)
    #             calculatePrice(r, "红勾", chajia)
    #
    #             time.sleep(2)
