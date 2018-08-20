# -*- coding: utf-8 -*-
import xlrd,openpyxl
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
class ReadExcel():
    def __init__(self,excelpath,sheetname='Sheet1'):
        self.wb=openpyxl.load_workbook(excelpath)
        self.sheet=self.wb.active
    def dict_data(self):
        # for row in self.sheet.rows:
        #     for cell in row:
        #         print cell.value
        # print "------------------------------"
        # for columns in self.sheet.columns:
        #     for cell in columns:
        #         print cell.value
        # print "------------------------------"
        key=[]
        values=[]
        for title in list(self.sheet.rows)[0]:
            key.append(title.value)
        for data in list(self.sheet.rows)[1]:
            values.append(data.value)
        data=[]
        data.append(dict(zip(key,values)))
        return data
if __name__ == "__main__":
    filepath="C:\\Users\\Administrator\\denglutest.xlsx"
    a=ExcelUtil(filepath)
    b=ReadExcel(filepath)
    print a.dict_data()
    print b.dict_data()
