# Install pi:
https://www.raspberrypi.com/software/
Download and run  Raspberry Pi Imager on osx
Choose OS: “Raspberry PI OD (32 bit)” <--- Not 64 bit, otherwise pi4j will nog work!
Before writting, edit the settings:
    set hostname: schaakrobot
    enable ssh
    select 'User password authentication'
    set username: pi
    set password: xxx
    set wifi ssid and password
Write to SD card

Find ip address of pi with a keyboard and monitor attached, or use your router admin page.

ssh-copy-id pi@192.168.178.88
ssh pi@192.168.178.88
sudo raspi-config
choose: 3 Interface Options, and enable: i2c and SPI


# Update the system:
sudo apt-get update
sudo apt-get upgrade
sudo rpi-update

# Install i2c modules 
sudo modprobe i2c-dev
sudo modprobe i2c-bcm2708

# Install java:
wget https://mirrors.huaweicloud.com/java/jdk/8u202-b08/jdk-8u202-linux-arm32-vfp-hflt.tar.gz
tar -xzf jdk-8u202-linux-arm32-vfp-hflt.tar.gz
sudo mv jdk1.8.0_202 /opt/java8
echo 'export JAVA_HOME=/opt/java8' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc
sudo ln -s /opt/java8/bin/java /usr/bin/java
(note, we need java8. Later versions are not supported by the used pi4j library)


# build pi4j native lib
cd ~
git clone https://github.com/WiringPi/WiringPi --branch master --single-branch wiringpi
cd ~/wiringpi
sudo ./build

# install stockfish and expect:
sudo apt-get install -y expect
sudo apt-get install stockfish

# Install schaakrob:
mkdir ~/git
cd ~/git
git clone https://github.com/robbertvdzon/schaakrob

# Initial build and run the code:
mkdir ~/git/schaakrob/target
cd ~/git/schaakrob
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

# optional: check i2c status
sudo i2cdetect -y -a 1
