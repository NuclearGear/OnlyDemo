# coding:utf-8
import requests
import re
import logging
from bs4 import BeautifulSoup
logging.captureWarnings(True) #设置忽略警告

# s = requests.session()  # 全局的s

def get_token(s):
    '''
    fuction: 获取token
    args: s 参数 -》s = requests.session()
    :return  anti_token  ->{'X-Anit-Forge-Token': 'xx', 'X-Anit-Forge-Code': '38515842'}
   '''
    # 局部的s没定义，从外部传入s
    url = 'https://passport.lagou.com/login/login.html'
    h1 = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0"
        }
    r1 = s.get(url, headers=h1, verify=False)
    # print(r1.text)

    soup = BeautifulSoup(r1.content, "html.parser", from_encoding='utf-8')
    #print soup
    tokenCode = {}
    try:
        t = soup.find_all('script')[1].get_text()
        print(t)
        tokenCode['X_Anti_Forge_Token'] = re.findall(r"Token = '(.+?)'", t)
        tokenCode['X_Anti_Forge_Code'] = re.findall(r"Code = '(.+?)'", t)
        return tokenCode
    except:
        print("获取token和code失败")
        tokenCode['X_Anti_Forge_Token'] = ""
        tokenCode['X_Anti_Forge_Code'] = ""
        return tokenCode


def login_lgw(s, anti_token, user, psw):

    url2 = 'https://passport.lagou.com/login/login.json'
    h2 = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0",
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        "X-Requested-With": "XMLHttpRequest",
        "X-Anit-Forge-Token": anti_token['X_Anti_Forge_Token'] ,
        "X-Anit-Forge-Code": anti_token['X_Anti_Forge_Code'],
        "Referer": "https://passport.lagou.com/login/login.html"
        }
    body ={
            "isValidate": "true",
            "username": user,
            "password": psw,
            "request_form_verifyCode": "",
            "submit": ""
        }
    print(s.headers)  # s的头部

    # 更新s的头部
    s.headers.update(h2)
    print(s.headers)
    r2 = s.post(url2, data=body, verify=False)
    print(r2.text)
    return r2.json()

if __name__ == "__main__":
    # 自测的内容
    s = requests.session()
    token = get_token(s)
    print(token)