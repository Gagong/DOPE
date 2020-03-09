#!/bin/bash
screen -ls  | egrep "^\s*[0-9]+.DOPE" | awk -F "." '{print $1}' | xargs kill
cd
mkdir -p DOPE
cd DOPE
wget -N "https://raw.githubusercontent.com/ITKewai/DOPE-IT-ZHOIAK/master/AUTOUPDATER/AutoUpdater.py" && python AutoUpdater.py
chmod +x ./DOPE.cli
cd
mkdir -p DOPEMULTI/1
cp ./DOPE/DOPE.cli ./DOPEMULTI/1/DOPE.cli
mkdir -p DOPEMULTI/2
cp ./DOPE/DOPE.cli ./DOPEMULTI/2/DOPE.cli
mkdir -p DOPEMULTI/3
cp ./DOPE/DOPE.cli ./DOPEMULTI/3/DOPE.cli
mkdir -p DOPEMULTI/4
cp ./DOPE/DOPE.cli ./DOPEMULTI/4/DOPE.cli
mkdir -p DOPEMULTI/5
cp ./DOPE/DOPE.cli ./DOPEMULTI/5/DOPE.cli
mkdir -p DOPEMULTI/6
cp ./DOPE/DOPE.cli ./DOPEMULTI/6/DOPE.cli
mkdir -p DOPEMULTI/7
cp ./DOPE/DOPE.cli ./DOPEMULTI/7/DOPE.cli
mkdir -p DOPEMULTI/8
cp ./DOPE/DOPE.cli ./DOPEMULTI/8/DOPE.cli
mkdir -p DOPEMULTI/9
cp ./DOPE/DOPE.cli ./DOPEMULTI/9/DOPE.cli
mkdir -p DOPEMULTI/10
cp ./DOPE/DOPE.cli ./DOPEMULTI/10/DOPE.cli
mkdir -p DOPEMULTI/11
cp ./DOPE/DOPE.cli ./DOPEMULTI/11/DOPE.cli
mkdir -p DOPEMULTI/12
cp ./DOPE/DOPE.cli ./DOPEMULTI/12/DOPE.cli
mkdir -p DOPEMULTI/13
cp ./DOPE/DOPE.cli ./DOPEMULTI/13/DOPE.cli
mkdir -p DOPEMULTI/14
cp ./DOPE/DOPE.cli ./DOPEMULTI/14/DOPE.cli
mkdir -p DOPEMULTI/15
cp ./DOPE/DOPE.cli ./DOPEMULTI/15/DOPE.cli
mkdir -p DOPEMULTI/16
cp ./DOPE/DOPE.cli ./DOPEMULTI/16/DOPE.cli
mkdir -p DOPEMULTI/17
cp ./DOPE/DOPE.cli ./DOPEMULTI/17/DOPE.cli
mkdir -p DOPEMULTI/18
cp ./DOPE/DOPE.cli ./DOPEMULTI/18/DOPE.cli
mkdir -p DOPEMULTI/19
cp ./DOPE/DOPE.cli ./DOPEMULTI/19/DOPE.cli
mkdir -p DOPEMULTI/20
cp ./DOPE/DOPE.cli ./DOPEMULTI/20/DOPE.cli
mkdir -p DOPEMULTI/21
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/21/DOPE.cli
mkdir -p DOPEMULTI/22
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/22/DOPE.cli
mkdir -p DOPEMULTI/23
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/23/DOPE.cli
mkdir -p DOPEMULTI/24
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/24/DOPE.cli
mkdir -p DOPEMULTI/25
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/25/DOPE.cli
mkdir -p DOPEMULTI/26
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/26/DOPE.cli
mkdir -p DOPEMULTI/27
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/27/DOPE.cli
mkdir -p DOPEMULTI/28
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/28/DOPE.cli
mkdir -p DOPEMULTI/29
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/29/DOPE.cli
mkdir -p DOPEMULTI/30
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/30/DOPE.cli
mkdir -p DOPEMULTI/31
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/31/DOPE.cli
mkdir -p DOPEMULTI/32
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/32/DOPE.cli
mkdir -p DOPEMULTI/33
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/33/DOPE.cli
mkdir -p DOPEMULTI/34
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/34/DOPE.cli
mkdir -p DOPEMULTI/35
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/35/DOPE.cli
mkdir -p DOPEMULTI/36
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/36/DOPE.cli
mkdir -p DOPEMULTI/37
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/37/DOPE.cli
mkdir -p DOPEMULTI/38
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/38/DOPE.cli
mkdir -p DOPEMULTI/39
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/39/DOPE.cli
mkdir -p DOPEMULTI/40
sudo cp ./DOPE/DOPE.cli ./DOPEMULTI/40/DOPE.cli
sleep 0.5
#DISCORD FILE AGGIORNATI
screen -S xDD -dm python xDD.sh
#DISCORD FILE AGGIORNATI
./xSTART.sh