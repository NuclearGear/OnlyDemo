#coding:utf-8
'''
Created on 

@author: Administrator
'''
import requests

#拼接url
host = "http://192.168.1.112:3100/"
#初始化url请求对象
r = requests.get(host)
#获取url请求对象中的有用信息，如token、cookies
token = r.cookies.items()[0][1]
cookies = r.cookies
#以下为测试，所获取的token及cookie的格式
print(type(token))   #获取token的类型
print(token)         #打印token
print(cookies)       #打印cookies
print(r.headers)     #打印头文件
print(r.url)         #打印URL