import json

data = []

with open("sorten.json", "r", encoding="utf-8") as json_f:
    data = json.load(json_f)

names = [key for key, val in data.items()]

for name in names:
    beschreibung = data[name]['beschreibung']
    p1 = str(data[name]['preise'][0])
    p2 = str(data[name]['preise'][1])
    p3 = str(data[name]['preise'][2])
    p4 = str(data[name]['preise'][3])
    class_name = name.replace(" ", "")
    class_name = class_name.replace(".", "")
    class_name = class_name.replace("-", "")


    java_lines = [
        "package de.sirmrmanuel0.pizza.pizzen;\n",
        "\n",
        "import de.sirmrmanuel0.pizza.BetterPizza;\n",
        "\n",
        "\n",
        "// This is an AUTO-Generated Class\n",
        "public class " + class_name + " extends BetterPizza {\n",
        "    public " + class_name + "(){\n",
        '        Name = "Pizza ' + name + '";\n',
        '        Beschreibung = "' + beschreibung + '";\n',
        '        prizes = new double[] {' + p1 + ', ' + p2 + ', ' + p3 + ', ' + p4 + '};\n',
        '    }\n',
        '}\n'
    ]

    with open(f"pizzen/{class_name}.java", "w", encoding="utf-8") as f:
        for i in java_lines:
            f.write(i)