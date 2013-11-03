f = open("relationships-1g.txt", "r")
out = open("relationships-mini.txt", "w")
count = 0
for line in f :
    if(count > 3500 ):
        break
    out.write(line)
    count += 1
out.close()
f.close()
