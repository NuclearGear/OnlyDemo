from airtest.cli.runner import AirtestCase, run_script
from argparse import *
import airtest.report.report as report
import jinja2
import shutil
import os
import io
from core.MultiAdb import MultiAdb


class CustomAirtestCase(AirtestCase):
    def setUp(self):
        print("custom setup")
        # add var/function/class/.. to globals
        # self.scope["hunter"] = "i am hunter"
        # self.scope["add"] = lambda x: x+1

        # exec setup script
        # self.exec_other_script("setup.owl")
        super(CustomAirtestCase, self).setUp()

    def tearDown(self):
        print("custom tearDown")
        # exec teardown script
        # self.exec_other_script("teardown.owl")
        super(CustomAirtestCase, self).setUp()

    def run_air(self, root_dir, device):
        # 聚合结果
        results = []
        # 获取所有用例集
        root_log = root_dir + '\\' + 'log'
        if os.path.isdir(root_log):
            shutil.rmtree(root_log)
        else:
            os.makedirs(root_log)
            print(str(root_log) + 'is created')

        for f in os.listdir(root_dir):
            if f.endswith(".air"):
                # f为.air案例名称：手机银行.airK
                airName = f
                script = os.path.join(root_dir, f)
                # airName_path为.air的全路径：D:\tools\airtestCase\案例集\log\手机银行
                print(script)
                # 日志存放路径和名称：D:\tools\airtestCase\案例集\log\手机银行1
                log = os.path.join(root_dir, 'log' + '\\' + airName.replace('.air', ''))
                log_1 = script + '\\' + 'log'
                print(log)
                if os.path.isdir(log):
                    shutil.rmtree(log)
                else:
                    os.makedirs(log)
                    print(str(log) + 'is created')
                output_file = log + '\\' + 'log.html'
                args = Namespace(device=device, log=log_1, recording=None, script=script)
                try:
                    run_script(args, AirtestCase)
                except:
                    pass
                finally:
                    # rpt = report.LogToHtml(script, log)
                    rpt = report.LogToHtml(script, log_1, script_name=f.replace(".air", ".py"))
                    rpt.report("log_template.html", output_file=output_file)

                    result = {}
                    result["name"] = airName.replace('.air', '')
                    result["result"] = rpt.test_result
                    results.append(result)
        # 生成聚合报告
        env = jinja2.Environment(
            loader=jinja2.FileSystemLoader(root_dir),
            extensions=(),
            autoescape=True
        )
        template = env.get_template("summary_template.html", root_dir)
        html = template.render({"results": results})
        output_file = os.path.join(root_dir, "summary.html")
        with io.open(output_file, 'w', encoding="utf-8") as f:
            f.write(html)
        print(output_file)


if __name__ == '__main__':
    test = CustomAirtestCase()
    device = ['android:///82f89b517d83']
    test.run_air('.\\TestCase', device)