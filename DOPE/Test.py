import requests
import json
import datetime

webhook = "https://discordapp.com/api/webhooks/697550461628186674/gbxd6kraDe-41GH3gTKBV06ptQ1_cTLcb6X-IHjo-pKUVVoWgjTfgtgq9sTsRx5l3xJd"

now = datetime.datetime.now()
data = {}
embed = {}

data["username"] = "DOPE VPS"

#Second method (Embed)
data["embeds"] = []
embed["description"] = "DOPE BOT successfully runned!"
embed["title"] = "**DOPE | LOG\nDate: " + now.strftime("%d.%m.%y %H:%M:%S") + "**"

data["embeds"].append(embed)

result = requests.post(webhook, data=json.dumps(data), headers={"Content-Type": "application/json"})

try:
    result.raise_for_status()
except requests.exceptions.HTTPError as err:
    print(err)
else:
    print("Payload delivered successfully, code {}.".format(result.status_code))