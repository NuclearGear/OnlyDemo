# coding:utf-8
import requests,datetime,time,threading,collections,re 
from bs4 import BeautifulSoup

test_url1 = "http://10.200.44.37:8889/b?exid=1&pdt=1&d=IFC_develop_2_autobuild&c=1"
test_url2 = "http://10.200.44.23:8889/b?exid=1&pdt=1&d=IFC_develop_2_autobuild&c=1"
pre_testurl="http://115.238.73.103:8080/b?exid=1&pdt=1&d=ifc_ui&c=1"
dc_cache_url="http://10.200.44.31:9980/dc/cache?db=IFC_develop_2_autobuild"
def guzi_posttest(test_url,b=1,w=1,c=1):
    for i in range(b):
        json={"id":"LygHZ1CcR0BLxS60b0q","tmax":100,"site":{"id":"220","domain":"http://www.bbb.com","sectioncat":["A02B00C00v2012.1"],"page":"allyes","ref":"","publisher":{"id":"282"},"allyessitetype":"M01N12v2012.1","allyespageform":"R10102v2012.1"},"device":{"dnt":0,"ua":"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0","ip":"10.200.39.15","language":"zh","js":1,"devicetype":2,"make":"","model":"","os":"ios","osv":"7.0.1","connectiontype":"2","geo":{"lat":"116.406568","lon":"39.928879"},"carrier":"46000","ifa":"ifa","didsha1":"didsha1","macsha1":"macsha1","hwv":"5S","h":1024,"w":768,"pxratio":1},"user":{"id":"LzkfX7QWJeGnExgED7Ee0b0O","cver":2},"imp":[{"id":"1","banner":{"w":300,"h":250,"pos":0,"maxbyte":500,"allyesadform":"s1321v1","allyesadformat":[0,1,2]},"tagid":"282-06","bidfloor":0.28,"bidfloorcur":"CNY"}],"cur":["CNY"],"bcat":[],"badv":[],"app":{"id":"101","cat":["29999"],"bundle":"com.suning.yigou","publisher":{"id":"123"}}}
        starttime = datetime.datetime.now()
        r = requests.post(test_url, json=json,verify=False)
        #print r.status_code
        #print r.headers
        if r.status_code ==200:
            response=r.text
            print (response)
            if w==1:
                try:
                    nurl = "".join(re.findall('''"nurl":"(.+?)"''', response))
                    #clicknurl= "".join(re.findall('''"adm":"(.+?)"''', response))
                    apr="dqG_j8LtuUBYb-HcZUNkWnVwTXBjczhqZThOZW9tdWNjSWQ1ZHRXUzE"
                    win=nurl.replace("${AUCTION_PRICE_ENC}", apr)
                    #print win
                    winresponse = requests.get(win)
                    print ("win发送第"+str(i+1)+"次"+str(winresponse.status_code)+test_url)
                    endtime = datetime.datetime.now()
                    print (endtime)
                except Exception as e:
                    endtime = datetime.datetime.now()
                    print (endtime)
                    #print"未找到winnurl"+e
            if c ==1:
                try:
                    clicknurl= "".join(re.findall('''winmaxClickUrl :(.+?)",''', response))
                    clicknurl=clicknurl.replace("\\", "")
                    clicknurl=clicknurl.replace("u=", "u=http://www.suning.com")
                    clicknurl=clicknurl.replace('''\"https''', "http")
                    #print clicknurl
                    clickresponse = requests.get(clicknurl)
                    print ("click发送第"+str(i+1)+"次"+str(clickresponse.status_code)+test_url)
                    endtime = datetime.datetime.now()
                    print (endtime)
                except Exception as e:
                    endtime = datetime.datetime.now()
                    print (endtime)
                    #print u"未找到clickurl"+e
        else:
            print("请求失败%s"%test_url)
            endtime = datetime.datetime.now()
            print (endtime)
            #raise  Exception
def DC_cache():
    dc_cache=requests.get(dc_cache_url)
    #print dc_cache.status_code
    dc=dc_cache.content
    soup = BeautifulSoup(dc, "html.parser")
    td=soup.find_all(['td'])
    cache=[]
    for i in td:
        i=i.text.replace("<td>", "")
        cache.append(i)
    cachetitle=[u"每日预算",u"请求数",u"点击数",u"曝光数",u"花费",u"状态码",u"使用百分比",u"剩余预算"]
    cachedata=[cache[18],cache[21],cache[22],cache[23],cache[24],cache[25],cache[26],cache[27]]
    cache=collections.OrderedDict()
    cache=dict(zip(cachetitle,cachedata))
    for key in cache:
        print (key+":"+cache[key],)
    print ("")
    return cache

def dctest(th):
    threads = []
    t1 = threading.Thread(target=guzi_posttest,args=(test_url1,b,w,c))
    threads.append(t1)
    t2 = threading.Thread(target=guzi_posttest,args=(test_url2,b,w,c))
    threads.append(t2)
    cache1=DC_cache()
    starttime = datetime.datetime.now()
    print ('start:%s'%(starttime))
    if th==2:
        for t in threads:
             t.setDaemon(True)
             t.start()
        t.join()
    elif th==1:
        guzi_posttest(test_url1,b,w,c)
    else:
        raise Exception
    endtime = datetime.datetime.now()
    print ('end:%s' %endtime)
    time.sleep(5)
    cache2=DC_cache()
    m=int(cache2[u"请求数"])-int(cache1[u"请求数"])
    n=int(cache2[u"曝光数"])-int(cache1[u"曝光数"])
    p=int(cache2[u"点击数"])-int(cache1[u"点击数"])
    if th==2:
        assert m==b*2
        if w==1:
            assert n==b*2
        if c==1:
            assert p==b*2
        print ("result:pass")
    elif th==1:
        assert m==b
        if w==1:
            assert n==b
        if c==1:
            assert p==b
        print ("result:pass")
    else:
        print ("error")
if __name__ == "__main__":
    b=1
    w=1
    c=0
    t=1
    dctest(t)