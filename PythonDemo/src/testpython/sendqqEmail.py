#coding=utf-8
import smtplib
from email.mime.text import MIMEText


def sendqqEmail(subject,content,msg_to='315118616@qq.com'):
    msg_from='1056396153@qq.com'                                 #发送方邮箱
    passwd='ipegauclyoczbdde'                                   #填入发送方邮箱的授权码
    # msg_to='315118616@qq.com'                                  #收件人邮箱

    msg = MIMEText(content)
    msg['Subject'] = subject
    msg['From'] = msg_from
    msg['To'] = msg_to
    try:
        s = smtplib.SMTP_SSL("smtp.qq.com",465)#邮件服务器及端口号
        s.login(msg_from, passwd)
        s.sendmail(msg_from, msg_to, msg.as_string())
        # print ("发送邮件成功")
    except s.SMTPException as e:
        print ("发送邮件失败")
        pass
    finally:
        s.quit()


if __name__ == "__main__":
    subject="测试"                                     #主题
    content="测试"     #正文
    sendqqEmail(subject,content,"18964698690@163.com")