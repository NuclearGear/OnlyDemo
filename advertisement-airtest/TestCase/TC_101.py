# -*- coding: utf-8 -*-
__author__ = "Only"

import unittest
from tools import  Screencap
from airtest.core.api import *
from poco.drivers.unity3d import UnityPoco
from Common.Common import Common
from BoothMaterial.BoothMaterial import *


# _print = print
# def print(*args, **kwargs):
#     _print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()), *args, **kwargs)

def Main(devices):
    print("{}进入unittest".format(devices))
    class TC101(unittest.TestCase):
        u'''测试用例101的集合'''

        @classmethod
        def setUpClass(self):
            u''' 这里放需要在所有用例执行前执行的部分'''


        def setUp(self):
            u'''这里放需要在每条用例前执行的部分'''


        def test_01_SP001(self):
            u'''验证顶通健康面'''
            # 每个函数里分别实例poco，否则容易出现pocoserver无限重启的情况
            print("验证顶通健康面")
            # if(Common.waitForTemplateVisibleOrNot(heathHomePageBoothMaterial["顶通健康面"])):
            touch(heathHomePageBoothMaterial["顶通健康面"],5)
            # if(Common.waitForTemplateVisibleOrNot(heathHomePageBoothMaterial["顶通健康面"])):
            #     Common.clickTemplate(heathHomePageBoothMaterial["顶通健康面"])
            sleep(2)
            Screencap.GetScreen(time.time(), devices, "顶通健康面落地页")
            Common.assertTemplateExist(LandingPageMaterial["baiduLandingPageMaterial"],"验证落地页")
            # el=Common.waitFor("name","com.pingan.papd:id/native_title_bar_iv_left",1)
            el=Common.waitFor("name","com.pingan.papd:id/title_back_icon",1)
            el.click()
            # Common.waitFor("xpath","com.pingan.papd:id/pop_ad_close_half").click()

        def test_02_of_MP007(self):
            u'''验证健康首页悬浮球广告'''
            # 每个函数里分别实例poco，否则容易出现pocoserver无限重启的情况
            print("验证健康首页悬浮球广告")
            # if(Common.waitForTemplateVisibleOrNot(heathHomePageBoothMaterial["健康首页悬浮球广告"])):
            touch(heathHomePageBoothMaterial["健康首页悬浮球广告"])
            sleep(2)
            Common.assertTemplateExist(LandingPageMaterial["baiduLandingPageMaterial"],"验证落地页")
            # el=Common.waitFor("name","com.pingan.papd:id/native_title_bar_iv_left",1)
            el=Common.waitFor("name","com.pingan.papd:id/title_back_icon",1)
            el.click()

        def test_03_of_MP009(self):
            u'''验证健康首页顶部Banner'''
            # 每个函数里分别实例poco，否则容易出现pocoserver无限重启的情况
            print("验证健康首页顶部Banner")
            if(Common.waitForTemplateVisibleOrNot(heathHomePageBoothMaterial["健康首页顶部Banner"])):
                touch(heathHomePageBoothMaterial["健康首页顶部Banner"])
                sleep(2)
                Common.assertTemplateExist(LandingPageMaterial["baiduLandingPageMaterial"],"验证落地页")
                #       el=Common.waitFor("name","com.pingan.papd:id/native_title_bar_iv_left",1)
                el=Common.waitFor("name","com.pingan.papd:id/title_back_icon",1)
                el.click()

        def test_04_of_MP006(self):
            u'''验证首页公共营销Banner'''
            # 每个函数里分别实例poco，否则容易出现pocoserver无限重启的情况
            print("验证首页公共营销Banner")
            if(Common.waitForTemplateVisibleOrNot(heathHomePageBoothMaterial["首页公共营销Banner"])):
                touch(heathHomePageBoothMaterial["首页公共营销Banner"])
            else:
                Common.swipeAndTouch(heathHomePageBoothMaterial["首页公共营销Banner"])
            sleep(2)
            Common.assertTemplateExist(LandingPageMaterial["baiduLandingPageMaterial"],"验证落地页")
            # el=Common.waitFor("name","com.pingan.papd:id/native_title_bar_iv_left",1)
            el=Common.waitFor("name","com.pingan.papd:id/title_back_icon",1)
            el.click()
            if(Common.waitForVisibleOrNot("xpath","com.pingan.papd:id/pop_ad_close_half")):
                Common.waitFor("xpath","com.pingan.papd:id/pop_ad_close_half").click()

        def tearDown(self):
            pass

        @classmethod
        def tearDownClass(self):
            u'''这里放需要在所有用例后执行的部分'''
            pass

    srcSuite = unittest.makeSuite(TC101)
    return srcSuite