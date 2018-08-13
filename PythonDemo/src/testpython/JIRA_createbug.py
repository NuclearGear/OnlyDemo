# coding:utf-8
from selenium import webdriver
import ddt
import exceltest
import os
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.common.exceptions import *
import sys,unittest,time
reload(sys)
sys.setdefaultencoding('utf-8')

readexcel=exceltest.ExcelUtil("C:\\Users\\only\\JIRA.xlsx")
d = readexcel.dict_data()
nowTime = time.strftime("%Y%m%d_%H_%M_%S")
path="..\\screenshot\\"

@ddt.ddt
class JIRA_CreateBug(unittest.TestCase):
    # def __init__(self,username,pwd):
    #     self.username=username
    #     self.pwd=pwd
    def Login_JIRA(self,username,pwd):
        # path = r'C:\Users\only\AppData\Roaming\Mozilla\Firefox\Profiles\yn80ouvt.default'
        # proffile = webdriver.FirefoxProfile(path)
        # self.driver = webdriver.Firefox(proffile)
        '''------------------selenium2不支持火狐47以上版本------------------------'''
        executable_path ="C:\driver\chromedriver.exe"
        self.driver= webdriver.Chrome(executable_path)
        self.driver.maximize_window()
        self.driver.implicitly_wait(30)
        self.driver.get("http://newjira.cnsuning.com/secure/Dashboard.jspa")
        self.send_keys(("id","username"),username)
        self.send_keys(("id","password"),pwd)
        self.driver.find_element_by_xpath("//input[@type='submit']").click()
        denglu=self.driver.find_element_by_xpath("//*[@id='logo']/a/span").text
        self.assertEqual(denglu,"苏宁开发管理平台")
        print u"登录成功!"
        return True
    def click(self, locator):
        '''
        点击操作
        Usage:
        locator = ("id","xxx")
        driver.click(locator)
        '''
        element = self.find_element(locator)
        element.click()
    def send_keys(self, locator, text):
        '''
        发送文本，清空后输入
        Usage:
        locator = ("id","xxx")
        driver.send_keys(locator, text)
        '''
        element = self.find_element(locator)
        element.clear()
        element.send_keys(text)
    def find_element(self, locator, timeout=30):
        '''等待定位元素，参数locator是元祖类型'''
        try:
            element = WebDriverWait(self.driver, timeout, 1).until(lambda x: x.find_element(*locator))
        except TimeoutException:
            print u"未定位到元素"
        else:
            return element
    def js_focus_element(self, locator):
        '''聚焦元素'''
        target = self.find_element(locator)
        self.driver.execute_script("arguments[0].scrollIntoView();", target)

    def create_bug(self,summarytext,youxianji,quexianlevel,miaoshu,shuoming,mokuai,yingxiangbanben,jingbanren,chaosongrenlist,upload_name):
        self.driver.find_element_by_id("create_link").click()
        # self.driver.switch_to_frame("")
        WebDriverWait(self.driver,10, 0.5).\
                until_not(lambda x: x.find_element_by_id("create-issue-dialog").is_displayed())#20秒每一秒查找元素，找到元素返回True，忽略超时报错
        time.sleep(5)
        chuangjianwenti=("xpath","//*[@id='create-issue-dialog']/div[1]/h2")
        # self.send_keys(("id","issuetype-field"),"缺陷")
        #输入主题
        self.driver.find_element_by_id("summary").send_keys(summarytext)
        #选择发现阶段
        self.driver.find_element_by_id("customfield_10234").click()
        self.driver.find_element_by_xpath("//*[@id='customfield_10234']//option[4]").click()
        #选择优先级
        self.send_keys(("id","priority-field"),youxianji)
        #选择缺陷等级
        self.click(chuangjianwenti)
        quexiandengji=("id","customfield_10015")
        self.click(quexiandengji)
        self.click(("xpath",".//*[@id='customfield_10015']//option[contains(text(),'"+quexianlevel+"')]"))
        #选择缺陷来源
        self.click(chuangjianwenti)
        self.click(("id","customfield_10235"))
        self.click(("xpath","//*[@id='customfield_10235']/option[2]"))
        #输入描述
        self.send_keys(("id","description"),miaoshu)
        #输入详细说明
        self.send_keys(("id","customfield_10273"),shuoming)
        #输入模块
        self.send_keys(("id","components-textarea"),mokuai)
        #输入影响版本
        self.click(("id","versions-textarea"))
        self.send_keys(("id","versions-textarea"),yingxiangbanben)
        #输入解决版本
        self.click(chuangjianwenti)
        self.click(("id","fixVersions"))
        self.click(("xpath","//*[@id='fixVersions']/optgroup/option[contains(text(),'"+yingxiangbanben+"')]"))
        #经办人
        self.driver.find_element_by_id("assignee-field").send_keys(jingbanren)
        #self.send_keys(("id","assignee-field"),jingbanren)
        time.sleep(1)
        self.send_keys(("id","assignee-field"),Keys.ENTER)
        #抄送人
        chaosongren=chaosongrenlist.split('，')
        self.js_focus_element(("id","customfield_10100"))
        for i in range(len(chaosongren)):
            self.driver.find_element_by_id("customfield_10100").send_keys(chaosongren[i])
            time.sleep(1)
            self.driver.find_element_by_id("customfield_10100").send_keys(Keys.ENTER)
        #上传图片
        if upload_name!="":
            self.click(("css selector",".issue-drop-zone__button"))
            lujing=u"C:\\Users\\only\\Desktop\\JIRA_upload\\"+str(upload_name)
            os.system("C:\\Users\\only\\Desktop\\JIRA_upload\\upload.exe %s"%lujing)
            #os.system("C:\\Users\\only\\Desktop\\JIRA_upload\\JIRA_sendfujian.exe")
        #创建缺陷
        time.sleep(2)
        self.click(("id","create-issue-submit"))

        WebDriverWait(self.driver,10, 0.5).\
                until(lambda x: x.find_element_by_css_selector(".issue-created-key.issue-link").is_displayed())
        result=self.find_element(("css selector",".issue-created-key.issue-link")).text
        self.click(("css selector",".issue-created-key.issue-link"))
        return result




    def setUp(self):
        print("start")
    def tearDown(self):
        self.driver.quit()
        print("end")
    @ddt.data(*d)
    def test_01(self,data):
        u'''登录JIRA'''
        self.Login_JIRA(data["username"], data["pwd"])
        createid=self.create_bug(data[u"主题"], data[u"优先级"],data[u"缺陷等级"], data[u"描述"],data[u"详细说明"], data[u"模块"],data[u"影响版本/解决版本"], data[u"经办人"], data[u"抄送人"], data[u"附件"])
        print createid+u"创建成功"
        self.driver.get_screenshot_as_file(path+u"JIRA"+nowTime+'.jpg')

if __name__ == "__main__":
    unittest.main()
