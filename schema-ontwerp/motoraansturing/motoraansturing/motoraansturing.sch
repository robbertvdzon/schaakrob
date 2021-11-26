EESchema Schematic File Version 4
EELAYER 30 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L MCU_Module:Arduino_Nano_v3.x A?
U 1 1 60EF44B3
P 5200 2300
F 0 "A?" V 5246 1256 50  0000 R CNN
F 1 "Arduino_Nano_v3.x" V 5155 1256 50  0000 R CNN
F 2 "Module:Arduino_Nano" H 5200 2300 50  0001 C CIN
F 3 "http://www.mouser.com/pdfdocs/Gravitech_Arduino_Nano3_0.pdf" H 5200 2300 50  0001 C CNN
	1    5200 2300
	0    -1   -1   0   
$EndComp
$Comp
L Connector:Conn_01x03_Male J?
U 1 1 60EF509D
P 2850 6250
F 0 "J?" V 3004 6062 50  0000 R CNN
F 1 "Encoder1" V 2750 6400 50  0000 R CNN
F 2 "" H 2850 6250 50  0001 C CNN
F 3 "~" H 2850 6250 50  0001 C CNN
	1    2850 6250
	0    -1   -1   0   
$EndComp
$Comp
L Connector:Conn_01x04_Male J?
U 1 1 60EF5663
P 950 5600
F 0 "J?" H 1058 5881 50  0000 C CNN
F 1 "Input" H 750 5600 50  0000 C CNN
F 2 "" H 950 5600 50  0001 C CNN
F 3 "~" H 950 5600 50  0001 C CNN
	1    950  5600
	1    0    0    -1  
$EndComp
$Comp
L Device:Buzzer BZ?
U 1 1 60EF5C54
P 6750 6000
F 0 "BZ?" V 6708 6152 50  0000 L CNN
F 1 "Buzzer" V 7000 5950 50  0000 L CNN
F 2 "" V 6725 6100 50  0001 C CNN
F 3 "~" V 6725 6100 50  0001 C CNN
	1    6750 6000
	0    1    1    0   
$EndComp
$Comp
L Connector:Conn_01x01_Male J?
U 1 1 60EF621E
P 7650 3500
F 0 "J?" H 7622 3432 50  0000 R CNN
F 1 "Step 1" H 7622 3523 50  0000 R CNN
F 2 "" H 7650 3500 50  0001 C CNN
F 3 "~" H 7650 3500 50  0001 C CNN
	1    7650 3500
	-1   0    0    1   
$EndComp
Wire Wire Line
	5800 2800 5800 3500
Wire Wire Line
	5800 3500 7450 3500
$Comp
L Connector:Conn_01x01_Male J?
U 1 1 60EF7C4E
P 7650 3700
F 0 "J?" H 7622 3632 50  0000 R CNN
F 1 "Enable" H 7622 3723 50  0000 R CNN
F 2 "" H 7650 3700 50  0001 C CNN
F 3 "~" H 7650 3700 50  0001 C CNN
	1    7650 3700
	-1   0    0    1   
$EndComp
$Comp
L Connector:Conn_01x01_Male J?
U 1 1 60EF7FBE
P 7650 3900
F 0 "J?" H 7622 3832 50  0000 R CNN
F 1 "Step 2" H 7622 3923 50  0000 R CNN
F 2 "" H 7650 3900 50  0001 C CNN
F 3 "~" H 7650 3900 50  0001 C CNN
	1    7650 3900
	-1   0    0    1   
$EndComp
$Comp
L Connector:Conn_01x01_Male J?
U 1 1 60EF81FA
P 7650 4100
F 0 "J?" H 7622 4032 50  0000 R CNN
F 1 "Dir" H 7622 4123 50  0000 R CNN
F 2 "" H 7650 4100 50  0001 C CNN
F 3 "~" H 7650 4100 50  0001 C CNN
	1    7650 4100
	-1   0    0    1   
$EndComp
Wire Wire Line
	5100 2800 5100 3700
Wire Wire Line
	5100 3700 7450 3700
Wire Wire Line
	5000 2800 5000 3900
Wire Wire Line
	5000 3900 7450 3900
Wire Wire Line
	4900 2800 4900 4100
Wire Wire Line
	4900 4100 7450 4100
Wire Wire Line
	5500 2800 5500 4900
Wire Wire Line
	5500 4900 6850 4900
$Comp
L Connector:Conn_01x03_Male J?
U 1 1 60EF8DB6
P 3500 6250
F 0 "J?" V 3654 6062 50  0000 R CNN
F 1 "Encoder2" V 3400 6400 50  0000 R CNN
F 2 "" H 3500 6250 50  0001 C CNN
F 3 "~" H 3500 6250 50  0001 C CNN
	1    3500 6250
	0    -1   -1   0   
$EndComp
$Comp
L Connector:Conn_01x02_Male J?
U 1 1 60EF9085
P 6000 6250
F 0 "J?" V 6154 6062 50  0000 R CNN
F 1 "Sensor2" V 5900 6350 50  0000 R CNN
F 2 "" H 6000 6250 50  0001 C CNN
F 3 "~" H 6000 6250 50  0001 C CNN
	1    6000 6250
	0    -1   -1   0   
$EndComp
$Comp
L Connector:Conn_01x02_Male J?
U 1 1 60EF93DE
P 5500 6250
F 0 "J?" V 5654 6062 50  0000 R CNN
F 1 "Sensor1" V 5400 6350 50  0000 R CNN
F 2 "" H 5500 6250 50  0001 C CNN
F 3 "~" H 5500 6250 50  0001 C CNN
	1    5500 6250
	0    -1   -1   0   
$EndComp
$Comp
L Connector:Conn_01x02_Male J?
U 1 1 60EF9810
P 2150 6250
F 0 "J?" V 2304 6062 50  0000 R CNN
F 1 "Fan" V 2050 6250 50  0000 R CNN
F 2 "" H 2150 6250 50  0001 C CNN
F 3 "~" H 2150 6250 50  0001 C CNN
	1    2150 6250
	0    -1   -1   0   
$EndComp
Wire Wire Line
	1150 5800 1700 5800
Wire Wire Line
	6000 5800 6000 6050
Wire Wire Line
	5500 6050 5500 5800
Connection ~ 5500 5800
Wire Wire Line
	5500 5800 6000 5800
Wire Wire Line
	3400 6050 3400 5800
Connection ~ 3400 5800
Wire Wire Line
	3400 5800 5500 5800
Wire Wire Line
	2750 6050 2750 5800
Connection ~ 2750 5800
Wire Wire Line
	2750 5800 3400 5800
Wire Wire Line
	5200 2800 5200 5250
Wire Wire Line
	5200 5250 6100 5250
Wire Wire Line
	6100 5250 6100 6050
Wire Wire Line
	5400 2800 5400 5500
Wire Wire Line
	5400 5500 5600 5500
Wire Wire Line
	5600 5500 5600 6050
Wire Wire Line
	4800 2800 4800 5400
Wire Wire Line
	4800 5400 3600 5400
Wire Wire Line
	3600 5400 3600 6050
Wire Wire Line
	5300 2800 5300 5500
Wire Wire Line
	5300 5500 2950 5500
Wire Wire Line
	2950 5500 2950 6050
Wire Wire Line
	1150 5700 2150 5700
Wire Wire Line
	3500 5700 3500 6050
Wire Wire Line
	2850 6050 2850 5700
Connection ~ 2850 5700
Wire Wire Line
	2850 5700 3500 5700
Wire Wire Line
	2650 2400 4200 2400
Connection ~ 2650 5700
Wire Wire Line
	2650 5700 2850 5700
Wire Wire Line
	6000 5800 6200 5800
Wire Wire Line
	6200 5800 6200 2300
Connection ~ 6000 5800
$Comp
L Device:R R?
U 1 1 60F0CBFA
P 1900 4100
F 0 "R?" H 1970 4146 50  0000 L CNN
F 1 "200" H 1970 4055 50  0000 L CNN
F 2 "" V 1830 4100 50  0001 C CNN
F 3 "~" H 1900 4100 50  0001 C CNN
	1    1900 4100
	1    0    0    -1  
$EndComp
Wire Wire Line
	1900 4250 1900 4450
Wire Wire Line
	2200 6050 2250 6050
Wire Wire Line
	5700 2800 5700 3500
Wire Wire Line
	5700 3500 1900 3500
Wire Wire Line
	1900 3500 1900 3950
Wire Wire Line
	6200 5800 6650 5800
Wire Wire Line
	6650 5800 6650 5900
Connection ~ 6200 5800
Wire Wire Line
	6850 4900 6850 5900
Wire Wire Line
	1150 5600 1350 5600
Wire Wire Line
	1350 5600 1350 1650
Wire Wire Line
	1350 1650 5600 1650
Wire Wire Line
	5600 1650 5600 1800
Wire Wire Line
	1150 5500 1250 5500
Wire Wire Line
	1250 5500 1250 1550
Wire Wire Line
	1250 1550 5700 1550
Wire Wire Line
	5700 1550 5700 1800
$Comp
L Device:Q_NMOS_DGS Q?
U 1 1 60F2D031
P 2050 4650
F 0 "Q?" V 2299 4650 50  0000 C CNN
F 1 "Q_NMOS_DGS" V 2390 4650 50  0000 C CNN
F 2 "" H 2250 4750 50  0001 C CNN
F 3 "~" H 2050 4650 50  0001 C CNN
	1    2050 4650
	0    1    1    0   
$EndComp
Wire Wire Line
	2650 2400 2650 5700
Wire Wire Line
	1900 4450 2050 4450
Wire Wire Line
	2250 4750 2250 6050
Connection ~ 2250 6050
$Comp
L Device:R R?
U 1 1 60F344EC
P 1700 5100
F 0 "R?" H 1770 5146 50  0000 L CNN
F 1 "10K" H 1770 5055 50  0000 L CNN
F 2 "" V 1630 5100 50  0001 C CNN
F 3 "~" H 1700 5100 50  0001 C CNN
	1    1700 5100
	1    0    0    -1  
$EndComp
Wire Wire Line
	1700 4750 1850 4750
Wire Wire Line
	1700 4750 1700 4950
Wire Wire Line
	1700 5250 1700 5800
Connection ~ 1700 5800
Wire Wire Line
	1700 5800 2750 5800
Wire Wire Line
	2150 6050 2150 5700
Connection ~ 2150 5700
Wire Wire Line
	2150 5700 2650 5700
$EndSCHEMATC