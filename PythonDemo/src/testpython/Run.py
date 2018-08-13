# coding=utf-8
import unittest,HTMLTestRunner_cn,time

def creatsuite():
    testsuite=unittest.TestSuite()
    test_dir = "D:\\OnlyGit\\helloWorld\\src\\testpython"
    discover = unittest.defaultTestLoader.discover(start_dir=test_dir,pattern="qq*.py",top_level_dir=None)
    #discover方法筛选出来的用例，循环添加到测试套件中
    for test_suite in discover:
        for test_case in test_suite:
            testsuite.addTests(test_case)
    return testsuite
if __name__ == '__main__':
    now = time.strftime("%Y_%m_%d %H_%M")
    filename = "D:\\OnlyGit\\helloWorld\\src\\report\\result.html"
    fp = open(filename, "wb")
    runner = HTMLTestRunner_cn.HTMLTestRunner(stream=fp,title=u'测试报告',description=u'用例执行情况：')
    runner.run(creatsuite())
    fp.close()