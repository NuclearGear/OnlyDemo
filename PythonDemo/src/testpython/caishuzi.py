# -*- coding: utf-8 -*-
import random
a = 1
b = 99
key = random.randint(1, 100)
while 1:
    guess = int(input("请输入一个整数%d" % a + "到%d:" % b))
    if guess<key and guess > a:
        a = guess
        print('请输入%d到' % a+"到%d:" % b)
    elif guess>key and guess<b:
        b = guess
        print('请输入%d' % a+"到%d:" % b)
    elif guess <= 1 or guess >= 100:
        print("小伙子，别调皮，请重新输入")
    elif guess == key:
        print('真聪明，猜对了！')
        break
