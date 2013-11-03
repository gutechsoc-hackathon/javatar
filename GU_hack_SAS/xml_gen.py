f = open("relationships-100m.txt", "r")
keys = {}
for line in f :
    items = line[:-1].split()
    if (len(items) == 1 and items[0] != "{" and items[0] != "}"):
        keys[items[0]] = []

o = open("graph_temp4.gexf", "w")
o.write("""<?xml version="1.0" encoding="UTF-8"?>
<gexf xmlns:viz="http:///www.gexf.net/1.1draft/viz" version="1.1" xmlns="http://www.gexf.net/1.1draft">
<meta lastmodifieddate="2010-04-01+00:34">
<creator>Gephi 0.7</creator>
</meta>
<graph mode="static" defaultedgetype="directed"><nodes>\n""")
for key in keys:
    o.write("<node id=\"%s\" label=\"%s\"></node>\n" % (key, key))
o.write("</nodes><edges>")

f.seek(0,0)
print(len(keys.keys()))

for line in f :
    items = line[:-1].split()
    if (len(items) == 1 and items[0] != "{" and items[0] != "}"):
        key = items[0]
        for further_line in f:
            items2 = further_line[:-1].split()
            
            if (items2[0] == "}"):
                #print("breaking")
                break
            if (len(items2) == 2):
                #print(items2[1])
                if (items2[1] in keys):
                    #print("in keys " + items2[1] + " " + key)
                    lst = keys.get(key, [])
                    lst += [items2[1]]
count = 0
for node in keys:
    for key in keys[node]:
        o.write("<edge id=\"%d\" source=\"%s\" target=\"%s\"/>\n" % (count, node, key))
        count+=1
o.write("</edges></graph></gexf>")
o.close()

f.close() 


