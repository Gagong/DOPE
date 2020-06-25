import requests
import json
import datetime

webhook = "https://discordapp.com/api/webhooks/725716864298123355/0YtPPth5bk9oIpSMn--oszB_vbkCfmbqaS59tqJ9FV_a7V_ebKQjlUxL8PYTniXqy7jo"

now = datetime.datetime.now()
data = {}
embed = {}

data["username"] = "DOPE RUNLOG"

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