# coding:utf-8
import requests

def elme(url):
    header={"User-Agent":"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.884.400 QQBrowser/9.0.2524.400",
            "Cookie": "perf_ssid=r5i6xcdx2wpmaaumy2qu02rqah4waesb_2018-07-24; ubt_ssid=pzkra8xtte4ct0byjxdbqg6w20zmfjq1_2018-07-24; _utrace=d3f82538569df1502b077e9976c013e8_2018-07-24; snsInfo[wx2a416286e96100ed]=%7B%22city%22%3A%22%E6%B5%A6%E4%B8%9C%E6%96%B0%E5%8C%BA%22%2C%22country%22%3A%22%E4%B8%AD%E5%9B%BD%22%2C%22eleme_key%22%3A%22a90928d5bb9f6d35c0785e78311e533c%22%2C%22headimgurl%22%3A%22http%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FQ0j4TwGTfTKNpepsCnC60WmUhVgZrwK3HRrq6x1u4nJVGYY7TO1iaMcSYXNKYic5HYs3uVvCL5icS5E6HKbFl5tWQ%2F132%22%2C%22language%22%3A%22zh_CN%22%2C%22nickname%22%3A%22Only%E4%B8%B6%F0%9F%90%B6%22%2C%22openid%22%3A%22oEGLvjhGwjryJndvv6WmSOIej7g8%22%2C%22privilege%22%3A%5B%5D%2C%22province%22%3A%22%E4%B8%8A%E6%B5%B7%22%2C%22sex%22%3A1%2C%22unionid%22%3A%22o_PVDuNRixw28jtZUK5NaAfLmyxY%22%2C%22name%22%3A%22Only%E4%B8%B6%F0%9F%90%B6%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdwx.qlogo.cn%2Fmmopen%2Fvi_32%2FQ0j4TwGTfTKNpepsCnC60WmUhVgZrwK3HRrq6x1u4nJVGYY7TO1iaMcSYXNKYic5HYs3uVvCL5icS5E6HKbFl5tWQ%2F132%22%7D"

}
    json={
	"method": "phone",
	"group_sn": "2a00c2ddd79ce4f5",
	"sign": "a90928d5bb9f6d35c0785e78311e533c",
	"phone": "",
	"device_id": "",
	"hardware_id": "",
	"platform": 0,
	"track_id": "undefined",
	"weixin_avatar": "",
	"weixin_username": "",
	"unionid": "o_PVDuNRixw28jtZUK5NaAfLmyxY",
	"latitude": -180,
	"longitude": -180
}

    response=requests.post(url,headers=header,json=json, verify=False)

    print response.status_code
    print response.text
    cook= response.cookies
    print cook


testurl="https://h5.ele.me/restapi/marketing/promotion/weixin/oEGLvjhGwjryJndvv6WmSOIej7g8"
elme(testurl)