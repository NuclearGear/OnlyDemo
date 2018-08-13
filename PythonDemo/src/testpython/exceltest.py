# -*- coding: utf-8 -*-
import xlrd
class ExcelUtil():
    def __init__(self,excelpath,sheetname="Sheet1"):
        self.data=xlrd.open_workbook(excelpath)
        self.table=self.data.sheet_by_name(sheetname)

        self.keys=self.table.row_values(0)#第一行key
        self.rowNum=self.table.nrows#总行数
        self.colNum=self.table.ncols#总列数

    def dict_data(self):
        if self.rowNum<=1:
            print("小于1")
        else:
            r=[]
            j=1
            for i in range(self.rowNum-1):
                s={}
                values=self.table.row_values(j)
                for x in range(self.colNum):
                    s[self.keys[x]]=values[x]
                r.append(s)
                j+=1
            return r

if __name__ == "__main__":
    filepath="C:\\Users\\Administrator\\denglutest.xlsx"
    a=ExcelUtil(filepath)
    print a.dict_data()