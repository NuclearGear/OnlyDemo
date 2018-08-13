#coding:utf-8
'''
Created on 2018年2月9日

@author: Administrator  Get请求方式模拟
'''
import requests
test_url = "http://www.baidu.com"
response = requests.get(test_url)
result = response.text
print(result)