import requests
import json
import datetime

link = 'https://powerofdark.space/api/status'
data = requests.get(link).text
data = json.loads(data)
ver = data['Versions']['cli']

webhook = "https://discordapp.com/api/webhooks/672124903075479568/X2bhdnkNcEBs4xENbTviBCVQgix6fzMIRvqo4pw8ct-5YfDA0idj-jhBCQgcjnEQt5VJ"

now = datetime.datetime.now()
data = {}
embed = {}

#First method (Message)
#data["content"] = "Date: **" + now.strftime("%d.%m.%y %H:%M:%S") + "** | DOPE updated on Zhoiak VPS! | New version: **" + ver + "**"
data["username"] = "VPS"

#Second method (Embed)
data["embeds"] = []
embed["description"] = "All bot started on Zhoiak VPS!"
embed["title"] = "New version: **" + ver + "**\nDate: **" + now.strftime("%d.%m.%y %H:%M:%S") + "**"
data["content"] = "@everyone"
data["embeds"].append(embed)

result = requests.post(webhook, data=json.dumps(data), headers={"Content-Type": "application/json"})

try:
    result.raise_for_status()
except requests.exceptions.HTTPError as err:
    print(err)
else:
    print("Payload delivered successfully, code {}.".format(result.status_code))