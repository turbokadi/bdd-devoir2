#!/bin/python

import io
import re
import json
import requests

resultList = []

regexName = '<li>.*<a href="(.*.html.*)">(.*)<\\/a><\\/li>'
regexSpells = '\/coreRulebook\/spells[^"]*" ?>([^<]*)'

def searchPets(url,page):
    namePage = requests.get(url+"/"+page).text
    names = re.findall(regexName, namePage)
    for name in names:
        monster = name[1].replace(",","")
        print("Name : "+monster)
        spellPage = requests.get(url+"/"+name[0]).text
        entry = {
            "name": monster,
            "spells": sorted(set(re.findall(regexSpells,spellPage))),
        }
        resultList.append(entry)

searchPets("http://legacy.aonprd.com/bestiary","monsterIndex.html")
searchPets("http://legacy.aonprd.com/bestiary2","additionalMonsterIndex.html")
searchPets("http://legacy.aonprd.com/bestiary3","monsterIndex.html")
searchPets("http://legacy.aonprd.com/bestiary4","monsterIndex.html")
searchPets("http://legacy.aonprd.com/bestiary5","index.html")

file = open("Bestiary.json","w")
file.write(json.dumps(resultList, sort_keys=True, indent=4, separators=(',', ': ')))
file.close()
