# -*- coding: utf_8 -*-

from locust import HttpLocust, TaskSet, task
import subprocess
import json


# 性能测试任务类 TaskSet.
class UserBehavior(TaskSet):
    # 开始
    def on_start(self):
        pass

    # 任务
    @task(1)
    def getTagVals(self):
        u"""
        request_url：请求路径
        request_params：请求头参数
        request_json：请求json参数
        """
        # 待测试的路径
        request_url = "http://10.244.1.3:80/b?exid=1&pdt=1&d=ifc_pre&c=1"
        # request_params = {
        #     "nonce": "abcdefg",
        #     "_type": None,
        #     "target": "CLNJ01",
        #     "timestamp": 1507860000,
        #     "apiId": "EC",
        #     "apiSign": "D41D8CD98F00B204E9800998ECF8427E"
        # }
        request_json = {"id":"eeoB9zxresjtYsNeomucXJchccFCE","tmax":100,"site":{"id":"101","domain":"ifeng.com","sectioncat":["A04B00C00v2012.1"],"page":"http://finance.ifeng.com/a/20130701/10037909_0.shtml","ref":"http://www.baidu.com/link?url=123&wd=ifeng","publisher":{"id":"101"},"allyessitetype":"M01N01v2012.1","allyespageform":"R10201v2012.1"},"device":{"dnt":0,"ua":"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0","ip":"10.200.39.15","language":"zh","js":0,"devicetype":1,"make":"","model":"","os":"ios","osv":"7.0.1","connectiontype":"2","geo":{"lat":"116.406568","lon":"39.928879"},"carrier":"46000","ifa":"ifa","didsha1":"didsha1","macsha1":"macsha1","hwv":"5S","h":400,"w":300,"pxratio":1},"user":{"id":"LzkfX7QWJeGnExgED7Ee0b0O","cver":2},"imp":[{"id":"1","https_flag":0,"video":{"mimes":["video/x-flv"],"linearity":1,"protocol":3,"w":400,"h":300,"maxduration":15,"minduration":15,"allyesadformat":[2,6],"allyesadform":"400"},"tagid":"279-100","bidfloor":0.28,"bidfloorcur":"CNY"}],"cur":["CNY"],"bcat":[],"badv":[]}
        response = self.client.post(
            url=request_url,
            json=request_json
        )
        if response.status_code != 200:
            print u"返回异常"
            print u"请求返回状态码:", response.status_code
        elif response.status_code == 200:
            print u"返回正常"

        # 这里可以编写自己需要校验的返回内容
        # content = json.loads(response.content)["content"]
        # if content["tagKey"] == 25:
        #     print u"校验成功"
        #     print json.dumps(content, encoding="UTF-8", ensure_ascii=False)


# 性能测试配置
class MobileUserLocust(HttpLocust):
    u"""
    min_wait ：用户执行任务之间等待时间的下界，单位：毫秒。
    max_wait ：用户执行任务之间等待时间的上界，单位：毫秒。
    """
    # weight = 3
    task_set = UserBehavior
    host = "http://10.244.1.3:80/b?exid=1&pdt=1&d=ifc_pre&c=1"
    min_wait = 3000
    max_wait = 6000


if __name__ == "__main__":
    subprocess.Popen("locust -f LocustTest.py", shell=True)