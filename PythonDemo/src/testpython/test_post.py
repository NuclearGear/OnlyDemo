#coding:utf-8
'''
Created on 2018年2月9日

@author: Administrator
'''

import requests
from test.test_get import test_url
username = '0092'
password = '0000'
test_url = 'http://192.168.1.112:3110/'
datalist = {'username':username,'pwd': password}
head = {"Content-Type": "application/Json"}
response = requests.post(test_url, datas=datalist,headers=head)
result = response.text          #读取请求返回的结果
print(result) 
