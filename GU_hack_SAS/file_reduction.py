f = open("relationships.txt", "r")
out = open("relationships-100m.txt", "w")
count = 0
for line in f :
    if(count > 6000000):
        for further_line in f:
            if (further_line[0] != " "):
                break;
            else:
                out.write(further_line)
        break
    out.write(line)
out.close()
f.close()
