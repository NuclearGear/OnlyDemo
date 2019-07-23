# -*- encoding=utf8 -*-

from BoothMaterial.BoothMaterial import *
from airtest.core.api import *
from poco.drivers.android.uiautomation import AndroidUiautomationPoco
poco = AndroidUiautomationPoco(use_airtest_input=True, screenshot_each_action=False)
auto_setup(__file__)

class Common():
    
    def logIn(self,phoneNum):
        '''
        登录平安好医生
        phoneNum：登录手机号
        前提：手机已安装app，且已登录过
        '''
        init_device("Android")
        connect_device("Android:///")
        start_app("com.pingan.papd")
        
        try:
            # 当前若已存在手机，则删除
            if poco(name="com.pingan.papd:id/btn_delete_phone_num").exists():
                poco(name="com.pingan.papd:id/btn_delete_phone_num").click()
            poco(text='请输入手机号').click()
            text(phoneNum)
            snapshot(msg="检查用户名是否可以正确输入",filename="inputPhoneNum.jpg")
            
            poco(text="下一步").click()
            sleep(0.5)
            text("666666")
            poco(text="登录").click()
            sleep(4)
            # 关闭弹框，尝试3次
            for i in range(3):
                if poco(name="com.pingan.papd:id/pop_ad_close_half").exists():
                    poco(name="com.pingan.papd:id/pop_ad_close_half").click()
                    sleep(2)
                elif poco(name="com.pingan.papd:id/iv_close").exists():
                    poco(name="com.pingan.papd:id/iv_close").click()
                    sleep(2)
                elif poco(name="com.pingan.papd:id/pop_ad_close_full").exists():
                    poco(name="com.pingan.papd:id/pop_ad_close_full").click()
                    sleep(2)
                #发现新版本，取消
                elif poco(name="com.pingan.papd:id/msg_dialog_btn_cancel").exists():
                    poco(name="com.pingan.papd:id/msg_dialog_btn_cancel").click()
                    sleep(2)
                elif poco(name="com.pingan.papd:id/close_img").exists():
                    poco(name="com.pingan.papd:id/close_img").click()
                else:
                    continue
                    
        except NameError as e:
            print("---->>",e)

    def logOut(self):
        '''
        登出平安好医生app
        1、若在则直接退出
        2、不在则先返回首页
        '''
        while True:
            if poco(name='com.pingan.papd:id/textTab4',text='我的').exists():
                poco(name='com.pingan.papd:id/textTab4',text='我的').click()
                sleep(2)
                if poco(name='com.pingan.papd:id/imageSetting').exists():
                    poco(name='com.pingan.papd:id/imageSetting').click()
                    sleep(2)
                    self.swipeAndTouch(elm=Template(r"tpl1538099999968.png", record_pos=(0.003, 0.832), resolution=(720, 1280)))
                    sleep(1)
                    touch(Template(r"tpl1538100033683.png", record_pos=(0.004, 0.683), resolution=(720, 1280)))
                    break
            else:
                keyevent('BACK')
                # 关闭弹框，尝试3次
                for i in range(3):
                    if poco(name="com.pingan.papd:id/pop_ad_close_half").exists():
                        poco(name="com.pingan.papd:id/pop_ad_close_half").click()
                        sleep(2)
                    elif poco(name="com.pingan.papd:id/iv_close").exists():
                        poco(name="com.pingan.papd:id/iv_close").click()
                        sleep(2)
                    elif exists(Template(r"tpl1538104454078.png", record_pos=(-0.196, 0.135), resolution=(720, 1280))):
                        touch(Template(r"tpl1538104454078.png", record_pos=(-0.196, 0.135), resolution=(720, 1280)))
                        sleep(1)

                    else:continue

    def swipeToTemplate(self,elm):
        '''
        滑动查找传入的图片
        elm：要查找的图片
        '''
        try:
            while True:
                if (exists(elm)):
                    return True
                else:
                    swipe((150,800),(150,50))
                    sleep(0.5)
                    continue
        except NameError as e:
            print('--->>',e)

    @staticmethod
    def swipeAndTouch(elm):
        '''
        滑动查找传入的图片并点击
        elm：要查找的图片
        '''
        try:
            while True:
                if (exists(elm)):
                    touch(elm)
                    break
                else:
                    swipe((150,800),(150,50))
                    continue
        except NameError as e:
            print('--->>',e)

    def swipeToElment(self,element):
        '''
        滑动查找元素
        '''
        try:
            while(True):
                if element.exists():
                    return True
                else:
                    swipe((150,800),(150,50))
                    sleep(0.4)
                    continue
        except NameError as e:
            print('--->>',e)

    def swipeAndClickELement(self,element):
        '''
        滑动查找元素并点击
        '''
        try:
            while True:
                if element.exists():
                    self.clickElement(element)
                    break
                else:
                    swipe((150,800),(150,50))
                    continue
        except NameError as e:
            print('--->>',e)

    @staticmethod
    def waitFor(By,value,timeout=3):
        try:
            if(By=="text"):
                element = poco(text=value).wait(timeout)
            elif(By=="name"):
                element = poco(name=value).wait(timeout)
            elif(By == "type"):
                element = poco(type=value).wait(timeout)
            elif(By == "xpath"):
                element = poco(value).wait(timeout)
            elif(By == "contains"):
                element = poco(textMatches='.*'+value+'.*').wait(timeout)
            else:
                raise Exception
        except Exception as e:
            raise Exception("参数不正确:"+By+"="+value)
        return  element

    @staticmethod
    def waitForVisibleOrNot(By,value,timeout=3):
        if Common.waitFor(By,value,timeout).exists():
            return True
        else:
            return False

    @staticmethod
    def waitForElemntVisibleOrNot(element,timeout=3):
        if element.wait(timeout).exists():
            return True
        else:
            return False

    @staticmethod
    def waitForTemplateVisibleOrNot(template):
        '''
        exists默认等待3s
        '''
        if exists(template):
            return True
        else:
            return False

    @staticmethod
    def clickElement(element):
        if (Common.waitForElemntVisibleOrNot(element)):
            element.click()
        else:
            raise Exception("元素不可见无法点击")

    @staticmethod
    def clickTemplate(template):
        if (Common.waitForTemplateVisibleOrNot(template)):
            touch(template)
        else:
            raise Exception("图片不可见无法点击")

    @staticmethod
    def putInValue(element,value):
        Common.clickElement(element)
        text(value)

    @staticmethod
    def poco_exist(element, assertContent):
        assert_equal(element.exists(), True, assertContent)

    @staticmethod
    def poco_not_exist(element, assertContent):
        assert_equal(element.exists(), False, assertContent)

    @staticmethod
    def assertTemplateExist(template,assertContent):
        assert_equal(Common.waitForTemplateVisibleOrNot(template), True, assertContent)

    @staticmethod
    def assertTemplateNotExist(self,template,assertContent):
        assert_equal(Common.waitForTemplateVisibleOrNot(template), False, assertContent)




if __name__ == "__main__":
    b = Common()
    # e=b.waitFor("text","测试一二三四")
    # b.waitFor("contains","粉天使").click()
    # b.assertTemplateExist(LandingPageMaterial["baiduLandingPageMaterial"],"验证顶通落地页")
    # Common.assertTemplateExist(LandingPageMaterial["baiduLandingPageMaterial"], "验证顶通落地页")
    # Common.poco_not_exist(poco(text="商品"),"测试")
    Common.swipeAndTouch(heathHomePageBoothMaterial["首页公共营销Banner"])
    # b.clickElement(e)
    # e=b.waitFor("name","com.pingan.papd:id/et_search_content")
    # b.putInValue(e,"测试")
    # e=b.waitFor("text","搜索")
    # b.clickElement(e)
    # b.swipeToElment(e)
    # b.swipeAndClickELement()
    # b.logOut()
    # b.switchHomePage(pageName="健康商城")
    # b.swipeToElm(elm=Template(r"tpl1548062898797.png", record_pos=(-0.013, 0.303), resolution=(720, 1280)))
