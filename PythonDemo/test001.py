# a ="we are the teet"
# #print (' '.join(a[:-2].split()[::-1]))
# a=a.split()[::-1]
# a[1]=a[1][:-1]
# print ' '.join(a)
a=["50","20","30","10","99","1"]
print range(5)
print range(5,5)
class listsort:
    def sort(self,list):
        for x in range(len(list)-1):
            for j in range(len(list)-1-x):
                if list[j] > list[j+1]:
                    list[j],list[j+1]=list[j+1],list[j]
        return list

    def sort2(self,list):
        for x in range(len(list)-1):
            for j in range(x+1,len(list)):
                if list[x] > list[j]:
                    list[x]=list[j]
        return list
haha=listsort()
print haha.sort(a)
print haha.sort2(a)