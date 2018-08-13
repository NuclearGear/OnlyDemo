#coding:Utf-8
'''
Created on 2018年2月9日

@author: Administrator
'''
import requests

url = 'http://127.0.0.1/wordpress/wp-login.php'

cookie = {'.ASPXANONYMOUS=o9wAlTvX0wEkAAAAZWY1ZGU4Y2YtMGZmYy00MDNlLTk5NmUtMmNiMDVlNGExNGM2dt6oi3nEn2cUMKYxoM1aecV7r4CbuGhq-q460sIJ1581':
        '.ASPXANONYMOUS=o9wAlTvX0wEkAAAAZWY1ZGU4Y2YtMGZmYy00MDNlLTk5NmUtMmNiMDVlNGExNGM2dt6oi3nEn2cUMKYxoM1aecV7r4CbuGhq-q460sIJ1581'
        ,'path':'/;HttpOnly'}
result = requests.get(url,cookies=cookie)
print(result.text)