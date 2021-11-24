import random

lines=open('/Users/linyouguang/Vscode/combine/combine.txt').readlines()
new = []  # 定义一个空列表，用来存储结果
for line in lines:
    temp1 = line.strip('\n')  # 去掉每行最后的换行符'\n'
    temp2 = temp1.split(',')  # 以','为标志，将每行分割成列表
    new.append(temp2)  # 将上一步得到的列表添加到new中
random.shuffle(new)#乱序一个列表
# print(new)
fm=open('/Users/linyouguang/Vscode/output/list_son.txt', 'w')
for i in new:
    x=len(i)
    for j in i[0:x-1]:
        fm.write(j)
        fm.write(',')
    for j in i[x-1]:
        fm.write(j)#去除列表中最后一个逗号
    fm.write("\n")
fm.close()

f_test = open('/Users/linyouguang/Vscode/input/5/test.txt',"w") 
f_train = open('/Users/linyouguang/Vscode/input/5/train.txt',"w") 
count = 0
with open("/Users/linyouguang/Vscode/output/list_son.txt") as f1:
    line = f1.readline()
    while line:
        if(count<60000):
            f_train.write(line)
        else:
            f_test.write(line)
        count = count + 1
        line = f1.readline()