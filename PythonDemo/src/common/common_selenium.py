# -*- coding: utf-8 -*
from selenium import webdriver
from selenium.common.exceptions import *   # 导入异常类
from selenium.webdriver.support.ui import Select
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.common.exceptions import NoAlertPresentException
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import selenium.webdriver.support.ui as ui
import sys,time,unittest,os,xlrd
import sys
reload(sys)
sys.setdefaultencoding('utf-8')
now = time.strftime("%Y-%m-%d_%H_%M_%S", time.localtime(time.time())) #当前时间

def browser(browser="ff"):
    try:
        if browser == "firefox" or browser == "ff":
            profile = webdriver.FirefoxProfile()
            profile.set_preference("browser.download.folderList", 2)
            profile.set_preference("browser.download.manager.showWhenStarting", False)
            profile.set_preference("browser.download.dir", "C:\\Users\\Administrator\\PycharmProjects\\pensee\\Download")
            profile.set_preference("browser.helperApps.neverAsk.saveToDisk", "application/ms-excel")
            profileDir = r"C:\Users\Administrator\AppData\Roaming\Mozilla\Firefox\Profiles\8zi3ywkk.default"
            profile = webdriver.FirefoxProfile(profileDir)
            driver = webdriver.Firefox(firefox_profile=profile)
            driver.implicitly_wait(10)
            return driver
        elif browser == "chrome" or browser == "gc":
            driver = webdriver.Chrome()
            return driver
        elif browser == "ie":
            driver = webdriver.Ie()
            return driver
        elif browser == "phantomjs" or browser == "pj":
            driver = webdriver.PhantomJS()
            return driver
        else:
            print("Not found this browser,You can enter 'firefox', 'chrome', 'ie' or 'phantomjs'")
    except Exception as msg:
        print "%s" % msg


class Common(object):

    def __init__(self,browser=browser()):
        """
        启动浏览器参数化，默认启动firefox.
        """
        self.driver=browser

    def open(self, url, t='', timeout=30):
        '''
        使用get打开url后，最大化窗口，判断title符合预期
        '''

        self.driver.get(url)
        self.driver.maximize_window()
        try:
            WebDriverWait(self.driver, timeout, 5).until(EC.title_contains(t))
        except TimeoutException:
            print("open %s title error" % url)
        except Exception as msg:
            print("Error:%s" % msg)

    def text_in_element(self, locator, text, timeout=10):
        '''文本是否在元素'''
        try:
            result = WebDriverWait(self.driver, timeout, 1).until(EC.text_to_be_present_in_element(locator, text))
        except TimeoutException:
            print u"元素没定位到："+str(locator)
            return False
        else:
            return result

    def is_text_in_value(self, locator, value, timeout=10):
        '''
        判断元素的value值，没定位到元素返回false,定位到返回判断结果布尔值
        result = driver.text_in_element(locator, text)
        '''
        try:
            result = WebDriverWait(self.driver, timeout, 1).until(EC.text_to_be_present_in_element_value(locator, value))
        except TimeoutException:
            print u"元素没定位到："+str(locator)
            return False
        else:
            return result

    def is_alert_present(self, timeout=10):
        '''判断页面是否有alert，
        有返回alert(注意这里是返回alert,不是True)
        没有返回False'''
        result = WebDriverWait(self.driver, timeout, 1).until(EC.alert_is_present())
        return result

    def is_visibility(self, locator, timeout=10):
        '''元素可见返回本身，不可见返回Fasle'''
        result = WebDriverWait(self.driver, timeout, 1).until(EC.visibility_of_element_located(locator))
        return result

    def is_invisibility(self, locator, timeout=10):
        '''元素不可见返回本身，不可见返回True'''
        result = WebDriverWait(self.driver, timeout, 1).until(EC.invisibility_of_element_located(locator))
        return result

    def is_clickabke(self, locator, timeout=10):
        '''元素可以点击is_enabled返回本身，不可点击返回Fasle'''
        result = WebDriverWait(self.driver, timeout, 1).until(EC.element_to_be_clickable(locator))
        return result

    def isdisappeared(self,locator,timeout=10):
        '''元素id是否可见'''
        try:
            result = WebDriverWait(self.driver,timeout,1).until(lambda x: x.find_element(*locator).is_displayed())
        except TimeoutException:
            return False
        else:
            return result

    def isnotdisappeared(self,locator,timeout=10):
        '''元素id是否不可见'''
        try:
            result = WebDriverWait(self.driver,timeout, 1). until_not(lambda x: x.find_element(*locator).is_displayed())
        except TimeoutException:
            return False
        else:
            return result

    def is_title(self, title, timeout=10):
        '''判断title完全等于'''
        result = WebDriverWait(self.driver, timeout, 1).until(EC.title_is(title))
        return result

    def is_title_contains(self, title, timeout=10):
        '''判断title包含'''
        result = WebDriverWait(self.driver, timeout, 1).until(EC.title_contains(title))
        return result

    def isselected(self,locator,timeout=10):
        '''检测元素是否被选中'''
        try:
            result = WebDriverWait(self.driver,timeout, 1). until(lambda x: x.find_element(*locator).is_selected())
        except TimeoutException:
            return False
        else:
            return result

    def is_selected_be(self, locator, selected=True, timeout=10):
        '''判断元素的状态，selected是期望的参数true/False
        返回布尔值'''
        result = WebDriverWait(self.driver, timeout, 1).until(EC.element_located_selection_state_to_be(locator, selected))
        return result

    def whichisselected(self,locator):
        '''检测一组元素哪个被选中'''
        try:
            elements=self.find_elements(locator)
        except Exception as msg:
            print u"元素未定位到或没有选中%s"%msg
        else:
            for i in elements:
                if i.is_selected()==True:
                    return i

    def find_element(self, locator, timeout=30):
        '''等待定位元素，参数locator是元祖类型'''
        try:
            element = WebDriverWait(self.driver, timeout, 1).until(lambda x: x.find_element(*locator))
        except TimeoutException:
            print u"未定位到元素"
        else:
            return element

    # def find_element(self, locator, timeout=30):
    #     '''
    #     定位元素，参数locator是元祖类型
    #     Usage:
    #     locator = ("id","xxx")
    #     driver.find_element(*locator)
    #     '''
    #     element = WebDriverWait(self.driver, timeout, 1).until(lambda x: x.find_element(*locator))
    #     return element

    def find_elements(self, locator, timeout=20):
        '''定位一组元素，参数locator是元祖类型'''
        try:
            elements = WebDriverWait(self.driver, timeout, 1).until(lambda x: x.find_elements(*locator))
        except TimeoutException:
            print u"未定位到元素"
        else:
            return elements

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

    def send_replace(self,locator,text):
        element = self.find_element(locator)
        element.click()
        element.send_keys(Keys.CONTROL,'a')
        element.send_keys(text)

    def usecontains(self,text,value='',operate="click"):
        '''
        xpath模糊匹配
        operate=click点击
        operate=sendkeys输入
        '''
        element = self.find_element(("xpath","//*[contains(text(),'"+text+"')]"))
        if operate=="sendkeys":
            element.clear()
            element.send_keys(value)
        else:
            element.click()

    def back(self):
        """
        Back to old window.

        Usage:
        driver.back()
        """
        self.driver.back()

    def forward(self):
        """
        Forward to old window.

        Usage:
        driver.forward()
        """
        self.driver.forward()

    def close(self):
        """
        Close the windows.

        Usage:
        driver.close()
        """
        self.driver.close()

    def quit(self):
        """
        Quit the driver and close all the windows.

        Usage:
        driver.quit()
        """
        self.driver.quit()

    def refresh(self):
        self.driver.refresh()

    def move_to_element(self, locator):
        '''
        鼠标悬停操作
        Usage:
        locator = ("id","xxx")
        driver.move_to_element(locator)
        '''
        element = self.find_element(locator)
        ActionChains(self.driver).move_to_element(element).perform()

    def double_click(self, locator):
        '''双击操作'''
        element = self.find_element(locator)
        ActionChains(self.driver).double_click(element).perform()

    def switch_to_lasthandle(self):
        '''获得当前最后的窗口'''
        for handle in self.driver.window_handles:
            self.driver.switch_to_window(handle)

    def get_title(self):
        '''获取title'''
        return self.driver.title

    def get_text(self, locator):
        '''获取文本'''
        element = self.find_element(locator)
        return element.text

    def get_attribute(self, locator, name):
        '''获取属性'''
        element = self.find_element(locator)
        return element.get_attribute(name)

    def js_execute(self, js):
        '''执行js'''
        return self.driver.execute_script(js)

    def js_removereadonly(self,id):
        '''去除只读属性'''
        js = 'document.getElementById(id).removeAttribute("readonly");'
        self.js_execute(js)

    def js_focus_element(self, locator):
        '''聚焦元素'''
        target = self.find_element(locator)
        self.driver.execute_script("arguments[0].scrollIntoView();", target)

    def js_scroll_top(self):
        '''滚动到顶部'''
        js = "window.scrollTo(0,0)"
        self.driver.execute_script(js)

    def js_scroll_end(self):
        '''滚动到底部'''
        js = "window.scrollTo(0,document.body.scrollHeight)"
        self.driver.execute_script(js)

    def select_by_index(self, locator, index):
        '''通过索引,index是索引第几个，从0开始'''
        element = self.find_element(locator)
        Select(element).select_by_index(index)

    def select_by_value(self, locator, value):
        '''通过value属性'''
        element = self.find_element(locator)
        Select(element).select_by_value(value)

    def select_by_text(self, locator, text):
        '''通过文本值定位'''
        element = self.find_element(locator)
        Select(element).select_by_value(text)

    def handle_change(self,handles_len):
        nowhandles=len(self.driver.window_handles)
        if nowhandles==handles_len:
            return False
        elif nowhandles>handles_len:
            return True
        elif nowhandles<handles_len:
            return True

    # def report(self,classname,methodname):
    #     now = time.strftime("%Y-%m-%d %H_%M", time.localtime(time.time()))
    #     testsuite=unittest.TestSuite()
    #     testsuite.addTest(classname(methodname))
    #     fp=open("C:\\Users\\Administrator\\PycharmProjects\\pensee\\Report\\"+now+".html",'wb')
    #     runner=HTMLTestRunner(stream=fp,
    #                       title=u"自动化测试报告",
    #                       description=u"用例测试情况:")
    #     runner.run(testsuite)
    #     fp.close()
'''
ID = "id"   
XPATH = "xpath"   
LINK_TEXT = "link text"   
PARTIAL_LINK_TEXT = "partial link text"   
NAME = "name"   
TAG_NAME = "tag name"   
CLASS_NAME = "class name"   
CSS_SELECTOR = "css selector"
'''
if __name__ == '__main__':
    # driver_n=Common(browser())
    # driver_n.login()
    # driver_n.quit()
    # driver_n.open("http://www.baidu.com")
    # a=driver_n.find_elements(("xpath","html/body/div[3]/div[2]/div[3]/a"))
    # for i in a:
    #     print i.text
    # driver_n.click(("link text",u"新闻"))
    # driver_n.switch_to_lasthandle()
    # all=driver_n.driver.window_handles
    # driver_n.close()
    # time.sleep(2)
    # result=WebDriverWait(driver_n,20, 1).until(lambda x:x.handle_change(len(all))==True)
    # print result
    # driver_n.quit()
    filepath="C:\\Users\\only\\denglutest.xlsx"
    a=ExcelUtil(filepath)
    print a.dict_data()

