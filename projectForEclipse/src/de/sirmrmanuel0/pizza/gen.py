import json
import os

data = []

# Erstellen der Ordnerstruktur
os.makedirs("pizzen/small/", exist_ok=True)
os.makedirs("pizzen/mid/", exist_ok=True)
os.makedirs("pizzen/normal/", exist_ok=True)
os.makedirs("pizzen/big/", exist_ok=True)

# Laden der Daten aus der JSON-Datei
with open("sorten.json", "r", encoding="utf-8") as json_f:
    data = json.load(json_f)

# Extrahieren der Namen aus den Daten
names = [key for key, val in data.items()]

# Iteration über die Namen
for name in names:
    beschreibung = data[name]['beschreibung']
    
    p = []
    
    p.append(str(data[name]['preise'][0]))
    p.append(str(data[name]['preise'][1]))
    p.append(str(data[name]['preise'][2]))
    p.append(str(data[name]['preise'][3]))

    # Erstellen eines Klassen-Namens
    class_name = name.replace(" ", "")
    class_name = class_name.replace(".", "")
    class_name = class_name.replace("-", "")

    # Iteration über die Größen (small, mid, normal, big)
    for i in range(4):
        ordner = ""
        if i == 0:
            ordner = "small"
        if i == 1:
            ordner = "mid"
        if i == 2:
            ordner = "normal"
        if i == 3:
            ordner = "big"

        # Erstellen der Java-Codezeilen für jede Pizza
        java_lines = [
            f"package de.sirmrmanuel0.pizza.pizzen.{ordner};\n",
            "\n",
            "import de.sirmrmanuel0.pizza.Pizza;\n",
            "\n",
            "\n",
            "// This is an AUTO-Generated Class\n",
            "// Es konnten keine neuen Methoden erstellt werden, da es unmöglich wäre\n",
            "// für alle Pizzen in Warenkorb.java ein switch statement oder ähnliches zu verwenden,\n",
            "// um immer die richtigen Konstruktoren und Klassen zu benutzen.\n",
            "// Deshalb mussten Methoden überschrieben werden, auch wenn es Standardmethoden wie toString sind.\n",
            "public class " + class_name + " extends Pizza {\n",
            "    public " + class_name + "(){\n",
            '        Name = "Pizza ' + name + '";\n',
            '        preis = ' + p[i] + ';\n',
            '        Beschreibung = "' + beschreibung + '";\n',
            '    }\n',
            "\n",
            "   @Override\n",
            "   public void setPreis(double increment){this.anzahl += (int) increment;}\n",
            "\n",
            "   @Override\n",
            "   public String toString(){return String.valueOf(anzahl);}\n",
            '}\n'
        ]

        # Schreiben der Java-Codezeilen in die Java-Datei
        with open(f"pizzen/{ordner}/{class_name}.java", "w", encoding="utf-8") as f:
            for ii in java_lines:
                f.write(ii)