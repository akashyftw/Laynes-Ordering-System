from asyncore import write
import csv

file = open("MenuKey.csv", "r")
line = file.readline().strip()
data = []
lineNum = 3
while line != '':
    data.append(line.split(","))
    line = file.readline().strip()
data[2][2] = "5 finger meal"
data[4][2] = "3 finger meal"
data[6][3] = "iced tea"
data[7][3] = "20 fingers and 4 toasts and 4 fries and 8 sauces"
data[8][2] = "club sandwich meal"
data[9][2] = "club sandwich only"
data[9][3] = "club sandwich"
data[10][2] = "sandwich meal combo"
data[11][3] = "2 fingers"
data[12][2] = "grill cheese meal combo"
data[13][3] = "sandwich only"
data[14][2] = "laynes sauce"
data[15][2] = "chicken finger"
data[16][3] = "side and 1 finger"
data[17][3] = "side and 5.5 oz"
data[18][2] = "potato salad"
data[18][3] = "side and 5.5 oz"
data[18][2] = "crinkle cut fries"
data[19][2] = "fountain drink"
data[20][2] = "bottle drink"
data[20][3] = "8 oz drink"

fields = data[1]
rows = [data[2],
        data[3],
        data[4],
        data[5],
        data[6],
        data[7],
        data[8],
        data[9],
        data[10],
        data[11],
        data[12],
        data[13],
        data[14],
        data[15],
        data[16],
        data[17],
        data[18],
        data[19],
        data[20]]
filename = "MenuKeyFormatted.csv"

with open(filename, 'w') as csvfile:
    csvWrit = csv.writer(csvfile)
    csvWrit.writerow(fields)
    csvWrit.writerows(rows)


