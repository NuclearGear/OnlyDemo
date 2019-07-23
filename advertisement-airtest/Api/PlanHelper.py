# coding:utf-8

import requests,datetime,time,threading,collections,re
import unittest


class PlanHelper():
    cookie='''_did=-1552029286118624; _webDtk="5ROhhgnNrqQGLjC59uECkoFZQJWtSsvecQT%2BUt04ABY%3D"; _dtk4399com=5ROhhgnNrqQGLjC59uECkoFZQJWtSsvecQT%2BUt04ABY%3D; _webdtk_01=%7B%22step%22%3A2%7D; _tk=HcmDL0KSANFrwZnP6K0QPVmrZH1aJN0TTgU0xJuFratL9ixdV5QpsDCyaRF8qKsn6CKAM1NaG9LENSHxYf58l1rK4AboiuhW%2FQC5mJkoF3A%3D; _ut=0; _wtk=be9d9dde36d0ad52d3870c283a9601ed; adbackend_rt=2; beacon_visit_count=19; beacon_msg_sequence=41'''

    def changePlanStatus(self,planids,status):
        '''
        *通过restful接口改变投放状态(及时生效)
        *暂停："pause"
        *继续："restore"
        *终止："terminate"
        '''
        url = "http://srv.test.pajkdc.com/adbackend/api/plan/" + status

        h = {
            "User-Agent": "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36",
            "Accept": "*/*",
            "Accept-Language": "zh-CN,zh;q=0.9",
            "Accept-Encoding": "gzip, deflate",
            "Referer": "http://www.test.pajkdc.com/adfrontend/",
            "Connection": "keep-alive",
            "Content-Type": "application/x-www-form-urlencoded",
            "Cookie":""
        }
        h["Cookie"]=self.cookie

        for i in planids:
            params = {"planIds": i}
            r = requests.get(url,params=params,headers=h)
            r.encoding="uft-8"
            print (r.url)
            print (r.status_code)
            print (r.text)
            result = r.json()
            #print (result)
            assert result['success'] == True

    def addlivespuid(self,channel,spuids,userid):
        '''
        *通过restful接口增加品牌推广
        *channel 0-平安好医生 1-寿险',
        '''
        h = {
            "User-Agent": "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36",
            "Accept": "*/*",
            "Accept-Language": "zh-CN,zh;q=0.9",
            "Accept-Encoding": "gzip, deflate",
            "Referer": "http://www.test.pajkdc.com/adfrontend/",
            "Connection": "keep-alive",
            "Content-Type": "application/x-www-form-urlencoded",
            "Cookie":""
        }
        h["Cookie"]=self.cookie

        for i in spuids:
            url = "http://srv.test.pajkdc.com/adaisle/anchor/brandPromotion/add?channel="+ channel +"&spuid=" + i + "&userId=" + userid
            r = requests.get(url,headers=h)
            r.encoding="uft-8"
            print (r.url)
            print (r.status_code)
            print (r.text)
            result = r.json()
            #print (result)
            assert result['success'] == True


if __name__ == "__main__":
        ph=PlanHelper()
        # planids=["34530"]
        # status="pause"
        # ph.changePlanStatus(planids,status)
        spuids=["30495654"]
        ph.addlivespuid("1",spuids,"20089690202")
