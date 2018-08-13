# coding:utf-8
import requests
import time
from datetime import datetime
import os

def get_servertime(host):
    r=requests.get(host)
    date=r.headers["Date"]
    ltime=time.strptime(date,'%d %b %Y %H:%M:%S') #把字符串,转换为为时间格式
    mtime=time.mktime(ltime)+28800 #把时间变更为时间戳，+8*60*60 8个小时为北京时间 ,相加
    ttime=time.localtime(mtime)#把时间戳转换为本地时间格式
    d="%d-%d-%d"%(ttime.tm_year,ttime.tm_mon,ttime.tm_mday)
    t="%d:%d:%d"%(ttime.tm_hour,ttime.tm_min,ttime.tm_sec)
    time_now=datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    print("服务器时间：",d,t)
    print("本机时间：",time_now)
    a=datetime.now().strftime("%H:%M:%S")
    at = datetime.strptime(t,'%H:%M:%S')
    bt = datetime.strptime(a,'%H:%M:%S')
    c=(at - bt).seconds if at>bt else (bt-at).seconds
    print("误差 {}s".format(c))

get_servertime("http://10.200.44.31:9980/dc/cache?db=IFC_develop_2_autobuild")