# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.http import HttpResponse,HttpResponseRedirect
from django.shortcuts import render
from django.contrib import auth
from django.contrib.auth.decorators import login_required

# Create your views here.
# def index(request):
#     return HttpResponse("hello")

def index(request):
    return render(request,"index.html")

@login_required
def event_manage(request):
    # username = request.COOKIES.get('user', '') # 读取浏览器 cookie
    username=request.session.get('user','')
    return render(request,"event_manage.html",{"user":username})

def login_action(request):
    if request.method == 'POST':
        username=request.POST.get('username','')
        password=request.POST.get('password','')
        user =auth.authenticate(username=username,password=password)
        if username is not None:
            auth.login(request,user)
            # return HttpResponse('login success!')
            request.session['user'] = username # 将 session 信息记录到浏览器
            response=HttpResponseRedirect('/event_manage/')
            # response.set_cookie("user",username,3600) #加cookie
            return response
        else:
            return render(request,'index.html', {'error': 'username or password error!'})
    else:
        return render(request,'index.html', {'error': 'username or password error!'})