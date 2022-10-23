# Install pi:
https://www.raspberrypi.com/software/
Download Raspberry Pi Imager on osx
Run “Raspberry Pi Imager” on osx
Choose OS: “Raspberry PI OD (32 bit)”
Write to SD card
Place SD card in PI and startup (with display and keyboard/mouse)
perform the following steps on the PI
finish installation (choose country and language)
username: pi
connect to wifi

# enable sshd
open raspberry pi configuration
enable: ssh, ic2
save
Finish the rest of the installation using ssh

# Update the system:
sudo apt-get update
sudo apt-get upgrade
sudo rpi-update

# Install i2c modules
#SKIP THIS STEP!!#  sudo apt-get install python-smbus i2c-tools
sudo modprobe i2c-dev
sudo modprobe i2c-bcm2708
i2cdetect -y 1

# Install java:
sudo apt install openjdk-8-jdk
(note, we need java8. Later versions are not supported by the used pi4j library)


# Configure java8 to be the default java
sudo update-alternatives --config java

# build pi4j native lib
## NIET NODIG ## sudo apt-get remove wiringpi -y ; niet nodig
## NIET NODIG ## sudo apt-get --yes install git-core gcc make ; niet nodig
cd ~
git clone https://github.com/WiringPi/WiringPi --branch master --single-branch wiringpi
cd ~/wiringpi
sudo ./build

# install stockfish and expect:
sudo apt-get install -y expect
sudo apt-get install stockfish

# Install schaakrob:
mkdir /home/pi/git
cd /home/pi/git
git clone https://github.com/robbertvdzon/schaakrob

# Initial build and run the code:
cd /home/pi/git/schaakrob
./update_and_run.sh

# Add the folling to  "crontab -e"
@reboot sh /home/pi/ui.sh

# Create /home/pi/ui.sh:
#!/bin/sh
echo startting >> /tmp/schaak.log
FILE=/tmp/rebuildui
cd /home/pi/git/schaakrob
while :
do
if test -f "$FILE"; then
echo "rebuild"
sudo /usr/bin/git pull
sudo ./mvnw package
rm $FILE
fi
java -jar schaakrob-server/target/schaakrob-server-1.0-shaded.jar >> /tmp/robot.log  2>&1
sleep 2
done

# Change permissions:
chmod a+x /home/pi/ui.sh


# After starting the server for the first time: fill in the calibration in the 'manual' section
A8: 16470,13130
H1: 4420,900
A11:2200,13000
H10: 400,850
A21: 20410,12700
H20:18600,450
Pakker hoogte: -1900
Snelheid: 0.8

# check i2c status
i2cdetect -y 1

# check gpio status:
gpio readall

# If you afterwards needs to enable an interface (ssh, i2c, SPI):
sudo raspi-config
choose: 3 Interface Options
