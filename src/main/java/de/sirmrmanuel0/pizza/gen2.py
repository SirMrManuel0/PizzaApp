import json

data = []

with open("sorten.json", "r", encoding="utf-8") as json_f:
    data = json.load(json_f)

names = [key for key, val in data.items()]

with open("array.txt", "w", encoding="utf-8") as f:
    for name in names:
        class_name = name.replace(" ", "")
        class_name = class_name.replace(".", "")
        class_name = class_name.replace("-", "")
        f.write("instances.add(new "+class_name+"());\n")
        
    
    