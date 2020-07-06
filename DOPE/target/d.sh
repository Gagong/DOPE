clear
PS3='Gagong Menu - Please enter your choice: '
options=("Run Dope" "Run Kitty" "Run Both" "Resume DOPE" "Resume Kitty" "Exit")
select opt in "${options[@]}"
do
    case $opt in
	    "Run Dope") 
			screen -ls  | egrep "^\s*[0-9]+.DISCORD-DOPE" | awk -F "." '{print $1}' | xargs kill
			clear
			screen -S DISCORD-DOPE -dm sudo java -jar dope.jar
			break
	        ;;
	    "Run Kitty")
			screen -ls  | egrep "^\s*[0-9]+.DISCORD-Kitty" | awk -F "." '{print $1}' | xargs kill
			clear
			screen -S DISCORD-Kitty -dm sudo java -jar KittyBot.jar
			break
			;;
	    "Run Both")
			screen -ls  | egrep "^\s*[0-9]+.DISCORD" | awk -F "." '{print $1}' | xargs kill
			clear
			screen -S DISCORD-DOPE -dm sudo java -jar dope.jar
			screen -S DISCORD-Kitty -dm sudo java -jar KittyBot.jar
			break
			;;
	    "Resume DOPE")
			screen -r DISCORD-DOPE
			break
			;;
	    "Resume Kitty")
			screen -r DISCORD-Kitty
			break
			;;
        "Exit")
			clear
			exit 1
            ;;
    *) echo "invalid option $REPLY";;
esac
done