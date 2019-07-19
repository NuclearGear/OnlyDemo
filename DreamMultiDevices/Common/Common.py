# -*- encoding=utf8 -*-
__author__ = "FANXINXIN371"

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
                        
                        
    def swipeAndTouch(self,elm):
        '''
        滑动查找传入的图片并点击
        elm：要查找的图片
        '''
        try:
            count = 0
            while True:
                if (exists(elm)):
                    touch(elm)
                    break
                else:
                    swipe((150,800),(150,50))
                    continue
        except NameError as e:
            print('--->>',e)     
            
            
    def swipeToElm(self,elm):
        '''
        滑动查找传入的图片
        elm：要查找的图片
        '''
        try:
            count = 0
            while True:
                if (exists(elm)):
                    return True
                else:
                    swipe((150,800),(150,50))
                    sleep(0.5)
                    continue
        except NameError as e:
            print('--->>',e)    
        
            
    def switchHomePage(self,pageName):
        '''
        健康商城、闪电购药页面切换
        1、输入的pageName必须为：健康商城、闪电购药
        '''
        try:
            # 判断当前是否存在页面切换底bar
            for i in range(2):
                if poco(name="com.pingan.papd:id/tabSwitchStub").exists():
                    if poco(text=pageName).exists():
                        poco(text=pageName).click()
                        sleep(2)
                    else:
                        poco(name="com.pingan.papd:id/tabSwitchStub").click()
                        sleep(2)
                    continue
                else: 
                    print("当前非底bar切换页面")
        except NameError as e:
            print('--->>',e)         
            

            
b = Base()
b.logIn(phoneNum="18652081580")
# b.logOut()
# b.switchHomePage(pageName="健康商城")
# b.swipeToElm(elm=Template(r"tpl1548062898797.png", record_pos=(-0.013, 0.303), resolution=(720, 1280)))
