# coding:utf-8

from selenium import webdriver
import unittest
import time
import ddt
import os
import exceltest
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from selenium.common.exceptions import *
from selenium.webdriver.support.wait import WebDriverWait
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

readexcel=exceltest.ExcelUtil("C:\\Users\\only\\denglutest.xlsx")
print readexcel.dict_data()
d = readexcel.dict_data()
nowTime = time.strftime("%Y%m%d_%H_%M_%S")
path="..\\screenshot\\"

def creatsuite():
    testsuite=unittest.TestSuite()
    test_dir = "C:\\Users\\Administrator\\PycharmProjects\\pensee\\TestCase"
    discover = unittest.defaultTestLoader.discover(start_dir=test_dir,pattern="test*.py",top_level_dir="TestCase")
    #discover方法筛选出来的用例，循环添加到测试套件中
    for test_suite in discover:
        for test_case in test_suite:
            testsuite.addTests(test_case)
    return testsuite

@ddt.ddt
class qqemailtest(unittest.TestCase):
    # def __init__(self,username,pwd):
    #     self.username=username
    #     self.pwd=pwd
    def Login(self,username,pwd):
        # path = r'C:\Users\only\AppData\Roaming\Mozilla\Firefox\Profiles\yn80ouvt.default'
        # proffile = webdriver.FirefoxProfile(path)
        # self.driver = webdriver.Firefox(proffile)
        '''------------------selenium2不支持火狐47以上版本------------------------'''
        executable_path ="C:\driver\chromedriver.exe"
        self.driver= webdriver.Chrome(executable_path)
        self.driver.maximize_window()
        self.driver.get("https://mail.qq.com/")
        self.driver.switch_to_frame("login_frame")
        self.driver.find_element_by_id("switcher_plogin").click()
        self.driver.find_element_by_id("u").clear()
        self.driver.find_element_by_id("u").send_keys(username)
        self.driver.find_element_by_id("p").send_keys(pwd)
        self.driver.find_element_by_id("login_button").click()
        # a = WebDriverWait(self.driver, 15).until(lambda x: x.find_element_by_id("err_m")
        # if element ==True:
        #     print self.driver.find_element_by_id("err_m").text
        try:
            WebDriverWait(self.driver,10, 0.5).\
                until(lambda x: x.find_element_by_id("err_m").is_displayed())#20秒每一秒查找元素，找到元素返回True，忽略超时报错
            return self.driver.find_element_by_id("err_m").text
        except TimeoutException:
            dengluemail=self.driver.find_element_by_id("useraddr").text
            self.assertEqual(dengluemail,username)
            print dengluemail+u"登录成功!"
            return True


    def sendqqemail(self,recipient,theme,body):
        xie=WebDriverWait(self.driver, 10).until(lambda x: x.find_element_by_id("composebtn"))
        # 点写信
        xie.click()
        # 切换iframe
        self.driver.switch_to.frame("mainFrame")
        time.sleep(2)
        # 输入收件人
        self.driver.find_element_by_xpath(".//*[@id='toAreaCtrl']/div[2]/input").send_keys(recipient)
        # 输入主题
        time.sleep(2)
        self.driver.find_element_by_id("subject").send_keys(theme+"_"+nowTime)
        time.sleep(2)
        # 输入正文body
        frame = self.driver.find_element_by_xpath("//iframe")
        self.driver.switch_to.frame(frame)
        self.driver.find_element_by_css_selector('[accesskey="q"]').send_keys(body+"_"+nowTime)
        # 切回主页面
        time.sleep(2)
        self.driver.switch_to.default_content()
        # time.sleep(2)
        # # 点传图片按钮，点击事件失效了
        # js = "document.getElementById('mainFrame').contentWindow.document.getElementsByName('UploadFile')[0].click()"
        # self.driver.execute_script(js)

        # 后面的是用autoit执行了
        self.driver.get_screenshot_as_file(path+u"邮件内容"+nowTime+'.jpg')
        #点击发送
        self.driver.switch_to.frame("mainFrame")
        self.driver.find_element_by_xpath(".//*[@id='toolbar'][1]/div/a[1]").click()
        time.sleep(4)
        self.driver.get_screenshot_as_file(path+u"发送结果"+nowTime+'.jpg')
        return self.driver.find_element_by_id("sendinfomsg").text

    def setUp(self):
        print("start")
    def tearDown(self):
        self.driver.quit()
        print("end")
    @ddt.data(*d)
    def test_01(self,data):
        u'''登录qq邮箱发送email数据驱动ddt'''
        dengluresult=self.Login(data["username"], data["pwd"])
        if dengluresult ==True:
            result=self.sendqqemail(data["to"],data["theme"],data["body"])
            self.assertEqual("您的邮件已发送",result)
            print u"发送邮件成功"
        else:
            if dengluresult in ["你还没有输入密码！","你还没有输入帐号！","你输入的帐号或密码不正确，请重新输入。"]:
                print ("校验成功")
            else:
                print dengluresult
                raise Exception("case失败")


if __name__ == "__main__":
    unittest.main()
