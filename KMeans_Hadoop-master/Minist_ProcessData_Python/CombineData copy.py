file_label = '/Users/linyouguang/Vscode/test1/label/test_label.txt'
file_image = '/Users/linyouguang/Vscode/test1/image/test_image.txt'
f = open('/Users/linyouguang/Vscode/combine/combine_test.txt',"w") 
with open(file_label) as f1, open(file_image) as f2:
     l1 = f1.readline()
     label_item = l1.split(",")
     line = f2.readline()
     count = 0
     while line:
        f.write(label_item[count]+','+line)
        # f.write('\n')
        count = count + 1
        line = f2.readline()