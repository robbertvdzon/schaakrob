Na installatie:
eerst via Gui: enable ssh via "raspberry pi configuration"
Daarna kun je inloggen met pi/raspberry

Update systeem:
sudo apt-get update
sudo apt-get upgrade
sudo rpi-update

Enable i2c via raspi-config
sudo raspi-config
(kies: 3 Interface Options / P5 I2C / enable )

Installeer i2c modules
sudo apt-get install python-smbus i2c-tools
sudo modprobe i2c-dev
sudo modprobe i2c-bcm2708
i2cdetect -y 1

Install java:
sudo apt install openjdk-8-jdk
(note, we need java8. Later versions are not supported by the used pi4j library)

Get code:
login
mkdir /home/pi/git
cd /home/pi/git
git clone https://github.com/robbertvdzon/schaakrob

Initial build and run the code:
cd /home/pi/git/schaakrob
./update_and_run.sh

voeg onderstaande regel toe via "crontab -e"
@reboot sh /home/pi/ui.sh

Create /home/pi/ui.sh:
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

Change permissies:
chmod a+x /home/pi/ui.sh


## SETUP CONNECTION TO nRF24L01
(following these steps: https://www.hackster.io/wirekraken/connecting-an-nrf24l01-to-raspberry-pi-9c0a57)


sudo raspi-config 
(enable SPI)
sudo reboot

sudo apt-get remove wiringpi -y
sudo apt-get --yes install git-core gcc make
cd ~
git clone https://github.com/WiringPi/WiringPi --branch master --single-branch wiringpi
cd ~/wiringpi
sudo ./build