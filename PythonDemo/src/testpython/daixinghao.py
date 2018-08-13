# coding:utf-8
def foo(*args, **kwargs):
    print 'args = ', args
    print 'kwargs = ', kwargs
    print '---------------------------------------'
foo(1,2,3)
foo(a=1,b=2,c=3)