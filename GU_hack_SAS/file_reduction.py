f = open("relationships.txt", "r")
out = open("relationships-1g.txt", "w")
count = 0
for line in f :
    if(count > 60000000 and line[0] != "}"):
        break
    out.write(line)
    count += 1
out.close()
f.close()
