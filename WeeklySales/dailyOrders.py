from random import randint

files = ["First", "Second", "Third", "Fourth"]

data = []
for i in range(len(files)):

    # read in file data 
    fileIn = open(files[i] + "WeekSales.csv", "r")
    line = fileIn.readline().strip() 

    while line != '':
        data.append(line.split(","))
        line = fileIn.readline().strip()

    fileIn.close()

# delete unecessary rows 
daysInWeek = 7
for i in range(daysInWeek * len(files)):
    data.pop(i * 20)

# initialize new data variables 
newData     = []                    # daily sales
startDay    = 1                       
orderID     = 1000                  # starting order ID
cost        = -1 
workerIDs   = [1452, 1732, 1526, 1252, 1922, 1219]  
managerIDs  = [9869, 9420]

# read order information by date 
for i in range(daysInWeek * len(files)):
    newData.append([])
    for j in range(20):
        row = data[i*20 + j]

        if j == 0:  

            #add orderID to newData -- FIX 
            newData[i].append(orderID)
            orderID += 1

            # add total cost to newData -- FIX 
            cost = row[-2] + row[-1]
            #print(float(cost[2:-1]))
            newData[i].append(float(cost[2:-1]))

            # add date to newData -- FIX  
            day = str(startDay) 
            if len(day) == 1:
                day = "0" + day
            newData[i].append("2022-02-" + day)
            startDay += 1

            # add workerID to newData -- FIX 
            workerID = randint(0, len(workerIDs) - 1)
            newData[i].append(workerIDs[workerID])
            
            # add managerID to newData -- FIX 
            managerID = randint(0, len(managerIDs) - 1)
            newData[i].append(managerIDs[managerID])

        else:    

            # read in menu key
            newData[i].append(int(row[2]))


# write order entities to a new file 
fileOut = open("Orders.csv", "w")
for i in range(len(newData)):
    line = ""
    for j in range(len(newData[i]) - 1):
        line += str(newData[i][j]) + ", "
    line += str(newData[i][j]) + "\n"
    fileOut.write(line)
fileOut.close()


